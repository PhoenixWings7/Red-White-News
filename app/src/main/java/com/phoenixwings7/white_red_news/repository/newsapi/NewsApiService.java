package com.phoenixwings7.white_red_news.repository.newsapi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NewsApiService {
    @GET("6125f2d0-0688-4547-aae8-0295d984f196")
    Call<List<ApiNewsPost>> getNews();
}
