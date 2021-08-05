package com.phoenixwings7.white_red_news.repository.database;

import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {NewsPost.class}, version = 1)
public abstract class Database extends RoomDatabase {
    public abstract NewsPostDao newsPostDao();
    public static final String dbName = "news";
}
