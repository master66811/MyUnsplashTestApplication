package com.example.myunsplashtestapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.myunsplashtestapplication.MainFragments.FavoriteFragment;
import com.example.myunsplashtestapplication.MainFragments.InfoFragment;
import com.example.myunsplashtestapplication.MainFragments.PhotoFragmentAlbom;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(navListener);

        //set the selected fragment for default - Don't work
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conteiner, new PhotoFragmentAlbom()).commit();
//        bottomNavigation.setSelectedItemId(R.id.nav_photo);
        //keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conteiner,
                    new PhotoFragmentAlbom()).commit();
        }
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_photo: //first fragment will be nav_favorite
                            selectedFragment = new FavoriteFragment();
                            break;
                        case R.id.nav_favorite: //second fragment will be nav_photo
                            selectedFragment = new PhotoFragmentAlbom();
                            break;
                        case R.id.nav_info:
                            selectedFragment = new InfoFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_conteiner, selectedFragment).commit();
                    return true;
                }
            };
}
