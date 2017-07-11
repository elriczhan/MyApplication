package com.example.xinshei.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class TimeLine extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //此布局只用了一个RecyclerView 充满了整个屏幕
        setContentView(R.layout.activity_time_line);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        //给RecyclerView添加布局管理 用瀑布流
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        //设置一点假数据
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("item: " + i);
        }

        recyclerView.setAdapter(new TimeLineAdapter(this, list));
        //用上的我们的时间线条目装饰
        recyclerView.addItemDecoration(new TimeLineItemDecorator(this));

    }

    private class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.holder> {

        private final Context context;
        private final ArrayList<String> list;

        public TimeLineAdapter(Context context, ArrayList<String> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public holder onCreateViewHolder(ViewGroup parent, int viewType) {
            //条目布局只是用了一个图片和一个TextView
            return new holder(LayoutInflater.from(context).inflate(R.layout.timeline_item, parent, false));
        }

        @Override
        public void onBindViewHolder(holder holder, int position) {
            holder.tv.setText(list.get(position));
            //为了呈现出 右边有两个的效果 和 每个条目的大小不一样
            if (position != 10 && position != 5 && position != 8) {
                holder.image.setMaxHeight(position * 100 + 50);
                holder.image.setMinimumHeight(position * 100 + 50);
            } else {
                holder.image.setMaxHeight(100);
                holder.image.setMinimumHeight(100);
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class holder extends RecyclerView.ViewHolder {
            TextView tv;
            ImageView image;

            public holder(View itemView) {
                super(itemView);
                tv = (TextView) itemView.findViewById(R.id.timeline_tv);
                image = (ImageView) itemView.findViewById(R.id.image);
            }
        }
    }

    /**
     * ItemDecoration 其实就是为了装饰 RecyclerView 和条目
     * 其中最主要的3个方法 分别是两个画的功能 和 一个给条目设置边距
     * public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state)
     * public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state)
     * public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)
     */
    private class TimeLineItemDecorator extends RecyclerView.ItemDecoration {
        private Context context;
        private final Paint paint;
        private final Bitmap bitmap;


        /**
         * 构造方法 传一些 你可能需要的参数进来
         *
         * @param context
         */
        public TimeLineItemDecorator(Context context) {
            this.context = context;
            paint = new Paint();
            paint.setColor(Color.BLUE);
            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        }


        /**
         * 此方法发生在 画条目之前， 所以会被条目所遮挡
         *
         * @param c      画布
         * @param parent 整个RecyclerView
         * @param state
         */
        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);

            //RecyclerView 中间画了一条线
            c.drawRect(parent.getWidth() / 2 - 5, 0, parent.getWidth() / 2 + 5, parent.getBottom(), paint);

            //然后分别给显示在界面的条目画 装饰
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);
                //计算他们是在整个RecyclerView的左边还是右边，得出 支线应该怎么画
                if (child.getLeft() < parent.getWidth() / 2) {
                    c.drawRect(child.getRight() / 2,
                            child.getBottom() - (child.getBottom() - child.getTop()) / 2 - 5,
                            parent.getWidth() / 2,
                            child.getBottom() - (child.getBottom() - child.getTop()) / 2 + 5, paint);
                } else {
                    c.drawRect(parent.getWidth() / 2,
                            child.getBottom() - (child.getBottom() - child.getTop()) / 2 - 5,
                            child.getRight() - (child.getRight() - child.getLeft()) / 2,
                            child.getBottom() - (child.getBottom() - child.getTop()) / 2 + 5, paint);
                }
            }
        }

        /**
         * 此方法发生在 画条目之后， 所以这里所绘画的东西会正当住条目内容
         *
         * @param c      画布
         * @param parent 整个RecyclerView
         * @param state
         */
        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDrawOver(c, parent, state);
            //给每个条目之后画上一个 小图标 也可以增加一些不同的东西
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);
                float left = (float) parent.getWidth() / 2 - bitmap.getWidth() / 2;
                float top = child.getTop() + child.getHeight() / 2 - bitmap.getHeight() / 2;
                c.drawBitmap(bitmap, left, top, paint);
            }
        }

        /**
         * 这个方法用来设置 RecyclerView 条目的偏移量的
         *
         * @param outRect 矩阵
         * @param view    当前的Item View
         * @param parent  指的是RecyclerView
         * @param state
         */
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            //用这个方法来这是 条目周围边距便宜量，分别为 左上右下
            //比如当前这个，就是左边空出50px距离，上面不空，右边空出50px，下面不空
            //outRect.left = 50; 或者这种方式来设置
            //outRect.top = 0;
            //outRect.right = 50;
            //outRect.bottom = 0;
            outRect.set(50, 0, 50, 0);
        }
    }
}
