package com.algo.transact.home.smart_home;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.algo.transact.AppConfig.AppConfig;
import com.algo.transact.R;
import com.algo.transact.generic_structures.GenericAdapterRecyclerView;
import com.algo.transact.generic_structures.IGenericAdapterRecyclerView;
import com.algo.transact.home.smart_home.beans.Peripheral;
import com.algo.transact.home.smart_home.holders.WaterLevelHolder;
import com.algo.transact.server_communicator.request_handler.SmartHomeRequestHandler;

import java.util.ArrayList;

public class WaterIndicatorDialogue extends BottomSheetDialog implements View.OnClickListener, IGenericAdapterRecyclerView{
//BottomSheetDialog

    private ArrayList<Peripheral> waterLevelPeripherals = new ArrayList<>();
    public RecyclerView rvWaterLevelList;
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

        SmartHomeRequestHandler.registerUser(this);
        LinearLayout llWaterLevel = (LinearLayout) findViewById(R.id.water_level_ll);
        llWaterLevel.setOnClickListener(this);


        rvWaterLevelList = (RecyclerView) findViewById(R.id.dialogue_water_rv_water_level_list);
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);
       // rvWaterLevelList.setLayoutManager(layoutManager);

        pDialog = new ProgressDialog(this.getContext());

    }

    public void showDialogue(ArrayList<Peripheral> alPeripherals) {
        Log.i(AppConfig.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        this.show();
     //   showProgressDialog();
     //   SmartHomeRequestHandler.getWaterLevelPeripherals(SmartHomeStore.getSHStore(this.getOwnerActivity()).getHouse(), SmartHomeRequestHandler.RECENT_LISTENER.WATER_INDICATOR_DIALOGUE);

        new GenericAdapterRecyclerView(this.getContext(), this, this.rvWaterLevelList, alPeripherals, R.layout.rv_item_water_level, 1, false);
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
    public RecyclerView.ViewHolder addRecyclerViewHolder(View itemView, GenericAdapterRecyclerView genericAdapterRecyclerView) {
        return new WaterLevelHolder(itemView);
    }

    @Override
    public void bindViewHolder(RecyclerView.ViewHolder holder, ArrayList list, int position, GenericAdapterRecyclerView genericAdapterRecyclerView) {
        final WaterLevelHolder wlViewHolder = (WaterLevelHolder) holder;
        Peripheral per = (Peripheral) list.get(position);
        if(per.getPer_type() == Peripheral.PERIPHERAL_TYPE.UNDERGROUND_WATER_TANK)
            wlViewHolder.water_level_tv_level.setText("Underground Tank "+per.getPer_value()+"% Full");
        else
            wlViewHolder.water_level_tv_level.setText("Terrace Tank "+per.getPer_value()+"% Full");
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
