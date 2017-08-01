package com.example.xinshei.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

public class UltraPullToRefreshActivity extends AppCompatActivity {

    @Bind(R.id.store_house_ptr_frame)
    PtrClassicFrameLayout ptrFrameLayout;
    @Bind(R.id.recyler)
    RecyclerView recyler;
    private ArrayList<String> list;
    private UltraPullToRefreshActivity.adapter adapter;

    public double DP;
    public double SP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ultra_pull_to_refresh);
        ButterKnife.bind(this);
        DP = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());
        SP = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 1, getResources().getDisplayMetrics());

        final ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.mipmap.ic_launcher);
        //设置自己的样子
//        ptrFrameLayout.setHeaderView(imageView);
        ptrFrameLayout.setEnabled(false);
        ptrFrameLayout.setLastUpdateTimeRelateObject(this);
        View contentView = ptrFrameLayout.getContentView();
        Toast.makeText(this, (contentView instanceof RecyclerView) + " is recyclerview", Toast.LENGTH_SHORT).show();
        ptrFrameLayout.addPtrUIHandler(new PtrUIHandler() {
            @Override
            public void onUIReset(PtrFrameLayout frame) {
//                imageView.setRotation(180f);
//                ptrFrameLayout.setEnabled(false);
            }

            @Override
            public void onUIRefreshPrepare(PtrFrameLayout frame) {

            }

            @Override
            public void onUIRefreshBegin(PtrFrameLayout frame) {

            }

            @Override
            public void onUIRefreshComplete(PtrFrameLayout frame) {
                Toast.makeText(UltraPullToRefreshActivity.this, "refresh finish!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
                imageView.setRotation(360 * ptrIndicator.getCurrentPercent());
            }
        });
        ptrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return true;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
//                textView.setText(" refreshing");
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Random random = new Random();
                        list.clear();
                        int i1 = random.nextInt(100);
                        for (int i = 0; i < i1; i++) {
                            list.add(random.nextInt(10) + "new SP value: " + (i * SP)
                                    + " and :" + TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, i, getResources().getDisplayMetrics()));
                        }
                        adapter.notifyDataSetChanged();
//                        textView.setText(random.nextInt() + " lol");
                        ptrFrameLayout.refreshComplete();
                    }
                }, 3000);
            }
        });

        list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add("dp times value : " + (int) (i * DP) + " and :" + (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, i, getResources().getDisplayMetrics()));
        }
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyler.setLayoutManager(linearLayoutManager);
        adapter = new adapter();
        ImageView image = new ImageView(this);
        image.setImageResource(R.mipmap.ic_launcher);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                int i1 = random.nextInt(10);
                for (int i = 0; i < i1; i++) {
                    list.add(random.nextInt(10) + "click more position: " + i);
                }
                adapter.notifyItemRangeInserted(list.size() - i1, i1);
            }
        });
        recyler.setAdapter(adapter);
        adapter.addFooter(image);
        recyler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                ptrFrameLayout.setEnabled(linearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0);
                if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == adapter.getItemCount() - 1) {
                    Random random = new Random();
                    int i1 = random.nextInt(10);
                    for (int i = 0; i < i1; i++) {
                        list.add(random.nextInt(10) + "more position: " + i);
                    }
                    adapter.notifyItemRangeInserted(list.size() - i1, i1);
                }
            }
        });

    }

    private class adapter extends RecyclerView.Adapter<viewholder> {

        private ImageView footer;

        @Override
        public viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == 1) {
                return new viewholder(footer);
            } else {
                return new viewholder(new TextView(UltraPullToRefreshActivity.this));
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (footer != null && position == list.size()) {
                return 1;
            } else {
                return 0;
            }
        }

        @Override
        public void onBindViewHolder(viewholder holder, int position) {
            if (footer != null && position == list.size()) {
                return;
            }
            holder.textView.setText(list.get(position));
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

        public viewholder(View itemView) {
            super(itemView);
            if (!(itemView instanceof ImageView)) {
                textView = (TextView) itemView;
            }
        }
    }
}
