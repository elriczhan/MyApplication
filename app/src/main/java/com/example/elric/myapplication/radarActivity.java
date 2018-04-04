package com.example.elric.myapplication;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;

import java.util.ArrayList;

/**
 * Created by xinshei on 17/5/12.
 */

public class radarActivity extends Activity {

    private RadarChart mChart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.radar_layout);
        mChart = (RadarChart) findViewById(R.id.radar);

//        mChart.setBackgroundColor(Color.rgb(60, 65, 82));

        mChart.getDescription().setEnabled(false);

        mChart.setWebLineWidth(1f);
        //线的颜色
        mChart.setWebColor(Color.LTGRAY);
        mChart.setWebLineWidthInner(1f);
        //网的颜色
        mChart.setWebColorInner(Color.LTGRAY);
        mChart.setWebAlpha(100);

        mChart.setBackgroundColor(Color.WHITE);


        //点击的样子
        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
//        MarkerView mv = new RadarMarkerView(this, R.layout.radar_markerview);
//        mv.setChartView(mChart); // For bounds control
//        mChart.setMarker(mv); // Set the marker to the chart

        setData();

        mChart.animateXY(
                1400, 1400,
                Easing.EasingOption.EaseInOutQuad,
                Easing.EasingOption.EaseInOutQuad);

        XAxis xAxis = mChart.getXAxis();
//        xAxis.setTypeface();
        xAxis.setTextSize(14f);
        xAxis.setYOffset(0f);
        xAxis.setXOffset(0f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {

            private String[] mActivities = new String[]{"重要预测", "经验值", "专业背景", "分析逻辑", "正确率"};

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mActivities[(int) value % mActivities.length];
            }
        });
        //外面的字体颜色
        xAxis.setTextColor(Color.BLACK);

        YAxis yAxis = mChart.getYAxis();
//        yAxis.setTypeface(mTfLight);
        yAxis.setLabelCount(5, false);
        yAxis.setTextSize(9f);
        //最大最小
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(10f);
        yAxis.setDrawLabels(false);

        //区域颜色描述
//        Legend l = mChart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
//        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        l.setDrawInside(false);
//        l.setTypeface(mTfLight);
//        l.setXEntrySpace(7f);
//        l.setYEntrySpace(5f);
//        l.setTextColor(Color.BLUE);

    }

    public void setData() {

        float mult = 10;
        float min = 0;
        int cnt = 5;

        ArrayList<RadarEntry> entries1 = new ArrayList<RadarEntry>();
//        ArrayList<RadarEntry> entries2 = new ArrayList<RadarEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < cnt; i++) {
            float val1 = (float) (Math.random() * mult) + min;
            entries1.add(new RadarEntry(val1));

//            float val2 = (float) (Math.random() * mult) + min;
//            entries2.add(new RadarEntry(val2));
        }

        RadarDataSet set1 = new RadarDataSet(entries1, "Last Week");
        //线的颜色
        set1.setColor(Color.parseColor("#1B8994"));
        //里面的填充色
        set1.setFillColor(Color.parseColor("#1B8994"));
        set1.setDrawFilled(true);
        set1.setFillAlpha(180);
        set1.setLineWidth(2f);
        set1.setDrawHighlightCircleEnabled(false);
        set1.setDrawHighlightIndicators(false);

//        RadarDataSet set2 = new RadarDataSet(entries2, "This Week");
//        set2.setColor(Color.rgb(121, 162, 175));
//        set2.setFillColor(Color.rgb(121, 162, 175));
//        set2.setDrawFilled(true);
//        set2.setFillAlpha(180);
//        set2.setLineWidth(2f);
//        set2.setDrawHighlightCircleEnabled(true);
//        set2.setDrawHighlightIndicators(false);

        ArrayList<IRadarDataSet> sets = new ArrayList<IRadarDataSet>();
        sets.add(set1);
//        sets.add(set2);

        RadarData data = new RadarData(sets);
//        data.setValueTypeface(mTfLight);
        data.setValueTextSize(12f);
        data.setDrawValues(false);
        data.setValueTextColor(Color.BLUE);

        mChart.setData(data);
        mChart.invalidate();
    }
}
