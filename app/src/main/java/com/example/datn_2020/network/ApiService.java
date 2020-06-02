package com.example.datn_2020.network;

import com.example.datn_2020.model.InformationAccountResponse;
import com.example.datn_2020.model.PlaceDetailHomeResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/getinfoaccount")
    Observable<InformationAccountResponse> getInfoAccountRequest();

    @GET("/getinfoplace")
    Observable<PlaceDetailHomeResponse> getInfoPlaceRequest();
}



