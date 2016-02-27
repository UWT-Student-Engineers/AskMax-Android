package edu.uw.askmax.model;

import java.util.List;

/**
 * Location model. Currently locations can only be classrooms.
 */
public class Location {

    private String building;
    private String room;
    private List<Coordinate> coordinates;

    public String getBuilding() {
        return building;
    }
    public String getRoom() {
        return room;
    }
    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    @Override
    public String toString() {
        return "Location{" +
                "building='" + building + '\'' +
                ", room='" + room + '\'' +
                ", coordinates=" + coordinates +
                '}';
    }
}
