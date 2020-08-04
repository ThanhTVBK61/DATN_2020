package com.example.datn_2020.repository.model;

import com.google.gson.annotations.SerializedName;

public class TripByIdPlaceModel {
    @SerializedName("IdTrip")
    private int idTrip;
    @SerializedName("NameTrip")
    private String nameTrip;

    public int getIdTrip() {
        return idTrip;
    }

    public void setIdTrip(int idTrip) {
        this.idTrip = idTrip;
    }

    public String getNameTrip() {
        return nameTrip;
    }

    public void setNameTrip(String nameTrip) {
        this.nameTrip = nameTrip;
    }
}
