package com.example.elric.myapplication;

import com.elriczhan.basecore.utils.LogUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by xinshei on 2018/5/17.
 */

public class MySQLManager {

    // 数据库连接常量
    public static final String DRIVER = "com.mysql.jdbc.Driver";
    public static final String USER = "test";
    public static final String PASS = "test";
    public static final String URL = "jdbc:mysql://192.168.100.178:3306/test";
//    public static final String URL = "jdbc:mysql:" + "//192.168.100.178:3306";
//    public static final String URL = "jdbc:jtds:sqlserver://192.168.100.178:3306;database=Assess";

    // 静态成员，支持单态模式
    private static MySQLManager per = null;
    private Connection conn = null;
    private Statement stmt = null;

    // 单态模式-懒汉模式
    private MySQLManager() {
    }

    public static MySQLManager createInstance() {
        if (per == null) {
            per = new MySQLManager();
            per.initDB();
        }
        return per;
    }

    // 加载驱动
    public void initDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e(e.toString());
        }
    }

    // 连接数据库，获取句柄+对象
    public void connectDB() {
        LogUtil.e("Connecting to database...");
        try {
            conn = DriverManager.getConnection(URL, USER, PASS);
            stmt = conn.createStatement();
            LogUtil.e("SqlManager:Connect to database successful.");
        } catch (SQLException e) {
            e.printStackTrace();
            LogUtil.e(e.toString());
        }
    }

    // 关闭数据库 关闭对象，释放句柄
    public void closeDB() {
        LogUtil.e("Close connection to database..");
        try {
            stmt.close();
            conn.close();
            LogUtil.e("Close connection successful");
        } catch (SQLException e) {
            e.printStackTrace();
            LogUtil.e(e.toString());
        }
    }

    // 查询
    public ResultSet executeQuery(String sql) {
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            LogUtil.e(e.toString());
        }
        return rs;
    }

    public boolean execute(String sql) {
        boolean rs = false;
        try {
            rs = stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            LogUtil.e(e.toString());
        }
        return rs;
    }

    // 增添/删除/修改
    public int executeUpdate(String sql) {
        int ret = 0;
        try {
            ret = stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            LogUtil.e(e.toString());
        }
        return ret;
    }
}
