package com.example.datn_2020.view.home.tabs_place_detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.datn_2020.R;
import com.example.datn_2020.repository.model.PlaceDetailHomeModel;
import com.example.datn_2020.viewmodel.ContainerVM;

public class OverviewFragment extends Fragment {

    private TextView tvTypePlace;
    private TextView tvContentOverview;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overview_place_detail,container,false);

        registerViews(view);
        registerData();

        return view;
    }

    private void registerData() {
        ContainerVM informationPlaceVM = new ViewModelProvider(getActivity()).get(ContainerVM.class);
        informationPlaceVM.getCurrentPlaceDetail().observe(getViewLifecycleOwner(), new Observer<PlaceDetailHomeModel>() {
            @Override
            public void onChanged(PlaceDetailHomeModel placeDetailHomeModel) {
                tvTypePlace.setText(placeDetailHomeModel.getType());
                tvContentOverview.setText(placeDetailHomeModel.getOverview());
            }
        });
    }

    private void registerViews(View view) {
        tvTypePlace = view.findViewById(R.id.tvTypePlace);
        tvContentOverview = view.findViewById(R.id.tvContentOverview);
    }
}
