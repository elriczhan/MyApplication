package com.example.xinshei.myapplication.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.xinshei.myapplication.R;

/**
 * Created by xinshei on 17/7/10.
 */

public class FlowerView extends View implements View.OnClickListener {
    private final Context context;
    private Bitmap icon;
    private Paint paint;
    private Paint paint2;
    private Path path;
    private Path path2;
    private Path path3;
    private float angle;
    private float angle2;
    private int width;
    private int height;
    private PathMeasure pm;
    private float[] mCurrentPosition = new float[2];
    private RectF oval;
    private float value;

    public FlowerView(Context context) {
        this(context, null);
    }

    public FlowerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public FlowerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    private void initView() {
        icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLUE);
        path = new Path();
        path3 = new Path();
        path2 = new Path();
        paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint2.setColor(Color.RED);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeWidth(20);
        path.moveTo(0, 0);
        path.rQuadTo(50, 50, 0, 50);
        path.rQuadTo(50, 50, 50, 10);
        path.rQuadTo(50, 50, 50, 50);
        path.rQuadTo(50, 50, 50, 50);
        path.rQuadTo(50, 50, 50, 50);
        path.rQuadTo(50, 50, 50, 50);
        path.rQuadTo(50, 50, 50, 50);
        path.rQuadTo(50, 50, 50, 50);
        path.lineTo(200,0);
//        path.addCircle(200, 200, 200, Path.Direction.CW);
        pm = new PathMeasure(path, true);
        oval = new RectF(0, 0, 400, 400);

        setOnClickListener(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        Log.e("asd", "???" + width + "-" + height);
        path.lineTo(width / 2, height / 2);
        path.lineTo(width / 2, 0);
        path.lineTo(0, height / 2);
        path.lineTo(0, height);
        path.lineTo(width, height);
        path.lineTo(width, 0);
        path.close();
        path3.addCircle(200, 200, 200, Path.Direction.CW);
        path.addPath(path3);
        pm.setPath(path, true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        canvas.drawPath(path, paint);

        // 绘制对应目标
        canvas.drawBitmap(icon, mCurrentPosition[0], mCurrentPosition[1], paint);
//        canvas.drawArc(0, 0, mCurrentPosition[0], mCurrentPosition[1], angle, angle2, true, paint);
        oval.left = 0 + mCurrentPosition[0];
        oval.top = 0 + mCurrentPosition[1];
        oval.right = 400 + mCurrentPosition[0];
        oval.bottom = 400 + mCurrentPosition[1];
        if (angle2 == 0) {
            angle2 = 5;
        }
        canvas.drawArc(oval, angle - 90, angle2, false, paint2);

        path2.reset();
//        path2.lineTo(0, 0);
        float stop = pm.getLength() * value / pm.getLength();
        float start = (float) (stop - ((0.5 - Math.abs(value / pm.getLength() - 0.5)) * pm.getLength()));
        pm.getSegment(start, stop, path2, true);
        canvas.drawPath(path2, paint2);

    }

    // 开启路径动画
    public void startPathAnim() {
        Log.e("asd", "no idea what is going on");
        // 0 － getLength()
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, pm.getLength());
        valueAnimator.setDuration(5000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        // 减速插值器
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                value = (Float) animation.getAnimatedValue();
                // 获取当前点坐标封装到mCurrentPosition
                pm.getPosTan(value, mCurrentPosition, null);
                postInvalidate();
            }
        });
        valueAnimator.start();

        ValueAnimator valueAnimator2 = ValueAnimator.ofFloat(1, 360);
        valueAnimator2.setDuration(3000);
        valueAnimator2.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator2.setInterpolator(new LinearInterpolator());
        valueAnimator2.setRepeatMode(ValueAnimator.RESTART);
        // 减速插值器
        valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                // 获取当前点坐标封装到mCurrentPosition
                angle = value;

                postInvalidate();
            }
        });
        valueAnimator2.start();

        ValueAnimator valueAnimator3 = ValueAnimator.ofFloat(1, 360);
        valueAnimator3.setDuration(2000);
        valueAnimator3.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator3.setInterpolator(new LinearInterpolator());
        valueAnimator3.setRepeatMode(ValueAnimator.REVERSE);
        // 减速插值器
        valueAnimator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                // 获取当前点坐标封装到mCurrentPosition
//                if (value > 180) {
//                    angle2 = 360 - value * 2;
//                } else {
                angle2 = value;
//                }
                postInvalidate();
            }
        });
        valueAnimator3.start();

    }


    @Override
    public void onClick(View v) {
        startPathAnim();
    }
}
