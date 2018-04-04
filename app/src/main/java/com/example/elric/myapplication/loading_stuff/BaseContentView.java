package com.example.elric.myapplication.loading_stuff;


import android.view.View;

public class BaseContentView {

    private View mSuccessView;
    private View mFailedView;
    private View mLoadingView;
    private View mEmptyView;

    public void setSuccessView(View view) {
        mSuccessView = view;
    }

    public View getSuccessView() {
        return mSuccessView;
    }

}
