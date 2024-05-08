package com.example.touristaapp.activities;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.touristaapp.R;
import com.example.touristaapp.fragments.MapsFragment;
import com.example.touristaapp.models.TouristAttraction;
import com.example.touristaapp.utils.JsonReader;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainActivity extends BaseActivity implements MapsFragment.OnMapReadyListener{

//    PermissionsManager permissionsManager;
//    LocationEngine locationEngine;
//    private MapView mapView;
//    private MapboxMap map;
//    private static final String TAG = "MainActivity";
    //private boolean isFirstLocationUpdate = true;
    String TAG = "MAINACTIVITY";
    TextView card1TV;
    TextView card1Rating;
    TextView card2TV;
    TextView card2Rating;
    TextView card3TV;
    TextView card3Rating;
    NavigationBarView navigation;
    Fragment mapFragment;
    Map<String, String> categoryMap = new HashMap<>();
    String categorySelected="Natural";
    List<TouristAttraction> touristAttractionList = new ArrayList<>();
    List<TouristAttraction> trendingAttractionList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        categoryMap.put("category1", "Natural");
        categoryMap.put("category2", "Historical");
        categoryMap.put("category3", "Cultural");
        categoryMap.put("category4", "Religious");
        categoryMap.put("category5", "Entertainment");
        categoryMap.put("category6", "Adventure");

        navigation = findViewById(R.id.bottomNavigationView);
        setupNavigation(navigation, MainActivity.class, R.id.home);
        card1TV= findViewById(R.id.card1tv);
        card1Rating= findViewById(R.id.card1rating);
        card2TV= findViewById(R.id.card2tv);
        card2Rating= findViewById(R.id.card2rating);
        card3TV= findViewById(R.id.card3tv);
        card3Rating= findViewById(R.id.card3rating);

//        // Initialize Maps SDK
//        try {
//            MapsInitializer.initialize(this);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
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
        touristAttractionList=readData(categorySelected);
        trendingAttractionList=getTrendingAttraction(touristAttractionList);
        setTrendingAttraction();
        //((MapsFragment) mapFragment).onMapReady(map);
        Log.d(TAG, "CategorySelected= " + categorySelected);
    }


    private void getcategorySelected() {
        TabLayout tabLayout = findViewById(R.id.category);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d(TAG,"onTabSelected: "+tab.getPosition());
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
                touristAttractionList=readData(categorySelected);
                trendingAttractionList=getTrendingAttraction(touristAttractionList);
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

        if(trendingAttractionList!=null){
            card1TV.setText(trendingAttractionList.get(0).getName());
            card1Rating.setText(String.valueOf(trendingAttractionList.get(0).getRating()));
            if(trendingAttractionList.size()>1) {
                card2TV.setText(trendingAttractionList.get(1).getName());
                card2Rating.setText(String.valueOf(trendingAttractionList.get(1).getRating()));
            }
            else {
                card2TV.setText("No Data");
                card2Rating.setText("0.0");
            }
            if(trendingAttractionList.size()>2) {
                card3TV.setText(trendingAttractionList.get(2).getName());
                card3Rating.setText(String.valueOf(trendingAttractionList.get(2).getRating()));
            }
            else {
                card3TV.setText("No Data");
                card3Rating.setText("0.0");
            }
        }else{
            card1TV.setText("No Data");
            card1Rating.setText("0.0");
            card2TV.setText("No Data");
            card2Rating.setText("0.0");
            card3TV.setText("No Data");
            card3Rating.setText("0.0");
        }
    }

    private List<TouristAttraction> readData(String category) {
        List<TouristAttraction> touristAttractionList=new ArrayList<>();
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
    if (touristAttractionList.size() > 0) {
        topAttractionList = touristAttractionList.stream()
                .sorted(Comparator.comparing(TouristAttraction::getRating).reversed())
                .limit(3)
                .collect(Collectors.toList());
        return topAttractionList;
    }
    else{
        return null;
    }
}

    @Override
    public void onMapReady() {
        // The map is ready. You can call addMarker or other methods here.
    addNewMarkers();
    }

}