package com.algo.transact.home.smart_home;

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
import com.algo.transact.home.smart_home.beans.Room;

import java.util.ArrayList;

public class SmartHomeActivity extends AppCompatActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    LinearLayout llViewHolder;
    ArrayList<Room> rooms;
    private SmartHomeActivity context;
    private Switch swMainSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_home);

        LinearLayout llWaterLevel = (LinearLayout) findViewById(R.id.smart_home_ll_water_level);
        llWaterLevel.setOnClickListener(this);

        LinearLayout llSettings = (LinearLayout) findViewById(R.id.smart_home_ll_settings);
        llSettings.setOnClickListener(this);

        swMainSwitch = (Switch) findViewById(R.id.smart_home_sw_main_switch);
        swMainSwitch.setOnClickListener(this);

        llViewHolder = (LinearLayout) findViewById(R.id.smart_home_ll_view_holder);

        context = this;
    }

    @Override
    protected void onStart() {
        super.onStart();

        Thread thread = new Thread() {
            @Override
            public void run() {
                displayRoomsView();
            }
        };

        thread.start();
    }

    public void displayRoomsView() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                llViewHolder.removeAllViews();
                House house = House.getHouse(123);
                rooms = house.getRooms();
                new ExpansionManager(context, rooms);
            }
        });
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
                waterLevelDialogue.showDialogue("Underground \n60 % full ", "Top \n75 % full ");
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
                break;

        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_set_view1:
                Toast.makeText(this, "Setting Expand view", Toast.LENGTH_SHORT).show();
                llViewHolder.removeAllViews();
                House house = House.getHouse(123);
                rooms = house.getRooms();
                new ExpansionManager(this, rooms);
                return true;
            case R.id.action_set_view2:
                Toast.makeText(this, "Setting single view", Toast.LENGTH_SHORT).show();
                llViewHolder.removeAllViews();
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
}
