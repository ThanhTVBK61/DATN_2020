package com.example.datn_2020.view.home.tabs_place_detail;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_2020.R;
import com.example.datn_2020.adapter.home.PostAdapter;
import com.example.datn_2020.repository.model.PostResponse;

import java.util.ArrayList;

public class ReviewFragment extends Fragment implements RatingBar.OnRatingBarChangeListener {

    private RatingBar rbYourRatingService, rbYourRatingQuality, rbYourRatingPrice, rbYourRatingLocation;
    private TextView tvScoreYourRating;
    private PostAdapter postAdapter;
    private RecyclerView recyclerViewPost;
    private ArrayList<PostResponse> listPostResponse = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review_place_detail, container, false);

        registerViews(view);

        rbYourRatingService.setOnRatingBarChangeListener(this);
        rbYourRatingQuality.setOnRatingBarChangeListener(this);
        rbYourRatingPrice.setOnRatingBarChangeListener(this);
        rbYourRatingLocation.setOnRatingBarChangeListener(this);
        listPostResponse.add(new PostResponse("Phạm Văn Khải"," ",4,"09:04 23-11-2020","Địa điểm này khá thú vị, chất lượng dịch vụ tốt"
        ,43,9));
        listPostResponse.add(new PostResponse("Đỗ Văn Dũng"," ",5,"13:04 20-02-2020","Giá cả phải chăng, xanh sạch đẹp"
        ,73,12));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerViewPost.setLayoutManager(linearLayoutManager);
        postAdapter = new PostAdapter(getActivity(),listPostResponse);
        recyclerViewPost.setAdapter(postAdapter);

        return view;
    }

    private void registerViews(View view) {
        rbYourRatingService = view.findViewById(R.id.rbYourRatingService);
        rbYourRatingQuality = view.findViewById(R.id.rbYourRatingQuality);
        rbYourRatingPrice = view.findViewById(R.id.rbYourRatingPrice);
        rbYourRatingLocation = view.findViewById(R.id.rbYourRatingLocation);
        tvScoreYourRating = view.findViewById(R.id.tvScoreYourRating);
        recyclerViewPost = view.findViewById(R.id.rvListPost);
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        switch (ratingBar.getId()){
            case R.id.rbYourRatingService:
                Log.i("rbYourRatingService", " "+ rating);
                setTvScoreYourRating(rbYourRatingService.getRating(),rbYourRatingQuality.getRating(),rbYourRatingPrice.getRating(),rbYourRatingLocation.getRating());
                break;
            case R.id.rbYourRatingQuality:
                Log.i("rbYourRatingQuality", " "+ rating);
                setTvScoreYourRating(rbYourRatingService.getRating(),rbYourRatingQuality.getRating(),rbYourRatingPrice.getRating(),rbYourRatingLocation.getRating());
                break;
            case R.id.rbYourRatingPrice:
                Log.i("rbYourRatingPrice", " "+ rating);
                setTvScoreYourRating(rbYourRatingService.getRating(),rbYourRatingQuality.getRating(),rbYourRatingPrice.getRating(),rbYourRatingLocation.getRating());
                break;
            case R.id.rbYourRatingLocation:
                Log.i("rbYourRatingLocation", " "+ rating);
                setTvScoreYourRating(rbYourRatingService.getRating(),rbYourRatingQuality.getRating(),rbYourRatingPrice.getRating(),rbYourRatingLocation.getRating());
                break;
        }
    }

    private void setTvScoreYourRating(float ratingService, float ratingQuality, float ratingPrice, float ratingLocation){
        float score = (ratingService + ratingQuality + ratingPrice + ratingLocation)/4;
        tvScoreYourRating.setText(String.valueOf((float)Math.round(score*10)/10));
    }

    @Override
    public void onDetach() {
        super.onDetach();

        Log.i("Review Fragment","Detach");
    }
}
