package au.com.netbay.metrofreewifi;

import com.google.android.gms.maps.model.LatLng;
import java.util.HashMap;

public class Constants {

    public static final String SSID = "Metro Free Wifi";
    public static final String TARGET_URL = "http://hotspot.netbaywifi.com.au/timeselect.php?fss&mac=";
    public static final float GEOFENCE_RADIUS_IN_METERS = 170;

    public static final HashMap<String, LatLng> TRAIN_STATIONS = new HashMap<String, LatLng>();
    static {
        // Flinders Street Station.
        TRAIN_STATIONS.put("FSS", new LatLng(-37.818271, 144.967061));
    }

    private Constants() {}
}
