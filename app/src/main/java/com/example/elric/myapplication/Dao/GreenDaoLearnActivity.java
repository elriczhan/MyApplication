package com.example.elric.myapplication.Dao;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.elric.myapplication.R;
import com.example.elric.myapplication.app;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GreenDaoLearnActivity extends AppCompatActivity {

    @Bind(R.id.add)
    Button add;
    @Bind(R.id.delete)
    Button delete;
    @Bind(R.id.update)
    Button update;
    @Bind(R.id.name)
    EditText name;
    @Bind(R.id.age)
    EditText age;
    @Bind(R.id.select)
    Button select;
    @Bind(R.id.recycler)
    RecyclerView recycler;
    private ArrayList<DaoBean> list;
    private DaoAdapter daoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_green_dao_learn);
        ButterKnife.bind(this);

        recycler.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        daoAdapter = new DaoAdapter(this, list);
        recycler.setAdapter(daoAdapter);

    }

    @OnClick({R.id.add, R.id.delete, R.id.update, R.id.select})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add:
                DaoBeanDao daoBeanDao = app.daoSession.getDaoBeanDao();
                DaoBean daoBean = new DaoBean();
                daoBean.setName(name.getText().toString().trim());
                daoBean.setAge(age.getText().toString().trim());
                long insert = daoBeanDao.insert(daoBean);
                Log.e("asd", insert + " id to ineset");
                break;
            case R.id.delete:
                DaoBeanDao daoBeanDaod = app.daoSession.getDaoBeanDao();
                List<DaoBean> list2 = daoBeanDaod.queryBuilder().build().list();
                for (DaoBean d : list2) {
                    if (d.getName().equals(name.getText().toString().trim())) {
                        daoBeanDaod.delete(d);
                    }
                }
                break;
            case R.id.update:
                DaoBeanDao daoBeanDaou = app.daoSession.getDaoBeanDao();
                List<DaoBean> list1 = daoBeanDaou.queryBuilder().build().list();
                for (DaoBean d : list1) {
                    if (d.getName().equals(name.getText().toString().trim())) {
                        d.setAge("update");
                        daoBeanDaou.update(d);
                    }
                }
                break;
            case R.id.select:
                break;
        }
        DaoBeanDao daoBeanDaos = app.daoSession.getDaoBeanDao();
        List<DaoBean> list = daoBeanDaos.queryBuilder().build().list();
        daoAdapter.notifyDataSetChanged(list);
    }
}
