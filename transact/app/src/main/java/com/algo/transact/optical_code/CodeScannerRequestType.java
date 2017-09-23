package com.algo.transact.optical_code;

/**
 * Created by patilsp on 9/23/2017.
 */

public class CodeScannerRequestType {

    public static String CODE_REQUEST_TYPE ="CODE_REQUEST_TYPE";

   public enum REQUEST_TYPE
    {
        REQ_PAY,
        REQ_SELECT_OUTLET,
        REQ_SELECT_OUTLET_ITEM
    }

}
