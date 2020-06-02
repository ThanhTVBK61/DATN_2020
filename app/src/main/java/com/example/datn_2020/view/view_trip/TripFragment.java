package com.example.datn_2020.view.view_trip;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.datn_2020.R;
import com.google.android.material.tabs.TabLayout;

public class TripFragment extends Fragment{

    private ViewPager pager;
    private TabLayout tabLayout;

    public TripFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip,container,false);
        pager=view.findViewById(R.id.vpTripViewPager);
        tabLayout=view.findViewById(R.id.tlTripTabLayout);
        addControl();
        return view;
    }

    private void addControl() {

    }
}
