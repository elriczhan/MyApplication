package com.example.xinshei.myapplication.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class DragCardView extends ViewGroup {

    private int padding = 10;
    private int dy;
    private int disy;

    public DragCardView(Context context) {
        this(context, null);
    }

    public DragCardView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public DragCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
//            MarginLayoutParams layoutParams = (MarginLayoutParams) child.getLayoutParams();
//            Log.e("asd", "layoutParams" + layoutParams.topMargin);
//            measureChildWithMargins(child, widthMeasureSpec, layoutParams.rightMargin, heightMeasureSpec, layoutParams.bottomMargin);
        }
        View children = getChildAt(0);
        ViewPerSize = children.getMeasuredHeight() / 2;
        this.childCount = getChildCount() - 1;
        totalSize = this.childCount * ViewPerSize;

    }

    private int ViewPerSize = 50;
    private int totalSize = 0;

    private int sDistance = 0;
    private int childCount = 0;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int viewTop = getPaddingTop();
        int num = (int) ((sDistance * 1f / totalSize) * childCount);
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
//        Log.e("asd", sDistance + "s : " + totalSize + " t " + childCount + " num " + num);
        for (int i = 0; i <= childCount; i++) {
            View children = getChildAt(i);
            int cHeight = children.getMeasuredHeight();
            if (children.getVisibility() != View.GONE) {
                if (i == 0) {
                    children.layout(l+paddingLeft, viewTop, r-paddingRight, viewTop + cHeight);
                } else {
                    if (i >= (childCount - num)) {
                        int move = sDistance - (childCount - i) * ViewPerSize;
                        viewTop += ViewPerSize;
                        int showtop = viewTop + move;
                        children.layout(l+paddingLeft, showtop, r-paddingRight, showtop + cHeight);
                    } else {
                        viewTop += ViewPerSize;
                        children.layout(l+paddingLeft, viewTop, r-paddingRight, viewTop + cHeight);
                    }
                }

            }
        }
    }

    private boolean intercept = false;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dy = (int) e.getY();
                intercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                intercept = true;
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
            case MotionEvent.ACTION_CANCEL:
                intercept = true;
                break;
        }
        return intercept || super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dy = (int) e.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int my = (int) e.getY();
                disy = my - dy;
                dy = my;
                sDistance += disy;
                if (sDistance < 0) {
                    sDistance = 0;
                } else if (sDistance > totalSize) {
                    sDistance = totalSize;
                }
                requestLayout();
                break;
            case MotionEvent.ACTION_UP:
//                final VelocityTracker velocityTracker = VelocityTracker.obtain();
//                //计算滑动速度,参数1000表示以1000毫秒为单位计算，即以秒为单位
//                velocityTracker.computeCurrentVelocity(1000);
//                int velocityX = (int) velocityTracker.getXVelocity();
//                velocityTracker.recycle();
                break;
        }
        return true;
    }
}
