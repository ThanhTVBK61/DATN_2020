package com.example.datn_2020.view.home;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.datn_2020.R;
import com.example.datn_2020.adapter.ImageSliderAdapter;
import com.example.datn_2020.adapter.ItemRecyclerViewClickListener;
import com.example.datn_2020.adapter.home.PlaceHomeAdapter;
import com.example.datn_2020.adapter.home.StartSnapHelper;
import com.example.datn_2020.repository.model.CurrentUser;
import com.example.datn_2020.repository.model.FavouritePlaceCheckModel;
import com.example.datn_2020.repository.model.PlaceModel;
import com.example.datn_2020.view.home.tabs_place_detail.OtherTypeBottomSheetDialog;
import com.example.datn_2020.view.start.StartActivity;
import com.example.datn_2020.viewmodel.ContainerVM;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.util.ArrayList;


public class MainHomeFragment extends Fragment implements View.OnClickListener, PlaceHomeAdapter.ItemHomeClickListener {

    private final String TAG = "MainHomeFragment";
    private final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private Boolean mLocationPermissionGranted = false;

    private ArrayList<PlaceModel> places = new ArrayList<>();
    private ArrayList<PlaceModel> museums = new ArrayList<>();

    private boolean isGuest;
    private Context mContext;
    private RecyclerView bestPlace;
    private RecyclerView nearPlace;
    private TextView tvSeeAllMuseum;
    private RecyclerView rvMuseum;
    private RecyclerView bestHotel;
    private LinearLayout llMoreImage;
    private ArrayList<PlaceModel> placeModels = new ArrayList<>();
    private WormDotsIndicator wormDotsIndicator;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private LinearLayout llHotelPlaces, llMuseumPlaces, llCinemaPlaces, llParkPlaces;
    private LinearLayout llUniversity, llRestaurantPlaces, llBankPlaces, llOtherPlaces;
    private ImageView ivMapHome;
    private ImageButton ibSearch;
    private SnapHelper snapHelper;

    private ContainerVM containerVM;

    private HomeFragment homeFragment;
    private FragmentActivity fragmentActivity;

    private FavouritePlaceCheckModel favouritePlaceCheckModel = new FavouritePlaceCheckModel();
    private PlaceHomeAdapter mPlaceHomeAdapter;
    private PlaceHomeAdapter museumAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_main_home, container, false);

        Log.i(TAG,"onCreateView");
        if (getActivity() != null) {
            fragmentActivity = getActivity();
        }

        homeFragment = (HomeFragment) getParentFragment();
        if(CurrentUser.getInstance().id == -1){
            isGuest = true;
        }

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
        //Noi dung cua Collapsing
        registerViews(view);
        registerViewPager(urls);
        registerCollapsingToolbar(view);

        //Khởi tạo danh sách địa điểm
        registerMuseumRecyclerView();

        containerVM = new ViewModelProvider(getActivity()).get(ContainerVM.class);
        containerVM.loadAllPlaceInfo(CurrentUser.getInstance().id);
        containerVM.getListAllPlaceInfo().observe(getViewLifecycleOwner(), new Observer<ArrayList<PlaceModel>>() {
            @Override
            public void onChanged(ArrayList<PlaceModel> placeModelsResponse) {
                places = placeModelsResponse;
                for (PlaceModel placeModel : placeModelsResponse) {
                    if (placeModel.getType().equals("Bảo tàng")) {
                        museums.add(placeModel);
                        if (museums.size() > 10)
                            break;
                    }
                }
                museumAdapter.setItems(museums);
            }
        });




        registerDataTest();
        initRecyclerView(nearPlace, placeModels);
        initRecyclerView(bestPlace, placeModels);
        initRecyclerView(bestHotel, placeModels);

        llMoreImage.setOnClickListener(this);
        llHotelPlaces.setOnClickListener(this);
        llMuseumPlaces.setOnClickListener(this);
        llOtherPlaces.setOnClickListener(this);
        ivMapHome.setOnClickListener(this);
        tvSeeAllMuseum.setOnClickListener(this);
        ibSearch.setOnClickListener(this);

        return view;
    }

    private void registerDataTest() {
        placeModels.add(new PlaceModel(1, "Vincom Phạm Ngọc Thạch", "Chùa Bộc Đống Đa Hà Nội", "https://images.foody.vn/res/g30/292738/prof/s576x330/foody-mobile-432-jpg-242-636143691704285196.jpg", "Vui chơi","21.006185 105.832004", "5", "4", "5", "5", 10352,0));
        placeModels.add(new PlaceModel(1, "Vincom Phạm Ngọc Thạch", "Chùa Bộc Đống Đa Hà Nội", "https://images.foody.vn/res/g30/292738/prof/s576x330/foody-mobile-432-jpg-242-636143691704285196.jpg", "Vui chơi","21.006185 105.832004", "5", "4", "5", "5", 10352,1));
        placeModels.add(new PlaceModel(1, "Vincom Phạm Ngọc Thạch", "Chùa Bộc Đống Đa Hà Nội", "https://images.foody.vn/res/g30/292738/prof/s576x330/foody-mobile-432-jpg-242-636143691704285196.jpg", "Vui chơi","21.006185 105.832004", "5", "4", "5", "5", 10352,1));
        placeModels.add(new PlaceModel(1, "Vincom Phạm Ngọc Thạch", "Chùa Bộc Đống Đa Hà Nội", "https://images.foody.vn/res/g30/292738/prof/s576x330/foody-mobile-432-jpg-242-636143691704285196.jpg", "Vui chơi","21.006185 105.832004", "5", "4", "5", "5", 10352,0));
        placeModels.add(new PlaceModel(1, "Vincom Phạm Ngọc Thạch", "Chùa Bộc Đống Đa Hà Nội", "https://images.foody.vn/res/g30/292738/prof/s576x330/foody-mobile-432-jpg-242-636143691704285196.jpg", "Vui chơi","21.006185 105.832004", "5", "4", "5", "5", 10352,1));
        placeModels.add(new PlaceModel(1, "Vincom Phạm Ngọc Thạch", "Chùa Bộc Đống Đa Hà Nội", "https://images.foody.vn/res/g30/292738/prof/s576x330/foody-mobile-432-jpg-242-636143691704285196.jpg", "Vui chơi","21.006185 105.832004", "5", "4", "5", "5", 10352,1));
        placeModels.add(new PlaceModel(1, "Vincom Phạm Ngọc Thạch", "Chùa Bộc Đống Đa Hà Nội", "https://images.foody.vn/res/g30/292738/prof/s576x330/foody-mobile-432-jpg-242-636143691704285196.jpg", "Vui chơi","21.006185 105.832004", "5", "4", "5", "5", 10352,0));
        placeModels.add(new PlaceModel(1, "Vincom Phạm Ngọc Thạch", "Chùa Bộc Đống Đa Hà Nội", "https://images.foody.vn/res/g30/292738/prof/s576x330/foody-mobile-432-jpg-242-636143691704285196.jpg", "Vui chơi","21.006185 105.832004", "5", "4", "5", "5", 10352,0));
        placeModels.add(new PlaceModel(1, "Vincom Phạm Ngọc Thạch", "Chùa Bộc Đống Đa Hà Nội", "https://images.foody.vn/res/g30/292738/prof/s576x330/foody-mobile-432-jpg-242-636143691704285196.jpg", "Vui chơi","21.006185 105.832004", "5", "4", "5", "5", 10352,1));
        placeModels.add(new PlaceModel(1, "Vincom Phạm Ngọc Thạch", "Chùa Bộc Đống Đa Hà Nội", "https://images.foody.vn/res/g30/292738/prof/s576x330/foody-mobile-432-jpg-242-636143691704285196.jpg", "Vui chơi","21.006185 105.832004", "5", "4", "5", "5", 10352,1));
    }

    private void registerViewPager(String[] urls) {
        viewPager.setAdapter(new ImageSliderAdapter(urls));
        wormDotsIndicator.setViewPager(viewPager);
    }

    private void registerViews(View view) {
        bestPlace = view.findViewById(R.id.rvrBestPlace);
        nearPlace = view.findViewById(R.id.rvrNearPlace);
        tvSeeAllMuseum = view.findViewById(R.id.tvSeeAllMuseum);
        rvMuseum = view.findViewById(R.id.rvMuseum);
        bestHotel = view.findViewById(R.id.rvrBestHotel);
        llMoreImage = view.findViewById(R.id.llMoreImageHome);
        wormDotsIndicator = view.findViewById(R.id.home_worm_dots_indicator);
        viewPager = view.findViewById(R.id.homeViewPager);
        ibSearch = view.findViewById(R.id.ibSearch);
        //Linear layout type place
        llHotelPlaces = view.findViewById(R.id.llHotelPlaces);
        llMuseumPlaces = view.findViewById(R.id.llMuseumPlaces);
        llCinemaPlaces = view.findViewById(R.id.llCinemaPlaces);
        llParkPlaces = view.findViewById(R.id.llParkPlaces);
        llUniversity = view.findViewById(R.id.llUniversity);
        llRestaurantPlaces = view.findViewById(R.id.llRestaurantPlaces);
        llBankPlaces = view.findViewById(R.id.llBankPlaces);
        llOtherPlaces = view.findViewById(R.id.llOtherPlaces);
        ivMapHome = view.findViewById(R.id.ivMapHome);

    }

    private void registerCollapsingToolbar(View view) {
        CollapsingToolbarLayout collapsingToolbarLayout = view.findViewById(R.id.homeCollapsingToolbar);
        ((AppCompatActivity) fragmentActivity).setSupportActionBar((Toolbar) view.findViewById(R.id.homeToolbar));
        collapsingToolbarLayout.setTitle("Hà nội  ");
        collapsingToolbarLayout.setCollapsedTitleTextColor(collapsingToolbarLayout.getResources().getColor(R.color.colorBlack));
        collapsingToolbarLayout.setExpandedTitleColor(Color.parseColor("#00FFFFFF"));
    }

    private void registerMuseumRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvMuseum.setLayoutManager(linearLayoutManager);
        rvMuseum.setHasFixedSize(true);
        SnapHelper snapHelper = new StartSnapHelper();
        snapHelper.attachToRecyclerView(rvMuseum);
        museumAdapter = new PlaceHomeAdapter(getActivity(),isGuest, museums,"museums", this);
        rvMuseum.setAdapter(museumAdapter);
    }

    private void initRecyclerView(RecyclerView mRecyclerView, ArrayList<PlaceModel> mPlaceModels) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        SnapHelper snapHelper = new StartSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);
        mPlaceHomeAdapter = new PlaceHomeAdapter(getActivity(),isGuest, mPlaceModels,"other", this);
        mRecyclerView.setAdapter(mPlaceHomeAdapter);
    }

//    @Override
//    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_item_search, menu);
//        return true;
//    }

//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.menu_item_search, menu);
//    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.llMoreImageHome:
                if (homeFragment == null) {
                    Log.i("mainHomeFragment", "null");
                } else {
                    homeFragment.replaceHomeFragment(new PlaceDetail());
                    fragmentActivity.findViewById(R.id.btnBottomNavigation).setVisibility(View.GONE);
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
                    fragmentActivity.findViewById(R.id.btnBottomNavigation).setVisibility(View.GONE);
                }
                break;
            case R.id.tvSeeAllMuseum:
            case R.id.llMuseumPlaces:
                if (homeFragment == null) {
                    Log.i("mainHomeFragment", "null");
                } else {
                    ListPlacesFragment listPlacesFragment = new ListPlacesFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("title", "Danh sách bảo tàng");
                    bundle.putString("type", "Bảo tàng");
                    listPlacesFragment.setArguments(bundle);
                    homeFragment.replaceHomeFragment(listPlacesFragment);
                    fragmentActivity.findViewById(R.id.btnBottomNavigation).setVisibility(View.GONE);
                }
                break;
            case R.id.llOtherPlaces:
                OtherTypeBottomSheetDialog otherTypeBottomSheetDialog = new OtherTypeBottomSheetDialog();
                otherTypeBottomSheetDialog.show(getChildFragmentManager(), "othertype");
                break;

            case R.id.ivMapHome:
                homeFragment.replaceHomeFragment(new AllPlaceMapFragment());
                fragmentActivity.findViewById(R.id.btnBottomNavigation).setVisibility(View.GONE);
                break;

            case R.id.ibSearch:
                SearchPlaceFragment mSearchPlaceFragment= new SearchPlaceFragment();
                Bundle bundle = new Bundle();
                bundle.putString("purpose","show");
                mSearchPlaceFragment.setArguments(bundle);
                homeFragment.replaceHomeFragment(mSearchPlaceFragment);
                fragmentActivity.findViewById(R.id.btnBottomNavigation).setVisibility(View.GONE);
                break;
        }
    }

    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(mContext, FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(mContext, COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
            } else {
                ActivityCompat.requestPermissions(fragmentActivity, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(fragmentActivity, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionGranted = false;
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {

            if (grantResults.length > 0) {
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
                        if (getActivity() != null) {
                            getActivity().finish();
                        }
                        return;
                    }
                }
                Log.d(TAG, "onRequestPermissionsResult: permission granted.");

                mLocationPermissionGranted = true;
            }
        }
    }

    @Override
    public void onItemClick(PlaceModel placeModel) {
        containerVM.loadPlaceById(placeModel.getIdPlace());
        PlaceDetail placeDetail = new PlaceDetail();
        Bundle mBundle = new Bundle();
        mBundle.putBoolean("parent", true);
        mBundle.putString("name", "home");
        mBundle.putInt("id", placeModel.getIdPlace());
        placeDetail.setArguments(mBundle);
        homeFragment.replaceHomeFragment(placeDetail);
        fragmentActivity.findViewById(R.id.btnBottomNavigation).setVisibility(View.GONE);

    }

    @Override
    public void onFavouriteItemClick(PlaceModel placeModel, final boolean isChecked, final int position, final String type) {

        favouritePlaceCheckModel.setIdPlace(placeModel.getIdPlace());
        favouritePlaceCheckModel.setFavourite(isChecked);
        favouritePlaceCheckModel.setIdUser(CurrentUser.getInstance().id);
        containerVM.updateFavouritePlace(favouritePlaceCheckModel);
        containerVM.setChangeTripFavourite(true);

        containerVM.getCheckFavouriteResponse().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean result) {
                if (result) {
                    if (isChecked) {
                        if(type.equals("museums")){
                            museums.get(position).setFavourite(1);
                            museumAdapter.notifyDataSetChanged();
                        }

                    } else {
                        if(type.equals("museums")){
                            museums.get(position).setFavourite(0);
                            museumAdapter.notifyDataSetChanged();
                        }

                    }

                } else {
                    AlertDialog mAlertDialog = new AlertDialog.Builder(mContext)
                            .setTitle("Thông báo")
                            .setMessage("Không thành công!")
                            .setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).create();
                    mAlertDialog.show();
                    Button negative = mAlertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                    negative.setTextColor(negative.getResources().getColor(R.color.colorBlue700));
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        containerVM.getActivateFragment().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer == 0){
                    Log.i(TAG,"onResume === "+ containerVM.isChangeMainFavourite());
                    if(containerVM.isChangeMainFavourite()){
                        containerVM.loadAllPlaceInfo(CurrentUser.getInstance().id);
                        containerVM.getListAllPlaceInfo().observe(getViewLifecycleOwner(), new Observer<ArrayList<PlaceModel>>() {
                            @Override
                            public void onChanged(ArrayList<PlaceModel> placeModelsResponse) {
                                ArrayList<PlaceModel> museums = new ArrayList<>();
                                places = placeModelsResponse;
                                for (PlaceModel placeModel : placeModelsResponse) {
                                    if (placeModel.getType().equals("Bảo tàng")) {
                                        museums.add(placeModel);
                                        if (museums.size() > 10)
                                            break;
                                    }
                                }
//                                initRecyclerView(rvMuseum, museums);
                                mPlaceHomeAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mContext = null;
    }
}
