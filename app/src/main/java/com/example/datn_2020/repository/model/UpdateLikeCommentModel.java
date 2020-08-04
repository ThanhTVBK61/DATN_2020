package com.example.datn_2020.repository.model;

public class UpdateLikeCommentModel {
    private int idUser;
    private int idComment;
    private boolean like;

    public UpdateLikeCommentModel(int idUser, int idComment, boolean like) {
        this.idUser = idUser;
        this.idComment = idComment;
        this.like = like;
    }

    public void setIdComment(int idComment) {
        this.idComment = idComment;
    }

    public void setLike(boolean like) {
        this.like = like;
    }
}
