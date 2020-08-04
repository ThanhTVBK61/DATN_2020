package com.example.datn_2020.repository.model;

public class UpdatePlaceInTripModel {
    private int idTrip;
    private int idPlace;

    public UpdatePlaceInTripModel(int idTrip, int idPlace) {
        this.idTrip = idTrip;
        this.idPlace = idPlace;
    }

    public int getIdTrip() {
        return idTrip;
    }

    public void setIdTrip(int idTrip) {
        this.idTrip = idTrip;
    }

    public int getIdPlace() {
        return idPlace;
    }

    public void setIdPlace(int idPlace) {
        this.idPlace = idPlace;
    }
}
