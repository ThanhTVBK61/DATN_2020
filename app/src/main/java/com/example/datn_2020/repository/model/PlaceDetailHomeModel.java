package com.example.datn_2020.repository.model;

import com.google.gson.annotations.SerializedName;

public class PlaceDetailHomeModel {

    @SerializedName("IdPlace")
    private int idPlace;
    @SerializedName("NamePlace")
    private String namePlace;
    @SerializedName("AddressPlace")
    private String address;
    @SerializedName("ListImageUrl")
    private String srcImage;
    @SerializedName("TypePlace")
    private String type;
    @SerializedName("CoordinatePlace")
    private String coordinatePlace;
    @SerializedName("Location")
    private String location;
    @SerializedName("Price")
    private String price;
    @SerializedName("Quality")
    private String quality;
    @SerializedName("Service")
    private String service;
    @SerializedName("SumRating")
    private int sumRating;

    @SerializedName("TimePlace")
    private String time;
    @SerializedName("DayTimePlace")
    private String day;
    @SerializedName("PhoneNumberPlace")
    private String phoneNumber;
    @SerializedName("EmailPlace")
    private String email;
    @SerializedName("Overview")
    private String overview;

    public PlaceDetailHomeModel() {
    }

    public PlaceDetailHomeModel(int idPlace, String namePlace, String address, String srcImage, String type, String coordinatePlace, String location, String price, String quality, String service, int sumRating, String time, String day, String phoneNumber, String email, String overview) {
        this.idPlace = idPlace;
        this.namePlace = namePlace;
        this.address = address;
        this.srcImage = srcImage;
        this.type = type;
        this.coordinatePlace = coordinatePlace;
        this.location = location;
        this.price = price;
        this.quality = quality;
        this.service = service;
        this.sumRating = sumRating;
        this.time = time;
        this.day = day;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.overview = overview;
    }

    public int getIdPlace() {
        return idPlace;
    }

    public void setIdPlace(int idPlace) {
        this.idPlace = idPlace;
    }

    public String getNamePlace() {
        return namePlace;
    }

    public void setNamePlace(String namePlace) {
        this.namePlace = namePlace;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSrcImage() {
        return srcImage;
    }

    public void setSrcImage(String srcImage) {
        this.srcImage = srcImage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCoordinatePlace() {
        return coordinatePlace;
    }

    public void setCoordinatePlace(String coordinatePlace) {
        this.coordinatePlace = coordinatePlace;
    }

    public float getLocation() {
        if(price != null){
            return Float.parseFloat(location);
        }
        return 0.0f;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public float getPrice() {
        if(price != null){
            return Float.parseFloat(price);
        }
        return 0.0f;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public float getQuality() {
        if(price != null){
            return Float.parseFloat(quality);
        }
        return 0.0f;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public float getService() {
        if(price != null){
            return Float.parseFloat(service);
        }
        return 0.0f;
    }

    public void setService(String service) {
        this.service = service;
    }

    public int getSumRating() {
        return sumRating;
    }

    public void setSumRating(int sumRating) {
        this.sumRating = sumRating;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
