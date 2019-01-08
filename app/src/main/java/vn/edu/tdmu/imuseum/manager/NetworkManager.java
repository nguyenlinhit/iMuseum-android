package vn.edu.tdmu.imuseum.manager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import vn.edu.tdmu.imuseum.model.Artifact;
import vn.edu.tdmu.imuseum.model.response.ResponseArtifact;
import vn.edu.tdmu.imuseum.ultils.Constant;
import vn.edu.tdmu.imuseum.ultils.UnixEpochDateTypeAdapter;
import vn.edu.tdmu.imuseum.views.MyApplication;

/**
 * Created by nvulinh on 11/12/17.
 *
 */

public class NetworkManager {
    private static final int BUFFER_SIZE_DEFAULT = 4096;
    private static final String CONTENT_TYPE_ENCODED_DATA = "application/x-www-form-urlencoded";
    private static final String CONTENT_TYPE_JSON = "application/json; charset=UTF-8";
    private static final String LOG_TAG = NetworkManager.class.getSimpleName();
    public static final String NET_SUPPORT_MOBILE = "3G";
    public static final String NET_SUPPORT_WIFI = "WIFI";
    public static final String[] NET_SUPPORT = new String[]{NET_SUPPORT_WIFI, NET_SUPPORT_MOBILE};
    private static final int TIME_OUT = 60000;

    public static boolean isConnected(Context context){
        NetworkInfo networkInfo = getConnectedNetwork(context);
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }
    public static boolean is3GConnected(Context context){
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getNetworkInfo(0).isConnected();
    }

    private static NetworkInfo getConnectedNetwork(Context context) {
        return ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
    }

    public static boolean releaseResourceSafely(HttpURLConnection httpURLConnection, Closeable... closeables){
        boolean isSuccessReleased = false;
        try {
            for (Closeable closeable : closeables) {
                if (closeable != null) {
                    closeable.close();
                }
            }

            isSuccessReleased = true;
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Exception " + e.getMessage());
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        } catch (Throwable th) {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return isSuccessReleased;
    }

    public List<Artifact> executeRequest(String urlString, Map<String, String> params, String jsonDataContent, String method){
        IOException e;

        HttpURLConnection httpsURLConnection = null;

        List<Artifact> response = new ArrayList<>();
        ResponseArtifact artifacts = new ResponseArtifact(response);

        if (isConnected(MyApplication.getInstance())){
            InputStream is = null;
            try {
                URL url = new URL(makeUrlParams(urlString, params));
                Log.e(LOG_TAG, "Call url: " + url);
                httpsURLConnection = (HttpURLConnection) url.openConnection();
                bindingHttpUrlConnection(jsonDataContent, method, httpsURLConnection);
                is = httpsURLConnection.getInputStream();
                byte[] data = new byte[4096];
                StringBuilder sb = new StringBuilder();
                while (true){
                    int length = is.read(data);
                    if (length == -1){
                        break;
                    }

                    sb.append(new String(data, 0, length));
                }
                Log.e(LOG_TAG, "Respone string: " + sb.toString());
                if (!TextUtils.isEmpty(sb.toString())){
                    response =  Arrays.asList(new Gson().fromJson(sb.toString(), Artifact[].class));
                    artifacts.setArtifacts(response);
                }
                releaseResourceSafely(httpsURLConnection, is);
            } catch (IOException e1) {
                e1.printStackTrace();
                e = e1;
                //response.setStatusCode(-3);
                //response.setMessage("Không thể lấy dữ liệu!!!");
                Log.e(LOG_TAG, "Request Timeout: " + e);
                releaseResourceSafely(httpsURLConnection, is);
            }
        }

        return response;
    }

    public List<Artifact> executeRequestAPI(String urlString, String jsonDataContent, String method){
        IOException e;

        HttpURLConnection httpsURLConnection = null;

        List<Artifact> response = new ArrayList<>();
        ResponseArtifact artifacts = new ResponseArtifact(response);

        if (isConnected(MyApplication.getInstance())){
            InputStream is = null;
            try {
                URL url = new URL(urlString);
                Log.e(LOG_TAG, "Call url: " + url);
                httpsURLConnection = (HttpURLConnection) url.openConnection();
                bindingHttpUrlConnection(jsonDataContent, method, httpsURLConnection);
                is = httpsURLConnection.getInputStream();
                byte[] data = new byte[4096];
                StringBuilder sb = new StringBuilder();
                while (true){
                    int length = is.read(data);
                    if (length == -1){
                        break;
                    }

                    sb.append(new String(data, 0, length));
                }
                Log.e(LOG_TAG, "Respone string: " + sb.toString());
                if (!TextUtils.isEmpty(sb.toString())){
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.serializeNulls().registerTypeAdapter(Date.class, UnixEpochDateTypeAdapter.getUnixEpochDateTypeAdapter()).create();
                    response =  Arrays.asList(gson.fromJson(sb.toString(), Artifact[].class));
                    artifacts.setArtifacts(response);
                    artifacts.setData(sb.toString());
                }
                releaseResourceSafely(httpsURLConnection, is);
            } catch (IOException e1) {
                e1.printStackTrace();
                e = e1;
                //response.setStatusCode(-3);
                //response.setMessage("Không thể lấy dữ liệu!!!");
                Log.e(LOG_TAG, "Request Timeout: " + e);
                releaseResourceSafely(httpsURLConnection, is);
            }
        }

        return response;
    }

    private void bindingHttpUrlConnection(String jsonDataContent, String method, HttpURLConnection httpsURLConnection) throws IOException {
        httpsURLConnection.setConnectTimeout(TIME_OUT);
        httpsURLConnection.setReadTimeout(TIME_OUT);
        httpsURLConnection.setRequestMethod(method);
        if (TextUtils.isEmpty(jsonDataContent)){
            httpsURLConnection.setRequestProperty("Content-Type", CONTENT_TYPE_ENCODED_DATA);
        }
        httpsURLConnection.setRequestProperty("Content-Type", CONTENT_TYPE_JSON);
        httpsURLConnection.connect();
        processRawAPIData(httpsURLConnection, jsonDataContent);
    }

    private void processRawAPIData(HttpURLConnection httpsURLConnection, String jsonDataContent) {
        DataOutputStream dataOutputStream = null;

        try {
            DataOutputStream dataOutputStream1 = new DataOutputStream(httpsURLConnection.getOutputStream());
            try {
                dataOutputStream1.writeBytes(jsonDataContent);
                dataOutputStream1.flush();
                try {
                    if (dataOutputStream1 != null){
                        dataOutputStream1.close();
                        dataOutputStream = dataOutputStream1;
                        return;
                    }
                } catch (IOException e2){
                    e2.printStackTrace();
                    dataOutputStream = dataOutputStream1;
                    return;
                }
            } catch (IOException e3){
                e3.printStackTrace();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private String makeUrlParams(String urlString, Map<String, String> params) {
        return urlString + params;
    }


    public List<Artifact> executePostRequest(String urlString, String jsonDataContent) {
        return executeRequestAPI(urlString, jsonDataContent, Constant.POST_METHOD);
    }

    public List<Artifact> executeGetRequest(String urlString, String jsonDataContent) {
        return executeRequestAPI(urlString,jsonDataContent, Constant.GET_METHOD);
    }
}
