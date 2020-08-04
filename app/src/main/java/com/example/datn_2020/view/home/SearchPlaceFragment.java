package com.example.datn_2020.view.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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
import com.example.datn_2020.repository.model.UpdatePlaceInTripModel;
import com.example.datn_2020.view.trip.TripFragment;
import com.example.datn_2020.viewmodel.ContainerVM;

import java.util.ArrayList;


public class SearchPlaceFragment extends Fragment implements ItemRecyclerViewClickListener {

    private final String TAG = "SearchPlaceFragment";

    private ArrayList<PlaceModel> listPlaces = new ArrayList<>();
    private ArrayList<Integer> currentPlace = new ArrayList<>();
    private Boolean isGuest = false;
    private ContainerVM searchVM;
    private Context mContext;
    private FavouritePlaceCheckModel favouritePlaceCheckModel = new FavouritePlaceCheckModel();
    private FragmentActivity fragmentActivity;
    private HomeFragment homeFragment;
    private ListPlacesAdapter listPlacesAdapter;
    private int idTrip;
    private RecyclerView rvListPlaceSearch;
    private String purpose = "";
    private Toolbar tbSearch;
    private TripFragment tripFragment;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_place, container, false);
        setHasOptionsMenu(true);

        Log.i(TAG, "Number current place:" + currentPlace.size());

        Bundle mBundle = getArguments();
        if (mBundle != null) {
            purpose = mBundle.getString("purpose");
        }


        if (purpose != null) {
            if (purpose.equals("add")) {
                tripFragment = (TripFragment) getParentFragment();
                currentPlace = mBundle.getIntegerArrayList("current place");
                Log.i(TAG,"Current Place: "+currentPlace.size());
                idTrip =mBundle.getInt("idtrip");
            } else {
                homeFragment = (HomeFragment) getParentFragment();
            }
        }

        Log.i(TAG, "Number current place:" + currentPlace.size());

        if (getActivity() != null) {
            fragmentActivity = getActivity();
        }
        searchVM = new ViewModelProvider(getActivity()).get(ContainerVM.class);
//        searchVM.loadAllPlaceInfo(CurrentUser.getInstance().id);

        if (CurrentUser.getInstance().id == -1) {
            isGuest = true;
        }
        registerViews(view);
        registerRecyclerView();

        searchVM.getListAllPlaceInfo().observe(getViewLifecycleOwner(), new Observer<ArrayList<PlaceModel>>() {
            @Override
            public void onChanged(ArrayList<PlaceModel> placeModelsResponse) {
                Log.i(TAG,"placeModelsResponse: "+placeModelsResponse.size());
                listPlaces = new ArrayList<>(placeModelsResponse);
//                listPlaces = placeModelsResponse;

                if (purpose.equals("add")) {
                    for (int index = 0; index < listPlaces.size(); index++) {
                        for (int i : currentPlace) {
                            if (listPlaces.get(index).getIdPlace() == i) {
                                listPlaces.remove(index);
                            }
                        }
                    }
                }
                Log.i(TAG,"listPlaces: "+listPlaces.size());
                listPlacesAdapter.setItems(listPlaces);
            }
        });

        ((AppCompatActivity) fragmentActivity).setSupportActionBar(tbSearch);
        registerToolbar();
        return view;
    }


    private void registerRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvListPlaceSearch.setLayoutManager(linearLayoutManager);
        rvListPlaceSearch.setHasFixedSize(true);
        listPlacesAdapter = new ListPlacesAdapter(getActivity(), isGuest, listPlaces, this);
        rvListPlaceSearch.setAdapter(listPlacesAdapter);
    }

    private void registerViews(View view) {
        tbSearch = view.findViewById(R.id.tbSearch);
        rvListPlaceSearch = view.findViewById(R.id.rvListPlaceSearch);
    }

    private void registerToolbar() {
        tbSearch.setNavigationIcon(R.drawable.ic_back);
        tbSearch.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (purpose.equals("show")) {
                    homeFragment.backHomeStack();
                    fragmentActivity.findViewById(R.id.btnBottomNavigation).setVisibility(View.VISIBLE);
                } else if (purpose.equals("add")) {
                    tripFragment.backTripStack();
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_item_search, menu);

        MenuItem searchItem = menu.findItem(R.id.menuItemSearch);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Nhập tên địa điểm ...");
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchText) {
                Log.i(TAG, searchText);
                listPlacesAdapter.getFilter().filter(searchText);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onItemClick(final PlaceModel placeModel, final int position) {
        if (purpose.equals("show")) {
            searchVM.loadPlaceById(placeModel.getIdPlace());
            PlaceDetail placeDetail = new PlaceDetail();

            Bundle mBundle = new Bundle();
            mBundle.putBoolean("parent", true);
            mBundle.putString("name", "home");
            mBundle.putInt("id", placeModel.getIdPlace());

            placeDetail.setArguments(mBundle);
            homeFragment.replaceHomeFragment(placeDetail);
        } else if (purpose.equals("add")) {
            AlertDialog alertDialog = new AlertDialog.Builder(mContext)
                    .setTitle("Thông báo")
                    .setMessage("Bạn muốn thêm địa điểm vào chuyến đi?")
                    .setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            searchVM.addPlaceInTrip(new UpdatePlaceInTripModel(idTrip,placeModel.getIdPlace()));
//                            searchVM.getAddPlaceInTripResponse().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
//                                @Override
//                                public void onChanged(Boolean result) {
//                                    if(result){
//
//                                    }else {
//                                        Toast.makeText(fragmentActivity, "Thêm địa điểm thất bại!", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
                            Log.i(TAG,"listplaces: "+listPlaces.size());
                            Log.i(TAG,"position: "+position);
                            listPlaces.remove(position);
                            listPlacesAdapter.setItems(listPlaces);
                            dialog.cancel();
                        }
                    }).create();
            alertDialog.show();
            Button negative = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
            Button positive = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
            negative.setTextColor(negative.getResources().getColor(R.color.colorGray700));
            positive.setTextColor(negative.getResources().getColor(R.color.colorBlue700));
        }

    }

    @Override
    public void onFavouriteItemClick(PlaceModel placeModel, final boolean isChecked, final int position) {
        favouritePlaceCheckModel.setIdPlace(placeModel.getIdPlace());
        favouritePlaceCheckModel.setFavourite(isChecked);
        favouritePlaceCheckModel.setIdUser(CurrentUser.getInstance().id);
        searchVM.updateFavouritePlace(favouritePlaceCheckModel);

        searchVM.getCheckFavouriteResponse().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean result) {
                if (result) {
                    if (isChecked) {
                        listPlaces.get(position).setFavourite(1);
                    } else {
                        listPlaces.get(position).setFavourite(0);
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

        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mContext = null;
    }
}
