package vn.edu.tdmu.imuseum.ultils;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;
import java.util.Map;

import vn.edu.tdmu.imuseum.listener.RequestApiListener;
import vn.edu.tdmu.imuseum.manager.NetworkManager;
import vn.edu.tdmu.imuseum.model.Artifact;
import vn.edu.tdmu.imuseum.model.response.Response;

/**
 * Created by nvulinh on 11/12/17.
 *
 */

public class RequestTask extends AsyncTask<String, Void, List<Artifact>> {

    private String jsonDataContent;
    private RequestApiListener requestApiListener;

    public RequestTask(RequestApiListener requestApiListener, String jsonDataContent){
        this.jsonDataContent = jsonDataContent;
        this.requestApiListener = requestApiListener;
    }
    public RequestTask(String jsonDataContent){
        this.jsonDataContent = jsonDataContent;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (this.requestApiListener != null){
            this.requestApiListener.onPrepareRequest();
        }

    }

    @Override
    protected List<Artifact> doInBackground(String... strings) {
        String url = strings[0];
        String method = strings[1];
        NetworkManager executor = new NetworkManager();
        if (Constant.GET_METHOD.equals(method)){
            return executor.executeGetRequest(url,this.jsonDataContent);
        }
        return executor.executePostRequest(url, this.jsonDataContent);
    }

    @Override
    protected void onPostExecute(List<Artifact> response) {
        super.onPostExecute(response);
        if (this.requestApiListener != null){
            this.requestApiListener.onRequestDone(response);
        }
    }

    protected void onCancelled(){
        Log.e("onCancelled", "onCancalled 1");
        super.onCancelled();
        this.requestApiListener = new RequestApiListener() {
            @Override
            public void onRequestDone(List<Artifact> response) {

            }

            @Override
            public void onPrepareRequest() {

            }
        };
    }

    protected void onCancelled(List<Artifact> response){
        Log.e("onCancelled", "onCancalled 2");
        super.onCancelled(response);
        this.requestApiListener = new RequestApiListener() {
            @Override
            public void onRequestDone(List<Artifact> response) {

            }

            @Override
            public void onPrepareRequest() {

            }
        };
    }
}
