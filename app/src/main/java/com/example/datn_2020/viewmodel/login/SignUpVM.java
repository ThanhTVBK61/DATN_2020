package com.example.datn_2020.viewmodel.login;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.datn_2020.repository.model.SignUpModel;
import com.example.datn_2020.repository.network.HandleResult;
import com.example.datn_2020.repository.network.login.SignUpCallApi;

public class SignUpVM extends ViewModel {
    private MutableLiveData<Boolean> mCheckSignUp = new MutableLiveData<>();
    private SignUpCallApi signUpCallApi;

    //Call api
    public MutableLiveData<Boolean> getmCheckSignUp() {
        return mCheckSignUp;
    }

    public void setLoginCallApi() {
        signUpCallApi = new SignUpCallApi();
        Log.i("Login","setLoginCallApi");
    }

    public void checkLogin(SignUpModel mSignUpModel) {
        signUpCallApi.checkSignUp(mSignUpModel, new HandleResult<Boolean>() {
            @Override
            public void handleResponseResult(Boolean result) {
                Log.i("Login", "Result Login: " + result);
                mCheckSignUp.setValue(result);
            }
        });
    }
}
