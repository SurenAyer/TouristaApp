package com.example.touristaapp.activities;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import com.example.touristaapp.R;
import com.google.android.material.navigation.NavigationBarView;

public class BaseActivity extends AppCompatActivity {
    String TAG = "BASEACTIVITY";

    protected void setupNavigation(NavigationBarView navigation, Class<?> currentActivityClass, @IdRes int selectedItemId) {
        int homeId = navigation.getMenu().findItem(R.id.home).getItemId();
        int exploreId = navigation.getMenu().findItem(R.id.explore).getItemId();
        int contributeId = navigation.getMenu().findItem(R.id.contribute).getItemId();
        int profileId = navigation.getMenu().findItem(R.id.profile).getItemId();
        navigation.setSelectedItemId(selectedItemId); // Set the selected item
        navigation.setOnItemSelectedListener(item -> {
            Intent intent;
            if (item.getItemId() == homeId) {
                Log.d(TAG, "onCreate: home");
                intent = new Intent(this, MainActivity.class);
            } else if (item.getItemId() == exploreId) {
                Log.d(TAG, "onCreate: explore");
                intent = new Intent(this, ExploreActivity.class);
            } else if (item.getItemId() == contributeId) {
                Log.d(TAG, "onCreate: contribute");
                intent = new Intent(this, ContributeActivity.class);
            } else if (item.getItemId() == profileId) {
                Log.d(TAG, "onCreate: profile");
                intent = new Intent(this, ProfileActivity.class);
            } else {
                return false;
            }
            // Prevents reloading the current activity
            if (!currentActivityClass.getName().equals(intent.getComponent().getClassName())) {
                startActivity(intent);
            }
            return true;
        });
    }
}