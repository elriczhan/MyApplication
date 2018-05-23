package com.example.elric.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.elriczhan.basecore.utils.LogUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class MYSQLActivity extends AppCompatActivity {
    private MySQLManager instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysql);


        Button insert = findViewById(R.id.connect);
        Button delete = findViewById(R.id.run);
        Button update = findViewById(R.id.close);
        Button query = findViewById(R.id.query);


        query.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        instance = MySQLManager.createInstance();
                        instance.connectDB();
                        ResultSet rs = instance.executeQuery("select * from test;");
                        if (rs != null) {
                            LogUtil.e("not null");
                            try {
                                while (rs.next()) {
                                    LogUtil.e(rs.getObject(1) + " name");
                                }
                            } catch (SQLException e) {
                                LogUtil.e(e.toString());

                            }
                        }
                        instance.closeDB();

//                        LogUtil.e("this is i : " + i );
                    }
                }).start();
            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {


                        Random random = new Random();
                        instance = MySQLManager.createInstance();
                        instance.connectDB();
                        instance.execute("insert into test values('" + random.nextInt(100) + " n')");
                        instance.closeDB();
                    }
                }).start();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        instance = MySQLManager.createInstance();
                        instance.connectDB();
                        instance.executeUpdate("DELETE from test");
                        instance.closeDB();
                    }
                }).start();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        instance = MySQLManager.createInstance();
                        instance.connectDB();
                        instance.executeUpdate("update test set name='asd' ");
                        instance.closeDB();
                    }
                }).start();
            }
        });


    }


}
