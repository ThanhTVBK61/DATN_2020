package com.example.datn_2020.view.account;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.datn_2020.R;

import java.util.Objects;

public class AddressAccountFragment extends Fragment {

    private Toolbar tbAddressAccount;
    private TextView tvEditAddress;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_address_account, container, false);

        tvEditAddress = view.findViewById(R.id.tvEditAddressAccount);
        tbAddressAccount = view.findViewById(R.id.tbAddressAccount);


        tbAddressAccount.setNavigationIcon(R.drawable.ic_back);
        tbAddressAccount.setNavigationOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
                AccountFragment accountFragment = (AccountFragment) getParentFragment();
                accountFragment.backStack();
                Objects.requireNonNull(getActivity()).findViewById(R.id.btnBottomNavigation).setVisibility(View.VISIBLE);
            }
        });

        tvEditAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountFragment accountFragment = (AccountFragment) getParentFragment();
                accountFragment.replaceFragment(new EditAddressAccountFragment());
            }
        });

        return view;
    }
}
