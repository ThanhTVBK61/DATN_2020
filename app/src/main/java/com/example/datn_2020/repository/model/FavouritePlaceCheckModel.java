package com.example.datn_2020.repository.model;

public class FavouritePlaceCheckModel {
    private int idPlace;
    private int idUser;
    private boolean favourite;

    public FavouritePlaceCheckModel() {
    }

    public FavouritePlaceCheckModel(int idPlace, int idUser, boolean favourite) {
        this.idPlace = idPlace;
        this.idUser = idUser;
        this.favourite = favourite;
    }

    public int getIdPlace() {
        return idPlace;
    }

    public void setIdPlace(int idPlace) {
        this.idPlace = idPlace;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }
}
