package com.algo.transact.home.smart_home;

import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.algo.transact.AppConfig.AppConfig;
import com.algo.transact.R;
import com.algo.transact.generic_structures.GenericAdapterRecyclerView;
import com.algo.transact.home.smart_home.beans.House;
import com.algo.transact.home.smart_home.beans.Peripheral;
import com.algo.transact.home.smart_home.beans.Room;
import com.algo.transact.home.smart_home.beans.SmartHomeCollector;
import com.algo.transact.home.smart_home.beans.SmartHomeStore;
import com.algo.transact.login.User;
import com.algo.transact.server_communicator.base.ResponseStatus;
import com.algo.transact.server_communicator.controllers.SmartHomeController;
import com.algo.transact.server_communicator.listener.ISmartHomeListener;

import java.util.ArrayList;

import static com.algo.transact.home.smart_home.SHRequestHandler.RECENT_LISTENER.EDIT_ROOM_ACTIVITY;
import static com.algo.transact.home.smart_home.SHRequestHandler.RECENT_LISTENER.SMART_HOME_ACTIVITY;
import static com.algo.transact.home.smart_home.SHRequestHandler.RECENT_LISTENER.WATER_INDICATOR_DIALOGUE;
import static com.algo.transact.home.smart_home.beans.Peripheral.HOUSE_SWITCH_ID;
import static com.algo.transact.home.smart_home.beans.Peripheral.ROOM_SWITCH_ID;

/**
 * Created by patilsp on 11/7/2017.
 */

public class SHRequestHandler implements ISmartHomeListener {

    RECENT_LISTENER listener;

    enum RECENT_LISTENER {
        SMART_HOME_ACTIVITY,
        ROOM_FRAGEMENT,
        EDIT_ROOM_ACTIVITY,

        SINGLE_ROOM_VIEW_FRAGMENT,
        WATER_INDICATOR_DIALOGUE
    }

    public SmartHomeActivity smartHomeActivity;
    public ArrayList<RoomFragment> roomFragment;
    public SingleRoomViewFragment singleRoomViewFragment;
    public WaterIndicatorDialogue waterIndicatorDialogue;
    public EditRoomActivity editRoomActivity;

    private static SHRequestHandler controller = new SHRequestHandler();

    SHRequestHandler() {
    }

    public static SHRequestHandler getInstance() {
        if (controller == null)
            controller = new SHRequestHandler();
        return controller;
    }

    static int counter;

    public static void registerRoom(RoomFragment roomFragment, int index) {
        Log.i(AppConfig.TAG, (counter++) + " -- Total registered room fragments :: " + controller.roomFragment.size());
        controller.roomFragment.set(index, roomFragment);
    }

    public static void registerUser(Object obj) {
        if (obj instanceof SmartHomeActivity)
            controller.smartHomeActivity = (SmartHomeActivity) obj;
        else if (obj instanceof SingleRoomViewFragment)
            controller.singleRoomViewFragment = (SingleRoomViewFragment) obj;
        else if (obj instanceof WaterIndicatorDialogue)
            controller.waterIndicatorDialogue = (WaterIndicatorDialogue) obj;
        else if (obj instanceof EditRoomActivity)
            controller.editRoomActivity = (EditRoomActivity) obj;
    }


    public static void getHouse(User user, RECENT_LISTENER recentListener) {
        controller.listener = recentListener;
        SmartHomeController.getHouse(user, controller);
    }

    public static void getPeripherals(Room room, RECENT_LISTENER recentListener) {
        controller.listener = recentListener;
        SmartHomeController.getPeripherals(room, controller);
    }

    public static void getWaterLevelPeripherals(House house, RECENT_LISTENER recentListener) {
        controller.listener = recentListener;
        SmartHomeController.getWaterLevelPeripherals(house, controller);
    }


    public static void updatePeripheralStatus(Room room, Peripheral peripheral, RECENT_LISTENER recentListener) {
        controller.listener = recentListener;
        SmartHomeController.updatePeripheralStatus(room, peripheral, controller);
    }

    public static void getPeripheralStatus(Peripheral peripheral, RECENT_LISTENER recentListener) {
        controller.listener = recentListener;
        SmartHomeController.getPeripheralStatus(peripheral, controller);
    }

    public static void updatePeripherals(ArrayList<Peripheral> alPeripherals, RECENT_LISTENER recentListener) {
        controller.listener = recentListener;
        SmartHomeController.updatePeripherals(alPeripherals, controller);
    }

    public static void updateRoom(Room room, RECENT_LISTENER recentListener) {
        controller.listener = recentListener;
        SmartHomeController.updateRoom(room, controller);
    }

    /*
    *
    * Response Methods Below
    *
    */

    @Override
    public void onGetHouse(SmartHomeCollector collector) {

        Log.i(AppConfig.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        boolean isCreateView = false;
        if (SmartHomeStore.getSHStore(smartHomeActivity) == null)
            isCreateView = true;

        smartHomeActivity.CollectorToStoreConverter(collector);
        if (listener == SMART_HOME_ACTIVITY) {
            if (isCreateView == true)
                smartHomeActivity.displayRoomsViewWithThread();
            else {
                smartHomeActivity.updateAllRooms();
                /*for(int i=0;i<controller.roomFragment.size();i++)
                {
                    controller.roomFragment.get(i).UpdateRoomView();
                }*/
            }
        }
    }

    @Override
    public void onGetPeripherals(ArrayList<Peripheral> alPeripherals) {
        if (listener == WATER_INDICATOR_DIALOGUE) {
            smartHomeActivity.hideProgressDialog();
            WaterIndicatorDialogue waterLevelDialogue = new WaterIndicatorDialogue(smartHomeActivity);
            waterLevelDialogue.showDialogue(alPeripherals);
        }
    }

    @Override
    public void onUpdatePeripheralStatus(Peripheral peripheral) {

        Log.d(AppConfig.TAG, " updatePeripheralStatus :: " + peripheral);
        switch (listener) {
            case ROOM_FRAGEMENT:
                for (int i = 0; i < roomFragment.size(); i++) {
                    if (roomFragment.get(i).room.getRoom_id() == peripheral.getRoom_id())
                        roomFragment.get(i).updatePeripheral(peripheral);
                }
                break;
            case SMART_HOME_ACTIVITY:
                if (peripheral.getPer_id() == HOUSE_SWITCH_ID) {
                    for (int i = 0; i < SmartHomeStore.getSHStore(smartHomeActivity).getAlRooms().size(); i++)
                        roomFragment.get(i).switchAllPeripherals(peripheral.getPer_status());
                }
                break;
            case SINGLE_ROOM_VIEW_FRAGMENT:
                //for (int i = 0; i < SmartHomeStore.getSHStore(smartHomeActivity).getAlRooms().size(); i++)
                if (peripheral.getPer_id() == ROOM_SWITCH_ID) {
                    for (int i = 0; i < SmartHomeStore.getSHStore(smartHomeActivity).getAlRooms().size(); i++)
                        if (peripheral.getRoom_id() == SmartHomeStore.getSHStore(smartHomeActivity).getAlRooms().get(i).getRoom_id())
                            roomFragment.get(i).switchAllPeripherals(peripheral.getPer_status());
                }
                break;
        }
    }

    @Override
    public void onUpdatePeripherals(ResponseStatus responseStatus) {
        if (listener == EDIT_ROOM_ACTIVITY) {
            if (responseStatus.getResponse() == ResponseStatus.RESPONSE.e_PERIPHERALS_LIST_UPDATED_SUCCESSFULLY) {
                editRoomActivity.updateSmartHomeCollector();
            }
        }
    }

    @Override
    public void onFailure() {
        if (listener == EDIT_ROOM_ACTIVITY) {
            Toast.makeText(editRoomActivity, "Looks like, Internet connection is down", Toast.LENGTH_SHORT).show();
            editRoomActivity.hideProgressDialog();
        } else if (listener == WATER_INDICATOR_DIALOGUE) {
            Toast.makeText(smartHomeActivity, "Looks like, Internet connection is down", Toast.LENGTH_SHORT).show();
            smartHomeActivity.hideProgressDialog();
        }
    }

    @Override
    public void onSuccess(ResponseStatus status) {

        if (listener == EDIT_ROOM_ACTIVITY) {
            if (status.getResponse() == ResponseStatus.RESPONSE.e_ROOM_INFORMATION_UPDATED_SUCCESSFULLY) {
                editRoomActivity.updateRoomDetails();
            }
        }
    }

}