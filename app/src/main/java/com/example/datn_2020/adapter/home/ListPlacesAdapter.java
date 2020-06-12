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

class ListPlacesViewHolder extends RecyclerView.ViewHolder{

    private SelectableRoundedImageView imgListPlaces;
    private TextView tvNameListPlaces;
    private CheckBox cbFavouriteListPlaces;
    private TextView tvAddressListPlaces;
    private RatingBar ratingMarkListPlaces;
    private TextView tvNumberRatingListPlaces;

    ListPlacesViewHolder(@NonNull View itemView) {
        super(itemView);
        imgListPlaces = itemView.findViewById(R.id.imgListPlaces);
        tvNameListPlaces = itemView.findViewById(R.id.tvNameListPlaces);
        cbFavouriteListPlaces = itemView.findViewById(R.id.cbFavouriteListPlaces);
        tvAddressListPlaces = itemView.findViewById(R.id.tvAddressListPlaces);
        ratingMarkListPlaces = itemView.findViewById(R.id.ratingMarkListPlaces);
        tvNumberRatingListPlaces = itemView.findViewById(R.id.tvNumberRatingListPlaces);
    }

    void bind(Context context, final PlaceResponse item_place){
        this.tvNameListPlaces.setText(item_place.getNamePlace());
        String numberRatingReview = item_place.getSumReview()+" đánh giá";
        this.tvNumberRatingListPlaces.setText(numberRatingReview);
        this.tvAddressListPlaces.setText(item_place.getAddress());
        Glide.with(context)
                .asBitmap()
                .load(item_place.getSrcImage())
                .into(this.imgListPlaces);
        if(item_place.getFavourite() == 1){
            this.cbFavouriteListPlaces.setChecked(true);
        }else {
            this.cbFavouriteListPlaces.setChecked(false);
        }

        this.cbFavouriteListPlaces.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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

        ratingMarkListPlaces.setRating(item_place.getNumberStar());
    }
}

public class ListPlacesAdapter extends RecyclerView.Adapter<ListPlacesViewHolder>{

    private Context context;
    private ArrayList<PlaceResponse> item_placeArrayList;

    public ListPlacesAdapter(Context context, ArrayList<PlaceResponse> item_placeArrayList) {
        this.context = context;
        this.item_placeArrayList = item_placeArrayList;
    }

    @NonNull
    @Override
    public ListPlacesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_places,parent,false);
        return new ListPlacesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListPlacesViewHolder holder, int position) {
        holder.bind(context,item_placeArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return item_placeArrayList.size();
    }
}
