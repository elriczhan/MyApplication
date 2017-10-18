package com.example.xinshei.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xinshei.myapplication.loading_stuff.ViewController;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewControllerActivity extends AppCompatActivity {

    @Bind(R.id.content)
    LinearLayout content;
    @Bind(R.id.error)
    Button error;
    @Bind(R.id.loading)
    Button loading;
    @Bind(R.id.original)
    Button original;
    @Bind(R.id.text)
    TextView text;
    private ViewController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_controller);
        ButterKnife.bind(this);
        controller = new ViewController(content);
    }

    @OnClick({R.id.loading, R.id.error, R.id.original})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.loading:
                controller.showLoading();
                break;
            case R.id.error:
                controller.showError();
                break;
            case R.id.original:
                controller.showOriginal();
                break;
        }
    }
}
