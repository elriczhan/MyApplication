package com.example.elric.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xinshei on 2018/5/4.
 */

public class CustomButtonProgress extends View {

    private Paint paint;
    private Paint paint2;
    private Paint paint3;
    private Paint paint5;
    private float centerX;
    private float centerY;
    private Rect rect;
    private RectF rect2;
    private float progress;
    private Path path;
    private float[] pos;
    private LinearGradient mLinearGradient;
    private PathMeasure pm;
    private Bitmap bitmap;

    public CustomButtonProgress(Context context) {
        super(context);
        init();
    }


    public CustomButtonProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomButtonProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        pm = new PathMeasure();
        pos = new float[2];
        pos[0] = Float.MIN_VALUE;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);

        paint3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint3.setColor(Color.GRAY);
        paint3.setStrokeWidth(20);
        paint3.setStyle(Paint.Style.STROKE);

        paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint2.setColor(Color.WHITE);
        paint2.setTextSize(20);

        rect = new Rect();
        paint2.getTextBounds("Start", 0, 4, rect);

        rect2 = new RectF();


        paint5 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint5.setColor(Color.BLACK);
        paint5.setStyle(Paint.Style.STROKE);
        paint5.setStrokeWidth(20);

        path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);

        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        float width = MeasureSpec.getSize(widthMeasureSpec);
        float height = MeasureSpec.getSize(heightMeasureSpec);


        centerX = width / 2;
        centerY = height / 2;

        rect2.set(20, 20, (int) width - 20, (int) height - 20);

        if (mLinearGradient == null) {
            mLinearGradient = new LinearGradient(width / 2, height, width / 2, 0, new int[]{
                    Color.RED, Color.GREEN},
                    null, Shader.TileMode.CLAMP);

            paint5.setShader(mLinearGradient);
        }

    }


    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawArc(rect2, 0, 360, true, paint3);

        canvas.drawCircle(centerX, centerY, 100, paint);
        canvas.drawText("Start", centerX - rect.width() / 2, centerY + rect.height() / 2, paint2);


        path.reset();
        path.addArc(rect2, 270, progress);

        canvas.drawPath(path, paint5);
        if (pos[0] == Float.MIN_VALUE) {
            pm.setPath(path, false);
            pm.getPosTan(pm.getLength(), pos, null);
        } else {
            canvas.drawCircle(pos[0], pos[1], 20, paint2);
        }

        canvas.save();

        canvas.rotate(progress, centerX, centerY);
//        canvas.drawCircle(50, 50, 10, paint5);
        canvas.drawBitmap(bitmap, centerX - bitmap.getWidth() / 2, 50, paint5);

        canvas.restore();
        super.onDraw(canvas);
    }

    public void setProgress(int progress) {

        if (progress > 100) {
            progress = 100;
        } else if (progress < 0) {
            progress = 0;
        }
        pm.setPath(path, false);
        pm.getPosTan(pm.getLength(), pos, null);
        this.progress = (float) (progress * 3.6);

        postInvalidate();
    }
}
