package com.algo.transact.home.smart_bike;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.algo.transact.R;

public class TrackingModeFragment extends Fragment implements View.OnClickListener {
    private View view;
    private RadioButton checkBox_sec, checkBox_min;
    //    private TextView textView_time;
    private EditText editText;

    public TrackingModeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_smart_bike_tracking_mode, container, false);
        checkBox_min = view.findViewById(R.id.fragment_smart_bike_tracking_mode_checkbox_minutes);
        checkBox_sec = view.findViewById(R.id.fragment_smart_bike_tracking_mode_checkbox_seconds);
//        textView_time = view.findViewById(R.id.fragment_smart_bike_tracking_mode_textview_time);
        editText = view.findViewById(R.id.fragment_smart_bike_tracking_mode_time_interval);
        editText.requestFocus();
        checkBox_sec.setChecked(true);
        checkBox_sec.setOnClickListener(this);
        checkBox_min.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_smart_bike_tracking_mode_checkbox_minutes:
                checkBox_sec.setChecked(false);
                checkBox_min.setChecked(true);
                editText.setHint("Enter Minutes");
//                textView_time.setText("min");
                break;
            case R.id.fragment_smart_bike_tracking_mode_checkbox_seconds:
                checkBox_min.setChecked(false);
                checkBox_sec.setChecked(true);
                editText.setHint("Enter Seconds");
//                textView_time.setText("sec");
                break;
        }
    }
}
