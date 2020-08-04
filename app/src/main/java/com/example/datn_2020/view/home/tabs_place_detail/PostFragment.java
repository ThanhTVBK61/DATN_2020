package com.example.datn_2020.view.home.tabs_place_detail;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.datn_2020.R;
import com.example.datn_2020.repository.model.CurrentUser;
import com.example.datn_2020.repository.model.NewPostModel;
import com.example.datn_2020.view.account.AccountFragment;
import com.example.datn_2020.view.home.HomeFragment;
import com.example.datn_2020.view.trip.TripFragment;
import com.example.datn_2020.viewmodel.ContainerVM;
import com.example.datn_2020.viewmodel.placedetail.CommentVM;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PostFragment extends Fragment implements RatingBar.OnRatingBarChangeListener {

    private TextView tvSendPost;
    private TextView tvScorePost;
    private RatingBar rbRatingService;
    private RatingBar rbRatingQuality;
    private RatingBar rbRatingPrice;
    private RatingBar rbRatingLocation;
    private EditText etPost;
    private Toolbar tbPost;

    private HomeFragment homeFragment;
    private TripFragment tripFragment;
    private AccountFragment accountFragment;

    private String nameParent="";
    private int idPlace;
    private Context mContext;

    private ContainerVM postVM;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);
        Bundle mBundle = getArguments();
        if (mBundle != null) {
            idPlace = mBundle.getInt("idPlace");
            nameParent = mBundle.getString("nameParent");
            if (nameParent != null) {
                switch (nameParent) {
                    case "com/example/datn_2020/viewmodel/trip":
                        tripFragment = (TripFragment) getParentFragment();
                        break;
                    case "home":
                        homeFragment = (HomeFragment) getParentFragment();
                        break;
                    case "com/example/datn_2020/viewmodel/account":
                        accountFragment = (AccountFragment) getParentFragment();
                        break;
                }
            }
        }

        postVM = new ViewModelProvider(getActivity()).get(ContainerVM.class);
        registerViews(view);
        registerBackToolbar();

        rbRatingService.setOnRatingBarChangeListener(this);
        rbRatingQuality.setOnRatingBarChangeListener(this);
        rbRatingPrice.setOnRatingBarChangeListener(this);
        rbRatingLocation.setOnRatingBarChangeListener(this);

        tvSendPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.tvSendPost:
                        if(etPost.getText().toString().trim().equals("")){
                            AlertDialog alertDialog = new AlertDialog.Builder(mContext)
                                    .setTitle("Thông báo")
                                    .setMessage("Bạn chưa viết nhận xét!")
                                    .setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    }).create();
                            alertDialog.show();
                            Button negative = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                            negative.setTextColor(negative.getResources().getColor(R.color.colorBlue700));
                        }else {
                            NewPostModel newPostModel = new NewPostModel();

                            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm dd/MM/yyyy");
                            Date date = new Date();
                            String time = formatter.format(date);
                            newPostModel.setTime(time);
                            newPostModel.setContent(etPost.getText().toString());
                            newPostModel.setIdPlace(idPlace);
                            newPostModel.setIdUser(CurrentUser.getInstance().id);
                            newPostModel.setLocation(rbRatingLocation.getRating());
                            newPostModel.setPrice(rbRatingPrice.getRating());
                            newPostModel.setQuality(rbRatingQuality.getRating());
                            newPostModel.setService(rbRatingService.getRating());

                            postVM.addPost(newPostModel).observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                                @Override
                                public void onChanged(Boolean res) {

                                }
                            });
                            switch (nameParent) {
                                case "com/example/datn_2020/viewmodel/trip":
                                    tripFragment = (TripFragment) getParentFragment();
                                    break;
                                case "home":
                                    homeFragment = (HomeFragment) getParentFragment();
                                    break;
                                case "com/example/datn_2020/viewmodel/account":
                                    accountFragment = (AccountFragment) getParentFragment();
                                    break;
                            }
                        }
                        break;
                }
            }
        });

        return view;
    }

    private void registerViews(View view) {
        tvSendPost = view.findViewById(R.id.tvSendPost);
        tvScorePost = view.findViewById(R.id.tvScorePost);
        rbRatingService = view.findViewById(R.id.rbRatingService);
        rbRatingQuality = view.findViewById(R.id.rbRatingQuality);
        rbRatingPrice = view.findViewById(R.id.rbRatingPrice);
        rbRatingLocation = view.findViewById(R.id.rbRatingLocation);
        etPost = view.findViewById(R.id.etPost);
        tbPost = view.findViewById(R.id.tbPost);
    }

    private void registerBackToolbar() {
        tbPost.setNavigationIcon(R.drawable.ic_back);
        tbPost.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (nameParent) {
                    case "com/example/datn_2020/viewmodel/trip":
                        tripFragment.backTripStack();
                        break;
                    case "home":
                        homeFragment.backHomeStack();
                        break;
                    case "com/example/datn_2020/viewmodel/account":
                        accountFragment.backStack();
                        break;
                }
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mContext = null;
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        switch (ratingBar.getId()) {
            case R.id.rbRatingService:
                Log.i("rbYourRatingService", " " + rating);
                setTvScoreYourRating(rbRatingService.getRating(), rbRatingQuality.getRating(), rbRatingPrice.getRating(), rbRatingLocation.getRating());
                break;
            case R.id.rbRatingQuality:
                Log.i("rbYourRatingQuality", " " + rating);
                setTvScoreYourRating(rbRatingService.getRating(), rbRatingQuality.getRating(), rbRatingPrice.getRating(), rbRatingLocation.getRating());
                break;
            case R.id.rbRatingPrice:
                Log.i("rbYourRatingPrice", " " + rating);
                setTvScoreYourRating(rbRatingService.getRating(), rbRatingQuality.getRating(), rbRatingPrice.getRating(), rbRatingLocation.getRating());
                break;
            case R.id.rbRatingLocation:
                Log.i("rbYourRatingLocation", " " + rating);
                setTvScoreYourRating(rbRatingService.getRating(), rbRatingQuality.getRating(), rbRatingPrice.getRating(), rbRatingLocation.getRating());
                break;
        }
    }

    private void setTvScoreYourRating(float ratingService, float ratingQuality, float ratingPrice, float ratingLocation) {
        float score = (ratingService + ratingQuality + ratingPrice + ratingLocation) / 4;
        tvScorePost.setText(String.valueOf((float) Math.round(score * 10) / 10));
    }

}
