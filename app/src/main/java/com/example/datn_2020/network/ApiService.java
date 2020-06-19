package com.example.datn_2020.network;

import com.example.datn_2020.model.ApiResponse;
import com.example.datn_2020.model.ChangePasswordModel;
import com.example.datn_2020.model.InformationAccountResponse;
import com.example.datn_2020.model.ListPlaceResponse;
import com.example.datn_2020.model.LoginModel;
import com.example.datn_2020.model.LoginResponse;
import com.example.datn_2020.model.PlaceDetailHomeResponse;
import com.example.datn_2020.model.SignUpModel;

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



