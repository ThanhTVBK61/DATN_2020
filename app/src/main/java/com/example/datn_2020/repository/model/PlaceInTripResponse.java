package com.example.datn_2020.repository.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PlaceInTripResponse {

    @SerializedName("isSuccess")
    private Boolean success;

    @SerializedName("data")
    private ArrayList<PlaceInTripModel> placeInTripModels;

    public PlaceInTripResponse() {
        placeInTripModels = new ArrayList<>();
    }

    public PlaceInTripResponse(Boolean success, ArrayList<PlaceInTripModel> placeInTripModels) {
        this.success = success;
        this.placeInTripModels = placeInTripModels;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public ArrayList<PlaceInTripModel> getPlaceInTripModels() {
        return placeInTripModels;
    }

    public void setPlaceInTripModels(ArrayList<PlaceInTripModel> placeInTripModels) {
        this.placeInTripModels = placeInTripModels;
    }
}
