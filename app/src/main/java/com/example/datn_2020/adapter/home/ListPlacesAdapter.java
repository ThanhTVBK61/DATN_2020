package com.example.datn_2020.adapter.home;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.datn_2020.R;
import com.example.datn_2020.adapter.ItemRecyclerViewClickListener;
import com.example.datn_2020.repository.model.PlaceModel;
import com.joooonho.SelectableRoundedImageView;

import java.util.ArrayList;
import java.util.List;

class ListPlacesViewHolder extends RecyclerView.ViewHolder {

    private SelectableRoundedImageView imgListPlaces;
    private TextView tvNameListPlaces;
    private ImageButton ibFavouriteListPlaces;
    private TextView tvAddressListPlaces;
    private RatingBar ratingMarkListPlaces;
    private TextView tvNumberRatingListPlaces;

    ListPlacesViewHolder(@NonNull View itemView) {
        super(itemView);
        imgListPlaces = itemView.findViewById(R.id.imgListPlaces);
        tvNameListPlaces = itemView.findViewById(R.id.tvNameListPlaces);
        ibFavouriteListPlaces = itemView.findViewById(R.id.ibFavouriteListPlaces);
        tvAddressListPlaces = itemView.findViewById(R.id.tvAddressListPlaces);
        ratingMarkListPlaces = itemView.findViewById(R.id.ratingMarkListPlaces);
        tvNumberRatingListPlaces = itemView.findViewById(R.id.tvNumberRatingListPlaces);
    }

    void bind(final Context context, final boolean isGuest, final int position, final PlaceModel item_place, final ItemRecyclerViewClickListener itemClickListener) {
        this.tvNameListPlaces.setText(item_place.getNamePlace());

//        đánh giá
        String numberRatingReview = item_place.getSumRating() + " đánh giá";
        this.tvNumberRatingListPlaces.setText(numberRatingReview);

        this.tvAddressListPlaces.setText(item_place.getAddress());
        String[] urls = item_place.getSrcImage().split(" ");
        Glide.with(context)
                .asBitmap()
                .load(urls[0])
                .into(this.imgListPlaces);

        //Có phải là địa điểm thích
        if (isGuest) {
            this.ibFavouriteListPlaces.setImageResource(R.drawable.ic_love_bound);
            this.ibFavouriteListPlaces.setTag(R.drawable.ic_love_bound);
        } else {
            if (item_place.getFavourite() == 1) {
                this.ibFavouriteListPlaces.setImageResource(R.drawable.ic_love);
                this.ibFavouriteListPlaces.setTag(R.drawable.ic_love);
            } else {
                this.ibFavouriteListPlaces.setImageResource(R.drawable.ic_love_bound);
                this.ibFavouriteListPlaces.setTag(R.drawable.ic_love_bound);
            }
        }

//        Rating

        float rating = (item_place.getLocation() + item_place.getPrice() + item_place.getService() + item_place.getQuality()) / 4.0f;
        ratingMarkListPlaces.setRating(rating);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(item_place,position);
            }
        });

        this.ibFavouriteListPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isGuest) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("Thông báo");
                    alertDialogBuilder.setMessage("Đăng nhập để tạo danh sách địa điểm yêu thích !");
                    alertDialogBuilder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    Button negative = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                    negative.setTextColor(negative.getResources().getColor(R.color.colorBlue700));
                } else {
                    Integer resource = (Integer) ibFavouriteListPlaces.getTag();
                    if (resource == R.drawable.ic_love) {
                        itemClickListener.onFavouriteItemClick(item_place, false,position);
                        ibFavouriteListPlaces.setImageResource(R.drawable.ic_love_bound);
                        ibFavouriteListPlaces.setTag(R.drawable.ic_love_bound);
                    } else if (resource == R.drawable.ic_love_bound) {
                        itemClickListener.onFavouriteItemClick(item_place, true,position);
                        ibFavouriteListPlaces.setImageResource(R.drawable.ic_love);
                        ibFavouriteListPlaces.setTag(R.drawable.ic_love);
                    }
                }
            }
        });
    }
}

public class ListPlacesAdapter extends RecyclerView.Adapter<ListPlacesViewHolder> implements Filterable {

    private Context context;
    private List<PlaceModel> item_placeArrayList;
    private ArrayList<PlaceModel> itemPlacesFull;
    private ItemRecyclerViewClickListener itemClickListener;
    private boolean isGuest;

    public ListPlacesAdapter(Context context, boolean isGuest, ArrayList<PlaceModel> item_placeArrayList, ItemRecyclerViewClickListener itemPlaceMapClickListener) {
        this.context = context;
        this.item_placeArrayList = item_placeArrayList;
        this.itemPlacesFull = new ArrayList<>(item_placeArrayList) ;
        this.itemClickListener = itemPlaceMapClickListener;
        this.isGuest = isGuest;
    }

    @NonNull
    @Override
    public ListPlacesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_places, parent, false);
        return new ListPlacesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListPlacesViewHolder holder, int position) {
        holder.bind(context, isGuest,position, item_placeArrayList.get(position), itemClickListener);
    }

    public void setItems(ArrayList<PlaceModel> items) {
        Log.i("SearchPlaceFragment1","items: "+ items.size());
        item_placeArrayList = items;
        Log.i("SearchPlaceFragment1","item_placeArrayList: "+ item_placeArrayList.size());
        itemPlacesFull = new ArrayList<>(items);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return item_placeArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return  position;
    }

    @Override
    public Filter getFilter() {
        return placeFilter;
    }

    private Filter placeFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<PlaceModel> filterListPlaces = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filterListPlaces.addAll(itemPlacesFull);
            } else {
                String filter = constraint.toString().toLowerCase().trim();
                for (PlaceModel currentPlace : itemPlacesFull) {
                    if (currentPlace.getNamePlace().toLowerCase().contains(filter)){
                        filterListPlaces.add(currentPlace);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filterListPlaces;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            item_placeArrayList.clear();
            item_placeArrayList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
