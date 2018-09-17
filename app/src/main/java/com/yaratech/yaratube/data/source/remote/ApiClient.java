package com.yaratech.yaratube.data.source.remote;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.yaratech.yaratube.BuildConfig;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private final static String BASE_URL = "https://api.vasapi.click/";
    private static Retrofit retrofit;

    private ApiClient() {
        //no instance
    }

    public static Retrofit getClient(File cacheDir) {
        if (retrofit == null) {
            OkHttpClient client = null;
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
                int cacheSize = 20 * 1024 * 1024; // 20 MiB
                Cache cache = new Cache(cacheDir, cacheSize);
                client = new OkHttpClient
                        .Builder()
                        .cache(cache)
                        .addInterceptor(interceptor).build();
            }

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    void setCache(OkHttpClient client) {

    }
}
