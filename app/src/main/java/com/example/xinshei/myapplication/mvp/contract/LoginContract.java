package com.example.xinshei.myapplication.mvp.contract;

import com.example.xinshei.myapplication.mvp.base.BaseMVPView;
import com.example.xinshei.myapplication.mvp.base.BaseModel;
import com.example.xinshei.myapplication.mvp.base.BasePresenter;

public interface LoginContract {

    abstract class LoginPresenter extends BasePresenter<LoginContract.ILoginView, LoginContract.IloginModel> {
        public abstract void login();
    }

    interface IloginModel extends BaseModel {
        boolean login(String username, String password);
    }

    interface ILoginView extends BaseMVPView {
        String getUsername();

        String getPassword();

        void showResult(String result);
    }
}
