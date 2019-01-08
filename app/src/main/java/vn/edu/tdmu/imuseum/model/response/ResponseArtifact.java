package vn.edu.tdmu.imuseum.model.response;



import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import vn.edu.tdmu.imuseum.model.Artifact;

/**
 * Created by nvulinh on 11/15/17.
 *
 */

public class ResponseArtifact implements Serializable {
    private List<Artifact> artifacts;
    private String  data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ResponseArtifact() {
        this.artifacts = new ArrayList<>();
    }

    public ResponseArtifact(List<Artifact> artifacts) {
        this.artifacts = artifacts;
    }

    public List<Artifact> getArtifacts() {
        return artifacts;
    }

    public void setArtifacts(List<Artifact> artifacts) {
        this.artifacts = artifacts;
    }

    public Artifact getMapArtifactInfor(int major){
        for (Artifact artifact : this.artifacts){
            if (artifact != null && artifact.getBeaconArtifact().getId() != 0 && major == artifact.getBeaconArtifact().getId()){
                return artifact;
            }
        }
        return null;
    }

    public List<Artifact> getMapArtifacts(int major){
        List<Artifact> artifactList = new ArrayList<>();
        for (Artifact artifact : this.artifacts){
            if (major == artifact.getBeaconArtifact().getId()){
                artifactList.add(artifact);
            }
        }
            return artifactList;
    }
}
