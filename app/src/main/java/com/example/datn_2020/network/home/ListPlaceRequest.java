package com.example.datn_2020.network.home;

import android.util.Log;

import com.example.datn_2020.model.ListPlaceResponse;
import com.example.datn_2020.network.ApiManager;
import com.example.datn_2020.network.ApiService;
import com.example.datn_2020.network.DisposableManager;
import com.example.datn_2020.network.HandleResult;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ListPlaceRequest {
    private ApiService apiService;
    private String idUser;
    private String typePlace;

    public ListPlaceRequest() {
        this.apiService = ApiManager.getInstance().create(ApiService.class);
    }

    public ListPlaceRequest(String idUser, String typePlace) {
        this.apiService = ApiManager.getInstance().create(ApiService.class);
        this.idUser = idUser;
        this.typePlace = typePlace;
    }

    public void getListPlace(final HandleResult<ListPlaceResponse> handleResult) {
        apiService.getListPlaceRequest(idUser, typePlace)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ListPlaceResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        DisposableManager.add(d);
                    }

                    @Override
                    public void onNext(ListPlaceResponse listPlaceResponse) {
                        handleResult.handleResponseResult(listPlaceResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Call place api","Error connect to sever to get ListPlace");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("Call place api","Success get ListPlace");
                    }
                });
    }
}
