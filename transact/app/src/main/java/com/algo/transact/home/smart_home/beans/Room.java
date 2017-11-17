package com.algo.transact.home.smart_home.beans;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by patilsp on 10/14/2017.
 */

public class Room implements Serializable {
    public static int ROOM_ID_NOT_REQUIRED = -10;
    public static int CREATE_NEW_ROOM = -11;
    public static int DEVICE_ID_NOT_REQUIRED = -111;

    private int room_id;
    private int house_id;
    private String room_name;
    private int device_id;
    private boolean is_emulated_room;

    public Room(int room_id, int house_id, String room_name) {
        this.room_id = room_id;
        this.house_id = house_id;
        this.room_name = room_name;
        is_emulated_room = true;
        device_id = DEVICE_ID_NOT_REQUIRED;
    }


    public int getDevice_id() {
        return device_id;
    }

    public boolean is_emulated_room() {
        return is_emulated_room;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public int getHouse_id() {
        return house_id;
    }

    public void setHouse_id(int house_id) {
        this.house_id = house_id;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    @Override
    public String toString() {
        return "Room{" +
                "room_id=" + room_id +
                ", house_id=" + house_id +
                ", room_name='" + room_name + '\'' +
                ", device_id=" + device_id +
                ", is_emulated_room=" + is_emulated_room +
                '}';
    }
}
