package com.team.newsdemo.ui.news;

import com.team.newsdemo.model.NewsApi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Android_ZzT on 17/8/8.
 */

public class NewsModel {

    private Retrofit retrofit;

    private OkHttpClient okHttpClient;

    public NewsModel() {
        okHttpClient = new OkHttpClient.Builder()
                //设置超时
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                //错误重连
                .retryOnConnectionFailure(true)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(NewsApi.HOST)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public NewsApi getNewsApi() {
        return retrofit.create(NewsApi.class);
    }
}
