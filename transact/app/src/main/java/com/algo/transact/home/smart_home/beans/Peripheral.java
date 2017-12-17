package com.algo.transact.home.smart_home.beans;

import com.algo.transact.R;

import java.io.Serializable;

/**
 * Created by patilsp on 10/14/2017.
 */

public class Peripheral implements Serializable {
    public static int ROOM_SWITCH_ID = -1;
    public static int HOUSE_SWITCH_ID = -2;


    private int per_id;
    private int room_id;
    private PERIPHERAL_TYPE per_type;
    private String per_name;
    private Status per_status;
    private int per_value;
    private boolean per_is_in_quick_access;
    private String per_phy_id;
    private int parent_device_id;

    public Peripheral(int per_id, int room_id, PERIPHERAL_TYPE per_type, String per_name, Status per_status, int per_value, boolean per_is_in_quick_access) {
        this.per_id = per_id;
        this.room_id = room_id;
        this.per_type = per_type;
        this.per_name = per_name;
        this.per_status = per_status;
        this.per_value = per_value;
        this.per_is_in_quick_access = per_is_in_quick_access;
    }

    public enum Status {
        ON,
        OFF
    }

    public enum PERIPHERAL_TYPE {
        BULB,
        FAN,
        FRIDGE,
        ROOM_SWITCH,
        MAIN_SWITCH,
        BELL,
        UNDERGROUND_WATER_TANK,
        TERRES_WATER_TANK,
        CAMERA
    }

    public boolean isPer_is_in_quick_access() {
        return per_is_in_quick_access;
    }

    public void setPer_is_in_quick_access(boolean per_is_in_quick_access) {
        this.per_is_in_quick_access = per_is_in_quick_access;
    }

    public int getPer_id() {
        return per_id;
    }

    public void setPer_id(int per_id) {
        this.per_id = per_id;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public PERIPHERAL_TYPE getPer_type() {
        return per_type;
    }

    public void setPer_type(PERIPHERAL_TYPE per_type) {
        this.per_type = per_type;
    }

    public String getPer_name() {
        return per_name;
    }

    public void setPer_name(String per_name) {
        this.per_name = per_name;
    }

    public Status getPer_status() {
        return per_status;
    }

    public void setPer_status(Status per_status) {
        this.per_status = per_status;
    }

    public int getPer_value() {
        return per_value;
    }

    public void setPer_value(int per_value) {
        this.per_value = per_value;
    }


    public static int getPeripheralIcon(PERIPHERAL_TYPE type) {
        switch (type) {

            case BULB:
                return R.drawable.ic_bulb;

            case FAN:
                return R.drawable.ic_bulb;

            case FRIDGE:
                return R.drawable.ic_fridge;

            case BELL:
                return R.drawable.ic_bulb;

            case UNDERGROUND_WATER_TANK:
                return R.drawable.ic_bulb;

            case TERRES_WATER_TANK:
                return R.drawable.ic_bulb;

            case CAMERA:
                return R.drawable.ic_bulb;

            case ROOM_SWITCH:
                return R.drawable.ic_room_switch;

            default:
                return R.drawable.ic_bulb;

        }
    }

    public static String getSeekbarText(PERIPHERAL_TYPE type) {
        switch (type) {

            case BULB:
                return "Brightness";

            case FAN:
                return "Speed";
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return "Peripheral{" +
                "per_id=" + per_id +
                ", room_id=" + room_id +
                ", per_type=" + per_type +
                ", per_name='" + per_name + '\'' +
                ", per_status=" + per_status +
                ", per_value=" + per_value +
                ", per_is_in_quick_access=" + per_is_in_quick_access +
                '}';
    }
}
