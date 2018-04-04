package com.example.elric.myapplication.mvp;

import android.app.Activity;
import android.os.SystemClock;

import com.example.elric.myapplication.mvp.contract.LoginContract;

public class loginPresenter extends LoginContract.LoginPresenter {
    @Override
    public void login(final Activity activity) {
        mView.ShowLoadingView();

        new Thread() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                mModel.login(mView.getUsername(), mView.getPassword(), new loginModel.LoginListener() {
                    @Override
                    public void onSuccess() {
                        mView.showResult("login success");
                        mView.ShowSuccessView();

                    }

                    @Override
                    public void onFailed() {
                        mView.showResult("try again!!!!");
                        mView.ShowErrorView(new Exception("try again!!!"));

                    }
                }, activity);
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
