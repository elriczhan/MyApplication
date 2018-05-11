package com.example.elric.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xinshei on 2018/5/2.
 */

public class customProgress extends View {

    private Bitmap iDot;
    private Bitmap iDot2;
    private String[] iWords;
    private String[] iColor;
    private float mWidth;
    private float mHeight;
    private float wordPerWidth;
    private float progressPerWidth;
    private float contentHeight;
    private float contentTop;
    private Paint mPaint;
    private Paint mTPaint;
    private float mLeftPadding;
    private Rect rect;
    private Rect rect2;
    private int progress;

    public customProgress(Context context) {
        super(context);
        init();
    }


    public customProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public customProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        iDot = BitmapFactory.decodeResource(getResources(), R.drawable.down_ar);
        iDot2 = BitmapFactory.decodeResource(getResources(), R.drawable.up_ar);
        iWords = new String[]{"清仓", "减仓", "观望", "加仓", "满仓"};
        iColor = new String[]{"#F44740", "#F67543", "#E1BB4F", "#5ACC4D", "#4DB246"};

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor(iColor[0]));

        mTPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTPaint.setColor(Color.WHITE);
        mTPaint.setTextSize(getDp(18));
        rect = new Rect();
        rect2 = new Rect();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = MeasureSpec.getSize(widthMeasureSpec) - getPaddingRight() - getPaddingLeft();
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        mLeftPadding = getPaddingLeft();

        float bottom = 0;
        if (iDot != null) {
            bottom = iDot.getHeight();
        }

        contentTop = bottom;
        contentHeight = mHeight - bottom * 2;

        wordPerWidth = mWidth / 5;

        progressPerWidth = mWidth / 100;

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(iDot, mLeftPadding + progress - iDot.getWidth() / 2, 0, mPaint);

        for (int i = 0; i < iColor.length; i++) {
            rect.set((int) (mLeftPadding + wordPerWidth * i), (int) contentTop,
                    (int) (mLeftPadding + wordPerWidth * (i + 1)), (int) (mHeight - contentTop));
            mPaint.setColor(Color.parseColor(iColor[i]));
            canvas.drawRect(rect, mPaint);

            mTPaint.getTextBounds(iWords[i], 0, iWords[i].length(), rect2);
            canvas.drawText(iWords[i], rect.left + (rect.right - rect.left) / 2 - rect2.width() / 2,
                    contentTop + (rect.bottom - rect.top) / 2 + rect2.height() / 2, mTPaint);
        }


        canvas.drawBitmap(iDot2, mLeftPadding + progress - iDot.getWidth() / 2, mHeight - contentTop, mPaint);


    }

    public void setProgress(int progress) {
        if (progress > 100) {
            progress = 100;
        } else if (progress < 0) {
            progress = 0;
        }
        this.progress = (int) (progress * progressPerWidth);
        postInvalidate();
    }

    public int getDp(int value) {
        return (int) ((value * getResources().getDisplayMetrics().density) + 0.5f);
    }

}
