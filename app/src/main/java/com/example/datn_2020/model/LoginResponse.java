package com.example.datn_2020.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("isSuccess")
    private Boolean isSuccess;

    @SerializedName("data")
    private LoginDataResponse mLoginDataResponse;

    public LoginResponse() {
        mLoginDataResponse = new LoginDataResponse();
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public LoginDataResponse getmLoginDataResponse() {
        return mLoginDataResponse;
    }
}
