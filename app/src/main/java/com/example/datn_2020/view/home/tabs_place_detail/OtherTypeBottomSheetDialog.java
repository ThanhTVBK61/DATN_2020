package com.example.datn_2020.view.home.tabs_place_detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.datn_2020.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class OtherTypeBottomSheetDialog extends BottomSheetDialogFragment {

    @Override
    public int getTheme() {
        return R.style.BottomSheet;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_dialog_other_type,container,false);


        return view;
    }
}
