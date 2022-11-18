package com.example.newsapp.models;

public class ModelNews {
    //variables
    String uid, id, title, description, date, categoryId, url;
    long timestamp, viewCount;

    //empty constructor, required for firebase
    public ModelNews() {

    }

    //constructor with all params
    public ModelNews(String uid, String id, String title, String description, String date, String categoryId, String url, long timestamp, long viewCount) {
        this.uid = uid;
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.categoryId = categoryId;
        this.url = url;
        this.timestamp = timestamp;
        this.viewCount = viewCount;
    }

    /*----Getter/Setters----*/
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getViewCount() {
        return viewCount;
    }

    public void setViewCount(long viewCount) {
        this.viewCount = viewCount;
    }
}
