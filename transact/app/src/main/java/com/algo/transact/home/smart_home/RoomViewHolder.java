package com.algo.transact.home.smart_home;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.algo.transact.AppConfig.AppState;
import com.algo.transact.R;
import com.algo.transact.generic_structures.GenericAdapterRecyclerView;
import com.algo.transact.generic_structures.IGenericAdapterRecyclerView;
import com.algo.transact.home.smart_home.beans.Peripheral;
import com.algo.transact.home.smart_home.beans.Room;

import java.util.ArrayList;

/**
 * Created by patilsp on 8/7/2017.
 */

public class RoomViewHolder extends RecyclerView.ViewHolder {

    public TextView tvRoomName;
    public RecyclerView rvPeripherals;
    IGenericAdapterRecyclerView listener;

    static int roomCounter;

    public RoomViewHolder(SmartHomeActivity smartHomeActivity, View view, IGenericAdapterRecyclerView listener, ArrayList<Room> rooms) {
        super(view);
        tvRoomName = (TextView) view.findViewById(R.id.card_room_tv_room_name);
        this.listener = listener;
        if(rooms.size() <= roomCounter)
            roomCounter=0;
        rvPeripherals = (RecyclerView) view.findViewById(R.id.card_room_rv_peripheral_list);
        ArrayList<Peripheral> alPeripherals = rooms.get(roomCounter).getPeripherals();
        if(alPeripherals.size()>0)
        {
            GenericAdapterRecyclerView rvPeripheralsAdapter = new GenericAdapterRecyclerView(smartHomeActivity, new PeripheralListener(), rvPeripherals, alPeripherals, R.layout.peripheral_layout, 1, true);
        }
        roomCounter++;
    }


    class PeripheralListener implements IGenericAdapterRecyclerView
    {

        @Override
        public RecyclerView.ViewHolder addRecyclerViewHolder(View itemView, GenericAdapterRecyclerView genericAdapterRecyclerView) {
            return new PeripheralViewHolder(itemView, this);
        }

        @Override
        public void bindViewHolder(RecyclerView.ViewHolder holder, ArrayList list, int position, GenericAdapterRecyclerView genericAdapterRecyclerView) {
            PeripheralViewHolder viewHolder = (PeripheralViewHolder) holder;
            Log.i(AppState.TAG," Posititon "+position);

            Peripheral per= (Peripheral) list.get(position);
            String perName = (String) per.getName();
            viewHolder.tvPeripheralName.setText(perName);
            viewHolder.ivPeripheralIcon.setImageResource(per.getPeripheralIcon(per.getType()));
            if(per.getSeekbarText(per.getType())!=null)
            viewHolder.tvSeekbarText.setText(per.getSeekbarText(per.getType()));
            else
            {
                viewHolder.tvSeekbarText.setVisibility(View.INVISIBLE);
                viewHolder.sbSeekBar.setVisibility(View.INVISIBLE);
            }

        }

        @Override
        public void rvListUpdateCompleteNotification(ArrayList list, GenericAdapterRecyclerView genericAdapterRecyclerView) {

        }

        @Override
        public void onRVClick(View view, int position, Boolean collapseState) {

        }

        @Override
        public void onRVLongClick(View view, int position) {

        }

        @Override
        public void onRVExpand(View view, ArrayList list, int position, View rvPrevExpanded) {

        }
    }
}

