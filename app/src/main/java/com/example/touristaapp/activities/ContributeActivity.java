package com.example.touristaapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.fragment.app.Fragment;

import com.example.touristaapp.R;
import com.example.touristaapp.fragments.MapsFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ContributeActivity extends BaseActivity {
    private MapsFragment mapFragment;
    private TextInputEditText placeName;
    private TextInputEditText address;
    private TextInputEditText contactNumber;
    private TextInputEditText openHours;
    private TextInputEditText description;
    private AppCompatAutoCompleteTextView placeCategory;
    private Button btnCreatePlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contribute);
        // Retrieve the login state
        SharedPreferences sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        // If the user is not logged in, redirect to LoginActivity
        if (!isLoggedIn) {
            Intent intent = new Intent(ContributeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }


        NavigationBarView navigation = findViewById(R.id.bottomNavigationView);
        setupNavigation(navigation, ViewPlaceActivity.class, R.id.contribute);
        placeName = findViewById(R.id.placeName);
        address = findViewById(R.id.address);
        contactNumber = findViewById(R.id.contactNumber);
        openHours = findViewById(R.id.openHours);
        description = findViewById(R.id.description);
        placeCategory = findViewById(R.id.placeCategory);
        btnCreatePlace = findViewById(R.id.createPlaceBtn);
        mapFragment = new MapsFragment();

        // Open fragment
        getSupportFragmentManager()
                .beginTransaction().replace(R.id.map_view, mapFragment)
                .commit();


        String[] items = getResources().getStringArray(R.array.categories);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, items);
        TextInputLayout textField = findViewById(R.id.textField);
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) textField.getEditText();
        if (autoCompleteTextView != null) {
            autoCompleteTextView.setAdapter(adapter);
        }
        btnCreatePlace.setOnClickListener(v -> {
            LatLng lastMarkerPosition = mapFragment.getLastMarkerPosition();
            if (lastMarkerPosition != null) {
                Log.d("CreatePlace", "Marker Position: " + lastMarkerPosition.toString());
                Log.d("CreatePlace", "Place Name: " + placeName.getText().toString());
                Log.d("CreatePlace", "Address: " + address.getText().toString());
                Log.d("CreatePlace", "Contact Number: " + contactNumber.getText().toString());
                Log.d("CreatePlace", "Open Hours: " + openHours.getText().toString());
                Log.d("CreatePlace", "Description: " + description.getText().toString());
                Log.d("CreatePlace", "Category: " + placeCategory.getText().toString());
            }
            else {
                Log.d("CreatePlace", "Marker Position is null");
            }
        });
    }
}