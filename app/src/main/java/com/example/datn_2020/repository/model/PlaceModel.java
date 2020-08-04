package com.example.datn_2020.repository.model;

import com.google.gson.annotations.SerializedName;

public class ListPlaceModel {

    @SerializedName("IdPlace")
    private int idPlace;
    @SerializedName("NamePlace")
    private String namePlace;
    @SerializedName("AddressPlace")
    private String address;
    @SerializedName("ListImageUrl")
    private String srcImage;
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

    private Boolean favourite = false;

    public ListPlaceModel() {
    }

    public ListPlaceModel(int idPlace, String namePlace, String address, String srcImage, String coordinatePlace, String location, String price, String quality, String service, int sumRating) {
        this.idPlace = idPlace;
        this.namePlace = namePlace;
        this.address = address;
        this.srcImage = srcImage;
        this.coordinatePlace = coordinatePlace;
        this.location = location;
        this.price = price;
        this.quality = quality;
        this.service = service;
        this.sumRating = sumRating;
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

    public String getCoordinatePlace() {
        return coordinatePlace;
    }

    public void setCoordinatePlace(String coordinatePlace) {
        this.coordinatePlace = coordinatePlace;
    }

    public double getLocation() {
        if(price != null){
            return Float.parseFloat(location);
        }
        return 0.0;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getQuality() {
        if(price != null){
            return Float.parseFloat(quality);
        }
        return 0.0;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public double getService() {
        if(price != null){
            return Float.parseFloat(service);
        }
        return 0.0;
    }

    public void setService(String service) {
        this.service = service;
    }

    public double getPrice() {
        if(price != null){
            return Float.parseFloat(price);
        }
        return 0.0;
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

    public Boolean getFavourite() {
        return favourite;
    }

    public void setFavourite(Boolean favourite) {
        this.favourite = favourite;
    }
}
