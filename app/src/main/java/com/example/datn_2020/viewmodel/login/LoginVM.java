package com.example.datn_2020.viewmodel.login;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.datn_2020.repository.HandleError;
import com.example.datn_2020.repository.LoginRepository;
import com.example.datn_2020.repository.model.LoginModel;
import com.example.datn_2020.repository.model.LoginResponse;
import com.example.datn_2020.repository.room.entity.User;
import com.example.datn_2020.repository.HandleSuccess;

public class LoginVM extends ViewModel {
    private LoginRepository loginRepository = new LoginRepository();
    private MutableLiveData<User> userData = new MutableLiveData<>();
    private MutableLiveData<String> errorData = new MutableLiveData<>();
    private MutableLiveData<Long> rowIdInsert = new MutableLiveData<>();
    private MutableLiveData<LoginResponse> mCheckLogin = new MutableLiveData<>();

//    SQLite

    public MutableLiveData<User> getUserData() {
        return this.userData;
    }

    public void checkUser() {
        loginRepository.getUser(new HandleSuccess<User>() {
            @Override
            public void handleSuccessResult(User result) {
                Log.i("StartActivity","Data:");
                userData.setValue(result);
            }
        }, new HandleError<String>() {
            @Override
            public void handleErrorResult(String error) {
                errorData.setValue(error);
            }
        });
    }

    public void insertUserData(int id, String mUsername, String mEmail) {
        User mUser = new User();
        mUser.id = id;
        mUser.username = mUsername;
        mUser.email = mEmail;
        loginRepository.insertUser(mUser, new HandleSuccess<Long>() {
            @Override
            public void handleSuccessResult(Long rowId) {
                rowIdInsert.setValue(rowId);
            }
        }, new HandleError<String>() {
            @Override
            public void handleErrorResult(String error) {
                errorData.setValue(error);
            }
        });
    }

    //Call api
    public MutableLiveData<LoginResponse> getmCheckLogin() {
        return mCheckLogin;
    }


    public void checkLogin(LoginModel mLoginModel) {
        loginRepository.checkLoginApi(mLoginModel, new HandleSuccess<LoginResponse>() {
            @Override
            public void handleSuccessResult(LoginResponse result) {
                Log.i("Login", "Result Login: " + result);
                mCheckLogin.setValue(result);
            }
        }, new HandleError<String>() {
            @Override
            public void handleErrorResult(String error) {

            }
        });
    }
}
