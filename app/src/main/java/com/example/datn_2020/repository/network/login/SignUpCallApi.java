package com.example.datn_2020.repository.network.login;

import android.util.Log;

import com.example.datn_2020.repository.model.SignUpModel;
import com.example.datn_2020.repository.network.ApiManager;
import com.example.datn_2020.repository.network.ApiService;
import com.example.datn_2020.repository.network.DisposableManager;
import com.example.datn_2020.repository.network.HandleResult;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SignUpCallApi {
    private ApiService apiService;

    public SignUpCallApi() {
        this.apiService = ApiManager.getInstance().create(ApiService.class);
    }

    public void checkSignUp(SignUpModel mSignInModel, final HandleResult<Boolean> handleResult) {
        Log.i("Sign Up", mSignInModel.getUsername() + " " + mSignInModel.getPassword());
        apiService.postSignUp(mSignInModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        DisposableManager.add(d);
                    }

                    @Override
                    public void onNext(Boolean signUpResponse) {
                        handleResult.handleResponseResult(signUpResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Sign Up", "Can not call api: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i("Sign Up", "Success to call sign up api");
                    }
                });
    }
}
