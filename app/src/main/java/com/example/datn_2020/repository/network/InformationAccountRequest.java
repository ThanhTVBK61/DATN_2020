package com.example.datn_2020.repository.network;

import android.util.Log;

import com.example.datn_2020.repository.model.InformationAccountResponse;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class InformationAccountRequest {

    private ApiService apiService;

    public InformationAccountRequest() {
        this.apiService = ApiManager.getInstance().create(ApiService.class);
    }

    public void getDataInformationAccountRequest(final HandleResult<InformationAccountResponse> handleResult){
        apiService.getInfoAccountRequest()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<InformationAccountResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        DisposableManager.add(d);
                    }

                    @Override
                    public void onNext(InformationAccountResponse informationAccountResponse) {
                        handleResult.handleResponseResult(informationAccountResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Error call api: ",e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i("Call API: ","Completed");
                    }
                });
    }
}
