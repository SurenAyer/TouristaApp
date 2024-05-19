package com.example.touristaapp.services;

import android.content.SharedPreferences;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Objects;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMessagingServTAG";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            // Store the message instead of showing it
            SharedPreferences sharedPreferences = getSharedPreferences("Notification", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("newMessageReceived", true);
            editor.putString("Title", remoteMessage.getNotification().getTitle());
            editor.putInt("AttractionId", Integer.parseInt(Objects.requireNonNull(remoteMessage.getNotification().getBody())));
            editor.apply();
        }
    }
 }