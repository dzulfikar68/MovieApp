package com.digitcreativestudio.moviecataloguelocalstorage;

import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //    bismillahhirrohmannirrohhim . . .

    Fragment fragment;
    Button setting, favorites;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_movie:
                    setFragment(new MovieFragment());
                    return true;
                case R.id.navigation_tv:
                    setFragment(new TvFragment());
                    return true;
                case R.id.navigation_movie_now:
                    setFragment(new MovieNowFragment());
                    return true;
                case R.id.navigation_tv_top:
                    setFragment(new TvTopFragment());
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setFragment(new MovieFragment());

        setting = findViewById(R.id.btn_setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
            }
        });

        favorites = findViewById(R.id.btn_favorite);
        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent moveWithObjectIntent = new Intent(getApplicationContext(), FavoriteActivity.class);
                startActivity(moveWithObjectIntent);
            }
        });
    }

    private void setFragment(Fragment fr){
        fragment = fr;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                .commit();
    }
}
