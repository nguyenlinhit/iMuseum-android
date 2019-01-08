package vn.edu.tdmu.imuseum.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.estimote.coresdk.observation.region.Region;
import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import vn.edu.tdmu.imuseum.adapter.BeaconAdapter;

import android.support.design.widget.NavigationView;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class BeaconActivity extends AppCompatActivity {

    private final static String TAG = "BeaconActivity";
    private BeaconManager beaconManager;
    private BeaconAdapter beaconAdapter;
    private ListView lvDiveceBea;

    public static final String DEFAULT_BEACON_UUID = "B9407F30-F5F8-466E-AFF9-25556B57FE6D";
    public static final String DEFAULT_BEACON_INENTIFIER = "rid";
    private static final Region ALL_ESTIMOTE_BEACONS_REGION = new BeaconRegion(DEFAULT_BEACON_INENTIFIER, UUID.fromString(DEFAULT_BEACON_UUID),null, null);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon);

        lvDiveceBea = (ListView) findViewById(R.id.lvDiveceBea);
        bindingBeaconManager();
    }

    private void bindingBeaconManager() {
        beaconManager = new BeaconManager(this);
        bindingListDevice();
        bindingRangingBeacon();
    }

    private void bindingRangingBeacon() {
        beaconManager.setRangingListener(new BeaconManager.BeaconRangingListener() {
            @Override
            public void onBeaconsDiscovered(BeaconRegion beaconRegion, final List<Beacon> beacons) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        beaconAdapter.replaceWith(beacons);
                    }
                });
            }
        });
    }

    private void bindingListDevice() {
        beaconAdapter = new BeaconAdapter(this);
        lvDiveceBea.setAdapter(beaconAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        MyApplication app = (MyApplication) getApplication();

        if (!SystemRequirementsChecker.checkWithDefaultDialogs(this)) {
            Log.e(TAG, "Can't scan for beacons, some pre-conditions were not met");
            Log.e(TAG, "Read more about what's required at: http://estimote.github.io/Android-SDK/JavaDocs/com/estimote/sdk/SystemRequirementsChecker.html");
            Log.e(TAG, "If this is fixable, you should see a popup on the app's screen right now, asking to enable what's necessary");
        } else if (!app.isBeaconNotificationsEnabled()) {
            Log.d(TAG, "Enabling beacon notifications");
            app.enableBeaconNotifications();
        }

        if (SystemRequirementsChecker.checkWithDefaultDialogs(this)){
            startScanding();
        }
    }

    private void startScanding() {
        if (beaconAdapter != null && beaconManager != null){
            beaconAdapter.replaceWith(Collections.<Beacon>emptyList());
            beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
                @Override
                public void onServiceReady() {
                    beaconManager.startRanging((BeaconRegion) ALL_ESTIMOTE_BEACONS_REGION);
                }
            });
        }
    }

    @Override
    protected void onStop() {
        if (beaconManager != null){
            beaconManager.stopRanging((BeaconRegion) ALL_ESTIMOTE_BEACONS_REGION);
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (beaconManager == null){
            assert false;
            beaconManager.disconnect();
        }
    }
}
