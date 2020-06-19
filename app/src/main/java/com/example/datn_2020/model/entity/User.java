package com.example.datn_2020.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    public int id;

    @ColumnInfo(name = "Username")
    public String username;

    @ColumnInfo(name = "Email")
    public String email;

    public User() {
    }
}
