package com.example.datn_2020.view.trip;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.datn_2020.R;

public class TripFragment extends Fragment {

    private FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip,container,false);

        fragmentManager = getChildFragmentManager();
        Log.i("TripFragment",String.valueOf(fragmentManager.getBackStackEntryCount()));

        fragmentManager.beginTransaction().add(R.id.flMainTrip,new MainTripFragment()).commit();

        return view;
    }

    public void replaceTripFragment(Fragment mFragment){
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_right_to_left,R.anim.exit_right_to_left,
                        R.anim.enter_left_to_right,R.anim.exit_left_to_right)
                .replace(R.id.flMainTrip,mFragment).addToBackStack(null).commit();
    }

    public void backTripStack(){
        fragmentManager.popBackStack();
    }

}
