package com.example.datn_2020.view.account;

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

    private RelativeLayout rlSetting;
    private LinearLayout llAddressAccount;
    private RelativeLayout rlInformationAccount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_account, container, false);
        registerViews(view);

        llAddressAccount.setOnClickListener(this);
        rlInformationAccount.setOnClickListener(this);
        rlSetting.setOnClickListener(this);

        return view;
    }

    private void registerViews(View view) {
        llAddressAccount = view.findViewById(R.id.llAddressAccount);
        rlInformationAccount = view.findViewById(R.id.rlInformationAccount);
        rlSetting = view.findViewById(R.id.rlSetting);
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
            case R.id.rlSetting:
                accountFragment.replaceFragment(new EditSettingFragment());
                Objects.requireNonNull(getActivity()).findViewById(R.id.btnBottomNavigation).setVisibility(View.GONE);
                break;
        }
    }
}
