package com.example.datn_2020.repository.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CommentResponse {

    @SerializedName("isSuccess")
    private boolean isSuccess;
    @SerializedName("data")
    private ArrayList<CommentModel> commentModels;

    public boolean isSuccess() {
        return isSuccess;
    }

    public ArrayList<CommentModel> getCommentModels() {
        return commentModels;
    }
}
