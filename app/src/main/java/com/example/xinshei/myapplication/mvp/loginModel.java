package com.example.xinshei.myapplication.mvp;

import android.os.SystemClock;
import android.text.TextUtils;

import com.example.xinshei.myapplication.mvp.contract.LoginContract;

/**
 * Created by xinshei on 17/4/20.
 */

public class loginModel implements LoginContract.IloginModel {


    @Override
    public void login(final String username, final String password, final LoginListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                if (TextUtils.equals(username, "123") && TextUtils.equals(password, "123")) {
                    listener.onSuccess();
                } else {
                    listener.onFailed();
                }

            }
        }).start();
    }

    public interface LoginListener {
        void onSuccess();

        void onFailed();
    }
}
