package com.example.datn_2020.network;

import com.example.datn_2020.model.InformationAccountResponse;
import com.example.datn_2020.model.ListPlaceResponse;
import com.example.datn_2020.model.PlaceDetailHomeResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/getinfoaccount")
    Observable<InformationAccountResponse> getInfoAccountRequest();

    @GET("/getinfoplace")
    Observable<PlaceDetailHomeResponse> getInfoPlaceRequest();

    @GET("/getlistplace")
    Observable<ListPlaceResponse> getListPlaceRequest(@Query("idUser") String idUser,
                                                      @Query("typePlace") String typePlace);
}



