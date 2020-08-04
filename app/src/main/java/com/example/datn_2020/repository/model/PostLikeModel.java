package com.example.datn_2020.repository.model;

import com.google.gson.annotations.SerializedName;

public class PostLikeModel {
    @SerializedName("CountIdLike")
    private int countIdLike;

    public int getCountIdLike() {
        return countIdLike;
    }
}
