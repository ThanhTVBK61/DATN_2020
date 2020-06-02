package com.example.datn_2020.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PlaceDetailHomeResponse {

    @SerializedName("Success")
    private Boolean success;

    @SerializedName("data")
    private ArrayList<PlaceDetailHomeModel> listPlaceDetail;

    public PlaceDetailHomeResponse() {
    }

    public PlaceDetailHomeResponse(Boolean success, ArrayList<PlaceDetailHomeModel> listPlaceDetail) {
        this.success = success;
        this.listPlaceDetail = listPlaceDetail;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public ArrayList<PlaceDetailHomeModel> getListPlaceDetail() {
        return listPlaceDetail;
    }

    public void setListPlaceDetail(ArrayList<PlaceDetailHomeModel> listPlaceDetail) {
        this.listPlaceDetail = listPlaceDetail;
    }
}
