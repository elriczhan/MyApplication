package com.example.xinshei.myapplication.mp_android_chart;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.xinshei.myapplication.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

/**
 * Created by xinshei on 17/4/17.
 */

public class ChartActivity extends Activity {

    private LineChart mChart;

    Toast toast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chartview);
        toast = Toast.makeText(this, "1", Toast.LENGTH_LONG);

        mChart = (LineChart) findViewById(R.id.chart);
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                toast.setText(e.getX() + " x:y " + e.getY());
                toast.show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
        mChart.setDrawGridBackground(false);
        mChart.getDescription().setEnabled(false);

        mChart.animateX(2000);

        //下面的点
        mChart.getLegend().setForm(Legend.LegendForm.NONE);

        //点击上点的信息图
        CusMarker cm = new CusMarker(this, R.layout.layout_marker);
        mChart.setMarker(cm);

        //设置不让放大缩小
//        mChart.setDragEnabled(false);
        mChart.setScaleEnabled(false);


        XAxis xAxis = mChart.getXAxis();
        xAxis.setDrawGridLines(false);
        //设置基线的位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        //左边的线
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
//        leftAxis.setDrawLabels(false);
        leftAxis.setAxisLineColor(Color.GRAY);
        leftAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return "$ " + value;
            }
        });

        //右边的数值关闭
        YAxis axisRight = mChart.getAxisRight();
        axisRight.setDrawGridLines(false);
        axisRight.setDrawLabels(false);
        axisRight.setDrawAxisLine(false);

        setData(999, 100);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }


    private void setData(int count, float range) {

        ArrayList<Entry> values = new ArrayList<Entry>();

        for (int i = 0; i < count; i++) {
            Entry entry;
            if (values.size() > 0) {
                entry = values.get(i - 1);
            } else {
                entry = new Entry(0, 0);
            }
            float val = 0;
            int a = (int) Math.floor(Math.random() * range);
            if (a % 2 == 0) {
                val = entry.getY() - 3;
            } else {
                val = entry.getY() + 3;
            }

//            values.add(new Entry(i, val, getResources().getDrawable(R.mipmap.ic_launcher)));
            values.add(new Entry(i, val, null));
        }

        LineDataSet set1;


        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, null);
            set1.setColor(Color.parseColor("#153A62"));
            set1.setCircleColor(Color.parseColor("#153A62"));
            set1.setCircleRadius(1.5f);
            set1.setLineWidth(3f);
            set1.setValueTextSize(0f);
            set1.setFormLineWidth(1f);
            set1.setFormSize(15.f);

            //设置高亮显示线
            set1.setDrawHighlightIndicators(false);

            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1); // add the datasets

            // create a data object with the datasets
            LineData data = new LineData(dataSets);

            // set data
            mChart.setData(data);
        }
    }

}
