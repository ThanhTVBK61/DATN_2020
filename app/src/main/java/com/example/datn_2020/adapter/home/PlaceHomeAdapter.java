package com.example.datn_2020.adapter.home;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.datn_2020.R;
import com.example.datn_2020.model.PlaceResponse;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;
import com.joooonho.SelectableRoundedImageView;

import java.util.ArrayList;

class ViewHolder extends RecyclerView.ViewHolder{

    private SelectableRoundedImageView imagePlace;
    private TextView namePlace;
    private TextView numberRating;
    private SmileRating smileRating;
    private Button favourite;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        imagePlace = itemView.findViewById(R.id.imgPlace);
        namePlace = itemView.findViewById(R.id.tvNamePlace);
        numberRating = itemView.findViewById(R.id.tvNumberRating);
        smileRating = itemView.findViewById(R.id.ratingMark);
        favourite = itemView.findViewById(R.id.btnFavourite);
    }

    public void bind(final Context context, final PlaceResponse item_place){
        this.namePlace.setText(item_place.getNamePlace());
        String numberRatingReview = item_place.getSumReview()+" đánh giá";
        this.numberRating.setText(numberRatingReview);
        Glide.with(context)
                .asBitmap()
                .load(item_place.getSrcImage())
                .into(this.imagePlace);
        if(item_place.getFavourite() == 1){
            this.favourite.setBackground(context.getDrawable(R.drawable.ic_star_checked));
        }else {
            this.favourite.setBackground(context.getDrawable(R.drawable.ic_star_unchecked));
        }

        this.favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item_place.getFavourite() == 1){
                    item_place.setFavourite(0);
                    favourite.setBackground(context.getDrawable(R.drawable.ic_star_unchecked));
                }else if(item_place.getFavourite() == 0){
                    item_place.setFavourite(1);
                    favourite.setBackground(context.getDrawable(R.drawable.ic_star_checked));
                }
            }
        });

        switch (item_place.getNumberStar()){
            case 1:
                smileRating.setSelectedSmile(BaseRating.TERRIBLE);
                break;
            case 2:
                smileRating.setSelectedSmile(BaseRating.BAD);
                break;
            case 3:
                smileRating.setSelectedSmile(BaseRating.OKAY);
                break;
            case 4:
                smileRating.setSelectedSmile(BaseRating.GOOD);
                break;
            case 5:
                smileRating.setSelectedSmile(BaseRating.GREAT);
                break;
        }
    }
}

public class PlaceHomeAdapter extends RecyclerView.Adapter<ViewHolder> {

    Context context;
    ArrayList<PlaceResponse> item_placeArrayList;

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
