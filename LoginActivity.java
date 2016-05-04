package au.com.netbay.metrofreewifi;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String checkBoxText = Constants.TERMS_CONDITIONS;
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox.append(Html.fromHtml(checkBoxText));
        checkBox.setMovementMethod(LinkMovementMethod.getInstance());

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Button button = (Button) findViewById(R.id.login_btn);
                if (isChecked) {
                    button.setEnabled(true);
                    button.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.colorPrimary));
                } else {
                    button.setEnabled(false);
                    button.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.colorGray));
                }
            }
        });

        prefs = getSharedPreferences("au.com.netbay.metrofreewifi", MODE_PRIVATE);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // First time startup.
        if (prefs.getBoolean("au.com.netbay.metrofreewifi.GeofenceIntentService", true)) {
            Log.i("Login", "Intent fired");
            Intent intent = new Intent(this, GoogleClientAPIService.class);
            startService(intent);

            prefs.edit().putBoolean("au.com.netbay.metrofreewifi.GeofenceIntentService", false).commit();
        }
    }

    public void loginBtnHandler(View view) {
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        String macAddress = wInfo.getMacAddress();

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.TARGET_URL + macAddress));
        startActivity(browserIntent);
    }
}
