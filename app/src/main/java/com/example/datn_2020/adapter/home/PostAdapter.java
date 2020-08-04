package com.example.datn_2020.adapter.home;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.datn_2020.R;
import com.example.datn_2020.repository.model.PostResponse;

import java.util.ArrayList;

class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView ivAccount;
    private ImageView ivLike;
    private TextView tvLike;
    private TextView tvName;
    private TextView contentRating;
    private TextView tvContentComment;
    private TextView contentPost;
    private TextView tvNumberLikePost;
    private TextView tvNumberCommentPost;
    private TextView timePost;
    private ImageView ivMoreOption;
    private LinearLayout llComment;
    private LinearLayout llLikePost;
    private RatingBar ratingBar;
    private RelativeLayout rlInformationLikeComment;

    private PostResponse mPostResponse;
    private PostAdapter.ItemPostClickListener mItemPostClickListener;

    PostViewHolder(@NonNull View itemView) {
        super(itemView);

        ivAccount = itemView.findViewById(R.id.ivAccountPost);
        ivLike = itemView.findViewById(R.id.ivLike);
        tvLike = itemView.findViewById(R.id.tvLike);
        tvName = itemView.findViewById(R.id.tvNameAccountPost);
        contentRating = itemView.findViewById(R.id.tvContentRating);
        contentPost = itemView.findViewById(R.id.tvContentPost);
        tvNumberLikePost = itemView.findViewById(R.id.tvNumberLikePost);
        tvContentComment = itemView.findViewById(R.id.tvContentComment);
        tvNumberCommentPost = itemView.findViewById(R.id.tvNumberCommentPost);
        timePost = itemView.findViewById(R.id.tvTimePost);
        ratingBar = itemView.findViewById(R.id.rbRatingPost);
        rlInformationLikeComment = itemView.findViewById(R.id.rlInformationLikeComment);
        ivMoreOption = itemView.findViewById(R.id.ivMoreOption);
        llComment = itemView.findViewById(R.id.llComment);
        llLikePost = itemView.findViewById(R.id.llLikePost);
    }

    void bind(final Context context, final boolean isGuest, final PostResponse postResponse, final PostAdapter.ItemPostClickListener itemPostClickListener){

        mPostResponse = postResponse;
        mItemPostClickListener = itemPostClickListener;

        if(postResponse.getUrl() == null){
            ivAccount.setImageResource(R.drawable.ic_unknown_person);
        }else {
            Glide.with(context)
                    .asBitmap()
                    .load(postResponse.getUrl())
                    .into(this.ivAccount);
        }
        tvName.setText(postResponse.getName());
        contentRating.setText(String.valueOf(postResponse.getRating()));
        ratingBar.setRating(postResponse.getRating());
        timePost.setText(postResponse.getTime());
        contentPost.setText(postResponse.getContent());
        tvNumberLikePost.setText(String.valueOf(postResponse.getNumLike()));
        tvNumberCommentPost.setText(String.valueOf(postResponse.getNumComment()));

        if(postResponse.isLiked()){
            ivLike.setColorFilter(ivLike.getResources().getColor(R.color.colorBlue700));
            tvLike.setTextColor(ivLike.getResources().getColor(R.color.colorBlue700));
        }else {
            ivLike.setColorFilter(ivLike.getResources().getColor(R.color.colorGray700));
            tvLike.setTextColor(ivLike.getResources().getColor(R.color.colorGray700));
        }

        llLikePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isGuest){
                    AlertDialog alertDialog = new AlertDialog.Builder(context)
                            .setTitle("Thông báo")
                            .setMessage("Đăng nhập để thích bài viết")
                            .setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).create();
                    alertDialog.show();
                    Button negative = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                    negative.setTextColor(negative.getResources().getColor(R.color.colorBlue700));
                }else {
                    if(tvLike.getCurrentTextColor() == tvLike.getResources().getColor(R.color.colorBlue700)){
                        itemPostClickListener.onLikeClick(postResponse,false);
                        ivLike.setColorFilter(ivLike.getResources().getColor(R.color.colorGray700));
                        tvLike.setTextColor(ivLike.getResources().getColor(R.color.colorGray700));
                        postResponse.decreaseLike();
                        tvNumberLikePost.setText(String.valueOf(postResponse.getNumLike()));

                    }else {
                        itemPostClickListener.onLikeClick(postResponse,true);
                        ivLike.setColorFilter(ivLike.getResources().getColor(R.color.colorBlue700));
                        tvLike.setTextColor(ivLike.getResources().getColor(R.color.colorBlue700));
                        postResponse.increaseLike();
                        tvNumberLikePost.setText(String.valueOf(postResponse.getNumLike()));
                    }
                }
            }
        });

        llComment.setOnClickListener(this);
        tvContentComment.setOnClickListener(this);
        tvNumberCommentPost.setOnClickListener(this);

//        llComment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                itemPostClickListener.onCommentClick(postResponse);
//            }
//        });
//
//        tvContentComment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                itemPostClickListener.onCommentClick(postResponse);
//            }
//        });

        ivMoreOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemPostClickListener.onMoreOptionClick(postResponse,getBindingAdapterPosition());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvContentComment:
            case R.id.tvNumberCommentPost:
            case R.id.llComment:
                mItemPostClickListener.onCommentClick(mPostResponse);
                break;
        }
    }
}

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {

    private Context context;
    private ArrayList<PostResponse> listPostResponse;
    private ItemPostClickListener itemPostClickListener;
    private boolean isGuest;

    public PostAdapter(Context context,boolean isGuest, ArrayList<PostResponse> listPostResponse, ItemPostClickListener itemPostClickListener) {
        this.context = context;
        this.listPostResponse = listPostResponse;
        this.itemPostClickListener = itemPostClickListener;
        this.isGuest = isGuest;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post,parent,false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.bind(context,isGuest,listPostResponse.get(position),itemPostClickListener);
    }

    @Override
    public int getItemCount() {
        return listPostResponse.size();
    }

    public void setItems(ArrayList<PostResponse> items){
        Log.i("ReviewFragment","Number items: "+items.size());
        listPostResponse = items;
        notifyDataSetChanged();
    }

    public interface ItemPostClickListener {
        void onCommentClick(PostResponse postResponse);
        void onMoreOptionClick(PostResponse postResponse, int position);
        void onLikeClick(PostResponse postResponse,boolean like);
    }
}
