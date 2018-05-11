package com.example.elric.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

public class CustomProgressBarActivity extends AppCompatActivity {

    private com.example.elric.myapplication.customProgress customProgress;
    private CustomButtonProgress cbp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_progress_bar);
        customProgress = findViewById(R.id.cp_asd);
        cbp = findViewById(R.id.cbp);
        SeekBar seekBar = findViewById(R.id.sk_asd);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                customProgress.setProgress(progress);
                cbp.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
