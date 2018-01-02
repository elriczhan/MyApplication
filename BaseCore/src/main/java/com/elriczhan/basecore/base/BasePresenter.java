package com.elriczhan.basecore.base;

/**
 * Created by xinshei on 17/4/20.
 */

public abstract class BasePresenter<V, M> {
    public V mView;
    public M mModel;

    public void attachVM(V view, M model) {
        this.mView = view;
        this.mModel = model;
    }

    public void detachVM() {
        mView = null;
        mModel = null;
    }
}
