package com.example.touristaapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.touristaapp.R;
import com.example.touristaapp.fragments.MapsFragment;
import com.example.touristaapp.models.TouristAttraction;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;

public class ReviewActivity extends BaseActivity {
    private Intent intent;
    private Gson gson;
    private TouristAttraction touristAttraction;
    private Fragment mapFragment;
    private TextView placeName;
    private TextView placeAddress;
    private TextView placePhoneNumber;
    private TextView placeOpenHours;
    private RatingBar placeRating;
    private String TAG = "REVIEWACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_review);
        NavigationBarView navigation = findViewById(R.id.bottomNavigationView);
        setupNavigation(navigation, ViewPlaceActivity.class, R.id.explore);
        placeName = findViewById(R.id.nameTV);
        placeAddress = findViewById(R.id.addressTV);
        placePhoneNumber = findViewById(R.id.contactTV);
        placeOpenHours = findViewById(R.id.openHoursTV);
        placeRating = findViewById(R.id.rating);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Get the intent that started this activity
        intent = getIntent();

        // Retrieve the JSON string from the intent
        String jsonData = intent.getStringExtra("touristAttraction");

        // Initialize a new Gson object
        gson = new Gson();
        touristAttraction= gson.fromJson(jsonData, TouristAttraction.class);

        // Check if touristAttraction is not null before using it
        if (touristAttraction != null) {
            Log.d(TAG, "onCreatePlace: " + touristAttraction.toString());
            placeName.setText(touristAttraction.getName());
            placeAddress.setText(touristAttraction.getAddress());
            placePhoneNumber.setText(String.valueOf(touristAttraction.getPhoneNumber()));
            placeOpenHours.setText(touristAttraction.getOpenHours());
            placeRating.setRating(touristAttraction.getRating());
            //btnViewReview.setOnClickListener(v -> openReviewActivity());
        } else {
            Log.d(TAG, "onCreatePlace: touristAttraction is null");
        }
    }
}