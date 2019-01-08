package vn.edu.tdmu.imuseum.estimote;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.util.Log;


import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import vn.edu.tdmu.imuseum.views.MainActivity;

/**
 * Created by nvulinh on 9/1/17.
 *
 */

public class BeaconNotificationManager {

    private static final String TAG = "BeaconNotificationManager";

    private BeaconManager beaconManager;

    private List<BeaconRegion> regionsToMonitor = new ArrayList<>();
    private HashMap<String,String> enterMessages = new HashMap<>();
    private HashMap<String,String> exitMessages = new HashMap<>();

    private Context context;

    private int notificationID = 0;

    public BeaconNotificationManager(Context context){
        this.context = context;
        beaconManager = new BeaconManager(context);
        beaconManager.setMonitoringListener(new BeaconManager.BeaconMonitoringListener() {
            @Override
            public void onEnteredRegion(BeaconRegion beaconRegion, List<Beacon> beacons) {
                Log.d(TAG,"onEnteredRegion: " + beaconRegion.getIdentifier());
                String message = enterMessages.get(beaconRegion.getIdentifier());
                if (message != null){
                    showNofication(message);
                }
            }

            @Override
            public void onExitedRegion(BeaconRegion beaconRegion) {
                Log.d(TAG,"onExitedRegion: " + beaconRegion.getIdentifier());
                String message = exitMessages.get(beaconRegion.getIdentifier());
                if (message != null){
                    showNofication(message);
                }
            }
        });
    }

    public void addNotification(BeaconID beaconID, String enterMessage, String exitMessage) {
        BeaconRegion region = beaconID.toBeaconRegion();
        enterMessages.put(region.getIdentifier(), enterMessage);
        exitMessages.put(region.getIdentifier(), exitMessage);
        regionsToMonitor.add(region);
    }

    public void startMonitoring() {
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                for (BeaconRegion region : regionsToMonitor) {
                    beaconManager.startMonitoring(region);
                }
            }
        });
    }

    private void showNofication(String message) {
        Intent resultIntent = new Intent(context, MainActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(
                context,0,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Beacon Notifications")
                .setContentText(message)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(resultPendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID++,builder.build());
    }
}
