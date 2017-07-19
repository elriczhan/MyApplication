package com.example.xinshei.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
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
                    WeChatBitmapToByteArray(bitmap);
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
                WeChatBitmapToByteArray(photo);
            }
        }
    }

    public static Bitmap WeChatBitmapToByteArray(Bitmap bmp) {

// 首先进行一次大范围的压缩
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, output);
        float zoom = (float) Math.sqrt(32 * 1024 / (float) output.toByteArray().length); //获取缩放比例

// 设置矩阵数据
        Matrix matrix = new Matrix();
        matrix.setScale(zoom, zoom);

// 根据矩阵数据进行新bitmap的创建
        Bitmap resultBitmap = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
        Log.e("asd", output.toByteArray().length + " length1");
        output.reset();

        resultBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
        Log.e("asd", output.toByteArray().length + " length2");

// 如果进行了上面的压缩后，依旧大于32K，就进行小范围的微调压缩
        while (output.toByteArray().length >  1024) {
            matrix.setScale(0.9f, 0.9f);//每次缩小 1/10

            resultBitmap = Bitmap.createBitmap(resultBitmap, 0, 0,
                    resultBitmap.getWidth(), resultBitmap.getHeight(), matrix, true);

            output.reset();
            resultBitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
            Log.e("asd", output.toByteArray().length + " length");
        }

        return resultBitmap;
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
