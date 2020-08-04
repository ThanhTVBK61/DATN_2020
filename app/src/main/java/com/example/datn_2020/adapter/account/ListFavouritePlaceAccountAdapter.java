package com.example.datn_2020.adapter.account;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.datn_2020.R;
import com.example.datn_2020.repository.model.PlaceModel;
import com.joooonho.SelectableRoundedImageView;

import java.util.ArrayList;

class ListFavouritePlacesAccountViewHolder extends RecyclerView.ViewHolder {

    private SelectableRoundedImageView imgFavouritePlaceAccount;
    private TextView tvNameFavouritePlaceAccount;
    private ImageButton ibFavouritePlaceAccount;
    private TextView tvAddressFavouritePlaceAccount;
    private RatingBar ratingMarkFavouritePlaceAccount;
    private TextView tvNumberRatingFavouritePlaceAccount;

    ListFavouritePlacesAccountViewHolder(@NonNull View itemView) {
        super(itemView);
        imgFavouritePlaceAccount = itemView.findViewById(R.id.imgFavouritePlaceAccount);
        tvNameFavouritePlaceAccount = itemView.findViewById(R.id.tvNameFavouritePlaceAccount);
        ibFavouritePlaceAccount = itemView.findViewById(R.id.ibFavouritePlaceAccount);
        tvAddressFavouritePlaceAccount = itemView.findViewById(R.id.tvAddressFavouritePlaceAccount);
        ratingMarkFavouritePlaceAccount = itemView.findViewById(R.id.ratingMarkFavouritePlaceAccount);
        tvNumberRatingFavouritePlaceAccount = itemView.findViewById(R.id.tvNumberRatingFavouritePlaceAccount);
    }

    void bind(Context context, final PlaceModel item_place, final int position, final ListFavouritePlaceAccountAdapter.ItemViewClickListener mItemViewClickListener) {
        if (item_place.getNamePlace() == null) {
            Log.i("ListFavourite", "null");
        } else {
            this.tvNameFavouritePlaceAccount.setText(item_place.getNamePlace());
        }

//        đánh giá
        String numberRatingReview = item_place.getSumRating() + " đánh giá";
        this.tvNumberRatingFavouritePlaceAccount.setText(numberRatingReview);

        this.tvAddressFavouritePlaceAccount.setText(item_place.getAddress());
        String[] urls = item_place.getSrcImage().split(" ");
        Glide.with(context)
                .asBitmap()
                .load(urls[0])
                .into(this.imgFavouritePlaceAccount);

//        Rating

        float rating = (item_place.getLocation() + item_place.getPrice() + item_place.getService() + item_place.getQuality()) / 4.0f;
        ratingMarkFavouritePlaceAccount.setRating(rating);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemViewClickListener.onItemClick(item_place);
            }
        });

        this.ibFavouritePlaceAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemViewClickListener.onFavouriteItemClick(item_place, position);
            }
        });
    }
}

public class ListFavouritePlaceAccountAdapter extends RecyclerView.Adapter<ListFavouritePlacesAccountViewHolder> {

    private Context context;
    private ArrayList<PlaceModel> item_placeArrayList;
    private ItemViewClickListener itemViewClickListener;

    public ListFavouritePlaceAccountAdapter(Context context, ArrayList<PlaceModel> item_placeArrayList, ItemViewClickListener itemViewClickListener) {
        this.context = context;
        this.item_placeArrayList = item_placeArrayList;
        this.itemViewClickListener = itemViewClickListener;
    }

    @NonNull
    @Override
    public ListFavouritePlacesAccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favourite_place_account, parent, false);
        return new ListFavouritePlacesAccountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListFavouritePlacesAccountViewHolder holder, int position) {
        holder.bind(context, item_placeArrayList.get(position),position, itemViewClickListener);
    }

    @Override
    public int getItemCount() {
        return item_placeArrayList.size();
    }

    public interface ItemViewClickListener {
        void onItemClick(PlaceModel placeModel);

        void onFavouriteItemClick(PlaceModel placeModel, int position);
    }
}

