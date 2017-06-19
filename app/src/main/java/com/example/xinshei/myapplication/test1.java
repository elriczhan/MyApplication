package com.example.xinshei.myapplication;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class test1 extends Activity {

    GLSurfaceView glView;
    private SimpleRenderer simpleRenderer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        glView = new gl(this);
        simpleRenderer = new SimpleRenderer();
        glView.setRenderer(simpleRenderer);
        setContentView(glView);
    }

    class gl extends GLSurfaceView {

        public gl(Context context) {
            super(context);
        }

        @Override
        public boolean onTouchEvent(final MotionEvent event) {
            queueEvent(new Runnable() {
                @Override
                public void run() {
                    simpleRenderer.setColor(event.getX() / getWidth(), event.getY() / getHeight(), 1.0f);
                }
            });
            return super.onTouchEvent(event);
        }
    }

    class SimpleRenderer implements GLSurfaceView.Renderer {

        private float r = 1f;
        private float g = 1f;
        private float b = 1f;

        public void setColor(float r, float g, float b) {
            this.r = r;
            this.g = g;
            this.b = b;
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(6 * 2 * 4);
            byteBuffer.order(ByteOrder.nativeOrder());
            FloatBuffer vertices = byteBuffer.asFloatBuffer();
            // 定义两个三角形的六个顶点
            vertices.put(new float[]{
                    0.0f, 0.0f,
                    10.0f, 200.0f,
                    90.0f, 0.0f
            });
            vertices.flip();
            gl.glColor4f(1.0f, 1.0f, 0.0f, 1.0f);

            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
            gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertices);
            gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
//            gl.glDrawArrays(GL10.GL_TRIANGLES, 3, 3);
            gl.glClearColor(r, g, b, 1);
//            Log.e("asd","rgb");

        }

        @Override
        public void onSurfaceChanged(GL10 gl, int arg1, int arg2) {
            gl.glViewport(0, 0, arg1, arg2);

        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {
            gl.glClearColor(1, 1, 1, 1);
            gl.glColor4f(1.0f, 0.0f, 0.0f, 0.0f);
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
            gl.glMatrixMode(GL10.GL_PROJECTION);
            gl.glLoadIdentity();
            gl.glOrthof(0, 480, 0, 800, 1, -1);
        }
    }
}
