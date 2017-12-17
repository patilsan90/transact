package com.algo.transact.home.smart_home.settings.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.algo.transact.R;

/**
 * Created by patilsp on 8/7/2017.
 */

public class DeviceHolder extends RecyclerView.ViewHolder {

    public EditText sh_device_et_local_ip;
    public TextView sh_device_tv_room_name;
    public Button sh_device_bt_update_ip;


    public DeviceHolder(View view) {
        super(view);
        sh_device_tv_room_name = (TextView) view.findViewById(R.id.sh_device_tv_room_name);
        sh_device_bt_update_ip = (Button) view.findViewById(R.id.sh_device_bt_update_ip);
        sh_device_et_local_ip = (EditText) view.findViewById(R.id.sh_device_et_local_ip);

    }

}

