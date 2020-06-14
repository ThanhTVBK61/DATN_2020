package com.example.datn_2020.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.datn_2020.MainActivity;
import com.example.datn_2020.R;
import com.example.datn_2020.view.container.ContainerActivity;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private TextView tvCreateAccount;
    private Button btnLogin;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        registerViews(view);

        tvCreateAccount.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        return view;
    }

    private void registerViews(View view) {
        tvCreateAccount= view.findViewById(R.id.tvCreateAccount);
        btnLogin= view.findViewById(R.id.btnLogin);
    }

    @Override
    public void onClick(View v) {
        MainActivity mainActivity = (MainActivity) getActivity();
        switch (v.getId()){
            case R.id.tvCreateAccount:
                mainActivity.replaceHomeFragment(new SignUpFragment());
                break;
            case R.id.btnLogin:
                Intent mIntent = new Intent(getActivity(), ContainerActivity.class);
                startActivity(mIntent);
                break;
        }
    }
}
