package com.example.xinshei.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.format.bg.BaseCellBackgroundFormat;
import com.bin.david.form.data.format.bg.ICellBackgroundFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.table.TableData;
import com.bin.david.form.listener.OnColumnItemClickListener;
import com.elriczhan.basecore.utils.LogUtil;

import java.util.ArrayList;

/**
 * Created by xinshei on 2018/3/5.
 */

class SmartTableViewActivity extends AppCompatActivity {

    private SmartTable smartTable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_table_layout);

        smartTable = (SmartTable) findViewById(R.id.smart);
        ArrayList<User> userList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            User user = new User();
            user.setAge(i);
            user.setAvantar(" avatar " + i);
            user.setName(" name " + i);
            user.setClazz("class " + i);
            user.setTime(i + 1L);
            userList.add(user);
        }

        Column<String> column1 = new Column<>("姓名", "name");
        column1.setFixed(true);
        column1.setOnColumnItemClickListener(new OnColumnItemClickListener<String>() {
            @Override
            public void onClick(Column<String> column, String value, String s, int position) {
                LogUtil.e("asd", "value: " + value + "  s:  " + s + " position: " + position);
            }
        });
        Column<Integer> column2 = new Column<>("年龄", "age");
        column2.setFixed(false);
        Column<Long> column3 = new Column<>("更新时间", "time");
        column3.setFixed(true);
        Column<String> column4 = new Column<>("头像", "avantar");
        //如果是多层，可以通过.来实现多级查询
        Column<String> column5 = new Column<>("班级", "clazz");
        //组合列
        Column totalColumn1 = new Column("组合列名", column2, column2, column2, column2, column4, column5);
        //表格数据 datas是需要填充的数据
        final TableData<User> tableData = new TableData<>("表格名", userList, column1, totalColumn1, column3);
        //设置数据
//        smartTable = findViewById(R.id.table);
        //table.setZoom(true,3);是否缩放
        TableConfig config = smartTable.getConfig();
//        config.setFixedXSequence(true);
        config.setShowXSequence(false);
//        config.setFixedYSequence(true);
        config.setShowYSequence(true);
        config.setYSequenceBackgroundColor(Color.YELLOW);
//        config.setColumnTitleBackgroundColor(Color.BLUE);
//        config.setContentBackgroundColor(Color.RED);
//        config.setLeftAndTopBackgroundColor(Color.GREEN);
        config.setContentBackgroundFormat(new BaseCellBackgroundFormat<CellInfo>() {
            @Override
            public int getBackGroundColor(CellInfo cellInfo) {
                if (cellInfo.position % 2 == 0) {
                    return Color.GREEN;
                }
                return 0;
            }

        });
        FontStyle contentStyle = config.getContentStyle();
        contentStyle.setTextColor(Color.BLUE);
        config.setContentStyle(contentStyle);
        FontStyle ySequenceStyle = config.getYSequenceStyle();
        ySequenceStyle.setTextColor(Color.BLACK);
        config.setYSequenceStyle(ySequenceStyle);
        ICellBackgroundFormat<Integer> ySequenceBgFormat = config.getYSequenceBgFormat();
        config.setYSequenceBgFormat(new ICellBackgroundFormat<Integer>() {
            @Override
            public void drawBackground(Canvas canvas, Rect rect, Integer integer, Paint paint) {

            }

            @Override
            public int getTextColor(Integer integer) {
                return 0;
            }
        });
        smartTable.setTableData(tableData);
    }
}
