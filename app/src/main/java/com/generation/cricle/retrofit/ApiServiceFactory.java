package com.generation.cricle.retrofit;

import android.os.Environment;

import com.generation.cricle.util.Constants;
import com.generation.cricle.util.LenientGsonConverterFactory;

import java.io.File;
import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServiceFactory {
    private static String BASE_URL = "http://10.90.77.142:8180/";
    private static OkHttpClient okHttpClient;
    private static Retrofit retrofit;
    private static ApiService apiService;
    private static final long cacheSize = 1024 * 1024 * 15;// 缓存文件最大限制大小20M
    private static String cacheDirectory = Environment.getExternalStorageDirectory() + "/aop"; // 设置缓存文件路径
    private static Cache cache = new Cache(new File(cacheDirectory), cacheSize);  //
    public static ApiService getInstance() {
        if (apiService == null) {
            if (retrofit == null) {
                retrofit = createRetrofit();
            }
            apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }

    private static Retrofit createRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getOkHttpClient())
                .addConverterFactory(LenientGsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(30, TimeUnit.SECONDS); // 设置连接超时时间
            builder.writeTimeout(30, TimeUnit.SECONDS);// 设置写入超时时间
            builder.readTimeout(30, TimeUnit.SECONDS);// 设置读取数据超时时间
            builder.retryOnConnectionFailure(true);// 设置进行连接失败重试
//            builder.addNetworkInterceptor(getInterceptor());
            if (Constants.ISDEBUG) {
                builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));//拦截器
            }
            builder.cache(cache);// 设置缓存，cacheDir和cacheSize需要根据实际情况设置
            okHttpClient = builder.build();
        }
        return okHttpClient;
    }

    private static Interceptor getInterceptor() {
        // 返回你自定义的拦截器，如果没有特殊需求可以返回null
        return null;
    }
}