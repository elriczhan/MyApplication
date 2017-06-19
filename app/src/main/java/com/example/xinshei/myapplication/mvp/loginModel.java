package com.example.xinshei.myapplication.mvp;

import android.text.TextUtils;

import com.example.xinshei.myapplication.mvp.interfacesss.IloginModel;

/**
 * Created by xinshei on 17/4/20.
 */

public class loginModel implements IloginModel {
    @Override
    public boolean login(String username, String password) {
        return TextUtils.equals(username, "123") && TextUtils.equals(password, "123");
    }
}
