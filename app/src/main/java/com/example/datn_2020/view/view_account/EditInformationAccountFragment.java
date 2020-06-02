package com.example.datn_2020.view.view_account;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.datn_2020.R;
import com.example.datn_2020.model.InformationAccountModel;
import com.example.datn_2020.viewmodel.viewmodel_account.InformationAccountVM;

public class EditInformationAccountFragment extends Fragment implements View.OnClickListener, EditSexAccountDialogFragment.SelectSexDialogInterface, View.OnFocusChangeListener {

    private Toolbar editToolbar;
    private RelativeLayout rlEditName,rlEditDatetime,rlEditSex,rlEditDescription;
    private ImageView ivClearName, ivClearDescription;
    private TextView tvSave;

    private EditText etName,etDescription;
    private TextView tvBirthday,tvSex;
    private TextView tvNotificationNameAccount;

    private RelativeLayout rlActive;
    private ImageView ivActive;

    private InformationAccountVM informationAccountVM ;
    private InformationAccountModel newInformationAccountModel = new InformationAccountModel();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_information_account, container,false);

        registerViews(view);

        // Gọi tác với View Model
        informationAccountVM = new ViewModelProvider(getActivity()).get(InformationAccountVM.class);
        // Thêm các giá trị mặc đinh
        registerDataInformation();
        registerButtonSave();
        registerButtonBack();

        rlActive = rlEditName;
        ivActive = ivClearName;

        rlEditName.setOnClickListener(this);
        rlEditDatetime.setOnClickListener(this);
        rlEditSex.setOnClickListener(this);
        rlEditDescription.setOnClickListener(this);
        etName.setOnFocusChangeListener(this);
        etDescription.setOnFocusChangeListener(this);
        tvSave.setOnClickListener(this);

        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence input, int start, int before, int count) {
                if(input.length() == 0){
                    rlEditName.setBackgroundResource(R.drawable.shape_edit_information_error);
                    tvNotificationNameAccount.setVisibility(View.VISIBLE);
                    ivClearName.setVisibility(View.GONE);
                }else {
                    rlEditName.setBackgroundResource(R.drawable.shape_edit_information_pressed);
                    tvNotificationNameAccount.setVisibility(View.GONE);
                    ivClearName.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence input, int start, int before, int count) {
                if(input.length() == 0){
                    ivClearDescription.setVisibility(View.GONE);
                }else {
                    ivClearDescription.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ivClearName.setOnClickListener(this);
        ivClearDescription.setOnClickListener(this);
        return view;
    }

    private void registerViews(View view) {
        editToolbar = view.findViewById(R.id.tbEditInformationAccount);
        rlEditName = view.findViewById(R.id.rlEditNameInformation);
        rlEditDatetime = view.findViewById(R.id.rlEditDatetimeInformation);
        rlEditSex = view.findViewById(R.id.rlEditSexInformation);
        rlEditDescription = view.findViewById(R.id.rlEditDescriptionInformation);
        etName = view.findViewById(R.id.etEditNameInformation);
        tvBirthday = view.findViewById(R.id.tvEditDatetimeInformation);
        tvSex = view.findViewById(R.id.tvEditSexInformation);
        etDescription = view.findViewById(R.id.etEditDescriptionInformation);
        ivClearName = view.findViewById(R.id.ivEditNameInformation);
        ivClearDescription = view.findViewById(R.id.ivEditDescriptionInformation);
        tvSave = view.findViewById(R.id.tvSaveInformationAccount);
        tvNotificationNameAccount = view.findViewById(R.id.tvNotificationNameAccount);
    }

    private void registerButtonBack() {
        editToolbar.setNavigationIcon(R.drawable.ic_back);
        editToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountFragment accountFragment = (AccountFragment) getParentFragment();
                accountFragment.backStack();
            }
        });
    }

    private void registerButtonSave() {
        tvSave.setTextColor(getResources().getColor(R.color.colorGray));
    }

    private void registerDataInformation() {
        informationAccountVM.getInfoAccountResponse().observe(getActivity(), new Observer<InformationAccountModel>() {
            @Override
            public void onChanged(InformationAccountModel informationAccountModel) {

                //Vi se add ten mac dinh vao
                etName.setText(informationAccountModel.getName());

                if(informationAccountModel.getDatetime().equals(" ")) {
                    tvBirthday.setText("Thêm ngày sinh");
                    tvBirthday.setTextColor(getResources().getColor(R.color.colorGray));
                }else {
                    tvBirthday.setText(informationAccountModel.getDatetime());
                }

                if(informationAccountModel.getSex() == 2) {
                    tvSex.setText("Thêm giới tính");
                    tvSex.setTextColor(getResources().getColor(R.color.colorGray));
                }else if(informationAccountModel.getSex() == 0){
                    tvSex.setText("Nữ");
                }else{
                    tvSex.setText("Nam");
                }

                if(informationAccountModel.getDescription().equals(" ")) {
                    etDescription.setHint("Thêm mô tả về bản thân");
                }else {
                    etDescription.setText(informationAccountModel.getDescription());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rlEditNameInformation:
                etName.requestFocus();
                etName.setSelection(etName.getText().length());
                break;
            case R.id.rlEditDescriptionInformation:
                etDescription.requestFocus();
                etDescription.setSelection(etDescription.getText().length());
                break;

            case R.id.rlEditDatetimeInformation:
                if(rlActive != rlEditName || etName.getText().length() != 0){
                    rlActive.setBackgroundResource(R.drawable.shape_edit_information);
                }else {
                    rlActive.setBackgroundResource(R.drawable.shape_edit_information_error);
                }
                rlActive = rlEditDatetime;
                rlActive.setBackgroundResource(R.drawable.shape_edit_information_pressed);

                break;
            case R.id.rlEditSexInformation:
                if(rlActive != rlEditName || etName.getText().length() != 0){
                    rlActive.setBackgroundResource(R.drawable.shape_edit_information);
                }else {
                    rlActive.setBackgroundResource(R.drawable.shape_edit_information_error);
                }
                rlActive = rlEditSex;
                rlActive.setBackgroundResource(R.drawable.shape_edit_information_pressed);

                EditSexAccountDialogFragment editSexAccountDialogFragment = new EditSexAccountDialogFragment();
                editSexAccountDialogFragment.setSelectSexDialog(this);
                editSexAccountDialogFragment.show(getChildFragmentManager(),null);
                break;

            case R.id.ivEditNameInformation:
                etName.getText().clear();
                etName.setHint("Nhập họ tên");
                break;

            case R.id.ivEditDescriptionInformation:
                etDescription.getText().clear();
                etDescription.setHint("Thêm mô tả về bản thân");
                break;

            case R.id.tvSaveInformationAccount:

                break;
        }
    }

    @Override
    public void selectSex(String sex) {
        Log.i("Callback ",sex);
        tvSave.setTextColor(getResources().getColor(R.color.colorBlue700));
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()){
            case R.id.etEditNameInformation:
                if(hasFocus) {
                    rlActive.setBackgroundResource(R.drawable.shape_edit_information);
                    rlActive = rlEditName;
                    ivActive.setVisibility(View.GONE);
                    ivActive = ivClearName;
                    if(etName.getText().length() == 0){
                        rlActive.setBackgroundResource(R.drawable.shape_edit_information_error);
                        ivActive.setVisibility(View.GONE);
                    }else {
                        rlActive.setBackgroundResource(R.drawable.shape_edit_information_pressed);
                        ivActive.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case R.id.etEditDescriptionInformation:
                if(hasFocus) {
                    if(rlActive != rlEditName || etName.getText().length() != 0){
                        rlActive.setBackgroundResource(R.drawable.shape_edit_information);
                    }else {
                        rlActive.setBackgroundResource(R.drawable.shape_edit_information_error);
                    }
                    rlActive = rlEditDescription;
                    rlActive.setBackgroundResource(R.drawable.shape_edit_information_pressed);

                    ivActive.setVisibility(View.GONE);
                    ivActive = ivClearDescription;
                    if (etDescription.getText().length() == 0){
                        ivActive.setVisibility(View.GONE);
                    }else {
                        ivActive.setVisibility(View.VISIBLE);
                    }

                }
                break;
        }
    }
}
