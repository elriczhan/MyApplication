package com.example.xinshei.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.xinshei.myapplication.Dao.GreenDaoLearnActivity;
import com.example.xinshei.myapplication.update.UpdateActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RVMenu extends AppCompatActivity {

    @Bind(R.id.recycler)
    RecyclerView recyclerView;
    private ArrayList<Class> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rvmenu);
        ButterKnife.bind(this);

//        StatusBarUtil.setColor(this, Color.BLUE);
//        StatusBarUtil.setTransparent(this);
//        StatusBarUtil.setTranslucent(this, 125);

        list = new ArrayList<>();
        list.add(Menu.class);
        list.add(CardLayoutActivity.class);
        list.add(FlowerActivity.class);
        list.add(CameraActivity.class);
        list.add(VideoWallPaperActivity.class);
        list.add(UltraPullToRefreshActivity.class);
        list.add(PaletteActivity.class);
        list.add(NFC_Activity.class);
        list.add(StartActivity.class);
        list.add(DragReyclerViewActivity.class);
        list.add(CoordinatorLayoutActivity.class);
        list.add(GreenDaoLearnActivity.class);
        list.add(FlowLayoutSelectActivity.class);
        list.add(ScreenShotActivity.class);
        list.add(DragCardViewActivity.class);
        list.add(SnakeActivity.class);
        list.add(newGlide.class);
        list.add(ViewControllerActivity.class);
        list.add(UpdateActivity.class);
        list.add(StatusBarFragmentActivity.class);
        list.add(SmartTableViewActivity.class);
        list.add(notification_eight_activity.class);


        //版本管理测试 test123 4 tag2 hahaha22222
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //反着来
//        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new rvAdapter());

        LinearLayout linearLayout = new LinearLayout(this);
        ImageView image = new ImageView(this);
        image.setImageResource(R.drawable.lufei);
        linearLayout.setBackgroundColor(Color.YELLOW);
        linearLayout.addView(image);

        SlidingMenu slidingMenu = new SlidingMenu(this);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT, true);
        slidingMenu.setMenu(linearLayout);
        slidingMenu.setMode(SlidingMenu.LEFT);
        slidingMenu.setShadowWidthRes(R.dimen.activity_horizontal_margin);
        slidingMenu.setBehindOffsetRes(R.dimen.activity_horizontal_margin);
        slidingMenu.setFadeDegree(0.35f);
        slidingMenu.setOffsetFadeDegree(0.4f);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        slidingMenu.setTouchModeBehind(SlidingMenu.TOUCHMODE_FULLSCREEN);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    /**
     * adapter and holder
     */
    private class rvAdapter extends RecyclerView.Adapter<holder> {

        @Override
        public holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new holder(LayoutInflater.from(RVMenu.this).inflate(R.layout.rv_menu_item, parent, false));
        }

        @Override
        public void onBindViewHolder(holder holder, final int position) {
            holder.button.setText(list.get(position).getSimpleName());
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(RVMenu.this, list.get(position)));
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    static class holder extends ViewHolder {

        @Bind(R.id.button)
        Button button;

        public holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
