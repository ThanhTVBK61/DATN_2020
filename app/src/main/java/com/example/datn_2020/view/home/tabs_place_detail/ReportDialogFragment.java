package com.example.datn_2020.view.home.tabs_place_detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.datn_2020.R;

public class ReportDialogFragment extends DialogFragment implements View.OnClickListener {

    private ImageView ivCloseReport;
    private TextView tvSendReport;
    private EditText etOtherContent;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private RadioButton rb5;

    private SelectReportDialogInterface mSelectReportDialogInterface;

    public void setSelectReportDialogInterface(SelectReportDialogInterface selectReportDialogInterface) {
        this.mSelectReportDialogInterface = selectReportDialogInterface;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_report, container, false);
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.shape_popup_sex);
        registerViews(view);

        ivCloseReport.setOnClickListener(this);
        tvSendReport.setOnClickListener(this);

        return view;
    }

    private void registerViews(View view) {
        ivCloseReport = view.findViewById(R.id.ivCloseReport);
        tvSendReport = view.findViewById(R.id.tvSendReport);
        etOtherContent = view.findViewById(R.id.etOtherContent);
        rb1 = view.findViewById(R.id.rb1);
        rb2 = view.findViewById(R.id.rb2);
        rb3 = view.findViewById(R.id.rb3);
        rb4 = view.findViewById(R.id.rb4);
        rb5 = view.findViewById(R.id.rb5);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivCloseReport:
                this.dismiss();
                break;
            case R.id.tvSendReport:
                String report = "";
                if (rb1.isChecked()) {
                    report = "Nội dung nhạy cảm";
                } else if (rb2.isChecked()) {
                    report = "Nội dung không phù hợp";
                } else if (rb3.isChecked()) {
                    report = "Ngôn ngữ lăng mạ";
                } else if (rb4.isChecked()) {
                    report = "Spam";
                } else if (rb5.isChecked()) {
                    report = "Tin giả";
                } else if (etOtherContent.getText().toString().trim().equals("")) {
                    this.dismiss();
                }

                String other = etOtherContent.getText().toString().trim();
                mSelectReportDialogInterface.selectReport(report +" "+other);

                this.dismiss();
                break;
        }
    }

    public interface SelectReportDialogInterface {
        void selectReport(String report);
    }
}
