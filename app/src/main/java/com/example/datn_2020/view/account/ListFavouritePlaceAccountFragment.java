package com.example.datn_2020.view.account;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.example.datn_2020.adapter.account.ListFavouritePlaceAccountAdapter;
import com.example.datn_2020.repository.model.CurrentUser;
import com.example.datn_2020.repository.model.FavouritePlaceCheckModel;
import com.example.datn_2020.repository.model.PlaceModel;
import com.example.datn_2020.view.home.PlaceDetail;
import com.example.datn_2020.viewmodel.ContainerVM;
import com.example.datn_2020.viewmodel.account.ListPlacesFavouriteAccountVM;

import java.util.ArrayList;
import java.util.Objects;

public class ListFavouritePlaceAccountFragment extends Fragment implements ListFavouritePlaceAccountAdapter.ItemViewClickListener {

    private Toolbar tbTitleListPlacesAccount;
    private RecyclerView rvListPlacesAccount;

    private ArrayList<PlaceModel> mFavouritePlace = new ArrayList<>();

    private ListPlacesFavouriteAccountVM accountVM;
    private FragmentActivity fragmentActivity;
    private AccountFragment accountFragment;
    private ListFavouritePlaceAccountAdapter listFavouritePlaceAccountAdapter;

    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_favourite_place_account,container,false);

        accountFragment = (AccountFragment) getParentFragment();

        if(getActivity()!=null){
            fragmentActivity = getActivity();
        }

        accountVM = new ViewModelProvider(this).get(ListPlacesFavouriteAccountVM.class);


        tbTitleListPlacesAccount = view.findViewById(R.id.tbTitleListPlacesAccount);
        rvListPlacesAccount = view.findViewById(R.id.rvListPlacesAccount);

        registerToolbar();
        loadData();

        return view;
    }

    private void loadData() {
        accountVM.loadAllPlaceFavourite(CurrentUser.getInstance().id);
        accountVM.getListPlaceFavourite().observe(getViewLifecycleOwner(), new Observer<ArrayList<PlaceModel>>() {
            @Override
            public void onChanged(ArrayList<PlaceModel> placeModels) {
                mFavouritePlace = placeModels;
                registerRecyclerview();
            }
        });
    }

    private void registerRecyclerview() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvListPlacesAccount.setLayoutManager(linearLayoutManager);
        rvListPlacesAccount.setHasFixedSize(true);
        listFavouritePlaceAccountAdapter = new ListFavouritePlaceAccountAdapter(getActivity(), mFavouritePlace, this);
        rvListPlacesAccount.setAdapter(listFavouritePlaceAccountAdapter);
    }

    private void registerToolbar() {
        tbTitleListPlacesAccount.setNavigationIcon(R.drawable.ic_back);
        tbTitleListPlacesAccount.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountFragment.backStack();
                Objects.requireNonNull(getActivity()).findViewById(R.id.btnBottomNavigation).setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onItemClick(PlaceModel placeModel) {
        accountVM.loadPlaceById(placeModel.getIdPlace());
        PlaceDetail placeDetail = new PlaceDetail();
        Bundle mBundle = new Bundle();
        mBundle.putBoolean("parent", false);
        mBundle.putString("name", "com/example/datn_2020/viewmodel/account");
        mBundle.putInt("id",placeModel.getIdPlace());
        placeDetail.setArguments(mBundle);
        accountFragment.replaceFragment(placeDetail);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onFavouriteItemClick(final PlaceModel placeModel, final int position) {

        Log.i("FavouritePlaceAccount",position+"");

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
                accountVM.updateFavouritePlace(favouritePlaceCheckModel);
                mFavouritePlace.remove(position);
                listFavouritePlaceAccountAdapter.notifyItemRemoved(position);
            }
        });
        //Tạo dialog
        AlertDialog alertDialog = builder.create();
        //Hiển thị
        alertDialog.show();
        alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorBlue700));
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorBlue700));

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
