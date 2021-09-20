package com.digitcreativestudio.moviefavoritesfinalproject;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;
import static com.digitcreativestudio.moviefavoritesfinalproject.Constant.BASE_BACKDROP_URL_WIDGET;
import static com.digitcreativestudio.moviefavoritesfinalproject.Constant.MOVIE_DETAIL;
import static com.digitcreativestudio.moviefavoritesfinalproject.MovieContract.MovieColumns.*;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_INTENT = "item";
    private ImageView image;
    private TextView title, description, release, rate, favorite;
//    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Detail Movie");
            // set menu back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        title = findViewById(R.id.text_title);
        description = findViewById(R.id.text_description);
        release = findViewById(R.id.text_release);
        rate = findViewById(R.id.text_rate);
        image = findViewById(R.id.image_movie);
        favorite = findViewById(R.id.image_favorite);

        //change model movie
        final Movie movie = getIntent().getParcelableExtra(MOVIE_DETAIL);

        //set member of layout
        //====================
        //set title
        title.setText(movie.getTitle());

        //set description
        String text1 = description.getText().toString().trim() + ":\n" + movie.getDescription();
        description.setText(text1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            description.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }

        //set rate
        rate.setText(movie.getRate());

        //set release
        String text2 = release.getText().toString().trim() + ": " + movie.getRelease();
        release.setText(text2);

        //set poster
        Glide.with(getApplicationContext()).load(BASE_BACKDROP_URL_WIDGET + movie.getPoster()).into(image);

        //check Favorite
        if (isRecordExists(""+movie.getMovie_id())) {
            favorite.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_favorite_red_24dp, 0, 0, 0);
        } else {
            favorite.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_favorite_white_24dp, 0, 0, 0);
        }
        Log.v("MovieExist", ": " + isRecordExists(""+movie.getId()));

        //click Favorite
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRecordExists(""+movie.getMovie_id())) {
                    ContentValues contentValues = new ContentValues();
                    // Put the task description and selected mPriority into the ContentValues
                    contentValues.put(COLUMN_ID, movie.getMovie_id());
                    contentValues.put(COLUMN_TITLE, movie.getTitle());
                    contentValues.put(COLUMN_DESCRIPTION, movie.getDescription());
                    contentValues.put(COLUMN_RATE, movie.getRate());
                    contentValues.put(COLUMN_RELEASE, movie.getRelease());
                    contentValues.put(COLUMN_POSTER, movie.getPoster());
                    contentValues.put(COLUMN_TYPE, movie.getType());
                    // Insert the content values via a ContentResolver
                    getContentResolver().insert(MovieContract.MovieColumns.CONTENT_URI, contentValues);
                    favorite.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_favorite_red_24dp, 0, 0, 0);
                } else {
                    Uri uri = MovieContract.MovieColumns.CONTENT_URI;
                    uri = uri.buildUpon().appendPath(""+movie.getMovie_id()).build();
                    Log.v("MovieDetail", ": "+uri);

                    getContentResolver().delete(uri, null, null);
                    favorite.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_favorite_white_24dp, 0, 0, 0);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isRecordExists(String id) {
        String selection = " " + COLUMN_ID + " = ?";
        String[] selectionArgs = { id };
        String[] projection = {COLUMN_ID};
        Uri uri = MovieContract.MovieColumns.CONTENT_URI;
        uri = uri.buildUpon().appendPath(id).build();

        Cursor cursor = getContentResolver().query(uri, projection ,
                selection, selectionArgs, null, null);

        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        Log.v("Movie Exist: ", Boolean.toString(exists));
        return exists;
    }

}
