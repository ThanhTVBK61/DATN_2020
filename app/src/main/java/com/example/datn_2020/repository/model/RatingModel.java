package com.example.datn_2020.repository.model;

import com.google.gson.annotations.SerializedName;

public class RatingModel {
    @SerializedName("LocationRating")
    private float locationRating = -1;
    @SerializedName("PriceRating")
    private float priceRating;
    @SerializedName("QualityRating")
    private float qualityRating;
    @SerializedName("ServiceRating")
    private float serviceRating;

    public RatingModel() {
    }

    public RatingModel(float locationRating, float priceRating, float qualityRating, float serviceRating) {
        this.locationRating = locationRating;
        this.priceRating = priceRating;
        this.qualityRating = qualityRating;
        this.serviceRating = serviceRating;
    }

    public float getLocationRating() {
        return locationRating;
    }

    public void setLocationRating(float locationRating) {
        this.locationRating = locationRating;
    }

    public float getPriceRating() {
        return priceRating;
    }

    public void setPriceRating(float priceRating) {
        this.priceRating = priceRating;
    }

    public float getQualityRating() {
        return qualityRating;
    }

    public void setQualityRating(float qualityRating) {
        this.qualityRating = qualityRating;
    }

    public float getServiceRating() {
        return serviceRating;
    }

    public void setServiceRating(float serviceRating) {
        this.serviceRating = serviceRating;
    }
}
