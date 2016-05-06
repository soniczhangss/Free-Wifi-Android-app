package au.com.netbay.metrofreewifi;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Splash extends Activity {

    private ProgressBar spinner;
    private Context splashClassContext;
    private WifiManager wifiManager;
    private SharedPreferences prefs = null;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getSharedPreferences("au.com.netbay.metrofreewifi", MODE_PRIVATE);

        splashClassContext = this.getApplicationContext();

        setContentView(R.layout.splashh);

        spinner = (ProgressBar) findViewById(R.id.spinner);
        spinner.setVisibility(View.VISIBLE);

        final ImageView bgImage = (ImageView) findViewById(R.id.imageView);
        bgImage.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        final ImageView wifiImage = (ImageView) findViewById(R.id.imageView3);
        wifiImage.getLayoutParams().width = size.x / 2;

        final ImageView metrologoImage = (ImageView) findViewById(R.id.metrologo);
        metrologoImage.getLayoutParams().width = size.x * 2 / 5;

        TextView appactionText = (TextView) findViewById(R.id.app_action);
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        appactionText.setText("Checking Wifi Status...");
        if (wifiManager.isWifiEnabled()) {
            if (isReachable()) {
                if (!isInternetAvailable()) {
                    Intent intent = new Intent(splashClassContext, LoginActivity.class);
                    startActivity(intent);
                    finishAffinity();
                } else {
                    showAlert("You still have the Internet access. You do not need to login again.");
                }
            } else {
                showAlert("Metro Free Wifi is not available.");
            }
        } else {
            showAlert("Your Wifi is turned off.");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (prefs.getBoolean("au.com.netbay.metrofreewifi.GeofenceIntentService", true)) {
            Log.i("Login", "Intent fired");
            Intent intent = new Intent(this, GoogleClientAPIService.class);
            startService(intent);

            prefs.edit().putBoolean("au.com.netbay.metrofreewifi.GeofenceIntentService", false).commit();
        }
    }

    private boolean isReachable() {
        boolean isReachable = false;

        String currentSSID = wifiManager.getConnectionInfo().getSSID();
        if (currentSSID.equals("\"" + Constants.SSID + "\""))
            isReachable = true;

        return isReachable;
    }

    public boolean isInternetAvailable() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (Exception e) {
            Log.e("Splash Screen", "Error checking internet connection", e);
        }
        return false;
    }

    private void showAlert(String info) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.splash_alertdialog, null);
        dialogBuilder.setView(dialogView);
        final TextView alertText = (TextView) dialogView.findViewById(R.id.alert_content);
        alertText.setText(info);
        final AlertDialog alertDialog = dialogBuilder.create();
        final Button btn = (Button) dialogView.findViewById(R.id.ok);
        btn.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                finishAffinity();
            }
        });
        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                GradientDrawable backgroundShape = (GradientDrawable)btn.getBackground();
                switch ( event.getAction() ) {
                    case MotionEvent.ACTION_DOWN:
                        backgroundShape.setColor(Color.WHITE);
                        btn.setTextColor(ContextCompat.getColor(splashClassContext, R.color.splash_dialog_background));
                        break;
                    case MotionEvent.ACTION_UP:
                        backgroundShape.setColor(ContextCompat.getColor(splashClassContext, R.color.splash_dialog_background));
                        btn.setTextColor(Color.WHITE);
                        break;
                }

                return false;
            }
        });
        alertDialog.show();
    }
}