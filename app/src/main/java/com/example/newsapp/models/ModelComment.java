package com.example.newsapp.models;

public class ModelComment {

    //variables
    String id, newsId, timestamp, comment, uid;

    //constructor, empty required by firebase
    public ModelComment() {
    }

    //constructor with all params
    public ModelComment(String id, String newsId, String timestamp, String comment, String uid) {
        this.id = id;
        this.newsId = newsId;
        this.timestamp = timestamp;
        this.comment = comment;
        this.uid = uid;
    }


    /*-----Getter/Setters-----*/
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
