package com.example.datn_2020.repository.model;

public class Report {
    private int idUserSend;
    private String nameUserSend;
    private int idPlace;
    private String report;
    private String type;
    private String time;

    public void setNameUserSend(String nameUserSend) {
        this.nameUserSend = nameUserSend;
    }

    public void setIdUserSend(int idUserSend) {
        this.idUserSend = idUserSend;
    }

    public void setIdPlace(int idPlace) {
        this.idPlace = idPlace;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
