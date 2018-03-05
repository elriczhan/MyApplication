package com.elriczhan.basecore.base;

import com.elriczhan.basecore.RxManager;

/**
 * Created by xinshei on 17/4/20.
 */

public abstract class BasePresenter<View extends BaseMVPView, Model extends BaseModel> {
    public View mView;
    public Model mModel;
    public RxManager rxManager;

    public void attachVM(View view, Model model) {
        this.mView = view;
        this.mModel = model;
        rxManager = new RxManager();
    }


    public void detachVM() {
        rxManager.clear();
        mView = null;
        mModel = null;
    }
}
