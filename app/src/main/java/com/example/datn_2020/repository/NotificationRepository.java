package com.example.datn_2020.repository;

import android.util.Log;

import com.example.datn_2020.repository.model.ApiResponse;
import com.example.datn_2020.repository.model.NotificationResponse;
import com.example.datn_2020.repository.model.NotificationUpdateModel;
import com.example.datn_2020.repository.model.NotificationUpdateStatusModel;
import com.example.datn_2020.repository.network.ApiManager;
import com.example.datn_2020.repository.network.ApiService;
import com.example.datn_2020.repository.network.DisposableManager;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NotificationRepository {
    private ApiService apiService;
    private final String TAG = "NotificationFragment";

    public NotificationRepository() {
        this.apiService = ApiManager.getInstance().create(ApiService.class);
    }

    public void joinTrip(NotificationUpdateModel mNotificationUpdateModel, final HandleSuccess<ApiResponse> handleSuccess, final HandleError<String> handleError) {
        apiService.joinTrip(mNotificationUpdateModel)
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
                        Log.i(TAG, "Can not call joinTrip api: " + e.getMessage());
                        handleError.handleErrorResult("ErrorCheckApi");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "Success to call joinTrip api");
                    }
                });
    }

    public void rejectTrip(NotificationUpdateModel mNotificationUpdateModel, final HandleSuccess<ApiResponse> handleSuccess, final HandleError<String> handleError) {
        apiService.rejectTrip(mNotificationUpdateModel)
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
                        Log.i(TAG, "Can not call rejectTrip api: " + e.getMessage());
                        handleError.handleErrorResult("ErrorCheckApi");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "Success to call rejectTrip api");
                    }
                });
    }

    public void updateStatusNotification(NotificationUpdateStatusModel mNotificationUpdateStatusModel, final HandleSuccess<ApiResponse> handleSuccess, final HandleError<String> handleError) {
        apiService.updateStatusNotification(mNotificationUpdateStatusModel)
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
                        Log.i(TAG, "Can not call updateStatusNotification api: " + e.getMessage());
                        handleError.handleErrorResult("ErrorCheckApi");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "Success to call updateStatusNotification api");
                    }
                });
    }


    public void loadNotification(int idUser, final HandleSuccess<NotificationResponse> handleSuccess, final HandleError<String> handleError) {
        apiService.loadNotification(idUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NotificationResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        DisposableManager.add(d);
                    }

                    @Override
                    public void onNext(NotificationResponse mNotificationResponse) {
                        handleSuccess.handleSuccessResult(mNotificationResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "Can not call rejectTrip api: " + e.getMessage());
                        handleError.handleErrorResult("ErrorCheckApi");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "Success to call rejectTrip api");
                    }
                });
    }


}
