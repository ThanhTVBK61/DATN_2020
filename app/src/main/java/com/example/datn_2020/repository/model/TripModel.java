package com.example.datn_2020.repository.model;

import com.google.gson.annotations.SerializedName;

public class TripModel {

    @SerializedName("IdTrip")
    private int id;
    @SerializedName("NameTrip")
    private String name;
    @SerializedName("ImageUrl")
    private String imageUrl;
    @SerializedName("TimeTrip")
    private String time;
    @SerializedName("DescriptionTrip")
    private String description;

    public TripModel() {
    }

    public TripModel(int id,String name) {
        this.name = name;
        this.id = id;
    }

    public TripModel(int id, String name, String imageUrl, String time, String description) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.time = time;
        this.description = description;
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

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
}
