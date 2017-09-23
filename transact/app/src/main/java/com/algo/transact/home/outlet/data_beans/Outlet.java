package com.algo.transact.home.outlet.data_beans;

import com.algo.transact.home.outlet.OutletType;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by sandeep on 5/8/17.
 */

public class Outlet implements Serializable {

    public int outletID;
    public String outletName;
    public String outletDisplayName;
    public OutletType.OUTLET_TYPE outletType;
    public String location;
    public int minOrder;
    public String currency;
    public boolean providesDelivery;
    public float deliveryRangeInKM; //in KM
    public long dailyOpenTimeInMillis;
    public long dailyCloseTimeInMillis;
    public ArrayList<WEEKDAY> outletCloseDays;

    public enum WEEKDAY {SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY};
    
    public Outlet(int outletID, String outletName, String outletDisplayName, OutletType.OUTLET_TYPE outletType, String location, int minOrder, String currency, boolean providesDelivery, float deliveryRangeInKM, long dailyOpenTimeInMillis, long dailyCloseTimeInMillis, ArrayList<WEEKDAY> outletCloseDays) {
        this.outletID = outletID;
        this.outletName = outletName;
        this.outletDisplayName = outletDisplayName;
        this.outletType = outletType;
        this.location = location;
        this.minOrder = minOrder;
        this.currency = currency;
        this.providesDelivery = providesDelivery;
        this.deliveryRangeInKM = deliveryRangeInKM;
        this.dailyOpenTimeInMillis = dailyOpenTimeInMillis;
        this.dailyCloseTimeInMillis = dailyCloseTimeInMillis;
        this.outletCloseDays = outletCloseDays;
    }

    public int getOutletID() {
        return outletID;
    }

    public String getOutletName() {
        return outletName;
    }

    public String getOutletDisplayName() {
        return outletDisplayName;
    }

    public OutletType.OUTLET_TYPE getOutletType() {
        return outletType;
    }

    public String getLocation() {
        return location;
    }

    public int getMinOrder() {
        return minOrder;
    }

    public boolean isProvidesDelivery() {
        return providesDelivery;
    }

    public float getDeliveryRangeInKM() {
        return deliveryRangeInKM;
    }

    public String getCurrency() {
        return currency;
    }

    public long getDailyOpenTimeInMillis() {
        return dailyOpenTimeInMillis;
    }

    public long getDailyCloseTimeInMillis() {
        return dailyCloseTimeInMillis;
    }

    public ArrayList<WEEKDAY> getOutletCloseDays() {
        return outletCloseDays;
    }

    public boolean isShopOpen() {
        long currentTimeInMillis = Calendar.getInstance().getTimeInMillis();
        if (getDailyOpenTimeInMillis() == 0 && getDailyCloseTimeInMillis() == 0)
            return true;

        if (getDailyOpenTimeInMillis() <= currentTimeInMillis && currentTimeInMillis <= getDailyCloseTimeInMillis())
            return true;
        return false;
    }
}
