package com.example.xinshei.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
        list = new ArrayList<>();
        list.add(Menu.class);
        list.add(CardLayoutActivity.class);
        list.add(FlowerActivity.class);
        list.add(CameraActivity.class);
        list.add(VideoWallPaperActivity.class);
        //版本管理测试 test123 4 tag2 hahaha
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new rvAdapter());
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
