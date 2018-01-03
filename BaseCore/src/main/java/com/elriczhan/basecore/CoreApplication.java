package com.elriczhan.basecore;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

/**
 * Created by xinshei on 2018/1/3.
 */

public class CoreApplication extends MultiDexApplication {
    private static CoreApplication coreApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        coreApplication = this;
    }

    public static Context getAppContext() {
        return coreApplication.getApplicationContext();
    }

}
