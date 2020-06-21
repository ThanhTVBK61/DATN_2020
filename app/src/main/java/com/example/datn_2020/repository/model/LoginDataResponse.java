package com.example.datn_2020.repository.model;

import com.google.gson.annotations.SerializedName;

public class LoginDataResponse {

    @SerializedName("Username")
    private String username;

    @SerializedName("EmailUser")
    private String email;

    public LoginDataResponse() {
    }

    public LoginDataResponse(String username, String emailUser) {
        this.username = username;
        this.email = emailUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
