package com.example.datn_2020.view.view_home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.example.datn_2020.view.view_home.tabs_place_detail.MoreInformationFragment;
import com.example.datn_2020.view.view_home.tabs_place_detail.OverviewFragment;
import com.example.datn_2020.view.view_home.tabs_place_detail.ReviewFragment;
import com.example.datn_2020.viewmodel.viewmodel_home.InformationPlaceVM;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.util.Objects;

public class PlaceDetail extends Fragment {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private WormDotsIndicator wormDotsIndicator;
    private String[] urls;
    private InformationPlaceVM informationPlaceVM;
    private TabLayout tabLayout;
    private ViewPager viewPagerTabs;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place_detail,container,false);

        registerViews(view);
        //Khởi tạo Collapsing
        registerCollapsingPlace(view);
        //Khởi tạo nút back cho toolbar
        registerBackToolbar();
        registerData();
        registerTabs();

        return view;
    }

    private void registerTabs() {
        PagerAdapter pagerAdapter = new PagerAdapter(getChildFragmentManager(),1);
        pagerAdapter.addNewTab(new OverviewFragment(),"Tổng quan");
        pagerAdapter.addNewTab(new ReviewFragment(),"Đánh giá");
        pagerAdapter.addNewTab(new MoreInformationFragment(),"Thông tin");
        viewPagerTabs.setAdapter(pagerAdapter);

        tabLayout.setupWithViewPager(viewPagerTabs);
    }

    private void registerData() {
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
        toolbar.setNavigationIcon(R.drawable.ic_back_white);
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
    }

    private void registerCollapsingPlace(View view) {
        CollapsingToolbarLayout collapsingToolbarLayout = view.findViewById(R.id.placeDetailCollapsingToolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar((Toolbar) view.findViewById(R.id.placeDetailToolbar));
        collapsingToolbarLayout.setTitle(" ");
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorWhite));
    }
}
