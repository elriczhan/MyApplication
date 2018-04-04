package com.example.elric.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class shaderView extends View {

    private final Context context;
    private Paint paint;
    private Drawable drawable;
    private int mWidth;
    private int mHeight;
    private Path path;
    private Bitmap bitmap;

    public shaderView(Context context) {
        this(context, null);
    }

    public shaderView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public shaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLUE);
        drawable = context.getResources().getDrawable(R.drawable.lufei);
        bitmap = drawableToBitamp(drawable);
        bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() / 5, bitmap.getHeight() / 5, false);

        paint.setShader(new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        mWidth = bitmap.getWidth();

        mHeight = bitmap.getHeight();
        Log.e("asd", mWidth + "--");
        Log.e("asd", mHeight + "--");
        path = new Path();
        path.moveTo(0, 0);
        path.quadTo(mWidth / 2, mHeight, mWidth, 0);
        path.close();
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawRect(0, 0, mWidth, mHeight, paint);

        canvas.drawPath(path, paint);
        path.moveTo(0, mHeight);
        path.quadTo(mWidth / 2, 0, mWidth, mHeight);
        path.close();
        canvas.drawPath(path, paint);
    }

    private Bitmap drawableToBitamp(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bd = (BitmapDrawable) drawable;
            return bd.getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }
}
