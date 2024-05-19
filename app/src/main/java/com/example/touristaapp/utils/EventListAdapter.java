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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class EventListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final List<String> name;
    private final List<String> date;
    private final List<String> time;
    private final List<Integer> duration;
    private final List<String> description;

    public EventListAdapter(Activity context, List<String> name, List<Long> date, List<Integer> duration,List<String> description) {
        super(context, R.layout.event_list, name);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.name = name;
        this.date = null;
        this.time = null;
        this.duration = duration;
        this.description = description;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.event_list, null, true);

        TextView nameView = (TextView) rowView.findViewById(R.id.eventNameTV);
        TextView dateView = (TextView) rowView.findViewById(R.id.eventDateTV);
        TextView timeView = (TextView) rowView.findViewById(R.id.eventTimeTV);
        TextView durationView = (TextView) rowView.findViewById(R.id.eventDurationTV);
        TextView descriptionTV = (TextView) rowView.findViewById(R.id.eventDescriptionTV);

        nameView.setText(name.get(position));
        //dateView.setText(date.get(position).toString());
        //timeView.setText(time.get(position).toString());
        dateView.setText("25/05/2024");
        timeView.setText("25/05/2024");
        float durationInHours = duration.get(position) / 60;
        durationView.setText(durationInHours+" hrs");
        descriptionTV.setText(description.get(position));
        return rowView;
    };
}