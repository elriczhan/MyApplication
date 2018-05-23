package com.example.elric.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xinshei on 2018/5/4.
 */

public class CustomButtonProgress3 extends View {

    private Paint paint3;
    private Paint paint;
    private Paint paint5;
    private float centerX;
    private float centerY;
    private RectF rect2;
    private RectF rect;
    private float progress;
    private float width;
    private float height;
    private Path path;

    public CustomButtonProgress3(Context context) {
        super(context);
        init();
    }


    public CustomButtonProgress3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomButtonProgress3(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        paint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint3.setColor(Color.GRAY);
        paint3.setStrokeWidth(40);
        paint3.setStyle(Paint.Style.STROKE);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(40);
        paint.setStyle(Paint.Style.FILL);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));

        rect2 = new RectF();
        rect = new RectF();


        paint5 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint5.setColor(Color.GREEN);
        paint5.setStyle(Paint.Style.STROKE);
        paint5.setStrokeWidth(5);
        paint5.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        path = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);

        centerX = width / 2;
        centerY = height / 2;


        rect2.set(20, 20, (int) width - 20, (int) height - 20);

        rect.set(0, 0, width, height);

        path.reset();
        path.moveTo(centerX, centerY);
        for (int degree = 0; degree < 360; degree += 5) {
            float x = (float) (centerX + Math.sin(Math.PI / 180 * degree) * centerX);
            float y = (float) (centerY + Math.cos(Math.PI / 180 * degree) * centerY);
            path.lineTo(x, y);
            path.lineTo(centerX, centerY);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {


        int i = canvas.saveLayer(0, 0, getWidth(), getHeight(), paint, Canvas.ALL_SAVE_FLAG);

        canvas.drawArc(rect2, 0, 360, false, paint3);


        canvas.drawPath(path, paint5);

        canvas.drawArc(rect, 275, progress, true, paint);

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
