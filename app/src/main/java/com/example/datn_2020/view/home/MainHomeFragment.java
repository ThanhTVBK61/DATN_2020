package com.example.datn_2020.view.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
import android.widget.TextView;

import com.example.datn_2020.R;
import com.example.datn_2020.adapter.ImageSliderAdapter;
import com.example.datn_2020.adapter.home.PlaceHomeAdapter;
import com.example.datn_2020.adapter.home.StartSnapHelper;
import com.example.datn_2020.repository.model.PlaceResponse;
import com.example.datn_2020.view.home.tabs_place_detail.OtherTypeBottomSheetDialog;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.util.ArrayList;
import java.util.Objects;


public class MainHomeFragment extends Fragment implements View.OnClickListener {

    private final String TAG="ListPlacesFragment";
    private final String FINE_LOCATION= Manifest.permission.ACCESS_FINE_LOCATION;
    private final String COURSE_LOCATION=Manifest.permission.ACCESS_COARSE_LOCATION;
    private final int LOCATION_PERMISSION_REQUEST_CODE =1234;

    private Boolean mLocationPermissionGranted = false;

    private RecyclerView bestPlace;
    private RecyclerView nearPlace;
    private TextView tvGanViTri;
    private TextView tvAllNearPlace;
    private RecyclerView bestRestaurant;
    private RecyclerView bestHotel;
    private LinearLayout llMoreImage;
    private ArrayList<PlaceResponse> placeResponseArrayList = new ArrayList<>();
    private SearchView searchView;
    private WormDotsIndicator wormDotsIndicator;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private LinearLayout llHotelPlaces,llMuseumPlaces,llCinemaPlaces,llParkPlaces;
    private LinearLayout llUniversity,llRestaurantPlaces,llBankPlaces,llOtherPlaces;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_home,container,false);
        //Noi dung cua Collapsing
        registerCollapsingToolbar(view);

        //Kiem tra xem google map da dc cai dat chua
//
//        List<ApplicationInfo> mApps = getActivity().getPackageManager().getInstalledApplications(PackageManager.GET_META_DATA);
//        Log.i("Installed app","Show app installed"+ mApps.size());
//
//        for (ApplicationInfo applicationInfo : mApps){
//
//            Log.i("Installed app",applicationInfo.packageName);
//            if(applicationInfo.packageName.equals("com.google.android.apps.maps"));
//            {
//                Log.i("Installed app","true");
//
//            }
//        }
//        ImageView imageView = view.findViewById(R.id.ivIconGoogleMaps);
//        try {
//            Drawable icon = getContext().getPackageManager().getApplicationIcon("com.google.android.apps.maps");
//            imageView.setImageDrawable(icon);
//        } catch (PackageManager.NameNotFoundException e) {
//            Log.i("Installed app","get icon error :"+ e.getMessage());
//            e.printStackTrace();
//        }


//                Uri gmmIntentUri = Uri.parse("google.navigation:q=21.004792, 105.845290");
////                Uri gmmIntentUri = Uri.parse("geo:0,0?q=1,Đại Cồ Việt, Hai Bà Trưng, Hà Nội");
////                Uri gmmIntentUri = Uri.parse("geo:0,0?q=21.004792, 105.845290(Đại học bách khoa)");
//                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//                mapIntent.setPackage("com.google.android.apps.maps");
//                if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
//                    startActivity(mapIntent);
//                }



        //View pager
        String[] urls = {
                "http://10.0.2.2:3000/congvienthongnhat/cvtn1.jpg",
                "https://images.unsplash.com/photo-1544614342-c48ab91d79fc?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
                "https://images.unsplash.com/photo-1544638627-725124bda50d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
                "https://images.unsplash.com/photo-1544638635-8a5838b796c6?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
                "https://images.unsplash.com/photo-1544633662-2b2afce79046?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60"
        };

        registerViews(view);
        registerViewPager(urls);

        registerDataTest();
        initRecyclerView(nearPlace,placeResponseArrayList);
        initRecyclerView(bestPlace,placeResponseArrayList);
        initRecyclerView(bestRestaurant,placeResponseArrayList);
        initRecyclerView(bestHotel,placeResponseArrayList);

        llMoreImage.setOnClickListener(this);
        llHotelPlaces.setOnClickListener(this);
        llMuseumPlaces.setOnClickListener(this);
        llOtherPlaces.setOnClickListener(this);
        return view;
    }

    private void registerDataTest() {
        placeResponseArrayList.add(new PlaceResponse("https://images.unsplash.com/photo-1544614342-c48ab91d79fc?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60",
                                            1,"Biển Nha Trang",1,923,"101A Phương Mai Đống Đa Hà Nội"));
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

    private void registerViewPager(String[] urls) {
        viewPager.setAdapter(new ImageSliderAdapter(urls));
        wormDotsIndicator.setViewPager(viewPager);
    }

    private void registerViews(View view) {
        bestPlace = view.findViewById(R.id.rvrBestPlace);
        nearPlace = view.findViewById(R.id.rvrNearPlace);
        tvGanViTri = view.findViewById(R.id.tvGanViTri);
        tvAllNearPlace = view.findViewById(R.id.tvAllNearPlace);
        bestRestaurant = view.findViewById(R.id.rvrBestRestaurants);
        bestHotel = view.findViewById(R.id.rvrBestHotel);
        llMoreImage = view.findViewById(R.id.llMoreImageHome);
        wormDotsIndicator = view.findViewById(R.id.home_worm_dots_indicator);
        viewPager = view.findViewById(R.id.homeViewPager);
        //Linear layout type place
        llHotelPlaces = view.findViewById(R.id.llHotelPlaces);
        llMuseumPlaces = view.findViewById(R.id.llMuseumPlaces);
        llCinemaPlaces = view.findViewById(R.id.llCinemaPlaces);
        llParkPlaces = view.findViewById(R.id.llParkPlaces);
        llUniversity = view.findViewById(R.id.llUniversity);
        llRestaurantPlaces = view.findViewById(R.id.llRestaurantPlaces);
        llBankPlaces = view.findViewById(R.id.llBankPlaces);
        llOtherPlaces = view.findViewById(R.id.llOtherPlaces);
    }

    private void registerCollapsingToolbar(View view) {
        CollapsingToolbarLayout collapsingToolbarLayout = view.findViewById(R.id.homeCollapsingToolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar((Toolbar) view.findViewById(R.id.homeToolbar));
        collapsingToolbarLayout.setTitle("Hà nội  ");
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.colorBlack));
        collapsingToolbarLayout.setExpandedTitleColor(Color.parseColor("#00FFFFFF"));
    }

    private void initRecyclerView(RecyclerView mRecyclerView, ArrayList<PlaceResponse> listPlaceResponses) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        SnapHelper snapHelper = new StartSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);
        PlaceHomeAdapter mPlaceHomeAdapter = new PlaceHomeAdapter(getActivity(),listPlaceResponses);
        mRecyclerView.setAdapter(mPlaceHomeAdapter);
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
            case R.id.llHotelPlaces:
                if (homeFragment == null) {
                    Log.i("mainHomeFragment", "null");
                } else {
                    ListPlacesFragment listPlacesFragment = new ListPlacesFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("title", "Danh sách khách sạn");
                    listPlacesFragment.setArguments(bundle);
                    homeFragment.replaceHomeFragment(listPlacesFragment);
                    Objects.requireNonNull(getActivity()).findViewById(R.id.btnBottomNavigation).setVisibility(View.GONE);
                }
                break;
            case R.id.llMuseumPlaces:
                if (homeFragment == null) {
                    Log.i("mainHomeFragment", "null");
                } else {
                    ListPlacesFragment listPlacesFragment = new ListPlacesFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("title", "Danh sách bảo tàng");
                    bundle.putString("type","Bảo tàng");
                    listPlacesFragment.setArguments(bundle);
                    homeFragment.replaceHomeFragment(listPlacesFragment);
                    Objects.requireNonNull(getActivity()).findViewById(R.id.btnBottomNavigation).setVisibility(View.GONE);
                }
                break;
            case  R.id.llOtherPlaces:
                OtherTypeBottomSheetDialog otherTypeBottomSheetDialog = new OtherTypeBottomSheetDialog();
                otherTypeBottomSheetDialog.show(getChildFragmentManager(),"othertype");
                break;
        }
    }

    private void getLocationPermission(){
        Log.d(TAG,"getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(getContext(),FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(getContext(),COURSE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                mLocationPermissionGranted = true;
            }else {
                ActivityCompat.requestPermissions(getActivity(),permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else {
            ActivityCompat.requestPermissions(getActivity(),permissions,LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG,"onRequestPermissionsResult: called.");
        mLocationPermissionGranted = false;
        if(requestCode == LOCATION_PERMISSION_REQUEST_CODE){

            if(grantResults.length > 0) {
                for (int grantResult : grantResults) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        mLocationPermissionGranted = false;
//                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
//                        alertDialogBuilder.setTitle("Thông báo");
//                        alertDialogBuilder.setMessage("Từ chối truy cập vị trí sẽ không xác định được các địa điểm quanh bạn");
//                        alertDialogBuilder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                nearPlace.setVisibility(View.GONE);
//                                tvGanViTri.setVisibility(View.GONE);
//                                tvAllNearPlace.setVisibility(View.GONE);
//                            }
//                        });
//                        Dialog mDialog = alertDialogBuilder.create();
//                        mDialog.show();
                        getActivity().finish();
                        return;
                    }
                }
                Log.d(TAG, "onRequestPermissionsResult: permission granted.");

                mLocationPermissionGranted = true;
            }
        }
    }
}
