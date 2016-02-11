package edu.uw.askmax;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Main screen of the app. It will display and control a layout that includes the map
 * WebView, the search bar, and will pop up details fragments when requested by the map or search.
 */
public class MainActivity extends AppCompatActivity {

    @Bind(R.id.main_map) WebView mapView;
    @Bind(R.id.main_search) SearchView searchView;

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
                // Search text changed
                return true;
            }
        });
    }
}
