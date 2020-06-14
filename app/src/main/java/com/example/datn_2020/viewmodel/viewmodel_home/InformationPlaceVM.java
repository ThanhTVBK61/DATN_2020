package com.example.datn_2020.viewmodel.viewmodel_home;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.datn_2020.model.PlaceDetailHomeModel;
import com.example.datn_2020.model.PlaceDetailHomeResponse;
import com.example.datn_2020.network.HandleResult;
import com.example.datn_2020.network.home.InformationPlaceDetailRequest;

public class InformationPlaceVM extends ViewModel {
    private MutableLiveData<PlaceDetailHomeModel> placeDetailHomeModel = new MutableLiveData<>();
    private InformationPlaceDetailRequest informationPlaceDetailRequest;

    public InformationPlaceVM() {
        informationPlaceDetailRequest = new InformationPlaceDetailRequest();
    }

    public MutableLiveData<PlaceDetailHomeModel> getInformationPlaceResponse() {
        return placeDetailHomeModel;
    }

    public void setInformationPlaceResponse(PlaceDetailHomeModel placeDetailHomeModel) {
        this.placeDetailHomeModel.setValue(placeDetailHomeModel);
    }

    public void loadInfoPlaceDetail(){
        informationPlaceDetailRequest.getDataInformationPlaceDetailRequest(new HandleResult<PlaceDetailHomeResponse>() {
            @Override
            public void handleResponseResult(PlaceDetailHomeResponse results) {
                if(results.getSuccess())
                {
                    Log.i("Get Data Sever: ","Success");
                    placeDetailHomeModel.setValue(results.getListPlaceDetail().get(0));
                }else if(!results.getSuccess()){
                    Log.i("Get Data Sever: ","isSuccess: false");
                }else {
                    Log.i("Get Data Sever: ","Can not get data from api");
                }
            }
        });
    }
}
