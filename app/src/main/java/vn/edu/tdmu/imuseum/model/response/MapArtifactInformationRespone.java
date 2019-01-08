package vn.edu.tdmu.imuseum.model.response;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import vn.edu.tdmu.imuseum.model.Artifact;

/**
 * Created by nvulinh on 11/8/17.
 * nguyen linh
 */

public class MapArtifactInformationRespone {
    @SerializedName("data")
    private List<Artifact> data;

    public MapArtifactInformationRespone() {
        this.data = new ArrayList<>();
    }

    public MapArtifactInformationRespone(List<Artifact> data) {
        this.data = data;
    }

    public List<Artifact> getData() {
        return data;
    }

    public void setData(List<Artifact> data) {
        this.data = data;
    }

    public Artifact getMapArtifactInforList(int major){
        for (Artifact artifact : this.data){
            if (artifact != null && artifact.getBeaconArtifact().getId() != 0 && major == artifact.getBeaconArtifact().getId()){
                return artifact;
            }
        }
        return null;
    }
}
