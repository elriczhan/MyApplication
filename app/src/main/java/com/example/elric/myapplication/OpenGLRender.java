package com.example.elric.myapplication;

import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by xinshei on 17/1/18.
 */

public class OpenGLRender implements Renderer {

    public float cr, cb, cg;

    public void setColor(float r, float g, float b) {
        cr = r;
        cg = g;
        cb = b;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
//        gl.glShadeModel(GL10.GL_SMOOTH);
//        gl.glClearColor(1.0f, 1f, 1f, 1f);
//        gl.glEnable(GL10.GL_LEQUAL);
//        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
        // 关闭抗抖动
        gl.glDisable(GL10.GL_DITHER);
        //设置系统对透视进行修正
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
        gl.glClearColor(0.5f, 0.5f, 0, 0);
        //设置阴影平滑模式
        gl.glShadeModel(GL10.GL_SMOOTH);
        //启用深度测试
        gl.glEnable(GL10.GL_DEPTH_TEST);
        //设置深度测试的类型
        gl.glDepthFunc(GL10.GL_LEQUAL);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
//        gl.glViewport(0, 0, width, height);
        // 设置3D视窗的大小及位置
        gl.glViewport(0, 0, width, height);
        //将当前矩阵模式设为投影矩阵
        gl.glMatrixMode(GL10.GL_PROJECTION);
        //初始化单位矩阵
        gl.glLoadIdentity();
        //计算透视视窗的宽度、高度比
        float ratio = (float) width / height;
        //调用此方法设置透视视窗的空间大小
        gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
//        gl.glLoadIdentity();
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT / GL10.GL_DEPTH_BUFFER_BIT);
        gl.glClearColor(cr, cg, cb, 0f);
//        Log.e("wtf", cr + ":" + cg + ":" + cb);
        gl.glClearDepthf(1.0f);

        gl.glEnable(GL10.GL_DEPTH_TEST);

        gl.glDepthFunc(GL10.GL_LEQUAL);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
        Log.e("asd","wtf");
    }
}
