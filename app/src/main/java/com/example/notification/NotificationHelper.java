package com.example.notification;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationHelper  extends MainActivity {
    static int NOTIFICATION_ID = 0;
    static void sendNotification(String title, String message, String CHANNEL_NAME, String CHANNEL_ID, int obrazek, MainActivity Obiekt, int style){


        Bitmap bitmap;
        bitmap = BitmapFactory.decodeResource(Obiekt.getResources(), obrazek);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            CharSequence name = CHANNEL_NAME;
            String description = "Opis Kanału Powiadomień";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notifiactionManager = Obiekt.getSystemService(NotificationManager.class);
            notifiactionManager.createNotificationChannel(channel);
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if(Obiekt.checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED){
                Obiekt.requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
                return;
            }
        }


        Intent intent = new Intent(Obiekt, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(Obiekt, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(Obiekt, CHANNEL_ID)
                .setSmallIcon(R.drawable.banan)

                .setContentText(message)

                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        switch (style){
            case 1:
                builder
                        .setContentTitle(title + NOTIFICATION_ID)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap));

        }
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(Obiekt);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
        NOTIFICATION_ID++;
    }


}
