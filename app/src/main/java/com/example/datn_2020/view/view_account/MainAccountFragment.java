package com.example.datn_2020.view.view_account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.datn_2020.R;

import java.util.Objects;


public class MainAccountFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_account, container, false);
        LinearLayout llAddressAccount = view.findViewById(R.id.llAddressAccount);
        RelativeLayout rlInformationAccount = view.findViewById(R.id.rlInformationAccount);

        llAddressAccount.setOnClickListener(this);
        rlInformationAccount.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        AccountFragment accountFragment = (AccountFragment) getParentFragment();

        switch (v.getId()){
            case R.id.llAddressAccount:
                accountFragment.replaceFragment(new AddressAccountFragment());
                Objects.requireNonNull(getActivity()).findViewById(R.id.btnBottomNavigation).setVisibility(View.GONE);
                break;
            case R.id.rlInformationAccount:
                accountFragment.replaceFragment(new InformationAccountFragment());
                Objects.requireNonNull(getActivity()).findViewById(R.id.btnBottomNavigation).setVisibility(View.GONE);
                break;
        }
    }
}
