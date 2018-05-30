package com.example.elric.myapplication;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by xinshei on 2018/5/28.
 */

public class CustomButtonProgress4 extends ViewGroup {

    private ImageView imageView;
    private CustomButtonProgress3 customButtonProgress3;

    public CustomButtonProgress4(Context context) {
        super(context);
        init(context);
    }

    public CustomButtonProgress4(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomButtonProgress4(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

        customButtonProgress3 = new CustomButtonProgress3(context);
        addView(customButtonProgress3);

        imageView = new ImageView(context);
        imageView.setBackground(getResources().getDrawable(R.drawable.rocket));
        AnimationDrawable anim = (AnimationDrawable) imageView.getBackground();
        anim.start();
        addView(imageView);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        //测量并保存layout的宽高(使用getDefaultSize时，wrap_content和match_perent都是填充屏幕)
        //稍后会重新写这个方法，能达到wrap_content的效果
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int childMeasureWidth = 0;
        int childMeasureHeight = 0;

        View child = getChildAt(0);
        childMeasureWidth = child.getMeasuredWidth();
        childMeasureHeight = child.getMeasuredHeight();
        left = 0;
        right = left + childMeasureWidth;
        top = 0;
        bottom = top + childMeasureHeight;
        child.layout(left, top, right, bottom);


        View child2 = getChildAt(1);
        childMeasureWidth = child2.getMeasuredWidth();
        childMeasureHeight = child2.getMeasuredHeight();

        float x = (float) (getWidth() / 2 + Math.sin(Math.PI / 180 * (-getProgress() + 180)) * getWidth() / 3);
        float y = (float) (getHeight() / 2 + Math.cos(Math.PI / 180 * (-getProgress() + 180)) * getHeight() / 3);

        left = (int) (x - childMeasureWidth / 2);
        right = left + childMeasureWidth;
        top = (int) (y - childMeasureHeight / 2);
        bottom = top + childMeasureHeight;
        child2.layout(left, top, right, bottom);

        child2.setRotation(getProgress());
    }


    public void setProgress(int progress) {
        if (customButtonProgress3 != null) {
            customButtonProgress3.setProgress(progress);
            requestLayout();
        }
    }

    public int getProgress() {
        if (customButtonProgress3 != null) {
            return (int) customButtonProgress3.getProgress();
        }
        return 0;
    }
}
