package com.example.datn_2020.adapter.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.datn_2020.R;
import com.example.datn_2020.model.PostResponse;

import java.util.ArrayList;

class PostViewHolder extends RecyclerView.ViewHolder{

    private ImageView ivAccount;
    private TextView tvName;
    private TextView contentRating;
    private TextView contentPost;
    private TextView tvNumberLikePost;
    private TextView tvNumberCommentPost;
    private TextView timePost;
    private RatingBar ratingBar;
    private RelativeLayout rlInformationLikeComment;

    PostViewHolder(@NonNull View itemView) {
        super(itemView);

        ivAccount = itemView.findViewById(R.id.ivAccountPost);
        tvName = itemView.findViewById(R.id.tvNameAccountPost);
        contentRating = itemView.findViewById(R.id.tvContentRating);
        contentPost = itemView.findViewById(R.id.tvContentPost);
        tvNumberLikePost = itemView.findViewById(R.id.tvNumberLikePost);
        tvNumberCommentPost = itemView.findViewById(R.id.tvNumberCommentPost);
        timePost = itemView.findViewById(R.id.tvTimePost);
        ratingBar = itemView.findViewById(R.id.rbRatingPost);
        rlInformationLikeComment = itemView.findViewById(R.id.rlInformationLikeComment);
    }

    void bind(final Context context, final PostResponse postResponse){
        if(postResponse.getUrl() == " "){
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
    }
}

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {

    private Context context;
    private ArrayList<PostResponse> listPostResponse;

    public PostAdapter(Context context, ArrayList<PostResponse> listPostResponse) {
        this.context = context;
        this.listPostResponse = listPostResponse;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post,parent,false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.bind(context,listPostResponse.get(position));
    }

    @Override
    public int getItemCount() {
        return listPostResponse.size();
    }
}
