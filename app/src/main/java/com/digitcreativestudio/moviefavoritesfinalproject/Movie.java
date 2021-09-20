package com.digitcreativestudio.moviefavoritesfinalproject;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static android.provider.BaseColumns._ID;
import static com.digitcreativestudio.moviefavoritesfinalproject.MovieContract.MovieColumns.*;
import static com.digitcreativestudio.moviefavoritesfinalproject.MovieContract.getColumnInt;
import static com.digitcreativestudio.moviefavoritesfinalproject.MovieContract.getColumnString;

public class Movie implements Parcelable {

    private int id;
    private int movie_id;
    private String title;
    private String description;
    private String rate;
    private String release;
    private String poster;
    private String type;

    public Movie(){}

    public Movie(
            int id,
            int movie_id,
            String title,
            String description,
            String rate,
            String release,
            String poster,
            String type
    ) {
        this.id = id;
        this.movie_id = movie_id;
        this.title = title;
        this.description = description;
        this.rate = rate;
        this.release = release;
        this.poster = poster;
        this.type = type;
    }

    public Movie(Cursor cursor) {
        this.id = getColumnInt(cursor, _ID);
        this.movie_id = getColumnInt(cursor, COLUMN_ID);
        this.title = getColumnString(cursor, COLUMN_TITLE);
        this.description = getColumnString(cursor, COLUMN_DESCRIPTION);
        this.rate = getColumnString(cursor, COLUMN_RATE);
        this.release = getColumnString(cursor, COLUMN_RELEASE);
        this.poster = getColumnString(cursor, COLUMN_POSTER);
        this.type = getColumnString(cursor, COLUMN_TYPE);
    }

    protected Movie(Parcel in) {
        id = in.readInt();
        movie_id = in.readInt();
        title = in.readString();
        description = in.readString();
        rate = in.readString();
        release = in.readString();
        poster = in.readString();
        type = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(movie_id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(rate);
        dest.writeString(release);
        dest.writeString(poster);
        dest.writeString(type);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static Creator<Movie> getCREATOR() {
        return CREATOR;
    }
}
