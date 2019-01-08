package vn.edu.tdmu.imuseum.model.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nvulinh on 11/10/17.
 *
 */

public class ArtifactBeaconInfor {
    @SerializedName("idArtifact")
    private int idArtifact;
    @SerializedName("listImage")
    private List<String> listImage;
    @SerializedName("major")
    private int major;
    @SerializedName("nameArtifact")
    private String nameArtifact;

    public ArtifactBeaconInfor() {
    }

    public ArtifactBeaconInfor(int idArtifact, List<String> listImage, int major, String nameArtifact) {
        this.idArtifact = idArtifact;
        this.listImage = listImage;
        this.major = major;
        this.nameArtifact = nameArtifact;
    }

    public int getIdArtifact() {
        return idArtifact;
    }

    public void setIdArtifact(int idArtifact) {
        this.idArtifact = idArtifact;
    }

    public List<String> getListImage() {
        return listImage;
    }

    public void setListImage(List<String> listImage) {
        this.listImage = listImage;
    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public String getNameArtifact() {
        return nameArtifact;
    }

    public void setNameArtifact(String nameArtifact) {
        this.nameArtifact = nameArtifact;
    }
}
