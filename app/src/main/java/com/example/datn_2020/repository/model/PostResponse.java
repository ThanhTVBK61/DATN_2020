package com.example.datn_2020.repository.model;

public class PostResponse {

    private int id;
    private int idAdminPost;
    private String name;
    private String url;
    private float rating;
    private String time;
    private String content;
    private int numLike;
    private int numComment;
    private boolean liked;

    public PostResponse() {
    }

    public PostResponse(int id, String name, String url, int rating, String time, String content, int numLike, int numComment) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.rating = rating;
        this.time = time;
        this.content = content;
        this.numLike = numLike;
        this.numComment = numComment;
    }

    public int getIdAdminPost() {
        return idAdminPost;
    }

    public void setIdAdminPost(int idAdminPost) {
        this.idAdminPost = idAdminPost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
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

    public void increaseLike() {
        numLike = numLike + 1;
    }

    public void decreaseLike() {
        numLike = numLike - 1;
    }
}
