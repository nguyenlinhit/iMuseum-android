package vn.edu.tdmu.imuseum.views;

import android.app.Application;
import android.support.multidex.MultiDexApplication;

import com.estimote.coresdk.common.config.EstimoteSDK;
import com.estimote.coresdk.service.BeaconManager;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.List;

import vn.edu.tdmu.imuseum.estimote.BeaconID;
import vn.edu.tdmu.imuseum.estimote.BeaconNotificationManager;

//
// Running into any issues? Drop us an email to: contact@estimote.com
//

public class MyApplication extends MultiDexApplication {

    private boolean beaconNotificationsEnabled = false;
    private static final String DEFAULT_UUID = "B9407F30-F5F8-466E-AFF9-25556B57FE6D";
    private List<BeaconID> beaconIDList = new ArrayList<>();
    private static MyApplication instance;
    private BeaconManager beaconManager;
    private Gson mGson;

    public static MyApplication self(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        beaconIDList.add(new BeaconID(DEFAULT_UUID,27599,45197));
        EstimoteSDK.initialize(getApplicationContext(), "nguyenlinh-it-1095-gmail-c-62e", "8411e5b5e4e13eb950a90ff581afebf9");
        mGson = new Gson();

    }

    public static MyApplication getInstance(){
        return instance;
    }

    public void enableBeaconNotifications(){
        if (beaconNotificationsEnabled){
            return;
        }

        BeaconNotificationManager beaconNotificationManager = new BeaconNotificationManager(this);
        for (BeaconID beaconID : beaconIDList){
            beaconNotificationManager.addNotification(beaconID,"Enter","Exit");
        }
        beaconNotificationManager.startMonitoring();
        beaconNotificationsEnabled = true;
    }

    public Boolean isBeaconNotificationsEnabled(){
        return beaconNotificationsEnabled;
    }

    public BeaconManager getBeaconManager() {
        return new BeaconManager(this);
    }

    public Gson getGson(){
        return this.mGson;
    }
}
