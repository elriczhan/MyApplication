package com.example.xinshei.myapplication.Dao;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xinshei.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xinshei on 2017/8/23.
 */

public class DaoAdapter extends RecyclerView.Adapter<DaoAdapter.viewholder> {

    private final ArrayList<DaoBean> list;
    private final Context contxt;

    public DaoAdapter(Context context, ArrayList<DaoBean> list) {
        this.contxt = context;
        this.list = list;
    }

    @Override
    public viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new viewholder(LayoutInflater.from(contxt).inflate(R.layout.item_dao, parent, false));
    }

    @Override
    public void onBindViewHolder(viewholder holder, int position) {
        DaoBean daoBean = list.get(position);
        holder.id.setText(daoBean.getId() + "");
        holder.name.setText(daoBean.getName() + "");
        holder.age.setText(daoBean.getAge() + "");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void notifyDataSetChanged(List<DaoBean> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public static class viewholder extends RecyclerView.ViewHolder {
        @Bind(R.id.id)
        TextView id;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.age)
        TextView age;

        public viewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
