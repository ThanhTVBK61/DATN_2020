package com.example.datn_2020.repository.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class InformationAccountModel {

    @SerializedName("NameUser")
    private String name;

    @SerializedName("BirthdayUser")
    private String datetime;

    @SerializedName("Sex")
    private int sex;

    @SerializedName("DescriptionUser")
    private String description;

    public InformationAccountModel() {
    }

    public InformationAccountModel(String name, String datetime, int sex, String description) {
        this.name = name;
        this.datetime = datetime;
        this.sex = sex;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NonNull
    @Override
    public String toString() {
        return getName() + " " + getDatetime() + " " + getDescription() + " "+ getSex();
    }
}
