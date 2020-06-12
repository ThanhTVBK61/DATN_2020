package com.example.datn_2020.view.view_home.tabs_place_detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.datn_2020.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ContactBottomSheet extends BottomSheetDialogFragment {

    @Override
    public int getTheme() {
        return R.style.BottomSheet;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_contact,container,false);
        return view;
    }
}
