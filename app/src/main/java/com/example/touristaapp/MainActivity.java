package com.example.touristaapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationBarView navigation = findViewById(R.id.bottomNavigationView);
        setupNavigation(navigation, MainActivity.class, R.id.home);

        //Fragment mapFragment = getSupportFragmentManager().findFragmentById(R.id.map_fragment);
//        if (mapFragment instanceof MapFragment) {
//            MapFragment actualMapFragment = (MapFragment) mapFragment;
//            actualMapFragment.getMapAsync(tomtomMap -> {
//                /* Your code goes here */
//            });
//        }
    }
}