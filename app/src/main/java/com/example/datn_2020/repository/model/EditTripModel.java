package com.example.datn_2020.repository.model;

public class EditTripModel {
    private int idTrip;
    private String newName;
    private String newDescription;

    public EditTripModel(int idTrip, String newName, String newDescription) {
        this.idTrip = idTrip;
        this.newName = newName;
        this.newDescription = newDescription;
    }

    public int getIdTrip() {
        return idTrip;
    }

    public void setIdTrip(int idTrip) {
        this.idTrip = idTrip;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public String getNewDescription() {
        return newDescription;
    }

    public void setNewDescription(String newDescription) {
        this.newDescription = newDescription;
    }
}
