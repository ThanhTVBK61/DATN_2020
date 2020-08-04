package com.example.datn_2020.viewmodel.login;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.datn_2020.repository.HandleError;
import com.example.datn_2020.repository.LoginRepository;
import com.example.datn_2020.repository.model.SignUpModel;
import com.example.datn_2020.repository.HandleSuccess;
import com.example.datn_2020.repository.model.ApiResponse;

public class SignUpVM extends ViewModel {
    private LoginRepository loginRepository = new LoginRepository();
    private MutableLiveData<Boolean> mCheckSignUp = new MutableLiveData<>();
    private MutableLiveData<String> errorData = new MutableLiveData<>();


    //Call api
    public MutableLiveData<Boolean> getmCheckSignUp() {
        return mCheckSignUp;
    }

    public void checkLogin(SignUpModel mSignUpModel) {
        loginRepository.checkSignUp(mSignUpModel, new HandleSuccess<ApiResponse>() {
            @Override
            public void handleSuccessResult(ApiResponse result) {
                Log.i("Login", "Result Login: " + result);
                mCheckSignUp.setValue(result.getIsSuccess());
            }
        }, new HandleError<String>() {
            @Override
            public void handleErrorResult(String err) {
                errorData.setValue(err);
            }
        });
    }
}
