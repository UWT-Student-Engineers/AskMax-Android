package edu.uw.askmax;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.uw.askmax.model.Location;

/**
 * Database implementation backed by JSON files.
 */
public class DatabaseJson implements DatabaseInterface {

    private static final String ROOT_DIR = "jsondb";

    private AssetManager am;

    private List<String> buildings;
    private List<String> rooms;

    private List<Location> locations;

    public DatabaseJson(Context context) {
        am = context.getAssets();

        try {
            setup();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setup() throws IOException {
        buildings = new ArrayList<>(Arrays.asList(am.list(ROOT_DIR)));
    }

    @Override
    public List<Location> search(String query) {
        return null; // Not implemented
    }

    @Override
    public Location get(String building, String room) {
        String path = ROOT_DIR + "/" + building + "/" + room + ".json";
        
        Gson gson = new Gson();
        try {
            return gson.fromJson(new InputStreamReader(am.open(path)), Location.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Location add(Location location) {
        return null; // Not implemented
    }
}
