package edu.uw.askmax;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.uw.askmax.model.Location;

/**
 * Database implementation backed by JSON files.
 */
public class DatabaseJson implements DatabaseInterface {

    private static final String ROOT_DIR = "common/jsondb";

    private AssetManager am;

    private List<String> buildings;
    private List<String> rooms;

    private List<Location> locations;

    private Map<String, Set<Location>> tagMap;

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

        // Load all tags
        tagMap = new HashMap<>();
        for (String building : buildings) {
            Location location = get(building);

            List<String> locationTags = location.getTags();
            for (String tag : locationTags) {
                Set<Location> list = tagMap.get(tag);
                if (list == null) {
                    list = new HashSet<>();
                }
                list.add(location);
                tagMap.put(tag, list);
            }
        }
    }

    @Override
    public List<Location> search(String query) {
        if (query.isEmpty()) {
            return new ArrayList<>(1);
        }

        Set<Location> results = new HashSet<>();

        for (String part: query.split(" ")) {
            // Very basic and rough
            for (String tag : tagMap.keySet()) {
                if (tag.startsWith(part)) {
                    results.addAll(tagMap.get(tag));
                }
            }
        }

        return new ArrayList<>(results);
    }

    @Override
    public Location get(String building) {
        return get(building, null);
    }

    @Override
    public Location get(String building, String room) {
        if (room == null) {
            room = "index";
        }

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
