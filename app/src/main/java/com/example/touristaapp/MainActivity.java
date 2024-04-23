package com.example.touristaapp;

import android.os.Bundle;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationBarView navigation = findViewById(R.id.bottomNavigationView);
        setupNavigation(navigation, MainActivity.class, R.id.home);
    }
}