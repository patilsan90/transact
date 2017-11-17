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
import com.algo.transact.home.smart_home.beans.SHUser;
import com.algo.transact.home.smart_home.beans.SmartHomeCollector;
import com.algo.transact.home.smart_home.beans.SmartHomeStore;
import com.algo.transact.home.smart_home.settings.SettingsActivity;
import com.algo.transact.login.User;
import com.algo.transact.server_communicator.base.ResponseStatus;
import com.algo.transact.server_communicator.controllers.SmartHomeController;
import com.algo.transact.server_communicator.listener.ISmartHomeListener;

import java.util.ArrayList;

import static com.algo.transact.home.smart_home.SHRequestHandler.RECENT_LISTENER.EDIT_ROOM_ACTIVITY;
import static com.algo.transact.home.smart_home.SHRequestHandler.RECENT_LISTENER.NEW_ROOM_DIALOGUE;
import static com.algo.transact.home.smart_home.SHRequestHandler.RECENT_LISTENER.SETTINGS_ACTIVITY;
import static com.algo.transact.home.smart_home.SHRequestHandler.RECENT_LISTENER.SMART_HOME_ACTIVITY;
import static com.algo.transact.home.smart_home.SHRequestHandler.RECENT_LISTENER.WATER_INDICATOR_DIALOGUE;
import static com.algo.transact.home.smart_home.beans.Peripheral.HOUSE_SWITCH_ID;
import static com.algo.transact.home.smart_home.beans.Peripheral.ROOM_SWITCH_ID;

/**
 * Created by patilsp on 11/7/2017.
 */

public class SHRequestHandler implements ISmartHomeListener {

    RECENT_LISTENER listener;


    public enum RECENT_LISTENER {
        SMART_HOME_ACTIVITY,
        ROOM_FRAGEMENT,
        EDIT_ROOM_ACTIVITY,
        SINGLE_ROOM_VIEW_FRAGMENT,
        WATER_INDICATOR_DIALOGUE,
        NEW_ROOM_DIALOGUE,
        SETTINGS_ACTIVITY
    }

    public SmartHomeActivity smartHomeActivity;
    public ArrayList<RoomFragment> roomFragment;
    public SingleRoomViewFragment singleRoomViewFragment;
    public WaterIndicatorDialogue waterIndicatorDialogue;
    public EditRoomActivity editRoomActivity;
    public NewRoomDialogue newRoomDialogue;
    private SettingsActivity settingsActivity;

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

        try {
            controller.roomFragment.set(index, roomFragment);
        } catch (IndexOutOfBoundsException e) {
            controller.roomFragment.add(index, roomFragment);
        }

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
        else if (obj instanceof NewRoomDialogue)
            controller.newRoomDialogue = (NewRoomDialogue) obj;
        else if (obj instanceof SettingsActivity)
            controller.settingsActivity = (SettingsActivity) obj;

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

    public static void addNewRoom(Room room, RECENT_LISTENER recentListener) {
        controller.listener = recentListener;
        SmartHomeController.addNewRoom(room, controller);
    }

    public static void addSHUser(SHUser user, RECENT_LISTENER recentListener) {
        controller.listener = recentListener;
        SmartHomeController.addSHUser(user, controller);
    }

    public static void removeSHUser(SHUser user, RECENT_LISTENER recentListener) {
        controller.listener = recentListener;
        SmartHomeController.removeSHUser(user, controller);
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
        int prevRoomCount = 0;
        if (SmartHomeStore.getSHStore(smartHomeActivity) == null)
            isCreateView = true;
        else
            prevRoomCount = SmartHomeStore.getSHStore(smartHomeActivity).getAlRooms().size();

        SmartHomeCollector.CollectorToStoreConverter(collector, smartHomeActivity);
        if (listener == SMART_HOME_ACTIVITY) {
            if (isCreateView == true)
                smartHomeActivity.displayRoomsViewWithThread();
            else {
                int newRoomCount = SmartHomeStore.getSHStore(smartHomeActivity).getAlRooms().size();
               /* if (newRoomCount == prevRoomCount)
                    smartHomeActivity.updateAllRooms();
                else*/
                smartHomeActivity.displayRoomsViewWithThread();

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
                editRoomActivity.updateSmartHomeStore();
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

    @Override
    public void onCreateRoom(Room room) {
        Log.i(AppConfig.TAG, "Adding New Room:: " + room);
        newRoomDialogue.updateRoomsList(smartHomeActivity, room);
        smartHomeActivity.displayRoomsViewWithThread();
    }

    @Override
    public void onSHUserAdd(SHUser user) {
        settingsActivity.userAdded(user);
    }

    @Override
    public void onSHUserRemove(SHUser user) {
        settingsActivity.userRemoved(user);
    }

}
