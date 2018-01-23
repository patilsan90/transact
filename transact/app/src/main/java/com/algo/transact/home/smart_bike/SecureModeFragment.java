package com.algo.transact.home.smart_bike;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.algo.transact.R;

import java.text.DecimalFormat;
import java.util.Calendar;

public class SecureModeFragment extends Fragment implements View.OnClickListener, View.OnFocusChangeListener {
    private View view;
    private TextView textView_to;
    private TextView editText_start_time, editText_end_time;
    private Button button_set;
    private int hour_start_time, hour_end_time;
    private int min_start_time, min_end_time;
    private DecimalFormat decimalFormat = new DecimalFormat("00");
    private Calendar mCurrentDate;
    private static final String AM = "AM";
    private static final String PM = "PM";
    String hour_start;

    public SecureModeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_smart_bike_secure_mode, container, false);
        editText_start_time = view.findViewById(R.id.fragment_smart_bike_secure_mode_edittext_start_time);
        editText_end_time = view.findViewById(R.id.fragment_smart_bike_secure_mode_edittext_end_time);
        button_set = view.findViewById(R.id.fragment_smart_bike_secure_mode_button_set);
        button_set.setOnClickListener(this);
        editText_start_time.setOnFocusChangeListener(this);
        editText_end_time.setOnFocusChangeListener(this);
        editText_start_time.setOnClickListener(this);
        editText_end_time.setOnClickListener(this);
        editText_end_time.setActivated(false);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_smart_bike_secure_mode_button_set:
                break;
            case R.id.fragment_smart_bike_secure_mode_edittext_end_time:
                showEndTimePicker();
                break;
            case R.id.fragment_smart_bike_secure_mode_edittext_start_time:
                showStartTimePicker();
                break;

        }
    }

    private void showEndTimePicker() {
        mCurrentDate = Calendar.getInstance();
        hour_end_time = mCurrentDate.get(Calendar.HOUR_OF_DAY);
        min_end_time = mCurrentDate.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                hour_end_time = i;
                min_end_time = i1;
                String hour_end_time = decimalFormat.format(i);
                String minute_end_time = decimalFormat.format(i1);
                String am_pm;
                if (i < 12)
                    am_pm = AM;
                else {
                    int hour = i - 12;
                    hour_end_time = decimalFormat.format(hour);
                    am_pm = PM;
                }
                editText_end_time.setText(hour_end_time + " : " + minute_end_time + " " + am_pm);
            }
        }, hour_end_time, min_end_time, false);
        mTimePicker.show();
    }

    private void showStartTimePicker() {
        Calendar mCurrentDate = Calendar.getInstance();
        hour_start_time = mCurrentDate.get(Calendar.HOUR_OF_DAY);
        min_start_time = mCurrentDate.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                hour_start_time = i;
                min_start_time = i1;
                 hour_start = decimalFormat.format(i);
                String minute_start_time = decimalFormat.format(i1);
                String am_pm;
                if (i < 12)
                    am_pm = AM;
                else {
                    int hour = i - 12;
                    hour_start = decimalFormat.format(hour);
                    am_pm = PM;
                }
                editText_start_time.setText(hour_start + " : " + minute_start_time + " " + am_pm);
            }
        }, hour_start_time, min_start_time, false);
        mTimePicker.show();
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        switch (view.getId()) {
            case R.id.fragment_smart_bike_secure_mode_edittext_end_time:
                if (hasFocus)
                    showEndTimePicker();
                break;
            case R.id.fragment_smart_bike_secure_mode_edittext_start_time:
                if (hasFocus)
                    showStartTimePicker();
                break;
        }

    }
}
