package com.example.datn_2020.repository.network.account;

import android.util.Log;

import com.example.datn_2020.repository.network.DisposableManager;
import com.example.datn_2020.repository.room.ApiResponse;
import com.example.datn_2020.repository.model.ChangePasswordModel;
import com.example.datn_2020.repository.network.ApiManager;
import com.example.datn_2020.repository.network.ApiService;
import com.example.datn_2020.repository.network.HandleResult;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ChangePassCallApi {
    private ApiService apiService;

    public ChangePassCallApi() {
        this.apiService = ApiManager.getInstance().create(ApiService.class);
    }

    public void changePassword(ChangePasswordModel mChangePasswordModel, final HandleResult<ApiResponse> handleResult) {
        Log.i("ChangePassword", mChangePasswordModel.getUsername() + " " + mChangePasswordModel.getOldPassword()
                + " -> " + mChangePasswordModel.getNewPassword());
        apiService.postChangePass(mChangePasswordModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        DisposableManager.add(d);
                    }

                    @Override
                    public void onNext(ApiResponse response) {
                        handleResult.handleResponseResult(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("ChangePassword", "Can not call ChangePassword api: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i("ChangePassword", "Success to call ChangePassword api");
                    }
                });
    }
}
