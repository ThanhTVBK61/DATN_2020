package com.example.datn_2020.view.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.datn_2020.R;
import com.example.datn_2020.adapter.ImageSliderAdapter;
import com.example.datn_2020.adapter.home.PagerAdapter;
import com.example.datn_2020.model.PlaceDetailHomeModel;
import com.example.datn_2020.network.DisposableManager;
import com.example.datn_2020.view.home.tabs_place_detail.ContactBottomSheet;
import com.example.datn_2020.view.home.tabs_place_detail.MoreInformationFragment;
import com.example.datn_2020.view.home.tabs_place_detail.OverviewFragment;
import com.example.datn_2020.view.home.tabs_place_detail.ReviewFragment;
import com.example.datn_2020.viewmodel.home.InformationPlaceVM;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.util.Objects;

public class PlaceDetail extends Fragment implements View.OnClickListener {

    private AppBarLayout placeDetailAppbar;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private WormDotsIndicator wormDotsIndicator;
    private String[] urls;
    private InformationPlaceVM informationPlaceVM;
    private TabLayout tabLayout;
    private ViewPager viewPagerTabs;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private LinearLayout llViewContact;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place_detail,container,false);

        registerViews(view);
        //Khởi tạo Collapsing
        registerCollapsingPlace(view);
        //Khởi tạo nút back cho toolbar
        registerBackToolbar();
        loadData();
        registerTabs();

        llViewContact.setOnClickListener(this);

        return view;
    }

    private void registerTabs() {
        PagerAdapter pagerAdapter = new PagerAdapter(getChildFragmentManager(),0);
        pagerAdapter.addNewTab(new OverviewFragment(),"Tổng quan");
        pagerAdapter.addNewTab(new ReviewFragment(),"Đánh giá");
        pagerAdapter.addNewTab(new MoreInformationFragment(),"Thông tin");
        viewPagerTabs.setAdapter(pagerAdapter);

        tabLayout.setupWithViewPager(viewPagerTabs);
    }

    private void loadData() {
        informationPlaceVM = new ViewModelProvider(getActivity()).get(InformationPlaceVM.class);
        informationPlaceVM.loadInfoPlaceDetail();
        informationPlaceVM.getInformationPlaceResponse().observe(getActivity(), new Observer<PlaceDetailHomeModel>() {
            @Override
            public void onChanged(PlaceDetailHomeModel placeDetailHomeModel) {
                urls = placeDetailHomeModel.getListImageUrl().split(" ");
                //Khởi tạo viewpager
                registerViewPager(urls);
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
                HomeFragment homeFragment = (HomeFragment) getParentFragment();
                homeFragment.backHomeStack();
                DisposableManager.dispose();
                Objects.requireNonNull(getActivity()).findViewById(R.id.btnBottomNavigation).setVisibility(View.VISIBLE);
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
                    collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorBlack));
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
        }
    }
}
