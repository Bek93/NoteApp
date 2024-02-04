package com.beknumonov.noteapp2;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingServices extends FirebaseMessagingService {
    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);

        Log.d("Notification: title", message.getData().toString());
        //Log.d("Notification: title", message.getNotification().getTitle());
        //Log.d("Notification: body", message.getNotification().getBody());


    }
}
