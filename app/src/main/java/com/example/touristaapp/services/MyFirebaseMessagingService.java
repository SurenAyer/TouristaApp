package com.example.touristaapp.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.touristaapp.R;
import com.example.touristaapp.activities.MainActivity;
import com.example.touristaapp.activities.ProfileActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Objects;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("MyFirebaseMessaging", "From: " + remoteMessage.getFrom());

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d("MyFirebaseMessaging", "Message Notification Body: " + remoteMessage.getNotification().getBody());
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