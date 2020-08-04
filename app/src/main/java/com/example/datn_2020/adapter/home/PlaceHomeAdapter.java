package com.example.datn_2020.adapter.home;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

class ViewHolder extends RecyclerView.ViewHolder {

    private SelectableRoundedImageView imagePlace;
    private TextView namePlace;
    private TextView numberRating;
    private TextView tvAddressPlace;
    private RatingBar ratingBar;
    private ImageButton ibFavourite;

    ViewHolder(@NonNull View itemView) {
        super(itemView);

        imagePlace = itemView.findViewById(R.id.imgPlace);
        namePlace = itemView.findViewById(R.id.tvNamePlace);
        numberRating = itemView.findViewById(R.id.tvNumberRating);
        ratingBar = itemView.findViewById(R.id.ratingMark);
        ibFavourite = itemView.findViewById(R.id.ibFavourite);
        tvAddressPlace = itemView.findViewById(R.id.tvAddressPlace);
    }

    void bind(final Context context, final PlaceModel currentPlace, final int position, final String type, final boolean isGuest, final PlaceHomeAdapter.ItemHomeClickListener itemClickListener) {
        this.namePlace.setText(currentPlace.getNamePlace());
        this.tvAddressPlace.setText(currentPlace.getAddress());

        String numberRatingReview = currentPlace.getSumRating() + " đánh giá";
        this.numberRating.setText(numberRatingReview);

        String[] urls = currentPlace.getSrcImage().split(" ");
        Glide.with(context)
                .asBitmap()
                .load(urls[0])
                .into(this.imagePlace);

        if (isGuest) {
            this.ibFavourite.setImageResource(R.drawable.ic_love_bound);
            this.ibFavourite.setTag(R.drawable.ic_love_bound);
        } else {
            if (currentPlace.getFavourite() == 1) {
                this.ibFavourite.setImageResource(R.drawable.ic_love);
                this.ibFavourite.setTag(R.drawable.ic_love);
            } else {
                this.ibFavourite.setImageResource(R.drawable.ic_love_bound);
                this.ibFavourite.setTag(R.drawable.ic_love_bound);
            }
        }

        this.ibFavourite.setOnClickListener(new View.OnClickListener() {
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
                    Integer resource = (Integer) ibFavourite.getTag();
                    if (resource == R.drawable.ic_love) {
                        itemClickListener.onFavouriteItemClick(currentPlace, false, position,type);
                        ibFavourite.setImageResource(R.drawable.ic_love_bound);
                        ibFavourite.setTag(R.drawable.ic_love_bound);
                    } else if (resource == R.drawable.ic_love_bound) {
                        itemClickListener.onFavouriteItemClick(currentPlace, true, position,type);
                        ibFavourite.setImageResource(R.drawable.ic_love);
                        ibFavourite.setTag(R.drawable.ic_love);
                    }
                }

            }
        });

        double rating = (currentPlace.getLocation() + currentPlace.getPrice() + currentPlace.getService() + currentPlace.getQuality()) / 4.0;
        ratingBar.setRating((float) rating);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClick(currentPlace);
            }
        });
    }
}

public class PlaceHomeAdapter extends RecyclerView.Adapter<ViewHolder> {

    private Context context;
    private ArrayList<PlaceModel> placeModels;
    private ItemHomeClickListener itemClickListener;
    private boolean isGuest;
    private String type;

    public PlaceHomeAdapter(Context context, boolean isGuest, ArrayList<PlaceModel> item_placeArrayList,String type, ItemHomeClickListener itemClickListener) {
        this.context = context;
        this.placeModels = item_placeArrayList;
        this.itemClickListener = itemClickListener;
        this.isGuest = isGuest;
        this.type = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_place, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(context, placeModels.get(position), position,type, isGuest, itemClickListener);
    }

    @Override
    public int getItemCount() {
        return placeModels.size();
    }

    public void setItems(ArrayList<PlaceModel> items) {
        placeModels = items;
        notifyDataSetChanged();
    }

    public interface ItemHomeClickListener {
        void onItemClick(PlaceModel placeModel);
        void onFavouriteItemClick(PlaceModel placeModel, boolean isChecked, int position,String type);
    }

}
