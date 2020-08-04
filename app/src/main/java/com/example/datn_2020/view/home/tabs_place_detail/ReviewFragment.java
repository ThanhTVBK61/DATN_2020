package com.example.datn_2020.view.home.tabs_place_detail;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_2020.R;
import com.example.datn_2020.adapter.home.PostAdapter;
import com.example.datn_2020.repository.model.ChangeRating;
import com.example.datn_2020.repository.model.CurrentUser;
import com.example.datn_2020.repository.model.DeleteComment;
import com.example.datn_2020.repository.model.PostCommentInfoModel;
import com.example.datn_2020.repository.model.PostInfoResponse;
import com.example.datn_2020.repository.model.PostLikeModel;
import com.example.datn_2020.repository.model.PostResponse;
import com.example.datn_2020.repository.model.RatingModel;
import com.example.datn_2020.repository.model.RatingResponse;
import com.example.datn_2020.repository.model.UpdatePlaceInTripModel;
import com.example.datn_2020.view.account.AccountFragment;
import com.example.datn_2020.view.home.HomeFragment;
import com.example.datn_2020.view.home.PlaceDetail;
import com.example.datn_2020.view.login.LoginActivity;
import com.example.datn_2020.view.trip.TripFragment;
import com.example.datn_2020.viewmodel.ContainerVM;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ReviewFragment extends Fragment implements RatingBar.OnRatingBarChangeListener, PostAdapter.ItemPostClickListener, View.OnClickListener, ReportDialogFragment.SelectReportDialogInterface {

    private final String TAG = "ReviewFragment";

    private RatingBar rbYourRatingService, rbYourRatingQuality, rbYourRatingPrice, rbYourRatingLocation;
    private TextView tvScoreYourRating;
    private TextView tvEditRating;
    private TextView tvLoginToRating;
    private boolean isGuest = false;
    private PostAdapter postAdapter;
    private RecyclerView recyclerViewPost;
    private ArrayList<PostResponse> listPostResponse = new ArrayList<>();

    private FragmentActivity fragmentActivity;
    private ContainerVM reviewVM;
    private Context mContext;
    private boolean updateRating = true;

    private int idPlace;
    private String namePlace;
    private String nameParent;
    private HomeFragment homeFragment;
    private TripFragment tripFragment;
    private AccountFragment accountFragment;
    private PlaceDetail placeDetail;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review_place_detail, container, false);

        placeDetail = (PlaceDetail) getParentFragment();

        if (getActivity() != null) {
            fragmentActivity = getActivity();
        }

        Bundle bundle = getArguments();
        if (bundle != null) {
            idPlace = bundle.getInt("idplace");
            namePlace = bundle.getString("nameplace");
            nameParent = bundle.getString("nameParent");
            if(nameParent!=null){
                switch (nameParent) {
                    case "com/example/datn_2020/viewmodel/trip":
                        tripFragment = (TripFragment) placeDetail.getParentFragment();
                        break;
                    case "home":
                        homeFragment = (HomeFragment) placeDetail.getParentFragment();
                        break;
                    case "com/example/datn_2020/viewmodel/account":
                        accountFragment = (AccountFragment) placeDetail.getParentFragment();
                        break;
                }
            }
        }
        registerViews(view);
        //Nếu là khách thì không thể rating được
        if (CurrentUser.getInstance().id == -1) {
            isGuest = true;
            tvEditRating.setVisibility(View.GONE);
            tvLoginToRating.setVisibility(View.VISIBLE);
        }


        reviewVM = new ViewModelProvider(fragmentActivity).get(ContainerVM.class);
        registerRecyclerView();

        if (!isGuest) {
            tvEditRating.setVisibility(View.VISIBLE);
            tvLoginToRating.setVisibility(View.GONE);
            //Lấy thông tin rating
            reviewVM.loadRatingById(CurrentUser.getInstance().id, idPlace);
            Log.i(TAG, "Loading rating ");
            reviewVM.getRatingModelResponse().observe(getViewLifecycleOwner(), new Observer<RatingResponse>() {
                @Override
                public void onChanged(RatingResponse mRatingResponse) {
                    if (mRatingResponse.isSuccess()) {
                        RatingModel ratingModel = mRatingResponse.getRatingModel();
                        setValueRatingBar(ratingModel.getLocationRating(), ratingModel.getPriceRating(), ratingModel.getQualityRating(), ratingModel.getServiceRating());
                    } else {
                        tvEditRating.setText("Bạn chưa đánh giá? Đánh giá");
                        tvEditRating.setTextColor(tvEditRating.getResources().getColor(R.color.colorGray700));
                    }
                }
            });
        }

        UpdatePlaceInTripModel mUpdatePlaceInTripModel = new UpdatePlaceInTripModel(CurrentUser.getInstance().id, idPlace);
        reviewVM.getInfoPost(mUpdatePlaceInTripModel);
        reviewVM.getInformationPost().observe(getViewLifecycleOwner(), new Observer<PostInfoResponse>() {
            @Override
            public void onChanged(PostInfoResponse postInfoResponse) {
                if (postInfoResponse.isSuccess()) {
                    listPostResponse.clear();
                    postInfoResponse.updateLiked();
                    int index = postInfoResponse.getPostCommentInfoModels().size();
                    Log.i(TAG,"Number post:"+ index);
                    ArrayList<PostCommentInfoModel> postCommentInfoModels = postInfoResponse.getPostCommentInfoModels();
                    ArrayList<PostLikeModel> postLikeModels = postInfoResponse.getSumLikes();
                    for (int i = 0; i < index; i++) {
                        PostResponse postResponse=new PostResponse();

                        postResponse.setIdAdminPost(postCommentInfoModels.get(i).getIdAdmin());
                        postResponse.setId(postCommentInfoModels.get(i).getIdPost());
                        postResponse.setName(postCommentInfoModels.get(i).getUsername());
                        postResponse.setTime(postCommentInfoModels.get(i).getTime());
                        postResponse.setContent(postCommentInfoModels.get(i).getContent());
                        postResponse.setNumComment(postCommentInfoModels.get(i).getSumComment());
                        postResponse.setRating(postCommentInfoModels.get(i).getRating());
                        postResponse.setNumLike(postLikeModels.get(i).getCountIdLike());
                        if(isGuest){
                            postResponse.setLiked(false);
                        }else{
                            postResponse.setLiked(postCommentInfoModels.get(i).isLiked());
                        }
//                        Log.i(TAG,"listPostResponse: +1");
                        listPostResponse.add(postResponse);
                    }
                    Log.i(TAG,"listPostResponse: "+listPostResponse.size());

                    postAdapter.setItems(listPostResponse);
                }
            }
        });

        rbYourRatingService.setOnRatingBarChangeListener(this);
        rbYourRatingQuality.setOnRatingBarChangeListener(this);
        rbYourRatingPrice.setOnRatingBarChangeListener(this);
        rbYourRatingLocation.setOnRatingBarChangeListener(this);
        tvEditRating.setOnClickListener(this);
        tvLoginToRating.setOnClickListener(this);



//        listPostResponse.add(new PostResponse(1, "Phạm Văn Khải", null, 4, "09:04 23-11-2020", "Địa điểm này khá thú vị, chất lượng dịch vụ tốt"
//                , 43, 9));
//        listPostResponse.add(new PostResponse(2, "Đỗ Văn Dũng", null, 5, "13:04 20-02-2020", "Giá cả phải chăng, xanh sạch đẹp"
//                , 73, 12));

        return view;
    }

    private void registerRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewPost.setLayoutManager(linearLayoutManager);
        postAdapter = new PostAdapter(getActivity(),isGuest, listPostResponse, this);
        recyclerViewPost.setAdapter(postAdapter);
    }

    private void registerViews(View view) {
        rbYourRatingService = view.findViewById(R.id.rbYourRatingService);
        rbYourRatingQuality = view.findViewById(R.id.rbYourRatingQuality);
        rbYourRatingPrice = view.findViewById(R.id.rbYourRatingPrice);
        rbYourRatingLocation = view.findViewById(R.id.rbYourRatingLocation);
        tvScoreYourRating = view.findViewById(R.id.tvScoreYourRating);
        recyclerViewPost = view.findViewById(R.id.rvListPost);
        tvEditRating = view.findViewById(R.id.tvEditRating);
        tvLoginToRating = view.findViewById(R.id.tvLoginToRating);
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        switch (ratingBar.getId()) {
            case R.id.rbYourRatingService:
                Log.i("rbYourRatingService", " " + rating);
                setTvScoreYourRating(rbYourRatingService.getRating(), rbYourRatingQuality.getRating(), rbYourRatingPrice.getRating(), rbYourRatingLocation.getRating());
                break;
            case R.id.rbYourRatingQuality:
                Log.i("rbYourRatingQuality", " " + rating);
                setTvScoreYourRating(rbYourRatingService.getRating(), rbYourRatingQuality.getRating(), rbYourRatingPrice.getRating(), rbYourRatingLocation.getRating());
                break;
            case R.id.rbYourRatingPrice:
                Log.i("rbYourRatingPrice", " " + rating);
                setTvScoreYourRating(rbYourRatingService.getRating(), rbYourRatingQuality.getRating(), rbYourRatingPrice.getRating(), rbYourRatingLocation.getRating());
                break;
            case R.id.rbYourRatingLocation:
                Log.i("rbYourRatingLocation", " " + rating);
                setTvScoreYourRating(rbYourRatingService.getRating(), rbYourRatingQuality.getRating(), rbYourRatingPrice.getRating(), rbYourRatingLocation.getRating());
                break;
        }
    }

    private void setTvScoreYourRating(float ratingService, float ratingQuality, float ratingPrice, float ratingLocation) {
        float score = (ratingService + ratingQuality + ratingPrice + ratingLocation) / 4;
        tvScoreYourRating.setText(String.valueOf((float) Math.round(score * 10) / 10));
    }

    @Override
    public void onCommentClick(PostResponse postResponse) {
        Bundle bundle = new Bundle();
        bundle.putInt("idPost",postResponse.getId());
        bundle.putInt("idPlace",idPlace);
        bundle.putInt("idAdmin",postResponse.getIdAdminPost());
        bundle.putString("username",postResponse.getName());
        bundle.putString("namePlace",namePlace);
        bundle.putString("nameParent",nameParent);
        CommentFragment commentFragment = new CommentFragment();
        commentFragment.setArguments(bundle);
        switch (nameParent) {
            case "com/example/datn_2020/viewmodel/trip":
                tripFragment.replaceTripFragment(commentFragment);
                break;
            case "home":
                homeFragment.replaceHomeFragment(commentFragment);
                break;
            case "com/example/datn_2020/viewmodel/account":
                accountFragment.replaceFragment(commentFragment);
                break;
        }
    }

    @Override
    public void onMoreOptionClick(final PostResponse postResponse, final int position) {
        if (CurrentUser.getInstance().id == postResponse.getIdAdminPost()) {
            AlertDialog alertDialog = new AlertDialog.Builder(mContext)
                    .setTitle("Thông báo")
                    .setMessage("Bạn có muốn xóa bình luận?")
                    .setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            reviewVM.deletePost(new DeleteComment(postResponse.getId()));
                            listPostResponse.remove(position);
                            postAdapter.notifyDataSetChanged();
                            dialog.cancel();
                        }
                    }).create();
            alertDialog.show();

            Button negative = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
            Button positive = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
            negative.setTextColor(negative.getResources().getColor(R.color.colorGray700));
            positive.setTextColor(negative.getResources().getColor(R.color.colorBlue700));


        } else {
            ReportDialogFragment reportDialogFragment = new ReportDialogFragment();
            reportDialogFragment.setSelectReportDialogInterface(this);
            reportDialogFragment.show(getChildFragmentManager(), null);
        }

    }

    @Override
    public void onLikeClick(PostResponse postResponse, boolean like) {
        if(like){
            reviewVM.likePost(new UpdatePlaceInTripModel(CurrentUser.getInstance().id,postResponse.getId()));
            reviewVM.getLikePostResponse().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean result) {
                    if(!result){
                        Toast.makeText(fragmentActivity, "Lỗi!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
            reviewVM.dislikePost(new UpdatePlaceInTripModel(CurrentUser.getInstance().id,postResponse.getId()));
            reviewVM.getDislikePostResponse().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean result) {
                    if(!result){
                        Toast.makeText(fragmentActivity, "Lỗi!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvEditRating:
                Log.i(TAG, "tvEditRating");
                if (tvEditRating.getText().toString().equals("Sửa")) {
                    Log.i(TAG, "tvEditRating -> Sửa");
                    updateRating = true;
                    tvEditRating.setTextColor(tvEditRating.getResources().getColor(R.color.colorBlue700));
                    tvEditRating.setText("Lưu");
                    setIndicator(false);
                } else if (tvEditRating.getText().toString().equals("Lưu")) {
                    Log.i(TAG, "tvEditRating -> Lưu");
                    tvEditRating.setTextColor(tvEditRating.getResources().getColor(R.color.colorGray700));
                    tvEditRating.setText("Sửa");
                    setIndicator(true);
                    if (updateRating) {
                        Log.i(TAG, "tvEditRating -> update");
                        updateRating();
                    } else {
                        Log.i(TAG, "tvEditRating -> add");
                        addRating();
                    }
                } else if (tvEditRating.getText().toString().equals("Bạn chưa đánh giá? Đánh giá")) {
                    updateRating = false;
                    tvEditRating.setTextColor(tvEditRating.getResources().getColor(R.color.colorBlue700));
                    tvEditRating.setText("Lưu");
                    setIndicator(false);
                }
                break;
            case R.id.tvLoginToRating:
                AlertDialog alertDialog = new AlertDialog.Builder(mContext)
                        .setTitle("Thông báo")
                        .setMessage("Đăng nhập để đánh giá!")
                        .setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).setPositiveButton("Đăng nhập", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent login = new Intent(fragmentActivity, LoginActivity.class);
                                fragmentActivity.startActivity(login);
                            }
                        }).create();
                alertDialog.show();
                Button negative = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                Button positive = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                negative.setTextColor(negative.getResources().getColor(R.color.colorGray700));
                positive.setTextColor(positive.getResources().getColor(R.color.colorBlue700));
                break;
        }
    }

    private void setIndicator(boolean change) {
        rbYourRatingService.setIsIndicator(change);
        rbYourRatingQuality.setIsIndicator(change);
        rbYourRatingPrice.setIsIndicator(change);
        rbYourRatingLocation.setIsIndicator(change);
    }

    private void setValueRatingBar(float location, float price, float quality, float service) {
        rbYourRatingService.setRating(location);
        rbYourRatingQuality.setRating(price);
        rbYourRatingPrice.setRating(quality);
        rbYourRatingLocation.setRating(service);
    }

    private void updateRating() {
        float location = rbYourRatingLocation.getRating();
        float service = rbYourRatingService.getRating();
        float quality = rbYourRatingQuality.getRating();
        float price = rbYourRatingPrice.getRating();
        Log.i(TAG, "Call updateRating");
        reviewVM.updateRating(new ChangeRating(CurrentUser.getInstance().id, idPlace, location, price, quality, service));
        reviewVM.getUpdateRatingResponse().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean result) {
                if (result) {
                    Toast.makeText(fragmentActivity, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(fragmentActivity, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addRating() {
        float location = rbYourRatingLocation.getRating();
        float service = rbYourRatingService.getRating();
        float quality = rbYourRatingQuality.getRating();
        float price = rbYourRatingPrice.getRating();
        Log.i(TAG, "Call addRating");
        reviewVM.addRating(new ChangeRating(CurrentUser.getInstance().id, idPlace, location, price, quality, service));
        reviewVM.getAddRatingResponse().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean result) {
                if (result) {
                    Toast.makeText(fragmentActivity, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(fragmentActivity, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.i(TAG, "Attach");
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mContext = null;
        Log.i("Review Fragment", "Detach");
    }

    @Override
    public void selectReport(String report) {

    }
}
