package com.example.datn_2020.view.login;

import android.content.Intent;
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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.datn_2020.R;
import com.example.datn_2020.repository.model.CurrentUser;
import com.example.datn_2020.repository.model.LoginDataResponse;
import com.example.datn_2020.repository.model.LoginModel;
import com.example.datn_2020.repository.model.LoginResponse;
import com.example.datn_2020.view.container.ContainerActivity;
import com.example.datn_2020.view.guest.GuestActivity;
import com.example.datn_2020.view.start.StartActivity;
import com.example.datn_2020.viewmodel.login.LoginVM;

public class LoginFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private final String TAG = "Login";

    private TextView tvCreateAccount;
    private TextView tvNotificationInput;
    private Button btnLogin;
    private LoginVM loginVM;
    private EditText etUsernameLogin;
    private CheckBox cbShowPassLogin;
    private EditText etPasswordLogin;
    private ImageButton ibCloseLogin;
    private LinearLayout llUsernameEmailLogin, llPasswordLogin;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        registerViews(view);

        loginVM = new ViewModelProvider(getActivity()).get(LoginVM.class);

        btnLogin.setOnClickListener(this);
        tvCreateAccount.setOnClickListener(this);
        ibCloseLogin.setOnClickListener(this);
        cbShowPassLogin.setOnCheckedChangeListener(this);

        return view;
    }

    private void registerViews(View view) {
        tvCreateAccount = view.findViewById(R.id.tvCreateAccount);
        ibCloseLogin = view.findViewById(R.id.ibCloseLogin);
        cbShowPassLogin = view.findViewById(R.id.cbShowPassLogin);
        btnLogin = view.findViewById(R.id.btnLogin);
        etUsernameLogin = view.findViewById(R.id.etUsernameLogin);
        etPasswordLogin = view.findViewById(R.id.etPasswordLogin);
        llUsernameEmailLogin = view.findViewById(R.id.llUsernameEmailLogin);
        llPasswordLogin = view.findViewById(R.id.llPasswordLogin);
        tvNotificationInput = view.findViewById(R.id.tvNotificationInput);
    }

    @Override
    public void onClick(View v) {
        LoginActivity loginActivity = (LoginActivity) getActivity();
        switch (v.getId()) {
            case R.id.tvCreateAccount:
                loginActivity.replaceHomeFragment(new SignUpFragment());
                break;
            case R.id.ibCloseLogin:
                Intent mIntent = new Intent(getActivity(), GuestActivity.class);
                startActivity(mIntent);
                getActivity().finish();
                break;
            case R.id.btnLogin:
                String username = etUsernameLogin.getText().toString();
                String password = etPasswordLogin.getText().toString();
                Log.i(TAG, "Username:" + username);
                Log.i(TAG, "Password:" + password);
                int check = 0;
                if (!checkData(username) || username.matches("")) {
                    check = -1;
                    llUsernameEmailLogin.setBackground(getResources().getDrawable(R.drawable.shape_edit_information_error));
                } else {
                    llUsernameEmailLogin.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_login));
                }
                if (!checkData(password) || password.matches("")) {
                    check = -1;
                    llPasswordLogin.setBackground(getResources().getDrawable(R.drawable.shape_edit_information_error));
                } else {
                    llPasswordLogin.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_login));
                }
                if (check == 0) {
                    LoginModel mLoginModel = new LoginModel(username, password);
                    loginVM.checkLogin(mLoginModel);
                    loginVM.getmCheckLogin().observe(getViewLifecycleOwner(), new Observer<LoginResponse>() {
                        @Override
                        public void onChanged(LoginResponse loginResponse) {
                            if (loginResponse.getSuccess()) {
                                LoginDataResponse mLoginDataResponse = loginResponse.getmLoginDataResponse();
                                //Luu username, email vao trong sqlite
                                loginVM.insertUserData(mLoginDataResponse.getIdUser(),mLoginDataResponse.getUsername(), mLoginDataResponse.getEmail());
                                //set gia tri cho 2 hang so trong LoginActivity
                                CurrentUser.getInstance().id = mLoginDataResponse.getIdUser();
                                CurrentUser.getInstance().username = mLoginDataResponse.getUsername();
                                CurrentUser.getInstance().email = mLoginDataResponse.getEmail();
                                //Chuyen sang activity khac
                                Intent mIntent = new Intent(getActivity(), ContainerActivity.class);
                                startActivity(mIntent);
                                getActivity().finish();
                            } else {
                                tvNotificationInput.setText("*Sai tên đăng nhập hoặc mật khẩu");
                                tvNotificationInput.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                } else {
                    tvNotificationInput.setVisibility(View.VISIBLE);
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
        if(isChecked){
            etPasswordLogin.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }else {
            etPasswordLogin.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }
}
