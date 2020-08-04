package com.example.datn_2020.adapter.trip;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.datn_2020.R;
import com.example.datn_2020.repository.model.PlaceInTripModel;
import com.example.datn_2020.repository.model.PlaceModel;
import com.joooonho.SelectableRoundedImageView;

import java.util.ArrayList;

class ListPlacesInTripViewHolder extends RecyclerView.ViewHolder {

    private SelectableRoundedImageView imgPlaceInTrip;
    private TextView tvNamePlaceInTrip;
    private ImageButton ibPlaceInTrip;
    private TextView tvAddressPlaceInTrip;
    private RatingBar ratingMarkPlaceInTrip;
    private TextView tvNumberRatingPlaceInTrip;
    private ImageView ivDeletePlace;

    ListPlacesInTripViewHolder(@NonNull View itemView) {
        super(itemView);
        imgPlaceInTrip = itemView.findViewById(R.id.imgPlaceInTrip);
        tvNamePlaceInTrip = itemView.findViewById(R.id.tvNamePlaceInTrip);
        ibPlaceInTrip = itemView.findViewById(R.id.ibPlaceInTrip);
        tvAddressPlaceInTrip = itemView.findViewById(R.id.tvAddressPlaceInTrip);
        ratingMarkPlaceInTrip = itemView.findViewById(R.id.ratingMarkPlaceInTrip);
        tvNumberRatingPlaceInTrip = itemView.findViewById(R.id.tvNumberRatingPlaceInTrip);
        ivDeletePlace = itemView.findViewById(R.id.ivDeletePlace);
    }

    void bind(Context context, final PlaceInTripModel item_place, final int position, final ListPlaceInTripAdapter.ItemPlaceInTripItemClick mItemPlaceInTripItemClick) {
        if (item_place.getNamePlace() == null) {
            Log.i("ListFavourite", "null");
        } else {
            this.tvNamePlaceInTrip.setText(item_place.getNamePlace());
        }

//        đánh giá
        String numberRatingReview = item_place.getSumRating() + " đánh giá";
        this.tvNumberRatingPlaceInTrip.setText(numberRatingReview);

        this.tvAddressPlaceInTrip.setText(item_place.getAddress());
        String[] urls = item_place.getSrcImage().split(" ");
        Glide.with(context)
                .asBitmap()
                .load(urls[0])
                .into(this.imgPlaceInTrip);

        //Có phải là địa điểm thích
        if (item_place.getFavourite() == 1) {
            this.ibPlaceInTrip.setImageResource(R.drawable.ic_love);
            this.ibPlaceInTrip.setTag(R.drawable.ic_love);
        } else {
            this.ibPlaceInTrip.setImageResource(R.drawable.ic_love_bound);
            this.ibPlaceInTrip.setTag(R.drawable.ic_love_bound);
        }

//        Rating

        float rating = (item_place.getLocation() + item_place.getPrice() + item_place.getService() + item_place.getQuality()) / 4.0f;
        ratingMarkPlaceInTrip.setRating(rating);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemPlaceInTripItemClick.onItemClick(item_place);
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mItemPlaceInTripItemClick.onDeletePlace(item_place,position);
                return true;
            }
        });

        ivDeletePlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemPlaceInTripItemClick.onDeletePlace(item_place,position);
            }
        });

        this.ibPlaceInTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer resource = (Integer) ibPlaceInTrip.getTag();
                if (resource == R.drawable.ic_love) {
                    mItemPlaceInTripItemClick.onFavouriteItemClick(item_place, false);
                    ibPlaceInTrip.setImageResource(R.drawable.ic_love_bound);
                    ibPlaceInTrip.setTag(R.drawable.ic_love_bound);
                } else if(resource == R.drawable.ic_love_bound){
                    mItemPlaceInTripItemClick.onFavouriteItemClick(item_place, true);
                    ibPlaceInTrip.setImageResource(R.drawable.ic_love);
                    ibPlaceInTrip.setTag(R.drawable.ic_love);
                }
            }
        });

    }
}

public class ListPlaceInTripAdapter extends RecyclerView.Adapter<ListPlacesInTripViewHolder> {

    private Context context;
    private ArrayList<PlaceInTripModel> item_placeArrayList;
    private ItemPlaceInTripItemClick mItemPlaceInTripItemClick;

    public ListPlaceInTripAdapter(Context context, ArrayList<PlaceInTripModel> item_placeArrayList, ItemPlaceInTripItemClick mItemPlaceInTripItemClick) {
        this.context = context;
        this.item_placeArrayList = item_placeArrayList;
        this.mItemPlaceInTripItemClick = mItemPlaceInTripItemClick;
    }

    @NonNull
    @Override
    public ListPlacesInTripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_place_in_trip, parent, false);
        return new ListPlacesInTripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListPlacesInTripViewHolder holder, int position) {
        holder.bind(context, item_placeArrayList.get(position),position, mItemPlaceInTripItemClick);
    }

    @Override
    public int getItemCount() {
        return item_placeArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setItems(ArrayList<PlaceInTripModel> items){
        Log.i("TripDetail","set Items");
        this.item_placeArrayList = items;
        notifyDataSetChanged();
    }

     public interface ItemPlaceInTripItemClick {
         void onItemClick(PlaceInTripModel placeInTripModel);

         void onFavouriteItemClick(PlaceInTripModel placeInTripModel, boolean isChecked);

         void onDeletePlace(PlaceInTripModel placeInTripModel, int position);
     }
}

