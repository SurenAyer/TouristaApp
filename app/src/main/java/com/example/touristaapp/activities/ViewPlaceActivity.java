package com.example.touristaapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import com.example.touristaapp.R;
import com.example.touristaapp.fragments.MapsFragment;
import com.example.touristaapp.models.TouristAttraction;
import com.example.touristaapp.utils.EventListAdapter;
import com.example.touristaapp.utils.ReviewListAdapter;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ViewPlaceActivity extends BaseActivity implements MapsFragment.OnMapReadyListener {

    private Intent intent;
    private Gson gson;
    private TouristAttraction touristAttraction;
    private Fragment mapFragment;
    private MaterialCardView detailsCardView;
    private TextView placeName;
    private TextView placeDescription;
    private TextView placeAddress;
    private TextView placePhoneNumber;
    private TextView placeOpenHours;
    private RatingBar placeRating;
    private Button btnAddReview;
    private Button btnAddEvent;
    private ImageView coverIV;

    private View mapView;
    private ListView reviewList;
    private ListView eventList;

    private ImageButton btnExpandReview;
    private ImageButton btnExpandEvent;
    private MaterialCardView reviewCardView;
    private MaterialCardView eventCardView;


    private List<String> reviewUserName;
    private List<String> reviewText;
    private List<Float> reviewRating;
    private List<String> eventName;
    private List<Long> eventDateTime;
    private List<Integer> eventDuration;
    private List<String> eventDescription;
    private static final String TAG = "ViewPlaceActivityTAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_place);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        NavigationBarView navigation = findViewById(R.id.bottomNavigationView);
        setupNavigation(navigation, ViewPlaceActivity.class, R.id.explore);
        detailsCardView = findViewById(R.id.details_card);
        placeName = findViewById(R.id.nameTV);
        placeDescription = findViewById(R.id.descriptionTV);
        placeAddress = findViewById(R.id.addressTV);
        placePhoneNumber = findViewById(R.id.contactTV);
        placeOpenHours = findViewById(R.id.openHoursTV);
        placeRating = findViewById(R.id.rating);
        btnAddReview = findViewById(R.id.addReviewBtn);
        btnAddEvent = findViewById(R.id.addEventBtn);
        mapView = findViewById(R.id.map_view);

        btnExpandReview = findViewById(R.id.expand_reviews);
        reviewCardView = findViewById(R.id.review_card);
        coverIV = findViewById(R.id.coverIV);

        eventCardView = findViewById(R.id.event_card);
        btnExpandEvent = findViewById(R.id.expand_events);


        reviewUserName = new ArrayList<>();
        reviewText = new ArrayList<>();
        reviewRating = new ArrayList<>();
        eventName = new ArrayList<>();
        eventDateTime = new ArrayList<>();
        eventDuration = new ArrayList<>();
        eventDescription = new ArrayList<>();


        // Initialize fragment
        mapFragment = new MapsFragment();
        ((MapsFragment) mapFragment).setOnMapReadyListener(this);

        // Open fragment
        getSupportFragmentManager()
                .beginTransaction().replace(R.id.map_view, mapFragment)
                .commit();
        // Get the intent that started this activity
        intent = getIntent();

        // Retrieve the JSON string from the intent
        String jsonData = intent.getStringExtra("touristAttraction");

        // Initialize a new Gson object
        gson = new Gson();
        touristAttraction = gson.fromJson(jsonData, TouristAttraction.class);

        // Check if touristAttraction is not null before using it
        if (touristAttraction != null) {
            placeName.setText(touristAttraction.getName());
            placeDescription.setText(touristAttraction.getDescription());
            placeAddress.setText(touristAttraction.getAddress());
            placePhoneNumber.setText(String.valueOf(touristAttraction.getPhoneNumber()));
            placeOpenHours.setText(touristAttraction.getOpenHours());
            placeRating.setRating(touristAttraction.getRating());
            try {
                touristAttraction.getReviews().forEach(review -> {
                    reviewUserName.add(review.getUserName());
                    reviewText.add(review.getComment());
                    reviewRating.add((float) review.getRating());
                });
            } catch (Exception e) {
                reviewUserName.add("No Reviews");
                reviewText.add("");
                reviewRating.add(0.0f);
            }

            try {
                touristAttraction.getEvents().forEach(event -> {
                    eventName.add(event.getEventName());
                    eventDateTime.add(event.getEventDate());
                    eventDuration.add(event.getDuration());
                    eventDescription.add(event.getDescription());
                });
            } catch (Exception e) {
                eventName.add("No Events");
                eventDateTime.add(0L);
                eventDuration.add(0);
                eventDescription.add("");
            }
        }
        btnAddReview.setOnClickListener(v -> addReviewActivity());
        btnAddEvent.setOnClickListener(v -> addEventActivity());

        // set the Review List
        ReviewListAdapter reviewListAdapter = new ReviewListAdapter(this, reviewUserName, reviewText, reviewRating);
        reviewList = (ListView) findViewById(R.id.reviewList);
        reviewList.setAdapter(reviewListAdapter);
        btnExpandReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reviewCardView.getVisibility() == View.VISIBLE) {
                    // Apply the animation
                    TransitionManager.beginDelayedTransition(reviewCardView, new AutoTransition());
                    reviewCardView.setVisibility(View.GONE);
                    btnExpandReview.setImageResource(R.drawable.ic_expand_more);
                    if (eventCardView.getVisibility() != View.VISIBLE) {
                        mapView.setVisibility(View.VISIBLE);
                        coverIV.setVisibility(View.VISIBLE);
                        Animation scaleUp = AnimationUtils.loadAnimation(ViewPlaceActivity.this, R.anim.scale_up);
                        mapView.startAnimation(scaleUp);
                    }
                } else {
                    TransitionManager.beginDelayedTransition(reviewCardView, new AutoTransition());
                    reviewCardView.setVisibility(View.VISIBLE);
                    mapView.setVisibility(View.GONE);
                    coverIV.setVisibility(View.GONE);
                    btnExpandReview.setImageResource(R.drawable.ic_expand_less);

                    // Apply the animation
                    Animation scaleUp = AnimationUtils.loadAnimation(ViewPlaceActivity.this, R.anim.scale_up);
                    reviewCardView.startAnimation(scaleUp);

                }
            }
        });


        // set the Event List
        EventListAdapter eventListAdapter = new EventListAdapter(this, eventName, eventDateTime, eventDuration, eventDescription);
        eventList = (ListView) findViewById(R.id.eventList);
        eventList.setAdapter(eventListAdapter);

        btnExpandEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ");
                if (eventCardView.getVisibility() == View.VISIBLE) {
                    // Apply the animation
                    TransitionManager.beginDelayedTransition(reviewCardView, new AutoTransition());
                    eventCardView.setVisibility(View.GONE);
                    btnExpandEvent.setImageResource(R.drawable.ic_expand_more);
                    if (reviewCardView.getVisibility() != View.VISIBLE) {
                        mapView.setVisibility(View.VISIBLE);
                        coverIV.setVisibility(View.VISIBLE);
                        // Apply the animation
                        Animation scaleUp = AnimationUtils.loadAnimation(ViewPlaceActivity.this, R.anim.scale_up);
                        mapView.startAnimation(scaleUp);
                    }

                } else {
                    TransitionManager.beginDelayedTransition(eventCardView, new AutoTransition());
                    eventCardView.setVisibility(View.VISIBLE);
                    mapView.setVisibility(View.GONE);
                    coverIV.setVisibility(View.GONE);
                    btnExpandEvent.setImageResource(R.drawable.ic_expand_less);

                    // Apply the animation
                    Animation scaleUp = AnimationUtils.loadAnimation(ViewPlaceActivity.this, R.anim.scale_up);
                    eventCardView.startAnimation(scaleUp);
                }
            }
        });
    }


    private void addReviewActivity() {
        // Retrieve the login state
        SharedPreferences sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        // If the user is not logged in, redirect to LoginActivity
        if (!isLoggedIn) {
            Intent intent = new Intent(ViewPlaceActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            String user=sharedPreferences.getString("user","");
            Intent reviewIntent = new Intent(ViewPlaceActivity.this, CreateReviewActivity.class);
            reviewIntent.putExtra("touristAttraction", gson.toJson(touristAttraction));
            reviewIntent.putExtra("user",user);
            startActivity(reviewIntent);
        }
    }


    private void addEventActivity() {
        // Retrieve the login state
        SharedPreferences sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        // If the user is not logged in, redirect to LoginActivity
        if (!isLoggedIn) {
            Intent intent = new Intent(ViewPlaceActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            Intent eventIntent = new Intent(ViewPlaceActivity.this, CreateEventActivity.class);
            eventIntent.putExtra("touristAttraction", gson.toJson(touristAttraction));
            startActivity(eventIntent);
        }
    }

    private void addNewMarkers() {
        List<TouristAttraction> touristAttractionList = new ArrayList<>();
        touristAttractionList.add(touristAttraction);
        ((MapsFragment) mapFragment).addMarker(ViewPlaceActivity.this, touristAttractionList, touristAttraction.getCategory());
    }

    @Override
    public void onMapReady() {

        addNewMarkers();
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

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}