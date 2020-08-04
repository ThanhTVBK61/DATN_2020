package com.example.datn_2020.repository.room;

import com.google.gson.annotations.SerializedName;

public class ApiResponse {

    @SerializedName("isSuccess")
    private Boolean isSuccess;

    public Boolean getIsSuccess() {
        return isSuccess;
    }
}
