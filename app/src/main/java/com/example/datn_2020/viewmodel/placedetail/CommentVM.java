package com.example.datn_2020.viewmodel.placedetail;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.datn_2020.repository.HandleError;
import com.example.datn_2020.repository.HandleSuccess;
import com.example.datn_2020.repository.HomeRepository;
import com.example.datn_2020.repository.model.AddCommentModel;
import com.example.datn_2020.repository.model.ApiResponse;
import com.example.datn_2020.repository.model.CommentModel;
import com.example.datn_2020.repository.model.CommentResponse;
import com.example.datn_2020.repository.model.DeleteComment;
import com.example.datn_2020.repository.model.UpdateLikeCommentModel;

import java.util.ArrayList;

public class CommentVM extends ViewModel {


    private MutableLiveData<ArrayList<CommentModel>> commentModels = new MutableLiveData<>();
    private HomeRepository homeRepository = new HomeRepository();
    private MutableLiveData<String> errorData = new MutableLiveData<>();

    private MutableLiveData<CommentModel> addCommentResponse = new MutableLiveData<>();

    //Lấy danh sách bình luận
    public void loadComments(int idUser, int idPost){
        homeRepository.loadComments(idUser,idPost, new HandleSuccess<CommentResponse>() {
            @Override
            public void handleSuccessResult(CommentResponse mCommentResponse) {

                commentModels.setValue(mCommentResponse.getCommentModels());
            }
        }, new HandleError<String>() {
            @Override
            public void handleErrorResult(String err) {
                errorData.setValue("Error"+err);
            }
        });
    }

    public MutableLiveData<ArrayList<CommentModel>> getCommentModels() {
        return commentModels;
    }

    //Thêm bình luận
    public MutableLiveData<CommentModel> addComment(AddCommentModel addCommentModel){
        final MutableLiveData<CommentModel> newComment = new MutableLiveData<>();
        homeRepository.addComment(addCommentModel, new HandleSuccess<CommentResponse>() {
            @Override
            public void handleSuccessResult(CommentResponse mCommentResponse) {

                if(mCommentResponse.isSuccess()){
                    newComment.setValue(mCommentResponse.getCommentModels().get(0));

                }
            }
        }, new HandleError<String>() {
            @Override
            public void handleErrorResult(String err) {
                errorData.setValue("Error"+err);
            }
        });
        return newComment;
    }

//    public MutableLiveData<CommentModel> getAddCommentResponse() {
//        return addCommentResponse;
//    }
//
    public MutableLiveData<Boolean> updateLikeComment(UpdateLikeCommentModel updateLikeCommentModel){
        final MutableLiveData<Boolean> checkLikeComment = new MutableLiveData<>();
        homeRepository.updateLikeComment(updateLikeCommentModel, new HandleSuccess<ApiResponse>() {
            @Override
            public void handleSuccessResult(ApiResponse mApiResponse) {

                if(mApiResponse.getIsSuccess()){
                    checkLikeComment.setValue(true);
                }else {
                    checkLikeComment.setValue(false);
                }
            }
        }, new HandleError<String>() {
            @Override
            public void handleErrorResult(String err) {
                errorData.setValue("Error"+err);
            }
        });
        return checkLikeComment;
    }

    public MutableLiveData<Boolean> deleteComment(DeleteComment mDeleteComment){
        final MutableLiveData<Boolean> delete = new MutableLiveData<>();
        homeRepository.deleteComment(mDeleteComment, new HandleSuccess<ApiResponse>() {
            @Override
            public void handleSuccessResult(ApiResponse mApiResponse) {

                if(mApiResponse.getIsSuccess()){
                    delete.setValue(true);
                }else {
                    delete.setValue(false);
                }
            }
        }, new HandleError<String>() {
            @Override
            public void handleErrorResult(String err) {
                errorData.setValue("Error"+err);
            }
        });
        return delete;
    }

//    public MutableLiveData<CommentModel> getAddCommentResponse() {
//        return addCommentResponse;
//    }
}
