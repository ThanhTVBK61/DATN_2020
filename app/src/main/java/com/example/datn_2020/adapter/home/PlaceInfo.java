package com.example.datn_2020.adapter.home;

import com.google.android.gms.maps.model.LatLng;

public class PlaceInfo {
    private String name;
    private String Address;
    private String rating;
    private LatLng latLng;

    public PlaceInfo(String name, String address, String rating, LatLng latLng) {
        this.name = name;
        Address = address;
        this.rating = rating;
        this.latLng = latLng;
    }

    public PlaceInfo() {
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}
