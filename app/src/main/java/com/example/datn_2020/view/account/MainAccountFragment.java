package com.example.datn_2020.view.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.datn_2020.R;
import com.example.datn_2020.repository.model.CurrentUser;
import com.example.datn_2020.view.start.StartActivity;
import com.example.datn_2020.viewmodel.ContainerVM;

import java.util.Objects;


public class MainAccountFragment extends Fragment implements View.OnClickListener {

    private LinearLayout llSetting;
    private LinearLayout llAddressAccount;
    private RelativeLayout rlInformationAccount;
    private LinearLayout llFavouritePlaceAccount;
    private TextView tvEmailAccount;
    private TextView tvNameAccount;

    private ContainerVM accountVM;
    private FragmentActivity fragmentActivity;

    private AccountFragment accountFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_account, container, false);
        registerViews(view);

        tvEmailAccount.setText(CurrentUser.getInstance().email);
        tvNameAccount.setText(CurrentUser.getInstance().username);
        accountFragment = (AccountFragment) getParentFragment();

        if (getActivity() != null) {
            fragmentActivity = getActivity();
        }

        accountVM = new ViewModelProvider(fragmentActivity).get(ContainerVM.class);

        rlInformationAccount.setOnClickListener(this);
        llSetting.setOnClickListener(this);

        llAddressAccount.setOnClickListener(this);
        llFavouritePlaceAccount.setOnClickListener(this);

        return view;
    }

    private void registerViews(View view) {
        rlInformationAccount = view.findViewById(R.id.rlInformationAccount);
        llSetting = view.findViewById(R.id.llSetting);
        tvEmailAccount = view.findViewById(R.id.tvEmailAccount);
        tvNameAccount = view.findViewById(R.id.tvNameAccount);

        llAddressAccount = view.findViewById(R.id.llAddressAccount);
        llFavouritePlaceAccount = view.findViewById(R.id.llFavouritePlaceAccount);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.llAddressAccount:
                accountFragment.replaceFragment(new AddressAccountFragment());
                Objects.requireNonNull(getActivity()).findViewById(R.id.btnBottomNavigation).setVisibility(View.GONE);
                break;
            case R.id.rlInformationAccount:
                accountFragment.replaceFragment(new InformationAccountFragment());
                Objects.requireNonNull(getActivity()).findViewById(R.id.btnBottomNavigation).setVisibility(View.GONE);
                break;
            case R.id.llSetting:
                accountFragment.replaceFragment(new EditSettingFragment());
                Objects.requireNonNull(getActivity()).findViewById(R.id.btnBottomNavigation).setVisibility(View.GONE);
                break;
            case R.id.llFavouritePlaceAccount:
                accountFragment.replaceFragment(new ListFavouritePlaceAccountFragment());
                Objects.requireNonNull(getActivity()).findViewById(R.id.btnBottomNavigation).setVisibility(View.GONE);
                break;
        }
    }
}
