package com.digitcreativestudio.moviecataloguelocalstorage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "movie_db";
    private Context context;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(Movie.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Movie.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertMovie(int id,
                               String title,
                               String description,
                               String rate,
                               String release,
                               String poster,
                               String type) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Movie.COLUMN_ID, id);
        values.put(Movie.COLUMN_TITLE, title);
        values.put(Movie.COLUMN_DESCRIPTION, description);
        values.put(Movie.COLUMN_RATE, rate);
        values.put(Movie.COLUMN_RELEASE, release);
        values.put(Movie.COLUMN_POSTER, poster);
        values.put(Movie.COLUMN_TYPE, type);

        // insert row
        long id_new = db.insert(Movie.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id_new;
    }

    public void deleteMovie(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Movie.TABLE_NAME, Movie.COLUMN_ID + " = ?",
                new String[]{String.valueOf(movie.getId())});
        db.close();
    }

    public Movie getMovie(int id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Movie.TABLE_NAME,
                new String[]{Movie.COLUMN_ID,
                        Movie.COLUMN_TITLE,
                        Movie.COLUMN_DESCRIPTION,
                        Movie.COLUMN_RATE,
                        Movie.COLUMN_RELEASE,
                        Movie.COLUMN_POSTER,
                        Movie.COLUMN_TYPE},
                Movie.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Movie movie = new Movie();
        movie.setId(cursor.getInt(cursor.getColumnIndex(Movie.COLUMN_ID)));
        movie.setTitle(cursor.getString(cursor.getColumnIndex(Movie.COLUMN_TITLE)));
        movie.setDescription(cursor.getString(cursor.getColumnIndex(Movie.COLUMN_DESCRIPTION)));
        movie.setGenre(cursor.getString(cursor.getColumnIndex(Movie.COLUMN_RATE)));
        movie.setRelease(cursor.getString(cursor.getColumnIndex(Movie.COLUMN_RELEASE)));
        movie.setPoster(cursor.getString(cursor.getColumnIndex(Movie.COLUMN_POSTER)));
        movie.setType(cursor.getString(cursor.getColumnIndex(Movie.COLUMN_TYPE)));

        // close the db connection
        cursor.close();

        return movie;
    }

    public ArrayList<Movie> getAllNotes(String type) {
        ArrayList<Movie> movies = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + Movie.TABLE_NAME +
                             " WHERE "+ Movie.COLUMN_TYPE + " = ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{type});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Movie movie = new Movie();
                movie.setId(cursor.getInt(cursor.getColumnIndex(Movie.COLUMN_ID)));
                movie.setTitle(cursor.getString(cursor.getColumnIndex(Movie.COLUMN_TITLE)));
                movie.setDescription(cursor.getString(cursor.getColumnIndex(Movie.COLUMN_DESCRIPTION)));
                movie.setGenre(cursor.getString(cursor.getColumnIndex(Movie.COLUMN_RATE)));
                movie.setRelease(cursor.getString(cursor.getColumnIndex(Movie.COLUMN_RELEASE)));
                movie.setPoster(cursor.getString(cursor.getColumnIndex(Movie.COLUMN_POSTER)));
                movie.setType(cursor.getString(cursor.getColumnIndex(Movie.COLUMN_TYPE)));

                movies.add(movie);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return movies;
    }

    public boolean existCheck(int id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Movie.TABLE_NAME,
                new String[]{Movie.COLUMN_ID,
                        Movie.COLUMN_TITLE,
                        Movie.COLUMN_DESCRIPTION,
                        Movie.COLUMN_RATE,
                        Movie.COLUMN_RELEASE,
                        Movie.COLUMN_POSTER,
                        Movie.COLUMN_TYPE},
                Movie.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        boolean result = false;

        try {
            Movie data = new Movie();
            data.setId(cursor.getInt(cursor.getColumnIndex(Movie.COLUMN_ID)));

            if(data.getId()==id){
                result = true;
                Log.e("ADA", "FAVORITE");
            }
        }catch (CursorIndexOutOfBoundsException e){
            e.printStackTrace();
            Log.e("TIDAK ADA", e.toString());
        }

        cursor.close();
        return result;
    }
}