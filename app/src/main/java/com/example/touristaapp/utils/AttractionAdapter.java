package com.example.touristaapp.utils;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.touristaapp.R;
import com.example.touristaapp.activities.ViewPlaceActivity;
import com.example.touristaapp.models.TouristAttraction;
import com.google.gson.Gson;

import java.util.List;

public class AttractionAdapter extends RecyclerView.Adapter<AttractionAdapter.ViewHolder> {
    private List<TouristAttraction> attractions;

    public AttractionAdapter(List<TouristAttraction> attractions) {
        this.attractions = attractions;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attraction_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TouristAttraction attraction = attractions.get(position);
        holder.bind(attraction);
    }

    @Override
    public int getItemCount() {
        return attractions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, address, category;
        RatingBar rating;
        TouristAttraction attraction;
        private Gson gson;

        public ViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.attractionName);
            address = view.findViewById(R.id.attractionAddress);
            category = view.findViewById(R.id.attractionCategory);
            rating = view.findViewById(R.id.attractionRating);
            view.setOnClickListener(this);
        }

        public void bind(TouristAttraction attraction) {
            this.attraction = attraction;
            name.setText(attraction.getName());
            address.setText(attraction.getAddress());
            category.setText(attraction.getCategory());
            rating.setRating(attraction.getRating());
        }

        @Override
        public void onClick(View v) {
            // Handle click events
            gson = new Gson();
            Intent intent = new Intent(v.getContext(), ViewPlaceActivity.class);
            String placeJson = gson.toJson(attraction);
            intent.putExtra("touristAttraction", placeJson);
            v.getContext().startActivity(intent);
        }
    }

    public void updateData(List<TouristAttraction> newAttractions) {
        this.attractions = newAttractions;
        notifyDataSetChanged();
    }

}