package com.algo.transact.home.smart_home;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
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
import com.algo.transact.generic_structures.GenericAdapterRecyclerView;
import com.algo.transact.generic_structures.IGenericAdapterRecyclerView;
import com.algo.transact.home.smart_home.beans.House;
import com.algo.transact.home.smart_home.beans.Peripheral;
import com.algo.transact.home.smart_home.beans.Room;
import com.algo.transact.home.smart_home.beans.SmartHomeCollector;
import com.algo.transact.home.smart_home.beans.SmartHomeConfig;
import com.algo.transact.home.smart_home.beans.SmartHomeStore;
import com.algo.transact.home.smart_home.holders.PeripheralViewHolder;
import com.algo.transact.server_communicator.listener.ISmartHomeListener;
import com.algo.transact.server_communicator.request_handler.ServerRequestHandler;

import java.util.ArrayList;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.algo.transact.AppConfig.IntentPutExtras.SMART_HOME_ROOM_INDEX;
import static com.algo.transact.AppConfig.IntentPutExtras.SMART_HOME_ROOM_OBJ;
import static com.algo.transact.home.smart_home.SHRequestHandler.RECENT_LISTENER.ROOM_FRAGEMENT;
import static com.algo.transact.home.smart_home.SHRequestHandler.RECENT_LISTENER.SMART_HOME_ACTIVITY;
import static com.algo.transact.home.smart_home.beans.Room.ROOM_ID_NOT_REQUIRED;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoomFragment extends Fragment implements View.OnClickListener, IGenericAdapterRecyclerView {

    public Room room;

    private Context context;
    public static String roomBundle = "room";
    public static String roomIndexBundle = "roomIndex";


    protected int roomIndex;

    public ArrayList<View> alvPeriperals = new ArrayList<>();

    public ArrayList<Peripheral> alPeriperals = new ArrayList<>();

    public RoomFragment() {
        // Required empty public constructor
    }

    RoomFragment fragment;
    private static String newRoomString = "Add New Room";
    private Switch swSwitchAllPer;

    LinearLayout llPeripheralList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = this.getActivity();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            room = (Room) bundle.getSerializable(roomBundle);
            roomIndex = bundle.getInt(roomIndexBundle);
        }


        if (SHRequestHandler.getInstance().roomFragment == null) {
            SHRequestHandler.getInstance().roomFragment = new ArrayList<>();
            int noOfRooms = SmartHomeStore.getSHStore(getActivity()).getAlRooms().size();
            for (int i = 0; i < noOfRooms; i++)
                SHRequestHandler.getInstance().roomFragment.add(new RoomFragment());
        }


        SHRequestHandler.registerRoom(this, roomIndex);

        fragment = this;
        //SHRequestHandler.registerUser(this);

        View view = inflater.inflate(R.layout.fragment_room, container, false);
        llPeripheralList = (LinearLayout) view.findViewById(R.id.room_fragment_ll_peripheral_list);
        swSwitchAllPer = (Switch) view.findViewById(R.id.room_fragment_sw_switch_all_per);
        if (SmartHomeConfig.getUserPreferences(this.getActivity()).getDefaultView() == SmartHomeConfig.VIEW.EXPAND_VIEW) {
            swSwitchAllPer.setOnClickListener(this);
            ViewGroup.LayoutParams lp = swSwitchAllPer.getLayoutParams();
            lp.height = WRAP_CONTENT;
            swSwitchAllPer.setLayoutParams(lp);

        } else {
            swSwitchAllPer.setVisibility(View.INVISIBLE);
            ViewGroup.LayoutParams lp = swSwitchAllPer.getLayoutParams();
            lp.height = 0;
            swSwitchAllPer.setLayoutParams(lp);
        }

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
        if (room.getRoom_id() != ROOM_ID_NOT_REQUIRED) {
            fetchAllPeripherals();

            Log.d(AppConfig.TAG, "Room Creation :: " + room + "    roomIndex:: " + roomIndex);
            Log.i(AppConfig.TAG, room + "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
            }.getClass().getEnclosingMethod().getName());

            llPeripheralList.removeAllViews();
/*
            for (int i = 0; i < alPeriperals.size(); i++) {
                llPeripheralList.addView(bindViewHolder(alPeriperals.get(i)));
            }
*/


        RecyclerView rvPeripheralsList= (RecyclerView)view.findViewById(R.id.room_fragment_rv_peripheral_list);
        new GenericAdapterRecyclerView(this.getContext(),this,rvPeripheralsList,alPeriperals,R.layout.peripheral_layout,1,false);


        }
        return view;
    }

    void updatePeripheralView(View vPeripheral, final Peripheral per, boolean isCreatingView) {

        ImageView ivPeripheralIcon = (ImageView) vPeripheral.findViewById(R.id.peripheral_iv_icon);
        ivPeripheralIcon.setImageResource(per.getPeripheralIcon(per.getPer_type()));

        final Switch swPeripheralNameAndOnOff = (Switch) vPeripheral.findViewById(R.id.peripheral_sw_name);
        swPeripheralNameAndOnOff.setText(per.getPer_name());
        swPeripheralNameAndOnOff.setChecked(per.getPer_status() == Peripheral.Status.ON ? true : false);

        if (isCreatingView == true) {
            swPeripheralNameAndOnOff.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, swPeripheralNameAndOnOff.isChecked() + "  Clicked on ON/OFF switch " + per.getPer_name(), Toast.LENGTH_SHORT).show();
                    per.setPer_status(swPeripheralNameAndOnOff.isChecked() ? Peripheral.Status.ON : Peripheral.Status.OFF);
                    SHRequestHandler.updatePeripheralStatus(room, per, ROOM_FRAGEMENT);
                }
            });
        }
        switch (per.getPer_type()) {
            case BULB:
            case FAN:
                TextView tvSeekbarText = (TextView) vPeripheral.findViewById(R.id.peripheral_tv_seekbar_text);
                tvSeekbarText.setText(per.getSeekbarText(per.getPer_type()));

                SeekBar sbSeekBar = (SeekBar) vPeripheral.findViewById(R.id.peripheral_sb_seekbar);
                sbSeekBar.setProgress(per.getPer_value());
                if (per.getPer_status() == Peripheral.Status.OFF)
                    sbSeekBar.setProgress(0);

                if (isCreatingView == true) {
                    sbSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                            per.setPer_value(seekBar.getProgress());
                            Toast.makeText(context, seekBar.getProgress() + " On Stop Seekbar ::" + per.getPer_name(), Toast.LENGTH_SHORT).show();
                            //Room refRoom = new Room(room.getRoom_id(), room.getRoom_name(), null);
                            SHRequestHandler.updatePeripheralStatus(room, per, ROOM_FRAGEMENT);

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
        Log.i(AppConfig.TAG, " bind Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " :: Per " + per.getPer_name());

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
                Log.i(AppConfig.TAG, "-- Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
                }.getClass().getEnclosingMethod().getName());
                //RoomViewEditDialogue roomViewEditDialogue = new RoomViewEditDialogue(this.getActivity());
                //roomViewEditDialogue.showDialogue(room, roomIndex, this.getActivity());

                Intent intent = new Intent(this.getActivity(), EditRoomActivity.class);
                intent.putExtra(SMART_HOME_ROOM_OBJ, room);
                intent.putExtra(SMART_HOME_ROOM_INDEX, roomIndex);
                startActivity(intent);
                break;

            case R.id.room_fragment_sw_switch_all_per:
                Peripheral peripheral = new Peripheral(Peripheral.ROOM_SWITCH_ID, room.getRoom_id(),
                        Peripheral.PERIPHERAL_TYPE.ROOM_SWITCH, "ROOM_SWITCH",
                        swSwitchAllPer.isChecked() ? Peripheral.Status.ON : Peripheral.Status.OFF,
                        0, true);
                SHRequestHandler.updatePeripheralStatus(room, peripheral, ROOM_FRAGEMENT);
                break;
            default:
                break;
        }
    }

    void fetchAllPeripherals() {

        alPeriperals = new ArrayList<>();


        Log.i(AppConfig.TAG,"fetchAllPeripherals ::"+room);
        Log.i(AppConfig.TAG,"fetchAllPeripherals ::"+roomIndex);

        if(room.getRoom_id()==ROOM_ID_NOT_REQUIRED)
            return;

        int totalPeripherals = SmartHomeStore.getSHStore(this.getActivity()).getAlQuickAccessRoomsPeripherals().get(roomIndex).size();

        for (int i = 0; i < totalPeripherals; i++) {
            if (room.getRoom_id() == SmartHomeStore.getSHStore(this.getActivity()).getAlQuickAccessRoomsPeripherals().get(roomIndex).get(i).getRoom_id()) {
                alPeriperals.add(SmartHomeStore.getSHStore(this.getActivity()).getAlQuickAccessRoomsPeripherals().get(roomIndex).get(i));
            }
        }

        for (int i = 0; i < alPeriperals.size(); i++) {
            llPeripheralList.addView(bindViewHolder(alPeriperals.get(i)));
        }

        totalPeripherals = SmartHomeStore.getSHStore(this.getActivity()).getAlRoomsPeripherals().get(roomIndex).size();

        for (int i = 0; i < totalPeripherals; i++) {
            if (room.getRoom_id() == SmartHomeStore.getSHStore(this.getActivity()).getAlRoomsPeripherals().get(roomIndex).get(i).getRoom_id()) {
                alPeriperals.add(SmartHomeStore.getSHStore(this.getActivity()).getAlRoomsPeripherals().get(roomIndex).get(i));
            }
        }
        Log.i(AppConfig.TAG, "FetchAllPeripherals:: " + alPeriperals);
    }

    public void updatePeripheral(Peripheral peripheral) {

        Log.d(AppConfig.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        Log.d(AppConfig.TAG, "Room updatePeripheral :: " + room + "    roomIndex:: " + roomIndex);

        fetchAllPeripherals();

        //UpdateRoomView();

        Log.i(AppConfig.TAG, peripheral + " SWITCH-- Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        Log.i(AppConfig.TAG, "FetchAllPeripherals:: " + alPeriperals);

        if (peripheral.getPer_type() == Peripheral.PERIPHERAL_TYPE.ROOM_SWITCH) {
            Log.i(AppConfig.TAG, alvPeriperals.size() + ":: AlP:: ROOM_SWITCH-- Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
            }.getClass().getEnclosingMethod().getName());
            switchAllPeripherals(peripheral.getPer_status());
        } else {
            for (int i = 0; i < alPeriperals.size(); i++) {
                if (peripheral.getPer_id() == alPeriperals.get(i).getPer_id()) {
                    alPeriperals.set(i, peripheral);
                    this.updatePeripheralView(this.alvPeriperals.get(i), alPeriperals.get(i), false);
                }
            }
        }
    }

    public void switchAllPeripherals(Peripheral.Status per_status) {
        for (int i = 0; i < alPeriperals.size(); i++) {
            alPeriperals.get(i).setPer_status(per_status);
            this.updatePeripheralView(this.alvPeriperals.get(i), alPeriperals.get(i), false);
        }

    }

    public void UpdateRoomView() {

        fetchAllPeripherals();

        llPeripheralList.removeAllViews();

        for (int i = 0; i < alPeriperals.size(); i++) {
            //  this.updatePeripheralView(this.alvPeriperals.get(i), alPeriperals.get(i), false);
            llPeripheralList.addView(bindViewHolder(alPeriperals.get(i)));
        }
    }

    @Override
    public RecyclerView.ViewHolder addRecyclerViewHolder(View itemView, GenericAdapterRecyclerView genericAdapterRecyclerView) {
        return new PeripheralViewHolder(itemView, this);
    }

    @Override
    public void bindViewHolder(RecyclerView.ViewHolder holder, ArrayList list, int position, GenericAdapterRecyclerView genericAdapterRecyclerView) {
        updatePeripheralViewRV(holder, (Peripheral) list.get(position), true);
    }

    void updatePeripheralViewRV(RecyclerView.ViewHolder holder, final Peripheral per, boolean isCreatingView) {

        PeripheralViewHolder viewHolder = (PeripheralViewHolder) holder;
        ImageView ivPeripheralIcon = (ImageView) viewHolder.ivPeripheralIcon;
        ivPeripheralIcon.setImageResource(per.getPeripheralIcon(per.getPer_type()));

        final Switch swPeripheralNameAndOnOff = (Switch) viewHolder.swPeripheralName;
        swPeripheralNameAndOnOff.setText(per.getPer_name());
        swPeripheralNameAndOnOff.setChecked(per.getPer_status() == Peripheral.Status.ON ? true : false);

        if (isCreatingView == true) {
            swPeripheralNameAndOnOff.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, swPeripheralNameAndOnOff.isChecked() + "  Clicked on ON/OFF switch " + per.getPer_name(), Toast.LENGTH_SHORT).show();
                    per.setPer_status(swPeripheralNameAndOnOff.isChecked() ? Peripheral.Status.ON : Peripheral.Status.OFF);
                    SHRequestHandler.updatePeripheralStatus(room, per, ROOM_FRAGEMENT);
                }
            });
        }
        switch (per.getPer_type()) {
            case BULB:
            case FAN:
                TextView tvSeekbarText = (TextView) viewHolder.tvSeekbarText;
                tvSeekbarText.setText(per.getSeekbarText(per.getPer_type()));

                SeekBar sbSeekBar = (SeekBar) viewHolder.sbSeekBar;//vPeripheral.findViewById(R.id.peripheral_sb_seekbar);
                sbSeekBar.setProgress(per.getPer_value());
                if (per.getPer_status() == Peripheral.Status.OFF)
                    sbSeekBar.setProgress(0);

                if (isCreatingView == true) {
                    sbSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                            per.setPer_value(seekBar.getProgress());
                            Toast.makeText(context, seekBar.getProgress() + " On Stop Seekbar ::" + per.getPer_name(), Toast.LENGTH_SHORT).show();
                            //Room refRoom = new Room(room.getRoom_id(), room.getRoom_name(), null);
                            SHRequestHandler.updatePeripheralStatus(room, per, ROOM_FRAGEMENT);

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
                // hideSeekbarLayout(vPeripheral);
                break;
            case ROOM_SWITCH:
                //  hideSeekbarLayout(vPeripheral);
                break;
            case UNDERGROUND_WATER_TANK:
            case TERRES_WATER_TANK:
                //  hideSeekbarLayout(vPeripheral);
                //  hideMainNameSwitchLayout(vPeripheral);
                break;
            default:
                break;
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
