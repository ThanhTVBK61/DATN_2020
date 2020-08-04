package com.example.datn_2020.repository.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PostInfoResponse {

    @SerializedName("isSuccess")
    private boolean isSuccess;
    @SerializedName("sumComment")
    private ArrayList<PostCommentInfoModel> postCommentInfoModels;
    @SerializedName("sumLike")
    private ArrayList<PostLikeModel> sumLikes;
    @SerializedName("checkLike")
    private ArrayList<PostLikeModel> idPostLike;

    public boolean isSuccess() {
        return isSuccess;
    }

    public ArrayList<PostCommentInfoModel> getPostCommentInfoModels() {
        return postCommentInfoModels;
    }

    public ArrayList<PostLikeModel> getSumLikes() {
        return sumLikes;
    }

    public ArrayList<PostLikeModel> getIdPostLike() {
        return idPostLike;
    }

    public void updateLiked() {
        for (PostCommentInfoModel postCommentInfoModel : postCommentInfoModels) {
            for (PostLikeModel postLikeModel : idPostLike) {
                if (postLikeModel.getCountIdLike() == postCommentInfoModel.getIdPost()) {
                    postCommentInfoModel.setLiked(true);
                    break;
                }
            }
        }
    }
}
