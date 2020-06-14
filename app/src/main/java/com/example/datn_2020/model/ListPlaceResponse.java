package com.example.datn_2020.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ListPlaceResponse {

    @SerializedName("isSuccess")
    private Boolean isSuccess;

    @SerializedName("data")
    private ArrayList<ListPlaceModel> listPlaceModels;

    public ListPlaceResponse() {
        listPlaceModels = new ArrayList<>();
    }

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public ArrayList<ListPlaceModel> getListPlaceModels() {
        return listPlaceModels;
    }

    public void setListPlaceModels(ArrayList<ListPlaceModel> listPlaceModels) {
        this.listPlaceModels = listPlaceModels;
    }
}
