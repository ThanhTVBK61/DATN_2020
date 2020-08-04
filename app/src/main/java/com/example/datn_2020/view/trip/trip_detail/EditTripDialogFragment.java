package com.example.datn_2020.view.trip.trip_detail;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.datn_2020.R;

public class EditTripDialogFragment extends DialogFragment implements View.OnClickListener {

    private SelectEditTrip mSelectEditTrip;
    private EditText etNameTrip;
    private EditText etDescriptionTrip;
    private TextView tvSaveEditTrip;
    private TextView tvCancelEditTrip;

    void setSelectEditTrip(SelectEditTrip selectSexDialog) {
        this.mSelectEditTrip = selectSexDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_edit_trip,container,false);
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.shape_popup_sex);

        registerViews(view);

        Bundle mBundle = getArguments();
        if(mBundle!=null){
            String nameTrip = mBundle.getString("name");
            etNameTrip.setText(nameTrip);
            etDescriptionTrip.setText(mBundle.getString("description"));
        }

        etNameTrip.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence input, int start, int before, int count) {
                if (input.length() == 0) {
                    tvSaveEditTrip.setTextColor(tvSaveEditTrip.getResources().getColor(R.color.colorGray700));
                } else {
                    tvSaveEditTrip.setTextColor(tvSaveEditTrip.getResources().getColor(R.color.colorBlue700));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etDescriptionTrip.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence input, int start, int before, int count) {
                if (input.length() == 0) {
                    tvSaveEditTrip.setTextColor(tvSaveEditTrip.getResources().getColor(R.color.colorGray700));
                } else {
                    tvSaveEditTrip.setTextColor(tvSaveEditTrip.getResources().getColor(R.color.colorBlue700));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tvCancelEditTrip.setOnClickListener(this);
        tvSaveEditTrip.setOnClickListener(this);

        return view;
    }

    private void registerViews(View view) {
        etNameTrip = view.findViewById(R.id.etNameTrip);
        etDescriptionTrip = view.findViewById(R.id.etDescriptionTrip);
        tvSaveEditTrip = view.findViewById(R.id.tvSaveEditTrip);
        tvCancelEditTrip = view.findViewById(R.id.tvCancelEditTrip);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvCancelEditTrip:
                this.dismiss();
                break;
            case R.id.tvSaveEditTrip:
                if(tvSaveEditTrip.getCurrentTextColor() == tvSaveEditTrip.getResources().getColor(R.color.colorBlue700)){
                    String newName = etNameTrip.getText().toString();
                    String description = etDescriptionTrip.getText().toString();
                    mSelectEditTrip.selectEdit(newName,description);
                    this.dismiss();
                }
                break;

        }
    }

    public interface SelectEditTrip {
        void selectEdit(String nameTrip,String description);
    }
}
