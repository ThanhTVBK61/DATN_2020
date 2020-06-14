package com.example.datn_2020.network.home;

import android.util.Log;

import com.example.datn_2020.model.PlaceDetailHomeResponse;
import com.example.datn_2020.network.ApiManager;
import com.example.datn_2020.network.ApiService;
import com.example.datn_2020.network.DisposableManager;
import com.example.datn_2020.network.HandleResult;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class InformationPlaceDetailRequest {
    private ApiService apiService;

    public InformationPlaceDetailRequest() {
        this.apiService = ApiManager.getInstance().create(ApiService.class);
    }

    public void getDataInformationPlaceDetailRequest(final HandleResult<PlaceDetailHomeResponse> handleResult){
        apiService.getInfoPlaceRequest()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PlaceDetailHomeResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        DisposableManager.add(d);
                    }

                    @Override
                    public void onNext(PlaceDetailHomeResponse placeDetailHomeResponse) {
                        handleResult.handleResponseResult(placeDetailHomeResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Call place api","Error connect to sever to get PlaceDetail");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("Call place api","Success get PlaceDetail");
                    }
                });
    }
}
