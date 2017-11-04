package com.algo.transact.home.smart_home;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.algo.transact.AppConfig.AppConfig;
import com.algo.transact.R;
import com.algo.transact.generic_structures.GenericAdapter;
import com.algo.transact.generic_structures.IGenericAdapter;
import com.algo.transact.generic_structures.IGenericAdapterSpinner;
import com.algo.transact.home.smart_home.beans.House;
import com.algo.transact.home.smart_home.beans.Peripheral;
import com.algo.transact.home.smart_home.beans.Room;

import java.util.ArrayList;

public class RoomViewEditDialogue extends Dialog implements IGenericAdapter {

    private EditText etRoomName;

    Activity activity;
    Room room;
    ArrayList<Room> alRoomsList;
    ListView lvPeripherals;

    private static String newRoomString = "Add New Room";

    public RoomViewEditDialogue(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(AppConfig.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.dialogue_room_view_edit);

        House house = House.getHouse(123);
        alRoomsList = house.getRooms();

        Room newRoom = new Room();
        newRoom.name = newRoomString;
        alRoomsList.add(newRoom);

        etRoomName = (EditText) findViewById(R.id.dialogue_room_view_et_room_name);
        lvPeripherals = (ListView) findViewById(R.id.dialogue_room_view_lv_per_list);

    }

    public void showDialogue(Room room, FragmentActivity activity) {
        Log.i(AppConfig.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        this.room = room;
        this.show();
        this.activity = activity;
        new GenericAdapter(activity, this, lvPeripherals, room.getPeripherals(), R.layout.peripheral_layout_edit_mode);
        etRoomName.setText(room.getName());

    }

    @Override
    public View addViewItemToList(View view, Object listItem, int index) {

        Peripheral per = (Peripheral) listItem;

        EditText etPerName = (EditText) view.findViewById(R.id.peripheral_et_name);
        ImageView ivIcon = (ImageView) view.findViewById(R.id.peripheral_iv_icon);
        Switch swOnOff = (Switch) view.findViewById(R.id.peripheral_sw_on_off);
        Spinner spRoomsList = (Spinner) view.findViewById(R.id.peripheral_sp_spinner);
        ivIcon.setImageResource(per.getPeripheralIcon(per.getType()));
        etPerName.setText(per.getName());
        swOnOff.setEnabled(per.getStatus() == Peripheral.Status.ON ? true : false);

        IGenericAdapterSpinner spinnerListener = new SpinnerListener();
        new GenericAdapter(activity, spinnerListener, spRoomsList, alRoomsList, R.layout.list_item_spinner_rooms);
        return view;
    }

    @Override
    public void listUpdateCompleteNotification(ArrayList list, GenericAdapter genericAdapter) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    class SpinnerListener implements IGenericAdapterSpinner {

        @Override
        public View addViewItemToList(View view, Object listItem, int index) {
            Room room = (Room) listItem;
            TextView tvRoomName = (TextView) view.findViewById(R.id.spinner_rooms_tv_room_name);
            tvRoomName.setText(room.getName());
            return view;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            Room selectedRoom = alRoomsList.get(position);
            if (selectedRoom.getName().equals(newRoomString)) {
                Log.d(AppConfig.TAG, "Selected, Create New Room");
                NewRoomDialogue roomDialogue = new NewRoomDialogue(activity);
                roomDialogue.showDialogue();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
