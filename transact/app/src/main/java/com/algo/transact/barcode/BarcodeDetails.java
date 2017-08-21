package com.algo.transact.barcode;

import android.util.Log;

import com.algo.transact.AppConfig.AppState;
import com.algo.transact.AppConfig.OutletType;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by sandeep on 6/8/17.
 */

public class BarcodeDetails implements Serializable {

    public static final String ACT_TP = "AT"; //Action Type
    public static final String MOB_NO = "MN"; //Mobile Number
    public static final String OUT_TP = "OT"; //Outlet Type
    public static final String OUT_ID = "OI"; //Outlet Id
    public static final String ITM_ID = "II"; //Item ID

public  enum ACTION_TYPE
    {
        WALLET_PAY,
        OUTLET_SELECTOR,
        OUTLET_ITEM_SELECTOR;
    }

    private  ACTION_TYPE actionType;
    private OutletType.OUTLET_TYPE outletType;
    private int outletID;
    private String itemID;
    private String mobileNo;

/*    public BarcodeDetails(BarcodeDetails.ACTION_TYPE actionType, OutletType.OUTLET_TYPE outletType, int outletID, int itemID) {
        this.actionType = actionType;
        this.outletType = outletType;
        this.outletID = outletID;
        this.itemID = itemID;
    }*/

    public BarcodeDetails.ACTION_TYPE getActionType() {
        return actionType;
    }

    public OutletType.OUTLET_TYPE getOutletType() {
        return outletType;
    }

    public int getOutletID() {
        return outletID;
    }

    public String getItemID() {
        return itemID;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public static BarcodeDetails getCodeObject(String code)
    {
        BarcodeDetails barcodeDetails = new BarcodeDetails();
        JSONObject jObj = null;

        try {
            jObj = new JSONObject(code);
            barcodeDetails.actionType = ACTION_TYPE.values() [jObj.getInt(BarcodeDetails.ACT_TP)];

            switch (barcodeDetails.actionType)
            {
                case WALLET_PAY:
                    barcodeDetails.mobileNo = jObj.getString(BarcodeDetails.MOB_NO);
                    break;
                case OUTLET_SELECTOR:
                    barcodeDetails.outletType = OutletType.OUTLET_TYPE.values()[jObj.getInt(BarcodeDetails.OUT_TP)];
                    barcodeDetails.outletID = jObj.getInt(BarcodeDetails.OUT_ID);
                    break;
                case OUTLET_ITEM_SELECTOR:
                    barcodeDetails.outletType = OutletType.OUTLET_TYPE.values()[jObj.getInt(BarcodeDetails.OUT_TP)];
                    barcodeDetails.outletID = jObj.getInt(BarcodeDetails.OUT_ID);
                    barcodeDetails.itemID = jObj.getString(BarcodeDetails.ITM_ID);
                    break;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(AppState.TAG,"Exception :: "+e.getStackTrace());
            return null;
        }

        return barcodeDetails;
    }

    @Override
    public String toString() {
        return "ActionType: "+actionType +"\n MobileNo: "+mobileNo+"\n OutletType: "+outletType+"\n OutletID: "+outletID+"\n ItemID: "+itemID;
    }

}
