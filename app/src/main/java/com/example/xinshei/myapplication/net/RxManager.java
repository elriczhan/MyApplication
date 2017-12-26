package com.example.xinshei.myapplication.net;

import org.reactivestreams.Publisher;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RxManager {
    private static Retrofit retrofit;
    private static OkHttpClient okHttpClient;

    public static Retrofit Instance() {
        if (null == retrofit) {
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
//                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//新的配置
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//新的配置
                    .client(okHttpInstance())
                    .baseUrl("http://wechat.xin-shei.com/v6/")
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
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build();
        }
        return okHttpClient;
    }


    /**
     * 节省步骤
     * .subscribeOn(Schedulers.io())
     * .unsubscribeOn(Schedulers.io())
     * .observeOn(AndroidSchedulers.mainThread());
     *
     * @param <T> 类型
     * @return 返回一个Transformer to Compose
     */
//    public static <T> Observable.Transformer<T, T> RunOnMainThread() {
//        return new Observable.Transformer<T, T>() {
//            @Override
//            public Observable<T> call(Observable<T> tObservable) {
//                return tObservable.subscribeOn(Schedulers.io())
//                        .unsubscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread());
//            }
//        };
//    }
    public static <T> ObservableTransformer<T, T> RunOnMainThread() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(io.reactivex.Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> FlowableTransformer<T, T> flowable_main_thread() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

}
