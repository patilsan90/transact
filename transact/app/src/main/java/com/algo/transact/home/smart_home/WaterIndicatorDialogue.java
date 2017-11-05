package com.algo.transact.home.smart_home;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.algo.transact.AppConfig.AppConfig;
import com.algo.transact.R;
import com.algo.transact.generic_structures.GenericAdapterRecyclerView;
import com.algo.transact.generic_structures.IGenericAdapterRecyclerView;
import com.algo.transact.home.smart_home.beans.House;
import com.algo.transact.home.smart_home.beans.Peripheral;
import com.algo.transact.server_communicator.listener.ISmartHomeListener;
import com.algo.transact.server_communicator.request_handler.ServerRequestHandler;

import java.util.ArrayList;

public class WaterIndicatorDialogue extends BottomSheetDialog implements View.OnClickListener, ISmartHomeListener, IGenericAdapterRecyclerView{
//BottomSheetDialog

    private ArrayList<Peripheral> waterLevelPeripherals = new ArrayList<>();
    private RecyclerView rvWaterLevelList;
    private ProgressDialog pDialog;

    public WaterIndicatorDialogue(@NonNull Context context) {
        super(context, R.style.Dialog);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(AppConfig.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialogue_water_level_indicator);

        LinearLayout llWaterLevel = (LinearLayout) findViewById(R.id.water_level_ll);
        llWaterLevel.setOnClickListener(this);


        rvWaterLevelList = (RecyclerView) findViewById(R.id.dialogue_water_rv_water_level_list);
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);
       // rvWaterLevelList.setLayoutManager(layoutManager);

        pDialog = new ProgressDialog(this.getContext());

        // showDialogue();
    }

    public void showDialogue() {
        Log.i(AppConfig.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        this.show();
        showProgressDialog();
        House house =new House();
        house.setId(SmartHomeActivity.house.getId());
        house.setAl_rooms(null);
        house.setOwner_id(SmartHomeActivity.house.getOwner_id());
        house.setAuthentication_code(SmartHomeActivity.house.getAuthentication_code());
        house.setHouse_name(SmartHomeActivity.house.getHouse_name());
        ServerRequestHandler.getWaterLevelPeripherals(house, this);
    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())
        {
            pDialog.show();
            pDialog.setMessage("Retrieving Water level data");
        }
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }


    @Override
    public void onClick(View v) {
        this.dismiss();
    }

    @Override
    public void onGetHouse(House house) {

    }

    @Override
    public void onGetPeripherals(ArrayList<Peripheral> alPeripherals) {

        new GenericAdapterRecyclerView(this.getContext(), this, rvWaterLevelList, alPeripherals, R.layout.rv_item_water_level, 1, false);

    }

    @Override
    public void updatePeripheralStatus(Peripheral peripheral) {

    }

    @Override
    public void onFailure() {

    }

    @Override
    public RecyclerView.ViewHolder addRecyclerViewHolder(View itemView, GenericAdapterRecyclerView genericAdapterRecyclerView) {
        return new WaterLevelHolder(itemView);
    }

    @Override
    public void bindViewHolder(RecyclerView.ViewHolder holder, ArrayList list, int position, GenericAdapterRecyclerView genericAdapterRecyclerView) {
        final WaterLevelHolder wlViewHolder = (WaterLevelHolder) holder;
        Peripheral per = (Peripheral) list.get(position);
        if(per.getType() == Peripheral.PERIPHERAL_TYPE.UNDERGROUND_WATER_TANK)
            wlViewHolder.water_level_tv_level.setText("Underground Tank "+per.getValue()+"% Full");
        else
            wlViewHolder.water_level_tv_level.setText("Terrace Tank "+per.getValue()+"% Full");

    }

    @Override
    public void rvListUpdateCompleteNotification(ArrayList list, GenericAdapterRecyclerView genericAdapterRecyclerView) {
        Log.i(AppConfig.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        hideProgressDialog();
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
