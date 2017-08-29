package com.example.xinshei.myapplication;

import android.app.Application;
import android.util.Log;

import com.example.xinshei.myapplication.Dao.DaoMaster;
import com.example.xinshei.myapplication.Dao.DaoSession;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

//import cat.ereza.customactivityoncrash.config.CaocConfig;

/**
 * Created by xinshei on 17/4/6.
 */

public class app extends Application {
    public static app app;
    public static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        app = this;
//        CrashHandler.getInstance().init(this);
//        CaocConfig.Builder.create()
//                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //default: CaocConfig.BACKGROUND_MODE_SHOW_CUSTOM
//                .enabled(true) //default: true
//                .showErrorDetails(false) //default: true
//                .showRestartButton(true) //default: true
//                .trackActivities(true) //default: false
//                .minTimeBetweenCrashesMs(2000) //default: 3000
//                .errorDrawable(R.mipmap.ic_launcher) //default: bug image
//                .restartActivity(radarActivity2.class) //default: null (your app's launch activity)
////                .errorActivity(radarActivity2.class) //default: null (default error activity)
////                .eventListener(new YourCustomEventListener()) //default: null
//                .apply();
        Log.e("asd", "start ---------");
        PushAgent.getInstance(this).onAppStart();

        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.setMessageChannel("TEST");
//注册推送服务，每次调用register方法都会回调该接口

        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                Log.e("asd", deviceToken + "---------");
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.e("asd", "fail ---------");
                Log.e("asd", s);
                Log.e("asd", s1);
            }
        });

        /**
         * green dao stuff
         */
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(getApplicationContext(), "dao.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
//        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getEncryptedWritableDb("password"));
        daoSession = daoMaster.newSession();
        //如果实体类有更新，那么要调用 daoSession.clear() 清除缓存，才能得到更新。
        daoSession.clear();

    }


}
