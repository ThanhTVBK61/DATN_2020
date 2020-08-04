package com.example.datn_2020.view.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.datn_2020.R;
import com.example.datn_2020.repository.model.CurrentUser;
import com.example.datn_2020.repository.model.InformationAccountModel;
import com.example.datn_2020.view.start.StartActivity;
import com.example.datn_2020.viewmodel.ContainerVM;

import java.util.Objects;

public class InformationAccountFragment extends Fragment {

    private ContainerVM informationAccountVM;
    private Toolbar toolbar;
    private TextView editInformationAccount;
    private TextView tvNameInformation,tvDatetimeInformation,tvSexInformation,tvDescriptionInformation;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information_account,container,false);

        registerViews(view);
        registerToolbar();

        //Gọi view model
        informationAccountVM = new ViewModelProvider(getActivity()).get(ContainerVM.class);
        //Load dữ liệu từ sever
        informationAccountVM.loadInfoAccount(CurrentUser.getInstance().id);
        registerDataViews();

        editInformationAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountFragment accountFragment = (AccountFragment) getParentFragment();
                accountFragment.replaceFragment(new EditInformationAccountFragment());
            }
        });

        return view;
    }

    private void registerViews(View view) {
        toolbar = view.findViewById(R.id.tbInformationAccount);
        editInformationAccount = view.findViewById(R.id.tvEditInformationAccount);
        tvNameInformation = view.findViewById(R.id.tvNameInformationAccount);
        tvDatetimeInformation = view.findViewById(R.id.tvDatetimeInformation);
        tvSexInformation = view.findViewById(R.id.tvSex);
        tvDescriptionInformation = view.findViewById(R.id.tvDescriptionInformation);
    }

    private void registerToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountFragment accountFragment = (AccountFragment) getParentFragment();
                accountFragment.backStack();
                Objects.requireNonNull(getActivity()).findViewById(R.id.btnBottomNavigation).setVisibility(View.VISIBLE);
            }
        });
    }

    private void registerDataViews() {
        informationAccountVM.getInfoAccountResponse().observe(getViewLifecycleOwner(), new Observer<InformationAccountModel>() {

            @Override
            public void onChanged(InformationAccountModel informationAccountModel) {

                //Minh sẽ đặt tên mặc định cho user
                tvNameInformation.setText(informationAccountModel.getName());

                if(informationAccountModel.getDatetime().equals(" ")) {
                    tvDatetimeInformation.setText("Thêm thông tin");
                    tvDatetimeInformation.setTextColor(tvDatetimeInformation.getResources().getColor(R.color.colorGray));
                }else {
                    tvDatetimeInformation.setText(informationAccountModel.getDatetime());
                }

                if(informationAccountModel.getSex() == 2) {
                    tvSexInformation.setText("Thêm thông tin");
                    tvSexInformation.setTextColor(tvSexInformation.getResources().getColor(R.color.colorGray));
                }else if(informationAccountModel.getSex() == 0){
                    tvSexInformation.setText("Nữ");
                }else{
                    tvSexInformation.setText("Nam");
                }

                if(informationAccountModel.getDescription() == null) {
                    tvDescriptionInformation.setText("Thêm thông tin");
                    tvDescriptionInformation.setTextColor(tvDescriptionInformation.getResources().getColor(R.color.colorGray));
                }else {
                    tvDescriptionInformation.setText(informationAccountModel.getDescription());
                }
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
