package com.digitcreativestudio.moviefavoritesfinalproject;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;

public class MovieContract {
    public static final class MovieColumns implements BaseColumns {
        public static final String TABLE_NAME = "movie";

        public static String COLUMN_ID = "movie_id";
        public static String COLUMN_TITLE = "title";
        public static String COLUMN_DESCRIPTION = "description";
        public static String COLUMN_RATE = "rate";
        public static String COLUMN_RELEASE = "release";
        public static String COLUMN_POSTER = "poster";
        public static String COLUMN_TYPE = "type";

        public static final String AUTHORITY = "com.digitcreativestudio.moviecataloguefinalproject";
        public static final String SCHEME = "content";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(MovieColumns.TABLE_NAME)
                .build();

        // Create table SQL query
        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + "("
                        + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + COLUMN_ID + " TEXT,"
                        + COLUMN_TITLE + " TEXT,"
                        + COLUMN_DESCRIPTION + " TEXT,"
                        + COLUMN_RATE + " TEXT,"
                        + COLUMN_RELEASE + " TEXT,"
                        + COLUMN_POSTER + " TEXT,"
                        + COLUMN_TYPE + " TEXT"
                        + ")";
    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }
}
