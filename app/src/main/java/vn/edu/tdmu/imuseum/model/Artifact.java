package vn.edu.tdmu.imuseum.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by nvulinh on 8/25/17.
 * @author Nguyễn Vũ Linh
 * Model Artifact
 */

public class Artifact  implements Serializable{
    private int idArtifact;
    private  String nameArtifact;
    private String titleArtifact;
    private String description;
    private String author;
    private Date dateCreated;
    private Date dateEdit;
    private int view;
    private Boolean active;
    private BeaconArtifact beaconArtifact;
    private CategoryArtifact categoryArtifact;
    private LocationArtifact locationArtifact;
    private List<Media> medias;

    private List<Artifact> artifacts;
    public List<Artifact> getArtifacts() {
        return artifacts;
    }

    public void setArtifacts(List<Artifact> artifacts) {
        this.artifacts = artifacts;
    }

    public Artifact() {
    }

    public Artifact(int idArtifact, String nameArtifact, String titleArtifact, String description, String author, Date dateCreated, Date dateEdit, int view, Boolean active, BeaconArtifact beaconArtifact, CategoryArtifact categoryArtifact, LocationArtifact locationArtifact, List<Media> medias) {
        this.idArtifact = idArtifact;
        this.nameArtifact = nameArtifact;
        this.titleArtifact = titleArtifact;
        this.description = description;
        this.author = author;
        this.dateCreated = dateCreated;
        this.dateEdit = dateEdit;
        this.view = view;
        this.active = active;
        this.beaconArtifact = beaconArtifact;
        this.categoryArtifact = categoryArtifact;
        this.locationArtifact = locationArtifact;
        this.medias = medias;
    }

    public int getIdArtifact() {
        return idArtifact;
    }

    public void setIdArtifact(int idArtifact) {
        this.idArtifact = idArtifact;
    }

    public String getNameArtifact() {
        return nameArtifact;
    }

    public void setNameArtifact(String nameArtifact) {
        this.nameArtifact = nameArtifact;
    }

    public String getTitleArtifact() {
        return titleArtifact;
    }

    public void setTitleArtifact(String titleArtifact) {
        this.titleArtifact = titleArtifact;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateEdit() {
        return dateEdit;
    }

    public void setDateEdit(Date dateEdit) {
        this.dateEdit = dateEdit;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public BeaconArtifact getBeaconArtifact() {
        return beaconArtifact;
    }

    public void setBeaconArtifact(BeaconArtifact beaconArtifact) {
        this.beaconArtifact = beaconArtifact;
    }

    public CategoryArtifact getCategoryArtifact() {
        return categoryArtifact;
    }

    public void setCategoryArtifact(CategoryArtifact categoryArtifact) {
        this.categoryArtifact = categoryArtifact;
    }

    public LocationArtifact getLocationArtifact() {
        return locationArtifact;
    }

    public void setLocationArtifact(LocationArtifact locationArtifact) {
        this.locationArtifact = locationArtifact;
    }

    public List<Media> getMedias() {
        return medias;
    }

    public void setMedias(List<Media> medias) {
        this.medias = medias;
    }
}
