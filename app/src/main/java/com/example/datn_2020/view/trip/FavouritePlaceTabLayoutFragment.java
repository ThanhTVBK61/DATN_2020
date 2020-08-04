package com.example.datn_2020.view.trip;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_2020.R;
import com.example.datn_2020.adapter.trip.ListFavouritePlaceAdapter;
import com.example.datn_2020.repository.model.CurrentUser;
import com.example.datn_2020.repository.model.FavouritePlaceCheckModel;
import com.example.datn_2020.repository.model.PlaceModel;
import com.example.datn_2020.repository.model.TripByIdPlaceModel;
import com.example.datn_2020.repository.model.UpdatePlaceInTripModel;
import com.example.datn_2020.view.home.PlaceDetail;
import com.example.datn_2020.viewmodel.ContainerVM;

import java.util.ArrayList;

public class FavouritePlaceTabLayoutFragment extends Fragment implements ListFavouritePlaceAdapter.ItemFavouritePlaceInTripListener, SelectFavouritePlaceDialogFragment.SelectTrip {

    private final String TAG = "FavouriteTripFragment";
    private RecyclerView rvListFavouritePlace;
    private ListFavouritePlaceAdapter listFavouritePlaceAdapter;
    private ArrayList<PlaceModel> placeResponseArrayList = new ArrayList<>();
    private ContainerVM favouriteTripVM;

    private FragmentActivity fragmentActivity;
    private TripFragment tripFragment;
    private Context mContext;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite_trip_layout,container,false);

        Log.i(TAG,"onCreateView");

        if (getActivity() != null) {
            fragmentActivity = getActivity();
        }

        favouriteTripVM = new ViewModelProvider(fragmentActivity).get(ContainerVM.class);

        MainTripFragment mainTripFragment = (MainTripFragment) getParentFragment();
        tripFragment = (TripFragment) mainTripFragment.getParentFragment();

        rvListFavouritePlace = view.findViewById(R.id.rvListFavouritePlace);

        loadData();

        return view;
    }

    private void loadData() {
        favouriteTripVM.loadAllPlaceFavourite(CurrentUser.getInstance().id);
        favouriteTripVM.getListPlaceFavourite().observe(getViewLifecycleOwner(), new Observer<ArrayList<PlaceModel>>() {
            @Override
            public void onChanged(ArrayList<PlaceModel> placeModels) {
                placeResponseArrayList = placeModels;
                registerRecyclerview();
            }
        });
    }

    private void registerRecyclerview() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvListFavouritePlace.setLayoutManager(linearLayoutManager);
        rvListFavouritePlace.setHasFixedSize(true);
        listFavouritePlaceAdapter = new ListFavouritePlaceAdapter(getActivity(), placeResponseArrayList, this);
        rvListFavouritePlace.setAdapter(listFavouritePlaceAdapter);
    }

    @Override
    public void onItemClick(PlaceModel placeModel) {
        favouriteTripVM.loadPlaceById(placeModel.getIdPlace());
        PlaceDetail placeDetail = new PlaceDetail();
        Bundle mBundle = new Bundle();
        mBundle.putBoolean("parent", true);
        mBundle.putString("name", "com/example/datn_2020/viewmodel/trip");
        placeDetail.setArguments(mBundle);
        tripFragment.replaceTripFragment(placeDetail);
        fragmentActivity.findViewById(R.id.btnBottomNavigation).setVisibility(View.GONE);
    }

    @Override
    public void onFavouriteItemClick(final PlaceModel placeModel, final int position) {
        //Tạo đối tượng
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn chắc chắn xóa địa điểm này khỏi danh sách ưa thích!!");
        //Nút Cancel
        builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FavouritePlaceCheckModel favouritePlaceCheckModel = new FavouritePlaceCheckModel();
                favouritePlaceCheckModel.setIdPlace(placeModel.getIdPlace());
                favouritePlaceCheckModel.setFavourite(false);
                favouritePlaceCheckModel.setIdUser(CurrentUser.getInstance().id);
                favouriteTripVM.updateFavouritePlace(favouritePlaceCheckModel);
                placeResponseArrayList.remove(position);
                listFavouritePlaceAdapter.notifyItemRemoved(position);
                favouriteTripVM.setChangeMainFavourite(true);
            }
        });
        //Tạo dialog
        AlertDialog alertDialog = builder.create();
        //Hiển thị
        alertDialog.show();
        Button negative = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        Button positive = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        negative.setTextColor(negative.getResources().getColor(R.color.colorBlue700));
        positive.setTextColor(positive.getResources().getColor(R.color.colorBlue700));

    }

    @Override
    public void onAddToTrip(PlaceModel placeModel) {
        SelectFavouritePlaceDialogFragment selectFavouritePlaceDialogFragment = new SelectFavouritePlaceDialogFragment();
        selectFavouritePlaceDialogFragment.setSelectFavouritePlaceDialogFragment(placeModel.getIdPlace(),this);
        selectFavouritePlaceDialogFragment.show(getChildFragmentManager(), null);
    }


    @Override
    public void selectTrip(TripByIdPlaceModel tripByIdPlaceModel,int idPlace) {
        favouriteTripVM.addPlaceInTrip(new UpdatePlaceInTripModel(tripByIdPlaceModel.getIdTrip(),idPlace));
    }

    @Override
    public void onResume() {
        super.onResume();
        favouriteTripVM.getActivateFragment().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == 1) {
                    Log.i(TAG, "onResume === " + favouriteTripVM.isChangeTripFavourite());
                    if (favouriteTripVM.isChangeTripFavourite()) {
                        favouriteTripVM.loadAllPlaceFavourite(CurrentUser.getInstance().id);
                        favouriteTripVM.getListPlaceFavourite().observe(getViewLifecycleOwner(), new Observer<ArrayList<PlaceModel>>() {
                            @Override
                            public void onChanged(ArrayList<PlaceModel> placeModels) {
                                placeResponseArrayList = placeModels;
                                listFavouritePlaceAdapter.notifyDataSetChanged();
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
        Log.i(TAG,"onAttach");
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }
}
