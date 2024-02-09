package com.beknumonov.noteapp2;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.beknumonov.noteapp2.model.News;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

public class MyFirebaseMessagingServices extends FirebaseMessagingService {
    private static String CHANNEL_ID = "NOTE_APP_CHANNEL_ID_01";

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);

        Log.d("Notification: title", message.getData().toString());
        if (message.getNotification() != null) {
            Log.d("Notification: title", message.getNotification().getTitle());
            Log.d("Notification: body", message.getNotification().getBody());
        }

        // showing custom notification

        String title = "Note App Notification";
        String body = "Note App Body";
        if (message.getNotification() != null) {
            title = message.getNotification().getTitle();
            body = message.getNotification().getBody();
        } else {
            title = message.getData().get("title");
            body = message.getData().get("body");
        }

        NotificationChannel channel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            channel = new NotificationChannel(CHANNEL_ID, "Channel", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Description");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }


        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALL);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_splash);
        builder.setColor(getColor(R.color.colorPrimary)).setContentTitle(title)
                .setContentText(body).setSound(sound).setPriority(NotificationCompat.PRIORITY_HIGH);

        if (message.getData().containsKey("news")) {
            News news = new Gson().fromJson(message.getData().get("news"), News.class);
            Intent intent = new Intent(this, NewsDetailsActivity.class);
            intent.putExtra("news", news);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE|PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);
        }


        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManagerCompat.notify(100, builder.build());


    }
}
