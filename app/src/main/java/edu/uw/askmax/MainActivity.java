package edu.uw.askmax;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Main screen of the app. It will display and control a layout that includes the map
 * WebView, the search bar, and will pop up details fragments when requested by the map or search.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Map behind everything, implemented in HTML/JavaScript, shown using a WebView.
     */
    @Bind(R.id.main_map) WebView mapView;

    @Bind(R.id.main_search) SearchView searchView;

    /**
     * Entrance to the main screen. This is called when the activity is created by Android and we
     * can tell it what layout to display and we can bind views to fields.
     *
     * The activity may have been shut down by the system, and if we saved any data in an
     * overriden onSaveInstanceState() method, it will be available in the state param.
     *
     * @param state saved state from before activity was destroyed
     */
    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);

        // We tell Android to display the activity_main layout
        setContentView(R.layout.activity_main);

        // Bind views through ButterKnife
        ButterKnife.bind(this);

        // Set the mapView to load html map
        mapView.loadUrl("file:///android_asset/common/html/map.html");
        mapView.getSettings().setJavaScriptEnabled(true);
        mapView.addJavascriptInterface(new MapJsInterface(this), "AskMaxAndroid");

        mapView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    mapView.evaluateJavascript("alertMax()", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {
                            Log.d("AskMax", "JS Result: " + value);
                        }
                    });
                }
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    mapView.evaluateJavascript("alertMax(\"" + query + "\")", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {
                            Log.d("AskMax", "JS Result: " + value);
                        }
                    });
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        Model model = new Model();
        model.setData("test data");
        Gson gson = new Gson();
        String json = gson.toJson(model);
        Log.d("AskMax", json);
    }
}
