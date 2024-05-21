package com.example.touristaapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.touristaapp.R;
import com.example.touristaapp.models.TouristAttraction;
import com.example.touristaapp.repositories.TouristAttractionRepository;
import com.example.touristaapp.repositories.TouristAttractionRepositoryImpl;
import com.example.touristaapp.utils.AttractionAdapter;
import com.example.touristaapp.utils.JsonReader;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ExploreActivity extends BaseActivity {

    private SearchView searchView;
    private List<TouristAttraction> touristAttractionList;

    private RecyclerView recyclerView;
    private AttractionAdapter adapter;
    private String TAG = "EXPLOREATTRACTIONTAG";
    private TouristAttractionRepository touristAttractionRepository;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        NavigationBarView navigation = findViewById(R.id.bottomNavigationView);
        setupNavigation(navigation, ExploreActivity.class, R.id.explore);
        recyclerView = findViewById(R.id.recyclerView);
        touristAttractionList= new ArrayList<>();
        touristAttractionRepository = new TouristAttractionRepositoryImpl();

        //Log.d(TAG, "readData: Reading data from json file");
        try {
            touristAttractionRepository.getAllTouristAttractions(task -> {
                if (task.isSuccessful()) {
                    touristAttractionList.addAll(task.getResult().toObjects(TouristAttraction.class));
                    adapter = new AttractionAdapter(touristAttractionList);
                    recyclerView.setAdapter(adapter);

                } else {
                    Log.d(TAG, "getAttractionsByCategory: Error getting data", task.getException());
                }
            });

        } catch (Exception e) {
            Log.d(TAG, "readData: Error reading data from json file", e);
        }


        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterTouristAttractions(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterTouristAttractions(newText);
                return false;
            }
        });

    }

    private void filterTouristAttractions(String query) {
        List<TouristAttraction> filteredList = new ArrayList<>();
        for (TouristAttraction attraction : touristAttractionList) {
            if (attraction.getName().toLowerCase().contains(query.toLowerCase()) ||
                    attraction.getAddress().toLowerCase().contains(query.toLowerCase()) ||
                    attraction.getCategory().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(attraction);
            }
        }
        adapter.updateData(filteredList);
    }
}