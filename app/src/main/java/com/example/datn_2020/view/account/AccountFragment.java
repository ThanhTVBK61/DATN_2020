package com.example.datn_2020.view.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.datn_2020.R;


public class AccountFragment extends Fragment {

    private FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        fragmentManager = getChildFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flAccount,new MainAccountFragment()).addToBackStack(null).commit();

        return view;
    }

    void replaceFragment(Fragment mFragment){
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_right_to_left,R.anim.exit_right_to_left,
                        R.anim.enter_left_to_right,R.anim.exit_left_to_right)
                .replace(R.id.flAccount,mFragment).addToBackStack(null).commit();
    }

     void backStack(){
        fragmentManager.popBackStack();
    }
}
