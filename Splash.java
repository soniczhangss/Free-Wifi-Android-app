package au.com.netbay.metrofreewifi;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class Splash extends Activity {

    private ProgressBar spinner;
    private Context splashClassContext;
    private WifiManager wifiManager;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        if (wifiManager.isWifiEnabled()) {
            if (isReachable()) {
                Intent intent = new Intent(splashClassContext, LoginActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        }
        showAlert();
    }

    private boolean isReachable() {
        boolean isReachable = false;

        String currentSSID = wifiManager.getConnectionInfo().getSSID();
        if (currentSSID.equals("\"" + Constants.SSID + "\""))
            isReachable = true;

        return isReachable;
    }

    private void showAlert() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.splash_alertdialog, null);
        dialogBuilder.setView(dialogView);
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