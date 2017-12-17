package com.algo.transact.home.smart_home.settings;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.algo.transact.R;
import com.algo.transact.generic_structures.GenericAdapterRecyclerView;
import com.algo.transact.generic_structures.IGenericAdapterRecyclerView;
import com.algo.transact.home.smart_home.beans.Device;
import com.algo.transact.home.smart_home.beans.Room;
import com.algo.transact.home.smart_home.beans.SHUser;
import com.algo.transact.home.smart_home.beans.SmartHomeStore;
import com.algo.transact.home.smart_home.settings.holders.DeviceHolder;
import com.algo.transact.home.smart_home.settings.holders.RegisteredUserHolder;
import com.algo.transact.server_communicator.request_handler.SmartHomeRequestHandler;

import java.util.ArrayList;

import static com.algo.transact.server_communicator.request_handler.SmartHomeRequestHandler.RECENT_LISTENER.SETTINGS_ACTIVITY;

public class SettingsActivity extends AppCompatActivity {

    private RecyclerView rvUsersList;
    private RecyclerView rvDevicesList;
    private EditText etNewUserMobile;
    private Button btSave;

    SettingsActivity activity;
    private ProgressDialog pDialog;
    private ArrayList<SHUser> alUsers;
    private ArrayList<Device> alDevices;

    SHUser user;
    private GenericAdapterRecyclerView alUserGenericAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        activity = this;

        SmartHomeRequestHandler.registerUser(this);
        etNewUserMobile = (EditText) findViewById(R.id.settings_et_new_user_mobile);
        btSave = (Button) findViewById(R.id.settings_bt_add);
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newMobileNo = etNewUserMobile.getText().toString().trim();
                if (newMobileNo != null && newMobileNo.length() > 0) {
                    SHUser user = new SHUser();
                    user.setHouse_id(SmartHomeStore.getSHStore(activity).getHouse().getHouse_id());
                    user.setMobile_number(newMobileNo);
                    SmartHomeRequestHandler.addSHUser(user, SETTINGS_ACTIVITY);
                    showProgressDialog();
                } else
                    Toast.makeText(activity, "Please enter mobile number to add", Toast.LENGTH_SHORT).show();
            }
        });

        rvUsersList = (RecyclerView) findViewById(R.id.settings_rv_users_list);
        rvDevicesList = (RecyclerView) findViewById(R.id.settings_rv_registered_devices);
        //GenericAdapterRecyclerView(Context mContext, final IGenericAdapterRecyclerView listener, RecyclerView listView,
        // final ArrayList list, int listViewItemId, int noOfColumns, final boolean isExpandableList) {
        alUsers = SmartHomeStore.getSHStore(this).getAlUsers();
        alDevices = SmartHomeStore.getSHStore(this).getAlDevices();

        if (alUsers != null)
            alUserGenericAdapter = new GenericAdapterRecyclerView(this, new RegiteredUserListener(), rvUsersList, alUsers, R.layout.rv_item_sh_registered_user, 1, false);

        if (alDevices != null)
            new GenericAdapterRecyclerView(this, new DevicesListListener(), rvDevicesList, alDevices, R.layout.rv_item_sh_device, 1, false);
    }

    private void showProgressDialog() {
        pDialog = new ProgressDialog(this);
        if (!pDialog.isShowing()) {
            pDialog.show();
            pDialog.setMessage("Adding user, give use a second");
        }
    }

    public void hideProgressDialog() {
        if (pDialog != null)
            if (pDialog.isShowing())
                pDialog.dismiss();
    }

    class RegiteredUserListener implements IGenericAdapterRecyclerView {

        @Override
        public RecyclerView.ViewHolder addRecyclerViewHolder(View itemView, GenericAdapterRecyclerView genericAdapterRecyclerView) {
            return new RegisteredUserHolder(itemView);
        }

        @Override
        public void bindViewHolder(RecyclerView.ViewHolder holder, ArrayList list, int position, GenericAdapterRecyclerView genericAdapterRecyclerView) {

            RegisteredUserHolder ruHolder = (RegisteredUserHolder) holder;
            final SHUser user = (SHUser) list.get(position);


            ruHolder.sh_registered_user_tv_mobile_no.setText(user.getUser_name() + "\n(" + user.getMobile_number() + ")");
            ruHolder.sh_registered_user_tv_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.user = user;
                    showProgressDialog();
                    SmartHomeRequestHandler.removeSHUser(user, SETTINGS_ACTIVITY);
                }
            });
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

    public void userAdded(SHUser user) {

        SmartHomeStore.getSHStore(this).getAlUsers().add(user);
        SmartHomeStore.getSHStore(this).saveShStore(this);

        alUserGenericAdapter.notifyDataSetChanged();
        etNewUserMobile.setText("");
        hideProgressDialog();
    }

    public void userRemoved(SHUser user) {

        //       SmartHomeStore.getSHStore(this).getAlUsers().remove(user);
        for (int i = 0; i < SmartHomeStore.getSHStore(this).getAlUsers().size(); i++) {
            if (SmartHomeStore.getSHStore(this).getAlUsers().get(i).getMobile_number().equals(user.getMobile_number())) {
                SmartHomeStore.getSHStore(this).getAlUsers().remove(i);
                break;
            }
        }
        SmartHomeStore.getSHStore(this).saveShStore(this);

        alUserGenericAdapter.notifyDataSetChanged();
        hideProgressDialog();
    }

    class DevicesListListener implements IGenericAdapterRecyclerView {

        @Override
        public RecyclerView.ViewHolder addRecyclerViewHolder(View itemView, GenericAdapterRecyclerView genericAdapterRecyclerView) {
            return new DeviceHolder(itemView);
        }

        @Override
        public void bindViewHolder(RecyclerView.ViewHolder holder, final ArrayList list, final int position, GenericAdapterRecyclerView genericAdapterRecyclerView) {

            final DeviceHolder devHolder = (DeviceHolder) holder;
            final Device dev = (Device) list.get(position);
            Room room = SmartHomeStore.getRoom(dev);
            if (room != null)
                devHolder.sh_device_tv_room_name.setText(room.getRoom_name());

            devHolder.sh_device_et_local_ip.setText(dev.getLocal_ip_addr());
            devHolder.sh_device_bt_update_ip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String strIp = devHolder.sh_device_et_local_ip.getText().toString().trim();
                    if (strIp != null && strIp.length() > 0) {
                        dev.setLocal_ip_addr(strIp);
                        SmartHomeStore.getSHStore(activity).saveShStore(activity);
                        Toast.makeText(activity, "IP address stored successfully", Toast.LENGTH_SHORT).show();
                    }
                }
            });
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
