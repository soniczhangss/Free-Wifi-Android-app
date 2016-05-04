package au.com.netbay.metrofreewifi;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

/**
 * Created by sonic on 25/01/2016.
 */
public class Constants {

    public static final String TERMS_CONDITIONS = "<a href='http://hotspot.netbaywifi.com.au/pagetc.php'>Terms and Conditions</a>.";
    public static final String SSID = "AP-WL500GP";
    public static final String TARGET_URL = "http://hotspot.netbaywifi.com.au/timeselect.php?fss&mac=";
    public static final float GEOFENCE_RADIUS_IN_METERS = 170;

    public static final HashMap<String, LatLng> TRAIN_STATIONS = new HashMap<String, LatLng>();
    static {
        // Flinders Street Station.
        TRAIN_STATIONS.put("FSS", new LatLng(-37.818302, 145.125499));
    }

    private Constants() {}
}
