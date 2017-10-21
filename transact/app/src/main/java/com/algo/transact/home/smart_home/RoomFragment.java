package com.algo.transact.home.smart_home;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.algo.transact.R;
import com.algo.transact.home.smart_home.beans.House;
import com.algo.transact.home.smart_home.beans.Peripheral;
import com.algo.transact.home.smart_home.beans.Room;
import com.google.android.gms.vision.text.Line;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoomFragment extends Fragment {

    private Room room;

    public RoomFragment() {
        // Required empty public constructor
    }

    public RoomFragment(Room room) {
        this.room=room;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_room, container, false);
        LinearLayout llPeripheralList = (LinearLayout) view.findViewById(R.id.room_fragment_ll_peripheral_list);

        TextView tvRoomName = (TextView) view.findViewById(R.id.card_room_tv_room_name);
        tvRoomName.setText(room.getName());

/*        LinearLayout expandView = new LinearLayout(getActivity());
        expandView.setOrientation(LinearLayout.VERTICAL);
        expandView.setPadding(10,10,10,10);*/



        ArrayList<Peripheral> peripherals = room.getPeripherals();

        for (int i = 0; i < peripherals.size(); i++) {
            llPeripheralList.addView(bindViewHolder(peripherals.get(i)));
        }
        return view;
    }


    public View bindViewHolder(Peripheral per) {
        LayoutInflater inflater = (LayoutInflater) this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vPeripheral = inflater.inflate(R.layout.peripheral_layout, null);

        TextView tvPerName = (TextView) vPeripheral.findViewById(R.id.peripheral_bulb_fan_sw_name);
        ImageView ivPeripheralIcon = (ImageView) vPeripheral.findViewById(R.id.peripheral_bulb_fan_iv_icon);
        SeekBar sbSeekBar = (SeekBar) vPeripheral.findViewById(R.id.peripheral_bulb_fan_seekbar);
        TextView tvSeekbarText = (TextView) vPeripheral.findViewById(R.id.peripheral_bulb_fan_tv_seekbar_text);

        String perName = (String) per.getName();
        tvPerName.setText(perName);
        ivPeripheralIcon.setImageResource(per.getPeripheralIcon(per.getType()));
        if (per.getSeekbarText(per.getType()) != null)
            tvSeekbarText.setText(per.getSeekbarText(per.getType()));
        else {
            LinearLayout llSeekbar = (LinearLayout) vPeripheral.findViewById(R.id.peripheral_ll_seekbar);
            ViewGroup.LayoutParams lp = llSeekbar.getLayoutParams();
            lp.height=0;
            llSeekbar.setLayoutParams(lp);
        }

      //  ViewGroup.LayoutParams lp = new ViewPager.LayoutParams();
       // lp.width = width;
       // vPeripheral.setLayoutParams(lp);

        return vPeripheral;
    }
}
