package com.example.datn_2020.repository.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NotificationResponse {
    @SerializedName("isSuccess")
    private boolean isSuccess;
    @SerializedName("data")
    private ArrayList<NotificationModel> notificationModels;

    public boolean isSuccess() {
        return isSuccess;
    }

    public ArrayList<NotificationModel> getNotificationModels() {
        return notificationModels;
    }
}
