package vn.edu.tdmu.imuseum.views;


import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.estimote.coresdk.observation.region.Region;
import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import vn.edu.tdmu.imuseum.adapter.HistoricArtifactListAdapter;
import vn.edu.tdmu.imuseum.model.Artifact;
import vn.edu.tdmu.imuseum.ultils.Constant;
import vn.edu.tdmu.imuseum.ultils.RequestUtils;
import vn.edu.tdmu.imuseum.ultils.Utils;

import android.support.design.widget.NavigationView;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class NearYorActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private HistoricArtifactListAdapter adapter;
    private BeaconManager beaconManager;
    String major;
    private static final Region ALL_ESTIMOTE_BEACONS_REGION = new BeaconRegion(Constant.DEFAULT_BEACON_INDENTIFIER, UUID.fromString(Constant.DEFAULT_BEACON_UUID), null, null);
    public static final String LOG_TAG = NearYorActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_yor);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        recyclerView = findViewById(R.id.recycler_view);

        Bundle extras = getIntent().getExtras();
        major = extras.getString("beaconID");
        List<Artifact> artifactArrayList = new ArrayList<>();
        try {
            artifactArrayList = RequestUtils.getInstance().doGetArtifactInfoByBeaconId(String.valueOf(major));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

            this.layoutManager = new LinearLayoutManager(getApplicationContext());

        this.recyclerView.setLayoutManager(this.layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.adapter = new HistoricArtifactListAdapter(this, artifactArrayList);
        //}
        this.recyclerView.setAdapter(this.adapter);
        this.adapter.notifyDataSetChanged();


    }


    private void bindingScanningBeaconStatus() {
        if (this.beaconManager != null) {
            this.beaconManager.setScanStatusListener(new BeaconManager.ScanStatusListener() {
                public void onScanStart() {
                }

                public void onScanStop() {
                }
            });
        }
    }

    private void bindingBeaconManager() {
        this.beaconManager = MyApplication.getInstance().getBeaconManager();
        bindingRangingBeacon();
        bindingScanningBeaconStatus();
    }

    private void bindingRangingBeacon() {
        this.beaconManager.setRangingListener(new BeaconManager.BeaconRangingListener() {
            @Override
            public void onBeaconsDiscovered(BeaconRegion beaconRegion, List<Beacon> beacons) {
                final List<Beacon> validBeacon = new ArrayList<>();
                for (Beacon beacon : beacons) {
                    validBeacon.add(beacon);
                }

                if (NearYorActivity.this.isDestroyed()) {
                    Utils.showWarningLog(NearYorActivity.LOG_TAG, "[getActivity bindingRangingBeacon NearYouFragment] is null");
                } else {
                    NearYorActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            NearYorActivity.this.adapter.replaceWith(validBeacon);
                        }
                    });
                }
            }
        });
    }


    public void onDestroy() {
        super.onDestroy();
        if (this.beaconManager != null) {
            this.beaconManager.disconnect();
        }

    }

    public void onResume() {
        super.onResume();
        bindingBeaconManager();
        if (SystemRequirementsChecker.checkWithDefaultDialogs(this)) {
            startScanning();
            return;
        }
    }

    public void onStop() {
        if (this.beaconManager != null) {
            this.beaconManager.stopRanging((BeaconRegion) ALL_ESTIMOTE_BEACONS_REGION);
        }
        super.onStop();
    }

    private void startScanning() {
        if (this.adapter != null) {
            this.adapter.replaceWith(Collections.<Beacon>emptyList());
            if (this.beaconManager != null) {
                this.beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
                    public void onServiceReady() {
                        NearYorActivity.this.beaconManager.startRanging((BeaconRegion) NearYorActivity.ALL_ESTIMOTE_BEACONS_REGION);
                    }
                });
            }
        }
    }


}
