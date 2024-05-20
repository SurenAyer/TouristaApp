package com.example.touristaapp.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.ActionBar;

import com.example.touristaapp.R;
import com.example.touristaapp.models.TouristAttraction;
import com.google.gson.Gson;

import java.util.Calendar;

public class CreateEventActivity extends BaseActivity {

    private TextView eventPlaceName;
    private EditText eventName;
    private EditText eventDescription;
    private TextView eventUserName;
    private EditText eventDate;
    private EditText eventTime;
    private NumberPicker eventDurationDays;
    private NumberPicker eventDurationHours;
    private Button submitEvent;
    private TouristAttraction touristAttraction;
    private Intent intent;
    private Gson gson;
    private String TAG = "CREATEEVENTTAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Retrieve the login state
        SharedPreferences sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        // If the user is not logged in, redirect to LoginActivity
        if (!isLoggedIn) {
            Intent intent = new Intent(CreateEventActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }


        eventPlaceName = findViewById(R.id.eventPlaceName);
        eventUserName = findViewById(R.id.eventUserName);
        eventName = findViewById(R.id.eventName);
        eventDescription = findViewById(R.id.eventDescription);
        eventDate = findViewById(R.id.eventDate);
        eventTime = findViewById(R.id.eventTime);
        eventDurationDays = findViewById(R.id.eventDurationDays);
        eventDurationHours = findViewById(R.id.eventDurationHours);


        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        int hour = cldr.get(Calendar.HOUR_OF_DAY);
        int minutes = cldr.get(Calendar.MINUTE);

        // date picker dialog
        eventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog picker = new DatePickerDialog(CreateEventActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eventDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        // time picker dialog
        eventTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog picker = new TimePickerDialog(CreateEventActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                eventTime.setText(sHour + ":" + sMinute);
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });
        NumberPicker eventDurationDays = findViewById(R.id.eventDurationDays);
        NumberPicker eventDurationHours = findViewById(R.id.eventDurationHours);

        eventDurationDays.setMinValue(0);
        eventDurationDays.setMaxValue(365); // Maximum days in a year

        eventDurationHours.setMinValue(0);
        eventDurationHours.setMaxValue(23); // Maximum hours in a day

        intent = getIntent();

        // Retrieve the JSON string from the intent
        String jsonData = intent.getStringExtra("touristAttraction");

        // Initialize a new Gson object
        gson = new Gson();
        Log.d(TAG, "jsonData: " + jsonData);
        touristAttraction = gson.fromJson(jsonData, TouristAttraction.class);

        // Check if touristAttraction is not null before using it
        if (touristAttraction != null) {
            Log.d("CREATEEVENTTAG", "onCreateEvent: " + touristAttraction.toString());
            eventUserName.setText(touristAttraction.getUser().getFirstName()+" "+touristAttraction.getUser().getLastName());
            eventPlaceName.setText(touristAttraction.getName());
        } else {
            Log.d("CREATEEVENTTAG", "onCreateEvent: touristAttraction is null");
        }
        submitEvent = findViewById(R.id.submitEvent);

        submitEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Use these values to create a new event
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}