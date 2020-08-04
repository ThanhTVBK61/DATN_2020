package com.example.datn_2020.repository.model;

import com.google.gson.annotations.SerializedName;

public class TripInformationModel {
    @SerializedName("IdTrip")
    private int id;
    @SerializedName("NameTrip")
    private String name;
    @SerializedName("DescriptionTrip")
    private String description;
    @SerializedName("Username")
    private String username;
    @SerializedName("TimeTrip")
    private String time;

    public TripInformationModel() {
    }

    public TripInformationModel(int id, String name, String description, String username, String time) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.username = username;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
