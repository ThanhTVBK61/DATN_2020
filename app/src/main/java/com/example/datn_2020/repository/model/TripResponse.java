package com.example.datn_2020.repository.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TripResponse {

    @SerializedName("isSuccess")
    private Boolean success;

    @SerializedName("data")
    private ArrayList<TripModel> tripModelList;

    public TripResponse() {
        this.tripModelList = new ArrayList<>();
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public ArrayList<TripModel> getTripModelList() {
        return tripModelList;
    }

    public void setTripModelList(ArrayList<TripModel> tripModelList) {
        this.tripModelList = tripModelList;
    }
}
