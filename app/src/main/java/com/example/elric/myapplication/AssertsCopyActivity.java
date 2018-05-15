package com.example.elric.myapplication;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.elriczhan.basecore.utils.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class AssertsCopyActivity extends AppCompatActivity {

    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asserts_copy);
        Button copy = findViewById(R.id.copy);
        Button list = findViewById(R.id.list);
        image = findViewById(R.id.image);


        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.e("start to copy!!!");
                File file = new File(Environment.getExternalStorageDirectory() + "/123/");
                file.mkdirs();
                copyFilesFromAssets(getApplicationContext(), "123.gif", Environment.getExternalStorageDirectory() + "/123/123.gif");
                LogUtil.e("end to end!!!");

            }
        });

        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listFile(new File(Environment.getExternalStorageDirectory() + "/123/"));
            }
        });


    }

    private void listFile(File file) {
        for (File f : file.listFiles()) {
            if (f.isDirectory()) {
                listFile(f);
            } else {
                LogUtil.e(" got it " + f.getName());
                LogUtil.e(" got it " + f.getName());
                LogUtil.e(" got it " + f.getName());
                LogUtil.e(" got it " + f.getName());
                LogUtil.e(" got it " + f.getName());
                LogUtil.e(" got it " + f.getName());
                Glide.with(getApplicationContext()).asGif().load(f).into(image);
            }
        }

    }

    public static void copyFilesFromAssets(Context context, String assetsPath, String savePath) {
        try {
            String fileNames[] = context.getAssets().list(assetsPath);// 获取assets目录下的所有文件及目录名
            if (fileNames.length > 0) {// 如果是目录
                File file = new File(savePath);
                file.mkdirs();// 如果文件夹不存在，则递归
                for (String fileName : fileNames) {
                    copyFilesFromAssets(context, assetsPath + "/" + fileName,
                            savePath + "/" + fileName);
                }
            } else {// 如果是文件
                LogUtil.e("got herererere!~~");
                InputStream is = context.getAssets().open(assetsPath);
                FileOutputStream fos = new FileOutputStream(new File(savePath));
                byte[] buffer = new byte[1024];
                int byteCount = 0;
                while ((byteCount = is.read(buffer)) != -1) {// 循环从输入流读取
                    // buffer字节
                    fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
                    LogUtil.e("write!!!!!!!!");
                }
                LogUtil.e("the bytecount + " + byteCount);
                fos.flush();// 刷新缓冲区
                is.close();
                fos.close();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            LogUtil.e("got exception!!!" + e.toString());
        }
    }
}
