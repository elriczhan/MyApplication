package com.example.xinshei.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

/**
 * Created by xinshei on 2017/9/20.
 */

public class GlideShadowTransformation extends BitmapTransformation {
    private String ID = "GlideShadowTransformation";
    private byte[] ID_BYTES = ID.getBytes(CHARSET);
    private int radius;
    private int shadow;

    public GlideShadowTransformation(int radius, int shadow) {
        this.radius = radius;
        this.shadow = shadow;
        ID = radius + "";
        ID_BYTES = ID.getBytes(CHARSET);
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        return ShadowCorp(pool, toTransform, outWidth, outHeight);
    }


    private Bitmap ShadowCorp(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        Bitmap result = pool.get(outWidth, outHeight, Bitmap.Config.ARGB_8888);
        result.setHasAlpha(true);

        BitmapShader shader = new BitmapShader(toTransform, Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        //方块画黑色的先 参数分别坐上右下 你可以改变坐上右下的位置
        RectF rect = new RectF(0, 0, result.getWidth(), result.getHeight());
        Canvas canvas = new Canvas(result);
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        canvas.save();
        //黑色的位置
        paint.setColor(Color.BLACK);
        canvas.drawRoundRect(rect, radius, radius, paint);
        canvas.restore();

        paint.setShader(shader);
        //方块画原图 参数分别坐上右下 变小shadow的大小溜出来给黑色的也可以改一改 不给黑色的
        rect = new RectF(shadow, shadow, result.getWidth() - shadow, result.getHeight() - shadow);
        canvas.drawRoundRect(rect, radius, radius, paint);

        return result;
    }

    @Override
    public int hashCode() {
        return ID.hashCode();
    }

    @Override
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
    }
}
