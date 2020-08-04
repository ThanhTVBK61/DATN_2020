package com.example.datn_2020.view.account;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.datn_2020.R;
import com.example.datn_2020.repository.model.ChangePasswordModel;
import com.example.datn_2020.repository.model.CurrentUser;
import com.example.datn_2020.view.start.StartActivity;
import com.example.datn_2020.viewmodel.ContainerVM;

public class ChangePasswordFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private TextView tvUsernameChangePassword;
    private EditText etOldPassword;
    private EditText etNewPassword;
    private CheckBox cbNewPassword;
    private CheckBox cbOldPassword;
    private Toolbar tbChangePassword;
    private Button btnChangePassword;
    private RelativeLayout rlOldPassword, rlNewPassword;
    private TextView tvNotificationChangePassword;

    private ContainerVM settingVM;
    private Context mContext;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        registerViews(view);
        registerToolbar();

        settingVM = new ViewModelProvider(getActivity()).get(ContainerVM.class);

        tvUsernameChangePassword.setText(CurrentUser.getInstance().username);
        btnChangePassword.setOnClickListener(this);

        cbNewPassword.setOnCheckedChangeListener(this);
        cbOldPassword.setOnCheckedChangeListener(this);

        return view;
    }

    private void registerViews(View view) {
        //Checkbox
        cbNewPassword = view.findViewById(R.id.cbNewPassword);
        cbOldPassword = view.findViewById(R.id.cbOldPassword);
        //Relative Layout
        rlOldPassword = view.findViewById(R.id.rlOldPassword);
        rlNewPassword = view.findViewById(R.id.rlNewPassword);
        //Edittext
        etOldPassword = view.findViewById(R.id.etOldPassword);
        etNewPassword = view.findViewById(R.id.etNewPassword);
        //TextView
        tvUsernameChangePassword = view.findViewById(R.id.tvUsernameChangePassword);
        tvNotificationChangePassword = view.findViewById(R.id.tvNotificationChangePassword);
        //Toolbar vs button
        tbChangePassword = view.findViewById(R.id.tbChangePassword);
        btnChangePassword = view.findViewById(R.id.btnChangePassword);
    }

    private void registerToolbar() {
        tbChangePassword.setNavigationIcon(R.drawable.ic_back);
        tbChangePassword.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountFragment accountFragment = (AccountFragment) getParentFragment();
                accountFragment.backStack();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnChangePassword:
                String newPass = etNewPassword.getText().toString();
                String oldPass = etOldPassword.getText().toString();

                String notification = "Định dạng mật khẩu không đúng*";
                //An thong bao truoc di
                tvNotificationChangePassword.setText(notification);
                tvNotificationChangePassword.setVisibility(View.GONE);
                //Dat lai background cho relative layout
                rlOldPassword.setBackgroundResource(R.drawable.shape_edit_information);
                rlNewPassword.setBackgroundResource(R.drawable.shape_edit_information);

                int check = 0;
                if (!checkData(newPass) || newPass.matches("")) {
                    check = -1;
                    rlNewPassword.setBackgroundResource(R.drawable.shape_edit_information_error);
                }

                if (!checkData(oldPass) || oldPass.matches("")) {
                    check = -1;
                    rlOldPassword.setBackgroundResource(R.drawable.shape_edit_information_error);
                }

                if(newPass.equals(oldPass)){
                    check = -1;
                    rlOldPassword.setBackgroundResource(R.drawable.shape_edit_information_error);
                    rlNewPassword.setBackgroundResource(R.drawable.shape_edit_information_error);
                    notification = "Mật khẩu bị trùng*";
                }

                if (check == 0) {
                    ChangePasswordModel changePasswordModel = new ChangePasswordModel(CurrentUser.getInstance().username, oldPass, newPass);
                    settingVM.changePassword(changePasswordModel);
                    settingVM.getResponse().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean result) {
                            Log.i("Change Password", String.valueOf(result));
                            if (result) {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                                alertDialogBuilder.setTitle("Đổi mật khẩu");
                                alertDialogBuilder.setMessage("Đổi mật khẩu thành công!");
                                alertDialogBuilder.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        AccountFragment accountFragment = (AccountFragment) getParentFragment();
                                        accountFragment.backStack();
                                    }
                                });
                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorBlue700));
                                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorBlue700));

                            } else{
                                tvNotificationChangePassword.setText("Nhập sai mật khẩu*. Nhập lại");
                                tvNotificationChangePassword.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                } else {
                    tvNotificationChangePassword.setText(notification);
                    tvNotificationChangePassword.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    private boolean checkData(String data) {
        if (data == null) {
            return false;
        }
        String filter = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz123456789";
        for (char c : data.toCharArray()) {
            if (filter.indexOf(c) < 0)
                return false;
        }
        return true;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.cbOldPassword:
                Toast.makeText(getActivity(), "checked", Toast.LENGTH_SHORT).show();
                if(isChecked){
                    etOldPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    etOldPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
            case R.id.cbNewPassword:
                Toast.makeText(getActivity(), "checked", Toast.LENGTH_SHORT).show();
                if(isChecked){
                    etNewPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    etNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                break;
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mContext = null;
    }
}
