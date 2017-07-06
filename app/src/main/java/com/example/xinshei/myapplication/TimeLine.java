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

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("item: " + i);
        }

        recyclerView.setAdapter(new TimeLineAdapter(this, list));

        recyclerView.addItemDecoration(new TimeLineItemDecorator(this));

    }

    private class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.holder> {

        private final Context context;
        private final ArrayList<String> list;

        public TimeLineAdapter(Context context, ArrayList<String> list) {
            this.context = context;
            this.list = list;
        }

//        @Override
//        public void onViewAttachedToWindow(holder holder) {
//            super.onViewAttachedToWindow(holder);
//            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
//            if (lp != null
//                    && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
//                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
//                p.setFullSpan(true);
////                Log.e("asd", "true!!!!!");
//            }
//        }

        @Override
        public holder onCreateViewHolder(ViewGroup parent, int viewType) {

            return new holder(LayoutInflater.from(context).inflate(R.layout.timeline_item, parent, false));
        }

        @Override
        public void onBindViewHolder(holder holder, int position) {
            holder.tv.setText(list.get(position));
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
                tv.setTextColor(Color.RED);
                tv.setBackgroundColor(Color.YELLOW);
            }
        }
    }

    private class TimeLineItemDecorator extends RecyclerView.ItemDecoration {
        private Context context;
        private final Paint paint;

        public TimeLineItemDecorator(Context context) {
            this.context = context;
            paint = new Paint();
            paint.setColor(Color.BLUE);
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDraw(c, parent, state);


            c.drawRect(parent.getWidth() / 2 - 5, 0, parent.getWidth() / 2 + 5, parent.getBottom(), paint);

            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);
//                int childAdapterPosition = parent.getChildAdapterPosition(child);
//                Log.e("asd", "childAdapterPosition: " + childAdapterPosition + "--getChildLayoutPosition:  " + parent.getChildLayoutPosition(child));
//                if (childAdapterPosition % 2 == 0) {
////                    c.drawRect(child.getRight(), child.getBottom() - (child.getBottom() - child.getTop()) / 2 - 5,
////                            parent.getWidth() / 2, child.getBottom() - (child.getBottom() - child.getTop()) / 2 + 5, paint);
//                    c.drawRect(child.getRight() / 2, child.getBottom() - (child.getBottom() - child.getTop()) / 2 - 5,
//                            parent.getWidth() / 2, child.getBottom() - (child.getBottom() - child.getTop()) / 2 + 5, paint);
//                } else {
////                    c.drawRect(parent.getWidth() / 2, child.getBottom() - (child.getBottom() - child.getTop()) / 2 - 5,
////                            child.getLeft(), child.getBottom() - (child.getBottom() - child.getTop()) / 2 + 5, paint);
//                    c.drawRect(parent.getWidth() / 2, child.getBottom() - (child.getBottom() - child.getTop()) / 2 - 5,
//                            child.getRight() - (child.getRight() - child.getLeft()) / 2, child.getBottom() - (child.getBottom() - child.getTop()) / 2 + 5, paint);
//                }
                if (child.getLeft() < parent.getWidth() / 2) {
                    c.drawRect(child.getRight() / 2, child.getBottom() - (child.getBottom() - child.getTop()) / 2 - 5,
                            parent.getWidth() / 2, child.getBottom() - (child.getBottom() - child.getTop()) / 2 + 5, paint);
                } else {
                    c.drawRect(parent.getWidth() / 2, child.getBottom() - (child.getBottom() - child.getTop()) / 2 - 5,
                            child.getRight() - (child.getRight() - child.getLeft()) / 2, child.getBottom() - (child.getBottom() - child.getTop()) / 2 + 5, paint);
                }
            }
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            super.onDrawOver(c, parent, state);

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);
                float left = (float) parent.getWidth() / 2 - bitmap.getWidth() / 2;
                float top = child.getTop() + child.getHeight() / 2 - bitmap.getHeight() / 2;
                c.drawBitmap(bitmap, left, top, paint);
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int childAdapterPosition = parent.getChildAdapterPosition(view);
            outRect.set(30, 0, 30, 0);
//            view.measure(0,0);
//            Log.e("asd", view.getLeft() + "---" + parent.getMeasuredWidth() / 2);
//            if (view.getLeft() < parent.getLeft() / 2) {
//                outRect.set(10, 0, 50, 0);
//            } else {
//                outRect.set(50, 0, 10, 0);
//            }
//            if (childAdapterPosition % 2 == 0) {
////                outRect.set(30, 0, 30, 0);
//                outRect.set(10, 0, 50, 0);
//            } else {
////                outRect.set(30, 0, 30, 0);
//                outRect.set(50, 0, 10, 0);
//            }

        }
    }
}
