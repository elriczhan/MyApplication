package com.example.elric.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import butterknife.ButterKnife;

/**
 * Created by xinshei on 17/2/7.
 */

public class Swipe extends Activity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_drawer);
        ButterKnife.bind(this);
    }
}
