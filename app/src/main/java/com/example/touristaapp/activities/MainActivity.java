package com.example.touristaapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.touristaapp.R;
import com.example.touristaapp.fragments.MapsFragment;
import com.example.touristaapp.models.TouristAttraction;
import com.example.touristaapp.utils.JsonReader;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainActivity extends BaseActivity implements MapsFragment.OnMapReadyListener {

    private String TAG = "MAINACTIVITY";
    private TextView card1TV;
    private RatingBar card1Rating;
    private TextView card2TV;
    private RatingBar card2Rating;
    private TextView card3TV;
    private RatingBar card3Rating;
    private NavigationBarView navigation;
    private Fragment mapFragment;
    private CardView cardView1;
    private CardView cardView2;
    private CardView cardView3;
    private Map<String, String> categoryMap;
    private String categorySelected = "Natural";
    private List<TouristAttraction> touristAttractionList;
    private List<TouristAttraction> trendingAttractionList;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        categoryMap = new HashMap<>();
        categoryMap.put("category1", "Natural");
        categoryMap.put("category2", "Historical");
        categoryMap.put("category3", "Cultural");
        categoryMap.put("category4", "Religious");
        categoryMap.put("category5", "Entertainment");
        categoryMap.put("category6", "Adventure");

        navigation = findViewById(R.id.bottomNavigationView);
        setupNavigation(navigation, MainActivity.class, R.id.home);
        card1TV = findViewById(R.id.card1tv);
        card1Rating = findViewById(R.id.card1rating);
        card2TV = findViewById(R.id.card2tv);
        card2Rating = findViewById(R.id.card2rating);
        card3TV = findViewById(R.id.card3tv);
        card3Rating = findViewById(R.id.card3rating);
        cardView1 = findViewById(R.id.card1);
        cardView2 = findViewById(R.id.card2);
        cardView3 = findViewById(R.id.card3);
        gson = new Gson();
        touristAttractionList = new ArrayList<>();
        trendingAttractionList = new ArrayList<>();
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPlaceIntent(trendingAttractionList.get(0));
            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPlaceIntent(trendingAttractionList.get(1));
            }
        });

        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPlaceIntent(trendingAttractionList.get(2));
            }
        });

        // Initialize fragment
        mapFragment = new MapsFragment();
        ((MapsFragment) mapFragment).setOnMapReadyListener(this);

        // Open fragment
        getSupportFragmentManager()
                .beginTransaction().replace(R.id.map_view, mapFragment)
                .commit();


        getcategorySelected();
        //Display Map and Trending Based on category
        //Get Currently Selected Category
        touristAttractionList = readData(categorySelected);
        trendingAttractionList = getTrendingAttraction(touristAttractionList);
        Log.d(TAG, "EventDate= " + trendingAttractionList.get(0).getEvents().get(0).getEventDate());
        setTrendingAttraction();
        //((MapsFragment) mapFragment).onMapReady(map);
        Log.d(TAG, "CategorySelected= " + categorySelected);
    }

    private void viewPlaceIntent(TouristAttraction touristAttraction) {
        Intent cardIntent = new Intent(MainActivity.this, ViewPlaceActivity.class);
        String placeJson = gson.toJson(touristAttraction);
        Log.d(TAG, "onClick: " + placeJson);
        cardIntent.putExtra("touristAttraction", placeJson);
        Log.d(TAG, "DataSent: " + cardIntent.getStringExtra("touristAttraction"));
        startActivity(cardIntent);
    }


    private void getcategorySelected() {
        TabLayout tabLayout = findViewById(R.id.category);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d(TAG, "onTabSelected: " + tab.getPosition());
                switch (tab.getPosition()) {
                    case 0:
                        //id = R.id.category1;
                        categorySelected = categoryMap.get("category1");
                        break;
                    case 1:
                        categorySelected = categoryMap.get("category2");
                        break;
                    case 2:
                        categorySelected = categoryMap.get("category3");
                        break;
                    case 3:
                        categorySelected = categoryMap.get("category4");
                        break;
                    case 4:
                        categorySelected = categoryMap.get("category5");
                        break;
                    case 5:
                        categorySelected = categoryMap.get("category6");
                        break;
                    default:
                        categorySelected = "Natural";
                        break;
                }
                touristAttractionList = readData(categorySelected);
                trendingAttractionList = getTrendingAttraction(touristAttractionList);
                setTrendingAttraction();
                addNewMarkers();
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
    }

    private void addNewMarkers() {
        ((MapsFragment) mapFragment).addMarker(MainActivity.this, touristAttractionList, categorySelected);
    }

    private void setTrendingAttraction() {
        cardView1.setClickable(true);
        cardView2.setClickable(true);
        cardView3.setClickable(true);
        if (trendingAttractionList != null) {
            card1TV.setText(trendingAttractionList.get(0).getName());
            card1Rating.setRating(trendingAttractionList.get(0).getRating());
            if (trendingAttractionList.size() > 1) {
                card2TV.setText(trendingAttractionList.get(1).getName());
                card2Rating.setRating(trendingAttractionList.get(1).getRating());
            } else {
                card2TV.setText("No Data");
                card2Rating.setRating(0.0F);
                cardView2.setClickable(false);
            }
            if (trendingAttractionList.size() > 2) {
                card3TV.setText(trendingAttractionList.get(2).getName());
                card3Rating.setRating(trendingAttractionList.get(2).getRating());
            } else {
                card3TV.setText("No Data");
                card3Rating.setRating(0.0F);
                cardView3.setClickable(false);
            }
        } else {
            card1TV.setText("No Data");
            card1Rating.setRating(0.0F);
            cardView1.setClickable(false);
            card2TV.setText("No Data");
            card2Rating.setRating(0.0F);
            cardView2.setClickable(false);
            card3TV.setText("No Data");
            card3Rating.setRating(0.0F);
            cardView3.setClickable(false);
        }
    }

    private List<TouristAttraction> readData(String category) {
        List<TouristAttraction> touristAttractionList = new ArrayList<>();
        try {
            //Log.d(TAG, "readData: Reading data from json file");
            touristAttractionList = JsonReader.readJsonFile(this, "data.json", category);
            assert touristAttractionList != null;
            return touristAttractionList;
        } catch (Exception e) {
            Log.d(TAG, "readData: Error reading data from json file", e);
            return null;
        }
    }

    private List<TouristAttraction> getTrendingAttraction(List<TouristAttraction> touristAttractionList) {
        List<TouristAttraction> topAttractionList = new ArrayList<>();
        if (!touristAttractionList.isEmpty()) {
            topAttractionList = touristAttractionList.stream()
                    .sorted(Comparator.comparing(TouristAttraction::getRating).reversed())
                    .limit(3)
                    .collect(Collectors.toList());
            return topAttractionList;
        } else {
            return null;
        }
    }

    @Override
    public void onMapReady() {
        // The map is ready. You can call addMarker or other methods here.
        addNewMarkers();
    }

}