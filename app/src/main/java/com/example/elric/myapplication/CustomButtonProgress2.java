package com.example.elric.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xinshei on 2018/5/4.
 */

public class CustomButtonProgress2 extends View {

    private Paint paint3;
    private Paint paint;
    private Paint paint5;
    private float centerX;
    private float centerY;
    private RectF rect2;
    private RectF rect;
    private float progress;
    private float wp;
    private float hp;
    private float width;
    private float height;

    public CustomButtonProgress2(Context context) {
        super(context);
        init();
    }


    public CustomButtonProgress2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomButtonProgress2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        paint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint3.setColor(Color.GRAY);
        paint3.setStrokeWidth(40);
        paint3.setStyle(Paint.Style.STROKE);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(40);
        paint.setStyle(Paint.Style.FILL);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));

        rect2 = new RectF();
        rect = new RectF();


        paint5 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint5.setColor(Color.GREEN);
        paint5.setStyle(Paint.Style.STROKE);
        paint5.setStrokeWidth(15);
        paint5.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);

        centerX = width / 2;
        centerY = height / 2;

        wp = width / 8;
        hp = height / 8;

        rect2.set(20, 20, (int) width - 20, (int) height - 20);

        rect.set(0 - 30, 0
                , width + 30, height + 60);
    }


    @Override
    protected void onDraw(Canvas canvas) {


        int i = canvas.saveLayer(0, 0, getWidth(), getHeight(), paint3, Canvas.ALL_SAVE_FLAG);

        canvas.drawArc(rect2, 0, 360, false, paint3);


        for (int w = 0; w < 8; w++) {
            canvas.drawLine(centerX, centerY + 30, w * wp, 0 - 10, paint5);

            canvas.drawLine(centerX, centerY + 30, width + 10, w * hp, paint5);

            canvas.drawLine(centerX, centerY + 30, w * wp, height + 10, paint5);

            canvas.drawLine(centerX, centerY + 30, 0 - 10, w * hp, paint5);
        }

        canvas.drawArc(rect, 260, progress, true, paint);

        canvas.restoreToCount(i);

        super.onDraw(canvas);
    }

    public void setProgress(int progress) {

        if (progress > 100) {
            progress = 100;
        } else if (progress < 0) {
            progress = 0;
        }
        this.progress = (float) (progress * 3.6);
        postInvalidate();
    }
}
