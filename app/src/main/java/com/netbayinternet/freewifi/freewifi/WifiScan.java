package com.netbayinternet.freewifi.freewifi;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v4.app.NotificationCompat;

import java.util.Iterator;
import java.util.List;

public class WifiScan extends BroadcastReceiver {
    private String ssid = "AP-WL500GP";
    private String bssid = "4C:0B:BE:02:F4:98";

    public WifiScan() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> scanResults = wifiManager.getScanResults();
        Iterator<ScanResult> iterator = scanResults.iterator();
        showNotification(context);
        while (iterator.hasNext()) {
            ScanResult currentAP = iterator.next();
            if (currentAP.SSID.equals(ssid) && currentAP.BSSID.equals(bssid)) {
                //showNotification(context);
            }
        }
    }

    private void showNotification(Context context) {
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, LoginActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_wifi_white)
                        .setContentTitle("Netbay Free Wifi")
                        .setContentText("Free Wifi is available. Tap to connect.");
        mBuilder.setContentIntent(contentIntent);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }
}
