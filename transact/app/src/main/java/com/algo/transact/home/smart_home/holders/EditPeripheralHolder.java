package com.algo.transact.home.smart_home.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;

import com.algo.transact.R;

/**
 * Created by patilsp on 8/7/2017.
 */

public class EditPeripheralHolder extends RecyclerView.ViewHolder {

    public EditText etPerName;
    public ImageView ivIcon;
    public Switch swOnOff;
    public Spinner spRoomsList;
    public CheckBox cbQuickAccess;

    public EditPeripheralHolder(View view) {
        super(view);
        etPerName = (EditText) view.findViewById(R.id.peripheral_et_name);
        ivIcon = (ImageView) view.findViewById(R.id.peripheral_iv_icon);
        swOnOff = (Switch) view.findViewById(R.id.peripheral_sw_on_off);
        spRoomsList = (Spinner) view.findViewById(R.id.peripheral_sp_spinner);
        cbQuickAccess=(CheckBox)view.findViewById(R.id.peripheral_cb_quick_access);
    }

}

