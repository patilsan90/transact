package com.algo.transact.home.smart_home;

import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.algo.transact.AppConfig.AppConfig;
import com.algo.transact.R;
import com.algo.transact.home.smart_home.beans.House;
import com.algo.transact.home.smart_home.beans.Peripheral;
import com.algo.transact.home.smart_home.beans.Room;
import com.algo.transact.home.smart_home.beans.SmartHomeConfig;
import com.algo.transact.login.User;
import com.algo.transact.server_communicator.listener.ISmartHomeListener;
import com.algo.transact.server_communicator.request_handler.ServerRequestHandler;

import java.util.ArrayList;

import static com.algo.transact.home.smart_home.beans.SmartHomeConfig.VIEW.EXPAND_VIEW;
import static com.algo.transact.home.smart_home.beans.SmartHomeConfig.VIEW.LIST_VIEW;

public class SmartHomeActivity extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener, ISmartHomeListener {

    LinearLayout llViewHolder;

    public static House house;
    private SmartHomeActivity context;
    private Switch swMainSwitch;

    private static String newRoomString = "Add New Room";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_home);

        context = this;

        LinearLayout llWaterLevel = (LinearLayout) findViewById(R.id.smart_home_ll_water_level);
        llWaterLevel.setOnClickListener(this);

        LinearLayout llSettings = (LinearLayout) findViewById(R.id.smart_home_ll_settings);
        llSettings.setOnClickListener(this);

        swMainSwitch = (Switch) findViewById(R.id.smart_home_sw_main_switch);
        swMainSwitch.setOnClickListener(this);

        llViewHolder = (LinearLayout) findViewById(R.id.smart_home_ll_view_holder);

        User user = User.getUserPreferences(this);

        house = House.getUserPreferences(this);
       // if (house == null)
            Log.d(AppConfig.TAG, "House object is null");
            ServerRequestHandler.getHouse(user, this);



        if (SmartHomeConfig.getUserPreferences(this) == null) {
            SmartHomeConfig smartHomeConfig = new SmartHomeConfig();
            smartHomeConfig.setDefaultView(EXPAND_VIEW);
            smartHomeConfig.setUserPreferences(this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (house != null) {
            displayRoomsView();
        }
    }

    public void displayRoomsView() {
        if (SmartHomeConfig.getUserPreferences(this).getDefaultView() == EXPAND_VIEW) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    llViewHolder.removeAllViews();
                    new ExpansionManager(context, house);
                }
            });
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    llViewHolder.removeAllViews();
                    SingleRoomViewFragment singleRoomViewFragment = new SingleRoomViewFragment();
                    android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    //transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
                    transaction.replace(R.id.smart_home_ll_view_holder, singleRoomViewFragment, LinearLayout.class.getName());
                    transaction.commit();
                }
            });
        }
    }

    public void showSettingsMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_smart_home_settings, popup.getMenu());
        popup.setOnMenuItemClickListener(this);
        popup.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.smart_home_ll_water_level:
                WaterIndicatorDialogue waterLevelDialogue = new WaterIndicatorDialogue(this);
                waterLevelDialogue.showDialogue();
                break;

            case R.id.smart_home_ll_settings:
                showSettingsMenu(v);
                break;

            case R.id.smart_home_sw_main_switch:
                if (swMainSwitch.isChecked()) {
                    Log.d(AppConfig.TAG, "Clicked on main switch, switch ON all the peripherals");
                } else {
                    Log.d(AppConfig.TAG, "Clicked on main switch, switch OFF all the peripherals");
                }
                Peripheral peripheral =new Peripheral(Peripheral.PERIPHERAL_TYPE.MAIN_SWITCH, "MAIN_SWITCH",swMainSwitch.isChecked()? Peripheral.Status.ON: Peripheral.Status.OFF,0);
                ServerRequestHandler.updatePeripheralStatus(new Room(), peripheral, this);
                break;

        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_set_view1:
                Toast.makeText(this, "Setting Expand view", Toast.LENGTH_SHORT).show();
                llViewHolder.removeAllViews();
                SmartHomeConfig smartHomeConfig = new SmartHomeConfig();
                smartHomeConfig.setDefaultView(EXPAND_VIEW);
                smartHomeConfig.setUserPreferences(this);

                new ExpansionManager(this, house);
                return true;
            case R.id.action_set_view2:
                Toast.makeText(this, "Setting single view", Toast.LENGTH_SHORT).show();
                llViewHolder.removeAllViews();
                smartHomeConfig = new SmartHomeConfig();
                smartHomeConfig.setDefaultView(LIST_VIEW);
                smartHomeConfig.setUserPreferences(this);

                SingleRoomViewFragment singleRoomViewFragment = new SingleRoomViewFragment();
                android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                //transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.smart_home_ll_view_holder, singleRoomViewFragment, LinearLayout.class.getName());
                transaction.commit();
                return true;
            default:
        }
        return false;
    }

    @Override
    public void onGetHouse(House house) {

        SmartHomeActivity.house = house;
        Room newRoom = new Room();
        newRoom.room_name = newRoomString;
        house.getAl_rooms().add(newRoom);
        SmartHomeActivity.house.setUserPreferences(this);

        Log.i(AppConfig.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        Thread thread = new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                displayRoomsView();
            }
        };

        thread.start();
    }

    @Override
    public void onGetPeripherals(ArrayList<Peripheral> alPeripherals) {
        Log.i(AppConfig.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());
    }

    @Override
    public void updatePeripheralStatus(Peripheral peripheral) {

    }

    @Override
    public void onFailure() {
        Log.i(AppConfig.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());
    }
}
