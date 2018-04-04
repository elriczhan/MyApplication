package com.example.elric.myapplication;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.dd.CircularProgressButton;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xinshei on 17/1/23.
 */

public class shineButton extends Activity {
    @OnClick(R.id.btnWithText)
    void click(final CircularProgressButton v) {
        if (v.getProgress() == 100) {
            v.setProgress(0);
        } else if (v.getProgress() == -1) {
            v.setProgress(0);
        } else {
            ValueAnimator value = ValueAnimator.ofInt(0, 100).setDuration(10000);
            value.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    v.setProgress((Integer) animation.getAnimatedValue());
                }
            });
            value.start();
        }
    }

    @OnClick(R.id.lol)
    void asd(View v) {
        Log.e("asd", "clicked");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shinebutton);
        ButterKnife.bind(this);
    }

}
