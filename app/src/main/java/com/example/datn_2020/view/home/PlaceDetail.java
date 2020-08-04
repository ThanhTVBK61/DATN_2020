package com.example.datn_2020.view.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.datn_2020.R;
import com.example.datn_2020.adapter.ImageSliderAdapter;
import com.example.datn_2020.adapter.home.PagerAdapter;
import com.example.datn_2020.repository.model.CurrentUser;
import com.example.datn_2020.repository.model.PlaceDetailHomeModel;
import com.example.datn_2020.repository.network.DisposableManager;
import com.example.datn_2020.view.account.AccountFragment;
import com.example.datn_2020.view.home.tabs_place_detail.ContactBottomSheet;
import com.example.datn_2020.view.home.tabs_place_detail.MoreInformationFragment;
import com.example.datn_2020.view.home.tabs_place_detail.OverviewFragment;
import com.example.datn_2020.view.home.tabs_place_detail.PostFragment;
import com.example.datn_2020.view.home.tabs_place_detail.ReviewFragment;
import com.example.datn_2020.view.trip.TripFragment;
import com.example.datn_2020.viewmodel.ContainerVM;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.util.Objects;

public class PlaceDetail extends Fragment implements View.OnClickListener {

    private final String TAG = "PlaceDetail";

    private AppBarLayout placeDetailAppbar;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private WormDotsIndicator wormDotsIndicator;
    private String[] urls;
    private ContainerVM informationPlaceVM;
    private boolean isGuest = false;
    private TabLayout tabLayout;
    private ViewPager viewPagerTabs;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private LinearLayout llViewContact;
    private PagerAdapter pagerAdapter;

    //attribute
    private TextView tvNamePlaceDetail;
    private TextView tvAddressPlaceDetail;
    private TextView tvTimePlaceDetail;
    private TextView tvSumReviews;
    private TextView tvNumberOfPicture;
    private TextView tvScorePlaceDetail;
    private RatingBar rbRatingLocation;
    private RatingBar rbRatingPrice;
    private RatingBar rbRatingQuality;
    private RatingBar rbRatingService;

    private HomeFragment homeFragment;
    private TripFragment tripFragment;
    private AccountFragment accountFragment;
    private Boolean visible = false;
    private String nameParent = " ";
    private int idPlace;
    private FragmentActivity fragmentActivity;
    private FloatingActionButton fbAddPost;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place_detail,container,false);

        if(getActivity()!= null){
            fragmentActivity = getActivity();
        }

        if(CurrentUser.getInstance().id == -1){
            isGuest = true;
        }

        Bundle bundle = getArguments();
        if(bundle != null){
            visible = bundle.getBoolean("parent");
            idPlace = bundle.getInt("id");
            nameParent = bundle.getString("name");
            if(nameParent!=null){
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


        informationPlaceVM = new ViewModelProvider(fragmentActivity).get(ContainerVM.class);
        registerViews(view);
        //Khởi tạo Collapsing
        registerCollapsingPlace(view);
        //Khởi tạo nút back cho toolbar
        registerBackToolbar();
        loadData();
        registerTabs();

        llViewContact.setOnClickListener(this);
        fbAddPost.setOnClickListener(this);

        return view;
    }

    private void registerTabs() {
        pagerAdapter = new PagerAdapter(getChildFragmentManager(),0);

        ReviewFragment reviewFragment = new ReviewFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("idplace",idPlace);
        bundle.putString("nameParent",nameParent);
        bundle.putString("nameplace",tvNamePlaceDetail.getText().toString());
        reviewFragment.setArguments(bundle);

        pagerAdapter.addNewTab(new OverviewFragment(),"Tổng quan");
        pagerAdapter.addNewTab(reviewFragment,"Đánh giá");
        pagerAdapter.addNewTab(new MoreInformationFragment(),"Thông tin");
        viewPagerTabs.setAdapter(pagerAdapter);

        tabLayout.setupWithViewPager(viewPagerTabs);
    }

    private void loadData() {
        informationPlaceVM.loadPlaceById(idPlace);
        informationPlaceVM.getCurrentPlaceDetail().observe(getViewLifecycleOwner(), new Observer<PlaceDetailHomeModel>() {
            @Override
            public void onChanged(PlaceDetailHomeModel placeDetailHomeModel) {
                Log.i(TAG,"Load data");
                tvNamePlaceDetail.setText(placeDetailHomeModel.getNamePlace());
                tvAddressPlaceDetail.setText(placeDetailHomeModel.getAddress());
                tvTimePlaceDetail.setText(placeDetailHomeModel.getTime());
                String sumRating = placeDetailHomeModel.getSumRating()+ " lượt đánh giá";
                tvSumReviews.setText(sumRating);
                rbRatingLocation.setRating(placeDetailHomeModel.getLocation());
                rbRatingPrice.setRating(placeDetailHomeModel.getPrice());
                rbRatingQuality.setRating(placeDetailHomeModel.getQuality());
                rbRatingService.setRating(placeDetailHomeModel.getService());

                String[] urls = placeDetailHomeModel.getSrcImage().split(" ");
                tvNumberOfPicture.setText(String.valueOf(urls.length));
                registerViewPager(urls);
                float score = (placeDetailHomeModel.getLocation()+placeDetailHomeModel.getPrice()+placeDetailHomeModel.getService()+placeDetailHomeModel.getQuality())/4.0f;
                double roundOff = Math.round(score * 100.0) / 100.0;
                tvScorePlaceDetail.setText(String.valueOf(roundOff));
            }
        });
    }

    private void registerViewPager(String[] urls) {
        viewPager.setAdapter(new ImageSliderAdapter(urls));
        wormDotsIndicator.setViewPager(viewPager);
    }

    private void registerBackToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
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

                if(visible){
                    Objects.requireNonNull(getActivity()).findViewById(R.id.btnBottomNavigation).setVisibility(View.VISIBLE);
                }else {
                    Objects.requireNonNull(getActivity()).findViewById(R.id.btnBottomNavigation).setVisibility(View.GONE);
                }
            }
        });
    }

    private void registerViews(View view) {
        toolbar = view.findViewById(R.id.placeDetailToolbar);
        viewPager = view.findViewById(R.id.placeDetailViewPager);
        wormDotsIndicator = view.findViewById(R.id.place_Detail_Worm_Dots_Indicator);
        tabLayout = view.findViewById(R.id.tlTabsDetailPlace);
        viewPagerTabs = view.findViewById(R.id.vpTabsDetailPlace);
        collapsingToolbarLayout = view.findViewById(R.id.placeDetailCollapsingToolbar);
        llViewContact = view.findViewById(R.id.llContact);
        placeDetailAppbar = view.findViewById(R.id.placeDetailAppbar);
        //attribute
        tvNamePlaceDetail = view.findViewById(R.id.tvNamePlaceDetail);
        tvAddressPlaceDetail = view.findViewById(R.id.tvAddressPlaceDetail);
        tvTimePlaceDetail = view.findViewById(R.id.tvTimePlaceDetail);
        tvScorePlaceDetail = view.findViewById(R.id.tvScorePlaceDetail);
        tvNumberOfPicture = view.findViewById(R.id.tvNumberOfPicture);
        tvSumReviews = view.findViewById(R.id.tvSumReviews);
        rbRatingLocation = view.findViewById(R.id.rbRatingLocation);
        rbRatingPrice = view.findViewById(R.id.rbRatingPrice);
        rbRatingQuality = view.findViewById(R.id.rbRatingQuality);
        rbRatingService = view.findViewById(R.id.rbRatingService);
        fbAddPost = view.findViewById(R.id.fbAddPost);

    }

    private void registerCollapsingPlace(View view) {
        final CollapsingToolbarLayout collapsingToolbarLayout = view.findViewById(R.id.placeDetailCollapsingToolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar((Toolbar) view.findViewById(R.id.placeDetailToolbar));
//        collapsingToolbarLayout.setTitle("Công viên thống nhất");
//        collapsingToolbarLayout.setExpandedTitleMarginBottom(1100);
//        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorBlack));
//        collapsingToolbarLayout.setExpandedTitleColor(Color.parseColor("#00FFFFFF"));

        placeDetailAppbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {

            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (Math.abs(verticalOffset) - scrollRange == 0) {
                    collapsingToolbarLayout.setTitle("Title");
                    collapsingToolbarLayout.setCollapsedTitleTextColor(collapsingToolbarLayout.getResources().getColor(R.color.colorBlack));
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle(" ");//careful there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.llContact:
                Log.i("Linear","clicked");
                ContactBottomSheet contactBottomSheet = new ContactBottomSheet();
                contactBottomSheet.show(getChildFragmentManager(),"ContactBottomSheet");
                break;
            case R.id.fbAddPost:
                PostFragment postFragment = new PostFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("idPlace",idPlace);
                bundle.putString("nameParent", nameParent);
                postFragment.setArguments(bundle);
                switch (nameParent) {
                    case "com/example/datn_2020/viewmodel/trip":
                        tripFragment.replaceTripFragment(postFragment);
                        break;
                    case "home":
                        homeFragment.replaceHomeFragment(postFragment);
                        break;
                    case "com/example/datn_2020/viewmodel/account":
                        accountFragment.replaceFragment(postFragment);
                        break;
                }
                break;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        DisposableManager.dispose();
    }
}
