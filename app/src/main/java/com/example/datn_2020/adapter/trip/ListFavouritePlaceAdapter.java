package com.example.datn_2020.adapter.trip;


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
import com.example.datn_2020.adapter.ItemRecyclerViewClickListener;
import com.example.datn_2020.repository.model.PlaceModel;
import com.joooonho.SelectableRoundedImageView;

import java.util.ArrayList;

class ListFavouritePlacesViewHolder extends RecyclerView.ViewHolder {

    private SelectableRoundedImageView imgFavouritePlace;
    private TextView tvNameFavouritePlace;
    private ImageButton ibFavouritePlace;
    private TextView tvAddressFavouritePlace;
    private RatingBar ratingMarkFavouritePlace;
    private TextView tvNumberRatingFavouritePlace;
    private TextView tvAddtoTrip;

    ListFavouritePlacesViewHolder(@NonNull View itemView) {
        super(itemView);
        imgFavouritePlace = itemView.findViewById(R.id.imgFavouritePlace);
        tvNameFavouritePlace = itemView.findViewById(R.id.tvNameFavouritePlace);
        ibFavouritePlace = itemView.findViewById(R.id.ibFavouritePlace);
        tvAddressFavouritePlace = itemView.findViewById(R.id.tvAddressFavouritePlace);
        ratingMarkFavouritePlace = itemView.findViewById(R.id.ratingMarkFavouritePlace);
        tvNumberRatingFavouritePlace = itemView.findViewById(R.id.tvNumberRatingFavouritePlace);
        tvAddtoTrip = itemView.findViewById(R.id.tvAddtoTrip);
    }

    void bind(Context context, final PlaceModel item_place, final int position, final ListFavouritePlaceAdapter.ItemFavouritePlaceInTripListener itemFavouritePlaceInTripListener) {
        if (item_place.getNamePlace() == null) {
            Log.i("ListFavourite", "null");
        } else {
            this.tvNameFavouritePlace.setText(item_place.getNamePlace());
        }

//        đánh giá
        String numberRatingReview = item_place.getSumRating() + " đánh giá";
        this.tvNumberRatingFavouritePlace.setText(numberRatingReview);

        this.tvAddressFavouritePlace.setText(item_place.getAddress());
        String[] urls = item_place.getSrcImage().split(" ");
        Glide.with(context)
                .asBitmap()
                .load(urls[0])
                .into(this.imgFavouritePlace);


//        Rating

        float rating = (item_place.getLocation() + item_place.getPrice() + item_place.getService() + item_place.getQuality()) / 4.0f;
        ratingMarkFavouritePlace.setRating(rating);

        tvAddtoTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemFavouritePlaceInTripListener.onAddToTrip(item_place);
            }
        });

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemFavouritePlaceInTripListener.onItemClick(item_place);
            }
        });

        this.ibFavouritePlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemFavouritePlaceInTripListener.onFavouriteItemClick(item_place, position);
            }
        });

    }
}

public class ListFavouritePlaceAdapter extends RecyclerView.Adapter<ListFavouritePlacesViewHolder> {

    private Context context;
    private ArrayList<PlaceModel> item_placeArrayList;
    private ItemFavouritePlaceInTripListener mItemFavouritePlaceInTripListener;

    public ListFavouritePlaceAdapter(Context context, ArrayList<PlaceModel> item_placeArrayList, ItemFavouritePlaceInTripListener mItemFavouritePlaceInTripListener) {
        this.context = context;
        this.item_placeArrayList = item_placeArrayList;
        this.mItemFavouritePlaceInTripListener = mItemFavouritePlaceInTripListener;
    }

    @NonNull
    @Override
    public ListFavouritePlacesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favourite_place, parent, false);
        return new ListFavouritePlacesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListFavouritePlacesViewHolder holder, int position) {
        holder.bind(context, item_placeArrayList.get(position),position, mItemFavouritePlaceInTripListener);
    }

    @Override
    public int getItemCount() {
        return item_placeArrayList.size();
    }

    public interface ItemFavouritePlaceInTripListener {
        void onItemClick(PlaceModel placeModel);
        void onFavouriteItemClick(PlaceModel placeModel, int position);
        void onAddToTrip(PlaceModel placeModel);
    }

}



