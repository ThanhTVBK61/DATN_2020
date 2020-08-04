package com.example.datn_2020.repository.model;

public class DeleteTripModel {
    private int idUser;
    private int idTrip;

    public DeleteTripModel(int idUser, int idTrip) {
        this.idUser = idUser;
        this.idTrip = idTrip;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdTrip() {
        return idTrip;
    }

    public void setIdTrip(int idTrip) {
        this.idTrip = idTrip;
    }
}
