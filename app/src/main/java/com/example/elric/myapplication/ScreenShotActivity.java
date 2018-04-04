package com.example.elric.myapplication;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

public class ScreenShotActivity extends AppCompatActivity {

    private ImageView image;
    private ScreenShotListenManager manager;
    private RequestOptions options =
            new RequestOptions()
                    .optionalTransform(new GlideBitmapTranformationRound(1))
//                    .optionalTransform(new GlideTransformRound(100))
//                                .bitmapTransform(new CropSquareTransformation(getApplicationContext()))
//                    .placeholder(R.drawable.lufei)
            ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_shot);
        image = (ImageView) findViewById(R.id.image);

        manager = ScreenShotListenManager.newInstance(this);
        manager.setListener(new ScreenShotListenManager.OnScreenShotListener() {
            @Override
            public void onShot(final String imagePath) {
                Log.e("asd", imagePath + "=====");

                //延迟两秒 毕竟图片地址虽然有了 但是图片还没存入 读取的话 会出问题
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(2000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Glide.with(ScreenShotActivity.this).load(imagePath)
                                        .transition(new DrawableTransitionOptions().crossFade(2000))
                                        .apply(options).into(image);
//                                Glide.with(ScreenShotActivity.this).load(imagePath)
//                                        .into(image);
                            }
                        });
                    }
                }).start();
                //自己做弹窗=。=
            }
        });

        manager.startListen();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.stopListen();
    }
}
