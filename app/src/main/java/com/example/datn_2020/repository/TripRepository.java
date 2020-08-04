package com.example.datn_2020.repository;

import android.util.Log;

import com.example.datn_2020.repository.model.AllUserResponse;
import com.example.datn_2020.repository.model.ApiResponse;
import com.example.datn_2020.repository.model.DeleteTripModel;
import com.example.datn_2020.repository.model.EditTripModel;
import com.example.datn_2020.repository.model.PlaceInTripResponse;
import com.example.datn_2020.repository.model.TripByIdPlaceModel;
import com.example.datn_2020.repository.model.TripByIdPlaceResponse;
import com.example.datn_2020.repository.model.TripInformationResponse;
import com.example.datn_2020.repository.model.TripModel;
import com.example.datn_2020.repository.model.TripResponse;
import com.example.datn_2020.repository.model.UpdatePlaceInTripModel;
import com.example.datn_2020.repository.model.UpdateTimeTripModel;
import com.example.datn_2020.repository.network.ApiManager;
import com.example.datn_2020.repository.network.ApiService;
import com.example.datn_2020.repository.network.DisposableManager;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TripRepository {
    private ApiService apiService;
    private String TAG = "TripDetail";
    private String TAG1 = "TripTabLayout";

    public TripRepository() {
        this.apiService = ApiManager.getInstance().create(ApiService.class);
    }

    //Call Api
    public void getTrip(int idUser, final HandleSuccess<TripResponse> handleSuccess, final HandleError<String> handleError){
        apiService.getTrip(idUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TripResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        DisposableManager.add(d);
                    }

                    @Override
                    public void onNext(TripResponse tripResponse) {
                        handleSuccess.handleSuccessResult(tripResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG1, "Can not call api: " + e.getMessage());
                        handleError.handleErrorResult("ErrorCheckApi");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG1, "Success to call login api");
                    }
                });
    }

    public void addTrip(TripModel tripModel, final HandleSuccess<TripResponse> handleSuccess, final HandleError<String> handleError){
        apiService.addTrip(tripModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TripResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        DisposableManager.add(d);
                    }

                    @Override
                    public void onNext(TripResponse tripResponse) {
                        handleSuccess.handleSuccessResult(tripResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG1, "Can not call api: " + e.getMessage());
                        handleError.handleErrorResult("ErrorCheckApi");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG1, "Success to call login api");
                    }
                });
    }

    public void getTripInformationByIdTrip(int idTrip, final HandleSuccess<TripInformationResponse> handleSuccess, final HandleError<String> handleError){
        apiService.getInformationTrip(idTrip)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TripInformationResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        DisposableManager.add(d);
                    }

                    @Override
                    public void onNext(TripInformationResponse tripInformationResponse) {
                        handleSuccess.handleSuccessResult(tripInformationResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "Can not call api: " + e.getMessage());
                        handleError.handleErrorResult("ErrorCheckApi");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "Success to call getTripInformationByIdTrip api");
                    }
                });
    }

    public void getPlaceInTrip(int idTrip, int idUser, final HandleSuccess<PlaceInTripResponse> handleSuccess, final HandleError<String> handleError){
        apiService.getPlaceInTrip(idTrip,idUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PlaceInTripResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        DisposableManager.add(d);
                    }

                    @Override
                    public void onNext(PlaceInTripResponse placeInTripResponse) {
                        handleSuccess.handleSuccessResult(placeInTripResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "Can not call api: " + e.getMessage());
                        handleError.handleErrorResult("ErrorCheckApi");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "Success to call getPlaceInTrip api");
                    }
                });
    }

    public void getAllUser(final HandleSuccess<AllUserResponse> handleSuccess, final HandleError<String> handleError){
        apiService.getAllUsername()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AllUserResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        DisposableManager.add(d);
                    }

                    @Override
                    public void onNext(AllUserResponse allUserResponse) {
                        handleSuccess.handleSuccessResult(allUserResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "Can not call api: " + e.getMessage());
                        handleError.handleErrorResult("ErrorCheckApi");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "Success to call getAllUser api");
                    }
                });
    }

    public void updateEditTrip(EditTripModel editTripModel, final HandleSuccess<ApiResponse> handleSuccess, final HandleError<String> handleError){
        apiService.updateEditTrip(editTripModel)
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
                        Log.i(TAG, "Can not call api: " + e.getMessage());
                        handleError.handleErrorResult("ErrorCheckApi");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "Success to call updateEditTrip api");
                    }
                });
    }

    public void updateTimeTrip(UpdateTimeTripModel mUpdateTimeTripModel, final HandleSuccess<ApiResponse> handleSuccess, final HandleError<String> handleError){
        apiService.updateTimeTrip(mUpdateTimeTripModel)
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
                        Log.i(TAG, "Can not call api: " + e.getMessage());
                        handleError.handleErrorResult("ErrorCheckApi");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "Success to call login api");
                    }
                });
    }

    public void deletePlaceInTrip(UpdatePlaceInTripModel mUpdatePlaceInTripModel, final HandleSuccess<ApiResponse> handleSuccess, final HandleError<String> handleError){
        apiService.deletePlaceInTrip(mUpdatePlaceInTripModel)
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
                        Log.i(TAG, "Can not call api: " + e.getMessage());
                        handleError.handleErrorResult("ErrorCheckApi");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "Success to call login api");
                    }
                });
    }

    public void addPlaceInTrip(UpdatePlaceInTripModel mUpdatePlaceInTripModel, final HandleSuccess<ApiResponse> handleSuccess, final HandleError<String> handleError){
        apiService.addPlaceInTrip(mUpdatePlaceInTripModel)
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
                        Log.i(TAG, "Can not call api: " + e.getMessage());
                        handleError.handleErrorResult("ErrorCheckApi");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "Success to call login api");
                    }
                });
    }

    public void deleteTrip(DeleteTripModel mDeleteTripModel, final HandleSuccess<ApiResponse> handleSuccess, final HandleError<String> handleError){
        apiService.deleteTrip(mDeleteTripModel)
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
                        Log.i(TAG, "Can not call api: " + e.getMessage());
                        handleError.handleErrorResult("ErrorCheckApi");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "Success to call login api");
                    }
                });
    }

    public void deleteTripMember(DeleteTripModel mDeleteTripModel, final HandleSuccess<ApiResponse> handleSuccess, final HandleError<String> handleError){
        apiService.deleteTripMember(mDeleteTripModel)
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
                        Log.i(TAG, "Can not call api: " + e.getMessage());
                        handleError.handleErrorResult("ErrorCheckApi");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "Success to call login api");
                    }
                });
    }

    public void addUserTrip(DeleteTripModel mDeleteTripModel, final HandleSuccess<ApiResponse> handleSuccess, final HandleError<String> handleError){
        apiService.addUserTrip(mDeleteTripModel)
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
                        Log.i(TAG, "Can not call api: " + e.getMessage());
                        handleError.handleErrorResult("ErrorCheckApi");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "Success to call login api");
                    }
                });
    }

    public void loadTripByIdPlace(int idUser,int idPlace, final HandleSuccess<TripByIdPlaceResponse> handleSuccess, final HandleError<String> handleError){
        apiService.loadTripByIdPlace(idUser,idPlace)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TripByIdPlaceResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        DisposableManager.add(d);
                    }

                    @Override
                    public void onNext(TripByIdPlaceResponse mTripByIdPlaceResponse) {
                        handleSuccess.handleSuccessResult(mTripByIdPlaceResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "Can not call loadTripByIdPlace api: " + e.getMessage());
                        handleError.handleErrorResult("ErrorloadTripByIdPlaceApi");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "Success to call loadTripByIdPlace api");
                    }
                });
    }
}
