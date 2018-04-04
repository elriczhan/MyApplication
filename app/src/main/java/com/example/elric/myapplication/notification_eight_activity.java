package com.example.elric.myapplication;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.elriczhan.basecore.utils.LogUtil;
import com.elriczhan.basecore.utils.SharePrefUtil;

import java.io.IOException;
import java.util.Random;

/**
 * Created by xinshei on 2018/3/12.
 */

public class notification_eight_activity extends AppCompatActivity {
    private String CHANNEL_ID = "channel_id";
    private Random random = new Random();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eight_activity);
        findViewById(R.id.noti_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNoti();
                LogUtil.e("asdasdasd");
                SharePrefUtil.saveString(notification_eight_activity.this, "asd", "asd");
            }
        });
        LogUtil.e(SharePrefUtil.getString(this, "asd", "nothing!!!"));
        findViewById(R.id.app_data).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearData();
            }
        });


    }

    private void clearData() {
        String cmd = "pm clear " + getPackageName();
//        String cmd = "reboot";
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void createNoti() {


        Intent i = new Intent(this, notification_eight_activity.class);
        if (Build.VERSION.SDK_INT >= 26) {

//            PendingIntent intent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);

            TaskStackBuilder tsb = TaskStackBuilder.create(this);
//            tsb.addParentStack(notification_eight_activity.class);
            tsb.addNextIntentWithParentStack(i);
            PendingIntent pendingIntent = tsb.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

            Notification.Builder mBuilder = new Notification.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("My notification")
                    .setContentIntent(pendingIntent)
                    .setContentText("Much longer text that cannot fit one line...")
                    .setStyle(new Notification.BigTextStyle()
                            .bigText("Much longer text that cannot fit one line..."));

            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "asd", importance);
            channel.setDescription("setDescription");
            // Register the channel with the system
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);


// notificationId is a unique int for each notification that you must define
            notificationManager.notify(random.nextInt(), mBuilder.build());
        } else {
            TaskStackBuilder tsb = TaskStackBuilder.create(this);
//            tsb.addParentStack(notification_eight_activity.class);
            tsb.addNextIntentWithParentStack(i);
            PendingIntent pendingIntent = tsb.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(this, "channel")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("My notification")
                    .setContentIntent(pendingIntent)
                    .setContentText("Much longer text that cannot fit one line...");
            NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            nm.notify(random.nextInt(), notificationCompat.build());
        }

    }
}

