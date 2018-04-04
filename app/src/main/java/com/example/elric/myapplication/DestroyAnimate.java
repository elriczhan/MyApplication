package com.example.elric.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tyrantgit.explosionfield.ExplosionField;

/**
 * Created by xinshei on 17/1/23.
 */

public class DestroyAnimate extends Activity implements View.OnClickListener {

    @Bind(R.id.button1)
    Button button1;

    @Bind(R.id.text)
    TextView text;

    @Bind(R.id.image)
    ImageView image;
    private ExplosionField ef;

    @OnClick({R.id.image, R.id.button1, R.id.text})
    void click(View v) {
        ef.explode(v);
        v.setOnClickListener(null);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test2);
        ButterKnife.bind(this);
        ef = ExplosionField.attach2Window(this);
        ef.expandExplosionBound(200, 200);
    }

    @Override
    public void onClick(View v) {

    }
}
