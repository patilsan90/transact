package com.algo.transact.home.smart_home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.algo.transact.AppConfig.AppState;
import com.algo.transact.R;
import com.algo.transact.generic_structures.GenericAdapterRecyclerView;
import com.algo.transact.generic_structures.IGenericAdapterRecyclerView;
import com.algo.transact.home.smart_home.beans.House;
import com.algo.transact.home.smart_home.beans.Room;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SingleRoomViewFragment extends Fragment implements IGenericAdapterRecyclerView {

    LinearLayout llRoomHolder;
    RecyclerView rvRoomsList;
    private ArrayList<Room> alRooms;

    public SingleRoomViewFragment() {
        // Required empty public constructor
    }

    private static String newRoomString = "Add New Room";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_single_room_view, container, false);
        llRoomHolder = (LinearLayout) view.findViewById(R.id.single_room_view_ll_room_holder);
        rvRoomsList = (RecyclerView) view.findViewById(R.id.single_room_view_rv_rooms_list);


        House house = House.getHouse(123);
        alRooms = house.getRooms();

        Room newRoom = new Room();
        newRoom.name = newRoomString;
        alRooms.add(newRoom);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvRoomsList.setLayoutManager(layoutManager);

        GenericAdapterRecyclerView rvRoomViewHolderAdapter = new GenericAdapterRecyclerView(this.getContext(), this, rvRoomsList, alRooms, R.layout.rv_item_card_room, -1, false);

        RoomFragment roomFragment = new RoomFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(RoomFragment.roomBundle, alRooms.get(0));
        roomFragment.setArguments(bundle);

        android.support.v4.app.FragmentTransaction transaction = this.getActivity().getSupportFragmentManager().beginTransaction();
        //transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.add(R.id.single_room_view_ll_room_holder, roomFragment, LinearLayout.class.getName());
        transaction.commit();
        return view;
    }

    @Override
    public RecyclerView.ViewHolder addRecyclerViewHolder(View itemView, GenericAdapterRecyclerView genericAdapterRecyclerView) {
        return new RoomViewHolder(itemView, this, alRooms);
    }

    @Override
    public void bindViewHolder(RecyclerView.ViewHolder holder, ArrayList list, int position, GenericAdapterRecyclerView genericAdapterRecyclerView) {
        RoomViewHolder roomViewHolder = (RoomViewHolder) holder;
        Room room = (Room) list.get(position);
        String roomName = (String) room.getName();
        roomViewHolder.tvRoomName.setText(roomName);
        if (room.getName().equals(newRoomString)) {
            roomViewHolder.ivAddNewRoom.setVisibility(View.VISIBLE);
        } else {
            ViewGroup.LayoutParams lp = roomViewHolder.ivAddNewRoom.getLayoutParams();
            lp.height = 0;
            roomViewHolder.ivAddNewRoom.setLayoutParams(lp);
        }
    }

    @Override
    public void rvListUpdateCompleteNotification(ArrayList list, GenericAdapterRecyclerView genericAdapterRecyclerView) {
        //   rvRoomsList.smoothScrollToPosition(0);
        Log.i(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());
    }

    @Override
    public void onRVClick(View view, int position, Boolean collapseState) {
        Log.i(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        Room currentRoom = alRooms.get(position);
        if (currentRoom.getName().equals(newRoomString)) {
            NewRoomDialogue roomDialogue = new NewRoomDialogue(getActivity());
            roomDialogue.showDialogue();
        } else {
            RoomFragment roomFragment = new RoomFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(RoomFragment.roomBundle, alRooms.get(position));
            roomFragment.setArguments(bundle);

            android.support.v4.app.FragmentTransaction transaction = this.getActivity().getSupportFragmentManager().beginTransaction();
            //transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
            transaction.replace(R.id.single_room_view_ll_room_holder, roomFragment, LinearLayout.class.getName());
            transaction.commit();
        }
    }

    @Override
    public void onRVLongClick(View view, int position) {

    }

    @Override
    public void onRVExpand(View view, ArrayList list, int position, View rvPrevExpanded) {

    }
}
