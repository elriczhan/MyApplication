package com.example.xinshei.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FlowLayoutSelectActivity extends AppCompatActivity {

    @Bind(R.id.select)
    Button select;
    @Bind(R.id.taglayout)
    TagFlowLayout taglayout;
    private TagAdapter<bean> tagAdapter;
    private MotionEvent mMotion = MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 0, 0, 0);
    private long millis;
    private int[] tagloc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout_select);
        ButterKnife.bind(this);

        final ArrayList<bean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            bean bean = new bean();
            bean.name = "index: " + i;
            if (i == 0 || i == 1 || i == 2 || i == 9) {
                bean.select = false;
            } else {
                bean.select = true;
            }
            list.add(bean);
        }
        final int dp10 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        tagAdapter = new TagAdapter<bean>(list) {
            @Override
            public View getView(FlowLayout parent, int position, bean o) {
                TextView inflate;
                if (!o.select) {
                    inflate = new TextView(FlowLayoutSelectActivity.this);
                    inflate.setBackgroundColor(Color.DKGRAY);
                    inflate.setPadding(dp10, dp10, dp10, dp10);
                } else {
                    inflate = (TextView) View.inflate(FlowLayoutSelectActivity.this, R.layout.item_flow_layout, null);
                }
                inflate.setText(o.name);
                return inflate;
            }

        };
        taglayout.setAdapter(tagAdapter);
        tagloc = new int[2];
        taglayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (!list.get(position).select) {
                    if (SystemClock.uptimeMillis() - millis > 100) {
                        millis = SystemClock.uptimeMillis();
                        int[] location = new int[2];
                        parent.getLocationOnScreen(tagloc);
                        view.getLocationInWindow(location);
                        mMotion.setLocation(location[0] - tagloc[0], location[1] - tagloc[1]);
                        taglayout.onTouchEvent(mMotion);
                        taglayout.performClick();
                        Log.e("asd", view.getX() + "x " + view.getY() + "y , " + location[0] + " : " + location[1] + " tagloc " + tagloc[0] + " : " + tagloc[1]);
                    }
                }
                return false;
            }
        });

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set<Integer> selectedList = taglayout.getSelectedList();
                Log.e("asd", selectedList.toString());
            }
        });

    }

    public class bean {
        public String name;
        public boolean select;
    }

}
