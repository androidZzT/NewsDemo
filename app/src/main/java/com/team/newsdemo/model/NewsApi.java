package com.team.newsdemo.model;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Android_ZzT on 17/8/8.
 */

public interface NewsApi {

    String HOST = "http://mingke.veervr.tv:1920";

    /**
     * http://mingke.veervr.tv:1920/test
     */
    @GET("/test")
    Observable<NewsResponse> getNews();
}
