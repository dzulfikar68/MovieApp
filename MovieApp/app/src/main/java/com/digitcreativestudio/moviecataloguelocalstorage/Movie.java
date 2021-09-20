package com.digitcreativestudio.moviecataloguelocalstorage;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {

    public static final String TABLE_NAME = "user";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_RATE = "rate";
    public static final String COLUMN_RELEASE = "release";
    public static final String COLUMN_POSTER = "poster";
    public static final String COLUMN_TYPE = "type";

    private int id;
    private String title;
    private String description;
    private String genre;
    private String release;
    private String poster;
    private String type;


    // Create table SQL query
//    AUTOINCREMENT
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY,"
                    + COLUMN_TITLE + " TEXT,"
                    + COLUMN_DESCRIPTION + " TEXT,"
                    + COLUMN_RATE + " TEXT,"
                    + COLUMN_RELEASE + " TEXT,"
                    + COLUMN_POSTER + " TEXT,"
                    + COLUMN_TYPE + " TEXT"
                    + ")";

    public Movie(){

    }

    protected Movie(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        genre = in.readString();
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
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(genre);
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
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
