package com.algo.transact.home.outlet.data_beans;

import com.algo.transact.home.outlet.OutletType;

import java.io.Serializable;

/**
 * Created by sandeep on 5/8/17.
 */

public class Outlet implements Serializable {

    public int outletID;
    public String outletName;
    public String outletDisplayName;
    public OutletType.OUTLET_TYPE outletType;
    public String location;

    public Outlet(int outletID, String outletName, String outletDisplayName, OutletType.OUTLET_TYPE outletType, String location) {
        this.outletID = outletID;
        this.outletName = outletName;
        this.outletDisplayName = outletDisplayName;
        this.outletType = outletType;
        this.location = location;
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
}
