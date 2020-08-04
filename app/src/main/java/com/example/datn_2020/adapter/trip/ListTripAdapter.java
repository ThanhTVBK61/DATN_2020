package com.example.datn_2020.adapter.trip;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.datn_2020.R;
import com.example.datn_2020.repository.model.TripModel;
import com.joooonho.SelectableRoundedImageView;

import java.util.ArrayList;

class ListTripViewHolder extends RecyclerView.ViewHolder {

    private SelectableRoundedImageView imgTrip;
    private TextView tvNameTrip;
    private TextView tvNameAdmin;
    private ImageView ivDeleteTrip;

    ListTripViewHolder(@NonNull View itemView) {
        super(itemView);
        imgTrip = itemView.findViewById(R.id.imgTrip);
        tvNameTrip = itemView.findViewById(R.id.tvNameTrip);
        tvNameAdmin = itemView.findViewById(R.id.tvNameAdmin);
        ivDeleteTrip = itemView.findViewById(R.id.ivDeleteTrip);
    }

    void bind(Context context, final TripModel currentTrip, final int position, final ListTripAdapter.ItemTripClickListener mItemTripClickListener) {

        String name = currentTrip.getName().trim();
        String nameTrip = " ";
        String admin = " ";
        int pos = name.indexOf(" ");
        admin = name.substring(0, pos);
        nameTrip = name.substring(pos + 1);

        this.tvNameTrip.setText(nameTrip);
        this.tvNameAdmin.setText(admin);

        String url = currentTrip.getImageUrl();
        Glide.with(context)
                .asBitmap()
                .load(url)
                .into(this.imgTrip);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemTripClickListener.onItemClick(currentTrip);
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mItemTripClickListener.onDeleteTrip(currentTrip, position);
                return true;
            }
        });
        ivDeleteTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemTripClickListener.onDeleteTrip(currentTrip, position);
            }
        });
    }

}

public class ListTripAdapter extends RecyclerView.Adapter<ListTripViewHolder> {

    private Context context;
    private ArrayList<TripModel> tripModelArrayList;
    private ItemTripClickListener mItemTripClickListener;

    public ListTripAdapter(Context context, ArrayList<TripModel> tripModelArrayList, ItemTripClickListener itemTripClickListener) {
        this.context = context;
        this.tripModelArrayList = tripModelArrayList;
        this.mItemTripClickListener = itemTripClickListener;
    }

    @NonNull
    @Override
    public ListTripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trip, parent, false);
        return new ListTripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListTripViewHolder holder, int position) {
        holder.bind(context, tripModelArrayList.get(position), position, mItemTripClickListener);
    }

    @Override
    public int getItemCount() {
        return tripModelArrayList.size();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setItems(ArrayList<TripModel> items) {
        tripModelArrayList = items;
        notifyDataSetChanged();
    }

    public interface ItemTripClickListener {
        void onItemClick(TripModel tripModel);

        void onDeleteTrip(TripModel mTripModel, int position);
    }
}
