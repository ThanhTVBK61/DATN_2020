package com.example.datn_2020.view.trip;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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
import com.example.datn_2020.adapter.trip.ListTripAdapter;
import com.example.datn_2020.repository.model.CurrentUser;
import com.example.datn_2020.repository.model.DeleteTripModel;
import com.example.datn_2020.repository.model.TripModel;
import com.example.datn_2020.view.start.StartActivity;
import com.example.datn_2020.view.trip.trip_detail.TripDetail;
import com.example.datn_2020.viewmodel.ContainerVM;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TripTabLayoutFragment extends Fragment implements ListTripAdapter.ItemTripClickListener, View.OnClickListener, AddTripFragmentDialog.SelectAddDialogInterface {
//
//    public TripTabLayoutFragment() {
//    }

    private final String TAG = "TripTabLayoutFragment";

    private RecyclerView rvListTrip;
    private ListTripAdapter listTripAdapter;
    private ArrayList<TripModel> tripModelArrayList = new ArrayList<>();
    private FloatingActionButton fbAddTrip;

    private ContainerVM tripVM;
    private FragmentActivity fragmentActivity;
    private TripFragment tripFragment;
    private Context mContext;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip_tablayout, container, false);

        rvListTrip = view.findViewById(R.id.rvListTrip);
        fbAddTrip = view.findViewById(R.id.fbAddTrip);

        if (getActivity() != null) {
            fragmentActivity = getActivity();
        }

        MainTripFragment mainTripFragment = (MainTripFragment) getParentFragment();
        tripFragment = (TripFragment) mainTripFragment.getParentFragment();
        registerRecyclerview();

        tripVM = new ViewModelProvider(getActivity()).get(ContainerVM.class);
        tripVM.loadTrip(CurrentUser.getInstance().id);
        tripVM.getListTripModelResponse().observe(getViewLifecycleOwner(), new Observer<ArrayList<TripModel>>() {
            @Override
            public void onChanged(ArrayList<TripModel> tripModels) {
                tripModelArrayList = tripModels;
                listTripAdapter.setItems(tripModelArrayList);
            }
        });

        fbAddTrip.setOnClickListener(this);
        return view;
    }

    private void registerRecyclerview() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvListTrip.setLayoutManager(linearLayoutManager);
        rvListTrip.setHasFixedSize(true);
        listTripAdapter = new ListTripAdapter(getActivity(), tripModelArrayList, this);
        rvListTrip.setAdapter(listTripAdapter);
    }

    @Override
    public void onItemClick(TripModel tripModel) {
        ArrayList<String> listNameTrip = new ArrayList<>();

        for (TripModel current : tripModelArrayList) {
            String name = current.getName().trim();
            listNameTrip.add(name.substring(name.indexOf(" ") + 1));
        }

        TripDetail mTripDetail = new TripDetail();
        Bundle mBundle = new Bundle();
        mBundle.putStringArrayList("names", listNameTrip);
        mBundle.putInt("idTrip",tripModel.getId());
        mTripDetail.setArguments(mBundle);

        tripFragment.replaceTripFragment(mTripDetail);
        fragmentActivity.findViewById(R.id.btnBottomNavigation).setVisibility(View.GONE);
    }

    @Override
    public void onDeleteTrip(final TripModel mTripModel, final int position) {
        AlertDialog mAlertDialog = new AlertDialog.Builder(mContext)
                .setTitle("Thông báo")
                .setMessage("Bạn muốn xóa chuyến đi?")
                .setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String admin = mTripModel.getName().substring(0, mTripModel.getName().indexOf(" "));

                        DeleteTripModel mDeleteTripModel = new DeleteTripModel(CurrentUser.getInstance().id, mTripModel.getId());

                        if(CurrentUser.getInstance().username.equals(admin)) {
                            tripVM.deleteTrip(mDeleteTripModel);
                        }else {
                            tripVM.deleteTripMember(mDeleteTripModel);
                        }
//                        tripVM.getDeleteTrip().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
//                            @Override
//                            public void onChanged(Boolean result) {
//                                if(result){
//                                    Toast.makeText(fragmentActivity, "Xóa thành công!", Toast.LENGTH_SHORT).show();
//                                    tripModelArrayList.remove(position);
//                                    Log.i("TripTabLayout", "tripModelArrayList:" + tripModelArrayList.size());
//                                    Log.i("TripTabLayout", "position: " + tripModelArrayList.size());
//                                    listTripAdapter.notifyDataSetChanged();
//                                }else {
//                                    Toast.makeText(fragmentActivity, "Xóa thất bại!", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
                        Log.i("TripTabLayout", "tripModelArrayList:" + tripModelArrayList.size());
                        Log.i("TripTabLayout", "position: " + tripModelArrayList.size());
                        tripModelArrayList.remove(position);
                        Log.i("TripTabLayout", "tripModelArrayList:" + tripModelArrayList.size());
                        Log.i("TripTabLayout", "position: " + tripModelArrayList.size());
                        listTripAdapter.notifyDataSetChanged();
//                        listTripAdapter.notifyItemChanged(position);
                        dialog.cancel();
                    }
                }).setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).create();
        mAlertDialog.show();
        Button positive = mAlertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        positive.setTextColor(positive.getResources().getColor(R.color.colorBlue700));
        Button negative = mAlertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        negative.setTextColor(positive.getResources().getColor(R.color.colorGray700));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fbAddTrip:
                AddTripFragmentDialog addTripFragmentDialog = new AddTripFragmentDialog();
                addTripFragmentDialog.setAddTripFragmentDialog(this);
                addTripFragmentDialog.show(getChildFragmentManager(), null);
                break;
        }
    }

    @Override
    public void selectAddTrip(String name) {
        String tripName = CurrentUser.getInstance().username + " " + name;
        int idUser = CurrentUser.getInstance().id;
        TripModel tripModel = new TripModel(idUser, tripName);

        tripVM.addTrip(tripModel);
        tripVM.getAddTripResponse().observe(getViewLifecycleOwner(), new Observer<TripModel>() {
            @Override
            public void onChanged(TripModel result) {
                if (result != null) {
                    //Thêm chuyen đi vào trong recyclerview
                    Toast.makeText(fragmentActivity, "Đã thêm chuyến đi mới!", Toast.LENGTH_LONG).show();
                    int index = tripModelArrayList.size();
                    tripModelArrayList.add(result);
                    listTripAdapter.notifyItemInserted(index);
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        tripVM.getNewTrip().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean result) {
                if(result){
                    tripVM.loadTrip(CurrentUser.getInstance().id);
                    tripVM.getListTripModelResponse().observe(getViewLifecycleOwner(), new Observer<ArrayList<TripModel>>() {
                        @Override
                        public void onChanged(ArrayList<TripModel> tripModels) {
                            tripModelArrayList = tripModels;
                            listTripAdapter.setItems(tripModelArrayList);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.i(TAG,"onActivityCreated");
    }

    @Override
    public void onPause() {
        super.onPause();

        Log.i(TAG,"onPause");
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
        Log.i(TAG,"onDetach");
        mContext = null;
    }
}
