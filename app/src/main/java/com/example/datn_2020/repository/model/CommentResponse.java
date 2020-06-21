package com.example.datn_2020.repository.model;

public class CommentResponse {
    private String contentComment;
    private int numLikedComment;

    public CommentResponse() {
    }

    public CommentResponse(String contentComment, int numLikedComment) {
        this.contentComment = contentComment;
        this.numLikedComment = numLikedComment;
    }

    public String getContentComment() {
        return contentComment;
    }

    public void setContentComment(String contentComment) {
        this.contentComment = contentComment;
    }

    public int getNumLikedComment() {
        return numLikedComment;
    }

    public void setNumLikedComment(int numLikedComment) {
        this.numLikedComment = numLikedComment;
    }
}
