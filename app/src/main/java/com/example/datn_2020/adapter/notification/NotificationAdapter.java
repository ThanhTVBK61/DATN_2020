package com.example.datn_2020.adapter.notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.datn_2020.R;
import com.example.datn_2020.repository.model.NotificationModel;

import java.util.ArrayList;

class NotificationViewHolder extends RecyclerView.ViewHolder {

    private ImageView ivImageAccountNotification;
    private ImageView ivConfirm;
    private TextView tvTimeNotification;
    private TextView tvConfirm;
    private TextView tvContentNotification;
    private Button btnAgreeInvitation;
    private Button btnDeleteInvitation;
    private RelativeLayout rlItemNotification;

    NotificationViewHolder(@NonNull View itemView) {
        super(itemView);
        ivImageAccountNotification = itemView.findViewById(R.id.ivImageAccountNotification);
        ivConfirm = itemView.findViewById(R.id.ivConfirm);
        tvTimeNotification = itemView.findViewById(R.id.tvTimeNotification);
        tvConfirm = itemView.findViewById(R.id.tvConfirm);
        tvContentNotification = itemView.findViewById(R.id.tvContentNotification);
        btnAgreeInvitation = itemView.findViewById(R.id.btnAgreeInvitation);
        btnDeleteInvitation = itemView.findViewById(R.id.btnDeleteInvitation);
        rlItemNotification = itemView.findViewById(R.id.rlItemNotification);
    }

    void bind(Context context, final NotificationModel notificationModel, final NotificationAdapter.ItemClickListener mItemClickListener) {
        if (notificationModel.getType().equals("Mời")) {
            String content = "<b>" + notificationModel.getNameAccount() + "</b> đã mời bạn tham gia chuyến đi <b>" + notificationModel.getNameTripPlace() + "<b>";
            tvContentNotification.setText(android.text.Html.fromHtml(content));
            ivConfirm.setVisibility(View.GONE);
            tvConfirm.setVisibility(View.GONE);
            btnAgreeInvitation.setVisibility(View.VISIBLE);
            btnDeleteInvitation.setVisibility(View.VISIBLE);
        } else if (notificationModel.getType().equals("Chấp nhận")) {
            String content = "<b>" + notificationModel.getNameAccount() + "</b> đã mời bạn tham gia chuyến đi <b>" + notificationModel.getNameTripPlace() + "<b>";
            tvContentNotification.setText(android.text.Html.fromHtml(content));
            btnAgreeInvitation.setVisibility(View.GONE);
            btnDeleteInvitation.setVisibility(View.GONE);
            ivConfirm.setVisibility(View.VISIBLE);
            tvConfirm.setVisibility(View.VISIBLE);
            tvConfirm.setText("Đã chấp nhận");
            ivConfirm.setBackgroundResource(R.drawable.ic_join);
        } else if (notificationModel.getType().equals("Từ chối")) {
            String content = "<b>" + notificationModel.getNameAccount() + "</b> đã mời bạn tham gia chuyến đi <b>" + notificationModel.getNameTripPlace() + "<b>";
            tvContentNotification.setText(android.text.Html.fromHtml(content));
            btnAgreeInvitation.setVisibility(View.GONE);
            btnDeleteInvitation.setVisibility(View.GONE);
            ivConfirm.setVisibility(View.VISIBLE);
            tvConfirm.setVisibility(View.VISIBLE);
            tvConfirm.setText("Đã từ chối");
            ivConfirm.setBackgroundResource(R.drawable.ic_reject);
        }else if(notificationModel.getType().equals("Bình luận")){
            String content = "<b>" + notificationModel.getNameAccount() + "</b> đã bình luận bài viết của bạn trong địa điểm <b>" + notificationModel.getNameTripPlace() + "<b>";
            tvContentNotification.setText(android.text.Html.fromHtml(content));
            btnAgreeInvitation.setVisibility(View.GONE);
            btnDeleteInvitation.setVisibility(View.GONE);
            ivConfirm.setVisibility(View.GONE);
            tvConfirm.setVisibility(View.GONE);
        }

        tvTimeNotification.setText(notificationModel.getTime());

        if(notificationModel.getStatus() == 1){
            rlItemNotification.setBackgroundColor(rlItemNotification.getResources().getColor(R.color.notification));
        }

        if (notificationModel.getImageAccount().equals("")) {
            ivImageAccountNotification.setImageResource(R.drawable.ic_unknown_person);
        } else {
            Glide.with(context)
                    .asBitmap()
                    .load(notificationModel.getImageAccount())
                    .into(this.ivImageAccountNotification);
        }
        btnAgreeInvitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAgreeInvitation.setVisibility(View.GONE);
                btnDeleteInvitation.setVisibility(View.GONE);
                ivConfirm.setVisibility(View.VISIBLE);
                tvConfirm.setVisibility(View.VISIBLE);
                tvConfirm.setText("Đã chấp nhận");
                ivConfirm.setBackgroundResource(R.drawable.ic_join);
                rlItemNotification.setBackgroundColor(rlItemNotification.getResources().getColor(R.color.colorWhite));
                notificationModel.setType("Đồng ý");
                mItemClickListener.onAgreeClick(notificationModel.getIdTripPlace(), notificationModel.getIdNotification());
            }
        });

        //Khi bấm hủy thì sẽ không hiển thị lên nữa
        btnDeleteInvitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAgreeInvitation.setVisibility(View.GONE);
                btnDeleteInvitation.setVisibility(View.GONE);
                ivConfirm.setVisibility(View.VISIBLE);
                tvConfirm.setVisibility(View.VISIBLE);
                tvConfirm.setText("Đã từ chối");
                ivConfirm.setBackgroundResource(R.drawable.ic_reject);
                rlItemNotification.setBackgroundColor(rlItemNotification.getResources().getColor(R.color.colorWhite));
                notificationModel.setType("Từ chối");
                mItemClickListener.onRejectClick(notificationModel.getIdNotification());
            }
        });


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(notificationModel.getStatus() == 1){
                    rlItemNotification.setBackgroundColor(rlItemNotification.getResources().getColor(R.color.colorWhite));
                    mItemClickListener.onItemClick(notificationModel.getIdNotification());
                }
            }
        });

    }
}

public class NotificationAdapter extends RecyclerView.Adapter<NotificationViewHolder> {


    private Context context;
    private ArrayList<NotificationModel> listNotification;
    private ItemClickListener mItemClickListener;

    public NotificationAdapter(Context context, ArrayList<NotificationModel> listNotification, ItemClickListener itemClickListener) {
        this.context = context;
        this.listNotification = listNotification;
        this.mItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        holder.bind(context, listNotification.get(position), mItemClickListener);
    }

    @Override
    public int getItemCount() {
        return listNotification.size();
    }

    public void addItem(NotificationModel newItem) {
        this.listNotification.add(0, newItem);
        notifyItemInserted(0);
    }

    public void setItems(ArrayList<NotificationModel> items) {
        listNotification = items;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClick(int idNotification);
        void onAgreeClick(int idTrip, int idNotification);
        void onRejectClick(int idNotification);
    }
}
