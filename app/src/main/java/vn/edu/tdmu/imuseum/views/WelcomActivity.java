package vn.edu.tdmu.imuseum.views;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.estimote.coresdk.observation.region.Region;
import com.estimote.coresdk.observation.region.RegionUtils;
import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;

import java.util.Collections;
import java.util.List;
import java.util.UUID;


import vn.edu.tdmu.imuseum.adapter.BeaconAdapter;
import vn.edu.tdmu.imuseum.dialog.ProgressDialog;
import vn.edu.tdmu.imuseum.model.Language;
import vn.edu.tdmu.imuseum.ultils.Constant;
import vn.edu.tdmu.imuseum.ultils.SharePrefs;


import static java.lang.String.valueOf;

public class WelcomActivity extends AppCompatActivity {



    private static final String LOG_TAG = WelcomActivity.class.getSimpleName();
    private static final Region ALL_ESTIMOTE_BEACONS_REGION = new BeaconRegion(Constant.DEFAULT_BEACON_INDENTIFIER, UUID.fromString(Constant.DEFAULT_BEACON_UUID), null, null);
    private ImageView im_icon;
    private TextView tv_app_name;
    private ProgressDialog progressDialog;
    private BeaconManager beaconManager;
    private BeaconAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        initViews();

        im_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });


    }

    protected void initViews() {
        im_icon =findViewById(R.id.img_icon);
        tv_app_name = findViewById(R.id.tv_app_name_wel);
        this.progressDialog = new ProgressDialog(this, getString(R.string.please_wait));
    }

    private void bindingBeaconManager() {
        adapter = new BeaconAdapter(this);
        this.beaconManager = MyApplication.getInstance().getBeaconManager();
        bindingRangingBeacon();
        bindingScanningBeaconStatus();
    }

    private void bindingRangingBeacon() {
        this.beaconManager.setRangingListener(new BeaconManager.BeaconRangingListener() {
            @Override
            public void onBeaconsDiscovered(BeaconRegion beaconRegion, final List<Beacon> beacons) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.replaceWith(beacons);
                    }
                });//end Range

                if (!beacons.isEmpty()) {
                    Beacon beaconInRange = beacons.get(0);
                    double distances = RegionUtils.computeAccuracy(beaconInRange);
                    String proximitys = RegionUtils.proximityFromAccuracy(distances).toString();
                    final String major = valueOf(beaconInRange.getMajor());

                    Log.d("DISTANCE to iBEACON: ", proximitys);


                    if (proximitys.equals("NEAR")) {
//                                showNotification("Wellcom You Entered range iBeacon of a Museum TMA, Enjoy your visit with our app. The more you know, the more you enjoy",
//                                       String.valueOf(beaconInRange.getMajor()));
                    } else if (proximitys.equals("IMMEDIATE")) {
                        DialogWelcom(major);
                    }
                }
            }
        });
    }
    private void DialogWelcom(final String major){
        ProgressDialog.Builder diaBuilder = new ProgressDialog.Builder(WelcomActivity.this);
        diaBuilder.setMessage("Welcome You Entered range iBeacon");
        diaBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(WelcomActivity.this, NearYorActivity.class);
                intent.putExtra("beaconID", major);
                startActivity(intent);
            }
        });
        diaBuilder.show();
    }



    private void bindingScanningBeaconStatus() {
        if (this.beaconManager != null) {
            this.beaconManager.setScanStatusListener(new BeaconManager.ScanStatusListener() {
                public void onScanStart() {
                    //NearYouFragment.this.rvHistoricItems.setEnabled(true);
                   // NearYouFragment.this.rvHistoricItems.setAlpha(1.0f);
                }

                public void onScanStop() {
                  //  NearYouFragment.this.rvHistoricItems.setEnabled(false);
                   // NearYouFragment.this.rvHistoricItems.setAlpha(0.5f);
                }
            });
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.beaconManager != null) {
            this.beaconManager.disconnect();
        }

        if ( progressDialog!=null && progressDialog.isShowing() ){
            progressDialog.cancel();
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
                        WelcomActivity.this.beaconManager.startRanging((BeaconRegion) WelcomActivity.ALL_ESTIMOTE_BEACONS_REGION);
                    }
                });
            }
        }
    }
}
