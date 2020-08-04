package com.example.datn_2020.view.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.datn_2020.R;
import com.example.datn_2020.repository.room.entity.User;
import com.example.datn_2020.repository.network.DisposableManager;
import com.example.datn_2020.view.container.ContainerActivity;
import com.example.datn_2020.view.start.StartActivity;
import com.example.datn_2020.viewmodel.login.LoginVM;

public class LoginActivity extends AppCompatActivity {

    private final String TAG = "Login";

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        LoginVM loginVM = new ViewModelProvider(this).get(LoginVM.class);
//        loginVM.checkUser();
//        loginVM.getUserData().observe(this, new Observer<User>() {
//            @Override
//            public void onChanged(User mUserData) {
//                if (mUserData != null) {
//                    Log.i(TAG, "usernameRoom != null");
//                    CurrentUser.getInstance().id = mUserData.id;
//                    CurrentUser.getInstance().username = mUserData.username;
//                    CurrentUser.getInstance().email = mUserData.email;
//                    Intent mIntent = new Intent(LoginActivity.this, ContainerActivity.class);
//                    startActivity(mIntent);
//                    finish();
//                } else {
//                    CurrentUser.getInstance().username = null;
//                    CurrentUser.getInstance().email = null;
//                    CurrentUser.getInstance().id = -1;
//                    Log.i(TAG, "usernameRoom == null");
//                }
//            }
//        });

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_right_to_left,R.anim.exit_right_to_left,
                        R.anim.enter_left_to_right,R.anim.exit_left_to_right)
                .replace(R.id.flLoginSignUp,new LoginFragment()).commit();
    }

     public void replaceHomeFragment(Fragment mFragment){
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.enter_right_to_left,R.anim.exit_right_to_left,
                        R.anim.enter_left_to_right,R.anim.exit_left_to_right)
                .replace(R.id.flLoginSignUp,mFragment).addToBackStack(null).commit();
    }

    public void backHomeStack(){
        fragmentManager.popBackStack();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        DisposableManager.dispose();
    }
}
