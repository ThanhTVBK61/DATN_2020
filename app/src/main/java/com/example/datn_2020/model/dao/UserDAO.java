package com.example.datn_2020.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.datn_2020.model.entity.User;

@Dao
public interface UserDAO {
    @Query("Select * from user")
    LiveData<User> getUser();

    @Insert
    void insertUser(User user);

    @Query("Delete from user where Username = :username")
    void deleteUser(String username);
}
