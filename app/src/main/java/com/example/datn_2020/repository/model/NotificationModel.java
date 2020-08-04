package com.example.datn_2020.repository.model;

import com.google.gson.annotations.SerializedName;

public class NotificationModel {
    @SerializedName("IdNotification")
    private int idNotification;
    @SerializedName("IdTrip")
    private int idTripPlace;
    @SerializedName("NameTrip")
    private String nameTripPlace;
    @SerializedName("ImageUrlUser")
    private String imageAccount;
    @SerializedName("Username")
    private String nameAccount;
    @SerializedName("Type")
    private String type;
    @SerializedName("Time")
    private String time;
    @SerializedName("Status")
    private int status;

    public NotificationModel() {
    }

    public NotificationModel(int idNotification, int idTripPlace, String nameTripPlace, String imageAccount, String nameAccount, String type, String time, int status) {
        this.idNotification = idNotification;
        this.idTripPlace = idTripPlace;
        this.nameTripPlace = nameTripPlace;
        this.imageAccount = imageAccount;
        this.nameAccount = nameAccount;
        this.type = type;
        this.time = time;
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImageAccount() {
        return imageAccount;
    }

    public void setImageAccount(String imageAccount) {
        this.imageAccount = imageAccount;
    }

    public int getIdTripPlace() {
        return idTripPlace;
    }

    public void setIdTripPlace(int idTripPlace) {
        this.idTripPlace = idTripPlace;
    }

    public String getNameTripPlace() {
        return nameTripPlace;
    }

    public void setNameTripPlace(String nameTripPlace) {
        this.nameTripPlace = nameTripPlace;
    }

    public String getNameAccount() {
        return nameAccount;
    }

    public void setNameAccount(String nameAccount) {
        this.nameAccount = nameAccount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIdNotification() {
        return idNotification;
    }

    public void setIdNotification(int idNotification) {
        this.idNotification = idNotification;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
