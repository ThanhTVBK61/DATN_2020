package com.example.datn_2020.repository.network.login;

import android.util.Log;

import com.example.datn_2020.repository.model.LoginModel;
import com.example.datn_2020.repository.model.LoginResponse;
import com.example.datn_2020.repository.network.ApiManager;
import com.example.datn_2020.repository.network.ApiService;
import com.example.datn_2020.repository.network.DisposableManager;
import com.example.datn_2020.repository.network.HandleResult;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginCallApi {
    private ApiService apiService;

    public LoginCallApi() {
        this.apiService = ApiManager.getInstance().create(ApiService.class);
    }

    public void checkLogin(LoginModel mLoginModel, final HandleResult<LoginResponse> handleResult) {
        Log.i("Login", mLoginModel.getUsername() + " " + mLoginModel.getPassword());
        apiService.postLogin(mLoginModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        DisposableManager.add(d);
                    }

                    @Override
                    public void onNext(LoginResponse loginResponse) {
                        handleResult.handleResponseResult(loginResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Login", "Can not call api: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i("Login", "Success to call login api");
                    }
                });
    }
}
