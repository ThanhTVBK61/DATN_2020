package com.example.datn_2020.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.datn_2020.R;
import com.example.datn_2020.repository.model.LoginDataResponse;
import com.example.datn_2020.repository.model.LoginModel;
import com.example.datn_2020.repository.model.LoginResponse;
import com.example.datn_2020.view.container.ContainerActivity;
import com.example.datn_2020.viewmodel.login.LoginVM;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private final String TAG = "Login";

    private TextView tvCreateAccount;
    private TextView tvNotificationInput;
    private Button btnLogin;
    private LoginVM loginVM;
    private EditText etUsernameLogin;
    private EditText etPasswordLogin;
    private LinearLayout llUsernameEmailLogin, llPasswordLogin;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        registerViews(view);

        loginVM = new ViewModelProvider(getActivity()).get(LoginVM.class);
        loginVM.setLoginCallApi();
        btnLogin.setOnClickListener(this);
        tvCreateAccount.setOnClickListener(this);
        return view;
    }

    private void registerViews(View view) {
        tvCreateAccount = view.findViewById(R.id.tvCreateAccount);
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
                    loginVM.getmCheckLogin().observe(getActivity(), new Observer<LoginResponse>() {
                        @Override
                        public void onChanged(LoginResponse loginResponse) {
                            if (loginResponse.getSuccess()) {
                                LoginDataResponse mLoginDataResponse = loginResponse.getmLoginDataResponse();
                                //Luu username, email vao trong sqlite
                                loginVM.insertUserData(mLoginDataResponse.getUsername(), mLoginDataResponse.getEmail());
                                //set gia tri cho 2 hang so trong LoginActivity
                                LoginActivity.USERNAME = mLoginDataResponse.getUsername();
                                LoginActivity.EMAIL = mLoginDataResponse.getEmail();
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
}
