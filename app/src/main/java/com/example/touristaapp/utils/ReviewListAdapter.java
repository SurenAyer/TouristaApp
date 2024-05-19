package com.example.touristaapp.utils;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.touristaapp.R;

import java.util.List;

public class ReviewListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final List<String> name;
    private final List<String> description;
    private final List<Float> rating;

    public ReviewListAdapter(Activity context, List<String> name, List<String> description, List<Float> rating) {
        super(context, R.layout.review_list, name);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.name = name;
        this.description = description;
        this.rating = rating;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.review_list, null, true);

        TextView nameView = (TextView) rowView.findViewById(R.id.nameTV);
        TextView descriptionTV = (TextView) rowView.findViewById(R.id.review_descriptionTV);
        RatingBar ratingBar = (RatingBar) rowView.findViewById(R.id.rating);

        nameView.setText(name.get(position));
        ratingBar.setRating(rating.get(position));
        descriptionTV.setText(description.get(position));
        return rowView;
    };
}