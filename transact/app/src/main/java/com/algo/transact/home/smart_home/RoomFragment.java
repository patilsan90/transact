package com.algo.transact.home.smart_home;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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

import com.algo.transact.AppConfig.AppState;
import com.algo.transact.R;
import com.algo.transact.home.smart_home.beans.House;
import com.algo.transact.home.smart_home.beans.Peripheral;
import com.algo.transact.home.smart_home.beans.Room;
import com.google.android.gms.vision.text.Line;

import java.util.ArrayList;

import static com.algo.transact.home.smart_home.beans.Peripheral.PERIPHERAL_TYPE.BULB;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoomFragment extends Fragment implements View.OnClickListener {

    private Room room;

    private Context context;
    public static String roomBundle = "room";


    public RoomFragment() {
        // Required empty public constructor
    }

    private static String newRoomString = "Add New Room";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = this.getActivity();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            room = (Room) bundle.getSerializable(roomBundle);
        }

        View view = inflater.inflate(R.layout.fragment_room, container, false);
        LinearLayout llPeripheralList = (LinearLayout) view.findViewById(R.id.room_fragment_ll_peripheral_list);

        TextView tvRoomName = (TextView) view.findViewById(R.id.card_room_tv_room_name);
        tvRoomName.setText(room.getName());

        if (!room.getName().equals(newRoomString)) {
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

        ArrayList<Peripheral> peripherals = room.getPeripherals();

        for (int i = 0; i < peripherals.size(); i++) {
            llPeripheralList.addView(bindViewHolder(peripherals.get(i)));
        }
        return view;
    }

    public View bindViewHolder(final Peripheral per) {
        LayoutInflater inflater = (LayoutInflater) this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vPeripheral = inflater.inflate(R.layout.peripheral_layout, null);

        ImageView ivPeripheralIcon = (ImageView) vPeripheral.findViewById(R.id.peripheral_iv_icon);
        ivPeripheralIcon.setImageResource(per.getPeripheralIcon(per.getType()));

        final Switch swPeripheralNameAndOnOff = (Switch) vPeripheral.findViewById(R.id.peripheral_sw_name);
        swPeripheralNameAndOnOff.setText(per.getName());

        swPeripheralNameAndOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, swPeripheralNameAndOnOff.isChecked() + "  Clicked on ON/OFF switch " + per.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        switch (per.getType()) {
            case BULB:
            case FAN:
                TextView tvSeekbarText = (TextView) vPeripheral.findViewById(R.id.peripheral_tv_seekbar_text);
                tvSeekbarText.setText(per.getSeekbarText(per.getType()));

                SeekBar sbSeekBar = (SeekBar) vPeripheral.findViewById(R.id.peripheral_sb_seekbar);
                sbSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        Toast.makeText(context, seekBar.getProgress() + " On Stop Seekbar ::" + per.getName(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                });
                break;

            case FRIDGE:
                hideSeekbarLayout(vPeripheral);
                break;

            default:
                break;

        }


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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.room_ll_list:
                Log.i(AppState.TAG, "Clicked on list ");
                break;
            case R.id.room_iv_edit_room:
                RoomViewEditDialogue roomViewEditDialogue = new RoomViewEditDialogue(this.getActivity());
                roomViewEditDialogue.showDialogue(room, this.getActivity());
            default:
                break;
        }
    }
}
