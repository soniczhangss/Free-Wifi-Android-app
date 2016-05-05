package au.com.netbay.metrofreewifi;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
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
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;

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

public class LoginActivity extends Activity {

    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView bgImage = (ImageView) findViewById(R.id.imageView);
        bgImage.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        final ImageView freewifiImage = (ImageView) findViewById(R.id.freewifi);
        freewifiImage.getLayoutParams().width = size.x * 3 / 5;

        final ImageButton loginbtn = (ImageButton) findViewById(R.id.login_btn);
        loginbtn.getLayoutParams().width = size.x * 3 / 5;

        final ImageView metrologoImage = (ImageView) findViewById(R.id.metrologo);
        metrologoImage.getLayoutParams().width = size.x * 2 / 5;

        /*String checkBoxText = Constants.TERMS_CONDITIONS;
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox.append(Html.fromHtml(checkBoxText));
        checkBox.setMovementMethod(LinkMovementMethod.getInstance());

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ImageButton button = (ImageButton) findViewById(R.id.login_btn);
                if (isChecked) {
                    button.setEnabled(true);
                } else {
                    button.setEnabled(false);
                }
            }
        });*/

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
