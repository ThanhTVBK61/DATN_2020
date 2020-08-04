package com.example.datn_2020.repository.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class InformationAccountResponse {

    @SerializedName("isSuccess")
    private boolean isSuccess;

    @SerializedName("data")
    private List<InformationAccountModel> informationAccountModel;

    public InformationAccountResponse() {
        informationAccountModel = new ArrayList<>();
    }

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public InformationAccountModel getInformationAccountModel(int position) {
        return informationAccountModel.get(position);
    }

    public void setInformationAccountModel(ArrayList<InformationAccountModel> informationAccountModel) {
        this.informationAccountModel = informationAccountModel;
    }
}
