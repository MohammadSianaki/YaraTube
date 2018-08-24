package com.yaratech.yaratube.data.source.remote;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.yaratech.yaratube.BuildConfig;

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

    public static Retrofit getClient() {
        if (retrofit == null) {
            OkHttpClient client = null;
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
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


}
