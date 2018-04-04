package com.example.elric.myapplication.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.elriczhan.basecore.utils.LogUtil;

/**
 * Created by xinshei on 2018/1/2.
 */

public class CusImageShadowView extends android.support.v7.widget.AppCompatImageView {

    private Paint shadowPaint;

    public CusImageShadowView(Context context) {
        super(context);
        init();
    }


    public CusImageShadowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CusImageShadowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        shadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        shadowPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        LogUtil.e(getWidth() + " lol " + getHeight());

        //绘制阴影，param1：模糊半径；param2：x轴大小：param3：y轴大小；param4：阴影颜色
        shadowPaint.setShadowLayer(5F, getWidth() / 5, getHeight() / 5, Color.BLACK);
        canvas.drawCircle(getWidth() / 2f, getHeight() / 2f, getWidth() / 1.8f - getPaddingTop(), shadowPaint);

        super.onDraw(canvas);
    }
}
