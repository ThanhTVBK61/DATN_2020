package com.example.datn_2020.repository;

import android.util.Log;

import com.example.datn_2020.repository.model.LoginModel;
import com.example.datn_2020.repository.model.LoginResponse;
import com.example.datn_2020.repository.model.SignUpModel;
import com.example.datn_2020.repository.network.ApiManager;
import com.example.datn_2020.repository.network.ApiService;
import com.example.datn_2020.repository.network.DisposableManager;
import com.example.datn_2020.repository.model.ApiResponse;
import com.example.datn_2020.repository.room.db.Db;
import com.example.datn_2020.repository.room.entity.User;

import io.reactivex.MaybeObserver;
import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginRepository {

    private ApiService apiService;

    public LoginRepository() {
        this.apiService = ApiManager.getInstance().create(ApiService.class);
    }

    //Room - SQLite
    public void getUser(final HandleSuccess<User> handleSuccess, final HandleError<String> handleError) {
        Db.getDB().userDAO().getUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MaybeObserver<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        DisposableManager.add(d);
                    }

                    @Override
                    public void onSuccess(User user) {
                        Log.i("Room Login: ","Success");
                        handleSuccess.handleSuccessResult(user);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Room Login: ","Error"+e.toString());
                        handleError.handleErrorResult(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i("Room Login: ","onComplete");
                        User mUser = new User();
                        mUser.id = -1;
                        handleSuccess.handleSuccessResult(mUser);
                    }
                });
    }

    public void insertUser(User mUser, final HandleSuccess<Long> handleSuccess, final HandleError<String> handleError){
        Db.getDB().userDAO().insert(mUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        DisposableManager.add(d);
                    }

                    @Override
                    public void onSuccess(Long rowId) {
                        handleSuccess.handleSuccessResult(rowId);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Room Login: ","Error"+e.toString());
                        handleError.handleErrorResult("ErrorInsert");
                    }
                });
    }




    //Call API
    public void checkLoginApi(LoginModel mLoginModel, final HandleSuccess<LoginResponse> handleSuccess, final HandleError<String> handleError){
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
                        handleSuccess.handleSuccessResult(loginResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Login", "Can not call api: " + e.getMessage());
                        handleError.handleErrorResult("ErrorCheckApi");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("Login", "Success to call login api");
                    }
                });
    }

    public void checkSignUp(SignUpModel mSignInModel, final HandleSuccess<ApiResponse> handleSuccess, final HandleError<String> handleError) {
        Log.i("Sign Up", mSignInModel.getUsername() + " " + mSignInModel.getPassword());
        apiService.postSignUp(mSignInModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        DisposableManager.add(d);
                    }

                    @Override
                    public void onNext(ApiResponse apiResponse) {
                        handleSuccess.handleSuccessResult(apiResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Sign Up", "Can not call api: " + e.getMessage());
                        handleError.handleErrorResult("ErrorCallSignUpApi");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("Sign Up", "Success to call sign up api");
                    }
                });
    }
}
