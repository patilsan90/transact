package com.algo.transact.home.smart_home.beans;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.algo.transact.AppConfig.AppConfig;
import com.google.gson.Gson;

import java.util.ArrayList;

import static com.algo.transact.home.smart_home.beans.Room.ROOM_ID_NOT_REQUIRED;

/**
 * Created by patilsp on 11/5/2017.
 */

public class SmartHomeCollector {
    private static final String HOMEDETAILS = "SmartHome";
    private static final String TransactPREFERENCES = "TranPrefCollector";

    private static String newRoomString = "Add New Room";

    private House house;
    private ArrayList<Room> alRooms;
    private ArrayList<Peripheral> alPeripherals;

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public ArrayList<Room> getAlRooms() {
        return alRooms;
    }

    public void setAlRooms(ArrayList<Room> alRooms) {
        this.alRooms = alRooms;
    }

    public ArrayList<Peripheral> getAlPeripherals() {
        return alPeripherals;
    }

    public void setAlPeripherals(ArrayList<Peripheral> alPeripherals) {
        this.alPeripherals = alPeripherals;
    }

    @Override
    public String toString() {
        return "SmartHomeCollector{" +
                "house=" + house +
                ", alRooms=" + alRooms +
                ", alPeripherals=" + alPeripherals +
                '}';
    }

    public static SmartHomeCollector getSHCollector(Activity activity) {

        SharedPreferences sharedpreferences = activity.getSharedPreferences(TransactPREFERENCES, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        SmartHomeCollector smartHomeCollector = gson.fromJson(sharedpreferences.getString(HOMEDETAILS, ""), SmartHomeCollector.class);
        return smartHomeCollector;
    }

    public void saveSHCollector(Activity activity) {

        SharedPreferences sharedpreferences = activity.getSharedPreferences(TransactPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        Gson gson = new Gson();
        editor.putString(HOMEDETAILS, gson.toJson(this));
        editor.apply();
    }


    public static void CollectorToStoreConverter(SmartHomeCollector homeCollector, Activity activity) {
        SmartHomeStore smStore = new SmartHomeStore();

        homeCollector.saveSHCollector(activity);

        Room newRoom = new Room(ROOM_ID_NOT_REQUIRED, homeCollector.getHouse().getHouse_id(), newRoomString);
        homeCollector.getAlRooms().add(newRoom);

        smStore.setHouse(homeCollector.getHouse());
        smStore.setAlRooms(homeCollector.getAlRooms());
        // smStore.setAlRoomsPeripherals(homeCollector.getAlPeripherals());

        Log.i(AppConfig.TAG, "Collector " + homeCollector);
        int totalRooms = homeCollector.getAlRooms().size();
        int totalPeripherals = homeCollector.getAlPeripherals().size();

        for (int j = 0; j < totalRooms; j++) {
            smStore.getAlRoomsPeripherals().add(new ArrayList<Peripheral>());
            smStore.getAlQuickAccessRoomsPeripherals().add(new ArrayList<Peripheral>());
        }

        for (int i = 0; i < totalPeripherals; i++) {
            Peripheral per = homeCollector.getAlPeripherals().get(i);
            for (int j = 0; j < totalRooms; j++) {
                if (per.getRoom_id() == smStore.getAlRooms().get(j).getRoom_id()) {
                    if (per.isPer_is_in_quick_access()) {
                        ArrayList<Peripheral> alPeripherals = smStore.getAlQuickAccessRoomsPeripherals().get(j);
                        alPeripherals.add(new Peripheral(per.getPer_id(), per.getRoom_id(), per.getPer_type(), per.getPer_name(), per.getPer_status(), per.getPer_value(), per.isPer_is_in_quick_access()));
                    } else {
                        ArrayList<Peripheral> alPeripherals = smStore.getAlRoomsPeripherals().get(j);
                        alPeripherals.add(new Peripheral(per.getPer_id(), per.getRoom_id(), per.getPer_type(), per.getPer_name(), per.getPer_status(), per.getPer_value(), per.isPer_is_in_quick_access()));
                    }
                }
            }
        }

        smStore.saveShStore(activity);
    }

}
