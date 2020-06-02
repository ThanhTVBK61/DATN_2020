package com.example.datn_2020.view.container;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.datn_2020.R;
import com.example.datn_2020.view.view_account.AccountFragment;
import com.example.datn_2020.view.view_home.HomeFragment;
import com.example.datn_2020.view.view_notification.NotificationFragment;
import com.example.datn_2020.view.view_trip.TripFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ContainerActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private BottomNavigationView bottomNavigationView;
    private Fragment fragmentHome = new HomeFragment();
    private Fragment fragmentTrip = new TripFragment();
    private Fragment fragmentNotification = new NotificationFragment();
    private Fragment fragmentAccount = new AccountFragment();
    private Fragment active= fragmentHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        bottomNavigationView = findViewById(R.id.btnBottomNavigation);

        fragmentManager.beginTransaction().add(R.id.flContainer,fragmentAccount,"account").hide(fragmentAccount).commit();
        fragmentManager.beginTransaction().add(R.id.flContainer,fragmentNotification,"notification").hide(fragmentNotification).commit();
        fragmentManager.beginTransaction().add(R.id.flContainer,fragmentTrip,"trip").hide(fragmentTrip).commit();
        fragmentManager.beginTransaction().add(R.id.flContainer,fragmentHome,"home").commit();


        bottomNavigationView.setOnNavigationItemSelectedListener(this);
//        if(savedInstanceState == null){
//            bottomNavigationView.setSelectedItemId(R.id.action_home);
//        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_home:
                fragmentManager.beginTransaction().hide(active).show(fragmentHome).commit();
                active = fragmentHome;
                break;
            case R.id.action_trip:
                fragmentManager.beginTransaction().hide(active).show(fragmentTrip).commit();
                active = fragmentTrip;
                break;
            case R.id.action_Notification:
                fragmentManager.beginTransaction().hide(active).show(fragmentNotification).commit();
                active = fragmentNotification;
                break;
            case R.id.action_account:
                fragmentManager.beginTransaction().hide(active).show(fragmentAccount).commit();
                active = fragmentAccount;
                break;
        }
        return true;
    }

}
