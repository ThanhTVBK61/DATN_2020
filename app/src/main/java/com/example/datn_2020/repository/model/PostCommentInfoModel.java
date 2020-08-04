package com.example.datn_2020.repository.model;

import com.google.gson.annotations.SerializedName;

public class PostCommentInfoModel {

    @SerializedName("IdUser")
    private int idAdmin;
    @SerializedName("Username")
    private String username;
    @SerializedName("IdPost")
    private int idPost;
    @SerializedName("Time")
    private String time;
    @SerializedName("ContentPost")
    private String content;
    @SerializedName("SumComment")
    private int sumComment;
    @SerializedName("Location")
    private float location;
    @SerializedName("Price")
    private float price;
    @SerializedName("Quality")
    private float quality;
    @SerializedName("Service")
    private float service;

    private boolean liked = false;

    public String getUsername() {
        return username;
    }

    public int getIdPost() {
        return idPost;
    }

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }

    public int getSumComment() {
        return sumComment;
    }

    public float getLocation() {
        return location;
    }

    public float getPrice() {
        return price;
    }

    public float getQuality() {
        return quality;
    }

    public float getService() {
        return service;
    }

    public float getRating() {
        return (location + price + quality + service) / 4.0f;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public int getIdAdmin() {
        return idAdmin;
    }
}
