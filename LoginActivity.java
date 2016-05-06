package au.com.netbay.metrofreewifi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class LoginActivity extends Activity {

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
    }

    public void loginBtnHandler(View view) {
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo wInfo = wifiManager.getConnectionInfo();
        String macAddress = wInfo.getMacAddress();

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.TARGET_URL + macAddress));
        startActivity(browserIntent);
    }
}
