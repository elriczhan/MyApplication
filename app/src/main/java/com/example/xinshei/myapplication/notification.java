package com.example.xinshei.myapplication;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xinshei on 17/2/3.
 */

public class notification extends Activity {

    @Bind(R.id.wave)
    WaveView mWaveView;

    private int mBorderColor = Color.parseColor("#44FFFFFF");
    private int mBorderWidth = 10;

    @OnClick(R.id.noti)
    void click(View v) {
        Log.e("asd", "button try");
        Resources res = getApplicationContext().getResources();
        Bitmap img = BitmapFactory.decodeResource(res, R.mipmap.ic_launcher);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(img)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");

//        NotificationCompat.InboxStyle inboxStyle =
//                new NotificationCompat.InboxStyle();
//        String[] events = new String[6];
//// Sets a title for the Inbox in expanded layout
//        inboxStyle.setBigContentTitle("Hello World!");
//// Moves events into the expanded layout
//        for (int i = 0; i < events.length; i++) {
//
//            inboxStyle.addLine(events[i]);
//            inboxStyle.setSummaryText("My notification");
//        }
//// Moves the expanded layout object into the notification object.
//        mBuilder.setStyle(inboxStyle);

        NotificationManager mNoti = (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        Random r = new Random();
        mNoti.notify(r.nextInt(), mBuilder.build());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noti);
        ButterKnife.bind(this);


    }
}
