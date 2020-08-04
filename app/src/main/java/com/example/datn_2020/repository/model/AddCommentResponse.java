package com.example.datn_2020.repository.model;

import com.google.gson.annotations.SerializedName;

public class AddCommentResponse {
    @SerializedName("isSuccess")
    private boolean isSuccess;
    @SerializedName("data")
    private int data;

    public boolean isSuccess() {
        return isSuccess;
    }

    public int getData() {
        return data;
    }
}
