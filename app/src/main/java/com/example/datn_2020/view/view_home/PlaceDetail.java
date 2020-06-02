package com.example.datn_2020.view.view_home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.datn_2020.R;
import com.example.datn_2020.adapter.ImageSliderAdapter;
import com.example.datn_2020.network.DisposableManager;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.util.Objects;

public class PlaceDetail extends Fragment {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private WormDotsIndicator wormDotsIndicator;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_place_detail,container,false);

        registerViews(view);
        //Khởi tạo Collapsing
        registerCollapsingPlace(view);
        //Khởi tạo nút back cho toolbar
        registerBackToolbar();
        //Khởi tạo viewpager
        String[] urls = {
                "https://images.unsplash.com/photo-1544614342-c48ab91d79fc?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
                "https://images.unsplash.com/photo-1544638627-725124bda50d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
                "https://images.unsplash.com/photo-1544638635-8a5838b796c6?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
                "https://images.unsplash.com/photo-1544633662-2b2afce79046?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"
        };

        registerViewPager(urls);

        return view;
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
    }

    private void registerCollapsingPlace(View view) {
        CollapsingToolbarLayout collapsingToolbarLayout = view.findViewById(R.id.placeDetailCollapsingToolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar((Toolbar) view.findViewById(R.id.placeDetailToolbar));
        collapsingToolbarLayout.setTitle(" ");
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorWhite));
    }
}
