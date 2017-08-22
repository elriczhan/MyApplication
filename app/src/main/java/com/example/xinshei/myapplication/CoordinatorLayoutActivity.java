package com.example.xinshei.myapplication;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CoordinatorLayoutActivity extends AppCompatActivity {

    @Bind(R.id.recycler)
    RecyclerView recyclerView;
    private ArrayList<String> list;
    @Bind(R.id.appbar)
    AppBarLayout appBarLayout;
    @Bind(R.id.lufei)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout);
        ButterKnife.bind(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new myAdapter());

        list = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            list.add("something " + i);
        }

        AppBarLayout.OnOffsetChangedListener onOffsetChangedListener = new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float height = -verticalOffset;
                float totalScrollRange = appBarLayout.getTotalScrollRange();
                int i = (int) ((height / totalScrollRange) * 255);
                StatusBarUtil.setColor(CoordinatorLayoutActivity.this, Color.rgb((int) (33 + (height / totalScrollRange) * (255 - 33)), (int) (33 + (height / totalScrollRange) * (255 - 33)), (int) (36 + (height / totalScrollRange) * (255 - 36))));
            }
        };
        appBarLayout.addOnOffsetChangedListener(onOffsetChangedListener);

//        int color = Color.parseColor("#FFFF00");
//        Drawable wrappedDrawable = DrawableCompat.wrap(getResources().getDrawable(R.mipmap.ic_launcher));
//        DrawableCompat.setTint(wrappedDrawable, color);
//        ColorStateList colorStateList = getResources().getColorStateList(R.drawable.image_color);
//        DrawableCompat.setTintList(wrappedDrawable,colorStateList);
//        imageView.setImageDrawable(wrappedDrawable);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }


    public class myAdapter extends RecyclerView.Adapter<viewholder> {

        private Random random = new Random();

        @Override
        public viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
            TextView textView = new TextView(CoordinatorLayoutActivity.this);
            int r = random.nextInt(200) + 55;
            int g = random.nextInt(200) + 55;
            int b = random.nextInt(200) + 55;
            textView.setBackgroundColor(Color.rgb(r, g, b));
            textView.setPadding(50, 50, 50, 50);
            return new viewholder(textView);
        }

        @Override
        public void onBindViewHolder(final viewholder holder, int position) {
            holder.textView.setText(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

    }

    public class viewholder extends RecyclerView.ViewHolder {
        TextView textView;

        public viewholder(View itemView) {
            super(itemView);
            if (!(itemView instanceof ImageView)) {
                textView = (TextView) itemView;
            }
        }
    }
}
