package com.example.datn_2020.view.view_home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.datn_2020.R;
import com.example.datn_2020.adapter.ImageSliderAdapter;
import com.example.datn_2020.adapter.home.PlaceHomeAdapter;
import com.example.datn_2020.adapter.home.StartSnapHelper;
import com.example.datn_2020.model.PlaceResponse;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.util.ArrayList;
import java.util.Objects;


public class MainHomeFragment extends Fragment implements View.OnClickListener {

    private PlaceHomeAdapter placeHomeAdapter;
    private PlaceHomeAdapter placeNearHomeAdapter;
    private PlaceHomeAdapter placeRestaurantAdapter;
    private PlaceHomeAdapter placeHotelAdapter;
    private RecyclerView bestPlace;
    private RecyclerView nearPlace;
    private RecyclerView bestRestaurant;
    private RecyclerView bestHotel;
    private LinearLayout llMoreImage;
    private ArrayList<PlaceResponse> placeResponseArrayList = new ArrayList<>();
    private SearchView searchView;
    private WormDotsIndicator wormDotsIndicator;
    private ViewPager viewPager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_home,container,false);
        //Noi dung cua Collapsing
        registerCollapsingToolbar(view);

        //View pager
        String[] urls = {
                "https://images.unsplash.com/photo-1544614342-c48ab91d79fc?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
                "https://images.unsplash.com/photo-1544638627-725124bda50d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
                "https://images.unsplash.com/photo-1544638635-8a5838b796c6?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
                "https://images.unsplash.com/photo-1544633662-2b2afce79046?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"
        };

        registerViews(view);
        registerViewPager(urls);

        placeResponseArrayList.add(new PlaceResponse("https://images.unsplash.com/photo-1544614342-c48ab91d79fc?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
                                            1,"Biển Nha Trang",1,923));
        placeResponseArrayList.add(new PlaceResponse("https://images.unsplash.com/photo-1544614342-c48ab91d79fc?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
                                            1,"Biển Nha Trang",4,92367));
        placeResponseArrayList.add(new PlaceResponse("https://images.unsplash.com/photo-1544614342-c48ab91d79fc?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
                                            1,"Biển Nha Trang",4,92367));
        placeResponseArrayList.add(new PlaceResponse("https://images.unsplash.com/photo-1544614342-c48ab91d79fc?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
                                            1,"Biển Nha Trang",4,92367));
        placeResponseArrayList.add(new PlaceResponse("https://images.unsplash.com/photo-1544614342-c48ab91d79fc?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
                                            1,"Biển Nha Trang",4,92367));
        placeResponseArrayList.add(new PlaceResponse("https://images.unsplash.com/photo-1544614342-c48ab91d79fc?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
                                            1,"Biển Nha Trang",4,92367));
        placeResponseArrayList.add(new PlaceResponse("https://images.unsplash.com/photo-1544614342-c48ab91d79fc?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
                                            1,"Biển Nha Trang",4,92367));
        placeResponseArrayList.add(new PlaceResponse("https://images.unsplash.com/photo-1544614342-c48ab91d79fc?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
                                            1,"Biển Nha Trang",4,92367));
        placeResponseArrayList.add(new PlaceResponse("https://images.unsplash.com/photo-1544614342-c48ab91d79fc?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
                                            1,"Biển Nha Trang",4,92367));
        placeResponseArrayList.add(new PlaceResponse("https://images.unsplash.com/photo-1544614342-c48ab91d79fc?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
                                            1,"Biển Nha Trang",4,92367));
        placeResponseArrayList.add(new PlaceResponse("https://images.unsplash.com/photo-1544614342-c48ab91d79fc?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
                                            1,"Biển Nha Trang",4,92367));
        initRecyclerView();
        initRestaurantHotel();

        llMoreImage.setOnClickListener(this);
        return view;
    }

    private void registerViewPager(String[] urls) {
        viewPager.setAdapter(new ImageSliderAdapter(urls));
        wormDotsIndicator.setViewPager(viewPager);
    }

    private void registerViews(View view) {
        bestPlace = view.findViewById(R.id.rvrBestPlace);
        nearPlace = view.findViewById(R.id.rvrNearPlace);
        bestRestaurant = view.findViewById(R.id.rvrBestRestaurants);
        bestHotel = view.findViewById(R.id.rvrBestHotel);
        llMoreImage = view.findViewById(R.id.llMoreImageHome);
        wormDotsIndicator = view.findViewById(R.id.home_worm_dots_indicator);
        viewPager = view.findViewById(R.id.homeViewPager);
    }

    private void registerCollapsingToolbar(View view) {
        CollapsingToolbarLayout collapsingToolbarLayout = view.findViewById(R.id.homeCollapsingToolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar((Toolbar) view.findViewById(R.id.homeToolbar));
        collapsingToolbarLayout.setTitle(" ");
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.colorWhite));
    }

    private void initRestaurantHotel() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        bestRestaurant.setLayoutManager(linearLayoutManager);
        bestHotel.setLayoutManager(linearLayoutManager1);
        SnapHelper snapHelper = new StartSnapHelper();
        SnapHelper snapHelper1 = new StartSnapHelper();
        snapHelper.attachToRecyclerView(bestRestaurant);
        snapHelper1.attachToRecyclerView(bestHotel);
        placeRestaurantAdapter = new PlaceHomeAdapter(getActivity(),placeResponseArrayList);
        placeHotelAdapter = new PlaceHomeAdapter(getActivity(),placeResponseArrayList);
        bestRestaurant.setAdapter(placeRestaurantAdapter);
        bestHotel.setAdapter(placeHotelAdapter);
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        bestPlace.setLayoutManager(linearLayoutManager);
        nearPlace.setLayoutManager(linearLayoutManager1);
        SnapHelper snapHelper = new StartSnapHelper();
        SnapHelper snapHelper1 = new StartSnapHelper();
        snapHelper.attachToRecyclerView(bestPlace);
        snapHelper1.attachToRecyclerView(nearPlace);
        placeHomeAdapter = new PlaceHomeAdapter(getActivity(),placeResponseArrayList);
        placeNearHomeAdapter = new PlaceHomeAdapter(getActivity(),placeResponseArrayList);
        bestPlace.setAdapter(placeHomeAdapter);
        nearPlace.setAdapter(placeNearHomeAdapter);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_item_search,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onClick(View v) {
        HomeFragment homeFragment = (HomeFragment) getParentFragment();

        switch (v.getId()){
            case R.id.llMoreImageHome:
                if(homeFragment == null){
                    Log.i("mainHomeFragment","null");
                }else {
                    homeFragment.replaceHomeFragment(new PlaceDetail());
                    Objects.requireNonNull(getActivity()).findViewById(R.id.btnBottomNavigation).setVisibility(View.GONE);
                }

                break;
        }
    }
}
