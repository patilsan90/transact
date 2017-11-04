package com.algo.transact.home.smart_home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.algo.transact.AppConfig.AppConfig;
import com.algo.transact.R;

public class WaterIndicatorDialogue extends BottomSheetDialog implements View.OnClickListener{
//BottomSheetDialog
    private TextView tvUndergroundStorage;
    private TextView tvTopStorage;


    public WaterIndicatorDialogue(@NonNull Context context) {
        super(context, R.style.Dialog);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(AppConfig.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialogue_water_level_indicator);
        tvUndergroundStorage = (TextView) findViewById(R.id.dialogue_water_underground_storage);
        tvTopStorage = (TextView) findViewById(R.id.dialogue_water_top_storage);

        LinearLayout llWaterLevel = (LinearLayout) findViewById(R.id.water_level_ll);
        llWaterLevel.setOnClickListener(this);
    }

    public void showDialogue(String underground, String top) {
        Log.i(AppConfig.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        this.show();

        tvUndergroundStorage.setText(underground);
        tvTopStorage.setText(top);

    }

    @Override
    public void onClick(View v) {
        this.dismiss();
    }
}
