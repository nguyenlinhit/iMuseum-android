package vn.edu.tdmu.imuseum.manager;


import vn.edu.tdmu.imuseum.model.response.ResponseArtifact;

public class MapArifactBeaconManager {
    private static final String LOG_TAG = MapArifactBeaconManager.class.getSimpleName();
    private static MapArifactBeaconManager instance;
    private static boolean isGet = false;
    private ResponseArtifact connectedBeacons;

    public static synchronized MapArifactBeaconManager getInstance() {
        MapArifactBeaconManager mapItemBeaconManager;
        synchronized (MapArifactBeaconManager.class) {
            if (instance == null) {
                instance = new MapArifactBeaconManager();
                instance.connectedBeacons = new ResponseArtifact();
                isGet = true;
            }
            mapItemBeaconManager = instance;
        }
        return mapItemBeaconManager;
    }

    public ResponseArtifact getConnectedBeacons() {
        return this.connectedBeacons;
    }


}
