package com.example.xinshei.myapplication;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class VideoWallPaperActivity extends AppCompatActivity {

    @Bind(R.id.rv_video)
    RecyclerView recyclerView;

    ArrayList<File> list;
    private Myadapter myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio_wall_paper);
        ButterKnife.bind(this);

        list = new ArrayList<>();


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myadapter = new Myadapter();
        recyclerView.setAdapter(myadapter);
        new Thread(new Runnable() {
            @Override
            public void run() {
                File file = new File(Environment.getExternalStorageDirectory() + "");
                listFileMp4File(file);
            }
        }).start();
    }

    private void listFileMp4File(File file) {
        for (File f : file.listFiles()) {
            if (f.isDirectory()) {
                listFileMp4File(f);
            } else if (f.getName().endsWith(".mp4")) {
                list.add(f);
            }
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myadapter.notifyDataSetChanged();
            }
        });
    }

    private class Myadapter extends RecyclerView.Adapter<holder> {

        @Override
        public holder onCreateViewHolder(ViewGroup parent, int viewType) {
            Button button = new Button(VideoWallPaperActivity.this);
            return new holder(button);
        }

        @Override
        public void onBindViewHolder(holder holder, final int position) {
            holder.button.setText(list.get(position).getName());
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    VideoWallPaper vw = new VideoWallPaper();
                    vw.setVoiceSilence(VideoWallPaperActivity.this);
                    vw.setToWallPaper(VideoWallPaperActivity.this, list.get(position).getAbsolutePath());
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    private class holder extends RecyclerView.ViewHolder {
        Button button;

        public holder(View itemView) {
            super(itemView);
            button = (Button) itemView;
        }
    }
}
