package edu.uw.askmax;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Methods that we expose to JavaScript.
 */
public class MapJsInterface {

    private Context context;

    public MapJsInterface(Context context) {
        this.context = context;
    }

    @JavascriptInterface
    public void alert(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void displayInfo(String json) {
        Intent intent = new Intent(context, DetailsActivity.class);
        Log.d("JS", json);
        intent.putExtra(DetailsActivity.EXTRA_LOCATION, json);
        context.startActivity(intent);
    }
}
