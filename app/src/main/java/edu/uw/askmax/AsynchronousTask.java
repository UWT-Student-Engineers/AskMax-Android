package edu.uw.askmax;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by Brandon on 2/16/2016.
 * <p/>
 * We need this to improve performance while doing db ops.
 * This is the stub, but we will instantiate this class in the
 * 'DatabaseTable.java' class, likely under the 'addData' method.
 * <p/>
 * Here is the idea for the instantiation:
 * ***************************************************************************************
 * AsynchronousTask async = new AsynchronousTask(this);
 * async.execute("add_data", id, building, room, floorplan, imageREF);
 * ***************************************************************************************
 */
public class AsynchronousTask extends AsyncTask<String, Void, Void> {

    Context context;

    AsynchronousTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     * This is the idea, see the eventual instantiation statements
     * in the header comment.
     *
     * @param params
     * @return
     */
    @Override
    protected Void doInBackground(String... params) {

        String method = params[0];
        if (method.equals("add_data")) {
            String id = params[1];
            String building = params[2];
            String room = params[3];
            String floorPlan = params[4];
            String imageRef = params[5];
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }


}