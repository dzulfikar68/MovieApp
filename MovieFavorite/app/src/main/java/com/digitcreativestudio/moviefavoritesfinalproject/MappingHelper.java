package com.digitcreativestudio.moviefavoritesfinalproject;

import android.database.Cursor;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.digitcreativestudio.moviefavoritesfinalproject.MovieContract.MovieColumns.*;

public class MappingHelper {
    public static ArrayList<Movie> mapCursorToArrayList(Cursor moviesCursor) {
        ArrayList<Movie> moviesList = new ArrayList<>();
        while (moviesCursor.moveToNext()) {
            int id = moviesCursor.getInt(moviesCursor.getColumnIndexOrThrow(_ID));
            int movie_id = moviesCursor.getInt(moviesCursor.getColumnIndexOrThrow(COLUMN_ID));
            String title = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(COLUMN_TITLE));
            String description = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
            String rate = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(COLUMN_RATE));
            String release = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(COLUMN_RELEASE));
            String poster = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(COLUMN_POSTER));
            String type = moviesCursor.getString(moviesCursor.getColumnIndexOrThrow(COLUMN_TYPE));
            moviesList.add(
                    new Movie(
                            id,
                            movie_id,
                            title,
                            description,
                            rate,
                            release,
                            poster,
                            type
                    )
            );
        }
        return moviesList;
    }
}
