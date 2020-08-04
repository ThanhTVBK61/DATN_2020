package com.example.datn_2020.repository.model;

public class ChangeRating {
    private float idUser;
    private float idPlace;
    private float locationRating;
    private float priceRating;
    private float qualityRating;
    private float serviceRating;

    public ChangeRating() {
    }

    public ChangeRating(float idUser, float idPlace, float locationRating, float priceRating, float qualityRating, float serviceRating) {
        this.idUser = idUser;
        this.idPlace = idPlace;
        this.locationRating = locationRating;
        this.priceRating = priceRating;
        this.qualityRating = qualityRating;
        this.serviceRating = serviceRating;
    }

    public void setIdUser(float idUser) {
        this.idUser = idUser;
    }

    public void setIdPlace(float idPlace) {
        this.idPlace = idPlace;
    }

    public void setLocationRating(float locationRating) {
        this.locationRating = locationRating;
    }

    public void setPriceRating(float priceRating) {
        this.priceRating = priceRating;
    }

    public void setQualityRating(float qualityRating) {
        this.qualityRating = qualityRating;
    }

    public void setServiceRating(float serviceRating) {
        this.serviceRating = serviceRating;
    }
}
