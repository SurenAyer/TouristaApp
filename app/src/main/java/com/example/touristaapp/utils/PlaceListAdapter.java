package com.example.touristaapp.utils;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.touristaapp.R;
import com.example.touristaapp.activities.ViewPlaceActivity;
import com.example.touristaapp.models.TouristAttraction;
import com.google.gson.Gson;

import java.util.List;

public class PlaceListAdapter extends ArrayAdapter<TouristAttraction> {

    private final Activity context;
    private final List<TouristAttraction> attractions;
    private Gson gson;

    public PlaceListAdapter(Activity context, List<TouristAttraction> attractions) {
        super(context, R.layout.attraction_list_item, attractions);
        this.context = context;
        this.attractions = attractions;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.attraction_list_item, null, true);

        TextView placeNameView = (TextView) rowView.findViewById(R.id.attractionName);
        TextView placeAddressTV = (TextView) rowView.findViewById(R.id.attractionAddress);
        TextView placeCategoryTV = (TextView) rowView.findViewById(R.id.attractionCategory);
        RatingBar ratingBar = (RatingBar) rowView.findViewById(R.id.attractionRating);

        TouristAttraction attraction = attractions.get(position);
        placeNameView.setText(attraction.getName());
        placeAddressTV.setText(attraction.getAddress());
        placeCategoryTV.setText(attraction.getCategory());
        ratingBar.setRating(attraction.getRating());
        return rowView;
    }
}