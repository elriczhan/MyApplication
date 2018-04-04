package com.example.elric.myapplication;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class Slider extends FrameLayout {
    private int mScreenWidth;
    private int mMenuRightPadding = 30;
    private int mMenuWidth;

    private ViewGroup menu;
    private ViewGroup content;

    public Slider(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScreenWidth = getResources().getDisplayMetrics().widthPixels;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        menu = (ViewGroup) getChildAt(0);
        content = (ViewGroup) getChildAt(1);
        // dp to px
        mMenuRightPadding = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, mMenuRightPadding, content
                        .getResources().getDisplayMetrics());

        mMenuWidth = mScreenWidth - mMenuRightPadding;
        menu.getLayoutParams().width = mMenuWidth;
        content.getLayoutParams().width = mScreenWidth;

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    private float downX;
    private float saveX;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX() + saveX;
                break;

            case MotionEvent.ACTION_MOVE:
                float moveX = ev.getX() - downX;
                if (moveX >= mMenuWidth) {
                    moveX = mMenuWidth;
                } else if (moveX < 0) {
                    moveX = 0;
                }
                content.setTranslationX(moveX);
                menu.setTranslationX(moveX - mMenuWidth);
                saveX = moveX;
                break;
            case MotionEvent.ACTION_UP:
                if (saveX < (mMenuWidth / 3)) {
                    if (saveX == -mMenuWidth) {
                        closeSlideMenu();
                    } else {
                        closeSlideMenu();
                    }
                } else {
                    openSlideMenu();
                }
        }

//        return super.onTouchEvent(ev);
        return true;
    }

    public void toggleSlideMenu() {
        if (saveX == 0) {
            openSlideMenu();
        } else {
            closeSlideMenu();
        }
    }

    //打开slideMenu
    public void openSlideMenu() {
        int s = (int) (saveX / mMenuWidth / 2 * 1000);
        ViewCompat.animate(content).translationX(mMenuWidth).setDuration(s).start();
        ViewCompat.animate(menu).translationX(0).setDuration(s).start();
        saveX = -mMenuWidth;
    }

    //关闭slideMenu
    public void closeSlideMenu() {
        int s = (int) Math.abs(saveX / mMenuWidth * 1000);
        ViewCompat.animate(content).translationX(0).setDuration(s).start();
        ViewCompat.animate(menu).translationX(-mMenuWidth).setDuration(s).start();
        saveX = 0;
    }

}