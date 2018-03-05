package com.elriczhan.basecore.net;

import com.elriczhan.basecore.CoreApplication;
import com.elriczhan.basecore.utils.LogUtil;
import com.elriczhan.basecore.utils.NetUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xinshei on 2018/1/2.
 */

public class RetrofitManager {

    private static Retrofit retrofit = null;
    private static OkHttpClient okHttpClient;
    private static String baseUrl = "http://www.baidu.com";

    private RetrofitManager() {
    }

    public static void baseUrl(String url) {
        if (retrofit != null) {
            retrofit.newBuilder().baseUrl(url).build();
        }
        baseUrl = url;
    }

    public static Retrofit getInstance() {
        if (retrofit == null) {
            synchronized (RetrofitManager.class) {
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(okhttpInstance())
                            .baseUrl(baseUrl)
                            .build();
                }
            }
        }
        return retrofit;
    }

    private static OkHttpClient okhttpInstance() {
        if (okHttpClient == null) {
            synchronized (RetrofitManager.class) {
                if (okHttpClient == null) {
                    okHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(60, TimeUnit.SECONDS)
                            .readTimeout(5, TimeUnit.SECONDS)
                            .writeTimeout(5, TimeUnit.SECONDS)
                            .addInterceptor(httpLoggingInterceptor)
                            .addInterceptor(cacheInterceptor)
                            .build();
                }
            }

        }
        return okHttpClient;
    }


    private static HttpLoggingInterceptor httpLoggingInterceptor =
            new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    LogUtil.i(message);
                }
            }).setLevel(HttpLoggingInterceptor.Level.BODY);

    private static final int TIMEOUT_CONNECT = 60; //60秒
    private static final int TIMEOUT_DISCONNECT = 60 * 60 * 24 * 7; //7天

    private static Interceptor cacheInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {

            if (!NetUtil.isNetworkAvailable(CoreApplication.getAppContext())) {
                Request request = chain.request();
                //离线的时候为7天的缓存。
                request = request.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + TIMEOUT_DISCONNECT)
                        .build();
                return chain.proceed(request);
            } else {
                okhttp3.Response originalResponse = chain.proceed(chain.request());
                String cacheControl = originalResponse.header("Cache-Control");
                //如果cacheControl为空，就让他TIMEOUT_CONNECT秒的缓存，本例是60秒，方便观察。注意这里的cacheControl是服务器返回的
                if (cacheControl == null) {
                    originalResponse = originalResponse.newBuilder()
                            .header("Cache-Control", "public, max-age=" + TIMEOUT_CONNECT)
                            .build();
                    return originalResponse;
                } else {
                    return originalResponse;
                }
            }
        }
    };

    /**
     * 节省步骤
     * .subscribeOn(Schedulers.io())
     * .unsubscribeOn(Schedulers.io())
     * .observeOn(AndroidSchedulers.mainThread());
     *
     * @param <T> 类型
     * @return 返回一个Transformer to Compose
     */
    public static <T> ObservableTransformer<T, T> RunOnMainThread() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(io.reactivex.Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

}
