package com.example.datn_2020.view.guest;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.datn_2020.R;
import com.example.datn_2020.view.login.LoginActivity;

public class OtherGuestFragment extends Fragment {


    private String mTitle="";

    public OtherGuestFragment(String title){
        mTitle = title;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other_guest, container, false);

        Button btnLoginGuest = view.findViewById(R.id.btnLoginGuest);
        Toolbar tbTripGuest = view.findViewById(R.id.tbTripGuest);

        tbTripGuest.setTitle(mTitle);

        btnLoginGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(getActivity(), LoginActivity.class);
                startActivity(mIntent);
            }
        });

        return view;
    }
}
