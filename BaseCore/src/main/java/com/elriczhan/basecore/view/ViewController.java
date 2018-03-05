package com.elriczhan.basecore.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elriczhan.basecore.R;
import com.elriczhan.basecore.utils.LogUtil;

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
    private View.OnClickListener onClickListener;
    public static String emptyDataText = "Empty Data";
    public static String ErrorDataText = "";
    private LinearLayout emptyLayout;
    private LinearLayout errorLayout;
    private View loadingLayout;

    public ViewController(View original, View.OnClickListener onClickListener) {
        this.mOriginalView = original;
        this.onClickListener = onClickListener;
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

    public void showEmpty() {
        if (emptyLayout == null) {
            emptyLayout = new LinearLayout(mContext);
            emptyLayout.setGravity(Gravity.CENTER);
            TextView textView = new TextView(mContext);
            textView.setText(emptyDataText);
            textView.setOnClickListener(onClickListener);
            emptyLayout.addView(textView);
        }
        showView(emptyLayout);
    }

    public void showLoading() {
        if (loadingLayout == null) {
            loadingLayout = LayoutInflater.from(mContext).inflate(R.layout.loading_view, mParent, false);
        }
        showView(loadingLayout);
    }

    public void showError(Throwable e) {
        if (errorLayout == null) {
            errorLayout = new LinearLayout(mContext);
            errorLayout.setGravity(Gravity.CENTER);
            TextView textView = new TextView(mContext);
            textView.setText(TextUtils.isEmpty(ErrorDataText) ? e.toString() : ErrorDataText);
            textView.setOnClickListener(onClickListener);
            textView.setId(R.id.text);
            errorLayout.addView(textView);
        } else {
            TextView textView = errorLayout.findViewById(R.id.text);
            textView.setText(TextUtils.isEmpty(ErrorDataText) ? e.toString() : ErrorDataText);
        }
        LogUtil.e(e.toString());
        showView(errorLayout);
    }

}
