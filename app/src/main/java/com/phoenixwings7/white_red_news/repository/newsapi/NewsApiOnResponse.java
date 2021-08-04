package com.phoenixwings7.white_red_news.repository.newsapi;

import java.util.List;

public interface NewsApiOnResponse<T> {
    void onSuccessfulResponse(List<T> responseData);
}
