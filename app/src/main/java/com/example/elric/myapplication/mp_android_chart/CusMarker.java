package com.example.elric.myapplication.mp_android_chart;

import android.content.Context;
import android.widget.TextView;

import com.example.elric.myapplication.R;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

/**
 * Created by xinshei on 2017/12/26.
 */

public class CusMarker extends MarkerView {


    private final TextView textView;

    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    public CusMarker(Context context, int layoutResource) {
        super(context, layoutResource);
        textView = (TextView) findViewById(R.id.charttext);
    }


    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        float x = e.getX();
        float y = e.getY();
        textView.setText(x + " x:y " + y);
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        // Log.e("ddd", "width:" + (-(getWidth() / 2)) + "height:" + (-getHeight()));
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }

}
