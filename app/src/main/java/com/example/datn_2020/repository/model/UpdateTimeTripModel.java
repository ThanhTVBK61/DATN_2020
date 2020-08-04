package com.example.datn_2020.repository.model;

public class UpdateTimeTripModel {
    private int idTrip;
    private String time;

    public UpdateTimeTripModel(int idTrip, String time) {
        this.idTrip = idTrip;
        this.time = time;
    }

    public UpdateTimeTripModel() {
    }
}
