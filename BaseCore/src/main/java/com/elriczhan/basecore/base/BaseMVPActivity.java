package com.elriczhan.basecore.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.elriczhan.basecore.ViewController;
import com.elriczhan.basecore.utils.LogUtil;
import com.elriczhan.basecore.utils.TypeUtil;


public abstract class BaseMVPActivity<P extends BasePresenter, M extends BaseModel> extends AppCompatActivity {
    //    public abstract class BaseMVPActivity<T extends BasePresenter, V extends BaseMVPView> extends AppCompatActivity {
//    public abstract class CoreBaseActivity<T extends CoreBasePresenter, E extends CoreBaseModel> extends SupportActivity {
    public P mPresenter;
    public M model;
    private View mRootView;
    private ViewController mViewController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootView = getRootView();
        setContentView(mRootView);
        mPresenter = TypeUtil.getType(this, 0);
        model = TypeUtil.getType(this, 1);
        if (this instanceof BaseMVPView)
            LogUtil.e(" presenter--- " + (mPresenter == null) + "----model---" + (model == null));
        mPresenter.attachVM(this, model);
        mViewController = new ViewController(mRootView);
    }

    protected abstract View getRootView();

    public void showLoading() {
        if (mViewController != null) {
            mViewController.showLoading();
        }
    }

    public void showError() {
        if (mViewController != null) {
            mViewController.showError();
        }
    }

    public void showOriginal() {
        if (mViewController != null) {
            mViewController.showOriginal();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachVM();
    }

}
