package edu.uw.askmax;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Main screen of the app. It will display and control a layout that includes the map
 * WebView, the search bar, and will pop up details fragments when requested by the map or search.
 */
public class MainActivity extends AppCompatActivity {

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
    }
}
