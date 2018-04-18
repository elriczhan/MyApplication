package com.elriczhan.basecore.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elriczhan.basecore.R;
import com.elriczhan.basecore.utils.LogUtil;
import com.elriczhan.basecore.utils.TypeUtil;
import com.elriczhan.basecore.view.ViewController;


public abstract class BaseMVPFragment<Presenter extends BasePresenter, Model extends BaseModel> extends Fragment implements BaseMVPView {
    public Presenter mPresenter;
    public Model model;
    private ViewGroup mRootView;
    private ViewController mViewController;
    private View mContent;
    private boolean showOnce = true;
    private boolean isCreated = false;
    private boolean isResume = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mContent = getRootView(inflater, container);
        mRootView = (ViewGroup) inflater.inflate(R.layout.layout_frame, container, false);
        mRootView.addView(mContent);
        mPresenter = TypeUtil.getType(this, 0);
        model = TypeUtil.getType(this, 1);
//        if (this instanceof BaseMVPView)
//            LogUtil.e(" presenter--- " + (mPresenter == null) + "----model---" + (model == null));
        mPresenter.attachVM(this, model);
        findView(mRootView);
        return mRootView;
    }

    protected abstract void findView(View mRootView);


    @Override
    public void onResume() {
        super.onResume();
        isResume = true;
        if (getUserVisibleHint() && showOnce && isCreated) {
            showOnce = false;
            loadData();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        isResume = false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint() && showOnce && isCreated && isResume) {
            showOnce = false;
            loadData();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        mViewController = new ViewController(mContent, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowLoadingView();
                loadData();
            }
        });
        mViewController.showLoading();
        isCreated = true;
        super.onActivityCreated(savedInstanceState);
    }

    protected abstract void loadData();

    protected abstract View getRootView(LayoutInflater inflater, ViewGroup container);


    @Override
    public void ShowLoadingView() {
        if (mViewController != null) {
            mViewController.showLoading();
        }
    }

    public void showEmpty() {
        if (mViewController != null) {
            mViewController.showEmpty();
        }
    }

    public void showEmpty(String msg) {
        if (mViewController != null) {
            mViewController.showEmpty(msg);
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

    protected abstract View getContentView(View mRootView);

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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null)
            mPresenter.detachVM();
    }

}
