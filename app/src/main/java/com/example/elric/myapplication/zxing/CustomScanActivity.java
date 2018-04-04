package com.example.elric.myapplication.zxing;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.elric.myapplication.R;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

public class CustomScanActivity extends Activity implements DecoratedBarcodeView.TorchListener { // 实现相关接口
    // 添加一个按钮用来控制闪光灯，同时添加两个按钮表示其他功能，先用Toast表示

    Button swichLight;
    Button hint1Show;
    Button hint2Show;
    DecoratedBarcodeView mDBV;

    private CaptureManager captureManager;
    private boolean isLightOn = false;

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        captureManager.onSaveInstanceState(outState);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_scan);
        swichLight = (Button) findViewById(R.id.btn_switch);
        hint1Show = (Button) findViewById(R.id.btn_hint1);
        hint2Show = (Button) findViewById(R.id.btn_hint2);
        mDBV = (DecoratedBarcodeView) findViewById(R.id.dbv_custom);

        swichLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击切换闪光灯
                if (isLightOn) {
                    mDBV.setTorchOff();
                } else {
                    mDBV.setTorchOn();
                }
            }
        });
        mDBV.setSoundEffectsEnabled(false);
        mDBV.setTorchListener(this);

        // 如果没有闪光灯功能，就去掉相关按钮
        if (!hasFlash()) {
            swichLight.setVisibility(View.GONE);
        }

        //重要代码，初始化捕获
        captureManager = new CaptureManager(this, mDBV);
        captureManager.initializeFromIntent(getIntent(), savedInstanceState);
        captureManager.decode();
    }

    // torch 手电筒
    @Override
    public void onTorchOn() {
        Toast.makeText(this, "torch on", Toast.LENGTH_LONG).show();
        isLightOn = true;
    }

    @Override
    public void onTorchOff() {
        Toast.makeText(this, "torch off", Toast.LENGTH_LONG).show();
        isLightOn = false;
    }

    // 判断是否有闪光灯功能
    private boolean hasFlash() {
        return getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }


    protected DecoratedBarcodeView initializeContent() {
        this.setContentView(com.google.zxing.client.android.R.layout.zxing_capture);
        return (DecoratedBarcodeView) this.findViewById(com.google.zxing.client.android.R.id.zxing_barcode_scanner);
    }

    protected void onResume() {
        super.onResume();
        captureManager.onResume();
    }

    protected void onPause() {
        super.onPause();
        captureManager.onPause();
    }

    protected void onDestroy() {
        super.onDestroy();
        captureManager.onDestroy();
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        captureManager.onSaveInstanceState(outState);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        captureManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return this.mDBV.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

}