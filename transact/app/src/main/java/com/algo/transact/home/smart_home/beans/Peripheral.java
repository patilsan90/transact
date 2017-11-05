package com.algo.transact.home.smart_home.beans;

import com.algo.transact.R;

import java.io.Serializable;

/**
 * Created by patilsp on 10/14/2017.
 */

public class Peripheral extends Room implements Serializable{

    private int peripheral_id;
    public PERIPHERAL_TYPE type;
    public String peripheral_name;
    public Status status;
    public int value;

    public Peripheral(PERIPHERAL_TYPE type, String name, Status status, int value) {
        this.type = type;
        this.peripheral_name = name;
        this.status = status;
        this.value = value;
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

    public int getPeripheral_id() {
        return peripheral_id;
    }

    public PERIPHERAL_TYPE getType() {
        return type;
    }

    public String getPeripheral_name() {
        return peripheral_name;
    }

    public Status getStatus() {
        return status;
    }

    public int getValue() {
        return value;
    }

    public void setPeripheral_id(int peripheral_id) {
        this.peripheral_id = peripheral_id;
    }

    public void setType(PERIPHERAL_TYPE type) {
        this.type = type;
    }

    public void setPeripheral_name(String peripheral_name) {
        this.peripheral_name = peripheral_name;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setValue(int value) {
        this.value = value;
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
}
