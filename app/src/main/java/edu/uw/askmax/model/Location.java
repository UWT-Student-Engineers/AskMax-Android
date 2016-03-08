package edu.uw.askmax.model;

import com.google.gson.Gson;

import java.util.List;

/**
 * Location model. Currently locations can only be classrooms.
 */
public class Location {

    private static Gson gson;

    public static Location fromJson(String json) {
        //added on the fly
        initGson();

        return gson.fromJson(json, Location.class);
    }

    private String id;
    private String title;
    private String description;
    private List<String> tags;
    private List<Coordinate> coordinates;

    public String getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public List<String> getTags() {
        return tags;
    }
    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    public String toJson() {
        initGson();
        return gson.toJson(this);
    }

    private static void initGson() {
        if (gson == null) {
            gson = new Gson();
        }
    }

    @Override
    public String toString() {
        return "Location{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", tags=" + tags +
                ", coordinates=" + coordinates +
                '}';
    }
}
