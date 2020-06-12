package com.example.datn_2020.adapter.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.datn_2020.R;
import com.example.datn_2020.model.PlaceResponse;
import com.joooonho.SelectableRoundedImageView;

import java.util.ArrayList;

class ViewHolder extends RecyclerView.ViewHolder{

    private SelectableRoundedImageView imagePlace;
    private TextView namePlace;
    private TextView numberRating;
    private TextView tvAddressPlace;
    private RatingBar ratingBar;
    private CheckBox favourite;

    ViewHolder(@NonNull View itemView) {
        super(itemView);

        imagePlace = itemView.findViewById(R.id.imgPlace);
        namePlace = itemView.findViewById(R.id.tvNamePlace);
        numberRating = itemView.findViewById(R.id.tvNumberRating);
        ratingBar = itemView.findViewById(R.id.ratingMark);
        favourite = itemView.findViewById(R.id.cbFavourite);
        tvAddressPlace = itemView.findViewById(R.id.tvAddressPlace);
    }

    void bind(Context context, final PlaceResponse item_place){
        this.namePlace.setText(item_place.getNamePlace());
        String numberRatingReview = "4.5 "+"("+ item_place.getSumReview()+")";
        this.numberRating.setText(numberRatingReview);
        this.tvAddressPlace.setText(item_place.getAddress());
        Glide.with(context)
                .asBitmap()
                .load(item_place.getSrcImage())
                .into(this.imagePlace);
        if(item_place.getFavourite() == 1){
            this.favourite.setChecked(true);
        }else {
            this.favourite.setChecked(false);
        }

        this.favourite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    item_place.setFavourite(1);
                    Log.i("Check","true");
                }else {
                    item_place.setFavourite(0);
                    Log.i("Check","false");
                }
            }
        });

        ratingBar.setRating(item_place.getNumberStar());
    }
}

public class PlaceHomeAdapter extends RecyclerView.Adapter<ViewHolder> {

    private Context context;
    private ArrayList<PlaceResponse> item_placeArrayList;

    public PlaceHomeAdapter(Context context, ArrayList<PlaceResponse> item_placeArrayList) {
        this.context = context;
        this.item_placeArrayList = item_placeArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_place,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(context,item_placeArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return item_placeArrayList.size();
    }
}
