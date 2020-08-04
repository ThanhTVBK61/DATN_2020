package com.example.datn_2020.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.datn_2020.repository.AccountRepository;
import com.example.datn_2020.repository.HandleError;
import com.example.datn_2020.repository.HandleSuccess;
import com.example.datn_2020.repository.NotificationRepository;
import com.example.datn_2020.repository.TripRepository;
import com.example.datn_2020.repository.model.AddCommentModel;
import com.example.datn_2020.repository.model.AllUserModel;
import com.example.datn_2020.repository.model.AllUserResponse;
import com.example.datn_2020.repository.model.ChangePasswordModel;
import com.example.datn_2020.repository.model.ChangeRating;
import com.example.datn_2020.repository.model.CommentModel;
import com.example.datn_2020.repository.model.CommentResponse;
import com.example.datn_2020.repository.model.DeleteComment;
import com.example.datn_2020.repository.model.DeleteTripModel;
import com.example.datn_2020.repository.model.EditTripModel;
import com.example.datn_2020.repository.model.FavouritePlaceCheckModel;
import com.example.datn_2020.repository.model.InformationAccountModel;
import com.example.datn_2020.repository.model.InformationAccountResponse;
import com.example.datn_2020.repository.model.NewPostModel;
import com.example.datn_2020.repository.model.NotificationModel;
import com.example.datn_2020.repository.model.NotificationResponse;
import com.example.datn_2020.repository.model.NotificationUpdateModel;
import com.example.datn_2020.repository.model.NotificationUpdateStatusModel;
import com.example.datn_2020.repository.model.PlaceDetailHomeModel;
import com.example.datn_2020.repository.model.PlaceDetailHomeResponse;
import com.example.datn_2020.repository.model.PlaceInTripModel;
import com.example.datn_2020.repository.model.PlaceInTripResponse;
import com.example.datn_2020.repository.model.PlaceModel;
import com.example.datn_2020.repository.model.ListPlaceResponse;
import com.example.datn_2020.repository.HomeRepository;
import com.example.datn_2020.repository.model.PostInfoResponse;
import com.example.datn_2020.repository.model.RatingResponse;
import com.example.datn_2020.repository.model.TripByIdPlaceModel;
import com.example.datn_2020.repository.model.TripByIdPlaceResponse;
import com.example.datn_2020.repository.model.TripInformationModel;
import com.example.datn_2020.repository.model.TripInformationResponse;
import com.example.datn_2020.repository.model.TripModel;
import com.example.datn_2020.repository.model.TripResponse;
import com.example.datn_2020.repository.model.ApiResponse;
import com.example.datn_2020.repository.model.UpdatePlaceInTripModel;
import com.example.datn_2020.repository.model.UpdateTimeTripModel;
import com.example.datn_2020.repository.room.entity.User;

import java.util.ArrayList;

public class ContainerVM extends ViewModel {

    private MutableLiveData<String> errorData = new MutableLiveData<>();
    private MutableLiveData<Integer> activateFragment = new MutableLiveData<>();
    private MutableLiveData<Boolean> newTrip = new MutableLiveData<>();

    //Home
    private HomeRepository homeRepository = new HomeRepository();
    private MutableLiveData<ArrayList<PlaceModel>> listCurrentTypePlace = new MutableLiveData<>();
    private MutableLiveData<ArrayList<PlaceModel>> listAllPlaceInfo = new MutableLiveData<>();
    private MutableLiveData<PlaceDetailHomeModel> currentPlaceDetail = new MutableLiveData<>();
    private MutableLiveData<Boolean> checkFavouriteResponse = new MutableLiveData<>();
    private MutableLiveData<RatingResponse> ratingModelResponse = new MutableLiveData<>();
    private MutableLiveData<Boolean> updateRatingResponse = new MutableLiveData<>();
    private MutableLiveData<Boolean> addRatingResponse = new MutableLiveData<>();
    private MutableLiveData<PostInfoResponse> getInformationPost = new MutableLiveData<>();
    private MutableLiveData<Boolean> likePost = new MutableLiveData<>();
    private MutableLiveData<Boolean> dislikePost = new MutableLiveData<>();
    private MutableLiveData<ArrayList<CommentModel>> commentModels = new MutableLiveData<>();
    private MutableLiveData<CommentModel> addCommentResponse = new MutableLiveData<>();

    //Account
    private AccountRepository accountRepository = new AccountRepository();
    private MutableLiveData<InformationAccountModel> infoAccountResponse = new MutableLiveData<>();
    private MutableLiveData<Boolean> response = new MutableLiveData<>();
    private MutableLiveData<Boolean> responseUpdateAccount = new MutableLiveData<>();
    private MutableLiveData<Integer> successData = new MutableLiveData<>();

    //Trip
    private TripRepository tripRepository = new TripRepository();
    private MutableLiveData<ArrayList<TripModel>> listTripModelResponse = new MutableLiveData<>();
    private MutableLiveData<ArrayList<PlaceModel>> listPlaceFavourite = new MutableLiveData<>();
    private MutableLiveData<ArrayList<TripInformationModel>> mTripInformationModels = new MutableLiveData<>();
    private MutableLiveData<ArrayList<PlaceInTripModel>> placeInTripModels = new MutableLiveData<>();
    private MutableLiveData<ArrayList<AllUserModel>> mAllUserModels = new MutableLiveData<>();
    private MutableLiveData<TripModel> addTripResponse = new MutableLiveData<>();
    private MutableLiveData<Boolean> editTripResponse = new MutableLiveData<>();
    private MutableLiveData<Boolean> updateTimeTripResponse = new MutableLiveData<>();
    private MutableLiveData<Boolean> deletePlaceInTripResponse = new MutableLiveData<>();
    private MutableLiveData<Boolean> addPlaceInTripResponse = new MutableLiveData<>();
    private MutableLiveData<Boolean> deleteTrip = new MutableLiveData<>();
    private MutableLiveData<Boolean> addTripUser = new MutableLiveData<>();
    private MutableLiveData<ArrayList<TripByIdPlaceModel>> tripByIdPlaces = new MutableLiveData<>();

    //Notification
    private NotificationRepository notificationRepository = new NotificationRepository();
    private MutableLiveData<Boolean> confirmTripUser = new MutableLiveData<>();
    private MutableLiveData<ArrayList<NotificationModel>> notificationResponses = new MutableLiveData<>();


    //    ********
    private boolean changeMainFavourite = false;
    private boolean changeTripFavourite = false;

    public MutableLiveData<Boolean> getNewTrip() {
        return newTrip;
    }

    public void setNewTrip(boolean newTrip) {
        this.newTrip.setValue(newTrip);
    }

    //    ****Home****

    public MutableLiveData<Integer> getActivateFragment() {
        return activateFragment;
    }

    public void setActivateFragment(int activateFragment) {
        this.activateFragment.setValue(activateFragment);
    }

    //truyen danh sách địa điểm từ list place sang map
    public void loadPlaceByType(int idUser, String typePlace) {
        homeRepository.getPlaceByType(idUser, typePlace, new HandleSuccess<ListPlaceResponse>() {
            @Override
            public void handleSuccessResult(ListPlaceResponse results) {
                if (results.getIsSuccess()) {
                    Log.i("ContainerVM-Home", "Success call api to getPlaceByType");
                    listCurrentTypePlace.setValue(results.getPlaceModels());
                } else if (!results.getIsSuccess()) {
                    Log.i("ContainerVM-Home", "False call api to getPlaceByType");
                } else {
                    Log.i("ContainerVM-Home", "Api getPlaceByType return nothing");
                }
            }
        }, new HandleError<String>() {
            @Override
            public void handleErrorResult(String err) {
                errorData.setValue(err);
            }
        });
    }

    public MutableLiveData<ArrayList<PlaceModel>> getListCurrentTypePlace() {
        return listCurrentTypePlace;
    }

    public void loadAllPlaceInfo(int idUser) {
        homeRepository.getAllPlace(idUser, new HandleSuccess<ListPlaceResponse>() {
            @Override
            public void handleSuccessResult(ListPlaceResponse results) {
                if (results.getIsSuccess()) {
                    Log.i("Get Data Sever: ", "Success call api to get ListPlace");
                    listAllPlaceInfo.setValue(results.getPlaceModels());
                } else if (!results.getIsSuccess()) {
                    Log.i("Get Data Sever: ", "False call api to get ListPlace");
                } else {
                    Log.i("Get Data Sever: ", "Api ListPlace return nothing");
                }
            }
        }, new HandleError<String>() {
            @Override
            public void handleErrorResult(String err) {
                errorData.setValue(err);
            }
        });
    }

    public MutableLiveData<ArrayList<PlaceModel>> getListAllPlaceInfo() {
        return listAllPlaceInfo;
    }


    public void loadPlaceById(int id) {
        homeRepository.getPlaceById(id, new HandleSuccess<PlaceDetailHomeResponse>() {
            @Override
            public void handleSuccessResult(PlaceDetailHomeResponse results) {
                if (results.getSuccess()) {
                    Log.i("Get Data Sever: ", "Success call api to get ListPlace");
                    currentPlaceDetail.setValue(results.getListPlaceDetail().get(0));
                } else if (!results.getSuccess()) {
                    Log.i("Get Data Sever: ", "False call api to get ListPlace");
                } else {
                    Log.i("Get Data Sever: ", "Api ListPlace return nothing");
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


    public void updateFavouritePlace(FavouritePlaceCheckModel favouritePlaceCheckModel) {
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

    public void loadRatingById(int idUser, int idPlace) {
        homeRepository.loadRatingById(idUser, idPlace, new HandleSuccess<RatingResponse>() {
            @Override
            public void handleSuccessResult(RatingResponse mRatingResponse) {

                if (mRatingResponse.isSuccess()) {
                    ratingModelResponse.setValue(mRatingResponse);
                } else {
                    Log.i("Get Data Sever: ", "False call api to get ListPlace");
                }

            }
        }, new HandleError<String>() {
            @Override
            public void handleErrorResult(String err) {
                errorData.setValue("Error" + err);
            }
        });
    }

    public MutableLiveData<RatingResponse> getRatingModelResponse() {
        return ratingModelResponse;
    }

    //Thêm rating của thành viên
    public void addRating(ChangeRating mChangeRating) {
        homeRepository.addRating(mChangeRating, new HandleSuccess<ApiResponse>() {
            @Override
            public void handleSuccessResult(ApiResponse mApiResponse) {

                addRatingResponse.setValue(mApiResponse.getIsSuccess());
            }
        }, new HandleError<String>() {
            @Override
            public void handleErrorResult(String err) {
                errorData.setValue("Error" + err);
            }
        });
    }

    public MutableLiveData<Boolean> getAddRatingResponse() {
        return addRatingResponse;
    }

    //Cập nhật rating của thành viên
    public void updateRating(ChangeRating mChangeRating) {
        homeRepository.updateRating(mChangeRating, new HandleSuccess<ApiResponse>() {
            @Override
            public void handleSuccessResult(ApiResponse mApiResponse) {

                updateRatingResponse.setValue(mApiResponse.getIsSuccess());
            }
        }, new HandleError<String>() {
            @Override
            public void handleErrorResult(String err) {
                errorData.setValue("Error" + err);
            }
        });
    }

    public MutableLiveData<Boolean> getUpdateRatingResponse() {
        return updateRatingResponse;
    }

    //Lấy thông bài đánh giá
    public void getInfoPost(UpdatePlaceInTripModel mUpdatePlaceInTripModel) {
        homeRepository.getInfoPost(mUpdatePlaceInTripModel, new HandleSuccess<PostInfoResponse>() {
            @Override
            public void handleSuccessResult(PostInfoResponse mPostInfoResponse) {

                getInformationPost.setValue(mPostInfoResponse);
            }
        }, new HandleError<String>() {
            @Override
            public void handleErrorResult(String err) {
                errorData.setValue("Error" + err);
            }
        });
    }

    public MutableLiveData<PostInfoResponse> getInformationPost() {
        return getInformationPost;
    }

    //Thích một bài post
    public void likePost(UpdatePlaceInTripModel mUpdatePlaceInTripModel) {
        homeRepository.likePost(mUpdatePlaceInTripModel, new HandleSuccess<ApiResponse>() {
            @Override
            public void handleSuccessResult(ApiResponse mApiResponse) {

                likePost.setValue(mApiResponse.getIsSuccess());
            }
        }, new HandleError<String>() {
            @Override
            public void handleErrorResult(String err) {
                errorData.setValue("Error" + err);
            }
        });
    }

    public MutableLiveData<Boolean> getLikePostResponse() {
        return likePost;
    }

    //Bỏ thích một bài post
    public void dislikePost(UpdatePlaceInTripModel mUpdatePlaceInTripModel) {
        homeRepository.dislikePost(mUpdatePlaceInTripModel, new HandleSuccess<ApiResponse>() {
            @Override
            public void handleSuccessResult(ApiResponse mApiResponse) {

                dislikePost.setValue(mApiResponse.getIsSuccess());
            }
        }, new HandleError<String>() {
            @Override
            public void handleErrorResult(String err) {
                errorData.setValue("Error" + err);
            }
        });
    }

    public MutableLiveData<Boolean> getDislikePostResponse() {
        return dislikePost;
    }

    //Lấy danh sách bình luận
    public void loadComments(int idUser, int idPost) {
        homeRepository.loadComments(idUser, idPost, new HandleSuccess<CommentResponse>() {
            @Override
            public void handleSuccessResult(CommentResponse mCommentResponse) {

                commentModels.setValue(mCommentResponse.getCommentModels());
            }
        }, new HandleError<String>() {
            @Override
            public void handleErrorResult(String err) {
                errorData.setValue("Error" + err);
            }
        });
    }

    public MutableLiveData<ArrayList<CommentModel>> getCommentModels() {
        return commentModels;
    }

    //Thêm bình luận
    public MutableLiveData<CommentModel> addComment(AddCommentModel addCommentModel) {
        final MutableLiveData<CommentModel> newComment = new MutableLiveData<>();
        homeRepository.addComment(addCommentModel, new HandleSuccess<CommentResponse>() {
            @Override
            public void handleSuccessResult(CommentResponse mCommentResponse) {

                if (mCommentResponse.isSuccess()) {
                    newComment.setValue(mCommentResponse.getCommentModels().get(0));

                }
            }
        }, new HandleError<String>() {
            @Override
            public void handleErrorResult(String err) {
                errorData.setValue("Error" + err);
            }
        });
        return newComment;
    }

    public MutableLiveData<CommentModel> getAddCommentResponse() {
        return addCommentResponse;
    }

    //    ****Account****

    //    Setting
    public void deleteUser(int idUser) {
        User mUser = new User();
        mUser.id = idUser;
        accountRepository.deleteUser(mUser, new HandleSuccess<Integer>() {
            @Override
            public void handleSuccessResult(Integer numberRows) {
                successData.setValue(numberRows);
            }
        }, new HandleError<String>() {
            @Override
            public void handleErrorResult(String err) {
                errorData.setValue(err);
            }
        });
    }

    public MutableLiveData<Boolean> getResponse() {
        return response;
    }

    public void changePassword(ChangePasswordModel mChangePasswordModel) {
        accountRepository.changePassword(mChangePasswordModel, new HandleSuccess<ApiResponse>() {
            @Override
            public void handleSuccessResult(ApiResponse result) {
                Log.i("Change password", "response api:" + result.getIsSuccess());
                response.setValue(result.getIsSuccess());
            }
        }, new HandleError<String>() {
            @Override
            public void handleErrorResult(String err) {
                errorData.setValue(err);
            }
        });
    }

    //        Information Account
    public void loadInfoAccount(int idUser) {
        accountRepository.getDataInformationAccountRequest(idUser, new HandleSuccess<InformationAccountResponse>() {
            @Override
            public void handleSuccessResult(InformationAccountResponse results) {
                if (results.getIsSuccess()) {
                    Log.i("Get User Info ", results.toString());
                    infoAccountResponse.setValue(results.getInformationAccountModel(0));
                } else if (!results.getIsSuccess()) {
                    Log.i("Get User Info ", "isSuccess: false");
                } else {
                    Log.i("Get User Info ", "Can not get data from api");
                }

            }
        }, new HandleError<String>() {
            @Override
            public void handleErrorResult(String err) {
                errorData.setValue(err);
            }
        });
    }

    public MutableLiveData<InformationAccountModel> getInfoAccountResponse() {
        return infoAccountResponse;
    }

    public void updateInformationAccount(InformationAccountModel informationAccountModel) {
        accountRepository.postChangeInformationAccount(informationAccountModel, new HandleSuccess<ApiResponse>() {
            @Override
            public void handleSuccessResult(ApiResponse result) {
                if (result.getIsSuccess()) {
                    responseUpdateAccount.setValue(result.getIsSuccess());
                } else if (!result.getIsSuccess()) {
                    Log.i("Get User Info ", "isSuccess: false");
                } else {
                    Log.i("Get User Info ", "Can not get data from api");
                }

            }
        }, new HandleError<String>() {
            @Override
            public void handleErrorResult(String err) {
                errorData.setValue(err);
            }
        });
    }

    public MutableLiveData<Boolean> getResponseUpdateAccount() {
        return responseUpdateAccount;
    }

    //    ****Trip****

    public void addTrip(TripModel tripModel) {
        tripRepository.addTrip(tripModel, new HandleSuccess<TripResponse>() {
            @Override
            public void handleSuccessResult(TripResponse results) {
                if (results.getSuccess()) {
                    Log.i("Get User Info ", results.toString());
                    addTripResponse.setValue(results.getTripModelList().get(0));
                } else if (!results.getSuccess()) {
                    Log.i("Get User Info ", "isSuccess: false");
                } else {
                    Log.i("Get User Info ", "Can not get data from api");
                }
            }
        }, new HandleError<String>() {
            @Override
            public void handleErrorResult(String err) {
                errorData.setValue(err);
            }
        });
    }

    public MutableLiveData<TripModel> getAddTripResponse() {
        return addTripResponse;
    }

    public void loadTrip(int idUser) {
        tripRepository.getTrip(idUser, new HandleSuccess<TripResponse>() {
            @Override
            public void handleSuccessResult(TripResponse results) {
                if (results.getSuccess()) {
                    Log.i("Get User Info ", results.toString());
                    listTripModelResponse.setValue(results.getTripModelList());
                } else if (!results.getSuccess()) {
                    Log.i("Get User Info ", "isSuccess: false");
                } else {
                    Log.i("Get User Info ", "Can not get data from api");
                }
            }
        }, new HandleError<String>() {
            @Override
            public void handleErrorResult(String err) {
                errorData.setValue(err);
            }
        });
    }

    public MutableLiveData<ArrayList<TripModel>> getListTripModelResponse() {
        return listTripModelResponse;
    }

    public void loadAllPlaceFavourite(int idUser) {
        homeRepository.loadAllFavouritePlace(idUser, new HandleSuccess<ListPlaceResponse>() {
            @Override
            public void handleSuccessResult(ListPlaceResponse results) {
                if (results.getIsSuccess()) {
                    Log.i("Get Data Sever: ", "Success call api to get ListPlace");
                    listPlaceFavourite.setValue(results.getPlaceModels());
                } else if (!results.getIsSuccess()) {
                    Log.i("Get Data Sever: ", "False call api to get ListPlace");
                } else {
                    Log.i("Get Data Sever: ", "Api ListPlace return nothing");
                }
            }
        }, new HandleError<String>() {
            @Override
            public void handleErrorResult(String err) {
                errorData.setValue(err);
            }
        });
    }

    public MutableLiveData<ArrayList<PlaceModel>> getListPlaceFavourite() {
        return listPlaceFavourite;
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


    public void loadPlaceInTrip(int idTrip, int idUser) {
        tripRepository.getPlaceInTrip(idTrip, idUser, new HandleSuccess<PlaceInTripResponse>() {
            @Override
            public void handleSuccessResult(PlaceInTripResponse results) {
                if (results.getSuccess()) {
                    Log.i("TripDetail", "Response LoadPlace");
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
        Log.i("TripDetail", "getPlaceInTripModels");
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


    public void updateEditTrip(EditTripModel mEditTripModel) {
        tripRepository.updateEditTrip(mEditTripModel, new HandleSuccess<ApiResponse>() {
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


    public void updateTimeTrip(UpdateTimeTripModel mUpdateTimeTripModel) {
        tripRepository.updateTimeTrip(mUpdateTimeTripModel, new HandleSuccess<ApiResponse>() {
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
        tripRepository.deletePlaceInTrip(mUpdatePlaceInTripModel, new HandleSuccess<ApiResponse>() {
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

    //Add place in trip
    public void addPlaceInTrip(UpdatePlaceInTripModel mUpdatePlaceInTripModel) {
        tripRepository.addPlaceInTrip(mUpdatePlaceInTripModel, new HandleSuccess<ApiResponse>() {
            @Override
            public void handleSuccessResult(ApiResponse results) {
                if (results.getIsSuccess()) {
                    Log.i("ContainerVM-Trip", results.toString());
                    addPlaceInTripResponse.setValue(results.getIsSuccess());
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


    public void deleteTrip(DeleteTripModel mDeleteTripModel) {
        tripRepository.deleteTrip(mDeleteTripModel, new HandleSuccess<ApiResponse>() {
            @Override
            public void handleSuccessResult(ApiResponse results) {
                if (results.getIsSuccess()) {
                    Log.i("ContainerVM-Trip", results.toString());
                    deleteTrip.setValue(results.getIsSuccess());
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

    public void deleteTripMember(DeleteTripModel mDeleteTripModel) {
        tripRepository.deleteTripMember(mDeleteTripModel, new HandleSuccess<ApiResponse>() {
            @Override
            public void handleSuccessResult(ApiResponse results) {
                if (results.getIsSuccess()) {
                    Log.i("ContainerVM-Trip", results.toString());
                    deleteTrip.setValue(results.getIsSuccess());
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

    public MutableLiveData<Boolean> getDeleteTrip() {
        return deleteTrip;
    }


    public MutableLiveData<Boolean> getAddTripUser() {
        return addTripUser;
    }


    public MutableLiveData<ArrayList<TripByIdPlaceModel>> loadTripByIdPlace(int idUser, int idPlace) {
        final MutableLiveData<ArrayList<TripByIdPlaceModel>> res = new MutableLiveData<>();
        tripRepository.loadTripByIdPlace(idUser, idPlace, new HandleSuccess<TripByIdPlaceResponse>() {
            @Override
            public void handleSuccessResult(TripByIdPlaceResponse results) {
                if (results.isSuccess()) {
                    Log.i("ContainerVM-Trip", results.toString());
                    res.setValue(results.getTripByIdPlaceModels());
//                    tripByIdPlaces.setValue(results.getTripByIdPlaceModels());
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
        return res;
    }

    public MutableLiveData<ArrayList<TripByIdPlaceModel>> getTripByIdPlaces() {
        return tripByIdPlaces;
    }

    public MutableLiveData<Boolean> deletePost(DeleteComment deleteComment) {
        final MutableLiveData<Boolean> checkDeletePost = new MutableLiveData<>();
        homeRepository.deletePost(deleteComment, new HandleSuccess<ApiResponse>() {
            @Override
            public void handleSuccessResult(ApiResponse results) {
                if (results.getIsSuccess()) {
                    Log.i("ContainerVM-Trip", results.toString());
                    checkDeletePost.setValue(true);
//                    tripByIdPlaces.setValue(results.getTripByIdPlaceModels());
                } else if (!results.getIsSuccess()) {
                    Log.i("ContainerVM-Trip", "isSuccess: false");
                    checkDeletePost.setValue(false);
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
        return checkDeletePost;
    }

    public MutableLiveData<Boolean> addPost(NewPostModel newPostModel) {
        final MutableLiveData<Boolean> checkAddPost = new MutableLiveData<>();
        homeRepository.addPost(newPostModel, new HandleSuccess<ApiResponse>() {
            @Override
            public void handleSuccessResult(ApiResponse results) {
                if (results.getIsSuccess()) {
                    Log.i("ContainerVM-Trip", results.toString());
                    checkAddPost.setValue(true);
                } else if (!results.getIsSuccess()) {
                    Log.i("ContainerVM-Trip", "isSuccess: false");
                    checkAddPost.setValue(false);
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
        return checkAddPost;
    }

    //Notification
    public void joinTrip(NotificationUpdateModel mNotificationUpdateModel) {
        notificationRepository.joinTrip(mNotificationUpdateModel, new HandleSuccess<ApiResponse>() {
            @Override
            public void handleSuccessResult(ApiResponse results) {
                if (results.getIsSuccess()) {
                    Log.i("ContainerVM-Trip", results.toString());
                    confirmTripUser.setValue(results.getIsSuccess());
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

    public void rejectTrip(NotificationUpdateModel mNotificationUpdateModel) {
        notificationRepository.rejectTrip(mNotificationUpdateModel, new HandleSuccess<ApiResponse>() {
            @Override
            public void handleSuccessResult(ApiResponse results) {
                if (results.getIsSuccess()) {
                    Log.i("ContainerVM-Trip", results.toString());
                    confirmTripUser.setValue(results.getIsSuccess());
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

    public void updateStatusNotification(NotificationUpdateStatusModel mNotificationUpdateStatusModel) {
        notificationRepository.updateStatusNotification(mNotificationUpdateStatusModel, new HandleSuccess<ApiResponse>() {
            @Override
            public void handleSuccessResult(ApiResponse results) {
                if (results.getIsSuccess()) {
                    Log.i("ContainerVM-Trip", results.toString());
                    confirmTripUser.setValue(results.getIsSuccess());
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

    public MutableLiveData<Boolean> getConfirmTripUser() {
        return confirmTripUser;
    }

    public void loadNotification(int idUser) {
        notificationRepository.loadNotification(idUser, new HandleSuccess<NotificationResponse>() {
            @Override
            public void handleSuccessResult(NotificationResponse results) {
                if (results.isSuccess()) {
                    notificationResponses.setValue(results.getNotificationModels());
                } else if (!results.isSuccess()) {
                    Log.i("ContainerVM-Noti", "isSuccess: false");
                } else {
                    Log.i("ContainerVM-Noti", "Can not get data from api");
                }
            }
        }, new HandleError<String>() {
            @Override
            public void handleErrorResult(String err) {
                errorData.setValue(err);
            }
        });
    }

    public MutableLiveData<ArrayList<NotificationModel>> getNotificationResponses() {
        return notificationResponses;
    }

    //***********************************************************
    public boolean isChangeMainFavourite() {
        return changeMainFavourite;
    }

    public void setChangeMainFavourite(boolean changeMainFavourite) {
        this.changeMainFavourite = changeMainFavourite;
    }

    public boolean isChangeTripFavourite() {
        return changeTripFavourite;
    }

    public void setChangeTripFavourite(boolean changeTripFavourite) {
        this.changeTripFavourite = changeTripFavourite;
    }
}
