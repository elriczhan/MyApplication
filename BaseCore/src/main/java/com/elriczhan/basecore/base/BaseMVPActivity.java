package com.elriczhan.basecore.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.elriczhan.basecore.utils.LogUtil;
import com.elriczhan.basecore.utils.TypeUtil;
import com.elriczhan.basecore.view.ViewController;


public abstract class BaseMVPActivity<Presenter extends BasePresenter, Model extends BaseModel> extends AppCompatActivity implements BaseMVPView {
    public Presenter mPresenter;
    public Model model;
    private ViewController mViewController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getRootView());
        mPresenter = TypeUtil.getType(this, 0);
        model = TypeUtil.getType(this, 1);
//        if (this instanceof BaseMVPView)
//            LogUtil.e(" presenter--- " + (mPresenter == null) + "----model---" + (model == null));
        mPresenter.attachVM(this, model);
        mViewController = new ViewController(getContentView(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowLoadingView();
                loadData();
            }
        });
        initView();
        ShowLoadingView();
        loadData();
    }

    protected abstract void initView();

    protected abstract void loadData();

    protected abstract View getContentView();

    protected abstract int getRootView();

    @Override
    public void ShowLoadingView() {
        if (mViewController != null) {
            mViewController.showLoading();
        }
    }

    @Override
    public void ShowErrorView(Exception e) {
        showError(e);
    }

    @Override
    public void ShowSuccessView() {
        showOriginal();
    }

    public void showError(Throwable e) {
        if (mViewController != null) {
            mViewController.showError(e);
        }
    }

    public void showOriginal() {
        if (mViewController != null) {
            mViewController.showOriginal();
        } else {
            LogUtil.e("show original error");
        }
    }

    public void showEmpty()
    {
        if (mViewController != null) {
            mViewController.showEmpty();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachVM();
    }

}
