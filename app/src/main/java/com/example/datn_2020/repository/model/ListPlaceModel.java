package com.example.datn_2020.repository.model;

import com.google.gson.annotations.SerializedName;

public class ListPlaceModel {

    @SerializedName("NamePlace")
    private String namePlace;
    @SerializedName("AddressPlace")
    private String address;
    @SerializedName("ListImageUrl")
    private String srcImage;
    @SerializedName("CoordinatePlace")
    private String coordinatePlace;
    //    private int numberStar;
    //    private int sumReview;
    //    private int favourite;

    public ListPlaceModel() {
    }

    public ListPlaceModel(String namePlace, String address,String srcImage, String coordinatePlace) {
        this.srcImage = srcImage;
        this.namePlace = namePlace;
        this.address = address;
        this.coordinatePlace = coordinatePlace;
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

    //    public int getFavourite() {
//        return favourite;
//    }
//
//    public void setFavourite(int favourite) {
//        this.favourite = favourite;
//    }
//
//    public int getNumberStar() {
//        return numberStar;
//    }
//
//    public void setNumberStar(int numberStar) {
//        this.numberStar = numberStar;
//    }
//
//    public int getSumReview() {
//        return sumReview;
//    }
//
//    public void setSumReview(int sumReview) {
//        this.sumReview = sumReview;
//    }
}
