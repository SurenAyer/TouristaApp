package com.example.touristaapp.services;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Objects;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMessagingServTAG";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        // Check if message contains a notification payload.
        Log.d(TAG, "Got Message: " + remoteMessage.getFrom());

        Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        Log.d(TAG, "Message Notification Title: " + remoteMessage.getNotification().getTitle());


        try {
            // Store the message instead of showing it
            SharedPreferences sharedPreferences1 = getSharedPreferences("NotificationMessage", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences1.edit();
            editor.putBoolean("newMessageReceived", true);
            editor.putString("Title", remoteMessage.getNotification().getTitle());

            editor.putString("AttractionId", remoteMessage.getNotification().getBody());

            editor.apply();
            Log.d(TAG, "From: " + sharedPreferences1.getBoolean("newMessageReceived", false));
            Log.d(TAG, "From: " + sharedPreferences1.getString("Title", ""));
            Log.d(TAG, "Message Notification Body: " + sharedPreferences1.getString("AttractionId", ""));
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        } catch (Exception e) {
            Log.d(TAG, "Error: " + e.getMessage());
        }

    }
}