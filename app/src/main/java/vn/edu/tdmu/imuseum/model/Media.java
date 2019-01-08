package vn.edu.tdmu.imuseum.model;

import java.io.Serializable;

/**
 * Created by nvulinh on 8/26/17.
 * @author nvulinh
 */

public class Media implements Serializable{
    private int idMedia;
    private String media;
    private String title;

    public Media() {
    }

    public Media(int idMedia, String media, String title) {
        this.idMedia = idMedia;
        this.media = media;
        this.title = title;
    }

    public int getIdMedia() {
        return idMedia;
    }

    public void setIdMedia(int idMedia) {
        this.idMedia = idMedia;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
