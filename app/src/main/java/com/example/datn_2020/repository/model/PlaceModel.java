package com.example.datn_2020.repository.model;

import com.google.gson.annotations.SerializedName;

public class PlaceModel {

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
    @SerializedName("MyFavourite")
    private int favourite;

    public PlaceModel() {
    }

    public PlaceModel(int idPlace, String namePlace, String address, String srcImage, String type, String coordinatePlace, String location, String price, String quality, String service, int sumRating, int favourite) {
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
        this.favourite = favourite;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public float getPrice() {
        if(price != null){
            return Float.parseFloat(price);
        }
        return 0.0f;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getSumRating() {
        return sumRating;
    }

    public void setSumRating(int sumRating) {
        this.sumRating = sumRating;
    }

    public int getFavourite() {
        return favourite;
    }

    public void setFavourite(int favourite) {
        this.favourite = favourite;
    }
}
