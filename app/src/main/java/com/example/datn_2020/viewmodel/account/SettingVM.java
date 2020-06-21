package com.example.datn_2020.viewmodel.account;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.datn_2020.repository.room.ApiResponse;
import com.example.datn_2020.repository.model.ChangePasswordModel;
import com.example.datn_2020.repository.room.db.Db;
import com.example.datn_2020.repository.network.HandleResult;
import com.example.datn_2020.repository.network.account.ChangePassCallApi;

public class SettingVM extends ViewModel {

    private MutableLiveData<Boolean> response = new MutableLiveData<>();
    private ChangePassCallApi changePassCallApi;

    public void deleteUser(String username){
        Db.getDB().userDAO().deleteUser(username);
    }

    public void setChangePassCallApi(){
        changePassCallApi = new ChangePassCallApi();
    }

    public MutableLiveData<Boolean> getResponse() {
        return response;
    }

    public void changePassword(ChangePasswordModel mChangePasswordModel){
        changePassCallApi.changePassword(mChangePasswordModel, new HandleResult<ApiResponse>() {
            @Override
            public void handleResponseResult(ApiResponse result) {
                Log.i("Change password","response api:"+String.valueOf(result.getIsSuccess()));
                response.setValue(result.getIsSuccess());
            }
        });
    }
}
