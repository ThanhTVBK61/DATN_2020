package com.example.datn_2020.view.view_trip;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.datn_2020.R;
import com.example.datn_2020.adapter.home.PagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class TripFragment extends Fragment{

    private ViewPager viewPager;
    private TabLayout tabLayout;

    public TripFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip,container,false);
        //register views
        registerViews(view);
        registerTabs();
        return view;
    }

    private void registerViews(View view) {
        viewPager=view.findViewById(R.id.vpTripViewPager);
        tabLayout=view.findViewById(R.id.tlTabsTrip);
    }

    private void registerTabs(){
        PagerAdapter pagerAdapter = new PagerAdapter(getChildFragmentManager(),1);
        pagerAdapter.addNewTab(new TripTabLayoutFragment(),"Danh sách chuyến đi");
        pagerAdapter.addNewTab(new SeenTripTabLayoutFragment(),"Địa điểm đã xem");
        viewPager.setAdapter(pagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
    }

}
