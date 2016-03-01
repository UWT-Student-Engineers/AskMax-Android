package edu.uw.askmax;

import java.util.List;

import edu.uw.askmax.model.Location;

/**
 * Methods common to any database system we use. Make sure to implement this in any database
 * implementation we try.
 */
public interface DatabaseInterface {

    List<Location> search(String query);
    Location get(String building);
    Location get(String building, String room);
    Location add(Location location);
}
