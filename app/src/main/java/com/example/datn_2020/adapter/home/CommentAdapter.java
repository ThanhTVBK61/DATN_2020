package com.example.datn_2020.adapter.home;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.datn_2020.R;
import com.example.datn_2020.repository.model.CommentModel;

import java.util.ArrayList;

class CommentViewHolder extends RecyclerView.ViewHolder {

    private TextView tvContentComment;
    private TextView tvNumberLikeComment;
    private TextView tvTimeComment;
    private ImageView ivPersonComment;
    private ImageView ivLikeComment;


    CommentViewHolder(@NonNull View itemView) {
        super(itemView);

        tvContentComment = itemView.findViewById(R.id.tvContentComment);
        tvNumberLikeComment = itemView.findViewById(R.id.tvNumberLikeComment);
        tvTimeComment = itemView.findViewById(R.id.tvTimeComment);
        ivPersonComment = itemView.findViewById(R.id.ivPersonComment);
        ivLikeComment = itemView.findViewById(R.id.ivLikeComment);
    }

    void bind(final Context context, final boolean isGuest, final CommentModel commentModel, final CommentAdapter.ItemCommentClickListener itemCommentClickListener) {
        //Set dữ liệu cho textview
        String content = "<b>" + commentModel.getUsername() + "</b><br>" + commentModel.getContent();
        tvContentComment.setText(android.text.Html.fromHtml(content));
        tvNumberLikeComment.setText(String.valueOf(commentModel.getSumLike()));
        tvTimeComment.setText(commentModel.getTime());

        //Set dữ liệu cho imageView
        if (commentModel.getImageUrlUser().equals("")) {
            ivPersonComment.setImageResource(R.drawable.ic_unknown_person);
        } else {
            Glide.with(context)
                    .asBitmap()
                    .load(commentModel.getImageUrlUser())
                    .into(this.ivPersonComment);
        }

        if (commentModel.getLike() == 1) {
            ivLikeComment.setColorFilter(ivLikeComment.getResources().getColor(R.color.colorBlue700));
        } else {
            ivLikeComment.setColorFilter(ivLikeComment.getResources().getColor(R.color.colorGray700));
        }

        ivLikeComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isGuest) {
                    AlertDialog alertDialog = new AlertDialog.Builder(context)
                            .setTitle("Thông báo")
                            .setMessage("Đăng nhập để thích bình luận")
                            .setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).create();
                    alertDialog.show();
                    Button negative = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                    negative.setTextColor(negative.getResources().getColor(R.color.colorBlue700));
                } else {
                    if (commentModel.getLike() == 1) {
                        commentModel.setLike(0);
                        int sum = Integer.parseInt(tvNumberLikeComment.getText().toString()) - 1;
                        tvNumberLikeComment.setText(String.valueOf(sum));
                        ivLikeComment.setColorFilter(ivLikeComment.getResources().getColor(R.color.colorGray700));

                        itemCommentClickListener.onLikeComment(commentModel.getIdComment(), false);
                    } else {
                        commentModel.setLike(1);
                        int sum = Integer.parseInt(tvNumberLikeComment.getText().toString()) + 1;
                        tvNumberLikeComment.setText(String.valueOf(sum));
                        ivLikeComment.setColorFilter(ivLikeComment.getResources().getColor(R.color.colorBlue700));

                        itemCommentClickListener.onLikeComment(commentModel.getIdComment(), true);
                    }
                }
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                itemCommentClickListener.onMoreOptionClick(commentModel, getBindingAdapterPosition());
                return true;
            }
        });

    }

}

public class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {

    private Context context;
    private ArrayList<CommentModel> commentModels;
    private ItemCommentClickListener mItemCommentClickListener;
    private boolean isGuest;

    public CommentAdapter(Context context, boolean isGuest, ArrayList<CommentModel> commentModels, ItemCommentClickListener itemCommentClickListener) {
        this.context = context;
        this.commentModels = commentModels;
        this.mItemCommentClickListener = itemCommentClickListener;
        this.isGuest = isGuest;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        holder.bind(context, isGuest, commentModels.get(position), mItemCommentClickListener);
    }

    @Override
    public int getItemCount() {
        return commentModels.size();
    }

    public void setItems(ArrayList<CommentModel> items) {
        Log.i("ReviewFragment", "Number items: " + items.size());
        commentModels = items;
        notifyDataSetChanged();
    }

    public void addItems(CommentModel item) {
        commentModels.add(item);
        notifyItemInserted(commentModels.size());
    }

    public interface ItemCommentClickListener {
        void onLikeComment(int idComment, boolean like);

        void onMoreOptionClick(CommentModel commentModel, int position);
    }
}