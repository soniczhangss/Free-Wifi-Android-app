package com.netbayinternet.freewifi.freewifi;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.SystemClock;

public class WifiSignalStrengthIntentService extends IntentService {
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_DETECTWIFISIGNALSTRENGTH = "com.netbayinternet.freewifi.freewifi.action.ACTION_DETECTWIFISIGNALSTRENGTH";

    public WifiSignalStrengthIntentService() {
        super("WifiSignalStrengthIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_DETECTWIFISIGNALSTRENGTH.equals(action)) {
                WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                handleActionDetectWifiSignalStrength(wifiManager);
            }
        }
    }

    private void handleActionDetectWifiSignalStrength(WifiManager wifiManager) {
        while (true) {
            wifiManager.startScan();
            SystemClock.sleep(10000);
        }
    }

}
