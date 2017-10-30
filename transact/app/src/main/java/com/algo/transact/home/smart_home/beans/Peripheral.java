package com.algo.transact.home.smart_home.beans;

import com.algo.transact.R;

import java.io.Serializable;

/**
 * Created by patilsp on 10/14/2017.
 */

public class Peripheral implements Serializable{

    public PERIPHERAL_TYPE type;
    public String name;
    public Status status;
    public int value;

    public Peripheral(PERIPHERAL_TYPE type, String name, Status status, int value) {
        this.type = type;
        this.name = name;
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
        BELL,
        UNDERGROUND_WATER_TANK,
        TERRES_WATER_TANK,
        CAMERA
    }

    public PERIPHERAL_TYPE getType() {
        return type;
    }

    public Status getStatus() {
        return status;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
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
