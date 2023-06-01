package com.example.newanimals.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.graphics.drawable.IconCompat;

import com.example.newanimals.activity.AdsActivity;

public class NotificationHelper {

    public static final String CHANEL_ID = "id";
    public static final String CHANEL_NAME = "name";
    public static final String CHANEL_DESCRIPTION = "descript";

    public static void showNotification(Context context, String title, String message, String image){
        Intent intent = new Intent(context, AdsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANEL_ID)
                .setSmallIcon(IconCompat.createWithContentUri(image))
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANEL_ID,CHANEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANEL_DESCRIPTION);
            notificationManager.createNotificationChannel(channel);
        }

    }
}
