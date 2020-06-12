package com.example.datn_2020.view.view_home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.datn_2020.R;
import com.example.datn_2020.adapter.home.PlaceInfo;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    private final String TAG="ListPlacesFragment";
    private final String FINE_LOCATION=Manifest.permission.ACCESS_FINE_LOCATION;
    private final String COURSE_LOCATION=Manifest.permission.ACCESS_COARSE_LOCATION;
    private final int LOCATION_PERMISSION_REQUEST_CODE =1234;
    private final float DEFAULT_ZOOM =15;

    private Boolean mLocationPermissionGranted = false;

    private ArrayList<PlaceInfo> placeInfoArrayList = new ArrayList<>();

    private GoogleMap mMap;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps,container,false);

        placeInfoArrayList.add(new PlaceInfo("Đại học Bách Khoa","Hai Bà Trưng","5",new LatLng(21.004977,105.843785)));
        placeInfoArrayList.add(new PlaceInfo("Đại học Xây Dựng","Hai Bà Trưng","5",new LatLng(21.003485,105.843465)));

        getLocationPermission();
        return view;
    }

    private void getLocationPermission(){
        Log.d(TAG,"getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(getContext(),FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(getContext(),COURSE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                mLocationPermissionGranted = true;
                initMap();
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
                        Log.d(TAG, "onRequestPermissionsResult: permission failed.");
                        HomeFragment homeFragment = (HomeFragment) getParentFragment();
                        homeFragment.backHomeStack();
                        return;
                    }
                }
                Log.d(TAG, "onRequestPermissionsResult: permission granted.");

                mLocationPermissionGranted = true;
                //Khoi tao Map
                initMap();
            }
        }
    }

    private void initMap(){
        Log.d(TAG,"initMap: Initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if(mapFragment == null){
            Log.d(TAG,"mapFragment is null");
        }else {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(getActivity(), "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG,"onMapReady: map is ready");
        mMap = googleMap;
        if(mLocationPermissionGranted){
            getDeviceLocation();
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.getUiSettings().setAllGesturesEnabled(true);
        }
    }

    private void getDeviceLocation(){
        Log.d(TAG,"getDeviceLocation: getting the devices current location");
        FusedLocationProviderClient mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        try {
            if(mLocationPermissionGranted){
                mFusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG,"onComplete: found location");
                            Location currentLocation = task.getResult();
                            moveCamera(new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude()));
                            setMakerPlace();
                        }else{
                            Log.d(TAG,"onComplete: current location is null");
                            Toast.makeText(getActivity(), "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.d(TAG,"getDeviceLocation: SecurityException: "+e.getMessage());
        }
    }

    private void moveCamera(LatLng latLng){
        Log.d(TAG,"moveCamera: moving the camera to: lat: "+ latLng.latitude+", lng: "+ latLng.longitude);
        mMap.clear();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,DEFAULT_ZOOM));
    }

    private void setMakerPlace(){
        mMap.clear();

        if(placeInfoArrayList.size()>0){
            for (PlaceInfo placeInfo : placeInfoArrayList) {
                String snippet = "Address: " + placeInfo.getAddress() + "\n" +
                        "Rating: " + placeInfo.getRating() + "\n";
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(placeInfo.getLatLng())
                        .title(placeInfo.getName())
                        .snippet(snippet);

                mMap.addMarker(markerOptions);
            }
        }
    }
}
