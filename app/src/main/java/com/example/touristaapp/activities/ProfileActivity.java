package com.example.touristaapp.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.touristaapp.R;
import com.google.android.material.navigation.NavigationBarView;

public class ProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        NavigationBarView navigation = findViewById(R.id.bottomNavigationView);
        setupNavigation(navigation, ViewPlaceActivity.class, R.id.profile);

    }
}