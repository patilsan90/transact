package com.algo.transact.home.smart_home;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.algo.transact.AppConfig.AppConfig;
import com.algo.transact.R;
import com.algo.transact.home.smart_home.beans.Peripheral;
import com.algo.transact.home.smart_home.beans.Room;
import com.algo.transact.home.smart_home.beans.SmartHomeCollector;
import com.algo.transact.home.smart_home.beans.SmartHomeStore;

import java.util.ArrayList;

import static com.algo.transact.home.smart_home.beans.Room.CREATE_NEW_ROOM;

public class NewRoomDialogue extends Dialog implements View.OnClickListener {

    private EditText etRoomName;
    private Room room;

    public NewRoomDialogue(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SHRequestHandler.registerUser(this);
        Log.i(AppConfig.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialogue_new_room);


        Button dialogue_new_room_bt_create = (Button) findViewById(R.id.dialogue_new_room_bt_create);


        dialogue_new_room_bt_create.setOnClickListener(this);

        etRoomName = (EditText) findViewById(R.id.dialogue_new_room_et_room_name);

    }

    public void showDialogue() {
        Log.i(AppConfig.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        this.show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dialogue_new_room_bt_create) {
            String roomName = etRoomName.getText().toString().trim();
            if (roomName != null && roomName.length() > 0) {
                room = new Room(CREATE_NEW_ROOM, SmartHomeStore.getSHStore(getOwnerActivity()).getHouse().getHouse_id(), roomName);
                SHRequestHandler.addNewRoom(room, SHRequestHandler.RECENT_LISTENER.NEW_ROOM_DIALOGUE);
            } else
                Toast.makeText(this.getContext(), "Room name is necessary", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateRoomsList(Activity activity, Room room) {

        Log.i(AppConfig.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        Log.i(AppConfig.TAG, "New Room:: " + room);

       // SmartHomeCollector.getSHCollector(activity).getAlRooms().add(room);
       // SmartHomeCollector.getSHCollector(activity).saveSHCollector(activity);

        SmartHomeStore.getSHStore(activity).getAlRooms().add(room);
        SmartHomeStore.getSHStore(activity).getAlQuickAccessRoomsPeripherals().add(new ArrayList<Peripheral>());
        SmartHomeStore.getSHStore(activity).getAlRoomsPeripherals().add(new ArrayList<Peripheral>());
        SmartHomeStore.getSHStore(activity).saveShStore(activity);
        this.dismiss();
    }
}
