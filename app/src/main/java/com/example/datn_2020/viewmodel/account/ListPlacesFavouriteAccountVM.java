package com.example.datn_2020.viewmodel.account;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.datn_2020.repository.HandleError;
import com.example.datn_2020.repository.HandleSuccess;
import com.example.datn_2020.repository.HomeRepository;
import com.example.datn_2020.repository.model.ApiResponse;
import com.example.datn_2020.repository.model.FavouritePlaceCheckModel;
import com.example.datn_2020.repository.model.ListPlaceResponse;
import com.example.datn_2020.repository.model.PlaceDetailHomeModel;
import com.example.datn_2020.repository.model.PlaceDetailHomeResponse;
import com.example.datn_2020.repository.model.PlaceModel;

import java.util.ArrayList;

public class ListPlacesFavouriteAccountVM extends ViewModel {

    private HomeRepository homeRepository = new HomeRepository();
    private MutableLiveData<String> errorData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<PlaceModel>> listPlaceFavourite = new MutableLiveData<>();
    private MutableLiveData<PlaceDetailHomeModel> currentPlaceDetail = new MutableLiveData<>();
    private MutableLiveData<Boolean> checkFavouriteResponse = new MutableLiveData<>();

    public void loadAllPlaceFavourite(int idUser) {
        homeRepository.loadAllFavouritePlace(idUser, new HandleSuccess<ListPlaceResponse>() {
            @Override
            public void handleSuccessResult(ListPlaceResponse results) {
                if(results.getIsSuccess())
                {
                    Log.i("Get Data Sever: ","Success call api to get ListPlace");
                    listPlaceFavourite.setValue(results.getPlaceModels());
                }else if(!results.getIsSuccess()){
                    Log.i("Get Data Sever: ","False call api to get ListPlace");
                }else {
                    Log.i("Get Data Sever: ","Api ListPlace return nothing");
                }
            }
        }, new HandleError<String>() {
            @Override
            public void handleErrorResult(String err) {
                errorData.setValue(err);
            }
        });
    }

    public MutableLiveData<ArrayList<PlaceModel>> getListPlaceFavourite() {
        return listPlaceFavourite;
    }


    public void loadPlaceById(int id) {
        homeRepository.getPlaceById(id, new HandleSuccess<PlaceDetailHomeResponse>() {
            @Override
            public void handleSuccessResult(PlaceDetailHomeResponse results) {
                if(results.getSuccess())
                {
                    Log.i("Get Data Sever: ","Success call api to get ListPlace");
                    currentPlaceDetail.setValue(results.getListPlaceDetail().get(0));
                }else if(!results.getSuccess()){
                    Log.i("Get Data Sever: ","False call api to get ListPlace");
                }else {
                    Log.i("Get Data Sever: ","Api ListPlace return nothing");
                }
            }
        }, new HandleError<String>() {
            @Override
            public void handleErrorResult(String err) {
                errorData.setValue(err);
            }
        });
    }
    public MutableLiveData<PlaceDetailHomeModel> getCurrentPlaceDetail() {
        return currentPlaceDetail;
    }


    public void updateFavouritePlace(FavouritePlaceCheckModel favouritePlaceCheckModel){
        homeRepository.updateFavouritePlace(favouritePlaceCheckModel, new HandleSuccess<ApiResponse>() {
            @Override
            public void handleSuccessResult(ApiResponse apiResponse) {
                checkFavouriteResponse.setValue(apiResponse.getIsSuccess());
            }
        }, new HandleError<String>() {
            @Override
            public void handleErrorResult(String err) {
                errorData.setValue(err);
            }
        });
    }
}
