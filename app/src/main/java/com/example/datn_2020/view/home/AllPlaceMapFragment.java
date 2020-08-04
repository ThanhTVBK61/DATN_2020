package com.example.datn_2020.view.home;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.datn_2020.R;
import com.example.datn_2020.repository.model.PlaceModel;
import com.example.datn_2020.viewmodel.ContainerVM;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.joooonho.SelectableRoundedImageView;

import java.util.ArrayList;
import java.util.Objects;

public class AllPlaceMapFragment extends Fragment implements View.OnClickListener, OnMapReadyCallback {

    private String TAG = "AllPlaceMap";
    private final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;

    private Boolean mLocationPermissionGranted = false;
    private ArrayList<PlaceModel> listPlace = new ArrayList<>();

    private Toolbar tbAllPlaceMap;
    private FloatingActionButton fabNavigationAllPlaces;
    private SelectableRoundedImageView imgAllPlaces;
    private TextView tvNameAllPlaces;
    private CheckBox cbFavouriteAllPlaces;
    private TextView tvAddressAllPlaces;
    private RatingBar ratingMarkAllPlaces;
    private TextView tvNumberRatingAllPlaces;
    private GoogleMap mMap;
    private String currentCoordinate;
    private Context mContext;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_place_map, container, false);

        ContainerVM listPlaceVM = new ViewModelProvider(getActivity()).get(ContainerVM.class);
        listPlaceVM.getListAllPlaceInfo().observe(getViewLifecycleOwner(), new Observer<ArrayList<PlaceModel>>() {
            @Override
            public void onChanged(ArrayList<PlaceModel> placeModels) {
                listPlace = placeModels;
                Log.i("OOOOOOOOOOOOOOOOOOO", "getlistall" + listPlace.size());
                String[] coordinate = listPlace.get(0).getCoordinatePlace().split(" ");
                currentCoordinate = coordinate[0] + "," + coordinate[1] + "(" + listPlace.get(0).getNamePlace() + ")";

            }
        });

        registerViews(view);
        registerToolbar();
        getLocationPermission();

        fabNavigationAllPlaces.setOnClickListener(this);
        return view;
    }

    private void registerViews(View view) {
        tbAllPlaceMap = view.findViewById(R.id.tbAllPlaceMap);
        fabNavigationAllPlaces = view.findViewById(R.id.fabNavigationAllPlaces);
        imgAllPlaces = view.findViewById(R.id.imgAllPlaces);
        tvNameAllPlaces = view.findViewById(R.id.tvNameAllPlaces);
        cbFavouriteAllPlaces = view.findViewById(R.id.cbFavouriteAllPlaces);
        tvAddressAllPlaces = view.findViewById(R.id.tvAddressAllPlaces);
        ratingMarkAllPlaces = view.findViewById(R.id.ratingMarkAllPlaces);
        tvNumberRatingAllPlaces = view.findViewById(R.id.tvNumberRatingAllPlaces);
    }

    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(getContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(getContext(), COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
            } else {
                mLocationPermissionGranted = false;
            }
        } else {
            mLocationPermissionGranted = false;
        }
        initMap();
    }

    private void initMap() {
        Log.d(TAG, "initMap: Initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.allPlaceMap);
        if (mapFragment == null) {
            Log.d(TAG, "mapFragment is null");
        } else {
            mapFragment.getMapAsync(this);
        }
    }

    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting the devices current location");
        FusedLocationProviderClient mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        try {
            if (mLocationPermissionGranted) {
                mFusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: found location");
                            Location currentLocation = task.getResult();
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()));
                            setMakerPlace();
                        } else {
                            Log.d(TAG, "onComplete: current location is null");
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.d(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setPlaceInfo(listPlace.get(0));
        if (mLocationPermissionGranted) {
            getDeviceLocation();
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.getUiSettings().setAllGesturesEnabled(true);
        } else {
            String[] coordinate = listPlace.get(0).getCoordinatePlace().split(" ");

            moveCamera(new LatLng(Float.parseFloat(coordinate[0]), Float.parseFloat(coordinate[1])));
            setMakerPlace();
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String namePlace = marker.getTitle();
                Log.i("NamePlace", namePlace);
                for (int pos = 0; pos < listPlace.size(); pos++) {
                    if (listPlace.get(pos).getNamePlace().equals(namePlace)) {

                        setPlaceInfo(listPlace.get(pos));

                        String[] coordinate = listPlace.get(pos).getCoordinatePlace().split(" ");
                        currentCoordinate = coordinate[0] + "," + coordinate[1] + "(" + listPlace.get(pos).getNamePlace() + ")";
                        break;
                    }
                }
                return false;
            }
        });
    }

    private void setPlaceInfo(final PlaceModel item_place) {
        tvNameAllPlaces.setText(item_place.getNamePlace());

//        đánh giá
        String numberRatingReview = item_place.getSumRating() + " đánh giá";
        tvNumberRatingAllPlaces.setText(numberRatingReview);

        tvAddressAllPlaces.setText(item_place.getAddress());
        String[] urls = item_place.getSrcImage().split(" ");
        Glide.with(getContext())
                .asBitmap()
                .load(urls[0])
                .into(imgAllPlaces);

        //Có phải là địa điểm thích
        if (item_place.getFavourite() == 1) {
            cbFavouriteAllPlaces.setChecked(true);
        } else {
            cbFavouriteAllPlaces.setChecked(false);
        }

        cbFavouriteAllPlaces.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    item_place.setFavourite(1);
                    Log.i("Check", "true");
                } else {
                    item_place.setFavourite(0);
                    Log.i("Check", "false");
                }
            }
        });

//        Rating

        double rating = (item_place.getLocation() + item_place.getPrice() + item_place.getService() + item_place.getQuality()) / 4.0;
        ratingMarkAllPlaces.setRating((float) rating);
    }

    private void moveCamera(LatLng latLng) {
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude);
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getContext(), R.raw.my_map_style));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
    }

    private void setMakerPlace() {
        Log.i(TAG, "Size:" + listPlace.size());
        if (listPlace.size() > 0) {

            for (PlaceModel placeInfo : listPlace) {
                String[] coordinate = placeInfo.getCoordinatePlace().split(" ");
                float lat = Float.parseFloat(coordinate[0]);
                float lng = Float.parseFloat(coordinate[1]);
                LatLng mLatLng = new LatLng(lat, lng);

                MarkerOptions markerOptions = new MarkerOptions()
                        .position(mLatLng)
                        .title(placeInfo.getNamePlace());
                mMap.addMarker(markerOptions);
            }
        }
    }

    private void registerToolbar() {
        Bundle bundle = getArguments();
        String typePlace;
        if (bundle != null) {
            typePlace = bundle.getString("type");
        } else {
            typePlace = "Error";
        }
        tbAllPlaceMap.setTitle("Tất cả địa điểm vui chơi");
        tbAllPlaceMap.setNavigationIcon(R.drawable.ic_back);
        tbAllPlaceMap.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment homeFragment = (HomeFragment) getParentFragment();
                homeFragment.backHomeStack();
                Objects.requireNonNull(getActivity()).findViewById(R.id.btnBottomNavigation).setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabNavigationAllPlaces:
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + currentCoordinate);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                    alertDialogBuilder.setTitle("Thông báo");
                    alertDialogBuilder.setMessage("Điện thoại không hỗ trợ tìm đường");
                    alertDialogBuilder.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorBlue700));
                }
                break;
        }
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
