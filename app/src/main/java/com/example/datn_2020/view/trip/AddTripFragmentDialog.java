package com.example.datn_2020.view.trip;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.datn_2020.R;

public class AddTripFragmentDialog extends DialogFragment implements View.OnClickListener {

    private SelectAddDialogInterface selectAddDialogInterface;
    private EditText etAddNameTrip;
    private TextView tvSelectSex;

    void setAddTripFragmentDialog(SelectAddDialogInterface selectAddDialogInterface) {
        this.selectAddDialogInterface = selectAddDialogInterface;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_add_trip,container,false);
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.shape_popup_sex);

        etAddNameTrip = view.findViewById(R.id.etAddNameTrip);
        tvSelectSex = view.findViewById(R.id.tvSelectSex);

        tvSelectSex.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvSelectSex:
                String name = etAddNameTrip.getText().toString();
                selectAddDialogInterface.selectAddTrip(name);
                getDialog().dismiss();
                break;
        }
    }

    public interface SelectAddDialogInterface {
        void selectAddTrip(String name);
    }
}
