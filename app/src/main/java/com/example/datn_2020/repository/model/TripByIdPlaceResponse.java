package com.example.datn_2020.repository.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TripByIdPlaceResponse {
    @SerializedName("isSuccess")
    private boolean isSuccess;
    @SerializedName("data")
    private ArrayList<TripByIdPlaceModel> tripByIdPlaceModels;

    public boolean isSuccess() {
        return isSuccess;
    }

    public ArrayList<TripByIdPlaceModel> getTripByIdPlaceModels() {
        return tripByIdPlaceModels;
    }
}
