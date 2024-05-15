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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import com.example.touristaapp.R;
import com.example.touristaapp.fragments.MapsFragment;
import com.example.touristaapp.models.TouristAttraction;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ViewPlaceActivity extends BaseActivity implements MapsFragment.OnMapReadyListener {

    Intent intent;
    Gson gson;
    TouristAttraction touristAttraction;
    Fragment mapFragment;
    MaterialCardView detailsCardView;
    TextView placeName;
    TextView placeDescription;
    TextView placeAddress;
    TextView placePhoneNumber;
    TextView placeOpenHours;
    RatingBar placeRating;
    Button btnAddReview;
    Button btnAddEvent;
    ImageView coverIV;

    View mapView;
    ListView reviewList;
    ListView eventList;

    ImageButton btnExpandReview;
    ImageButton btnExpandEvent;
    MaterialCardView reviewCardView;
    MaterialCardView eventCardView;


    List<String> reviewUserName;

    List<String> reviewText;

    List<Float> reviewRating;

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
        mapView= findViewById(R.id.map_view);

        btnExpandReview = findViewById(R.id.expand_reviews);
        reviewCardView = findViewById(R.id.review_card);
        coverIV = findViewById(R.id.coverIV);

        eventCardView = findViewById(R.id.event_card);
        btnExpandEvent = findViewById(R.id.expand_events);


        reviewUserName = new ArrayList<>();
        reviewText = new ArrayList<>();
        reviewRating = new ArrayList<>();




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
        Log.d("VIEWPLACETAG", "jsonData: " + jsonData);
        touristAttraction = gson.fromJson(jsonData, TouristAttraction.class);

        // Check if touristAttraction is not null before using it
        if (touristAttraction != null) {
            Log.d("VIEWPLACETAG", "onCreatePlace: " + touristAttraction.toString());
            placeName.setText(touristAttraction.getName());
            placeDescription.setText(touristAttraction.getDescription());
            placeAddress.setText(touristAttraction.getAddress());
            placePhoneNumber.setText(String.valueOf(touristAttraction.getPhoneNumber()));
            placeOpenHours.setText(touristAttraction.getOpenHours());
            placeRating.setRating(touristAttraction.getRating());
            //btnViewReview.setOnClickListener(v -> openReviewActivity());
            touristAttraction.getReview().forEach(review -> {
                reviewUserName.add(review.getUserName());
                reviewText.add(review.getComment());
                reviewRating.add((float) review.getRating());
            });
        } else {
            Log.d("VIEWPLACETAG", "onCreatePlace: touristAttraction is null");
        }

        btnAddReview.setOnClickListener(v -> addReviewActivity());
        btnAddEvent.setOnClickListener(v -> addEventActivity());

        // set the Review List
        ReviewListAdapter reviewListAdapter = new ReviewListAdapter(this, reviewUserName, reviewText, reviewRating);
        reviewList = (ListView) findViewById(R.id.reviewList);
        reviewList.setAdapter(reviewListAdapter);
       /* reviewList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                if (position == 0) {
                    //code specific to first list item
                    Toast.makeText(getApplicationContext(), "Place Your First Option Code", Toast.LENGTH_SHORT).show();
                } else if (position == 1) {
                    //code specific to 2nd list item
                    Toast.makeText(getApplicationContext(), "Place Your Second Option Code", Toast.LENGTH_SHORT).show();
                } else if (position == 2) {

                    Toast.makeText(getApplicationContext(), "Place Your Third Option Code", Toast.LENGTH_SHORT).show();
                } else if (position == 3) {

                    Toast.makeText(getApplicationContext(), "Place Your Forth Option Code", Toast.LENGTH_SHORT).show();
                } else if (position == 4) {

                    Toast.makeText(getApplicationContext(), "Place Your Fifth Option Code", Toast.LENGTH_SHORT).show();
                }
            }
        });*/
        btnExpandReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (reviewCardView.getVisibility() == View.VISIBLE) {
                    // Apply the animation
                    TransitionManager.beginDelayedTransition(reviewCardView, new AutoTransition());
                    reviewCardView.setVisibility(View.GONE);
                    btnExpandReview.setImageResource(R.drawable.ic_expand_more);
                    if(eventCardView.getVisibility() != View.VISIBLE){
                        mapView.setVisibility(View.VISIBLE);
                        coverIV.setVisibility(View.VISIBLE);
                        Animation scaleUp = AnimationUtils.loadAnimation(ViewPlaceActivity.this, R.anim.scale_up);
                        mapView.startAnimation(scaleUp);
                    }
                } else {
                    TransitionManager.beginDelayedTransition(reviewCardView, new AutoTransition());
                    Animation scaleDown = AnimationUtils.loadAnimation(ViewPlaceActivity.this, R.anim.scale_down);
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
        EventListAdapter eventListAdapter = new EventListAdapter(this, reviewUserName, reviewText, reviewRating);
        eventList = (ListView) findViewById(R.id.eventList);
        eventList.setAdapter(eventListAdapter);
        /*eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                if (position == 0) {
                    //code specific to first list item
                    Toast.makeText(getApplicationContext(), "Place Your First Option Code", Toast.LENGTH_SHORT).show();
                } else if (position == 1) {
                    //code specific to 2nd list item
                    Toast.makeText(getApplicationContext(), "Place Your Second Option Code", Toast.LENGTH_SHORT).show();
                } else if (position == 2) {

                    Toast.makeText(getApplicationContext(), "Place Your Third Option Code", Toast.LENGTH_SHORT).show();
                } else if (position == 3) {

                    Toast.makeText(getApplicationContext(), "Place Your Forth Option Code", Toast.LENGTH_SHORT).show();
                } else if (position == 4) {

                    Toast.makeText(getApplicationContext(), "Place Your Fifth Option Code", Toast.LENGTH_SHORT).show();
                }
            }
        });*/

        btnExpandEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eventCardView.getVisibility() == View.VISIBLE) {
                    // Apply the animation
                    TransitionManager.beginDelayedTransition(reviewCardView, new AutoTransition());
                    eventCardView.setVisibility(View.GONE);
                    btnExpandEvent.setImageResource(R.drawable.ic_expand_more);
                    if(reviewCardView.getVisibility() != View.VISIBLE){
                        mapView.setVisibility(View.VISIBLE);
                        coverIV.setVisibility(View.VISIBLE);
                        // Apply the animation
                        Animation scaleUp = AnimationUtils.loadAnimation(ViewPlaceActivity.this, R.anim.scale_up);
                        mapView.startAnimation(scaleUp);
                    }

                } else {
                    TransitionManager.beginDelayedTransition(eventCardView, new AutoTransition());
                    Animation scaleDown = AnimationUtils.loadAnimation(ViewPlaceActivity.this, R.anim.scale_down);
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
            Intent reviewIntent = new Intent(ViewPlaceActivity.this, CreateReviewActivity.class);
            reviewIntent.putExtra("touristAttraction", gson.toJson(touristAttraction));
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
        Log.d("VIEWPLACETAG", "onMapReady: " + touristAttraction.toString());
        List<TouristAttraction> touristAttractionList = new ArrayList<>();
        touristAttractionList.add(touristAttraction);
        ((MapsFragment) mapFragment).addMarker(ViewPlaceActivity.this, touristAttractionList, touristAttraction.getCategory());
        //((MapsFragment) mapFragment).showPathToMarker(touristAttraction.getLatitude(), touristAttraction.getLongitude());
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