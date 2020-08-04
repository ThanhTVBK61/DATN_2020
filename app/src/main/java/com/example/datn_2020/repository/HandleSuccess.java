package com.example.datn_2020.repository.network;

public interface HandleResult<T> {
    abstract void handleResponseResult(T params);
}
