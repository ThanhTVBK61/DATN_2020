package com.example.datn_2020.repository.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TripInformationResponse {

    @SerializedName("isSuccess")
    private boolean isSuccess;
    @SerializedName("data")
    private ArrayList<TripInformationModel> tripInformationModels;

    public TripInformationResponse() {
        tripInformationModels = new ArrayList<>();
    }

    public TripInformationResponse(boolean isSuccess, ArrayList<TripInformationModel> tripInformationModels) {
        this.isSuccess = isSuccess;
        this.tripInformationModels = tripInformationModels;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public ArrayList<TripInformationModel> getTripInformationModels() {
        return tripInformationModels;
    }

    public void setTripInformationModels(ArrayList<TripInformationModel> tripInformationModels) {
        this.tripInformationModels = tripInformationModels;
    }
}
