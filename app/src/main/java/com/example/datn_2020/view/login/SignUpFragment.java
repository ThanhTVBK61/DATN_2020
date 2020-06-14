package com.example.datn_2020.view.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.datn_2020.MainActivity;
import com.example.datn_2020.R;

public class SignUpFragment extends Fragment implements View.OnClickListener {

    private ImageView ivBackSignUp;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        registerViews(view);

        ivBackSignUp.setOnClickListener(this);

        return view;
    }

    private void registerViews(View view) {
        ivBackSignUp = view.findViewById(R.id.ivBackSignUp);
    }

    @Override
    public void onClick(View v) {
        MainActivity mainActivity = (MainActivity) getActivity();
        switch (v.getId()){
            case R.id.ivBackSignUp:
                mainActivity.backHomeStack();
                break;
        }
    }
}
