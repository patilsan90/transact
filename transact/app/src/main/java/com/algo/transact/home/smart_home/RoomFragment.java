package com.algo.transact.home.smart_home;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.algo.transact.AppConfig.AppConfig;
import com.algo.transact.R;
import com.algo.transact.home.smart_home.beans.House;
import com.algo.transact.home.smart_home.beans.Peripheral;
import com.algo.transact.home.smart_home.beans.Room;
import com.algo.transact.server_communicator.listener.ISmartHomeListener;
import com.algo.transact.server_communicator.request_handler.ServerRequestHandler;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoomFragment extends Fragment implements View.OnClickListener, ISmartHomeListener {

    private Room room;

    private Context context;
    public static String roomBundle = "room";


    private ArrayList<View> alvPeriperals = new ArrayList<>();

    public RoomFragment() {
        // Required empty public constructor
    }

    RoomFragment fragment;
    private static String newRoomString = "Add New Room";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = this.getActivity();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            room = (Room) bundle.getSerializable(roomBundle);
        }
        fragment = this;
        View view = inflater.inflate(R.layout.fragment_room, container, false);
        LinearLayout llPeripheralList = (LinearLayout) view.findViewById(R.id.room_fragment_ll_peripheral_list);

        TextView tvRoomName = (TextView) view.findViewById(R.id.card_room_tv_room_name);
        tvRoomName.setText(room.getRoom_name());

        if (!room.getRoom_name().equals(newRoomString)) {
            ImageView ivAddNewRoom = (ImageView) view.findViewById(R.id.card_room_iv_add_new_room);
            ViewGroup.LayoutParams lp = ivAddNewRoom.getLayoutParams();
            lp.height = 0;
        }


        LinearLayout llList = (LinearLayout) view.findViewById(R.id.room_ll_list);
        llList.setOnClickListener(this);

        ImageView ivEditRoom = (ImageView) view.findViewById(R.id.room_iv_edit_room);
        ivEditRoom.setOnClickListener(this);

/*        LinearLayout expandView = new LinearLayout(getActivity());
        expandView.setOrientation(LinearLayout.VERTICAL);
        expandView.setPadding(10,10,10,10);*/

        ArrayList<Peripheral> peripheralsQuckAccess = room.getAl_peripheralsQuickAccess();
    if(peripheralsQuckAccess!=null) {
        for (int i = 0; i < peripheralsQuckAccess.size(); i++) {
            llPeripheralList.addView(bindViewHolder(peripheralsQuckAccess.get(i)));
        }
    }
        ArrayList<Peripheral> peripherals = room.getAl_peripherals();

        for (int i = 0; i < peripherals.size(); i++) {
            llPeripheralList.addView(bindViewHolder(peripherals.get(i)));
        }
        return view;
    }

    void updatePeripheralView(View vPeripheral, final Peripheral per, boolean isCreatingView) {

        ImageView ivPeripheralIcon = (ImageView) vPeripheral.findViewById(R.id.peripheral_iv_icon);
        ivPeripheralIcon.setImageResource(per.getPeripheralIcon(per.getType()));

        final Switch swPeripheralNameAndOnOff = (Switch) vPeripheral.findViewById(R.id.peripheral_sw_name);
        swPeripheralNameAndOnOff.setText(per.getPeripheral_name());
        swPeripheralNameAndOnOff.setChecked(per.getStatus() == Peripheral.Status.ON ? true : false);

        if(isCreatingView == true) {
            swPeripheralNameAndOnOff.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, swPeripheralNameAndOnOff.isChecked() + "  Clicked on ON/OFF switch " + per.getPeripheral_name(), Toast.LENGTH_SHORT).show();
                    per.setStatus(swPeripheralNameAndOnOff.isChecked() ? Peripheral.Status.ON : Peripheral.Status.OFF);
                    Room refRoom = new Room(room.getRoom_id(), room.getRoom_name(), null);
                    ServerRequestHandler.updatePeripheralStatus(refRoom, per, fragment);

                }
            });
        }
        switch (per.getType()) {
            case BULB:
            case FAN:
                TextView tvSeekbarText = (TextView) vPeripheral.findViewById(R.id.peripheral_tv_seekbar_text);
                tvSeekbarText.setText(per.getSeekbarText(per.getType()));

                SeekBar sbSeekBar = (SeekBar) vPeripheral.findViewById(R.id.peripheral_sb_seekbar);
                sbSeekBar.setProgress(per.getValue());
                if(isCreatingView == true) {
                    sbSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                            per.setValue(seekBar.getProgress());
                            Toast.makeText(context, seekBar.getProgress() + " On Stop Seekbar ::" + per.getPeripheral_name(), Toast.LENGTH_SHORT).show();
                            Room refRoom = new Room(room.getRoom_id(), room.getRoom_name(), null);
                            ServerRequestHandler.updatePeripheralStatus(refRoom, per, fragment);

                        }

                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {
                        }

                    });
                }
                break;

            case FRIDGE:
                hideSeekbarLayout(vPeripheral);
                break;
            case ROOM_SWITCH:
                hideSeekbarLayout(vPeripheral);
                break;
            case UNDERGROUND_WATER_TANK:
            case TERRES_WATER_TANK:
                hideSeekbarLayout(vPeripheral);
                hideMainNameSwitchLayout(vPeripheral);
                break;
            default:
                break;

        }
    }

    public View bindViewHolder(final Peripheral per) {
        LayoutInflater inflater = (LayoutInflater) this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vPeripheral = inflater.inflate(R.layout.peripheral_layout, null);
        alvPeriperals.add(vPeripheral);

        updatePeripheralView(vPeripheral, per, true);

        //  ViewGroup.LayoutParams lp = new ViewPager.LayoutParams();
        // lp.width = width;
        // vPeripheral.setLayoutParams(lp);

        return vPeripheral;
    }

    private void hideSeekbarLayout(View vPeripheral) {

        LinearLayout llSeekbar = (LinearLayout) vPeripheral.findViewById(R.id.peripheral_ll_seekbar);
        ViewGroup.LayoutParams lp = llSeekbar.getLayoutParams();
        lp.height = 0;
        llSeekbar.setLayoutParams(lp);
    }
    private void hideMainNameSwitchLayout(View vPeripheral) {

        LinearLayout llSeekbar = (LinearLayout) vPeripheral.findViewById(R.id.peripheral_ll_name_switch_view);
        ViewGroup.LayoutParams lp = llSeekbar.getLayoutParams();
        lp.height = 0;
        llSeekbar.setLayoutParams(lp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.room_ll_list:
                Log.i(AppConfig.TAG, "Clicked on list ");
                break;
            case R.id.room_iv_edit_room:
                RoomViewEditDialogue roomViewEditDialogue = new RoomViewEditDialogue(this.getActivity());
                roomViewEditDialogue.showDialogue(room, this.getActivity());
            default:
                break;
        }
    }

    @Override
    public void onGetHouse(House house) {

    }

    @Override
    public void onGetPeripherals(ArrayList<Peripheral> alPeripherals) {
    }

    @Override
    public void updatePeripheralStatus(Peripheral peripheral) {
        Log.d(AppConfig.TAG, " updatePeripheralStatus :: " + peripheral);

        ArrayList<Peripheral> peripheralsQuckAccess = room.getAl_peripheralsQuickAccess();

        boolean isQuickOption = false;

        if(peripheralsQuckAccess!=null) {
            for (int i = 0; i < peripheralsQuckAccess.size(); i++) {
                if (peripheral.getPeripheral_id() == peripheralsQuckAccess.get(i).getPeripheral_id()) {
                    isQuickOption = true;
                    updatePeripheralView(alvPeriperals.get(i), peripheral, false);
                    break;
                }
            }
        }

        if (isQuickOption == false) {
            ArrayList<Peripheral> peripherals = room.getAl_peripherals();

            for (int i = 0; i < peripherals.size(); i++) {
                if (peripheral.getPeripheral_id() == peripherals.get(i).getPeripheral_id()) {
                    updatePeripheralView(alvPeriperals.get(i), peripheral, false);
                    break;
                }
            }
        }
    }

    @Override
    public void onFailure() {

    }
}
