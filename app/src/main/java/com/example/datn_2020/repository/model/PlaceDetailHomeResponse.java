package com.example.datn_2020.repository.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PlaceDetailHomeResponse {

    @SerializedName("isSuccess")
    private Boolean success;

    @SerializedName("data")
    private List<PlaceDetailHomeModel> listPlaceDetail;

    public PlaceDetailHomeResponse() {
        listPlaceDetail = new ArrayList<>();
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<PlaceDetailHomeModel> getListPlaceDetail() {
        return listPlaceDetail;
    }

    public void setListPlaceDetail(ArrayList<PlaceDetailHomeModel> listPlaceDetail) {
        this.listPlaceDetail = listPlaceDetail;
    }
}
