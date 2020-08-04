package com.example.datn_2020.repository;

import android.util.Log;

import androidx.room.Delete;

import com.example.datn_2020.repository.model.AddCommentModel;
import com.example.datn_2020.repository.model.AddCommentResponse;
import com.example.datn_2020.repository.model.ChangeRating;
import com.example.datn_2020.repository.model.CommentResponse;
import com.example.datn_2020.repository.model.DeleteComment;
import com.example.datn_2020.repository.model.FavouritePlaceCheckModel;
import com.example.datn_2020.repository.model.ListPlaceResponse;
import com.example.datn_2020.repository.model.NewPostModel;
import com.example.datn_2020.repository.model.PlaceDetailHomeResponse;
import com.example.datn_2020.repository.model.PostInfoResponse;
import com.example.datn_2020.repository.model.RatingModel;
import com.example.datn_2020.repository.model.RatingResponse;
import com.example.datn_2020.repository.model.UpdateLikeCommentModel;
import com.example.datn_2020.repository.model.UpdatePlaceInTripModel;
import com.example.datn_2020.repository.network.ApiManager;
import com.example.datn_2020.repository.network.ApiService;
import com.example.datn_2020.repository.network.DisposableManager;
import com.example.datn_2020.repository.model.ApiResponse;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeRepository {
    private ApiService apiService;

    public HomeRepository() {
        this.apiService = ApiManager.getInstance().create(ApiService.class);
    }

    //Call Api
    public void getAllPlace(int idUser,final HandleSuccess<ListPlaceResponse> handleSuccess, final HandleError<String> handleError) {
        apiService.getAllPlaceInfo(idUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ListPlaceResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        DisposableManager.add(d);
                    }

                    @Override
                    public void onNext(ListPlaceResponse listPlaceResponse) {
                        handleSuccess.handleSuccessResult(listPlaceResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Call place api","Error connect to sever to get ListPlace");
                        handleError.handleErrorResult("ErrorCallGetListPlaceApi");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("Call place api","Success get ListPlace");
                    }
                });
    }

    public void getPlaceByType(int idUser,String typePlace,final HandleSuccess<ListPlaceResponse> handleSuccess, final HandleError<String> handleError) {
        apiService.getPlaceByType(idUser,typePlace)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ListPlaceResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        DisposableManager.add(d);
                    }

                    @Override
                    public void onNext(ListPlaceResponse listPlaceResponse) {
                        handleSuccess.handleSuccessResult(listPlaceResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("HomeRepository","Error connect to sever to getPlaceByType");
                        handleError.handleErrorResult("ErrorCallGetPlaceByTypeApi");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("HomeRepository","Success getPlaceByType");
                    }
                });
    }

    public void getPlaceById(int id,final HandleSuccess<PlaceDetailHomeResponse> handleSuccess, final HandleError<String> handleError) {
        apiService.getPlaceById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PlaceDetailHomeResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        DisposableManager.add(d);
                    }

                    @Override
                    public void onNext(PlaceDetailHomeResponse placeDetailHomeResponse) {
                        handleSuccess.handleSuccessResult(placeDetailHomeResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("HomeRepository","Error connect to sever to getPlaceById");
                        handleError.handleErrorResult("ErrorCallGetPlaceByIdApi");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("HomeRepository","Success getPlaceById");
                    }
                });
    }

    public void updateFavouritePlace(FavouritePlaceCheckModel favouritePlaceCheckModel, final HandleSuccess<ApiResponse> handleSuccess, final HandleError<String> handleError) {
        apiService.postUpdateFavourite(favouritePlaceCheckModel)
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
                        Log.i("Call place api","Error connect to sever to get ListPlace");
                        handleError.handleErrorResult("ErrorCallGetListPlaceApi");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("Call place api","Success get ListPlace");
                    }
                });
    }

    public void loadAllFavouritePlace(int idUser, final HandleSuccess<ListPlaceResponse> handleSuccess, final HandleError<String> handleError) {
        apiService.getFavouritePlace(idUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ListPlaceResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        DisposableManager.add(d);
                    }

                    @Override
                    public void onNext(ListPlaceResponse listPlaceResponse) {
                        handleSuccess.handleSuccessResult(listPlaceResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Call place api","Error connect to sever to get ListPlace");
                        handleError.handleErrorResult("ErrorCallGetListPlaceApi");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("Call place api","Success get ListPlace");
                    }
                });
    }

    public void loadRatingById(int idUser,int idPlace, final HandleSuccess<RatingResponse> handleSuccess, final HandleError<String> handleError) {
        apiService.loadRatingById(idUser,idPlace)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RatingResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        DisposableManager.add(d);
                    }

                    @Override
                    public void onNext(RatingResponse mRatingResponse) {
                        Log.i("ReviewFragment","Return "+ mRatingResponse.isSuccess());
                        handleSuccess.handleSuccessResult(mRatingResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Call place api","Error connect to sever to get ListPlace");
                        handleError.handleErrorResult("ErrorCallGetListPlaceApi");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("Call place api","Success get ListPlace");
                    }
                });
    }

    public void updateRating(ChangeRating mChangeRating, final HandleSuccess<ApiResponse> handleSuccess, final HandleError<String> handleError) {
        apiService.updateRating(mChangeRating)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        DisposableManager.add(d);
                    }

                    @Override
                    public void onNext(ApiResponse mApiResponse) {
                        Log.i("ReviewFragment","Return false "+ mApiResponse.getIsSuccess());
                        handleSuccess.handleSuccessResult(mApiResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Call place api","Error connect to sever to get ListPlace");
                        handleError.handleErrorResult("ErrorCallGetListPlaceApi");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("Call place api","Success get ListPlace");
                    }
                });
    }

    public void addRating(ChangeRating mChangeRating, final HandleSuccess<ApiResponse> handleSuccess, final HandleError<String> handleError) {
        apiService.addRating(mChangeRating)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        DisposableManager.add(d);
                    }

                    @Override
                    public void onNext(ApiResponse mApiResponse) {
                        Log.i("ReviewFragment","Return false "+ mApiResponse.getIsSuccess());
                        handleSuccess.handleSuccessResult(mApiResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Call place api","Error connect to sever to get ListPlace");
                        handleError.handleErrorResult("ErrorCallGetListPlaceApi");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("Call place api","Success get ListPlace");
                    }
                });
    }

    public void loadComments(int idUser, int idPost, final HandleSuccess<CommentResponse> handleSuccess, final HandleError<String> handleError) {
        apiService.loadComments(idUser,idPost)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommentResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        DisposableManager.add(d);
                    }

                    @Override
                    public void onNext(CommentResponse mCommentResponse) {
                        Log.i("CommentFragment","loadComments Return false "+ mCommentResponse.isSuccess());
                        handleSuccess.handleSuccessResult(mCommentResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Call place api","Error connect to sever to get loadComments === "+ e.getMessage());
                        handleError.handleErrorResult("ErrorCallGetListPlaceApi");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("Call place api","Success get loadComments");
                    }
                });
    }

    public void addComment(AddCommentModel mAddCommentModel, final HandleSuccess<CommentResponse> handleSuccess, final HandleError<String> handleError) {
        apiService.addComment(mAddCommentModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommentResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        DisposableManager.add(d);
                    }

                    @Override
                    public void onNext(CommentResponse mCommentResponse) {
                        Log.i("CommentFragment","loadComments Return "+ mCommentResponse.isSuccess());
                        handleSuccess.handleSuccessResult(mCommentResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Call place api","Error connect to sever to get loadComments === "+ e.getMessage());
                        handleError.handleErrorResult("ErrorCallGetListPlaceApi");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("Call place api","Success get loadComments");
                    }
                });
    }

    public void deleteComment(DeleteComment mDeleteComment, final HandleSuccess<ApiResponse> handleSuccess, final HandleError<String> handleError) {
        apiService.deleteComment(mDeleteComment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        DisposableManager.add(d);
                    }

                    @Override
                    public void onNext(ApiResponse mApiResponse) {
                        Log.i("CommentFragment","deleteComment Return "+ mApiResponse.getIsSuccess());
                        handleSuccess.handleSuccessResult(mApiResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Call place api","Error connect to sever to get deleteComment === "+ e.getMessage());
                        handleError.handleErrorResult("ErrorCallGetListPlaceApi");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("Call place api","Success get deleteComment");
                    }
                });
    }

    public void deletePost (DeleteComment mDeleteComment, final HandleSuccess<ApiResponse> handleSuccess, final HandleError<String> handleError) {
        apiService.deletePost(mDeleteComment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        DisposableManager.add(d);
                    }

                    @Override
                    public void onNext(ApiResponse mApiResponse) {
                        Log.i("CommentFragment","deletePost Return "+ mApiResponse.getIsSuccess());
                        handleSuccess.handleSuccessResult(mApiResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Call place api","Error connect to sever to get deletePost === "+ e.getMessage());
                        handleError.handleErrorResult("ErrorCallGetListPlaceApi");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("Call place api","Success get deletePost");
                    }
                });
    }

    public void addPost (NewPostModel newPostModel, final HandleSuccess<ApiResponse> handleSuccess, final HandleError<String> handleError) {
        apiService.addPost(newPostModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        DisposableManager.add(d);
                    }

                    @Override
                    public void onNext(ApiResponse mApiResponse) {
                        Log.i("CommentFragment","addPost Return "+ mApiResponse.getIsSuccess());
                        handleSuccess.handleSuccessResult(mApiResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Call place api","Error connect to sever to get addPost === "+ e.getMessage());
                        handleError.handleErrorResult("ErrorCallGetListPlaceApi");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("Call place api","Success get addPost");
                    }
                });
    }

    public void updateLikeComment(UpdateLikeCommentModel mUpdateLikeCommentModel, final HandleSuccess<ApiResponse> handleSuccess, final HandleError<String> handleError) {
        apiService.updateLikeComment(mUpdateLikeCommentModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        DisposableManager.add(d);
                    }

                    @Override
                    public void onNext(ApiResponse mApiResponse) {
                        Log.i("CommentFragment","loadComments Return false "+ mApiResponse.getIsSuccess());
                        handleSuccess.handleSuccessResult(mApiResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Call place api","Error connect to sever to get loadComments === "+ e.getMessage());
                        handleError.handleErrorResult("ErrorCallGetListPlaceApi");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("Call place api","Success get loadComments");
                    }
                });
    }

    public void getInfoPost(UpdatePlaceInTripModel mUpdatePlaceInTripModel, final HandleSuccess<PostInfoResponse> handleSuccess, final HandleError<String> handleError) {
        apiService.getInfoPost(mUpdatePlaceInTripModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PostInfoResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        DisposableManager.add(d);
                    }

                    @Override
                    public void onNext(PostInfoResponse mPostInfoResponse) {
                        Log.i("ReviewFragment","get postinfo Return "+ mPostInfoResponse.isSuccess());
                        handleSuccess.handleSuccessResult(mPostInfoResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Call place api","Error connect to sever to get ListPlace");
                        handleError.handleErrorResult("ErrorCallGetListPlaceApi");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("Call place api","Success get ListPlace");
                    }
                });
    }

    public void dislikePost(UpdatePlaceInTripModel mUpdatePlaceInTripModel, final HandleSuccess<ApiResponse> handleSuccess, final HandleError<String> handleError) {
        apiService.dislikePost(mUpdatePlaceInTripModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        DisposableManager.add(d);
                    }

                    @Override
                    public void onNext(ApiResponse mApiResponse) {
                        Log.i("ReviewFragment","Dislike Return "+ mApiResponse.getIsSuccess());
                        handleSuccess.handleSuccessResult(mApiResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Call place api","Error connect to sever to get ListPlace");
                        handleError.handleErrorResult("ErrorCallGetListPlaceApi");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("Call place api","Success dislike");
                    }
                });
    }

    public void likePost(UpdatePlaceInTripModel mUpdatePlaceInTripModel, final HandleSuccess<ApiResponse> handleSuccess, final HandleError<String> handleError) {
        apiService.likePost(mUpdatePlaceInTripModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        DisposableManager.add(d);
                    }

                    @Override
                    public void onNext(ApiResponse mApiResponse) {
                        Log.i("ReviewFragment","Like Return  "+ mApiResponse.getIsSuccess());
                        handleSuccess.handleSuccessResult(mApiResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("Call place api","Error connect to sever to get ListPlace");
                        handleError.handleErrorResult("ErrorCallGetListPlaceApi");
                    }

                    @Override
                    public void onComplete() {
                        Log.i("Call place api","Success like");
                    }
                });
    }
}
