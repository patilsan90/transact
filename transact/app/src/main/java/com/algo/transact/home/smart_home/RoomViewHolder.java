package com.algo.transact.home.smart_home;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.algo.transact.R;
import com.algo.transact.generic_structures.IGenericAdapterRecyclerView;

/**
 * Created by patilsp on 8/7/2017.
 */

public class RoomViewHolder extends RecyclerView.ViewHolder {

    public TextView tvRoomName;

    IGenericAdapterRecyclerView listener;

    public RoomViewHolder(View view, IGenericAdapterRecyclerView listener) {
        super(view);
        tvRoomName = (TextView) view.findViewById(R.id.card_room_tv_room_name);
        this.listener = listener;
    }
}
