package com.example.elric.myapplication;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by xinshei on 17/1/18.
 */

public class bufferUtil {

    public static FloatBuffer mBuffer;
    public static FloatBuffer floatToBuffer(float[] a){
        //先初始化buffer，数组的长度*4，因为一个float占4个字节
        ByteBuffer mbb = ByteBuffer.allocateDirect(a.length*4);
        //数组排序用nativeOrder
        mbb.order(ByteOrder.nativeOrder());
        mBuffer = mbb.asFloatBuffer();
        mBuffer.put(a);
        mBuffer.position(0);
        return mBuffer;
    }
}
