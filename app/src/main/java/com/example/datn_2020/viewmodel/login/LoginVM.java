package com.example.datn_2020.viewmodel.login;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.datn_2020.model.LoginModel;
import com.example.datn_2020.model.LoginResponse;
import com.example.datn_2020.model.db.Db;
import com.example.datn_2020.model.entity.User;
import com.example.datn_2020.network.HandleResult;
import com.example.datn_2020.network.login.LoginCallApi;

public class LoginVM extends ViewModel {
    private LiveData<User> userData;
    private MutableLiveData<LoginResponse> mCheckLogin = new MutableLiveData<>();
    private LoginCallApi loginCallApi;

    public LoginVM() {
        this.userData = Db.getDB().userDAO().getUser();
    }

    //SQLite
    public LiveData<User> getUserData() {
        return this.userData;
    }

    public void insertUserData(String mUsername, String mEmail) {
        User mUser = new User();
        mUser.username = mUsername;
        mUser.email = mEmail;
        Db.getDB().userDAO().insertUser(mUser);
    }

    //Call api
    public MutableLiveData<LoginResponse> getmCheckLogin() {
        return mCheckLogin;
    }

    public void setLoginCallApi() {
        loginCallApi = new LoginCallApi();
        Log.i("Login","setLoginCallApi");
    }

    public void checkLogin(LoginModel mLoginModel) {
        loginCallApi.checkLogin(mLoginModel, new HandleResult<LoginResponse>() {
            @Override
            public void handleResponseResult(LoginResponse result) {
                Log.i("Login", "Result Login: " + result);
                mCheckLogin.setValue(result);
            }
        });
    }
}
