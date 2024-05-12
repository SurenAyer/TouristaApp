package com.example.touristaapp.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.touristaapp.R;
import com.google.android.material.navigation.NavigationBarView;

public class ExploreActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Toast.makeText(this, "Welcome to Explore Activity", Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        NavigationBarView navigation = findViewById(R.id.bottomNavigationView);
        setupNavigation(navigation, ExploreActivity.class, R.id.explore);


    }
}