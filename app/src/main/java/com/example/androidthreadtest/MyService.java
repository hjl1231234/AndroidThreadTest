package com.example.androidthreadtest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import static android.app.Notification.VISIBILITY_SECRET;

public class MyService extends Service {


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("abc", "onCreate	executed");


        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(this);
        //产生notification
        Notification notification = builder.setContentTitle("This	is	content	title")
                .setContentText("This	is	content	text")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.mipmap.ic_launcher))
                .setContentIntent(pi)
                .build();


        NotificationChannel channel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            channel = new NotificationChannel("1", "my_channel", NotificationManager.IMPORTANCE_DEFAULT);

        //设置提示灯
            channel.enableLights(true);
            channel.setLightColor(Color.green(1));
        //显示图标
            channel.canShowBadge();
            channel.setShowBadge(true);

            manager.createNotificationChannel(channel);
            builder.setChannelId("1");
        }
        manager.notify(1, notification);
        startForeground(1, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
