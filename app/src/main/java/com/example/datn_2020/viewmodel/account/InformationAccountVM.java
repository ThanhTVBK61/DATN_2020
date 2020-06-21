package com.example.datn_2020.viewmodel.account;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.datn_2020.repository.model.InformationAccountModel;
import com.example.datn_2020.repository.model.InformationAccountResponse;
import com.example.datn_2020.repository.network.HandleResult;
import com.example.datn_2020.repository.network.InformationAccountRequest;

public class InformationAccountVM extends ViewModel {
    private MutableLiveData<InformationAccountModel> infoAccountResponse = new MutableLiveData<>();
    private InformationAccountRequest informationAccountRequest;

    public InformationAccountVM(){
        informationAccountRequest = new InformationAccountRequest();
    }

    public MutableLiveData<InformationAccountModel> getInfoAccountResponse() {
        return infoAccountResponse;
    }

    public void setInfoAccountResponse(InformationAccountModel infoAccountResponse) {
        this.infoAccountResponse.setValue(infoAccountResponse);
    }

    public void loadInfoAccount(){
        informationAccountRequest.getDataInformationAccountRequest(new HandleResult<InformationAccountResponse>() {
            @Override
            public void handleResponseResult(InformationAccountResponse results) {
                if(results.getIsSuccess())
                {
                    Log.i("Get Data Sever: ",results.toString());
                    infoAccountResponse.setValue(results.getInformationAccountModel(0));
                }else if(!results.getIsSuccess()){
                    Log.i("Get Data Sever: ","isSuccess: false");
                }else {
                    Log.i("Get Data Sever: ","Can not get data from api");
                }

            }
        });
    }
}
