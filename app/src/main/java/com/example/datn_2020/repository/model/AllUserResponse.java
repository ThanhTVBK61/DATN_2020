package com.example.datn_2020.repository.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AllUserResponse {

    @SerializedName("isSuccess")
    private boolean isSuccess;
    @SerializedName("data")
    private ArrayList<AllUserModel> allUserModels;

    public AllUserResponse() {
        allUserModels = new ArrayList<>();
    }

    public AllUserResponse(boolean isSuccess, ArrayList<AllUserModel> allUserModels) {
        this.isSuccess = isSuccess;
        this.allUserModels = allUserModels;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public ArrayList<AllUserModel> getAllUserModels() {
        return allUserModels;
    }

    public void setAllUserModels(ArrayList<AllUserModel> allUserModels) {
        this.allUserModels = allUserModels;
    }
}
