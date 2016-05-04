package au.com.netbay.metrofreewifi;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class Splash extends Activity {

    private ProgressBar spinner;
    private Context splashClassContext;
    private WifiManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        splashClassContext = this.getApplicationContext();

        setContentView(R.layout.splashh);

        spinner = (ProgressBar) findViewById(R.id.spinner);
        spinner.setVisibility(View.VISIBLE);

        final ImageView bgImage = (ImageView) findViewById(R.id.imageView);
        bgImage.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        final ImageView txtImage = (ImageView) findViewById(R.id.imageView3);
        Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.scale);
        txtImage.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.scale2);
                txtImage.startAnimation(animation);

                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        spinner.setVisibility(View.VISIBLE);

                        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
                        if (wifiManager.isWifiEnabled()) {
                            if (isReachable()) {
                                Intent intent = new Intent(splashClassContext, LoginActivity.class);
                                startActivity(intent);
                                finishAffinity();
                            } else
                                showAlert();
                        } else
                            showAlert();

                        spinner.setVisibility(View.GONE);

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private boolean isReachable() {
        boolean isReachable = false;

        String currentSSID = wifiManager.getConnectionInfo().getSSID();
        if (currentSSID.equals("\"" + Constants.SSID + "\""))
            isReachable = true;

        return isReachable;
    }

    private void showAlert() {
        AlertDialog alertDialog = new AlertDialog.Builder(Splash.this).create();
        alertDialog.setTitle("Oops");
        alertDialog.setMessage("Metro free wifi is not reachable. Please go to Flinders Street Station and try again.");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finishAffinity();
                    }
                });
        alertDialog.show();
    }
}