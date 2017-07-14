package com.example.xinshei.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CameraActivity extends AppCompatActivity {

    @Bind(R.id.iv_photo)
    ImageView image;
    private String imgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);

    }


    @OnClick(R.id.btn_take)
    void click() {
        //调用系统拍照
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imgUrl = Environment.getExternalStorageDirectory() + File.separator + "img+" + new Date().getTime() + ".jpg";
        startActivityForResult(intent, 1111);
    }

    @OnClick(R.id.btn_take2)
    void click2() {
        //调用系统拍照
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imgUrl = Environment.getExternalStorageDirectory() + File.separator + "img+" + new Date().getTime() + ".jpg";
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(imgUrl)));
//启动拍照的窗体。并注册 回调处理
        startActivityForResult(intent, 1111);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 1111) {
            if (data == null) {
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(imgUrl); // 根据路径获取数据
                    Bitmap bitmap = BitmapFactory.decodeStream(fis);
                    image.setImageBitmap(bitmap);// 显示图片
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("asd", e.toString());
                } finally {
                    try {
                        fis.close();// 关闭流
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("asd", e.toString());
                    }
                }
            } else {
                final Bitmap photo = data.getParcelableExtra("data");
                image.setImageBitmap(photo);
                saveImage(photo, imgUrl);
            }
        }
    }

    public void saveImage(Bitmap photo, String spath) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(spath, false));
            photo.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
