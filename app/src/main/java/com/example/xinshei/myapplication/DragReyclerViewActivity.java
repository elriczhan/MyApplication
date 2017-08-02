package com.example.xinshei.myapplication;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class DragReyclerViewActivity extends AppCompatActivity {

    private ArrayList<String> list;
    private DragReyclerViewActivity.myAdapter myAdapter;
    private ItemTouchHelper itemTouchHelper;
    private DragReyclerViewActivity.ith ith;
    private boolean enable = true;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_reycler_view);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        Button dragEnable = (Button) findViewById(R.id.dragEnable);
        dragEnable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ith != null) {
                    enable = !enable;
                    ith.setLongPress(enable);
                    myAdapter.notifyDataSetChanged();
                }
            }
        });
        list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add("swap recycler " + i);
        }
        myAdapter = new myAdapter();
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(myAdapter);
        ith = new ith();
        itemTouchHelper = new ItemTouchHelper(ith);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }


    private class ith extends ItemTouchHelper.Callback {

        public boolean drag = true;

        @Override
        public boolean isLongPressDragEnabled() {
            return drag;
        }

        public void setLongPress(boolean drag) {
            this.drag = drag;
        }

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager) {
                int swipeFlag = 0;
                int dragFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                return makeMovementFlags(dragFlag, swipeFlag);
            } else if (layoutManager instanceof LinearLayoutManager) {// linearLayoutManager
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                int orientation = linearLayoutManager.getOrientation();

                int dragFlag = 0;
                int swipeFlag = 0;

                // 为了方便理解，相当于分为横着的ListView和竖着的ListView
                if (orientation == LinearLayoutManager.HORIZONTAL) {// 如果是横向的布局
                    swipeFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                    dragFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                } else if (orientation == LinearLayoutManager.VERTICAL) {// 如果是竖向的布局，相当于ListView
                    dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
//                        swipeFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                }
                return makeMovementFlags(dragFlag, swipeFlag);
            }
            return 0;
        }

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                viewholder itemViewHolder = (viewholder) viewHolder;
                itemViewHolder.select();
            }
            super.onSelectedChanged(viewHolder, actionState);
        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            viewholder itemViewHolder = (viewholder) viewHolder;
            itemViewHolder.finsih();
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            if (list != null) {
                // 更换数据源中的数据Item的位置

                Collections.swap(list, viewHolder.getAdapterPosition(), target.getAdapterPosition());
                // 更新UI中的Item的位置，主要是给用户看到交互效果
                myAdapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

        }
    }


    private class myAdapter extends RecyclerView.Adapter<viewholder> {

        private ImageView footer;
        private Random random = new Random();

        @Override
        public viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == 1) {
                return new viewholder(footer);
            } else {
                TextView textView = new TextView(DragReyclerViewActivity.this);
                int r = random.nextInt(200) + 55;
                int g = random.nextInt(200) + 55;
                int b = random.nextInt(200) + 55;
                textView.setBackgroundColor(Color.rgb(r, g, b));
                textView.setPadding(50, 50, 50, 50);
                return new viewholder(textView);
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (footer != null && position == list.size()) {
                return 1;
            } else {
                if (enable) {
                    return 2;
                } else {
                    return 0;
                }
            }
        }

        @Override
        public void onBindViewHolder(final viewholder holder, int position) {
            if (footer != null && position == list.size()) {
                return;
            }
            int itemViewType = getItemViewType(position);
            if (itemViewType == 0) {
                holder.textView.setText(list.get(position));
            } else {
                holder.textView.setText(list.get(position) + " edit mode");
            }
            holder.textView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (enable) {
                        itemTouchHelper.startDrag(holder);
                    }
                    return enable;
                }

            });
        }

        @Override
        public int getItemCount() {
            return list.size() + (footer != null ? 1 : 0);
        }

        public void addFooter(ImageView image) {
            this.footer = image;
            notifyItemInserted(list.size());
        }
    }

    private class viewholder extends RecyclerView.ViewHolder {
        TextView textView;

        public void select() {
            scaleItem(1.0f, 1.2f, 0.5f);
        }

        public void finsih() {
            scaleItem(1.2f, 1.0f, 1.0f);
        }

        public void scaleItem(float start, float end, float alpha) {
            ObjectAnimator anim1 = ObjectAnimator.ofFloat(itemView, "scaleX",
                    start, end);
            ObjectAnimator anim2 = ObjectAnimator.ofFloat(itemView, "scaleY",
                    start, end);
            ObjectAnimator anim3 = ObjectAnimator.ofFloat(itemView, "alpha",
                    alpha);

            AnimatorSet animSet = new AnimatorSet();
            animSet.setDuration(200);
            animSet.setInterpolator(new LinearInterpolator());
            animSet.playTogether(anim1, anim2, anim3);
            animSet.start();
        }


        public viewholder(View itemView) {
            super(itemView);
            if (!(itemView instanceof ImageView)) {
                textView = (TextView) itemView;
            }
        }
    }
}
