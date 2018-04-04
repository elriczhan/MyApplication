package com.example.elric.myapplication;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by xinshei on 17/1/18.
 */

public class mOpenGL extends GLSurfaceView {

    public final OpenGLRender mRender;


    public mOpenGL(Context context) {
        super(context);

        mRender = new OpenGLRender();

        setRenderer(mRender);
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {


        queueEvent(new Runnable() {
            @Override
            public void run() {
                mRender.setColor(event.getX() / getWidth(), event.getY() / getHeight(), 1.0f);
                Log.e("asd","wtf");
            }
        });

        return super.onTouchEvent(event);

    }
}
