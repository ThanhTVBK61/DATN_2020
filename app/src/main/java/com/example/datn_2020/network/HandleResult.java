package com.example.datn_2020.network;

public interface HandleResult<T> {
    abstract void handleResponseResult(T params);
}
