package com.algo.transact.home.smart_home;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.algo.transact.AppConfig.AppConfig;
import com.algo.transact.AppConfig.IntentPutExtras;
import com.algo.transact.R;
import com.algo.transact.generic_structures.GenericAdapter;
import com.algo.transact.generic_structures.GenericAdapterRecyclerView;
import com.algo.transact.generic_structures.IGenericAdapterRecyclerView;
import com.algo.transact.generic_structures.IGenericAdapterSpinner;
import com.algo.transact.home.outlet.data_beans.Outlet;
import com.algo.transact.home.smart_home.beans.House;
import com.algo.transact.home.smart_home.beans.Peripheral;
import com.algo.transact.home.smart_home.beans.Room;
import com.algo.transact.home.smart_home.beans.SmartHomeCollector;
import com.algo.transact.home.smart_home.beans.SmartHomeStore;
import com.algo.transact.home.smart_home.holders.EditPeripheralHolder;

import java.util.ArrayList;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static com.algo.transact.home.smart_home.SHRequestHandler.RECENT_LISTENER.EDIT_ROOM_ACTIVITY;
import static com.algo.transact.home.smart_home.beans.Room.ROOM_ID_NOT_REQUIRED;

public class EditRoomActivity extends AppCompatActivity implements IGenericAdapterRecyclerView, View.OnClickListener {

    private EditText etRoomName;

    //Activity activity;
    Room room;
    ArrayList<Room> alRoomsList;
    RecyclerView rvPeripherals;

    private static String newRoomString = "Add New Room";

    private boolean isChanged = false;

    Button btSave;
    Button btCancle;
    private int roomIndex;
    // private ArrayList<Peripheral> alPeripherals;
    private ArrayList<ModPeripheral> alPeripheralsLocal;
    private ProgressDialog pDialog;

    private ArrayList<Peripheral> alEditedPeripherals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_room);

        Log.i(AppConfig.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        roomIndex = getIntent().getIntExtra(IntentPutExtras.SMART_HOME_ROOM_INDEX, 0);
        room = (Room) getIntent().getSerializableExtra(IntentPutExtras.SMART_HOME_ROOM_OBJ);

        SHRequestHandler.registerUser(this);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // setContentView(R.layout.dialogue_room_view_edit);

        btSave = (Button) findViewById(R.id.dialogue_room_view_bt_save);
        btCancle = (Button) findViewById(R.id.dialogue_room_view_bt_cancel);
        btSave.setOnClickListener(this);
        btCancle.setOnClickListener(this);

        etRoomName = (EditText) findViewById(R.id.dialogue_room_view_et_room_name);
        rvPeripherals = (RecyclerView) findViewById(R.id.dialogue_room_view_rv_per_list);
        alPeripheralsLocal = new ArrayList<>();
        displayPeripheralList();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public void displayPeripheralList() {
        Log.i(AppConfig.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());


        ArrayList alRooms = new ArrayList();
        alRooms.add(new Room(ROOM_ID_NOT_REQUIRED, 1, "Select Different Room"));
        alRooms.addAll(SmartHomeStore.getSHStore(this).getAlRooms());

        alRoomsList = alRooms;

        ArrayList<Peripheral> alPer = new ArrayList<>();
        int size1 = SmartHomeStore.getSHStore(this).getAlQuickAccessRoomsPeripherals().get(roomIndex).size();
        int size2 = SmartHomeStore.getSHStore(this).getAlRoomsPeripherals().get(roomIndex).size();
        Log.d(AppConfig.TAG, size1 + "    Sizes " + size2);
        Log.d(AppConfig.TAG, "Store::   " + SmartHomeStore.getSHStore(this));

        for (int i = 0; i < size1; i++) {
            Peripheral per = SmartHomeStore.getSHStore(this).getAlQuickAccessRoomsPeripherals().get(roomIndex).get(i);
            alPeripheralsLocal.add(new ModPeripheral(new Peripheral(per.getPer_id(), per.getRoom_id(), per.getPer_type(), per.getPer_name(), per.getPer_status(), per.getPer_value(), per.isPer_is_in_quick_access()), false));
        }
        for (int i = 0; i < size2; i++) {
            Peripheral per = SmartHomeStore.getSHStore(this).getAlRoomsPeripherals().get(roomIndex).get(i);
            alPeripheralsLocal.add(new ModPeripheral(new Peripheral(per.getPer_id(), per.getRoom_id(), per.getPer_type(), per.getPer_name(), per.getPer_status(), per.getPer_value(), per.isPer_is_in_quick_access()), false));
        }

        new GenericAdapterRecyclerView(this, this, rvPeripherals, alPeripheralsLocal, R.layout.rv_item_edit_peripheral, 1, false);
        etRoomName.setText(room.getRoom_name());
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = MATCH_PARENT;

    }

    @Override
    public void finish() {
        hideProgressDialog();
        super.finish();

    }

    private void showProgressDialog() {
        pDialog = new ProgressDialog(this);
        if (!pDialog.isShowing()) {
            pDialog.show();
            pDialog.setMessage("Saving modifications, give use a second");
        }
    }

    public void hideProgressDialog() {
        if (pDialog != null)
            if (pDialog.isShowing())
                pDialog.dismiss();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dialogue_room_view_bt_save) {
            //ServerRequestHandler.
            alEditedPeripherals = new ArrayList<>();

            int totalPer = alPeripheralsLocal.size();

            for (int i = 0; i < totalPer; i++) {
                if (alPeripheralsLocal.get(i).isModified) {
                    alEditedPeripherals.add(alPeripheralsLocal.get(i).peripheral);
                    Log.d(AppConfig.TAG, alPeripheralsLocal.get(i).peripheral.getPer_id() + " :: Per name:: " + alPeripheralsLocal.get(i).peripheral.getPer_name() + " Room id " + alPeripheralsLocal.get(i).peripheral.getRoom_id());

                }
            }
            Log.d(AppConfig.TAG, "alEditedPeripherals Size :: " + alEditedPeripherals.size());
            Log.d(AppConfig.TAG, "alModifiedPeripherals Size :: " + alPeripheralsLocal.size());

            String roomName = etRoomName.getText().toString().trim();
            Log.d(AppConfig.TAG, "New Room Name:: " + roomName);
            boolean isModified = false;
            if (roomName.length() != 0 && !roomName.equals(room.getRoom_name())) {
                room.setRoom_name(roomName);
                SHRequestHandler.updateRoom(room, EDIT_ROOM_ACTIVITY);
                isModified = true;
            }

            if (alEditedPeripherals.size() > 0) {
                SHRequestHandler.updatePeripherals(alEditedPeripherals, EDIT_ROOM_ACTIVITY);
                isModified = true;
            }
            if (isModified)
                showProgressDialog();
            else
                Toast.makeText(this, "Nothing modified", Toast.LENGTH_SHORT).show();

            //this.finish();

        } else if (v.getId() == R.id.dialogue_room_view_bt_cancel) {
            this.finish();
        }
    }

    @Override
    public RecyclerView.ViewHolder addRecyclerViewHolder(View itemView, GenericAdapterRecyclerView genericAdapterRecyclerView) {
        return new EditPeripheralHolder(itemView);
    }

    @Override
    public void bindViewHolder(RecyclerView.ViewHolder holder, ArrayList list, final int position, GenericAdapterRecyclerView genericAdapterRecyclerView) {
        final ModPeripheral per = (ModPeripheral) list.get(position);

        final EditPeripheralHolder editPeripheralHolder = (EditPeripheralHolder) holder;
        editPeripheralHolder.ivIcon.setImageResource(per.peripheral.getPeripheralIcon(per.peripheral.getPer_type()));
        editPeripheralHolder.etPerName.setText(per.peripheral.getPer_name());
        editPeripheralHolder.swOnOff.setChecked(per.peripheral.getPer_status() == Peripheral.Status.ON ? true : false);
        editPeripheralHolder.cbQuickAccess.setChecked(per.peripheral.isPer_is_in_quick_access());
        editPeripheralHolder.cbQuickAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                per.peripheral.setPer_is_in_quick_access(editPeripheralHolder.cbQuickAccess.isChecked());
                alPeripheralsLocal.get(position).isModified = true;
            }
        });
        editPeripheralHolder.etPerName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i(AppConfig.TAG, "-- Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
                }.getClass().getEnclosingMethod().getName());
                alPeripheralsLocal.get(position).peripheral.setPer_name(editPeripheralHolder.etPerName.getText().toString().trim());
                alPeripheralsLocal.get(position).isModified = true;
            }
        });
        editPeripheralHolder.swOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SHRequestHandler.updatePeripheralStatus(room, per.peripheral, EDIT_ROOM_ACTIVITY);
            }
        });
        IGenericAdapterSpinner spinnerListener = new SpinnerListener(per.peripheral, position);
        new GenericAdapter(this, spinnerListener, editPeripheralHolder.spRoomsList, alRoomsList, R.layout.list_item_spinner_rooms);
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

    public void updateSmartHomeStore() {
       // SmartHomeCollector collector = SmartHomeCollector.getSHCollector(this);

        SmartHomeStore shStore=SmartHomeStore.getSHStore(this);

        int eSize = alEditedPeripherals.size();
        for (int i = 0; i < shStore.getAlAllPeripherals().size(); i++) {
            Peripheral cPer = shStore.getAlAllPeripherals().get(i);
            for (int j = 0; j < eSize; j++) {
                if (cPer.getPer_id() == alEditedPeripherals.get(j).getPer_id()) {
                    shStore.getAlAllPeripherals().set(i, alEditedPeripherals.get(j));
                }
            }
        }
        int totalRooms = shStore.getAlRooms().size();
        for (int i = 0; i < shStore.getAlAllPeripherals().size(); i++) {
            Peripheral per = shStore.getAlAllPeripherals().get(i);
            for (int j = 0; j < totalRooms; j++) {
                if (per.getRoom_id() == shStore.getAlRooms().get(j).getRoom_id()) {
                    if (per.isPer_is_in_quick_access()) {
                        ArrayList<Peripheral> alPeripherals = shStore.getAlQuickAccessRoomsPeripherals().get(j);
                        alPeripherals.add(new Peripheral(per.getPer_id(), per.getRoom_id(), per.getPer_type(), per.getPer_name(), per.getPer_status(), per.getPer_value(), per.isPer_is_in_quick_access()));
                    } else {
                        ArrayList<Peripheral> alPeripherals = shStore.getAlRoomsPeripherals().get(j);
                        alPeripherals.add(new Peripheral(per.getPer_id(), per.getRoom_id(), per.getPer_type(), per.getPer_name(), per.getPer_status(), per.getPer_value(), per.isPer_is_in_quick_access()));
                    }
                }
            }
        }

       // collector.saveSHCollector(this);

        //SmartHomeCollector.CollectorToStoreConverter(collector, this);
        shStore.saveShStore(this);
        Toast.makeText(this, "Successfully Updated", Toast.LENGTH_SHORT).show();
        this.finish();
    }

    public void updateRoomDetails() {

        Log.d(AppConfig.TAG, " Updating room :: " + room);


        ArrayList<Room> alRooms = SmartHomeStore.getSHStore(this).getAlRooms();
        for (int i = 0; i < alRooms.size(); i++)
            if (alRooms.get(i).getRoom_id() == room.getRoom_id())
                alRooms.set(i, room);

       // SmartHomeCollector.getSHCollector(this).saveSHCollector(this);

        SmartHomeStore shStore = SmartHomeStore.getSHStore(this);
        alRooms = shStore.getAlRooms();
        for (int i = 0; i < alRooms.size(); i++)
            if (alRooms.get(i).getRoom_id() == room.getRoom_id())
                alRooms.set(i, room);

        shStore.saveShStore(this);

        this.finish();
    }

    private class ModPeripheral {
        Peripheral peripheral;
        boolean isModified;

        public ModPeripheral(Peripheral peripheral, boolean isModified) {
            this.peripheral = peripheral;
            this.isModified = isModified;
        }
    }

    class SpinnerListener implements IGenericAdapterSpinner {

        Peripheral peripheral;
        int perPosition;
        boolean isLoadTime;
        TextView tvRoomName;

        public SpinnerListener(Peripheral per, int position) {
            peripheral = per;
            this.perPosition = position;
            isLoadTime = true;
        }

        @Override
        public View addViewItemToList(View view, Object listItem, int index) {
            Room room = (Room) listItem;
            tvRoomName = (TextView) view.findViewById(R.id.spinner_rooms_tv_room_name);
            tvRoomName.setText(room.getRoom_name());
            //ServerRequestHandler.updatePeripheralInformation(room, peripheral, listener);

            return view;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Log.i(AppConfig.TAG, "-- Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
            }.getClass().getEnclosingMethod().getName());
            if (isLoadTime != true) {
                Room selectedRoom = alRoomsList.get(position);

                if (selectedRoom.getRoom_id() != ROOM_ID_NOT_REQUIRED) {
                    alPeripheralsLocal.get(perPosition).peripheral.setRoom_id(selectedRoom.getRoom_id());
                    alPeripheralsLocal.get(perPosition).isModified = true;
                } else {
                    alPeripheralsLocal.get(perPosition).peripheral.setRoom_id(room.getRoom_id());
                    alPeripheralsLocal.get(perPosition).isModified = false;
                }
            } else {
                isLoadTime = false;
                //tvRoomName.setText("Select Different room");
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

}
