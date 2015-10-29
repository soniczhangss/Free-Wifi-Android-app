package com.netbayinternet.freewifi.freewifi;

import android.app.ActivityManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {
    private static final String ACTION_DETECTWIFISIGNALSTRENGTH = "com.netbayinternet.freewifi.freewifi.action.ACTION_DETECTWIFISIGNALSTRENGTH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent mServiceIntent = new Intent(ACTION_DETECTWIFISIGNALSTRENGTH, null, this, WifiSignalStrengthIntentService.class);
        startService(mServiceIntent);
    }
}
