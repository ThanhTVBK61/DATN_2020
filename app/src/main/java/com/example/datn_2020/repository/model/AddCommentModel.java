package com.example.datn_2020.repository.model;

public class AddCommentModel {
    private int idUser;
    private int idPost;
    private String content;
    private String time;

    public AddCommentModel(int idUser, int idPost, String content, String time) {
        this.idUser = idUser;
        this.idPost = idPost;
        this.content = content;
        this.time = time;
    }

    public AddCommentModel() {
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setIdPost(int idPost) {
        this.idPost = idPost;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
