package com.digitcreativestudio.moviecataloguelocalstorage;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_INTENT = "item";
    private ImageView image;
    private TextView title, description, release, genre, favorite;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        db = new DatabaseHelper(this);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Detail Movie");
            // set menu back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        title = findViewById(R.id.text_title);
        description = findViewById(R.id.text_description);
        release = findViewById(R.id.text_release);
        genre = findViewById(R.id.text_genre);
        image = findViewById(R.id.image_movie);
        favorite = findViewById(R.id.image_favorite);

        final Movie movie = getIntent().getParcelableExtra(EXTRA_INTENT);
        title.setText(movie.getTitle());
        description.setText(description.getText().toString().trim() + ":\n" + movie.getDescription());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            description.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }
        release.setText(release.getText().toString().trim() + ": " + movie.getRelease());
        genre.setText(movie.getGenre());
        Glide.with(getApplicationContext()).load(movie.getPoster()).into(image);

        if(db.existCheck(movie.getId())){
            favorite.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_favorite_red_24dp, 0, 0, 0);
        } else {
            favorite.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_favorite_white_24dp, 0, 0, 0);
        }

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!db.existCheck(movie.getId())){
                    db.insertMovie(
                            movie.getId(),
                            movie.getTitle(),
                            movie.getDescription(),
                            movie.getGenre(),
                            movie.getRelease(),
                            movie.getPoster(),
                            movie.getType()
                    );
                    Toast.makeText(getApplicationContext(), "favorite: "+movie.getTitle(), Toast.LENGTH_LONG).show();
                    favorite.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_favorite_red_24dp, 0, 0, 0);
                } else {
                    db.deleteMovie(
                            movie
                    );
                    Toast.makeText(getApplicationContext(), "un-favorite: "+movie.getTitle(), Toast.LENGTH_LONG).show();
                    favorite.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_favorite_white_24dp, 0, 0, 0);
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
}
