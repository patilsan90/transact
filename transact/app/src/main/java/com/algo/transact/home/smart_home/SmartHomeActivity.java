package com.algo.transact.home.smart_home;

import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.algo.transact.AppConfig.AppState;
import com.algo.transact.R;
import com.algo.transact.generic_structures.GenericAdapterRecyclerView;
import com.algo.transact.generic_structures.IGenericAdapterRecyclerView;

import java.util.ArrayList;

public class SmartHomeActivity extends AppCompatActivity implements IGenericAdapterRecyclerView, View.OnClickListener {

    private RecyclerView rvRoomsList;
    private GenericAdapterRecyclerView rvRoomsAdapter;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_home);

        rvRoomsList = (RecyclerView) findViewById(R.id.smart_home_rv_rooms_list);

        LinearLayout llWaterLevel = (LinearLayout) findViewById(R.id.smart_home_ll_water_level);
        llWaterLevel.setOnClickListener(this);

        TextView tvConfigureDevice = (TextView) findViewById(R.id.smart_home_drawer_tv_configure_device);
        tvConfigureDevice.setOnClickListener(this);

        ArrayList<String> roomNames = new ArrayList<>();

        roomNames.add("Hall");
        roomNames.add("Kitchen");
        roomNames.add("Sandeeps Room");
        roomNames.add("Vijen & Kapil");
        roomNames.add("Govind");

        rvRoomsAdapter = new GenericAdapterRecyclerView(this, this, rvRoomsList, roomNames, R.layout.rv_item_card_room, 2, true);
        createDrawer();
    }

    @Override
    public RecyclerView.ViewHolder addRecyclerViewHolder(View itemView, GenericAdapterRecyclerView genericAdapterRecyclerView) {
        return new RoomViewHolder(itemView, this);
    }

    @Override
    public void bindViewHolder(RecyclerView.ViewHolder holder, ArrayList list, int position, GenericAdapterRecyclerView genericAdapterRecyclerView) {

        RoomViewHolder roomViewHolder = (RoomViewHolder) holder;
        String roomName = (String) list.get(position);
        roomViewHolder.tvRoomName.setText(roomName);
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


    public void openDrawer(View view) {
        Log.i(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    private void createDrawer() {
        // DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.smart_home_drawer_layout);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                Log.i(AppState.TAG, "onDrawerOpened " + getTitle());
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                Log.i(AppState.TAG, "onDrawerClosed: " + getTitle());
                invalidateOptionsMenu();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerToggle.syncState();
        // mDrawerLayout.setDrawerListener(mDrawerToggle); /*check if any issue comes because of addDrawerListener.*/
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.smart_home_drawer_tv_configure_device:
                startActivity(new Intent(this, ScanWifiActivity.class));
                break;
            case R.id.smart_home_ll_water_level:
                WaterIndicatorDialogue waterLevelDialogue = new WaterIndicatorDialogue(this);
                waterLevelDialogue.showDialogue("Underground \n60 % full ", "Top \n75 % full ");
                break;


        }
    }
}