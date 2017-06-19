package com.example.xinshei.myapplication.uninstall;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.FileObserver;
import android.os.IBinder;
import android.util.Log;

import java.io.File;

public class SDCardListenSer extends Service {
    SDCardListener[] listenners;

    @SuppressLint("SdCardPath")
    @Override
    public void onCreate() {
        SDCardListener[] listenners = {
                new SDCardListener("/data/data/com.example.uninstalldemos", this),
                new SDCardListener(Environment.getExternalStorageDirectory() + File.separator + "1.txt", this)};
        this.listenners = listenners;

        Log.i("onEvent", "=========onCreate============");
        for (SDCardListener listener : listenners) {
            listener.startWatching();
        }

        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "1.txt");
        Log.i("onEvent", "dddddddddddddddddddddd nCreate============");
        if (file.exists())
            file.delete();
        /*try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
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

class SDCardListener extends FileObserver {
    private String mPath;
    private final Context mContext;

    public SDCardListener(String parentpath, Context ctx) {
        super(parentpath);
        this.mPath = parentpath;
        this.mContext = ctx;
    }

    @Override
    public void onEvent(int event, String path) {
        int action = event & FileObserver.ALL_EVENTS;
        switch (action) {

            case FileObserver.DELETE:
                Log.i("onEvent", "delete path: " + mPath + File.separator + path);
                //openBrowser();
                break;

            case FileObserver.MODIFY:
                Log.i("onEvent", "更改目录" + mPath + File.separator + path);
                break;

            case FileObserver.CREATE:
                Log.i("onEvent", "创建文件" + mPath + File.separator + path);
                break;

            default:
                break;
        }
    }

    protected void openBrowser() {
        Uri uri = Uri.parse("http://aoi.androidesk.com");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        mContext.startActivity(intent);
    }

    public void exeShell(String cmd) {
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}