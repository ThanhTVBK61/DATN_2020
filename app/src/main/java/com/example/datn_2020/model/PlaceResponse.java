package com.example.datn_2020.model;

import com.google.gson.annotations.SerializedName;

public class PlaceResponse {

    private String srcImage;
    private int favourite;
    private String namePlace;
    private int numberStar;
    private int sumReview;
    private String address;

    public PlaceResponse() {
    }

    public PlaceResponse(String srcImage, int favourite, String namePlace, int numberStar, int sumReview, String address) {
        this.srcImage = srcImage;
        this.favourite = favourite;
        this.namePlace = namePlace;
        this.numberStar = numberStar;
        this.sumReview = sumReview;
        this.address = address;
    }

    public int getFavourite() {
        return favourite;
    }

    public void setFavourite(int favourite) {
        this.favourite = favourite;
    }

    public String getSrcImage() {
        return srcImage;
    }

    public void setSrcImage(String srcImage) {
        this.srcImage = srcImage;
    }

    public String getNamePlace() {
        return namePlace;
    }

    public void setNamePlace(String namePlace) {
        this.namePlace = namePlace;
    }

    public int getNumberStar() {
        return numberStar;
    }

    public void setNumberStar(int numberStar) {
        this.numberStar = numberStar;
    }

    public int getSumReview() {
        return sumReview;
    }

    public void setSumReview(int sumReview) {
        this.sumReview = sumReview;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
