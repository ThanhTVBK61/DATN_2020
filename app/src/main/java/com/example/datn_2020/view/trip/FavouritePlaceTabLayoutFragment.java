package com.example.datn_2020.view.trip;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_2020.R;
import com.example.datn_2020.adapter.ItemRecyclerViewClickListener;
import com.example.datn_2020.adapter.trip.ListFavouritePlaceAdapter;
import com.example.datn_2020.repository.model.PlaceModel;
import com.example.datn_2020.view.home.PlaceDetail;
import com.example.datn_2020.viewmodel.ContainerVM;

import java.util.ArrayList;

public class FavouriteTripTabLayoutFragment extends Fragment implements ItemRecyclerViewClickListener {

    private final String TAG = "FavouriteTripFragment";
    private RecyclerView rvListFavouritePlace;
    private ListFavouritePlaceAdapter listFavouritePlaceAdapter;
    private ArrayList<PlaceModel> placeResponseArrayList = new ArrayList<>();
    private ContainerVM favouriteTripVM;

    private FragmentActivity fragmentActivity;
    private TripFragment tripFragment;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite_trip_layout,container,false);

        Log.i(TAG,"onCreateView");

        favouriteTripVM = new ViewModelProvider(getActivity()).get(ContainerVM.class);

        MainTripFragment mainTripFragment = (MainTripFragment) getParentFragment();
        tripFragment = (TripFragment) mainTripFragment.getParentFragment();
        if (getActivity() != null) {
            fragmentActivity = getActivity();
        }

        rvListFavouritePlace = view.findViewById(R.id.rvListFavouritePlace);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rvListFavouritePlace.setLayoutManager(linearLayoutManager);
        listFavouritePlaceAdapter = new ListFavouritePlaceAdapter(getActivity(),placeResponseArrayList,this);
        rvListFavouritePlace.setAdapter(listFavouritePlaceAdapter);
        return view;
    }

    @Override
    public void onItemClick(PlaceModel placeModel) {
        favouriteTripVM.loadPlaceById(placeModel.getIdPlace());
        PlaceDetail placeDetail = new PlaceDetail();
        Bundle mBundle = new Bundle();
        mBundle.putBoolean("parent", true);
        placeDetail.setArguments(mBundle);
        tripFragment.replaceTripFragment(placeDetail);
        fragmentActivity.findViewById(R.id.btnBottomNavigation).setVisibility(View.GONE);
    }

    @Override
    public void onFavouriteItemClick(PlaceModel placeModel, boolean isChecked) {

    }

    @Override
    public void onResume() {
        super.onResume();
        favouriteTripVM.getActivateFragment().observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer == 1){
                    Log.i(TAG,"onResume");
                }
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.i(TAG,"onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG,"onCreate");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG,"onStart");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG,"onPause");
    }
}
