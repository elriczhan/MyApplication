package com.example.xinshei.myapplication;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RxManager {
    private static Retrofit retrofit;
    private static OkHttpClient okHttpClient;

    public static Retrofit Instance() {
        if (null == retrofit) {
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//新的配置
                    .client(okHttpInstance())
                    .baseUrl("http://211.99.143.91:808/")
                    .build();
        }
        return retrofit;
    }

    public static OkHttpClient okHttpInstance() {
        if (null == okHttpClient) {
            okHttpClient = new OkHttpClient().newBuilder()
                    //设置连接时间
                    .connectTimeout(60, TimeUnit.SECONDS)
                    //设置读时间
                    .readTimeout(5, TimeUnit.SECONDS)
                    //设置写时间
                    .writeTimeout(5, TimeUnit.SECONDS)
                    .build();
        }
        return okHttpClient;
    }
}
