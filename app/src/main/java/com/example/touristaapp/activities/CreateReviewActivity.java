package com.example.touristaapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.touristaapp.R;
import com.example.touristaapp.models.TouristAttraction;
import com.google.gson.Gson;

public class CreateReviewActivity extends BaseActivity {

    private TextView userName;
    private TextView placeName;
    private RatingBar reviewRating;
    private EditText reviewComment;
    private Button submitReview;
    private TouristAttraction touristAttraction;
    private Intent intent;
    private Gson gson;
    private String TAG = "CREATEREVIEWTAG";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_review);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }



        userName = findViewById(R.id.reviewUserName);
        placeName = findViewById(R.id.reviewPlaceName);
        reviewRating = findViewById(R.id.reviewRating);
        reviewComment = findViewById(R.id.reviewComment);
        submitReview = findViewById(R.id.submitReview);
        intent = getIntent();

        // Retrieve the JSON string from the intent
        String jsonData = intent.getStringExtra("touristAttraction");

        // Initialize a new Gson object
        gson = new Gson();
        Log.d(TAG, "jsonData: " + jsonData);
        touristAttraction = gson.fromJson(jsonData, TouristAttraction.class);

        // Check if touristAttraction is not null before using it
        if (touristAttraction != null) {
            Log.d(TAG, "onCreateReview: " + touristAttraction.toString());
            userName.setText(touristAttraction.getUser().getFirstName()+" "+touristAttraction.getUser().getLastName());
            placeName.setText(touristAttraction.getName());
        } else {
            Log.d(TAG, "onCreateReview: touristAttraction is null");
        }

        submitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String userName = reviewUserName.getText().toString();
                float rating = reviewRating.getRating();
                String comment = reviewComment.getText().toString();

                // Use these values to create a new review
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}