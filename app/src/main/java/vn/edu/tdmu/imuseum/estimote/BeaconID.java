package vn.edu.tdmu.imuseum.estimote;

import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;

import java.util.UUID;

/**
 * Created by nvulinh on 9/1/17.
 *
 */

public class BeaconID {
    private UUID proximityUUID;
    private int major;
    private int minor;
    private String macAddress;

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public BeaconID(UUID proximityUUID, int major, int minor) {
        this.proximityUUID = proximityUUID;
        this.major = major;
        this.minor = minor;
    }

    public BeaconID(String UUIDString, int major, int minor){
        this(UUID.fromString(UUIDString), major, minor);
    }

    public static BeaconID fromBeaconID(Beacon beacon){
        return new BeaconID(beacon.getProximityUUID(), beacon.getMajor(), beacon.getMinor());
    }

    public UUID getProximityUUID() {
        return proximityUUID;
    }

    public void setProximityUUID(UUID proximityUUID) {
        this.proximityUUID = proximityUUID;
    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMinor() {
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

    public BeaconRegion toBeaconRegion() {
        return new BeaconRegion(toString(), getProximityUUID(), getMajor(), getMinor());
    }

    public String toString() {
        return getProximityUUID().toString() + ":" + getMajor() + ":" + getMinor();
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null){
            return false;
        }

        if (obj == this){
            return true;
        }

        if (getClass() != obj.getClass()){
            return super.equals(obj);
        }

        BeaconID other = (BeaconID) obj;

        return getProximityUUID().equals(other.getProximityUUID())
                && getMajor() == other.getMajor()
                && getMinor() == other.getMinor();
    }
}
