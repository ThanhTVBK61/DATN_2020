package com.example.datn_2020.repository;

import android.util.Log;

import com.example.datn_2020.repository.model.ChangePasswordModel;
import com.example.datn_2020.repository.model.InformationAccountModel;
import com.example.datn_2020.repository.model.InformationAccountResponse;
import com.example.datn_2020.repository.network.ApiManager;
import com.example.datn_2020.repository.network.ApiService;
import com.example.datn_2020.repository.network.DisposableManager;
import com.example.datn_2020.repository.model.ApiResponse;
import com.example.datn_2020.repository.room.db.Db;
import com.example.datn_2020.repository.room.entity.User;

import io.reactivex.Observer;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AccountRepository {
    private ApiService apiService;

    public AccountRepository() {
        this.apiService = ApiManager.getInstance().create(ApiService.class);
    }

    //Room - Sqlite

    public void deleteUser(User mUser, final HandleSuccess<Integer> handleSuccess, final HandleError<String> handleError){
        Db.getDB().userDAO().delete(mUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        DisposableManager.add(d);
                    }

                    @Override
                    public void onSuccess(Integer numberRows) {
                        handleSuccess.handleSuccessResult(numberRows);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Room Login: ","Error"+e.toString());
                        handleError.handleErrorResult("ErrorDelete");
                    }
                });
    }

    //Call API

    public void changePassword(ChangePasswordModel mChangePasswordModel, final HandleSuccess<ApiResponse> handleSuccess, final HandleError<String> handleError) {
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
                        handleSuccess.handleSuccessResult(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("ChangePassword", "Can not call ChangePassword api: " + e.getMessage());
                        handleError.handleErrorResult("ErrorCallChangePasswordApi");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("ChangePassword", "Success to call ChangePassword api");
                    }
                });
    }

    public void getDataInformationAccountRequest(int idUser, final HandleSuccess<InformationAccountResponse> handleSuccess, final HandleError<String> handleError){
        apiService.getInfoAccountRequest(idUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<InformationAccountResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        DisposableManager.add(d);
                    }

                    @Override
                    public void onNext(InformationAccountResponse informationAccountResponse) {
                        handleSuccess.handleSuccessResult(informationAccountResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Error call api: ",e.getLocalizedMessage());
                        handleError.handleErrorResult("ErrorCallGetInfoAccountRequestApi");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("Call API: ","Completed");
                    }
                });
    }

    public void postChangeInformationAccount(InformationAccountModel informationAccountModel, final HandleSuccess<ApiResponse> handleSuccess, final HandleError<String> handleError){

        Log.i("EditInformation",""+informationAccountModel.toString());
        apiService.postChangeInformationAccount(informationAccountModel)
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
                        Log.i("Error call api: ",e.getLocalizedMessage());
                        handleError.handleErrorResult("ErrorCallGetInfoAccountRequestApi");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("Call API: ","Completed");
                    }
                });
    }



}
