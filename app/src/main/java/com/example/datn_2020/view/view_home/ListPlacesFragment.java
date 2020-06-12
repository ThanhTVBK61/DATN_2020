package com.example.datn_2020.view.view_home;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_2020.R;
import com.example.datn_2020.adapter.home.ListPlacesAdapter;
import com.example.datn_2020.model.PlaceResponse;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.ArrayList;
import java.util.Objects;

public class ListPlacesFragment extends Fragment implements View.OnClickListener {

    private  final  String TAG = "ListPlaceFragment";
    private  final int ERROR_DIALOG_REQUEST = 9001;

    private Toolbar tbTitleListPlaces;
    private LinearLayout llFindMap;
    private RecyclerView rvListPlaces;
    private ArrayList<PlaceResponse> placeResponseArrayList = new ArrayList<>();
    private ListPlacesAdapter listPlacesAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_places,container,false);

        registerDataTest();
        registerViews(view);
        registerToolbar();
        registerRecyclerView();

        llFindMap.setOnClickListener(this);

        return view;
    }

    // Kiem tra dien thoai cua nguoi dung co ho tro google service
    public boolean isServiceOK(){
        Log.d(TAG,"isServiceOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getActivity());

        if(available == ConnectionResult.SUCCESS){
            //Nguoi dung co the tai Map
            Log.d(TAG,"isServices: Google Play Services is working");
            return  true;
        }else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //Co loi xay ra
            Log.d(TAG,"isServiceOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(getActivity(),available,ERROR_DIALOG_REQUEST);
            dialog.show();
        }else {
            Toast.makeText(getActivity(), "We can make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void registerDataTest() {
        placeResponseArrayList.add(new PlaceResponse("https://images.unsplash.com/photo-1544614342-c48ab91d79fc?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
                1,"Công viên Thống Nhất  nhất Công viên thống nhất",1,923,"101A Phương Mai Đống Đa Hà Nội"));
        placeResponseArrayList.add(new PlaceResponse("https://images.unsplash.com/photo-1544614342-c48ab91d79fc?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
                1,"Biển Nha Trang",4,92367,"101A Phương Mai Đống Đa Hà Nội"));
        placeResponseArrayList.add(new PlaceResponse("https://images.unsplash.com/photo-1544614342-c48ab91d79fc?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
                1,"Biển Nha Trang",4,92367,"101A Phương Mai Đống Đa Hà Nội"));
        placeResponseArrayList.add(new PlaceResponse("https://images.unsplash.com/photo-1544614342-c48ab91d79fc?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
                1,"Biển Nha Trang",4,92367,"101A Phương Mai Đống Đa Hà Nội"));
        placeResponseArrayList.add(new PlaceResponse("https://images.unsplash.com/photo-1544614342-c48ab91d79fc?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
                1,"Biển Nha Trang",4,92367,"101A Phương Mai Đống Đa Hà Nội"));
        placeResponseArrayList.add(new PlaceResponse("https://images.unsplash.com/photo-1544614342-c48ab91d79fc?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
                1,"Biển Nha Trang",4,92367,"101A Phương Mai Đống Đa Hà Nội"));
        placeResponseArrayList.add(new PlaceResponse("https://images.unsplash.com/photo-1544614342-c48ab91d79fc?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
                1,"Biển Nha Trang",4,92367,"101A Phương Mai Đống Đa Hà Nội"));
        placeResponseArrayList.add(new PlaceResponse("https://images.unsplash.com/photo-1544614342-c48ab91d79fc?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
                1,"Biển Nha Trang",4,92367,"101A Phương Mai Đống Đa Hà Nội"));
        placeResponseArrayList.add(new PlaceResponse("https://images.unsplash.com/photo-1544614342-c48ab91d79fc?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
                1,"Biển Nha Trang",4,92367,"101A Phương Mai Đống Đa Hà Nội"));
        placeResponseArrayList.add(new PlaceResponse("https://images.unsplash.com/photo-1544614342-c48ab91d79fc?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
                1,"Biển Nha Trang",4,92367,"101A Phương Mai Đống Đa Hà Nội"));
        placeResponseArrayList.add(new PlaceResponse("https://images.unsplash.com/photo-1544614342-c48ab91d79fc?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
                1,"Biển Nha Trang",4,92367,"101A Phương Mai Đống Đa Hà Nội"));
    }

    private void registerToolbar() {
        Bundle bundle = getArguments();
        if(bundle != null){
            tbTitleListPlaces.setTitle(bundle.getString("title"));
        }else {
            tbTitleListPlaces.setTitle("Không nhận được title");
        }
        tbTitleListPlaces.setNavigationIcon(R.drawable.ic_back);
        tbTitleListPlaces.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment homeFragment = (HomeFragment) getParentFragment();
                homeFragment.backHomeStack();
                Objects.requireNonNull(getActivity()).findViewById(R.id.btnBottomNavigation).setVisibility(View.VISIBLE);
            }
        });
    }

    private void registerRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        rvListPlaces.setLayoutManager(linearLayoutManager);
        listPlacesAdapter = new ListPlacesAdapter(getActivity(),placeResponseArrayList);
        rvListPlaces.setAdapter(listPlacesAdapter);
    }

    private void registerViews(View view) {
        tbTitleListPlaces = view.findViewById(R.id.tbTitleListPlaces);
        llFindMap = view.findViewById(R.id.llFindMap);
        rvListPlaces = view.findViewById(R.id.rvListPlaces);
    }

    @Override
    public void onClick(View v) {
        HomeFragment homeFragment = (HomeFragment) getParentFragment();
        switch (v.getId()){
            case R.id.llFindMap:
                if(isServiceOK()){
                    homeFragment.replaceHomeFragment(new MapsFragment());
                }
                break;
        }
    }
}
