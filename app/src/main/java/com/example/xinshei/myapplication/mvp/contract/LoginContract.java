package com.example.xinshei.myapplication.mvp.contract;

import com.elriczhan.basecore.base.BaseMVPView;
import com.elriczhan.basecore.base.BaseModel;
import com.elriczhan.basecore.base.BasePresenter;
import com.example.xinshei.myapplication.mvp.loginModel;

public interface LoginContract {

    abstract class LoginPresenter extends BasePresenter<ILoginView, IloginModel> {
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
