package vn.edu.tdmu.imuseum.model;

import java.io.Serializable;

/**
 * Created by nvulinh on 8/25/17.
 * @author Nguyễn Vũ Linh
 */

 class LocationBeacon implements Serializable {
    private int idLocation;
    private String nameLoc;
    private String type;
    private int idParent;
    private String path;

    public LocationBeacon() {
    }

    public LocationBeacon(int idLocation, String nameLoc, String type, int idParent, String path) {
        this.idLocation = idLocation;
        this.nameLoc = nameLoc;
        this.type = type;
        this.idParent = idParent;
        this.path = path;
    }

    public int getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(int idLocation) {
        this.idLocation = idLocation;
    }

    public String getNameLoc() {
        return nameLoc;
    }

    public void setNameLoc(String nameLoc) {
        this.nameLoc = nameLoc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIdParent() {
        return idParent;
    }

    public void setIdParent(int idParent) {
        this.idParent = idParent;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
