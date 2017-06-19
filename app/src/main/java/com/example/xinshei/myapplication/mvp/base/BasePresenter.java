package com.example.xinshei.myapplication.mvp.base;

/**
 * Created by xinshei on 17/4/20.
 */

public abstract class BasePresenter<T> {
    public T mView;

    public void attach(T mView) {
        this.mView = mView;
    }

    public void dettach() {
        mView = null;
    }
}
