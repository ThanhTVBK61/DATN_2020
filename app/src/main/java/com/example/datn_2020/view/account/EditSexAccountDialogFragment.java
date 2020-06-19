package com.example.datn_2020.view.account;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.datn_2020.R;

public class EditSexAccountDialogFragment extends DialogFragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private RadioButton rbMan,rbWoman;
    private TextView tvSelect,tvNotification;
    private SelectSexDialogInterface selectSexDialog;
    private ImageView closeDialog;

    public void setSelectSexDialog(SelectSexDialogInterface selectSexDialog) {
        this.selectSexDialog = selectSexDialog;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_edit_sex_account,container,false);
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.shape_popup_sex);

        registerViews(view);

        rbMan.setOnCheckedChangeListener(this);
        rbWoman.setOnCheckedChangeListener(this);

        tvSelect.setOnClickListener(this);
        closeDialog.setOnClickListener(this);

        return view;
    }

    private void registerViews(View view) {
        rbMan = view.findViewById(R.id.rbMan);
        rbWoman = view.findViewById(R.id.rbWoman);
        tvSelect = view.findViewById(R.id.tvSelectSex);
        tvNotification = view.findViewById(R.id.tvNotificationSexAccount);
        closeDialog = view.findViewById(R.id.ivCloseSexDialog);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        selectSexDialog = null;
        Log.i("Dialog fragment: ","Detach");
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            tvNotification.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivCloseSexDialog:
                getDialog().dismiss();
                break;
            case R.id.tvSelectSex:
                if(rbMan.isChecked()){
                    selectSexDialog.selectSex("Nam");
                    getDialog().dismiss();
                }else if(rbWoman.isChecked()){
                    selectSexDialog.selectSex("Nữ");
                    getDialog().dismiss();
                }else {
                    tvNotification.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    public interface SelectSexDialogInterface {
        void selectSex(String sex);
    }
}
