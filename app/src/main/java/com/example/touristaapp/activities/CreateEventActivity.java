package com.example.touristaapp.activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;

import com.example.touristaapp.R;
import com.example.touristaapp.models.Event;
import com.example.touristaapp.models.Review;
import com.example.touristaapp.models.TouristAttraction;
import com.example.touristaapp.models.User;
import com.example.touristaapp.repositories.EventRepository;
import com.example.touristaapp.repositories.EventRepositoryImpl;
import com.example.touristaapp.repositories.TouristAttractionRepository;
import com.example.touristaapp.repositories.TouristAttractionRepositoryImpl;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
    private EventRepository eventRepository;
    private TouristAttractionRepository touristAttractionRepository;
    private ProgressDialog progressDialog;
    private User user;


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
        eventRepository = new EventRepositoryImpl();
        touristAttractionRepository = new TouristAttractionRepositoryImpl();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Submitting event...");
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

        // Retrieve the JSON string from the intent
        intent = getIntent();

        // Retrieve the JSON string from the intent
        String jsonData = intent.getStringExtra("touristAttraction");
        String userJsonData= intent.getStringExtra("user");

        // Initialize a new Gson object
        gson = new Gson();
        Log.d(TAG, "jsonData: " + jsonData);
        touristAttraction = gson.fromJson(jsonData, TouristAttraction.class);
        user= gson.fromJson(userJsonData, User.class);

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

                String name = eventName.getText().toString();
                String description = eventDescription.getText().toString();
                String date = eventDate.getText().toString();
                String time = eventTime.getText().toString();
                int durationDays = eventDurationDays.getValue();
                int durationHours = eventDurationHours.getValue();
                Long dateTime=Long.valueOf(date.split("/")[0])*24*60*60*1000+Long.valueOf(date.split("/")[1])*30*24*60*60*1000+Long.valueOf(date.split("/")[2])*365*24*60*60*1000;
                Event event=new Event();
                event.setEventName(name);
                event.setDescription(description);
                event.setEventDate(Long.valueOf(dateTime));
                event.setUserId(user.getUserId());
                event.setDuration(durationDays*24+durationHours);

                progressDialog.show();

                eventRepository.addEvent(event, new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Review Created");
                            if(touristAttraction.getEvents()==null){
                                List<Event> events=new ArrayList<>();
                                events.add(event);
                                touristAttraction.setEvents(events);
                            }
                            else {
                                touristAttraction.getEvents().add(event);
                            }
                            touristAttractionRepository.updateTouristAttraction(String.valueOf(touristAttraction.getAttractionId()), touristAttraction, updateTask -> {
                                if (updateTask.isSuccessful()) {
                                    Log.d("CreatePlace", "Attraction updated successfully: " + touristAttraction);
                                    progressDialog.dismiss();
                                    Intent intent = new Intent(CreateEventActivity.this, ViewPlaceActivity.class);
                                    intent.putExtra("touristAttraction", gson.toJson(touristAttraction));
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Log.e("CreatePlace", "Failed to update attraction: " + updateTask.getException());
                                }
                            });

                        } else {
                            Log.e(TAG, "Failed to add review to database", task.getException());
                        }
                    }
                });
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