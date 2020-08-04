package com.example.datn_2020.repository.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.datn_2020.repository.room.entity.User;

import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface UserDAO {
    @Query("Select * from user")
    Maybe<User> getUser();

    @Insert
    Single<Long> insert(User user);

    @Delete
    Single<Integer> delete(User user);
}
