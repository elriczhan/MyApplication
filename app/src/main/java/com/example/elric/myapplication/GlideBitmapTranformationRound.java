package com.example.elric.myapplication;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;

import java.security.MessageDigest;

/**
 * Created by xinshei on 2017/9/20.
 */

public class GlideBitmapTranformationRound extends BitmapTransformation {
    private String ID = "GlideBitmapTranformationRound";
    private byte[] ID_BYTES = ID.getBytes(CHARSET);
    private int radius;

    public GlideBitmapTranformationRound(int radius) {
        this.radius = radius;
        ID = radius+"";
        ID_BYTES = ID.getBytes(CHARSET);
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        return TransformationUtils.roundedCorners(pool, toTransform, outWidth, outHeight, radius);
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
