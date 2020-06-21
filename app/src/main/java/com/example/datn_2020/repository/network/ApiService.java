package com.example.datn_2020.repository.network;

import com.example.datn_2020.repository.room.ApiResponse;
import com.example.datn_2020.repository.model.ChangePasswordModel;
import com.example.datn_2020.repository.model.InformationAccountResponse;
import com.example.datn_2020.repository.model.ListPlaceResponse;
import com.example.datn_2020.repository.model.LoginModel;
import com.example.datn_2020.repository.model.LoginResponse;
import com.example.datn_2020.repository.model.PlaceDetailHomeResponse;
import com.example.datn_2020.repository.model.SignUpModel;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    //Login
    @POST("/login")
    Observable<LoginResponse> postLogin(@Body LoginModel mLogin);

    @POST("/signup")
    Observable<Boolean> postSignUp(@Body SignUpModel mSignUp);

    //***
    @GET("/getinfoaccount")
    Observable<InformationAccountResponse> getInfoAccountRequest();

    @GET("/getinfoplace")
    Observable<PlaceDetailHomeResponse> getInfoPlaceRequest();

    @GET("/getlistplace")
    Observable<ListPlaceResponse> getListPlaceRequest(@Query("idUser") String idUser,
                                                      @Query("typePlace") String typePlace);

    //Account
    @POST("/changepassword")
    Observable<ApiResponse> postChangePass(@Body ChangePasswordModel mChangePasswordModel);
}



