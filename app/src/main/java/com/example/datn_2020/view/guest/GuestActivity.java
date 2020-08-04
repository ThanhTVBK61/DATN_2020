package com.example.datn_2020.view.guest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.datn_2020.R;
import com.example.datn_2020.view.home.HomeFragment;
import com.example.datn_2020.viewmodel.ContainerVM;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class GuestActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private Fragment fragmentHomeGuest = new HomeFragment();
    private Fragment fragmentTripGuest = new OtherGuestFragment("Chuyến đi");
    private Fragment fragmentNotificationGuest = new OtherGuestFragment("Thông báo");
    private Fragment fragmentAccountGuest = new OtherGuestFragment("Tài khoản");
    private Fragment active = fragmentHomeGuest;
    private BottomNavigationView btnBottomNavigationGuest;
    private ContainerVM containerVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);

        btnBottomNavigationGuest = findViewById(R.id.btnBottomNavigation);

        containerVM = new ViewModelProvider(this).get(ContainerVM.class);

        fragmentManager.beginTransaction().add(R.id.flGuest, fragmentAccountGuest, "com/example/datn_2020/viewmodel/account").hide(fragmentAccountGuest).commit();
        fragmentManager.beginTransaction().add(R.id.flGuest, fragmentNotificationGuest, "com/example/datn_2020/adapter/notification").hide(fragmentNotificationGuest).commit();
        fragmentManager.beginTransaction().add(R.id.flGuest, fragmentTripGuest, "com/example/datn_2020/viewmodel/trip").hide(fragmentTripGuest).commit();
        fragmentManager.beginTransaction().add(R.id.flGuest, fragmentHomeGuest, "home").show(fragmentHomeGuest).commit();

        btnBottomNavigationGuest.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home: fragmentManager.beginTransaction().hide(active).show(fragmentHomeGuest).commit();
                active = fragmentHomeGuest;
                break;
            case R.id.action_trip:
                fragmentManager.beginTransaction().hide(active).show(fragmentTripGuest).commit();
                active = fragmentTripGuest;
                break;
            case R.id.action_Notification:
                fragmentManager.beginTransaction().hide(active).show(fragmentNotificationGuest).commit();
                active = fragmentNotificationGuest;
                break;
            case R.id.action_account:
                fragmentManager.beginTransaction().hide(active).show(fragmentAccountGuest).commit();
                active = fragmentAccountGuest;
                break;
        }
        return true;
    }
}
