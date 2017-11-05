package com.algo.transact.home.smart_home.beans;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by patilsp on 10/14/2017.
 */

public class Room implements Serializable {
    int room_id;
    public String room_name;

    ArrayList<Peripheral> al_peripheralsQuickAccess = new ArrayList<>();

    ArrayList<Peripheral> al_peripherals = new ArrayList<>();

    public int getRoom_id() {
        return room_id;
    }

    public String getRoom_name() {
        return room_name;
    }

    public ArrayList<Peripheral> getAl_peripherals() {
        return al_peripherals;
    }

    public ArrayList<Peripheral> getAl_peripheralsQuickAccess() {
        return al_peripheralsQuickAccess;
    }

    public Room() {
    }

    public Room(int room_id, String room_name, ArrayList<Peripheral> al_peripherals) {
        this.room_id = room_id;
        this.room_name = room_name;
        this.al_peripherals = al_peripherals;
    }

}
