package com.example.touristaapp.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.example.touristaapp.R;
import com.example.touristaapp.models.Event;
import com.example.touristaapp.models.Review;
import com.example.touristaapp.models.TouristAttraction;
import com.example.touristaapp.models.User;
import com.example.touristaapp.repositories.TouristAttractionRepository;
import com.example.touristaapp.repositories.TouristAttractionRepositoryImpl;
import com.example.touristaapp.services.MyFirebaseMessagingService;
import com.example.touristaapp.utils.AttractionAdapter;
import com.example.touristaapp.utils.EventListAdapter;
import com.example.touristaapp.utils.JsonReader;
import com.example.touristaapp.utils.PlaceListAdapter;
import com.example.touristaapp.utils.ReviewListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProfileActivity extends BaseActivity {
    private Button logOutButton;
    private TextView userName;
    private TextView userEmail;
    private TextView userPhone;
    private TabLayout tabLayout;
    private FloatingActionButton notificationBtn;
    private User user;
    private ListView profilePlaceList;
    private ListView profileReviewList;
    private ListView profileEventList;
    private SharedPreferences notificationSharedPreferences;
    private Gson gson;
    private String TAG = "ProfileActivityTAG";
    private TouristAttractionRepository touristAttractionRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        gson = new Gson();
        // Retrieve the login state
        SharedPreferences sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        // If the user is not logged in, redirect to LoginActivity
        if (!isLoggedIn) {
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        String userJson = sharedPreferences.getString("user", "");
        User user = gson.fromJson(userJson, User.class);

        NavigationBarView navigation = findViewById(R.id.bottomNavigationView);
        setupNavigation(navigation, ViewPlaceActivity.class, R.id.profile);

        userName = findViewById(R.id.userNameTV);
        userEmail = findViewById(R.id.userEmailTV);
        userPhone = findViewById(R.id.userPhoneTV);
        userName.setText(user.getFirstName() + " " + user.getLastName());
        userEmail.setText(user.getEmail());
        userPhone.setText(String.valueOf(user.getPhoneNumber()));
        logOutButton = findViewById(R.id.logOut);
        notificationBtn = findViewById(R.id.notificationBtn);
        tabLayout = findViewById(R.id.myContributionTab);
        profilePlaceList = findViewById(R.id.profilePlaceList);
        profileReviewList = findViewById(R.id.profileReviewList);
        profileEventList = findViewById(R.id.profileEventList);

        touristAttractionRepository = new TouristAttractionRepositoryImpl();
        notificationSharedPreferences = getSharedPreferences("Notification", MODE_PRIVATE);
        Boolean newMessageReceived = notificationSharedPreferences.getBoolean("newMessageReceived", false);

        if (newMessageReceived) {
            notificationBtn.setVisibility(View.VISIBLE);
        }
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ProfileActivity.this)
                        .setTitle("Logout")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean("isLoggedIn", false);
                                editor.putString("user", "");
                                editor.apply();

                                // Navigate back to the login screen
                                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        profilePlaceList.setVisibility(View.VISIBLE);
                        profileReviewList.setVisibility(View.GONE);
                        profileEventList.setVisibility(View.GONE);
                        break;
                    case 1:
                        profilePlaceList.setVisibility(View.GONE);
                        profileReviewList.setVisibility(View.VISIBLE);
                        profileEventList.setVisibility(View.GONE);
                        break;

                    case 2:
                        profilePlaceList.setVisibility(View.GONE);
                        profileReviewList.setVisibility(View.GONE);
                        profileEventList.setVisibility(View.VISIBLE);
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Do something when a tab is unselected, if needed
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Do something when a tab is reselected, if needed
            }
        });
        notificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve the stored message

                Boolean newMessageReceived = notificationSharedPreferences.getBoolean("newMessageReceived", false);
                int attractionId = notificationSharedPreferences.getInt("AttractionId", 0);
                String title = notificationSharedPreferences.getString("Title", "New Tourist Attraction");
                JsonReader jsonReader = new JsonReader();
                TouristAttraction touristAttraction = jsonReader.getAttractionData(ProfileActivity.this, attractionId);

                if (newMessageReceived) {
                    // Show the notification
                    // Create and show a dialog
                    new AlertDialog.Builder(ProfileActivity.this)
                            .setTitle(title)
                            .setMessage(touristAttraction.getName() + " just got added.")
                            .setPositiveButton("Check out", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Navigate to the details of the new attraction
                                    Intent intent = new Intent(ProfileActivity.this, ViewPlaceActivity.class);
                                    String placeJson = gson.toJson(touristAttraction);
                                    intent.putExtra("touristAttraction", placeJson);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("Not Now", null)
                            .show();

                    // Reset the newMessageReceived flag
                    SharedPreferences.Editor editor = notificationSharedPreferences.edit();
                    editor.putBoolean("newMessageReceived", false);
                    editor.apply();
                    notificationBtn.setVisibility(View.GONE);
                }
            }
        });
        getAllTouristAttractions(user.getUserId());


    }


    public void getAllTouristAttractions(String userId) {
        // Get all the tourist attractions
        List<TouristAttraction> touristAttractionList = new ArrayList<>();
        try {
            touristAttractionRepository.getAllTouristAttractions(task -> {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        TouristAttraction attraction = new TouristAttraction();
                        Log.d(TAG, "getAttractionsBEFORE: " + attraction.toString());
                        attraction = document.toObject(TouristAttraction.class);
                        User user = document.get("user", User.class);
                        assert attraction != null;
                        attraction.setUser(user);
                        //   Log.d(TAG, "getAttractions: " + attraction.toString());
                        touristAttractionList.add(attraction);
                    }
                    user = gerUserData(userId, touristAttractionList);
                    if (user != null) {
                        setUserData(user);
                    }
                } else {
                    Log.e(TAG, "getAttractionsByCategory: Error getting data", task.getException());
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "readData: Error reading data from json file", e);
        }
    }


    public User gerUserData(String userId, List<TouristAttraction> touristAttractionList) {
        Log.d(TAG, "getAttractionsUser: " + userId);
        // Get the user's data from the database
        List<TouristAttraction> filteredAttractionList = new ArrayList<>();
        List<Review> reviewList = new ArrayList<>();
        List<Event> eventList = new ArrayList<>();
        if (touristAttractionList != null) {
            Log.d(TAG, "getAttractionsUser START: " + touristAttractionList.toString());

            try {
                filteredAttractionList = touristAttractionList.stream()
                        .filter(attraction -> attraction.getUser() != null && attraction.getUser().getUserId().equals(userId))
                        .collect(Collectors.toList());
                reviewList = touristAttractionList.stream()
                        .filter(attraction -> attraction.getReviews() != null)
                        .flatMap(attraction -> attraction.getReviews().stream())
                        .filter(review -> review.getUserId().equals(userId))
                        .collect(Collectors.toList());

                eventList = touristAttractionList.stream()
                        .filter(attraction -> attraction.getEvents() != null)
                        .flatMap(attraction -> attraction.getEvents().stream())
                        .filter(event -> event.getUserId().equals(userId))
                        .collect(Collectors.toList());

                if (!filteredAttractionList.isEmpty()) {
                    user = filteredAttractionList.get(0).getUser();
                    user.setTouristAttractions(filteredAttractionList);
                    user.setReviews(reviewList);
                    user.setEvents(eventList);
                }
                Log.d(TAG, "getAttractionsUser: " + user.toString());
                return user;
            } catch (Exception e) {
                Log.e(TAG, "getAttractionsUser: Error filtering data", e);
            }

        }
        return null;
    }


    private void setUserData(User user) {
        userName.setText(user.getFirstName() + " " + user.getLastName());
        userEmail.setText(user.getEmail());
        userPhone.setText(String.valueOf(user.getPhoneNumber()));
        setMyTouristAttractions(user.getTouristAttractions());
        setMyReviews(user.getReviews());
        setMyEvents(user.getEvents());
    }


    private void setMyTouristAttractions(List<TouristAttraction> touristAttractions) {
        // Show the user's created places
        PlaceListAdapter placeListAdapter = new PlaceListAdapter(this, touristAttractions);
        ListView placeListView = (ListView) findViewById(R.id.profilePlaceList);
        placeListView.setAdapter(placeListAdapter);
    }

    private void setMyReviews(List<Review> reviewList) {
        // set the Review List
        List<String> reviewUserName = reviewList.stream().map(Review::getUserName).collect(Collectors.toList());
        List<String> reviewText = reviewList.stream().map(Review::getComment).collect(Collectors.toList());
        List<Float> reviewRating = reviewList.stream().map(review -> (float) review.getRating()).collect(Collectors.toList());
        ReviewListAdapter reviewListAdapter = new ReviewListAdapter(this, reviewUserName, reviewText, reviewRating);
        ListView reviewListView = (ListView) findViewById(R.id.profileReviewList);
        reviewListView.setAdapter(reviewListAdapter);
    }

    private void setMyEvents(List<Event> events) {
        // set the Event List
        List<String> eventName = events.stream().map(event -> event.getEventName()).collect(Collectors.toList());
        List<Long> eventDate = events.stream().map(Event::getEventDate).collect(Collectors.toList());
        List<Integer> eventDuration = events.stream().map(Event::getDuration).collect(Collectors.toList());
        List<String> eventDescription = events.stream().map(Event::getDescription).collect(Collectors.toList());
        EventListAdapter eventListAdapter = new EventListAdapter(this, eventName, eventDate, eventDuration, eventDescription);
        ListView eventList = (ListView) findViewById(R.id.profileEventList);
        eventList.setAdapter(eventListAdapter);
    }
}