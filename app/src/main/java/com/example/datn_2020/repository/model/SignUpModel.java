package com.example.datn_2020.repository.model;

public class SignUpModel {
    private String username;
    private String password;
    private String email;
    private String address;
    private String sex;

    public SignUpModel() {
    }

    public SignUpModel(String username, String password, String email, String address, String sex) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.sex = sex;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
