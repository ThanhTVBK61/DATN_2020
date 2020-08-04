package com.example.datn_2020.view.account;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.datn_2020.R;
import com.example.datn_2020.repository.model.CurrentUser;
import com.example.datn_2020.repository.room.entity.User;
import com.example.datn_2020.view.login.LoginActivity;
import com.example.datn_2020.view.start.StartActivity;
import com.example.datn_2020.viewmodel.ContainerVM;

import java.util.Objects;


public class EditSettingFragment extends Fragment implements View.OnClickListener {

    private LinearLayout llLogout;
    private LinearLayout llChangePasswordSetting;
    private ContainerVM settingVM;
    private Toolbar tbSetting;
    private FragmentActivity fragmentActivity;
    private Context mContext;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_setting, container, false);

        if (getActivity() != null) {
            fragmentActivity = getActivity();
        }

        llLogout = view.findViewById(R.id.llLogout);
        llChangePasswordSetting = view.findViewById(R.id.llChangePasswordSetting);
        tbSetting = view.findViewById(R.id.tbSetting);

        registerToolbar();

        settingVM = new ViewModelProvider(fragmentActivity).get(ContainerVM.class);

        llLogout.setOnClickListener(this);
        llChangePasswordSetting.setOnClickListener(this);
        return view;
    }

    private void registerToolbar() {
        tbSetting.setNavigationIcon(R.drawable.ic_back);
        tbSetting.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountFragment accountFragment = (AccountFragment) getParentFragment();
                accountFragment.backStack();
                Objects.requireNonNull(getActivity()).findViewById(R.id.btnBottomNavigation).setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llLogout:
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                alertDialogBuilder.setTitle("Đăng xuất");
                alertDialogBuilder.setMessage("Bạn có chắc chắn thoát?");
                alertDialogBuilder.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("Setting", CurrentUser.getInstance().username + CurrentUser.getInstance().username);
                        settingVM.deleteUser(CurrentUser.getInstance().id);
                        User user = new User();
                        user.id = -1;
                        CurrentUser.setInstance(user);
                        Intent mIntent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(mIntent);
                        getActivity().finish();
                    }
                });
                alertDialogBuilder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorGray700));
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorBlue700));
                break;
            case R.id.llChangePasswordSetting:
                AccountFragment accountFragment = (AccountFragment) getParentFragment();
                accountFragment.replaceFragment(new ChangePasswordFragment());
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
