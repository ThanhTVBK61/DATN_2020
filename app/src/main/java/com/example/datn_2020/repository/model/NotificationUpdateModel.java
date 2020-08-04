package com.example.datn_2020.repository.model;

public class NotificationUpdateModel {
    private int idUser;
    private int idNotification;
    private int idTrip;

    public NotificationUpdateModel(int idUser, int idNotification, int idTrip) {
        this.idUser = idUser;
        this.idNotification = idNotification;
        this.idTrip = idTrip;
    }

    public NotificationUpdateModel(int idNotification) {
        this.idNotification = idNotification;
    }

    public NotificationUpdateModel() {
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setIdNotification(int idNotification) {
        this.idNotification = idNotification;
    }

    public void setIdTrip(int idTrip) {
        this.idTrip = idTrip;
    }
}
