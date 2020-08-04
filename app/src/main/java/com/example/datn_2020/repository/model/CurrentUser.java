package com.example.datn_2020.repository.model;

import com.example.datn_2020.repository.room.entity.User;

public class CurrentUser {
    private static User INSTANCE;

    private CurrentUser(){}

    public static User getInstance(){
        return INSTANCE;
    }

    public static void setInstance(User update){
        INSTANCE = update;
    }
}
