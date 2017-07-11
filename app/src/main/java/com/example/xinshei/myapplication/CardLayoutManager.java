package com.example.xinshei.myapplication;

import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class CardLayoutManager extends RecyclerView.LayoutManager {


    private final RecyclerView mRecyclerView;
    private final ItemTouchHelper mItemTouchHelper;

    public CardLayoutManager(RecyclerView recyclerView, ItemTouchHelper touchHelper) {
        this.mRecyclerView = recyclerView;
        this.mItemTouchHelper = touchHelper;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(final RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        // 先移除所有view
        removeAllViews();
        // 在布局之前，将所有的子 View 先 Detach 掉，放入到 Scrap 缓存中
        detachAndScrapAttachedViews(recycler);
        int itemCount = getItemCount();
        // 在这里，我们默认配置 CardConfig.DEFAULT_SHOW_ITEM = 3。即在屏幕上显示的卡片数为3
        // 当数据源个数大于最大显示数时
        if (itemCount > CardConfig.DEFAULT_SHOW_ITEM) {
            // 把数据源倒着循环，这样，第0个数据就在屏幕最上面了
            for (int position = CardConfig.DEFAULT_SHOW_ITEM; position >= 0; position--) {
                final View view = recycler.getViewForPosition(position);
                // 将 Item View 加入到 RecyclerView 中
                addView(view);
                // 测量 Item View
                measureChildWithMargins(view, 0, 0);
                // getDecoratedMeasuredWidth(view) 可以得到 Item View 的宽度
                // 所以 widthSpace 就是除了 Item View 剩余的值
                int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);
                // 同理
                int heightSpace = getHeight() - getDecoratedMeasuredHeight(view);
                // 将 Item View 放入 RecyclerView 中布局
                // 在这里默认布局是放在 RecyclerView 中心
                layoutDecoratedWithMargins(view, widthSpace / 2, heightSpace / 2,
                        widthSpace / 2 + getDecoratedMeasuredWidth(view),
                        heightSpace / 2 + getDecoratedMeasuredHeight(view));
                // 其实屏幕上有四张卡片，但是我们把第三张和第四张卡片重叠在一起，这样看上去就只有三张
                // 第四张卡片主要是为了保持动画的连贯性
                if (position == CardConfig.DEFAULT_SHOW_ITEM) {
                    // 按照一定的规则缩放，并且偏移Y轴。
                    // CardConfig.DEFAULT_SCALE 默认为0.1f，CardConfig.DEFAULT_TRANSLATE_Y 默认为14
                    view.setScaleX(1 - (position - 1) * CardConfig.DEFAULT_SCALE);
                    view.setScaleY(1 - (position - 1) * CardConfig.DEFAULT_SCALE);
                    view.setTranslationY((position - 1) * view.getMeasuredHeight() / CardConfig.DEFAULT_TRANSLATE_Y);
                } else if (position > 0) {
                    view.setScaleX(1 - position * CardConfig.DEFAULT_SCALE);
                    view.setScaleY(1 - position * CardConfig.DEFAULT_SCALE);
                    view.setTranslationY(position * view.getMeasuredHeight() / CardConfig.DEFAULT_TRANSLATE_Y);
                } else {
                    // 设置 mTouchListener 的意义就在于我们想让处于顶层的卡片是可以随意滑动的
                    // 而第二层、第三层等等的卡片是禁止滑动的
                    view.setOnTouchListener(mOnTouchListener);
                }
            }
        } else {
            // 当数据源个数小于或等于最大显示数时，和上面的代码差不多
            for (int position = itemCount - 1; position >= 0; position--) {
                final View view = recycler.getViewForPosition(position);
                addView(view);
                measureChildWithMargins(view, 0, 0);
                int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);
                int heightSpace = getHeight() - getDecoratedMeasuredHeight(view);

                layoutDecoratedWithMargins(view, widthSpace / 2, heightSpace / 2,
                        widthSpace / 2 + getDecoratedMeasuredWidth(view),
                        heightSpace / 2 + getDecoratedMeasuredHeight(view));

                if (position > 0) {
                    view.setScaleX(1 - position * CardConfig.DEFAULT_SCALE);
                    view.setScaleY(1 - position * CardConfig.DEFAULT_SCALE);
                    view.setTranslationY(position * view.getMeasuredHeight() / CardConfig.DEFAULT_TRANSLATE_Y);
                } else {
                    view.setOnTouchListener(mOnTouchListener);
                }
            }
        }
    }


    private View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            RecyclerView.ViewHolder childViewHolder = mRecyclerView.getChildViewHolder(v);
            // 把触摸事件交给 mItemTouchHelper，让其处理卡片滑动事件
            if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                mItemTouchHelper.startSwipe(childViewHolder);
            }
            return false;
        }
    };

    public interface OnSwipeListener<T> {

        /**
         * 卡片还在滑动时回调
         *
         * @param viewHolder 该滑动卡片的viewHolder
         * @param ratio      滑动进度的比例
         * @param direction  卡片滑动的方向，CardConfig.SWIPING_LEFT 为向左滑，CardConfig.SWIPING_RIGHT 为向右滑，
         *                   CardConfig.SWIPING_NONE 为不偏左也不偏右
         */
        void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction);

        /**
         * 卡片完全滑出时回调
         *
         * @param viewHolder 该滑出卡片的viewHolder
         * @param t          该滑出卡片的数据
         * @param direction  卡片滑出的方向，CardConfig.SWIPED_LEFT 为左边滑出；CardConfig.SWIPED_RIGHT 为右边滑出
         */
        void onSwiped(RecyclerView.ViewHolder viewHolder, T t, int direction);

        /**
         * 所有的卡片全部滑出时回调
         */
        void onSwipedClear();

    }

    interface CardConfig {

        // CardConfig.DEFAULT_SCALE 默认为0.1f，CardConfig.DEFAULT_TRANSLATE_Y 默认为14
        int DEFAULT_SHOW_ITEM = 5;
        float DEFAULT_SCALE = 0.1f;
        int DEFAULT_TRANSLATE_Y = 14;
        int SWIPING_LEFT = 1;
        int SWIPING_RIGHT = 2;
        int SWIPED_LEFT = 1;
        int SWIPED_RIGHT = 2;
        float DEFAULT_ROTATE_DEGREE = 60;
        int SWIPING_NONE = 0;
    }

}
