package com.elriczhan.basecore.net;

import com.elriczhan.basecore.CoreApplication;
import com.elriczhan.basecore.utils.LogUtil;
import com.elriczhan.basecore.utils.NetUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.CacheControl;
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
    private static String baseUrl = "";
    private static Interceptor headerInterceptor;

    private RetrofitManager() {
    }

    public static Retrofit baseUrl(String url) {
        baseUrl = url;
        if (retrofit != null) {
            return retrofit.newBuilder().baseUrl(url).build();
        }
        return getInstance();
    }

    public static void addHeaders(final Map<String, String> map) {
        headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                try {
                    Request.Builder builder = chain.request().newBuilder();
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        builder.addHeader(entry.getKey(), entry.getValue());
//                        LogUtil.e("header " + entry.getKey() + " " + entry.getValue());
                    }
                    Request request = builder.build();
                    return chain.proceed(request);
                } catch (Exception e) {
                    LogUtil.e(e.toString());
                }
                return chain.proceed(chain.request());
            }
        };

        if (okHttpClient != null)
            okHttpClient = okHttpClient.newBuilder().addInterceptor(headerInterceptor).build();
        if (retrofit != null)
            retrofit = retrofit.newBuilder().client(okHttpClient).build();

    }


    public static class NullStringToEmptyAdapterFactory<T> implements TypeAdapterFactory {
        @SuppressWarnings("unchecked")
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            Class<T> rawType = (Class<T>) type.getRawType();
            if (rawType != String.class) {
                return null;
            }
            return (TypeAdapter<T>) new StringNullAdapter();
        }
    }

    public static class StringNullAdapter extends TypeAdapter<String> {
        @Override
        public String read(JsonReader reader) throws IOException {
            // TODO Auto-generated method stub
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return "";
            }
            return reader.nextString();
        }

        @Override
        public void write(JsonWriter writer, String value) throws IOException {
            // TODO Auto-generated method stub
            if (value == null) {
                writer.nullValue();
                return;
            }
            writer.value(value);
        }
    }

    public static Retrofit getInstance() {
        if (retrofit == null) {
            synchronized (RetrofitManager.class) {
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                                    .registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory())
                                    .create()))
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
                    //cache url
                    File httpCacheDirectory = new File(CoreApplication.getAppContext().getCacheDir(), "responses");
                    int cacheSize = 10 * 1024 * 1024; // 10 MiB
                    Cache cache = new Cache(httpCacheDirectory, cacheSize);

                    if (headerInterceptor == null) {
                        okHttpClient = new OkHttpClient.Builder()
                                .connectTimeout(60, TimeUnit.SECONDS)
                                .readTimeout(60, TimeUnit.SECONDS)
                                .writeTimeout(60, TimeUnit.SECONDS)
                                .addInterceptor(httpLoggingInterceptor)
//                                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                                .addInterceptor(cacheInterceptor)
                                .retryOnConnectionFailure(true)
                                .cache(cache)
                                .build();
                    } else {
                        okHttpClient = new OkHttpClient.Builder()
                                .connectTimeout(60, TimeUnit.SECONDS)
                                .readTimeout(60, TimeUnit.SECONDS)
                                .writeTimeout(60, TimeUnit.SECONDS)
                                .addInterceptor(httpLoggingInterceptor)
//                                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                                .addInterceptor(cacheInterceptor)
                                .addInterceptor(headerInterceptor)
                                .retryOnConnectionFailure(true)
                                .cache(cache)
                                .build();
                    }

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

    private static Interceptor cacheInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            CacheControl.Builder cacheBuilder = new CacheControl.Builder();
            cacheBuilder.maxAge(0, TimeUnit.SECONDS);
            cacheBuilder.maxStale(365, TimeUnit.DAYS);
            CacheControl cacheControl = cacheBuilder.build();

            Request request = chain.request();
            if (!NetUtil.isNetworkAvailable(CoreApplication.getAppContext())) {
                request = request.newBuilder()
                        .cacheControl(cacheControl)
                        .build();
            }
            Response originalResponse = chain.proceed(request);
            if (NetUtil.isNetworkAvailable(CoreApplication.getAppContext())) {
                int maxAge = 0; // read from cache
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public ,max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 7; // tolerate 1-weeks stale
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
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
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

}
