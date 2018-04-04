package com.example.elric.myapplication.loading_stuff;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public abstract class BaseView extends AppCompatActivity {

    public View mRootView;
    private View loadingView;
    private ViewGroup.LayoutParams layoutParams;
    private ViewGroup parent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootView = getView();
        setContentView(mRootView);
        showLoading();
    }

    public void showLoading() {
        parent = (ViewGroup) mRootView.getParent();
        layoutParams = mRootView.getLayoutParams();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        parent.addView(getLoadingView());
    }

    public abstract View getView();

    public View getLoadingView() {
        TextView view = new TextView(this);
        view.setText("test loading");
        view.setLayoutParams(layoutParams);
        return view;
    }
}
