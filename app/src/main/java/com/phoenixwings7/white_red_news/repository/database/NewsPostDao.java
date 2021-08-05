package com.phoenixwings7.white_red_news.repository.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NewsPostDao {
    @Query(("SELECT * FROM news_posts"))
    List<NewsPost> getAllNewsPosts();

    @Insert
    void addNewsPost(NewsPost post);

    @Insert
    void addNewsPosts(List<NewsPost> newsPosts);
}
