package com.example.touristaapp.utils;

import android.content.Context;
import android.util.Log;

import com.example.touristaapp.models.Event;
import com.example.touristaapp.models.Review;
import com.example.touristaapp.models.TouristAttraction;
import com.example.touristaapp.models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JsonReader {

    static String TAG = "JsonReader";
    private User user;

    public static List<TouristAttraction> readJsonFile(Context context, String fileName, String category) {
        Log.d("DATAREQUEST", "Data Request For: " + category);
        String json = null;
        List<TouristAttraction> touristAttractionList = new ArrayList<>();
        Gson gson = new Gson();

        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
            JSONArray touristAttractions = jsonObject.getJSONArray("tourist_attraction");
            Log.d("JSONARRAY", "readJsonFile= " + touristAttractions.toString());
            for (int i = 0; i < touristAttractions.length(); i++) {
                JSONObject attraction = touristAttractions.getJSONObject(i);
                TouristAttraction touristAttraction = gson.fromJson(attraction.toString(), TouristAttraction.class);
                touristAttractionList.add(touristAttraction);
            }
        } catch (JSONException e) {
            Log.d("JSONARRAY", "readJsonFile: Error reading json file", e);
            e.printStackTrace();
        }
        Log.d("JSONARRAY", "TouristAttractionData: " + touristAttractionList.toString());
        // Filter the data based on the category
        List<TouristAttraction> filteredList = new ArrayList<>();

        if (touristAttractionList != null && category != null) {
            try {
                filteredList = touristAttractionList.stream()
                        .filter(attraction -> category.equals(attraction.getCategory()))
                        .collect(Collectors.toList());
                Log.d("JSONARRAY", "FilteredData: " + filteredList.toString());
                return filteredList;
            } catch (Exception e) {
                Log.d("JSONARRAY", "readJsonFile: Error filtering data", e);
                return null;
            }
        } else {
            return touristAttractionList;
        }

    }

    public  User getUserData(Context context,  int userId) {
        Log.d("DATAREQUEST", "Data Request For: " + userId);
        String json = null;
        List<TouristAttraction> touristAttractionList = new ArrayList<>();
        Gson gson = new Gson();
        try {
            InputStream is = context.getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
            JSONArray touristAttractions = jsonObject.getJSONArray("tourist_attraction");
            Log.d("USERDATA", "readJsonFile= " + touristAttractions.toString());
            for (int i = 0; i < touristAttractions.length(); i++) {
                JSONObject attraction = touristAttractions.getJSONObject(i);
                TouristAttraction touristAttraction = gson.fromJson(attraction.toString(), TouristAttraction.class);
                touristAttractionList.add(touristAttraction);
            }
        } catch (JSONException e) {
            Log.d("USERDATA", "readJsonFile: Error reading json file", e);
            e.printStackTrace();
        }
        Log.d("USERDATA", "TouristAttractionData: " + touristAttractionList.toString());
        // Filter the data based on the category
        List<TouristAttraction> filteredAttractionList;
        List<Review> reviewList;
        List<Event> eventList;


        if (touristAttractionList != null ) {
            try {
                filteredAttractionList = touristAttractionList.stream()
                        .filter(attraction -> userId==attraction.getUser().getUserId())
                        .collect(Collectors.toList());

                reviewList = touristAttractionList.stream()
                        .flatMap(attraction -> attraction.getReviews().stream())
                        .filter(review -> userId==review.getUserId())
                        .collect(Collectors.toList());
                Log.d("USERDATA", "ReviewList: " + reviewList.size());
                eventList = touristAttractionList.stream()
                        .flatMap(attraction -> attraction.getEvents().stream())
                        .filter(event -> userId==event.getUserId())
                        .collect(Collectors.toList());
                if (!filteredAttractionList.isEmpty()) {
                    user = filteredAttractionList.get(0).getUser();
                    user.setTouristAttractions(filteredAttractionList);
                    user.setReviews(reviewList);
                    user.setEvents(eventList);
                }

                return user;
            } catch (Exception e) {
                Log.d("USERDATA", "readJsonFile: Error filtering data", e);
                return null;
            }
        } else {
            return null;
        }
    }
    public  TouristAttraction getAttractionData(Context context,  int attractionId) {
        String json = null;
        List<TouristAttraction> touristAttractionList = new ArrayList<>();
        Gson gson = new Gson();
        try {
            InputStream is = context.getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
            JSONArray touristAttractions = jsonObject.getJSONArray("tourist_attraction");
            Log.d("USERDATA", "readJsonFile= " + touristAttractions.toString());
            for (int i = 0; i < touristAttractions.length(); i++) {
                JSONObject attraction = touristAttractions.getJSONObject(i);
                TouristAttraction touristAttraction = gson.fromJson(attraction.toString(), TouristAttraction.class);
                touristAttractionList.add(touristAttraction);
            }
        } catch (JSONException e) {
            Log.d("USERDATA", "readJsonFile: Error reading json file", e);
            e.printStackTrace();
        }
        if (touristAttractionList != null ) {
            try {
                TouristAttraction attraction;
                attraction = touristAttractionList.stream()
                        .filter(attraction1 -> attractionId==attraction1.getAttractionId())
                        .collect(Collectors.toList()).get(0);
                return attraction;
            } catch (Exception e) {
                Log.d("USERDATA", "readJsonFile: Error filtering data", e);
                return null;
            }
        } else {
            return null;
        }
    }
}