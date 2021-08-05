package com.phoenixwings7.white_red_news.repository;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.room.Room;

import com.phoenixwings7.white_red_news.repository.database.Database;
import com.phoenixwings7.white_red_news.repository.database.NewsPost;
import com.phoenixwings7.white_red_news.repository.newsapi.ApiController;
import com.phoenixwings7.white_red_news.repository.newsapi.ApiNewsPost;
import com.phoenixwings7.white_red_news.repository.newsapi.NewsApiOnResponse;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataHandler implements NewsApiOnResponse<ApiNewsPost> {
    private ApiController apiController = new ApiController(this);
    private String externalStorageDir;
    private Database database;

    public DataHandler(Context appContext, String externalStorageDir){
        this.externalStorageDir = externalStorageDir;
        // set receiver for all data from this DataHandler

        setUpDatabase(appContext);
        // do it once, just here because this is a mock API
        getNewsPostsFromAPI();
    }

    private void setUpDatabase(Context appContext) {
        database = Room.databaseBuilder(appContext, Database.class, Database.dbName)
                .build();
    }

    private void getNewsPostsFromAPI() {
        apiController.start();
    }

    public List<NewsPost> getNewsPosts(){
        return database.newsPostDao().getAllNewsPosts();
    }

    @Override
    public void onApiSuccessfulResponse(List<ApiNewsPost> responseData) {
        List<NewsPost> newsPosts = mapApiResponseToDbNewsPosts(responseData);
        saveNewsPostsToDb(newsPosts);
    }

    private void saveNewsPostsToDb(List<NewsPost> newsPosts) {
        database.newsPostDao().addNewsPosts(newsPosts);
    }

    private List<NewsPost> mapApiResponseToDbNewsPosts(List<ApiNewsPost> responseData) {
        List<NewsPost> posts = new ArrayList<>();

        for (ApiNewsPost apiNewsPost: responseData){
            String title = apiNewsPost.getTitle();
            String description = apiNewsPost.getDescription();
            String urlToImage = apiNewsPost.getUrlToImage();

            Bitmap image = null;
            try {
                image = Picasso.get().load(urlToImage).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String imgPath = saveImageToExternalStorage(image);

            NewsPost newsPost = new NewsPost(title, description, imgPath);
            posts.add(newsPost);
        }
        return posts;
    }

    private String saveImageToExternalStorage(Bitmap image) {
        if (image == null) {return null;}

        String imageName = UUID.randomUUID().toString();
        File imgFile = new File(externalStorageDir, imageName);

        // save image as JPEG file
        try {
            OutputStream outputStream = new FileOutputStream(imgFile);
            image.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imgFile.getAbsolutePath();
    }
}
