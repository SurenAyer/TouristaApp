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
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.touristaapp.R;
import com.example.touristaapp.models.Review;
import com.example.touristaapp.models.TouristAttraction;
import com.example.touristaapp.models.User;
import com.example.touristaapp.repositories.ReviewRepository;
import com.example.touristaapp.repositories.ReviewRepositoryImpl;
import com.example.touristaapp.repositories.TouristAttractionRepository;
import com.example.touristaapp.repositories.TouristAttractionRepositoryImpl;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

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
    private ReviewRepository reviewRepository;
    private TouristAttractionRepository touristAttractionRepository;
    private User user;


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
        reviewRepository = new ReviewRepositoryImpl();
        touristAttractionRepository = new TouristAttractionRepositoryImpl();
        intent = getIntent();
        // Retrieve the JSON string from the intent
        String jsonData = intent.getStringExtra("touristAttraction");
        String userJsonData= intent.getStringExtra("user");

        // Initialize a new Gson object
        gson = new Gson();
        Log.d(TAG, "jsonData: " + jsonData);
        touristAttraction = gson.fromJson(jsonData, TouristAttraction.class);
        user= gson.fromJson(userJsonData, User.class);

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
                Review review= new Review();
                review.setRating(rating);
                review.setComment(comment);
                review.setUserId(user.getUserId());
                review.setUserName(user.getFirstName()+" "+user.getLastName());
                review.setTimestamp(System.currentTimeMillis());
                // Save in Database
                reviewRepository.addReview(review, new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Review Created");
                            if(touristAttraction.getReviews()==null){
                                List<Review> reviews=new ArrayList<>();
                                reviews.add(review);
                                touristAttraction.setReviews(reviews);
                                touristAttraction.setRating(rating);
                                }
                            else {
                                touristAttraction.getReviews().add(review);
                                float averageRating=touristAttraction.getReviews().stream().map(Review::getRating).reduce(0.0f, Float::sum)/touristAttraction.getReviews().size();
                                touristAttraction.setRating(averageRating);
                            }
                            touristAttractionRepository.updateTouristAttraction(String.valueOf(touristAttraction.getAttractionId()), touristAttraction, updateTask -> {
                                if (updateTask.isSuccessful()) {
                                    Log.d("CreatePlace", "Attraction updated successfully: " + touristAttraction);
                                    Intent intent = new Intent(CreateReviewActivity.this, ViewPlaceActivity.class);
                                    intent.putExtra("touristAttraction", gson.toJson(touristAttraction));
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Log.e("CreatePlace", "Failed to update attraction: " + updateTask.getException());
                                }
                            });

                        } else {
                            Log.e(TAG, "Failed to add review to database", task.getException());
                        }
                    }
                });
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