package com.example.xinshei.myapplication.uninstall;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;

import java.io.File;

public class UnInstallServer extends Service {


    SDCardListener[] listenners;

    @SuppressLint("SdCardPath")
    @Override
    public void onCreate() {
        SDCardListener[] listenners = {
                new SDCardListener("/data/data/com.example.xinshei.myapplication", this),
                new SDCardListener(Environment.getExternalStorageDirectory() + File.separator + "test.txt", this)};
        this.listenners = listenners;

        for (SDCardListener listener : listenners) {
            listener.startWatching();
        }

        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "test.txt");

        if (file.exists())
            file.delete();
    }

    @Override
    public void onDestroy() {
        for (SDCardListener listener : listenners) {
            listener.stopWatching();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
