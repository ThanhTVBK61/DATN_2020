package com.example.datn_2020.viewmodel.trip;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.datn_2020.repository.HandleError;
import com.example.datn_2020.repository.HandleSuccess;
import com.example.datn_2020.repository.HomeRepository;
import com.example.datn_2020.repository.TripRepository;
import com.example.datn_2020.repository.model.AllUserModel;
import com.example.datn_2020.repository.model.AllUserResponse;
import com.example.datn_2020.repository.model.ApiResponse;
import com.example.datn_2020.repository.model.EditTripModel;
import com.example.datn_2020.repository.model.FavouritePlaceCheckModel;
import com.example.datn_2020.repository.model.PlaceDetailHomeModel;
import com.example.datn_2020.repository.model.PlaceDetailHomeResponse;
import com.example.datn_2020.repository.model.PlaceInTripModel;
import com.example.datn_2020.repository.model.PlaceInTripResponse;
import com.example.datn_2020.repository.model.TripInformationModel;
import com.example.datn_2020.repository.model.TripInformationResponse;
import com.example.datn_2020.repository.model.UpdatePlaceInTripModel;
import com.example.datn_2020.repository.model.UpdateTimeTripModel;

import java.util.ArrayList;

public class TripDetailVM extends ViewModel {

    private TripRepository tripRepository = new TripRepository();
    private HomeRepository homeRepository = new HomeRepository();

    private MutableLiveData<Boolean> editTripResponse = new MutableLiveData<>();
    private MutableLiveData<String> errorData = new MutableLiveData<>();
    private MutableLiveData<ArrayList<TripInformationModel>> mTripInformationModels = new MutableLiveData<>();
    private MutableLiveData<ArrayList<PlaceInTripModel>> placeInTripModels = new MutableLiveData<>();
    private MutableLiveData<ArrayList<AllUserModel>> mAllUserModels = new MutableLiveData<>();
    private MutableLiveData<Boolean> updateTimeTripResponse = new MutableLiveData<>();
    private MutableLiveData<Boolean> deletePlaceInTripResponse = new MutableLiveData<>();
    private MutableLiveData<PlaceDetailHomeModel> currentPlaceDetail = new MutableLiveData<>();
    private MutableLiveData<Boolean> checkFavouriteResponse = new MutableLiveData<>();

    public void updateEditTrip(EditTripModel mEditTripModel) {
        tripRepository.updateEditTrip(mEditTripModel,new HandleSuccess<ApiResponse>() {
            @Override
            public void handleSuccessResult(ApiResponse results) {
                if (results.getIsSuccess()) {
                    Log.i("ContainerVM-Trip", results.toString());
                    editTripResponse.setValue(results.getIsSuccess());
                } else if (!results.getIsSuccess()) {
                    Log.i("ContainerVM-Trip", "isSuccess: false");
                } else {
                    Log.i("ContainerVM-Trip", "Can not get data from api");
                }
            }
        }, new HandleError<String>() {
            @Override
            public void handleErrorResult(String err) {
                errorData.setValue(err);
            }
        });
    }

    public MutableLiveData<Boolean> getEditTripResponse() {
        return editTripResponse;
    }


    public void loadTripInformation(int idTrip) {
        tripRepository.getTripInformationByIdTrip(idTrip, new HandleSuccess<TripInformationResponse>() {
            @Override
            public void handleSuccessResult(TripInformationResponse results) {
                if (results.isSuccess()) {
                    Log.i("ContainerVM-Trip", results.toString());
                    mTripInformationModels.setValue(results.getTripInformationModels());
                } else if (!results.isSuccess()) {
                    Log.i("ContainerVM-Trip", "isSuccess: false");
                } else {
                    Log.i("ContainerVM-Trip", "Can not get data from api");
                }
            }
        }, new HandleError<String>() {
            @Override
            public void handleErrorResult(String err) {
                errorData.setValue(err);
            }
        });
    }

    public MutableLiveData<ArrayList<TripInformationModel>> getmTripInformationModels() {
        return mTripInformationModels;
    }


    public void loadPlaceInTrip(int idTrip,int idUser) {
        tripRepository.getPlaceInTrip(idTrip,idUser, new HandleSuccess<PlaceInTripResponse>() {
            @Override
            public void handleSuccessResult(PlaceInTripResponse results) {
                if (results.getSuccess()) {
                    Log.i("TripDetail","Response LoadPlace");
                    Log.i("ContainerVM-Trip", results.toString());
                    placeInTripModels.setValue(results.getPlaceInTripModels());
                } else if (!results.getSuccess()) {
                    Log.i("ContainerVM-Trip", "isSuccess: false");
                } else {
                    Log.i("ContainerVM-Trip", "Can not get data from api");
                }
            }
        }, new HandleError<String>() {
            @Override
            public void handleErrorResult(String err) {
                errorData.setValue(err);
            }
        });
    }

    public MutableLiveData<ArrayList<PlaceInTripModel>> getPlaceInTripModels() {
        Log.i("TripDetail","getPlaceInTripModels");
        return placeInTripModels;
    }

    public void loadAllUser() {
        tripRepository.getAllUser(new HandleSuccess<AllUserResponse>() {
            @Override
            public void handleSuccessResult(AllUserResponse results) {
                if (results.isSuccess()) {
                    Log.i("ContainerVM-Trip", results.toString());
                    mAllUserModels.setValue(results.getAllUserModels());
                } else if (!results.isSuccess()) {
                    Log.i("ContainerVM-Trip", "isSuccess: false");
                } else {
                    Log.i("ContainerVM-Trip", "Can not get data from api");
                }
            }
        }, new HandleError<String>() {
            @Override
            public void handleErrorResult(String err) {
                errorData.setValue(err);
            }
        });

    }

    public MutableLiveData<ArrayList<AllUserModel>> getmAllUserModels() {
        return mAllUserModels;
    }


    public void updateTimeTrip(UpdateTimeTripModel mUpdateTimeTripModel) {
        tripRepository.updateTimeTrip(mUpdateTimeTripModel,new HandleSuccess<ApiResponse>() {
            @Override
            public void handleSuccessResult(ApiResponse results) {
                if (results.getIsSuccess()) {
                    Log.i("ContainerVM-Trip", results.toString());
                    updateTimeTripResponse.setValue(results.getIsSuccess());
                } else if (!results.getIsSuccess()) {
                    Log.i("ContainerVM-Trip", "isSuccess: false");
                } else {
                    Log.i("ContainerVM-Trip", "Can not get data from api");
                }
            }
        }, new HandleError<String>() {
            @Override
            public void handleErrorResult(String err) {
                errorData.setValue(err);
            }
        });
    }

    public MutableLiveData<Boolean> getUpdateTimeTripResponse() {
        return updateTimeTripResponse;
    }


    public void deletePlaceInTrip(UpdatePlaceInTripModel mUpdatePlaceInTripModel) {
        tripRepository.deletePlaceInTrip(mUpdatePlaceInTripModel,new HandleSuccess<ApiResponse>() {
            @Override
            public void handleSuccessResult(ApiResponse results) {
                if (results.getIsSuccess()) {
                    Log.i("ContainerVM-Trip", results.toString());
                    deletePlaceInTripResponse.setValue(results.getIsSuccess());
                } else if (!results.getIsSuccess()) {
                    Log.i("ContainerVM-Trip", "isSuccess: false");
                } else {
                    Log.i("ContainerVM-Trip", "Can not get data from api");
                }
            }
        }, new HandleError<String>() {
            @Override
            public void handleErrorResult(String err) {
                errorData.setValue(err);
            }
        });
    }

    public MutableLiveData<Boolean> getDeletePlaceInTripResponse() {
        return deletePlaceInTripResponse;
    }


    public void loadPlaceById(int id) {
        homeRepository.getPlaceById(id, new HandleSuccess<PlaceDetailHomeResponse>() {
            @Override
            public void handleSuccessResult(PlaceDetailHomeResponse results) {
                if(results.getSuccess())
                {
                    Log.i("Get Data Sever: ","Success call api to get ListPlace");
                    currentPlaceDetail.setValue(results.getListPlaceDetail().get(0));
                }else if(!results.getSuccess()){
                    Log.i("Get Data Sever: ","False call api to get ListPlace");
                }else {
                    Log.i("Get Data Sever: ","Api ListPlace return nothing");
                }
            }
        }, new HandleError<String>() {
            @Override
            public void handleErrorResult(String err) {
                errorData.setValue(err);
            }
        });
    }
    public MutableLiveData<PlaceDetailHomeModel> getCurrentPlaceDetail() {
        return currentPlaceDetail;
    }


    public void updateFavouritePlace(FavouritePlaceCheckModel favouritePlaceCheckModel){
        homeRepository.updateFavouritePlace(favouritePlaceCheckModel, new HandleSuccess<ApiResponse>() {
            @Override
            public void handleSuccessResult(ApiResponse apiResponse) {
                checkFavouriteResponse.setValue(apiResponse.getIsSuccess());
            }
        }, new HandleError<String>() {
            @Override
            public void handleErrorResult(String err) {
                errorData.setValue(err);
            }
        });
    }

    public MutableLiveData<Boolean> getCheckFavouriteResponse() {
        return checkFavouriteResponse;
    }
}
