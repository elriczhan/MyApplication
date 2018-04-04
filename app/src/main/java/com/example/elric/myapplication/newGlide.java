package com.example.elric.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class newGlide extends AppCompatActivity {

    @Bind(R.id.image1)
    ImageView image1;
    @Bind(R.id.image2)
    ImageView image2;
    @Bind(R.id.image3)
    ImageView image3;
    @Bind(R.id.image4)
    ImageView image4;
    @Bind(R.id.image5)
    ImageView image5;
    @Bind(R.id.image6)
    ImageView image6;
    @Bind(R.id.seekbar)
    SeekBar seekbar;

    private int rp = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_glide);
        ButterKnife.bind(this);

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                rp = progress;

                Glide.with(newGlide.this)
                        .load(R.drawable.lufei)
                        .apply(new RequestOptions()
                                .optionalTransform(new GlideBitmapTranformationRound(rp)))
                        .into(image1);

                Glide.with(newGlide.this)
                        .load(R.drawable.lufei)
                        .apply(new RequestOptions()
                                .optionalTransform(new GlideTransformRound(rp)))
                        .into(image2);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @OnClick(R.id.click)
    public void onViewClicked() {

        Glide.with(this).load(R.drawable.lufei).apply(new RequestOptions()
                .centerCrop()).into(image3);
        Glide.with(this).load(R.drawable.lufei).apply(new RequestOptions()
                .centerInside()).into(image4);
        Glide.with(this).load(R.drawable.lufei).apply(new RequestOptions()
                .circleCrop()).into(image5);
        Glide.with(this).load(R.drawable.lufei).apply(new RequestOptions()).into(image6);
    }
}
