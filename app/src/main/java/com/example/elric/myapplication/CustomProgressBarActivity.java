package com.example.elric.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import com.elriczhan.basecore.utils.LogUtil;

public class CustomProgressBarActivity extends AppCompatActivity {

    private com.example.elric.myapplication.customProgress customProgress;
    private CustomButtonProgress cbp;
    private CustomButtonProgress2 cbp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_progress_bar);
        customProgress = findViewById(R.id.cp_asd);
        cbp2 = findViewById(R.id.cbp2);
        cbp = findViewById(R.id.cbp);
        SeekBar seekBar = findViewById(R.id.sk_asd);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                customProgress.setProgress(progress);
                cbp.setProgress(progress);
                cbp2.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


//        for(int i =0 ; i < 360 ; i++)
//        {
            LogUtil.e(Math.sin(30) +" this is angle : " + 30 );
            LogUtil.e(Math.cos(30) +" this is angle : " + 30 );
            LogUtil.e(Math.tan(30) +" this is angle : " + 30 );
//            LogUtil.e(Math.cos(i) +" this is angle : " + i );
//        }


    }
}
