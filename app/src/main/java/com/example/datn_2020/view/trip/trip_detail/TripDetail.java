package com.example.datn_2020.view.trip.trip_detail;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_2020.R;
import com.example.datn_2020.adapter.trip.ListPlaceInTripAdapter;
import com.example.datn_2020.repository.model.AllUserModel;
import com.example.datn_2020.repository.model.CurrentUser;
import com.example.datn_2020.repository.model.EditTripModel;
import com.example.datn_2020.repository.model.FavouritePlaceCheckModel;
import com.example.datn_2020.repository.model.Message;
import com.example.datn_2020.repository.model.PlaceInTripModel;
import com.example.datn_2020.repository.model.SocketIo;
import com.example.datn_2020.repository.model.TripInformationModel;
import com.example.datn_2020.repository.model.UpdatePlaceInTripModel;
import com.example.datn_2020.repository.model.UpdateTimeTripModel;
import com.example.datn_2020.repository.network.DisposableManager;
import com.example.datn_2020.view.home.PlaceDetail;
import com.example.datn_2020.view.home.SearchPlaceFragment;
import com.example.datn_2020.view.trip.TripFragment;
import com.example.datn_2020.viewmodel.ContainerVM;
import com.example.datn_2020.viewmodel.trip.TripDetailVM;
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
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class TripDetail extends Fragment implements OnMapReadyCallback, View.OnClickListener, AddMemberDialogFragment.SelectAddMember, ListPlaceInTripAdapter.ItemPlaceInTripItemClick, EditTripDialogFragment.SelectEditTrip, TimeTripDialogFragment.SelectUpdateTimeTrip {

    private final String TAG = "TripDetail";
    private final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;

    private Boolean mLocationPermissionGranted = false;

    private TripDetailVM tripDetailVM;
    private FragmentActivity fragmentActivity;
    private FloatingActionButton fbAddPlace;
    private Context mContext;

    private TextView tvNameTrip;
    private TextView tvAdminTrip;
    private TextView tvMemberTrip;
    private TextView tvTimeTrip;
    private TextView tvDescriptionTrip;
    private ImageView ivEditMemberInTrip;
    private LinearLayout llAddMember;
    private LinearLayout llEditTrip;
    private LinearLayout llEditTimeTrip;
    private RecyclerView rvListPlaceInTrip;

    private String members = "\n";

    private ImageButton ibButtonTripBack;
    private GoogleMap mMap;

    private BottomSheetBehavior mBottomSheetBehavior;
    private ArrayList<AllUserModel> allUser = new ArrayList<>();
    private ArrayList<String> listNameTrip = new ArrayList<>();
    private String nameTrip = "";
    private String time = "";
    private String description = "";
    private String admin = "";
    private int id = -1;
    private Message newMessage;

    private ArrayList<PlaceInTripModel> mPlaceInTripModels = new ArrayList<>();
    private ListPlaceInTripAdapter listPlaceInTripAdapter;

    private TripFragment tripFragment;
    private FavouritePlaceCheckModel favouritePlaceCheckModel = new FavouritePlaceCheckModel();
    private ArrayList<Marker> listMarker = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip_detail, container, false);

        tripFragment = (TripFragment) getParentFragment();
        mPlaceInTripModels.clear();
        Bundle getBundle = getArguments();
        if (getBundle != null) {
            listNameTrip = getBundle.getStringArrayList("names");
            id = getBundle.getInt("idTrip");
        }
        if (listNameTrip != null) {
            Log.d(TAG, "List names: " + listNameTrip.size());
        } else {
            Log.d(TAG, "Error get names!");
        }

        registerViews(view);

        if (getActivity() != null) {
            fragmentActivity = getActivity();
        }

        tripDetailVM = new ViewModelProvider(this).get(TripDetailVM.class);
        tripDetailVM.loadTripInformation(id);
        tripDetailVM.loadPlaceInTrip(id, CurrentUser.getInstance().id);
        loadBaseInformation();
        registerRecyclerview();
        getData();

        //Lay ten dang nhap su dung kiem tra ten dang nhap co ton tai khi nguoi dung muon them
        //thanh viên vào
        getAllUser();

        RelativeLayout mView = view.findViewById(R.id.rlBottomSheet);
        mBottomSheetBehavior = BottomSheetBehavior.from(mView);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

//        getLocationPermission();

        ibButtonTripBack.setOnClickListener(this);
        tvMemberTrip.setOnClickListener(this);
        ivEditMemberInTrip.setOnClickListener(this);
        llAddMember.setOnClickListener(this);
        llEditTrip.setOnClickListener(this);
        llEditTimeTrip.setOnClickListener(this);
        fbAddPlace.setOnClickListener(this);

        return view;
    }

    private void getAllUser() {
        tripDetailVM.loadAllUser();
        tripDetailVM.getmAllUserModels().observe(getViewLifecycleOwner(), new Observer<ArrayList<AllUserModel>>() {
            @Override
            public void onChanged(ArrayList<AllUserModel> allUserModels) {
                allUser = allUserModels;
            }
        });
    }

    private void getData() {
        tripDetailVM.getPlaceInTripModels().observe(getViewLifecycleOwner(), new Observer<ArrayList<PlaceInTripModel>>() {
            @Override
            public void onChanged(ArrayList<PlaceInTripModel> placeInTripModels) {
                mPlaceInTripModels = placeInTripModels;
                Log.i(TAG, "Number Place: " + mPlaceInTripModels.size());
//                registerRecyclerview();
                listPlaceInTripAdapter.setItems(mPlaceInTripModels);
                getLocationPermission();
            }
        });
    }

    private void registerRecyclerview() {
        Log.i(TAG, "Init Recycler View: ");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvListPlaceInTrip.setLayoutManager(linearLayoutManager);
        rvListPlaceInTrip.setHasFixedSize(true);
        listPlaceInTripAdapter = new ListPlaceInTripAdapter(mContext, mPlaceInTripModels, this);
        rvListPlaceInTrip.setAdapter(listPlaceInTripAdapter);
    }

    private void loadBaseInformation() {
        tripDetailVM.getmTripInformationModels().observe(getViewLifecycleOwner(), new Observer<ArrayList<TripInformationModel>>() {
            @Override
            public void onChanged(ArrayList<TripInformationModel> tripInformationModels) {

                String name = tripInformationModels.get(0).getName().trim();

                int position = name.indexOf(" ");
                admin = name.substring(0, position);
                nameTrip = name.substring(position + 1);

                tvNameTrip.setText(nameTrip);
                String adminTrip = "Admin: " + admin;
                tvAdminTrip.setText(adminTrip);

                String member = "Thành viên: " + tripInformationModels.size();
                tvMemberTrip.setText(member);

                //Nếu không phải người tạo chuyến đi sẽ không xóa được thành viên khác
                if (!admin.equals(CurrentUser.getInstance().username)) {
                    ivEditMemberInTrip.setVisibility(View.GONE);
                }


                if (tripInformationModels.get(0).getTime() == null || tripInformationModels.get(0).getTime().equals("")) {
                    tvTimeTrip.setText("Chưa có thời gian cho chuyến đi này");
                    tvTimeTrip.setTextColor(tvTimeTrip.getResources().getColor(R.color.colorGray));
                } else {
                    time = tripInformationModels.get(0).getTime().trim();
                    tvTimeTrip.setText(time);
                    tvTimeTrip.setTextColor(tvTimeTrip.getResources().getColor(R.color.colorBlack));
                }


                if (tripInformationModels.get(0).getDescription() == null || tripInformationModels.get(0).getDescription().equals("")) {
                    tvDescriptionTrip.setText("Chưa có mô tả cho chuyến đi này");
                    tvDescriptionTrip.setTextColor(tvDescriptionTrip.getResources().getColor(R.color.colorGray));
                } else {
                    description = tripInformationModels.get(0).getDescription();
                    tvDescriptionTrip.setText(description);
                    tvDescriptionTrip.setTextColor(tvDescriptionTrip.getResources().getColor(R.color.colorBlack));
                }
                int sum = tripInformationModels.size();
//                members += tripInformationModels.get(0).getUsername() +"\n";
                members = "";
                for (int pos = 0; pos < sum; pos++) {
//                    if (pos != sum - 1) {
//                        members += "\n\n" + tripInformationModels.get(pos).getUsername();
//                    }
                    members += "\n" + tripInformationModels.get(pos).getUsername();
                    Log.i(TAG, "members: "+members);
                }
            }
        });
    }

    private void registerViews(View view) {
        ibButtonTripBack = view.findViewById(R.id.ibButtonTripBack);
        tvNameTrip = view.findViewById(R.id.tvNameTrip);
        tvAdminTrip = view.findViewById(R.id.tvAdminTrip);
        fbAddPlace = view.findViewById(R.id.fbAddPlace);
        tvMemberTrip = view.findViewById(R.id.tvMemberTrip);
        ivEditMemberInTrip = view.findViewById(R.id.ivEditMemberInTrip);
        tvTimeTrip = view.findViewById(R.id.tvTimeTrip);
        tvDescriptionTrip = view.findViewById(R.id.tvDescriptionTrip);
        rvListPlaceInTrip = view.findViewById(R.id.rvListPlaceInTrip);

        llAddMember = view.findViewById(R.id.llAddMember);
        llEditTrip = view.findViewById(R.id.llEditTrip);
        llEditTimeTrip = view.findViewById(R.id.llEditTimeTrip);
    }

    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(mContext, FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = ContextCompat.checkSelfPermission(mContext, COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        } else {
            mLocationPermissionGranted = false;
        }
        initMap();
    }

    private void initMap() {
        Log.d(TAG, "initMap: Initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fAllPlaceInTrip);
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
                            // setMakerPlace();
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
        moveCamera(new LatLng(21.004977, 105.843785));
        setMakerPlace();
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
        if (mPlaceInTripModels.size() > 0) {
            for (PlaceInTripModel placeInfo : mPlaceInTripModels) {
                String[] coordinate = placeInfo.getCoordinatePlace().trim().split(" ");
                float lat = Float.parseFloat(coordinate[0]);
                float lng = Float.parseFloat(coordinate[1]);
                LatLng mLatLng = new LatLng(lat, lng);

                MarkerOptions markerOptions = new MarkerOptions()
                        .position(mLatLng)
                        .title(placeInfo.getNamePlace());
//                if (typePlace.equals("Bảo tàng"))
//                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_museum));
                listMarker.add(mMap.addMarker(markerOptions));
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibButtonTripBack:
//                TripFragment tripFragment = (TripFragment) getParentFragment();
                tripFragment.backTripStack();
                Objects.requireNonNull(getActivity()).findViewById(R.id.btnBottomNavigation).setVisibility(View.VISIBLE);
                break;
            case R.id.ivEditMemberInTrip:

                break;
            case R.id.tvMemberTrip:
                // setup the alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Thành viên trong chuyến đi");
                // add a list
                builder.setMessage(members);

                builder.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();
                Button positive = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                positive.setTextColor(positive.getResources().getColor(R.color.colorBlue700));
                break;

            case R.id.llAddMember:
                AddMemberDialogFragment addMemberDialogFragment = new AddMemberDialogFragment();
                addMemberDialogFragment.setSelectAddMember(this);
                addMemberDialogFragment.show(getChildFragmentManager(), null);
                break;
            case R.id.llEditTrip:
                EditTripDialogFragment editTripDialogFragment = new EditTripDialogFragment();
                Bundle mBundle = new Bundle();
                mBundle.putString("name", nameTrip);
                mBundle.putString("description", description);
                editTripDialogFragment.setArguments(mBundle);
                editTripDialogFragment.setSelectEditTrip(this);
                editTripDialogFragment.show(getChildFragmentManager(), null);
                break;
            case R.id.llEditTimeTrip:
                TimeTripDialogFragment timeTripDialogFragment = new TimeTripDialogFragment();
                String start = "...";
                String end = "...";
                if (!time.equals("")) {
                    start = time.substring(0, time.indexOf(" "));
                    end = time.substring(time.indexOf(" ") + 1);
                }
                Bundle timeBundle = new Bundle();
                timeBundle.putString("start", start);
                timeBundle.putString("end", end);
                timeTripDialogFragment.setArguments(timeBundle);
                timeTripDialogFragment.setSelectUpdateTimeTrip(this);
                timeTripDialogFragment.show(getChildFragmentManager(), null);
                break;
            case R.id.fbAddPlace:
                SearchPlaceFragment mSearchPlaceFragment = new SearchPlaceFragment();
                ArrayList<Integer> currentPlace = new ArrayList<>();
                for (PlaceInTripModel placeModel : mPlaceInTripModels) {
                    currentPlace.add(placeModel.getIdPlace());
                }
                Log.i(TAG,"mPlaceInTripModels: "+mPlaceInTripModels.size());
                Log.i(TAG,"currentPlace: "+currentPlace.size());
                Bundle bundle = new Bundle();
                bundle.putString("purpose", "add");
                bundle.putInt("idtrip", id);
                bundle.putIntegerArrayList("current place", currentPlace);
                mSearchPlaceFragment.setArguments(bundle);
                tripFragment.replaceTripFragment(mSearchPlaceFragment);
                break;

        }
    }

    @Override
    public void selectAdd(String username) {
        Log.i(TAG, username);
        int idAccountReceiver = checkUsername(username);
        if (idAccountReceiver != -1) {
            //Tạo đối tượng gửi đi
            newMessage = new Message();
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm dd/MM/yyyy");
            Date date = new Date();
            String time = formatter.format(date);
            newMessage.setTime(time);
            newMessage.setIdTrip(id);
            newMessage.setIdUserSend(CurrentUser.getInstance().id);
            newMessage.setNameTrip(nameTrip);
            newMessage.setType("Mời");
            newMessage.setUsernameReceive(username);
            newMessage.setUsernameSend(CurrentUser.getInstance().username);
            newMessage.setIdUserReceive(idAccountReceiver);
            for (AllUserModel userModel : allUser) {
                if (userModel.getUsername().equals(CurrentUser.getInstance().username))
                    newMessage.setImageUrlSend(userModel.getImage());
            }

            Gson gson = new Gson();
            String mMessage = gson.toJson(newMessage);
            Log.i("Message", " " + mMessage);
            SocketIo.getInstance().emit("add member", mMessage);
        } else {
            //Tạo đối tượng
            AlertDialog.Builder b = new AlertDialog.Builder(mContext);
            b.setTitle("Thông báo");
            b.setMessage("Tên thành viên không tồn tại!!");
            //Nút Cancel
            b.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            //Tạo dialog
            AlertDialog alertDialog = b.create();
            //Hiển thị
            alertDialog.show();
            Button negative = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
            negative.setTextColor(negative.getResources().getColor(R.color.colorBlue700));
        }
    }

    private int checkUsername(String username) {
        if (username.equals(CurrentUser.getInstance().username))
            return -1;

        //Hiển thị danh sách người dùng
        Log.i(TAG, "Number username" + allUser.size());
        for (AllUserModel allUserModel : allUser) {
            Log.i(TAG, allUserModel.getUsername());
        }
        //Trả về địa chỉ id của người muốn thêm vào
        for (AllUserModel userModel : allUser) {
            if (userModel.getUsername().equals(username))
                return userModel.getIdUser();
        }

        return -1;
    }

    @Override
    public void selectEdit(final String nameTrip, final String newDescription) {
        Toast.makeText(fragmentActivity, newDescription, Toast.LENGTH_SHORT).show();
        //Kiem tra ten co bi trung
        String newName = nameTrip.trim();
        boolean check = true;
        for (String mName : listNameTrip) {
            if (newName.equals(mName)) {
                check = false;
                new AlertDialog.Builder(mContext).setTitle("Thông báo")
                        .setMessage("Tên chuyến đi không hợp lệ!")
                        .setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        }).create().show();
                break;
            }
        }

        if (check) {
            //update len server
            newName = admin + " " + newName;
            EditTripModel mEditTripModel = new EditTripModel(id, newName, newDescription);
            tripDetailVM.updateEditTrip(mEditTripModel);
            tripDetailVM.getEditTripResponse().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean result) {
                    if (result) {
                        tvNameTrip.setText(nameTrip);
                        if (!description.equals("")) {
                            tvDescriptionTrip.setText(newDescription);
                            tvDescriptionTrip.setTextColor(tvDescriptionTrip.getResources().getColor(R.color.colorBlack));
                        }
                        new AlertDialog.Builder(mContext).setTitle("Thông báo")
                                .setMessage("Cập nhật thành công!")
                                .setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                }).create().show();
                    } else {
                        new AlertDialog.Builder(mContext).setTitle("Thông báo")
                                .setMessage("Cập nhật thất bại!")
                                .setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                }).create().show();
                    }
                }
            });
        }
    }

    @Override
    public void selectUpdate(final String timeTrip) {

        tripDetailVM.updateTimeTrip(new UpdateTimeTripModel(id, timeTrip));
        tripDetailVM.getUpdateTimeTripResponse().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean result) {
                if (result) {
                    tvTimeTrip.setText(timeTrip);
                    tvTimeTrip.setTextColor(tvTimeTrip.getResources().getColor(R.color.colorBlack));
                    AlertDialog mAlertDialog = new AlertDialog.Builder(mContext).setTitle("Thông báo")
                            .setMessage("Cập nhật thành công")
                            .setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).create();
                    mAlertDialog.show();
                    mAlertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorBlue700));
                } else {
                    AlertDialog mAlertDialog = new AlertDialog.Builder(mContext).setTitle("Thông báo")
                            .setMessage("Cập nhật thời gian thất bại")
                            .setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).create();
                    mAlertDialog.show();
                    mAlertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorBlue700));
                }
            }
        });
    }

    @Override
    public void onItemClick(PlaceInTripModel placeInTripModel) {
        tripDetailVM.loadPlaceById(placeInTripModel.getIdPlace());
        PlaceDetail placeDetail = new PlaceDetail();
        Bundle mBundle = new Bundle();
        mBundle.putBoolean("parent", false);
        mBundle.putString("name", "com/example/datn_2020/viewmodel/trip");
        mBundle.putInt("id", placeInTripModel.getIdPlace());
        placeDetail.setArguments(mBundle);
        tripFragment.replaceTripFragment(placeDetail);
    }

    @Override
    public void onFavouriteItemClick(PlaceInTripModel placeInTripModel, boolean isChecked) {
        favouritePlaceCheckModel.setIdPlace(placeInTripModel.getIdPlace());
        favouritePlaceCheckModel.setFavourite(isChecked);
        favouritePlaceCheckModel.setIdUser(CurrentUser.getInstance().id);
        tripDetailVM.updateFavouritePlace(favouritePlaceCheckModel);
    }

    @Override
    public void onDeletePlace(final PlaceInTripModel placeInTripModel, final int position) {
        AlertDialog mAlertDialog = new AlertDialog.Builder(mContext)
                .setTitle("Thông báo")
                .setMessage("Bạn muốn xóa địa điểm khỏi chuyến đi?")
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        UpdatePlaceInTripModel mUpdatePlaceInTripModel = new UpdatePlaceInTripModel(id, placeInTripModel.getIdPlace());
                        tripDetailVM.deletePlaceInTrip(mUpdatePlaceInTripModel);
//                        tripDetailVM.getDeletePlaceInTripResponse().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
//                            @Override
//                            public void onChanged(Boolean result) {
//                                if (result) {
////                                    Toast.makeText(fragmentActivity, "Xóa thành công!", Toast.LENGTH_SHORT).show();
//                                    mPlaceInTripModels.remove(position);
//                                    listPlaceInTripAdapter.notifyItemRemoved(position);
//
//                                } else {
//                                    Toast.makeText(fragmentActivity, "Xóa thất bại!", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });


                        for(Marker marker:listMarker){
                            LatLng latLng =  marker.getPosition();
                            Log.i(TAG,"latitude: "+ latLng.latitude+" longitude: "+ latLng.longitude);
                            String[] coordinate = placeInTripModel.getCoordinatePlace().trim().split(" ");
                            double lat = Float.parseFloat(coordinate[0]);
                            double lng = Float.parseFloat(coordinate[1]);
                            Log.i(TAG,"lat: "+ lat+" lng: "+ lng);
                            if(lat == latLng.latitude && lng==latLng.longitude){
                                marker.remove();
                                break;
                            }
                        }
                        mPlaceInTripModels.remove(position);
                        listPlaceInTripAdapter.notifyDataSetChanged();

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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
        Log.i("TripDetail", "onDetach");

        DisposableManager.dispose();
    }
}
