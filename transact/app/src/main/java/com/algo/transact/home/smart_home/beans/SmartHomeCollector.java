package com.algo.transact.home.smart_home.beans;

import android.app.Activity;
import android.util.Log;

import com.algo.transact.AppConfig.AppConfig;

import java.util.ArrayList;

/**
 * Created by patilsp on 11/5/2017.
 */

public class SmartHomeCollector {
    private static final String HOMEDETAILS = "SmartHome";
    private static final String TransactPREFERENCES = "TranPrefCollector";

    private House house;
    private ArrayList<Room> alRooms;
    private ArrayList<Peripheral> alPeripherals;

    private ArrayList<Device> alDevices;
    private ArrayList<SHUser> alUsers;

    public ArrayList<Device> getAlDevices() {
        return alDevices;
    }

    public ArrayList<SHUser> getAlUsers() {
        return alUsers;
    }

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


    public static void CollectorToStoreConverter(SmartHomeCollector homeCollector, Activity activity) {
        SmartHomeStore smStore = new SmartHomeStore();

        smStore.setHouse(homeCollector.getHouse());
        smStore.setAlRooms(homeCollector.getAlRooms());
        smStore.setAlAllPeripherals(homeCollector.getAlPeripherals());

        // smStore.setAlRoomsPeripherals(homeCollector.getAlPeripherals());


        Log.i(AppConfig.TAG, "Collector " + homeCollector);
        int totalRooms = homeCollector.getAlRooms().size();


        smStore.setAlDevices(homeCollector.getAlDevices());
        smStore.setAlUsers(homeCollector.getAlUsers());
        smStore.setAlRoomsPeripherals(new ArrayList<ArrayList<Peripheral>>());
        smStore.setAlQuickAccessRoomsPeripherals(new ArrayList<ArrayList<Peripheral>>());

        for (int j = 0; j < totalRooms; j++) {
            smStore.getAlRoomsPeripherals().add(new ArrayList<Peripheral>());
            smStore.getAlQuickAccessRoomsPeripherals().add(new ArrayList<Peripheral>());
        }

        int totalPeripherals = smStore.getAlAllPeripherals().size();
        for (int i = 0; i < totalPeripherals; i++) {
            Peripheral per = smStore.getAlAllPeripherals().get(i);
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
