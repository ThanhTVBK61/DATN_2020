package com.example.datn_2020.view.trip.trip_detail;

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

public class AddMemberDialogFragment extends DialogFragment implements View.OnClickListener {

    private SelectAddMember mSelectAddMember;
    private EditText etNameNewUser;
    private TextView tvSelectAddMember;

    void setSelectAddMember(SelectAddMember selectSexDialog) {
        this.mSelectAddMember = selectSexDialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_add_member,container,false);
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.shape_popup_sex);

        tvSelectAddMember = view.findViewById(R.id.tvSelectAddMember);
        etNameNewUser = view.findViewById(R.id.etNameNewUser);

        tvSelectAddMember.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvSelectAddMember:
                String username = etNameNewUser.getText().toString();
                mSelectAddMember.selectAdd(username);
                getDialog().dismiss();
                break;
        }
    }

    public interface SelectAddMember {
        void selectAdd(String username);
    }
}
