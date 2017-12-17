package com.algo.transact.home.smart_home.beans;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.algo.transact.AppConfig.AppConfig;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by patilsp on 11/6/2017.
 */

public class SmartHomeStore {
    private static final String HOMEDETAILS = "SmartHome";
    private static final String TransactPREFERENCES = "TranPref";

    private House house;
    private ArrayList<Room> alRooms = new ArrayList<>();
    private ArrayList<ArrayList<Peripheral>> alRoomsPeripherals = new ArrayList<>();
    private ArrayList<ArrayList<Peripheral>> alQuickAccessRoomsPeripherals = new ArrayList<>();

    private ArrayList<Device> alDevices = new ArrayList<>();
    private ArrayList<SHUser> alUsers = new ArrayList<>();

    private ArrayList<Peripheral> alAllPeripherals=new ArrayList<>();

    private static SmartHomeStore shStore;


    public static Room getRoom(Device dev) {
        if (shStore == null) {
            Log.e(AppConfig.TAG, "Error, SHStore is not initialized, ");
            return null;
        }

        for (int i = 0; i < shStore.getAlRooms().size(); i++) {
            if (shStore.getAlRooms().get(i).getDevice_id() == dev.getDevice_id() && !shStore.getAlRooms().get(i).is_emulated_room()) {
                return shStore.getAlRooms().get(i);
            }
        }
        return null;
    }

    public static SmartHomeStore getSHStore(Activity activity) {

        if (shStore == null)
            shStore = getUserPreferences(activity);

        return shStore;
    }

    public ArrayList<Peripheral> getAlAllPeripherals() {
        return alAllPeripherals;
    }

    public void setAlAllPeripherals(ArrayList<Peripheral> alAllPeripherals) {
        this.alAllPeripherals = alAllPeripherals;
    }

    public void setAlDevices(ArrayList<Device> alDevices) {
        this.alDevices = alDevices;
    }

    public void setAlUsers(ArrayList<SHUser> alUsers) {
        this.alUsers = alUsers;
    }

    public void saveShStore(Activity activity) {
        SmartHomeStore.shStore = this;
        setUserPreferences(activity);
    }

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

    public ArrayList<ArrayList<Peripheral>> getAlRoomsPeripherals() {
        return alRoomsPeripherals;
    }

    public void setAlRoomsPeripherals(ArrayList<ArrayList<Peripheral>> alRoomsPeripherals) {
        this.alRoomsPeripherals = alRoomsPeripherals;
    }

    public ArrayList<ArrayList<Peripheral>> getAlQuickAccessRoomsPeripherals() {
        return alQuickAccessRoomsPeripherals;
    }

    public void setAlQuickAccessRoomsPeripherals(ArrayList<ArrayList<Peripheral>> alQuickAccessRoomsPeripherals) {
        this.alQuickAccessRoomsPeripherals = alQuickAccessRoomsPeripherals;
    }

    public static SmartHomeStore getUserPreferences(Activity activity) {

        SharedPreferences sharedpreferences = activity.getSharedPreferences(TransactPREFERENCES, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        SmartHomeStore smartHomeStore = gson.fromJson(sharedpreferences.getString(HOMEDETAILS, ""), SmartHomeStore.class);
        return smartHomeStore;
    }

    public void setUserPreferences(Activity activity) {

        SharedPreferences sharedpreferences = activity.getSharedPreferences(TransactPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        Gson gson = new Gson();
        editor.putString(HOMEDETAILS, gson.toJson(this));
        editor.apply();
    }

    @Override
    public String toString() {
        return "SmartHomeStore{" +
                "house=" + house +
                ", alRooms=" + alRooms +
                ", alRoomsPeripherals=" + alRoomsPeripherals +
                ", alQuickAccessRoomsPeripherals=" + alQuickAccessRoomsPeripherals +
                '}';
    }
}
