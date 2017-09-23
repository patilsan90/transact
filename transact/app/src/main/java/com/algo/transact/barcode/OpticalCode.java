package com.algo.transact.barcode;

import android.util.Log;

import com.algo.transact.AppConfig.AppState;
import com.algo.transact.home.outlet.OutletType;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by sandeep on 6/8/17.
 */

public class OpticalCode implements Serializable {

 public  enum ACT_TP
    {
        WALLET_PAY,
        OUTLET_SELECTOR,
        OUTLET_ITEM_SELECTOR;
    }

    private  ACT_TP at;  //Action Type
    private OutletType.OUTLET_TYPE ot; //Outlet Type
    private int oi;   //Outlet Id
    private String ii; //Item ID
    private String mn;  //Mobile Number

    @Override
    public String toString() {
        return "ActionType: "+at +"\n MobileNo: "+mn+"\n OutletType: "+ot+"\n OutletID: "+oi+"\n ItemID: "+ii;
    }


    public ACT_TP getActionType() {
        return at;
    }

    public OutletType.OUTLET_TYPE getOutletType() {
        return ot;
    }

    public int getOutletId() {
        return oi;
    }

    public String getItemId() {
        return ii;
    }

    public String getMobileNo() {
        return mn;
    }
}
