package com.phoenixwings7.white_red_news.repository.newsapi;

import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiController implements Callback<List<ApiNewsPost>> {
    private final String LOGCAT_TAG = this.getClass().getName();
    private static final String BASE_URL = "https://run.mocky.io/v3/";
    private final NewsApiOnResponse<ApiNewsPost> doOnResponse;

    public ApiController(NewsApiOnResponse<ApiNewsPost> doOnResponse){
        this.doOnResponse = doOnResponse;
    }

    /**
     * This method sends a call to get data from the mocked news API and sets response callbacks
     */
    public void start(){
        GsonConverterFactory converterFactory = GsonConverterFactory.create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(converterFactory)
                .build();

        NewsApiService newsAPIService = retrofit.create(NewsApiService.class);
        Call<List<ApiNewsPost>> call = newsAPIService.getNews();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<ApiNewsPost>> call, Response<List<ApiNewsPost>> response) {
        if (response.isSuccessful()) {
            List<ApiNewsPost> apiNewsPosts = response.body();
            doOnResponse.onApiSuccessfulResponse(apiNewsPosts);
        }
        else {
            Log.e(LOGCAT_TAG, String.valueOf(response.errorBody()));
        }
    }

    @Override
    public void onFailure(Call<List<ApiNewsPost>> call, Throwable t) {
        Log.e(LOGCAT_TAG, t.getMessage());
    }
}
