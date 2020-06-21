package com.example.datn_2020.adapter.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.datn_2020.R;
import com.example.datn_2020.repository.model.NotificationModel;

import java.util.ArrayList;

class NotificationViewHolder extends RecyclerView.ViewHolder{

    private ImageView ivImageAccountNotification;
    private TextView tvContentNotification;

    NotificationViewHolder(@NonNull View itemView) {
        super(itemView);
        ivImageAccountNotification = itemView.findViewById(R.id.ivImageAccountNotification);
        tvContentNotification = itemView.findViewById(R.id.tvContentNotification);
    }

    void bind(Context context, NotificationModel notificationModel){
        String notification =" ";
        tvContentNotification.setText(notification);
        if(notificationModel.getImageAccount().equals(" ")){
            ivImageAccountNotification.setImageResource(R.drawable.ic_unknown_person);
        }else {
            Glide.with(context)
                    .asBitmap()
                    .load(notificationModel.getImageAccount())
                    .into(this.ivImageAccountNotification);
        }
    }
}

public class NotificationAdapter extends RecyclerView.Adapter<NotificationViewHolder> {


    private Context context;
    private ArrayList<NotificationModel> listNotification;

    public NotificationAdapter(Context context, ArrayList<NotificationModel> listNotification) {
        this.context = context;
        this.listNotification = listNotification;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification,parent,false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        holder.bind(context,listNotification.get(position));
    }

    @Override
    public int getItemCount() {
        return listNotification.size();
    }
}
