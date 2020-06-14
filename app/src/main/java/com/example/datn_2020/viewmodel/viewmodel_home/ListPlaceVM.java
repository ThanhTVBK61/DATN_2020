package com.example.datn_2020.viewmodel.viewmodel_home;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.datn_2020.model.ListPlaceModel;
import com.example.datn_2020.model.ListPlaceResponse;
import com.example.datn_2020.network.HandleResult;
import com.example.datn_2020.network.home.ListPlaceRequest;

import java.util.ArrayList;

public class ListPlaceVM extends ViewModel {
    private MutableLiveData<ArrayList<ListPlaceModel>> listPlaceModelMutableLiveData = new MutableLiveData<>();
    private ListPlaceRequest listPlaceRequest;

    public MutableLiveData<ArrayList<ListPlaceModel>> getListPlaceModelMutableLiveData() {
        return listPlaceModelMutableLiveData;
    }

    public void setListPlaceModelMutableLiveData(ArrayList<ListPlaceModel> listPlaceModel) {
        this.listPlaceModelMutableLiveData.setValue(listPlaceModel);
    }

    public void loadListPlace(String idUser, String typePlace){
        listPlaceRequest = new ListPlaceRequest(idUser,typePlace);
        listPlaceRequest.getListPlace(new HandleResult<ListPlaceResponse>() {
            @Override
            public void handleResponseResult(ListPlaceResponse results) {
                if(results.getIsSuccess())
                {
                    Log.i("Get Data Sever: ","Success call api to get ListPlace");
                    listPlaceModelMutableLiveData.setValue(results.getListPlaceModels());
                }else if(!results.getIsSuccess()){
                    Log.i("Get Data Sever: ","False call api to get ListPlace");
                }else {
                    Log.i("Get Data Sever: ","Api ListPlace return nothing");
                }
            }
        });
    }
}
