package com.example.elric.myapplication.update;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.elric.myapplication.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        ButterKnife.bind(this);
//        http://wechat.xin-shei.com/v6/api/base/update/
    }

    @OnClick({R.id.update, R.id.update2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.update:
                //do update thing
                update update = new update();
                update.check(this);
                break;
            case R.id.update2:
                break;
        }
    }
}
