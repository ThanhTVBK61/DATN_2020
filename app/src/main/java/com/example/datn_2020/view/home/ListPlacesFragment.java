package com.example.datn_2020.view.home;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_2020.R;
import com.example.datn_2020.adapter.ItemRecyclerViewClickListener;
import com.example.datn_2020.adapter.home.ListPlacesAdapter;
import com.example.datn_2020.repository.model.CurrentUser;
import com.example.datn_2020.repository.model.FavouritePlaceCheckModel;
import com.example.datn_2020.repository.model.PlaceModel;
import com.example.datn_2020.viewmodel.ContainerVM;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.ArrayList;
import java.util.Objects;

public class ListPlacesFragment extends Fragment implements View.OnClickListener, ItemRecyclerViewClickListener {

    private final String TAG = "ListPlaceFragment";
    private final int ERROR_DIALOG_REQUEST = 9001;

    private Toolbar tbTitleListPlaces;
    private LinearLayout llFindMap;
    private RecyclerView rvListPlaces;
    private String type;
    private FragmentActivity fragmentActivity;
    private Context mContext;

    private ContainerVM listPlaceVM;
    private HomeFragment homeFragment;
    private boolean isGuest;

    private ListPlacesAdapter listPlacesAdapter;
    private ArrayList<PlaceModel> museumPlaceModel = new ArrayList<>();
    private FavouritePlaceCheckModel favouritePlaceCheckModel = new FavouritePlaceCheckModel();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_places, container, false);

        Log.i(TAG, "onCreateView");

        if (getActivity() != null) {
            fragmentActivity = getActivity();
        }

        registerViews(view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            tbTitleListPlaces.setTitle(bundle.getString("title"));
            type = bundle.getString("type");
        } else {
            tbTitleListPlaces.setTitle("Không nhận được title");
        }

        if (CurrentUser.getInstance().id == -1) {
            isGuest = true;
        }
        homeFragment = (HomeFragment) getParentFragment();
        registerToolbar();
        registerRecyclerView();

        listPlaceVM = new ViewModelProvider(fragmentActivity).get(ContainerVM.class);
        listPlaceVM.loadPlaceByType(CurrentUser.getInstance().id, type);
        loadData();

        llFindMap.setOnClickListener(this);

        return view;
    }

    private void loadData() {
        Log.i(TAG, "Call load data");

        listPlaceVM.getListCurrentTypePlace().observe(getViewLifecycleOwner(), new Observer<ArrayList<PlaceModel>>() {
            @Override
            public void onChanged(ArrayList<PlaceModel> placeModels) {
                museumPlaceModel = placeModels;
                Log.i(TAG, "Size place: " + museumPlaceModel.size());
                listPlacesAdapter.setItems(museumPlaceModel);
            }
        });
    }

    // Kiem tra dien thoai cua nguoi dung co ho tro google service
    private boolean isServiceOK() {
        Log.d(TAG, "isServiceOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getActivity());

        if (available == ConnectionResult.SUCCESS) {
            //Nguoi dung co the tai Map
            Log.d(TAG, "isServices: Google Play Services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //Co loi xay ra
            Log.d(TAG, "isServiceOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(getActivity(), available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(getActivity(), "We can make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void registerToolbar() {
        tbTitleListPlaces.setNavigationIcon(R.drawable.ic_back);
        tbTitleListPlaces.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeFragment.backHomeStack();
                Objects.requireNonNull(getActivity()).findViewById(R.id.btnBottomNavigation).setVisibility(View.VISIBLE);
            }
        });
    }

    private void registerRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvListPlaces.setLayoutManager(linearLayoutManager);
        rvListPlaces.setHasFixedSize(true);
        listPlacesAdapter = new ListPlacesAdapter(getActivity(), isGuest, museumPlaceModel, this);
        rvListPlaces.setAdapter(listPlacesAdapter);
    }

    private void registerViews(View view) {
        tbTitleListPlaces = view.findViewById(R.id.tbTitleListPlaces);
        llFindMap = view.findViewById(R.id.llFindMap);
        rvListPlaces = view.findViewById(R.id.rvListPlaces);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llFindMap:
                if (isServiceOK()) {
                    MapsFragment mMapsFragment = new MapsFragment();
                    Bundle mBundle = new Bundle();
                    mBundle.putString("type", type);
                    mMapsFragment.setArguments(mBundle);
                    homeFragment.replaceHomeFragment(mMapsFragment);
                }
                break;
        }
    }

    @Override
    public void onItemClick(PlaceModel placeModel, int position) {
//        listPlaceVM.loadPlaceById(placeModel.getIdPlace());
        PlaceDetail placeDetail = new PlaceDetail();
        Bundle mBundle = new Bundle();
        mBundle.putBoolean("parent", false);
        mBundle.putString("name", "home");
        mBundle.putInt("id", placeModel.getIdPlace());
        placeDetail.setArguments(mBundle);
        homeFragment.replaceHomeFragment(placeDetail);
    }

    @Override
    public void onFavouriteItemClick(PlaceModel placeModel, final boolean checked, final int position) {

        favouritePlaceCheckModel.setIdPlace(placeModel.getIdPlace());
        favouritePlaceCheckModel.setFavourite(checked);
        favouritePlaceCheckModel.setIdUser(CurrentUser.getInstance().id);
        listPlaceVM.updateFavouritePlace(favouritePlaceCheckModel);
        listPlaceVM.getCheckFavouriteResponse().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean result) {
                if (result) {
                    if (checked) {
                        museumPlaceModel.get(position).setFavourite(1);
                    } else {
                        museumPlaceModel.get(position).setFavourite(0);
                    }
                    listPlacesAdapter.notifyDataSetChanged();
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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        Log.i(TAG, "onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();

        Log.i(TAG, "Detach");
    }
}
