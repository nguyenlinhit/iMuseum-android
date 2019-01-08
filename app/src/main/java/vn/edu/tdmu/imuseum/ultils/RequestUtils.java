package vn.edu.tdmu.imuseum.ultils;


import android.util.Log;

import java.util.List;
import java.util.concurrent.ExecutionException;

import vn.edu.tdmu.imuseum.listener.RequestApiListener;
import vn.edu.tdmu.imuseum.manager.NetworkManager;
import vn.edu.tdmu.imuseum.model.Artifact;
import vn.edu.tdmu.imuseum.model.response.ResponseArtifact;

/**
 * Created by nvulinh on 11/10/17.
 *
 */

public class RequestUtils {
    private static RequestUtils instance;

    public static RequestUtils getInstance(){
        if(instance == null){
            instance = new RequestUtils();
        }
        return instance;
    }

    private String getUrl(String apiUrl){
        return apiUrl;
        //return Server.ARTIFACT;
    }

    public List<Artifact> doGetArtifactInfoByBeaconId( String major) throws ExecutionException, InterruptedException {
        Log.e("URL", Server.GET_ARTIFACT_BY_NEAR_BEACON + major + Server.ADD_POSFIX_ARTIFACT);
        return new RequestTaskNoListener(null).execute(getUrl(Server.GET_ARTIFACT_BY_NEAR_BEACON + major + Server.ADD_POSFIX_ARTIFACT), Constant.GET_METHOD).get();
    }

    public void doGetAllMapBeaconItem(RequestApiListener requestApiListener) {
        new RequestTask(requestApiListener, null).execute(getUrl(Server.ARTIFACT), Constant.GET_METHOD);

    }

    public List<Artifact> doGetAllMapBeaconItemNoListener() {
        try {
            return new RequestTaskNoListener(null).execute(getUrl(Server.ARTIFACT), Constant.GET_METHOD).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Artifact doGetArtifactInfoByBeaconIdDeTail(int major, int idArtifact) throws ExecutionException, InterruptedException {
        List<Artifact> artifactList = new RequestTaskNoListener(null).execute(getUrl(Server.GET_ARTIFACT_BY_NEAR_BEACON + major + Server.ADD_POSFIX_ARTIFACT), Constant.GET_METHOD).get();

        for (Artifact artifact : artifactList){
            if (idArtifact == artifact.getIdArtifact()){
                return artifact;
            }
        }
        return null;
    }
}
