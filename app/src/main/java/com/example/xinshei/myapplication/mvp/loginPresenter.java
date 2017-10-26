package com.example.xinshei.myapplication.mvp;

import android.os.SystemClock;

import com.example.xinshei.myapplication.mvp.contract.LoginContract;

public class loginPresenter extends LoginContract.LoginPresenter {
    @Override
    public void login() {
        mView.showLoading();
        new Thread() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                mModel.login(mView.getUsername(), mView.getPassword(), new loginModel.LoginListener() {
                    @Override
                    public void onSuccess() {
                        mView.showResult("login success");
                        mView.hideLoading();
                    }

                    @Override
                    public void onFailed() {
                        mView.showResult("try again!!!!");
                        mView.hideLoading();
                    }
                });
//                if (mModel.login()) {
//                    mView.showResult("login success");
//                } else {
//                    mView.showResult("try again!!!!");
//                }
//                mView.hideLoading();
            }
        }.start();

    }
}
