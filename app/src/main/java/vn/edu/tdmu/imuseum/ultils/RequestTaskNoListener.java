package vn.edu.tdmu.imuseum.ultils;

import android.os.AsyncTask;

import java.util.List;

import vn.edu.tdmu.imuseum.manager.NetworkManager;
import vn.edu.tdmu.imuseum.model.Artifact;
import vn.edu.tdmu.imuseum.model.response.ResponseArtifact;

/**
 * Created by nvulinh on 11/16/17.
 *
 */

public class RequestTaskNoListener extends AsyncTask<String, Void, List<Artifact>>{

    private String jsonDataContent;
    public static ResponseArtifact responseArtifact = new ResponseArtifact();

    public RequestTaskNoListener() {

    }

    public RequestTaskNoListener(String jsonDataContent) {
        this.jsonDataContent = jsonDataContent;
    }

    @Override
    protected List<Artifact> doInBackground(String... strings) {
        String url = strings[0];
        String method = strings[1];
        NetworkManager executor = new NetworkManager();
        if (Constant.GET_METHOD.equals(method)){
            return executor.executeGetRequest(url,this.jsonDataContent);
        }
        return executor.executeGetRequest(url, this.jsonDataContent);
    }

    @Override
    protected void onPostExecute(List<Artifact> artifacts) {
        super.onPostExecute(artifacts);
        responseArtifact.setArtifacts(artifacts);
        getAllArtifact(artifacts);
    }


    public static List<Artifact> getAllArtifact(List<Artifact> artifacts){
        return artifacts;
    }
}
