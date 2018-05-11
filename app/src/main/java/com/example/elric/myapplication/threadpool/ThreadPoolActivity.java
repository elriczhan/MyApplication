package com.example.elric.myapplication.threadpool;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.elriczhan.basecore.utils.LogUtil;
import com.example.elric.myapplication.R;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by xinshei on 2018/5/7.
 */

public class ThreadPoolActivity extends AppCompatActivity {
    private ThreadPoolExecutor threadPoolExecutor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pool);


        Button start = findViewById(R.id.start);
        Button stop = findViewById(R.id.stop);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (threadPoolExecutor != null && !threadPoolExecutor.isShutdown()) {
                    return;
                }
                threadPoolExecutor = new ThreadPoolExecutor(3, 6, 3
                        , TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(100));
                final Random random = new Random();
                for (int i = 0; i < 100; i++) {
                    final int finalI = i;
                    threadPoolExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            LogUtil.e("thread start  i = " + finalI + " and size : " + threadPoolExecutor.getPoolSize());

                            int time = random.nextInt(10000) + 2000;
                            try {
                                Thread.sleep(time);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            LogUtil.e("thread end  i = " + finalI + " sleep " + time);
                        }
                    });
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                threadPoolExecutor.shutdownNow();
                LogUtil.e("got shutdown!!!!!!!!!");
            }
        });


    }
}
