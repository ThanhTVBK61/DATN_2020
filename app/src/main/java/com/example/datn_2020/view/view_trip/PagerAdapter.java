package com.example.datn_2020.view.view_trip;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private String[] listTab = {"Chuyến đi","Đã xem gần đây"};
    private TripFragment tripFragment;
    private SeenTripTabLayoutFragment seenTripTabLayoutFragment;

    public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm,behavior);

        tripFragment = new TripFragment();
        seenTripTabLayoutFragment = new SeenTripTabLayoutFragment();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return tripFragment;
        }else return seenTripTabLayoutFragment;
    }

    @Override
    public int getCount() {
        return listTab.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listTab[position];
    }
}
