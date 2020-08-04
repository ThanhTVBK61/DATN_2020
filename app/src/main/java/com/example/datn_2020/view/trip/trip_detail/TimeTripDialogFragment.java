package com.example.datn_2020.view.trip.trip_detail;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.datn_2020.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeTripDialogFragment extends DialogFragment implements View.OnClickListener {

    private RelativeLayout rlStartTrip;
    private RelativeLayout rlEndTrip;
    private TextView tvStartTimeTrip;
    private TextView tvEndTimeTrip;
    private TextView tvCancelUpdateTime;
    private TextView tvSaveUpdateTime;

    private String start = "...";
    private String end = "...";

    private RelativeLayout active;

    private Context mContext;

    private SelectUpdateTimeTrip mSelectUpdateTimeTrip;

    void setSelectUpdateTimeTrip(SelectUpdateTimeTrip selectUpdateTimeTrip) {
        this.mSelectUpdateTimeTrip = selectUpdateTimeTrip;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_time_trip, container, false);
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.shape_popup_sex);

        registerViews(view);

        Bundle mBundle = getArguments();
        if (mBundle != null) {
            start = mBundle.getString("start");
            end = mBundle.getString("end");
        }

        tvStartTimeTrip.setText(start);
        tvEndTimeTrip.setText(end);

        active = rlStartTrip;

        rlStartTrip.setOnClickListener(this);
        rlEndTrip.setOnClickListener(this);
        tvCancelUpdateTime.setOnClickListener(this);
        tvSaveUpdateTime.setOnClickListener(this);

        return view;
    }

    private void registerViews(View view) {
        rlStartTrip = view.findViewById(R.id.rlStartTrip);
        rlEndTrip = view.findViewById(R.id.rlEndTrip);
        tvStartTimeTrip = view.findViewById(R.id.tvStartTimeTrip);
        tvEndTimeTrip = view.findViewById(R.id.tvEndTimeTrip);
        tvCancelUpdateTime = view.findViewById(R.id.tvCancelUpdateTime);
        tvSaveUpdateTime = view.findViewById(R.id.tvSaveUpdateTime);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlStartTrip:

                active.setBackgroundResource(R.drawable.shape_edit_information);
                active = rlStartTrip;
                active.setBackgroundResource(R.drawable.shape_edit_information_pressed);

                showStartDatePickerDialog();
                break;
            case R.id.rlEndTrip:

                active.setBackgroundResource(R.drawable.shape_edit_information);
                active = rlEndTrip;
                active.setBackgroundResource(R.drawable.shape_edit_information_pressed);

                showEndDatePickerDialog();
                break;
            case R.id.tvCancelUpdateTime:
                this.dismiss();
                break;
            case R.id.tvSaveUpdateTime:
                if (tvEndTimeTrip.getText().toString().equals("...") || tvStartTimeTrip.getText().toString().equals("...")) {
                    AlertDialog mAlertDialog = new AlertDialog.Builder(mContext).setTitle("Thông báo")
                            .setMessage("Chưa nhập đầy đủ thời gian!")
                            .setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).create();
                    mAlertDialog.show();
                    mAlertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorBlue700));
                } else {
                    String timeTrip = tvStartTimeTrip.getText().toString() + " " + tvEndTimeTrip.getText().toString();
                    mSelectUpdateTimeTrip.selectUpdate(timeTrip);
                    this.dismiss();
                }
                break;
        }
    }

    private void showStartDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Calendar calendar = Calendar.getInstance();

                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DATE, dayOfMonth);

                Date currentDate = calendar.getTime();

                String endDate = tvEndTimeTrip.getText().toString();

                if (!endDate.equals("...")) {

                    try {
                        Date date = formatter.parse(endDate);
                        if (currentDate.after(date)) {
                            //Tạo đối tượng
                            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                            builder.setTitle("Thông báo");
                            builder.setMessage("Thời gian không hợp lệ!");
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
                            tvSaveUpdateTime.setTextColor(tvSaveUpdateTime.getResources().getColor(R.color.colorBlue700));
                            tvStartTimeTrip.setText(formatter.format(currentDate));
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    tvSaveUpdateTime.setTextColor(tvSaveUpdateTime.getResources().getColor(R.color.colorBlue700));
                    tvStartTimeTrip.setText(formatter.format(currentDate));
                }
            }
        }, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void showEndDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Calendar calendar = Calendar.getInstance();

                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DATE, dayOfMonth);

                Date endDate = calendar.getTime();
                Date currentDate = new Date();
                String startDate = tvStartTimeTrip.getText().toString();

                if (endDate.before(currentDate)) {
                    //Tạo đối tượng
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Thông báo");
                    builder.setMessage("Thời gian không hợp lệ!");
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
                } else if (!startDate.equals("...")) {
                    try {
                        Date date = formatter.parse(startDate);
                        if (endDate.before(date)) {
                            //Tạo đối tượng
                            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                            builder.setTitle("Thông báo");
                            builder.setMessage("Thời gian không hợp lệ!");
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
                            tvSaveUpdateTime.setTextColor(tvSaveUpdateTime.getResources().getColor(R.color.colorBlue700));
                            tvEndTimeTrip.setText(formatter.format(endDate));
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } else {
                    tvSaveUpdateTime.setTextColor(tvSaveUpdateTime.getResources().getColor(R.color.colorBlue700));
                    tvEndTimeTrip.setText(formatter.format(endDate));
                }
            }
        }, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    public interface SelectUpdateTimeTrip {
        void selectUpdate(String timeTrip);
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
}
