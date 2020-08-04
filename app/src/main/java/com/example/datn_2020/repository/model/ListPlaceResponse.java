package com.example.datn_2020.repository.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ListPlaceResponse {

    @SerializedName("isSuccess")
    private Boolean isSuccess;

    @SerializedName("data")
    private ArrayList<PlaceModel> placeModels;

    public ListPlaceResponse() {
        placeModels = new ArrayList<>();
    }

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public ArrayList<PlaceModel> getPlaceModels() {
        return placeModels;
    }

    public void setPlaceModels(ArrayList<PlaceModel> placeModels) {
        this.placeModels = placeModels;
    }
}
