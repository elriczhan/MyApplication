package com.example.xinshei.myapplication.mvp.contract;

import com.example.xinshei.myapplication.mvp.base.BaseMVPView;
import com.example.xinshei.myapplication.mvp.base.BaseModel;
import com.example.xinshei.myapplication.mvp.base.BasePresenter;
import com.example.xinshei.myapplication.mvp.loginModel;

public interface LoginContract {

    abstract class LoginPresenter extends BasePresenter<LoginContract.ILoginView, LoginContract.IloginModel> {
        public abstract void login();
    }

    interface IloginModel extends BaseModel {
        void login(String username, String password, final loginModel.LoginListener listener);
    }

    interface ILoginView extends BaseMVPView {
        String getUsername();

        String getPassword();

        void showResult(String result);
    }
}
