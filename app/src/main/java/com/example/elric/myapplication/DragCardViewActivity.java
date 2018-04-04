package com.example.elric.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DragCardViewActivity extends AppCompatActivity {


    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_card_view);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4, R.id.tv5, R.id.tv6})
    public void onViewClicked(View view) {
        if (toast == null) {
            toast = Toast.makeText(this, view.getId() + " view", Toast.LENGTH_LONG);
        } else {
            toast.setText(view.getId() + " view");
        }
        toast.show();
    }
}
