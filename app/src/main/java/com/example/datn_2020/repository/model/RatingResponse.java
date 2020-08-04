package com.example.datn_2020.repository.model;

import com.google.gson.annotations.SerializedName;

public class RatingResponse {
    @SerializedName("isSuccess")
    private boolean isSuccess;
    @SerializedName("data")
    private RatingModel ratingModel;

    public RatingResponse() {
    }

    public RatingResponse(boolean isSuccess, RatingModel ratingModel) {
        this.isSuccess = isSuccess;
        this.ratingModel = ratingModel;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public RatingModel getRatingModel() {
        return ratingModel;
    }
}
