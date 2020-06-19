package com.example.datn_2020.model;

import com.google.gson.annotations.SerializedName;

public class ApiResponse {

    @SerializedName("isSuccess")
    private Boolean isSuccess;

    public Boolean getIsSuccess() {
        return isSuccess;
    }
}
