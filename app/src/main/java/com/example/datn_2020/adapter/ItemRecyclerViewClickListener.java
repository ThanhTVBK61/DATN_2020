package com.example.datn_2020.adapter;
import com.example.datn_2020.repository.model.PlaceModel;

public interface ItemRecyclerViewClickListener {
    void onItemClick(PlaceModel placeModel, int position);
    void onFavouriteItemClick(PlaceModel placeModel, boolean isChecked, int position);
}
