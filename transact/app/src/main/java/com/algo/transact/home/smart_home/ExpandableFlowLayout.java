package com.algo.transact.home.smart_home;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.algo.transact.AppConfig.AppState;
import com.algo.transact.R;
import com.algo.transact.generic_structures.GenericAdapterRecyclerView;
import com.algo.transact.generic_structures.IGenericAdapterRecyclerView;
import com.algo.transact.home.smart_home.beans.House;
import com.algo.transact.home.smart_home.beans.Peripheral;
import com.algo.transact.home.smart_home.beans.Room;

import java.util.ArrayList;

/**
 * Created by patilsp on 10/15/2017.
 */

public class ExpandableFlowLayout implements View.OnClickListener {

    private final ArrayList<Room> rooms;
    ArrayList<LinearLayout> expandViews = new ArrayList<>();
    Activity activity;
    LinearLayout viewParentHolder;
    LayoutInflater inflater;
    int width;

    public ExpandableFlowLayout(Activity activity) {
        this.activity = activity;
        viewParentHolder = (LinearLayout) activity.findViewById(R.id.smart_home_ll_expandable_flow_view);
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Point size = new Point();
        activity.getWindow().getWindowManager().getDefaultDisplay().getSize(size);
        width = size.x;
        House house = House.getHouse(123);
        rooms = house.getRooms();
        inflateLayout();
    }

    public void inflateLayout() {
        TableRow row = new TableRow(activity);
        View room;
        TableLayout tlTableLayout = new TableLayout(activity);
        for (int i = 0; i < rooms.size(); i++) {
            Log.i(AppState.TAG, "III " + i);
            room = inflater.inflate(R.layout.rv_item_card_room, null);
            LinearLayout llRoom = (LinearLayout) room.findViewById(R.id.card_room_ll_card_view);
            llRoom.setId(i);
            llRoom.setOnClickListener(this);
            ViewGroup.LayoutParams p1 = llRoom.getLayoutParams();
            p1.width = width / 2;
            llRoom.setLayoutParams(p1);
            row.addView(room);
            bindView(llRoom, rooms.get(i));
            if (i % 2 != 0) {
                tlTableLayout.addView(row);
                viewParentHolder.addView(tlTableLayout);

                row = new TableRow(activity);
                LinearLayout expandView = new LinearLayout(activity);
                expandView.setOrientation(LinearLayout.VERTICAL);
                expandView.setPadding(10,10,10,10);

                expandViews.add(expandView);
                row.addView(expandView);
                viewParentHolder.addView(row);
                row = new TableRow(activity);

                tlTableLayout = new TableLayout(activity);
            }
        }
        viewParentHolder.addView(row);

        row = new TableRow(activity);
        LinearLayout expandView = new LinearLayout(activity);
        expandView.setOrientation(LinearLayout.VERTICAL);
        expandView.setPadding(10,10,10,10);
        expandViews.add(expandView);
        row.addView(expandView);
        viewParentHolder.addView(row);

    }

    private void bindView(LinearLayout llRoom, Room room) {
        TextView tvRoomName;

        tvRoomName = (TextView) llRoom.findViewById(R.id.card_room_tv_room_name);

        tvRoomName.setText(room.getName());

    }

    @Override
    public void onClick(View v) {

        Log.i(AppState.TAG, " IDDD " + v.getId());
        RecyclerView rvPeripherals = new RecyclerView(activity);//(RecyclerView) v.findViewById(R.id.card_room_rv_peripheral_list);
        ViewGroup.LayoutParams lp = new ViewPager.LayoutParams();
        lp.width = width;
        rvPeripherals.setLayoutParams(lp);

        rvPeripherals.setBackgroundColor(0x444444);
        LinearLayout expandView = expandViews.get(v.getId() / 2);
        expandView.removeAllViews();

        ArrayList<Peripheral> peripherals = rooms.get(v.getId()).getPeripherals();

        for (int i = 0; i < peripherals.size(); i++) {
            expandView.addView(bindViewHolder(peripherals.get(i)));
        }

    }

    public View bindViewHolder(Peripheral per) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vPeripheral = inflater.inflate(R.layout.peripheral_layout, null);

        TextView tvPerName = (TextView) vPeripheral.findViewById(R.id.peripheral_bulb_fan_sw_name);
        ImageView ivPeripheralIcon = (ImageView) vPeripheral.findViewById(R.id.peripheral_bulb_fan_iv_icon);
        SeekBar sbSeekBar = (SeekBar) vPeripheral.findViewById(R.id.peripheral_bulb_fan_seekbar);
        TextView tvSeekbarText = (TextView) vPeripheral.findViewById(R.id.peripheral_bulb_fan_tv_seekbar_text);

        String perName = (String) per.getName();
        tvPerName.setText(perName);
        ivPeripheralIcon.setImageResource(per.getPeripheralIcon(per.getType()));
        if (per.getSeekbarText(per.getType()) != null)
            tvSeekbarText.setText(per.getSeekbarText(per.getType()));
        else {
            LinearLayout llSeekbar = (LinearLayout) vPeripheral.findViewById(R.id.peripheral_ll_seekbar);
            ViewGroup.LayoutParams lp = llSeekbar.getLayoutParams();
            lp.height=0;
            llSeekbar.setLayoutParams(lp);
        }

        ViewGroup.LayoutParams lp = new ViewPager.LayoutParams();
        lp.width = width;
        vPeripheral.setLayoutParams(lp);

        return vPeripheral;
    }

}

