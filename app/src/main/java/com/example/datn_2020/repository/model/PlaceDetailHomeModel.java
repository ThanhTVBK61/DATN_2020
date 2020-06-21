package com.example.datn_2020.repository.model;

import com.google.gson.annotations.SerializedName;

public class PlaceDetailHomeModel {

    @SerializedName("NamePlace")
    private String name;

    @SerializedName("TimePlace")
    private String time;

    @SerializedName("DayTimePlace")
    private String day;

    @SerializedName("PhoneNumberPlace")
    private int phoneNumber;

    @SerializedName("EmailPlace")
    private String email;

    @SerializedName("AddressPlace")
    private String address;

    @SerializedName("TypePlace")
    private String type;

    @SerializedName("Overview")
    private String overview;

    @SerializedName("ListImageUrl")
    private String listImageUrl;

    public PlaceDetailHomeModel() {
    }

    public PlaceDetailHomeModel(String name, String time, String day, int phoneNumber, String email, String address, String type, String overview, String listImageUrl) {
        this.name = name;
        this.time = time;
        this.day = day;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.type = type;
        this.overview = overview;
        this.listImageUrl = listImageUrl;
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

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getListImageUrl() {
        return listImageUrl;
    }

    public void setListImageUrl(String listImageUrl) {
        this.listImageUrl = listImageUrl;
    }
}
