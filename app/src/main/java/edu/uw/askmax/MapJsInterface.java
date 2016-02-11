package edu.uw.askmax;

import android.content.Context;
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
}
