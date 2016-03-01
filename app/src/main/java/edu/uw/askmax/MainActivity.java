package edu.uw.askmax;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import com.google.gson.Gson;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.uw.askmax.model.Location;
import timber.log.Timber;

/**
 * Main screen of the app. It will display and control a layout that includes the map
 * WebView, the search bar, and will pop up details fragments when requested by the map or search.
 */
public class MainActivity extends AppCompatActivity {

    @Bind(R.id.main_map) WebView mapView;
    @Bind(R.id.main_search) EditText searchView;

    private DatabaseInterface database;

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        database = new DatabaseJson(this);

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

        searchView.addTextChangedListener(new TextChangedWatcher() {
            @Override
            public void onTextChanged(String s) {
                s = normalizeSearch(s);

                // centerCamera() takes a list of locations
                List<Location> list = database.search(s);

                // Convert to a json string
                Gson gson = new Gson();
                String json = gson.toJson(list);

                String js = "centerCamera(" + json + ");";
                Timber.d("Javascript: %s", js);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    mapView.evaluateJavascript(js, new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {
                            Log.d("Max", value);
                        }
                    });
                }
            }
        });
    }

    private String normalizeSearch(String s) {
        return s.toLowerCase();
    }
}
