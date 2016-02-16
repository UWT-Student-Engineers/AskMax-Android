package edu.uw.askmax;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Main screen of the app. It will display and control a layout that includes the map
 * WebView, the search bar, and will pop up details fragments when requested by the map or search.
 */
public class MainActivity extends AppCompatActivity {

    @Bind(R.id.main_map) WebView mapView;
    @Bind(R.id.main_search) SearchView searchView;

    private Map<String, locationData> roomData = new HashMap<>();

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Set the mapView to load html map
        mapView.loadUrl("file:///android_asset/common/html/map.html");
        mapView.getSettings().setJavaScriptEnabled(true);
        mapView.addJavascriptInterface(new MapJsInterface(this), "AskMaxAndroid");

        mapView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // Page done loading
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Search submit tapped
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String targetName = newText.toUpperCase();

                if (roomData.containsKey(targetName)) {
                    locationData targetLocation = roomData.get(targetName);

                    //call the method centerCamera(targetName, targetLat, targetLng, zoomLevel)
                    // in map.js
                    StringBuilder builder = new StringBuilder("javascript:centerCamera(\"");
                    builder.append(targetName);
                    builder.append("\",\"");
                    builder.append(targetLocation.getLat());
                    builder.append("\",\"");
                    builder.append(targetLocation.getLng());
                    builder.append("\",\"");

                    builder.append(targetLocation.getZoomLevel());
                    builder.append("\")");
                    mapView.loadUrl(builder.toString());
                }

                return true;
            }
        });
    }

    // this class is just 1/2 of the map named "roomData"

    //build the map from a file, table, or from a database.
    // will change and have the function to read json file later


    private void initializeDatabase(){
        roomData.put("BB", new locationData(47.24573265, -122.4372974, 18));
        roomData.put("TOY", new locationData(47.24636993, -122.43736044, 18));

        roomData.put("BB107", new locationData(47.24575723, -122.43739732, 20));
        roomData.put("TOY109", new locationData(47.24636993, -122.43736044, 20));
    }


    /*
    private class that store the location data (LatLng, zoom level and maybe picture later)
    // we can put picture, building name etc inside
     */
    private class locationData {
        double myLat;
        double myLng;
        int zoom;
        protected locationData(double lat, double lng, int theZoomLevel) {
            myLat = lat;
            myLng = lng;
            zoom = theZoomLevel;
        }
        protected double getLat() {
            return myLat;
        }

        protected double getLng() {
            return myLng;
        }
        protected int getZoomLevel() {
            return zoom;
        }
    }
}
