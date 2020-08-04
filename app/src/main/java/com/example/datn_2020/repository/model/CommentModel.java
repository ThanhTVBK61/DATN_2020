package com.example.datn_2020.repository.model;

import com.google.gson.annotations.SerializedName;

public class CommentModel {
    @SerializedName("IdComment")
    private int idComment;
    @SerializedName("IdPostComment")
    private int idPost;
    @SerializedName("ContentComment")
    private String content;
    @SerializedName("Time")
    private String time;

    @SerializedName("IdUserComment")
    private int idUser;
    @SerializedName("Username")
    private String username;
    @SerializedName("ImageUrlUser")
    private String imageUrlUser;
    @SerializedName("SumLike")
    private int sumLike;
    @SerializedName("LikeComment")
    private int like;

    public CommentModel() {
    }

    public CommentModel(int idComment, int idPost, String content, String time, int idUser, String username, String imageUrlUser, int sumLike, int like) {
        this.idComment = idComment;
        this.idPost = idPost;
        this.content = content;
        this.time = time;
        this.idUser = idUser;
        this.username = username;
        this.imageUrlUser = imageUrlUser;
        this.sumLike = sumLike;
        this.like = like;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageUrlUser() {
        return imageUrlUser;
    }

    public void setImageUrlUser(String imageUrlUser) {
        this.imageUrlUser = imageUrlUser;
    }

    public int getSumLike() {
        return sumLike;
    }

    public void setSumLike(int sumLike) {
        this.sumLike = sumLike;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getIdComment() {
        return idComment;
    }

    public void setIdComment(int idComment) {
        this.idComment = idComment;
    }

    public int getIdPost() {
        return idPost;
    }

    public void setIdPost(int idPost) {
        this.idPost = idPost;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
