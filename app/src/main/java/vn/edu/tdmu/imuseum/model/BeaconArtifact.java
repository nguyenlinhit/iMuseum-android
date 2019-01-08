package vn.edu.tdmu.imuseum.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by nvulinh on 8/25/17.
 * @author Nguyễn Vũ Linh
 */

public class BeaconArtifact implements Serializable {
    private Date dateCreated;
    private Date dateEdit;
    private LocationBeacon locationBeacon;
    private Boolean active;
    private int id;

    public BeaconArtifact() {
    }

    public BeaconArtifact(Date dateCreated, Date dateEdit, LocationBeacon locationBeacon, Boolean active, int id) {
        this.dateCreated = dateCreated;
        this.dateEdit = dateEdit;
        this.locationBeacon = locationBeacon;
        this.active = active;
        this.id = id;
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

    public LocationBeacon getLocationBeacon() {
        return locationBeacon;
    }

    public void setLocationBeacon(LocationBeacon locationBeacon) {
        this.locationBeacon = locationBeacon;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
