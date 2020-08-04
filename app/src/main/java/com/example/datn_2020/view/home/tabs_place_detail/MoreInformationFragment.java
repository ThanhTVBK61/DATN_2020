package com.example.datn_2020.view.home.tabs_place_detail;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.datn_2020.R;
import com.example.datn_2020.repository.model.PlaceDetailHomeModel;
import com.example.datn_2020.viewmodel.ContainerVM;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

public class MoreInformationFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "MapMoreInformation";
    private GoogleMap mMap;

    private FragmentActivity fragmentActivity;
    private TextView tvPhoneMoreInformation;
    private TextView tvMailMoreInformation;
    private TextView tvAddressMoreInformation;
    private LatLng latLng;
    private Context mContext;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_moreinformation_place_detail,container,false);

        if(getActivity()!=null){
            fragmentActivity = getActivity();
        }

        registerViews(view);
        registerData();

        initMap();

        return view;
    }

    private void registerData() {
        ContainerVM informationPlaceVM = new ViewModelProvider(fragmentActivity).get(ContainerVM.class);
        informationPlaceVM.getCurrentPlaceDetail().observe(getViewLifecycleOwner(), new Observer<PlaceDetailHomeModel>() {
            @Override
            public void onChanged(PlaceDetailHomeModel placeDetailHomeModel) {
                Log.i(TAG,"load data");
                tvPhoneMoreInformation.setText(placeDetailHomeModel.getPhoneNumber());
                tvMailMoreInformation.setText(placeDetailHomeModel.getEmail());
                tvAddressMoreInformation.setText(placeDetailHomeModel.getAddress());

                String[] coordinate =  placeDetailHomeModel.getCoordinatePlace().split(" ");

                latLng = new LatLng(Float.parseFloat(coordinate[0]),Float.parseFloat(coordinate[1]));
            }
        });
    }

    private void registerViews(View view) {
        tvPhoneMoreInformation = view.findViewById(R.id.tvPhoneMoreInformation);
        tvMailMoreInformation = view.findViewById(R.id.tvMailMoreInformation);
        tvAddressMoreInformation = view.findViewById(R.id.tvAddressMoreInformation);
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fMapMoreInformation);
        if (mapFragment == null) {
            Log.i("MoreInformationFragment", "mapFragment is null");
        } else {
            mapFragment.getMapAsync(this);
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        moveCamera(latLng);
        setMakerPlace(latLng);
    }

    private void moveCamera(LatLng latLng) {
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            mContext, R.raw.my_map_style));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
        float DEFAULT_ZOOM = 14;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
    }

    private void setMakerPlace(LatLng mLatLng) {
        MarkerOptions markerOptions = new MarkerOptions()
                .position(mLatLng);

        mMap.addMarker(markerOptions);
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
