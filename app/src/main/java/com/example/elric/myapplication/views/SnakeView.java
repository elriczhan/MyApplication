package com.example.elric.myapplication.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class SnakeView extends ViewGroup {

    private final Context context;
    private int downX;
    private int downY;
    private int disX;
    private int disY;
    private int maxX = 1;
    private int maxY = 1;

    private ArrayList<Integer> listx = new ArrayList<>();
    private ArrayList<Integer> listy = new ArrayList<>();
    private int cw;
    private int dx;
    private int dy;
    private Paint paint;

    public SnakeView(Context context) {
        this(context, null);
    }

    public SnakeView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public SnakeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    private Random random = new Random(100);
    private int doty = -100;
    private int dotX = -100;
    private TextView tv;

    private Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            requestLayout();
            return false;
        }
    });

    private void initView() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        Timer timer = new Timer("asd");
        timer.schedule(new TimerTask() {


            @Override
            public void run() {
                if (random != null) {
                    dotX = random.nextInt(maxX);
                    doty = random.nextInt(maxY);
                    tv = new TextView(context);
                    tv.setBackgroundColor(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                    tv.setWidth(20);
                    tv.setHeight(20);
                    handler.sendEmptyMessageDelayed(0, 0);
                    Log.e("asd", "draw " + dotX + " : " + doty);
                }
            }
        }, 0, 3000);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

//        int dx1 = disX - listx.get(0);
//        int dy1 = disY - listy.get(0);
//        int distance = (int) Math.sqrt(dx1 * dx1 + dy1 * dy1);
//        if (distance > cw) {
        listx.add(5, disX);
        listy.add(5, disY);
//        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            int la = i + 1;
            View child = getChildAt(la);
            child.layout(listx.get(la * 5), listy.get(la * 5), listx.get(la * 5) + child.getMeasuredWidth(), listy.get(la * 5) + child.getMeasuredHeight());
        }
        View childAt = getChildAt(0);
        childAt.layout(dotX - 15, doty - 15, dotX + 15, doty + 15);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (maxX == 1) {
            listx = new ArrayList<>(getChildCount() * 5);
            listy = new ArrayList<>(getChildCount() * 5);
            for (int i = 0; i < getChildCount() * 5; i++) {
                listx.add(0, disX);
                listy.add(0, disY);
            }
        }
        maxX = width;
        maxY = height;
//        disX = maxX / 2;
//        disY = maxY / 2;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            cw = child.getMeasuredWidth();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) e.getX();
                downY = (int) e.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) e.getX();
                int moveY = (int) e.getY();
                dx = moveX - downX;
                dy = moveY - downY;
                downX = moveX;
                downY = moveY;
                disX += dx;
                disY += dy;
                if (disX < 0) {
                    disX = 0;
                } else if (disX > maxX) {
                    disX = maxX;
                }
                if (disY < 0) {
                    disY = 0;
                } else if (disY > maxY) {
                    disY = maxY;
                }
                int dx1 = dotX - disX;
                int dy1 = doty - disY;
                int distance = (int) Math.sqrt(dx1 * dx1 + dy1 * dy1);
                if (distance < cw) {
                    if (tv != null) {
                        addView(tv);
                        tv = null;
                        dotX = -100;
                        doty = -100;
                    }
                }
                requestLayout();
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }
}
