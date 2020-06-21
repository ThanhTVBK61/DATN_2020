package com.example.datn_2020.view.home;

import android.Manifest;
import android.app.AlertDialog;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.example.datn_2020.R;
import com.example.datn_2020.adapter.home.ListPlacesAdapter;
import com.example.datn_2020.adapter.home.StartSnapHelper;
import com.example.datn_2020.repository.model.ListPlaceModel;
import com.example.datn_2020.viewmodel.home.ListPlaceVM;
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

import java.util.ArrayList;

public class MapsFragment extends Fragment implements OnMapReadyCallback, ListPlacesAdapter.ItemPlaceMapClickListener, View.OnClickListener {

    private final String TAG = "ListPlacesFragment";
    private final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private final float DEFAULT_ZOOM = 13;

    private Boolean mLocationPermissionGranted = false;

    private String currentCoordinate;

    private ListPlaceVM listPlaceVM;
    private ArrayList<ListPlaceModel> listPlace = new ArrayList<>();
    private ListPlacesAdapter listPlacesAdapter;

    private RecyclerView rvListPlacesMap;
    private Toolbar tbListPlaceMap;
    private GoogleMap mMap;
    private String typePlace;
    private FloatingActionButton fabNavigation;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);

        registerViews(view);
        registerToolbar();

        listPlaceVM = new ViewModelProvider(getActivity()).get(ListPlaceVM.class);
        listPlaceVM.getListPlaceModelMutableLiveData().observe(getActivity(), new Observer<ArrayList<ListPlaceModel>>() {
            @Override
            public void onChanged(ArrayList<ListPlaceModel> listPlaceModels) {
                listPlace = listPlaceModels;
                String[] coordinate = listPlace.get(0).getCoordinatePlace().split(" ");
                currentCoordinate = coordinate[0]+","+coordinate[1]+"("+listPlace.get(0).getNamePlace()+")";
            }
        });

        getLocationPermission();
        registerRecyclerView();
        fabNavigation.setOnClickListener(this);

        return view;
    }

    private void registerToolbar() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            typePlace = bundle.getString("type");
        } else {
            typePlace = "Error";
        }
        tbListPlaceMap.setTitle(typePlace);
        tbListPlaceMap.setNavigationIcon(R.drawable.ic_back);
        tbListPlaceMap.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment homeFragment = (HomeFragment) getParentFragment();
                homeFragment.backHomeStack();
            }
        });
    }

    private void registerRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvListPlacesMap.setLayoutManager(linearLayoutManager);
        SnapHelper snapHelper = new StartSnapHelper();
        snapHelper.attachToRecyclerView(rvListPlacesMap);
        listPlacesAdapter = new ListPlacesAdapter(getActivity(), listPlace,this);
        rvListPlacesMap.setAdapter(listPlacesAdapter);

    }

    private void registerViews(View view) {
        rvListPlacesMap = view.findViewById(R.id.rvListPlacesMap);
        tbListPlaceMap = view.findViewById(R.id.tbListPlaceMap);
        fabNavigation = view.findViewById(R.id.fabNavigation);
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
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment == null) {
            Log.d(TAG, "mapFragment is null");
        } else {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(getActivity(), "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;
        if (mLocationPermissionGranted) {
            getDeviceLocation();
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.getUiSettings().setAllGesturesEnabled(true);
        } else {
            moveCamera(new LatLng(21.004977, 105.843785));
            setMakerPlace();
        }
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String namePlace = marker.getTitle();
                Log.i("NamePlace",namePlace);
                for (int pos = 0; pos < listPlace.size(); pos++) {
                    if(listPlace.get(pos).getNamePlace().equals(namePlace)){
                        rvListPlacesMap.smoothScrollToPosition(pos);
                        String[] coordinate = listPlace.get(pos).getCoordinatePlace().split(" ");
                        currentCoordinate = coordinate[0]+","+coordinate[1]+"("+listPlace.get(pos).getNamePlace()+")";
                        break;
                    }
                }
                return false;
            }
        });
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
                            Toast.makeText(getActivity(), "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.d(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }
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
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
    }

    private void setMakerPlace() {

        if (listPlace.size() > 0) {
            for (ListPlaceModel placeInfo : listPlace) {
                String[] coordinate = placeInfo.getCoordinatePlace().split(" ");
                float lat = Float.parseFloat(coordinate[0]);
                float lng = Float.parseFloat(coordinate[1]);
                LatLng mLatLng = new LatLng(lat, lng);

                MarkerOptions markerOptions = new MarkerOptions()
                        .position(mLatLng)
                        .title(placeInfo.getNamePlace());
//                if (typePlace.equals("Bảo tàng"))
//                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_museum));
                mMap.addMarker(markerOptions);
            }
        }
    }

    @Override
    public void onItemClick(ListPlaceModel listPlaceModel) {
        HomeFragment homeFragment = (HomeFragment) getParentFragment();
        homeFragment.replaceHomeFragment(new PlaceDetail());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fabNavigation:
//                Uri gmmIntentUri = Uri.parse("google.navigation:q="+currentCoordinate);
//                Uri gmmIntentUri = Uri.parse("geo:0,0?q=1,Đại Cồ Việt, Hai Bà Trưng, Hà Nội");
                Uri gmmIntentUri = Uri.parse("geo:0,0?q="+currentCoordinate);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(mapIntent);
                }else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
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
}
