package com.example.elric.myapplication;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.dingmouren.paletteimageview.PaletteImageView;
import com.example.elric.myapplication.views.CusImageShadowView;

public class PaletteActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private PaletteImageView paletteImageVieww;
    private SeekBar mSeek1, mSeek2, mSeek3, mSeek4;
//    private LinearLayout activity_main;
//    private Toolbar toolbar;

    private RequestOptions options =
            new RequestOptions()
                    .fitCenter()
//                    .optionalTransform(new GlideBitmapTranformationRound(1000))
                    .optionalTransform(new GlideShadowTransformation(50, 20))
//                                .bitmapTransform(new CropSquareTransformation(getApplicationContext()))
                    .placeholder(R.drawable.cat_paw);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palette);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        Glide.with(this).asBitmap().load(R.drawable.lufei).apply(options).into(imageView);
        initView();
        initListener();

        String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1502771282999&di=5d754e201281f5bd9f1095436a058ba0&imgtype=0&src=http%3A%2F%2Fe.hiphotos.baidu.com%2Fzhidao%2Fwh%253D450%252C600%2Fsign%3D75aaa91fa444ad342eea8f83e59220c2%2F0bd162d9f2d3572cf556972e8f13632763d0c388.jpg";


//        Glide.with(this).asBitmap().load(R.drawable.lufei).apply(options).into(new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
//
//                if (resource != null) {
//                    paletteImageVieww.setBitmap(resource);
////                    paletteImageVieww.refreshDrawableState();
//                    paletteImageVieww.setPaletteRadius(1);
//                    paletteImageVieww.setPaletteShadowRadius(10);
//                    paletteImageVieww.setPaletteShadowOffset(-100, 0);
//                } else {
//                }
//            }
//        });


        final CusImageShadowView cis = (CusImageShadowView) findViewById(R.id.cimage);

        Glide.with(this).asBitmap().load(R.drawable.lufei).apply(options).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {

                if (resource != null) {
                    cis.setImageBitmap(resource);
                }
            }
        });


    }


    private void initView() {
        paletteImageVieww = (PaletteImageView) findViewById(R.id.palette);

//        paletteImageView.setShadowColor(getResources().getColor(R.color.blue));
        mSeek1 = (SeekBar) findViewById(R.id.seek1);
        mSeek2 = (SeekBar) findViewById(R.id.seek2);
        mSeek3 = (SeekBar) findViewById(R.id.seek3);
        mSeek4 = (SeekBar) findViewById(R.id.seek4);
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        activity_main = (LinearLayout) findViewById(R.id.activity_main);
//        setSupportActionBar(toolbar);
//        toolbar.setTitle("PaletteImageView");


//        Glide.with(this).asBitmap().load(R.drawable.lufei).apply(options).into(new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
//                paletteImageVieww.setBitmap(resource);
//            }
//        });


    }

    private void initListener() {
//        mSeek1.setOnSeekBarChangeListener(this);
//        mSeek2.setOnSeekBarChangeListener(this);
//        mSeek3.setOnSeekBarChangeListener(this);
//        mSeek4.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        show(seekBar, progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }


    private void show(SeekBar seekBar, int progress) {
        switch (seekBar.getId()) {
            case R.id.seek1:
                paletteImageVieww.setPaletteRadius(progress);
                break;
            case R.id.seek2:
                paletteImageVieww.setPaletteShadowRadius(progress);
                break;
            case R.id.seek3:
                paletteImageVieww.setPaletteShadowOffset(progress, 0);
                break;
            case R.id.seek4:
                paletteImageVieww.setPaletteShadowOffset(0, progress);
                break;
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.one:
//                startActivity(new Intent(PaletteActivity.this, SampleOneActivity.class));
//                break;
//            case R.id.two:
//                startActivity(new Intent(PaletteActivity.this, SampleTwoActivity.class));
//                break;
//
//        }
//        return true;
//    }

    @Override
    protected void onPause() {
//        activity_main.removeAllViews();
        super.onPause();
    }
}
