package com.example.xinshei.myapplication.mvp;

import android.os.SystemClock;

import com.example.xinshei.myapplication.mvp.interfacesss.ILoginView;
import com.example.xinshei.myapplication.mvp.interfacesss.IloginPressenter;

public class loginPresenter extends IloginPressenter {
    private final ILoginView loginView;
    private final loginModel loginModel;
    //    public abstract void login();

    public loginPresenter(ILoginView loginView) {
        this.loginView = loginView;
        loginModel = new loginModel();
    }

    @Override
    public void login() {
        loginView.showLoading();
        new Thread() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                if (loginModel.login(loginView.getUsername(), loginView.getPassword())) {
                    loginView.showResult("login success");
                } else {
                    loginView.showResult("try again!!!!");
                }
                loginView.hideLoding();
            }
        }.start();

    }
}
