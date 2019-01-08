package vn.edu.tdmu.imuseum.views;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.estimote.coresdk.observation.region.Region;
import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.repackaged.okhttp_v2_2_0.com.squareup.okhttp.internal.Util;
import com.estimote.coresdk.service.BeaconManager;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import vn.edu.tdmu.imuseum.adapter.BeaconAdapter;
import vn.edu.tdmu.imuseum.listener.TaskCompleted;
import vn.edu.tdmu.imuseum.model.Artifact;
import vn.edu.tdmu.imuseum.model.Language;
import vn.edu.tdmu.imuseum.ultils.Constant;
import vn.edu.tdmu.imuseum.ultils.LanguageUtils;
import vn.edu.tdmu.imuseum.ultils.Server;
import vn.edu.tdmu.imuseum.ultils.SharePrefs;
import vn.edu.tdmu.imuseum.ultils.Utils;
import vn.edu.tdmu.imuseum.views.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity{

    private final static String TAG = "MainActivity";
    private List<Artifact> artifacts;
    public static final String SERVER_URL = Server.ARTIFACT;
    private BeaconManager beaconManager;

    private ActivityMainBinding mBinding;

    public static final String DEFAULT_BEACON_UUID = "B9407F30-F5F8-466E-AFF9-25556B57FE6D";
    public static final String DEFAULT_BEACON_INENTIFIER = "rid";
    private static final Region ALL_ESTIMOTE_BEACONS_REGION = new BeaconRegion(DEFAULT_BEACON_INENTIFIER, UUID.fromString(DEFAULT_BEACON_UUID),null, null);
    private BeaconAdapter beaconAdapter;
    private ListView lvDevice;

    int[] imageSliders = {R.drawable.hinhanhslidermot, R.drawable.hinhanhsliderhai, R.drawable.hinhanhsliderba, R.drawable.hinhanhsliderbon};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        LanguageUtils.loadLocale();
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.setMain(MainActivity.this);

        Button btnBeacon = findViewById(R.id.btnBeacon);
        Button btnArtifact = findViewById(R.id.btnArtifact);
        Button btnMap = findViewById(R.id.btnMap);
        CarouselView carouselview = findViewById(R.id.carouselview);
        Button btnAbout = findViewById(R.id.btnAbout);
        btnBeacon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, WelcomActivity.class);
                startActivity(i);
            }
        });

        btnArtifact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ArtifactSaveActivity.class);
                startActivity(i);
            }
        });

        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, MapActivity.class);
                startActivity(i);
            }
        });
        carouselview.setPageCount(imageSliders.length);
        carouselview.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                Picasso.with(MainActivity.this).load(imageSliders[position])
                        .placeholder(R.drawable.ic_home)
                        .error(R.drawable.no_image)
                        .rotate(0)
                        .into(imageView);
            }
        });

        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, InfoMuseumActivity.class);
                startActivity(i);
            }
        });


    }


    public void openLanguageScreen() {
        Intent intent = new Intent(MainActivity.this, LanguageSetting.class);
        startActivityForResult(intent, Constant.RequestCode.CHANGE_LANGUAGE);
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
        lvDevice.setAdapter(beaconAdapter);
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
            //beaconManager.disconnect();
        }
    }

    private void handleArtifactList(List<Artifact> artifacts) {
        this.artifacts = artifacts;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (Artifact artifact : MainActivity.this.artifacts) {
                    Toast.makeText(MainActivity.this, artifact.getMedias().get(0).getMedia()+ "", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void failLoadingPosts() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "Failed to load Artifacts. Have a look at LogCat.", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constant.RequestCode.CHANGE_LANGUAGE:
                if (resultCode == RESULT_OK) {
                    updateViewByLanguage();
                }
                break;
        }
    }

    private void updateViewByLanguage() {
        mBinding.btnBeacon.setText(getString(R.string.hi_n_v_t));
        mBinding.btnMap.setText(getString(R.string.map));
        mBinding.btnArtifact.setText(getString(R.string.favotite));
        mBinding.btnSetting.setText(getString(R.string.setting));
        mBinding.btnAbout.setText(getString(R.string.about));

    }



    private class Task extends AsyncTask<Void, Void,String> {
        private TaskCompleted callback;

        private Task(TaskCompleted callback) {
            this.callback = callback;
        }


        @Override
        protected String doInBackground(Void... voids) {
            return Utils.translate("Hello World", "en", "vi");
        }

        @Override
        protected void onPostExecute(String s) {
            callback.onTaskComplete(s);
        }
    }
}
