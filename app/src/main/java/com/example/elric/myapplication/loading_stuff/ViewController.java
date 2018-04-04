package com.example.elric.myapplication.loading_stuff;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.elric.myapplication.R;

/**
 * Created by xinshei on 2017/9/27.
 */

public class ViewController {
    private View mOriginalView;
    private View mCurrentView;
    private ViewGroup mParent;
    private ViewGroup.LayoutParams mParams;
    private int mIndex;
    private Context mContext;

    public ViewController(View original) {
        this.mOriginalView = original;
        init();
    }

    private void showView(View view) {
        if (view != mCurrentView) {
            mParent.removeViewAt(mIndex);
            mParent.addView(view, mIndex, mParams);
            mCurrentView = view;
        }
    }

    public void showOriginal() {
        showView(mOriginalView);
    }

    private void init() {
        mContext = mOriginalView.getContext();
        mParent = (ViewGroup) mOriginalView.getParent();
        mParams = mOriginalView.getLayoutParams();
        int childCount = mParent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (mParent.getChildAt(i) == mOriginalView) {
                mIndex = i;
                break;
            }
        }
    }

    public void showLoading() {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.loading_view, mParent, false);
        showView(inflate);
    }

    public void showError() {
        LinearLayout layout  = new LinearLayout(mContext);
        layout.setGravity(Gravity.CENTER);
        TextView textView = new TextView(mContext);
        textView.setText("error text");
        layout.addView(textView);
        showView(layout);
    }

}
