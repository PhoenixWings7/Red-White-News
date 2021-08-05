package com.phoenixwings7.white_red_news.repository.newsapi;

import com.google.gson.annotations.SerializedName;

public class ApiNewsPost {
    @SerializedName("id")
    private final int id;
    @SerializedName("title")
    private final String title;
    @SerializedName("description")
    private final String description;
    @SerializedName("icon")
    private final String urlToImage;

    public ApiNewsPost(int id, String title, String description, String urlToImage) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.urlToImage = urlToImage;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrlToImage() {
        return urlToImage;
    }
}
