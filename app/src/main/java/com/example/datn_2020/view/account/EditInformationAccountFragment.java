package com.example.datn_2020.view.account;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.datn_2020.R;
import com.example.datn_2020.repository.model.CurrentUser;
import com.example.datn_2020.repository.model.InformationAccountModel;
import com.example.datn_2020.view.start.StartActivity;
import com.example.datn_2020.viewmodel.ContainerVM;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditInformationAccountFragment extends Fragment implements View.OnClickListener, EditSexAccountDialogFragment.SelectSexDialogInterface, View.OnFocusChangeListener, DatePickerDialog.OnDateSetListener {

    private Toolbar editToolbar;
    private RelativeLayout rlEditDatetime, rlEditSex, rlEditDescription;
    private ImageView ivClearDescription;
    private TextView tvSave;

    private EditText etDescription;
    private TextView tvSex;
    private TextView tvEditNameInformation;
    private TextView tvDatetimeInformation;

    private RelativeLayout rlActive;

    private ContainerVM informationAccountVM;

    private FragmentActivity fragmentActivity;

    private Context mContext;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_information_account, container, false);

        registerViews(view);

        if(getActivity()!=null){
            fragmentActivity =getActivity();
        }

        // Gọi tác với View Model
        informationAccountVM = new ViewModelProvider(fragmentActivity).get(ContainerVM.class);
        // Thêm các giá trị mặc đinh
        registerDataInformation();
        registerButtonSave();
        registerButtonBack();

        tvEditNameInformation.setText(CurrentUser.getInstance().username);

        rlActive = rlEditDatetime;

        rlEditDatetime.setOnClickListener(this);
        rlEditSex.setOnClickListener(this);
        rlEditDescription.setOnClickListener(this);
        etDescription.setOnFocusChangeListener(this);
        tvSave.setOnClickListener(this);

        etDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence input, int start, int before, int count) {
                tvSave.setTextColor(tvSave.getResources().getColor(R.color.colorBlue700));
//                tvSave.setTextColor(ContextCompat.getColor(mContext,R.color.colorBlue700));

                if (input.length() == 0) {
                    ivClearDescription.setVisibility(View.GONE);
                } else {
                    ivClearDescription.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ivClearDescription.setOnClickListener(this);
        return view;
    }

    private void registerViews(View view) {
        editToolbar = view.findViewById(R.id.tbEditInformationAccount);
        rlEditDatetime = view.findViewById(R.id.rlEditDatetimeInformation);
        rlEditSex = view.findViewById(R.id.rlEditSexInformation);
        rlEditDescription = view.findViewById(R.id.rlEditDescriptionInformation);
        tvEditNameInformation = view.findViewById(R.id.tvEditNameInformation);
        tvSex = view.findViewById(R.id.tvEditSexInformation);
        tvDatetimeInformation = view.findViewById(R.id.tvDatetimeInformation);
        etDescription = view.findViewById(R.id.etEditDescriptionInformation);
        ivClearDescription = view.findViewById(R.id.ivEditDescriptionInformation);
        tvSave = view.findViewById(R.id.tvSaveInformationAccount);
    }

    private void registerButtonBack() {
        editToolbar.setNavigationIcon(R.drawable.ic_back);
        editToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountFragment accountFragment = (AccountFragment) getParentFragment();
                accountFragment.backStack();
            }
        });
    }

    private void registerButtonSave() {
        tvSave.setTextColor(getResources().getColor(R.color.colorGray));
    }

    private void registerDataInformation() {
        informationAccountVM.getInfoAccountResponse().observe(getViewLifecycleOwner(), new Observer<InformationAccountModel>() {
            @Override
            public void onChanged(InformationAccountModel informationAccountModel) {

                if (informationAccountModel.getDatetime().equals(" ")) {
                    tvDatetimeInformation.setText("Thêm ngày sinh");
                    tvDatetimeInformation.setTextColor(getResources().getColor(R.color.colorGray));
                } else {
                    tvDatetimeInformation.setText(informationAccountModel.getDatetime());
                }

                if (informationAccountModel.getSex() == 2) {
                    tvSex.setText("Thêm giới tính");
                    tvSex.setTextColor(getResources().getColor(R.color.colorGray));
                } else if (informationAccountModel.getSex() == 0) {
                    tvSex.setText("Nữ");
                } else if (informationAccountModel.getSex() == 1){
                    tvSex.setText("Nam");
                }else {
                    tvSex.setText("...");
                }

                if (informationAccountModel.getDescription() != null) {
                    etDescription.setText(informationAccountModel.getDescription());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlEditDescriptionInformation:

                rlActive.setBackgroundResource(R.drawable.shape_edit_information);
                rlActive = rlEditDescription;
                rlActive.setBackgroundResource(R.drawable.shape_edit_information_pressed);

                etDescription.requestFocus();
                etDescription.setSelection(etDescription.getText().length());
                break;

            case R.id.rlEditDatetimeInformation:

                etDescription.clearFocus();

                rlActive.setBackgroundResource(R.drawable.shape_edit_information);
                rlActive = rlEditDatetime;
                rlActive.setBackgroundResource(R.drawable.shape_edit_information_pressed);

                showDataPickerDialog();

                break;
            case R.id.rlEditSexInformation:

                rlActive.setBackgroundResource(R.drawable.shape_edit_information);
                rlActive = rlEditSex;
                rlActive.setBackgroundResource(R.drawable.shape_edit_information_pressed);

                EditSexAccountDialogFragment editSexAccountDialogFragment = new EditSexAccountDialogFragment();
                editSexAccountDialogFragment.setSelectSexDialog(this);
                editSexAccountDialogFragment.show(getChildFragmentManager(), null);
                break;

            case R.id.ivEditDescriptionInformation:
                etDescription.getText().clear();
                Log.i("EditInformation","Text: "+ etDescription.getText().toString().length());
                break;

            case R.id.tvSaveInformationAccount:
                if(tvSave.getCurrentTextColor()==getResources().getColor(R.color.colorBlue700)){
                    Log.i("EditInformation","Save");
                    String username = tvEditNameInformation.getText().toString();
                    String datetime = tvDatetimeInformation.getText().toString();
                    if(datetime.equals("...")){
                        datetime = "";
                    }
                    int mSex = -1;
                    String sex = tvSex.getText().toString();

                    if(sex.equals("Nam")){
                        mSex = 1;
                    }else if(sex.equals("Nữ")){
                        mSex = 0;
                    }

                    String description = etDescription.getText().toString();
                    Log.i("EditInformation","Data: "+username+""+datetime+""+mSex+""+description);
                    InformationAccountModel newInformationAccountModel = new InformationAccountModel(username, datetime, mSex, description);
                    informationAccountVM.updateInformationAccount(newInformationAccountModel);
                    informationAccountVM.getResponseUpdateAccount().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean result) {
                            if(result){
                                //Tạo đối tượng
                                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                builder.setTitle("Thông báo");
                                builder.setMessage("Cập nhật thông tin thành công !");
                                //Nút Cancel
                                builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                                //Tạo dialog
                                AlertDialog alertDialog = builder.create();
                                //Hiển thị
                                alertDialog.show();
                                alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorBlue700));
                            }else {
                                //Tạo đối tượng
                                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                builder.setTitle("Thông báo");
                                builder.setMessage("Cập nhật thông tin không thành công !");
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
                                alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorBlue700));
                            }
                        }
                    });
                }
                break;
        }
    }

    private void showDataPickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, this, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

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
            alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorBlue700));
        } else {
            tvSave.setTextColor(tvSave.getResources().getColor(R.color.colorBlue700));
            tvDatetimeInformation.setText(formatter.format(dateOne));
        }
    }

    @Override
    public void selectSex(String sex) {
        Log.i("Callback ", sex);
        tvSex.setText(sex);
        tvSave.setTextColor(tvSave.getResources().getColor(R.color.colorBlue700));
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v.getId() == R.id.etEditDescriptionInformation) {
            if (hasFocus) {

                rlActive.setBackgroundResource(R.drawable.shape_edit_information);
                rlActive = rlEditDescription;
                rlActive.setBackgroundResource(R.drawable.shape_edit_information_pressed);
            }
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

    }
}
