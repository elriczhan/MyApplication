package com.example.xinshei.myapplication.mvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.xinshei.myapplication.mvp.utils.TypeUtil;

public abstract class BaseMVPActivity<P extends BasePresenter, M extends BaseModel> extends AppCompatActivity {
    //    public abstract class BaseMVPActivity<T extends BasePresenter, V extends BaseMVPView> extends AppCompatActivity {
//    public abstract class CoreBaseActivity<T extends CoreBasePresenter, E extends CoreBaseModel> extends SupportActivity {
    public P presenter;
    public M model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = TypeUtil.getType(this, 0);
        model = TypeUtil.getType(this, 1);
        if (this instanceof BaseMVPView)
            Log.e("asd", " presenter--- " + (presenter == null) + "----model---" + (model == null));
        presenter.attachVM(this, model);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null)
            presenter.detachVM();
    }

}
