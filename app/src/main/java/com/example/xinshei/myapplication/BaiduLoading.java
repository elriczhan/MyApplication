package com.example.xinshei.myapplication;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class BaiduLoading extends View {

    private int color;
    private String text;
    private Paint mPaint;
    private Paint textPaint;
    private Path path;
    private int mWidth;
    private int mHeight;
    private float currentPersent = 0;
    private int drawHeight;

    public BaiduLoading(Context context) {
        this(context, null);
    }

    public BaiduLoading(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public BaiduLoading(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        drawHeight = mHeight;
    }

    private void initView(Context context, AttributeSet attrs) {
        //获取自定义参数值
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BaiduLoading);
        //自定义颜色和文字
        color = array.getColor(R.styleable.BaiduLoading_color, Color.rgb(41, 163, 254));
        text = array.getString(R.styleable.BaiduLoading_text);
        array.recycle();
        //图形及路径填充画笔（抗锯齿、填充、防抖动）
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(color);
        mPaint.setDither(true);
        //文字画笔（抗锯齿、白色、粗体）
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 100, context.getResources().getDisplayMetrics()));
        //闭合波浪路径
        path = new Path();

    }

    public void setPercent(float percent) {
        this.currentPersent = percent;
        invalidate();
    }

    public void setHeight(float percent) {
        this.drawHeight = mHeight - (int) (percent * mHeight);
        invalidate();
    }

    private Path getActionPath(float percent) {
        Path path = new Path();
        int x = -mWidth;
        //当前x点坐标（根据动画进度水平推移，一个动画周期推移的距离为一个周期的波长）
        x += percent * mWidth;
        //波形的起点
        path.moveTo(x, drawHeight);
        //控制点的相对宽度
        int quadWidth = mWidth / 4;
        //控制点的相对高度
        int quadHeight = mHeight / 20 * 3;
        //第一个周期波形
        path.rQuadTo(quadWidth, quadHeight, quadWidth * 2, 0);
        path.rQuadTo(quadWidth, -quadHeight, quadWidth * 2, 0);
        //第二个周期波形
        path.rQuadTo(quadWidth, quadHeight, quadWidth * 2, 0);
        path.rQuadTo(quadWidth, -quadHeight, quadWidth * 2, 0);
        //右侧的直线
        path.lineTo(x + mWidth * 2, mHeight);
        //下边的直线
        path.lineTo(x, mHeight);
        //自动闭合补出左边的直线
        path.close();
        return path;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //底部的字
        textPaint.setColor(color);
        drawCenterText(canvas, textPaint, text);
        //上层的字
        textPaint.setColor(Color.WHITE);
        canvas.save(Canvas.CLIP_SAVE_FLAG);
        //裁剪成圆形
        Path o = new Path();
        o.addCircle(mWidth / 2, mHeight / 2, mWidth / 2, Path.Direction.CCW);
        canvas.clipPath(o);
        //生成闭合波浪路径
        path = getActionPath(currentPersent);
        //画波浪
        canvas.drawPath(path, mPaint);
        //裁剪文字
        canvas.clipPath(path);
        drawCenterText(canvas, textPaint, text);
        canvas.restore();
    }

    private void drawCenterText(Canvas canvas, Paint textPaint, String text) {
        Rect rect = new Rect(0, 0, mWidth, mHeight);
        textPaint.setTextAlign(Paint.Align.CENTER);

        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float top = fontMetrics.top;
        float bottom = fontMetrics.bottom;

        int centerY = (int) (rect.centerY() - top / 2 - bottom / 2);

        canvas.drawText(text, rect.centerX(), centerY, textPaint);
    }


}
