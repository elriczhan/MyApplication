package com.example.elric.myapplication.mvp.contract;

import android.app.Activity;

import com.elriczhan.basecore.base.BaseMVPView;
import com.elriczhan.basecore.base.BaseModel;
import com.elriczhan.basecore.base.BasePresenter;
import com.example.elric.myapplication.mvp.loginModel;

public interface LoginContract {

    abstract class LoginPresenter extends BasePresenter<ILoginView, IloginModel> {
        public abstract void login(Activity activity);
    }

    interface IloginModel extends BaseModel {
        void login(String username, String password, final loginModel.LoginListener listener, Activity activity);
    }

    interface ILoginView extends BaseMVPView {
        String getUsername();

        String getPassword();

        void showResult(String result);
    }
}
