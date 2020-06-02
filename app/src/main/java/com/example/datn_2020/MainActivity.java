package com.example.datn_2020;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.datn_2020.view.login.LoginFragment;
import com.example.datn_2020.view.login.SignUpFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvLogIn;
    TextView tvSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvLogIn = findViewById(R.id.tvLogIn);
        tvSignUp =findViewById(R.id.tvSignUp);

        ChangeFragmnet("login");

        tvLogIn.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
    }

    private void ChangeFragmnet(String fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment newFragment = null;

        if(fragment.equals("login")){
            newFragment = LoginFragment.newInstance();
            fragmentTransaction.replace(R.id.flContainerLogin,newFragment);
            fragmentTransaction.commit();
        }else if(fragment.equals("signup")){
            newFragment = SignUpFragment.newInstance();
            fragmentTransaction.replace(R.id.flContainerLogin,newFragment);
            fragmentTransaction.commit();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvLogIn:
                //Bat su kien khi text la mau xam, con mau den la dang click roi
                if(tvLogIn.getTextSize() == 20)
                    break;
                tvLogIn.setTextSize(20);
                tvLogIn.setTextColor(getResources().getColor(R.color.colorBlueDark));
                tvSignUp.setTextSize(17);
                tvSignUp.setTextColor(getResources().getColor(R.color.colorBlueLight));

                ChangeFragmnet("login");
                break;
            case R.id.tvSignUp:
                //Bat su kien khi text la mau xam, con mau den la dang click roi
                if(tvSignUp.getTextSize() == 20)
                    break;
                tvSignUp.setTextSize(20);
                tvSignUp.setTextColor(getResources().getColor(R.color.colorBlueDark));
                tvLogIn.setTextSize(17);
                tvLogIn.setTextColor(getResources().getColor(R.color.colorBlueLight));

                ChangeFragmnet("signup");
                break;
        }
    }
}
