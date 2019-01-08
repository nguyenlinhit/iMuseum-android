package vn.edu.tdmu.imuseum.ultils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import vn.edu.tdmu.imuseum.dialog.BaseDialog;
import vn.edu.tdmu.imuseum.dialog.ProgressDialog;
import vn.edu.tdmu.imuseum.listener.RequestApiListener;

/**
 * Created by nvulinh on 11/12/17.
 *
 */

public class Utils {

    private static final String LOG_TAG = "Utils";
    public static AlertDialog alertDialog;
    public static TextToSpeech textToSpeech;

    private Utils() {
    }

    public static void showDebugLog(String Tag, String message) {
        Log.d(Tag, "Thread[" + Thread.currentThread().getId() + "] -> " + message);
    }

    public static void showWarningLog(String Tag, String message) {
        Log.w(Tag, "Thread[" + Thread.currentThread().getId() + "] -> " + message);
    }

    public static void showErrorLog(String Tag, String message) {
        Log.e(Tag, "Thread[" + Thread.currentThread().getId() + "] -> " + message);
    }

    public static String getDeviceUID(Context context) {
        String androidId = null;
        try {
            androidId = Settings.Secure.getString(context.getContentResolver(), "android_id");
        } catch (Exception ex) {
            showWarningLog(LOG_TAG, "Can't get android ID, exception: " + ex.getMessage());
        }
        return androidId;
    }

    public static String getDateFromTimeStamp(int time, boolean isMonth) {
        SimpleDateFormat formatter;
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(((long) time) * 1000);
        if (isMonth) {
            formatter = new SimpleDateFormat("yyyy/MM", Locale.US);
        } else {
            formatter = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
        }
        return formatter.format(cal.getTime());
    }

    public static String getTimeFromTimestamp(int time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(((long) time) * 1000);
        return new SimpleDateFormat(Constant.FORMAT_HOUR, Locale.US).format(cal.getTime());
    }

    public static boolean isApplicationInstalled(Context ctx, String uri) {
        try {
            ctx.getPackageManager().getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }


    public static Boolean isActivityRunning(Context context, Class activityClass) {
        for (ActivityManager.RunningTaskInfo task : ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getRunningTasks(Integer.MAX_VALUE)) {
            if (activityClass.getCanonicalName().equalsIgnoreCase(task.baseActivity.getClassName())) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    public static String getFullTimeFromTimeStamp(int time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(((long) time) * 1000);
        return new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(cal.getTime());
    }

    public static void showDialog(ProgressDialog progressDialog, Activity activity) {
        if (progressDialog != null && activity != null && !activity.isFinishing()) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            progressDialog.show();
        }
    }

    public static void dismissDialog(ProgressDialog progressDialog, Activity activity) {
        if (progressDialog != null && activity != null && !activity.isFinishing() && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public static String getPhoneNumber(Context context) {
        return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number().substring(1);
    }

    public static boolean hasFroyo() {
        return Build.VERSION.SDK_INT >= 8;
    }

    public static boolean hasGingerbread() {
        return Build.VERSION.SDK_INT >= 9;
    }

    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= 11;
    }

    public static boolean hasHoneycombMR1() {
        return Build.VERSION.SDK_INT >= 12;
    }

    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= 16;
    }

    public static boolean hasKitKat() {
        return Build.VERSION.SDK_INT >= 19;
    }

    public static void showAlertDialog(Context context, String message, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton("OK", onClickListener);
        if (alertDialog == null) {
            alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } else if (!alertDialog.isShowing()) {
            alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }


    public static void showDialog(BaseDialog baseDialog) {
        if (baseDialog != null && !baseDialog.isShowing()) {
            baseDialog.show();
        }
    }

    public static void dismissDialog(BaseDialog baseDialog) {
        if (baseDialog != null && baseDialog.isShowing()) {
            baseDialog.dismiss();
        }
    }

    public static void showToast(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void processData(final Activity activity, final RequestApiListener registerRequestListener, final RequestApiListener getItemBeaconListener) {
        //RequestUtils.getInstance().doGetAllMapBeaconItem(registerRequestListener);
    }

    public static String translate(String text, String sourceLang, String targetLang) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20180303T065416Z.8b0cb060dea9b957.1fd0f6f00b48d12308ecace9dcdaa7febdbd5c09&text="+text+"&lang="+sourceLang+"-"+targetLang;
        String text_trans = null;
        Request request = new Request.Builder().url(url).build();
        try(Response response = client.newCall(request).execute()) {
            String jsonData = response.body().string();
            JSONObject jsonObject = new JSONObject(jsonData);
            text_trans = jsonObject.getString("text");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return text_trans;
    }

}
