package com.example.xinshei.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FlowerActivity extends AppCompatActivity {

    @Bind(R.id.flower)
    FlowerView flower;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flower);
        ButterKnife.bind(this);

        flower.startPathAnim();

    }
}
