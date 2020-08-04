package com.example.datn_2020.view.login;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.datn_2020.R;
import com.example.datn_2020.repository.model.SignUpModel;
import com.example.datn_2020.view.account.EditSexAccountDialogFragment;
import com.example.datn_2020.viewmodel.login.SignUpVM;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class SignUpFragment extends Fragment implements View.OnClickListener, EditSexAccountDialogFragment.SelectSexDialogInterface, DatePickerDialog.OnDateSetListener, CompoundButton.OnCheckedChangeListener {

    private final String TAG = "Sign up";
    private ImageView ivBackSignUp;
    private RelativeLayout rlUsernameSignUp, rlPasswordSignUp, rlEmailSignUp, rlAddressSignUp, rlBirthdaySignUp, rlSexSignUp;
    private TextView tvSexSignup, tvBirthdaySignup;
    private TextView tvNotificationSignUp;
    private CheckBox cbPasswordSignUp;
    private Button btnSignUp;
    private EditText etUsernameSignup, etPasswordSignup, etEmailSignup, etAddressSignup;

    private SignUpVM signUpVM;
    private Context mContext;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        registerViews(view);
        signUpVM = new ViewModelProvider(getActivity()).get(SignUpVM.class);

        ivBackSignUp.setOnClickListener(this);
        rlSexSignUp.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        rlBirthdaySignUp.setOnClickListener(this);
        cbPasswordSignUp.setOnCheckedChangeListener(this);

        return view;
    }

    private void registerViews(View view) {
        //Image
        ivBackSignUp = view.findViewById(R.id.ivBackSignUp);
        //Edit text
        etUsernameSignup = view.findViewById(R.id.etUsernameSignup);
        etPasswordSignup = view.findViewById(R.id.etPasswordSignup);
        etEmailSignup = view.findViewById(R.id.etEmailSignup);
        etAddressSignup = view.findViewById(R.id.etAddressSignup);
        //Relative
        rlUsernameSignUp = view.findViewById(R.id.rlUsernameSignUp);
        rlPasswordSignUp = view.findViewById(R.id.rlPasswordSignUp);
        rlEmailSignUp = view.findViewById(R.id.rlEmailSignUp);
        rlAddressSignUp = view.findViewById(R.id.rlAddressSignUp);
        rlBirthdaySignUp = view.findViewById(R.id.rlBirthdaySignUp);
        rlSexSignUp = view.findViewById(R.id.rlSexSignUp);
        //TextView
        tvSexSignup = view.findViewById(R.id.tvSexSignup);
        tvBirthdaySignup = view.findViewById(R.id.tvBirthdaySignup);
        tvNotificationSignUp = view.findViewById(R.id.tvNotificationSignUp);
        //Button
        btnSignUp = view.findViewById(R.id.btnSignUp);
        cbPasswordSignUp = view.findViewById(R.id.cbPasswordSignUp);
    }

    @Override
    public void onClick(View v) {
        final LoginActivity loginActivity = (LoginActivity) getActivity();
        switch (v.getId()) {
            case R.id.ivBackSignUp:
                loginActivity.backHomeStack();
                break;
            case R.id.rlSexSignUp:
                EditSexAccountDialogFragment editSexAccountDialogFragment = new EditSexAccountDialogFragment();
                editSexAccountDialogFragment.setSelectSexDialog(this);
                editSexAccountDialogFragment.show(getChildFragmentManager(), null);
                break;
            case R.id.rlBirthdaySignUp:
                showDataPickerDialog();
                break;

            case R.id.btnSignUp:
                String username = etUsernameSignup.getText().toString();
                String password = etPasswordSignup.getText().toString();
                String email = etEmailSignup.getText().toString();
                String address = etAddressSignup.getText().toString();
                String sex = tvSexSignup.getText().toString();

                //Reset background
                rlUsernameSignUp.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_login));
                rlPasswordSignUp.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_login));
                rlAddressSignUp.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_login));

                Log.i("Sign Up",password);

                int check = 0;
                if (!checkData(username)||username.matches("")) {
                    check = -1;
                    rlUsernameSignUp.setBackground(getResources().getDrawable(R.drawable.shape_edit_information_error));
                }

                if (!checkData(password)||password.matches("")) {
                    check = -1;
                    rlPasswordSignUp.setBackground(getResources().getDrawable(R.drawable.shape_edit_information_error));
                }

//                if (!checkEmail(email)||email.matches("")) {
//                    check = -1;
//                    rlEmailSignUp.setBackground(getResources().getDrawable(R.drawable.shape_edit_information_error));
//                }else {
//                    rlEmailSignUp.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_login));
//                }

                if (!checkAddress(address)) {
                    check = -1;
                    rlAddressSignUp.setBackground(getResources().getDrawable(R.drawable.shape_edit_information_error));
                }

                if (sex.equals("Nam") || sex.equals("Nữ") || sex.equals("...")) {
                    rlSexSignUp.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_login));
                }else {
                    check = -1;
                    rlSexSignUp.setBackground(getResources().getDrawable(R.drawable.shape_edit_information_error));
                }

                String birthday = tvBirthdaySignup.getText().toString();
                if(birthday.equals("...")){
                    birthday="";
                }

                if (check == 0) {
                    int mSex = -1;
                    //Khi dữ liệu sex,address người dùng không nhập
                    if(sex.equals("Nam")){
                        mSex =1;
                    }if(sex.equals("Nữ")){
                        mSex =0;
                    }
                    if(address.matches("")){
                        address = " ";
                    }

                    //Dữ liệu gửi lên sever
                    SignUpModel mSignUpModel = new SignUpModel(username, password, email, address, mSex,birthday);
                    signUpVM.checkLogin(mSignUpModel);
                    signUpVM.getmCheckSignUp().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean result) {
                            if(result){
                                loginActivity.backHomeStack();
                            }else {
                                rlUsernameSignUp.setBackground(getResources().getDrawable(R.drawable.shape_edit_information_error));
                                tvNotificationSignUp.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                }
                break;
        }
    }

    @Override
    public void selectSex(String sex) {
        Log.i(TAG, sex);
        tvSexSignup.setText(sex);
    }

    private boolean checkData(String data) {
        if (data == null) {
            return false;
        }
        String filter = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz123456789";
        for (char c : data.toCharArray()) {
            if (filter.indexOf(c) < 0)
                return false;
        }
        return true;
    }

    private boolean checkAddress(String data) {
        if (data == null) {
            return false;
        }
        String filter = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz123456789,- ";
        for (char c : data.toCharArray()) {
            if (filter.indexOf(c) < 0)
                return false;
        }
        return true;
    }

    private boolean checkEmail(String data) {
        if (data == null) {
            return false;
        }
//        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        String emailRegex = "[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+";

        Pattern pat = Pattern.compile(emailRegex);

        return pat.matcher(data).matches();
    }

    private void showDataPickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,this, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),Calendar.getInstance().get(Calendar.DAY_OF_MONTH));


        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, dayOfMonth);

        Date dateOne = calendar.getTime();
        Date currentDate = new Date();

        if (dateOne.after(currentDate)) {
            //Tạo đối tượng
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Thông báo");
            builder.setMessage("Ngày sinh không phù hợp!");
            //Nút Cancel
            builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });

            //Tạo dialog
            AlertDialog alertDialog = builder.create();
            //Hiển thị
            alertDialog.show();
            Button negative = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
            negative.setTextColor(negative.getResources().getColor(R.color.colorBlue700));
        } else {
            String date = dayOfMonth + "/" + (month + 1) + "/" + year;
            tvBirthdaySignup.setText(date);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mContext = null;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            etPasswordSignup.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }else {
            etPasswordSignup.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }
}
