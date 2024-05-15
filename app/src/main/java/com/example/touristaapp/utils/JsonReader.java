package com.example.touristaapp.utils;

import android.content.Context;
import android.util.Log;

import com.example.touristaapp.models.TouristAttraction;
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
}