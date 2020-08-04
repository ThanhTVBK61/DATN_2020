package com.example.datn_2020.repository.model;

import com.google.gson.annotations.SerializedName;

public class LoginDataResponse {

    @SerializedName("Id")
    private int idUser;

    @SerializedName("Username")
    private String username;

    @SerializedName("EmailUser")
    private String email;

    public LoginDataResponse() {
    }

    public LoginDataResponse(int id, String username, String email) {
        this.idUser = id;
        this.username = username;
        this.email = email;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int id) {
        this.idUser = id;
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
