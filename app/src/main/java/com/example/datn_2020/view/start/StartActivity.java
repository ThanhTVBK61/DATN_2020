package com.example.datn_2020.view.start;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.datn_2020.R;
import com.example.datn_2020.repository.model.CurrentUser;
import com.example.datn_2020.repository.room.entity.User;
import com.example.datn_2020.view.container.ContainerActivity;
import com.example.datn_2020.view.guest.GuestActivity;
import com.example.datn_2020.view.login.LoginActivity;
import com.example.datn_2020.viewmodel.login.LoginVM;

public class StartActivity extends AppCompatActivity {

    private final String TAG ="StartActivity";
//    public static String USERNAME;
//    public static String EMAIL;
//    public static int ID;

    private final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private Boolean mLocationPermissionGranted = false;
    private boolean done = false;
    private LoginVM loginVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start);
        loginVM = new ViewModelProvider(StartActivity.this).get(LoginVM.class);
        loginVM.checkUser();

        getLocationPermission();

    }

    private void intent() {
        loginVM.getUserData().observe(StartActivity.this, new Observer<User>() {
            @Override
            public void onChanged(User mUserData) {
                if (mUserData.id != -1) {
                    Log.i(TAG, "usernameRoom != null id = "+ mUserData.id);
//                    ID = mUserData.id;
////                    USERNAME = mUserData.username;
////                    EMAIL = mUserData.email;
                    CurrentUser.setInstance(mUserData);
                    Intent mIntent = new Intent(StartActivity.this, ContainerActivity.class);
                    startActivity(mIntent);
                    finish();
                } else {
                    User mUser = new User();
                    mUser.id = -1;
                    mUser.username = "";
                    mUser.email = "";
                    CurrentUser.setInstance(mUser);

                    Log.i(TAG, "usernameRoom == null");
                    Intent mIntent = new Intent(StartActivity.this, GuestActivity.class);
                    startActivity(mIntent);
                    finish();
                }
            }
        });
    }

    public void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this, FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
                intent();
            } else {
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionGranted = false;
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {

            if (grantResults.length > 0) {
                for (int grantResult : grantResults) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        mLocationPermissionGranted = false;
                        intent();
                        return;
                    }
                }
                Log.d(TAG, "onRequestPermissionsResult: permission granted.");
                mLocationPermissionGranted = true;
                intent();
            }
        }
    }
}


