package edu.uw.askmax;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

/**
 * Created by andre on 2/4/2016.
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
