package com.example.datn_2020.model;

import java.util.List;

public class PostResponse {
    private String name;
    private String url;
    private int rating;
    private String time;
    private String content;
    private int numLike;
    private int numComment;

    public PostResponse() {
    }

    public PostResponse(String name, String url, int rating, String time, String content, int numLike, int numComment) {
        this.name = name;
        this.url = url;
        this.rating = rating;
        this.time = time;
        this.content = content;
        this.numLike = numLike;
        this.numComment = numComment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getNumLike() {
        return numLike;
    }

    public void setNumLike(int numLike) {
        this.numLike = numLike;
    }

    public int getNumComment() {
        return numComment;
    }

    public void setNumComment(int numComment) {
        this.numComment = numComment;
    }

}
