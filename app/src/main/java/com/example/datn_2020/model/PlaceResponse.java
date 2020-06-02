package com.example.datn_2020.model;

import com.google.gson.annotations.SerializedName;

public class PlaceResponse {

    @SerializedName("srcImage")
    private String srcImage;
    @SerializedName("favourite")
    private int favourite;
    @SerializedName("namePlace")
    private String namePlace;
    @SerializedName("numberStar")
    private int numberStar;
    @SerializedName("sumReview")
    private int sumReview;

    public PlaceResponse() {
    }

    public PlaceResponse(String srcImage, int favourite, String namePlace, int numberStar, int sumReview) {
        this.srcImage = srcImage;
        this.favourite = favourite;
        this.namePlace = namePlace;
        this.numberStar = numberStar;
        this.sumReview = sumReview;
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
}
