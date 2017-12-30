package com.algo.transact.home.smart_home.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.algo.transact.R;
import com.algo.transact.generic_structures.IGenericAdapterRecyclerView;
import com.algo.transact.home.smart_home.beans.Room;

import java.util.ArrayList;

/**
 * Created by patilsp on 8/7/2017.
 */

public class RoomViewHolder extends RecyclerView.ViewHolder {

    public TextView tvRoomName;
    public Switch swRoomSwitch;

    public ImageView ivAddNewRoom;

    public RoomViewHolder(View view, IGenericAdapterRecyclerView listener, ArrayList<Room> rooms) {
        super(view);
        tvRoomName = (TextView) view.findViewById(R.id.card_room_tv_room_name);
        ivAddNewRoom = (ImageView) view.findViewById(R.id.card_room_iv_add_new_room);
        swRoomSwitch = (Switch) view.findViewById(R.id.card_room_sw_room_switch);
    }

}

