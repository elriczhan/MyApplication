package com.example.elric.myapplication;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.elric.myapplication.radar.RadarData;
import com.example.elric.myapplication.radar.RadarView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xinshei on 17/5/12.
 */

public class radarActivity2 extends Activity {

    private RadarView mChart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.radar_layout2);
        mChart = (RadarView) findViewById(R.id.radarView);

        mChart.setRotationEnable(false);

        List<Integer> lsit = new ArrayList<>();
        lsit.add(0xff26C6DA);
        lsit.add(0xff4DD0E1);
        lsit.add(0xff80DEEA);
        lsit.add(0xffB2EBF2);
        lsit.add(0xffE0F7FA);
        mChart.setLayerColor(lsit);

        List<String> text = new ArrayList<>();
        text.add("重要预测");
        text.add("经验值");
        text.add("专业背景");
        text.add("分析逻辑");
        text.add("正确率");
        mChart.setVertexText(text);
        mChart.setMaxValue(10);

        List<Float> values = new ArrayList<>();
        values.add(5f);
        values.add(6f);
        values.add(4f);
        values.add(4f);
        values.add(9f);
        List<String> sub = new ArrayList<>();
        sub.add("5分");
        sub.add("6分");
        sub.add("4分");
        sub.add("4分");
        sub.add("9分");
        mChart.setSubVertexText(sub);
        mChart.setSubVertexTextColor(Color.parseColor("#FD9129"));
        mChart.setVertexTextColor(Color.BLACK);

        RadarData data = new RadarData(values);
        data.setColor(Color.parseColor("#00838F"));
        data.setValueTextEnable(false);
        data.setVauleTextColor(Color.RED);
        mChart.addData(data);
    }


}
