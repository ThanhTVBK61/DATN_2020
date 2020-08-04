package com.example.datn_2020.repository.model;

import com.google.gson.annotations.SerializedName;

public class AllUserModel {

    @SerializedName("IdUser")
    private int idUser;

    @SerializedName("Username")
    private String username;

    @SerializedName("ImageUrlUser")
    private String image;

    public AllUserModel() {
    }

    public AllUserModel(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
