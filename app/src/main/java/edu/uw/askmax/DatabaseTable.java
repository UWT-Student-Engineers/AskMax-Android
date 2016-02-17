package edu.uw.askmax;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Brandon on 2/16/2016.
 *
 * This is from the Android tutorial, with some intermediary changes in progress: original 
 * db is for a 'dictionary' with word/definition key/value pairs.  This obviously needs
 * to be refactored, some of which I've already done.
 * 
 * "There are many ways to store your data, such as in an online database, in a local SQLite
 * database, or even in a text file. It is up to you to decide what is the best solution for
 * your application. This lesson shows you how to create a SQLite virtual table that can provide
 * robust full-text searching. The table is populated with data from a text file that contains a
 * word and definition pair on each line in the file.
 *
 * A virtual table behaves similarly to a SQLite table, but reads and writes to an object in
 * memory via callbacks, instead of to a database file. To create a virtual table, create a
 * class for the table: "
 *
 * --from developer.android.com/training/search/search.htm
 */
public class DatabaseTable {

    private static final String TAG = "UWT_LocationsDatabase";

    //COLUMNS (not final!!!)
    public static final String COL_ROOM = "ROOM";//i.e. "107" or "220" etc.
    public static final String COL_BUILDING = "BUILDING";//i.e. "BB" or "BHS" etc.

    private static final String DATABASE_NAME = "LOCATIONS";
    private static final String FTS_VIRTUAL_TABLE = "FTS";
    private static final int DATABASE_VERSION = 1;


    private final DatabaseOpenHelper mDatabaseOpenHelper;

    public DatabaseTable(Context context) {
        mDatabaseOpenHelper = new DatabaseOpenHelper(context);
    }

    /**
     * "When you have the virtual table created and populated, use the query supplied by your
     * SearchView to search the data. Add the following methods to the DatabaseTable class to
     * build a SQL statement that searches for the query:"
     * --from developer.android.com/training/search/search.htm
     */
    public Cursor getWordMatches(String query, String[] columns) {
        String selection = COL_ROOM + " MATCH ?";
        String[] selectionArgs = new String[] {query + "*"};

        return query(selection, selectionArgs, columns);
    }

    /*
    Used in conjuction with method 'getWordMatches' above
     */
    private Cursor query(String selection, String[] selectionArgs, String[] columns) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(FTS_VIRTUAL_TABLE);

        Cursor cursor = builder.query(mDatabaseOpenHelper.getReadableDatabase(),
                columns, selection, selectionArgs, null, null, null);

        if(cursor == null) {
            return null;

        }else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }


    /**
     * "Create an inner class in DatabaseTable that extends SQLiteOpenHelper. The SQLiteOpenHelper
     * class defines abstract methods that you must override so that your database table can be
     * created and upgraded when necessary. For example, here is some code that declares a database
     * table that will contain words for a dictionary app:"
     * --from developer.android.com/training/search/search.htm
     */
    private static class DatabaseOpenHelper extends SQLiteOpenHelper {

        private final Context mHelperContext;
        private SQLiteDatabase mDatabase;

        private static final String FTS_TABLE_CREATE =
                "CREATE VIRTUAL TABLE " + FTS_VIRTUAL_TABLE +
                        " USING fts3 (" +
                        COL_ROOM + ", " +
                        COL_BUILDING + ")";

        DatabaseOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            mHelperContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            mDatabase = db;
            mDatabase.execSQL(FTS_TABLE_CREATE); //creates the database

            loadLocations(); //populate the db table just created
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + FTS_VIRTUAL_TABLE);
            onCreate(db);
        }


        /**
         * Code for reading the text file located in res/raw/definitions.txt, that contains
         * words and their definitions, parses that file, and inserts each line of that file
         * as a row in the virtual table.
         *
         * Tip: You also might want to set up a callback to notify your UI activity of
         * this thread's completion.
         */
        private void loadLocations() {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        loadData();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();
        }

        //this parser needs adjustment for our usecase 
        private void loadData() throws IOException {
            final Resources resources = mHelperContext.getResources();
            InputStream inputStream = resources.openRawResource(R.raw.definitions);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            try {
                String line;
                while((line = reader.readLine()) != null) {
                    String[] strings = TextUtils.split(line, ",");//changed from '-' to ','
                    if(strings.length < 2) continue;
                    long id = addData(strings[0].trim(), strings[1].trim());
                    if(id < 0) {
                        Log.e(TAG, "unable to add word: " + strings[0].trim());
                    }
                }
            } finally {
                reader.close();
            }
        }
        //the parameter count may work (i.e. we may not need more than two), 
        //but this needs some quick refactoring: name of params, count of params, etc.
        public long addData(String word, String definition) {
            ContentValues initialValues = new ContentValues();
            initialValues.put(COL_ROOM, word);
            initialValues.put(COL_BUILDING, definition);

            return mDatabase.insert(FTS_VIRTUAL_TABLE, null, initialValues);
        }
    } //END OF INNER CLASS DatabaseOpenHelper
}
