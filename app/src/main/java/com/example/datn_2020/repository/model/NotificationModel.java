package com.example.datn_2020.repository.model;

public class NotificationModel {
    private String imageAccount;
    private String namePlace;
    private String nameAccount;

    public NotificationModel() {
    }

    public NotificationModel(String imageAccount, String namePlace, String nameAccount) {
        this.imageAccount = imageAccount;
        this.namePlace = namePlace;
        this.nameAccount = nameAccount;
    }

    public String getImageAccount() {
        return imageAccount;
    }

    public void setImageAccount(String imageAccount) {
        this.imageAccount = imageAccount;
    }

    public String getNamePlace() {
        return namePlace;
    }

    public void setNamePlace(String namePlace) {
        this.namePlace = namePlace;
    }

    public String getNameAccount() {
        return nameAccount;
    }

    public void setNameAccount(String nameAccount) {
        this.nameAccount = nameAccount;
    }
}
