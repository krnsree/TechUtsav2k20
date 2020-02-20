package com.example.techutsav;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.techutsav.fragments.MainActivityNew;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static android.content.Context.NOTIFICATION_SERVICE;

public class Firebase extends FirebaseMessagingService {


    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("TOKEN: ", s);
        //dU7o2uVJsr0:APA91bGcOTVsz6oTwV8lL-Q5OaOXqC7R0d-UNh8MqIKt0ENeIAYRYHrIOntd0eg9G4XOHjPMlgTI6C7BF5pXQHCbhV-jyKsB8Kk-UVc2mhSJj7kLAZRhnkOYIwwzGGfTsGT7PQFKapRU

    }

    @Override
    public void onMessageReceived(RemoteMessage message) {
        super.onMessageReceived(message);
        Log.e("msg", "onMessageReceived: " + message.getData().get("message"));
        Intent intent = new Intent(this, MainActivityNew.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String channelId = "Default";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.logo)
                .setAutoCancel(true)

                .setContentTitle(message.getNotification().getTitle())
                .setContentText(message.getNotification().getBody()).setAutoCancel(true).setContentIntent(pendingIntent);
        ;
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }
        manager.notify(0, builder.build());
        //sendMyNotification(message.getNotification().getBody());
    }
}
