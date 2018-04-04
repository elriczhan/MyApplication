/*
 * Copyright (C) 2013 The Android Open Source Project
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.elric.myapplication;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;

import java.text.DateFormat;
import java.util.Date;

public class notificationA extends Activity {
    /**
     * This sample demonstrates notifications with custom content views.
     * <p>
     * <p>On API level 16 and above a big content view is also defined that is used for the
     * 'expanded' notification. The notification is created by the NotificationCompat.Builder.
     * The expanded content view is set directly on the {@link android.app.Notification} once it has been build.
     * (See {@link android.app.Notification#bigContentView}.) </p>
     * <p>
     * <p>The content views are inflated as {@link android.widget.RemoteViews} directly from their XML layout
     * definitions using {@link android.widget.RemoteViews#RemoteViews(String, int)}.</p>
     */
    private void createNotification() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        //Create Intent to launch this Activity again if the notification is clicked.
        //intent  设置这些属性来开启app， 主要是不能 启动的页面 设置他的launchMode要不然，会一直打开，这样的方式参数也不能弄。
        //或许发送在正价一个PendingIntent 发送一个延迟广播，作为参数的传递。或者打开app页面
        Intent i = new Intent();
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        i.setAction(Intent.ACTION_MAIN);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//        i.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        i.setComponent(new ComponentName(this, RVMenu.class));
//        PendingIntent intent = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent i2 = new Intent();
        i2.setComponent(new ComponentName(this, notificationA.class));

        Intent[] intents = new Intent[]{i, i2};
        PendingIntent activities = PendingIntent.getActivities(this, 0, intents, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(activities);

        // Sets the ticker text
        builder.setTicker(getResources().getString(R.string.custom_notification));

        // Sets the small icon for the ticker
        builder.setSmallIcon(R.mipmap.ic_launcher);

        // Cancel the notification when clicked
        builder.setAutoCancel(true);

        // Build the notification
        Notification notification = builder.build();

        // Inflate the notification layout as RemoteViews
        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.notification);

        // Set text on a TextView in the RemoteViews programmatically.
        final String time = DateFormat.getTimeInstance().format(new Date()).toString();
        final String text = getResources().getString(R.string.collapsed, time);
        contentView.setTextViewText(R.id.textView, text);
 
        /* Workaround: Need to set the content view here directly on the notification.
         * NotificationCompatBuilder contains a bug that prevents this from working on platform
         * versions HoneyComb.
         * See https://code.google.com/p/android/issues/detail?id=30495
         */
        notification.contentView = contentView;

        // Add a big content view to the notification if supported.
        // Support for expanded notifications was added in API level 16.
        // (The normal contentView is shown when the notification is collapsed, when expanded the
        // big content view set here is displayed.)
        if (Build.VERSION.SDK_INT >= 16) {
            // Inflate and set the layout for the expanded notification view
            RemoteViews expandedView =
                    new RemoteViews(getPackageName(), R.layout.notification_expanded);
            notification.bigContentView = expandedView;
        }

        // START_INCLUDE(notify)
        // Use the NotificationManager to show the notification
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(0, notification);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_main);
    }

    /**
     * Create and show a notification with a custom layout.
     * This callback is defined through the 'onClick' attribute of the
     * 'Show Notification' button in the XML layout.
     *
     * @param v
     */
    public void showNotificationClicked(View v) {
        createNotification();
    }
}