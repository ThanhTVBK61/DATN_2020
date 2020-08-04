package com.example.datn_2020.view.container;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.datn_2020.R;
import com.example.datn_2020.repository.model.CurrentUser;
import com.example.datn_2020.repository.model.SocketIo;
import com.example.datn_2020.view.account.AccountFragment;
import com.example.datn_2020.view.home.HomeFragment;
import com.example.datn_2020.view.notification.NotificationFragment;
import com.example.datn_2020.view.trip.TripFragment;
import com.example.datn_2020.viewmodel.ContainerVM;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ContainerActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private final String TAG="MapActivity";
    private final String FINE_LOCATION=Manifest.permission.ACCESS_FINE_LOCATION;
    private final String COURSE_LOCATION=Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE =1234;
    public Boolean mLocationPermissionGranted = false;

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private BottomNavigationView bottomNavigationView;
    private Fragment fragmentHome = new HomeFragment();
    private Fragment fragmentTrip = new TripFragment();
    private Fragment fragmentNotification = new NotificationFragment();
    private Fragment fragmentAccount = new AccountFragment();
    private Fragment active = fragmentHome;
    private ContainerVM containerVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        //Tao ket noi socket
        SocketIo.getInstance().connect();
        SocketIo.getInstance().emit("new connection", CurrentUser.getInstance().username);

        bottomNavigationView = findViewById(R.id.btnBottomNavigation);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        }

        containerVM = new ViewModelProvider(this).get(ContainerVM.class);

        fragmentManager.beginTransaction().add(R.id.flContainer, fragmentAccount, "com/example/datn_2020/viewmodel/account").hide(fragmentAccount).commit();
        fragmentManager.beginTransaction().add(R.id.flContainer, fragmentNotification, "com/example/datn_2020/adapter/notification").hide(fragmentNotification).commit();
        fragmentManager.beginTransaction().add(R.id.flContainer, fragmentTrip, "com/example/datn_2020/viewmodel/trip").hide(fragmentTrip).commit();
        fragmentManager.beginTransaction().add(R.id.flContainer, fragmentHome, "home").show(fragmentHome).commit();

        containerVM.setActivateFragment(0);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
//        getLocationPermission();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_home:
                containerVM.setActivateFragment(0);
                fragmentManager.beginTransaction().hide(active).show(fragmentHome).commit();
                active = fragmentHome;
                break;
            case R.id.action_trip:
                containerVM.setActivateFragment(1);
                fragmentManager.beginTransaction().hide(active).show(fragmentTrip).commit();
                active = fragmentTrip;
                break;
            case R.id.action_Notification:
                containerVM.setActivateFragment(2);
                fragmentManager.beginTransaction().hide(active).show(fragmentNotification).commit();
                active = fragmentNotification;
                break;
            case R.id.action_account:
                containerVM.setActivateFragment(3);
                fragmentManager.beginTransaction().hide(active).show(fragmentAccount).commit();
                active = fragmentAccount;
                break;
        }
        return true;
    }

//    private void getLocationPermission(){
//        Log.d(TAG,"getLocationPermission: getting location permissions");
//        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};
//
//        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
//            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),COURSE_LOCATION)==PackageManager.PERMISSION_GRANTED){
//                mLocationPermissionGranted = true;
//            }else {
//                ActivityCompat.requestPermissions(this,permissions, LOCATION_PERMISSION_REQUEST_CODE);
//            }
//        }else {
//            ActivityCompat.requestPermissions(this,permissions,LOCATION_PERMISSION_REQUEST_CODE);
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        Log.d(TAG,"onRequestPermissionsResult: called.");
//        mLocationPermissionGranted = false;
//        if(requestCode == LOCATION_PERMISSION_REQUEST_CODE){
//
//            if(grantResults.length > 0) {
//                for (int grantResult : grantResults) {
//                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
//                        mLocationPermissionGranted = false;
//                        Log.d(TAG, "onRequestPermissionsResult: permission failed.");
//                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//                        alertDialogBuilder.setTitle("Thông báo");
//                        alertDialogBuilder.setMessage("Từ chối truy cập vị trí sẽ không xác định được các địa điểm quanh bạn");
//                        alertDialogBuilder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        });
//                        Dialog mDialog = alertDialogBuilder.create();
//                        mDialog.show();
//
//                        return;
//                    }
//                }
//                Log.d(TAG, "onRequestPermissionsResult: permission granted.");
//
//                mLocationPermissionGranted = true;
//            }
//        }
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        SocketIo.getInstance().disconnect();
    }
}
