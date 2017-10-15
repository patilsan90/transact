package com.algo.transact.networksdk.service.transactcall;

import android.content.Context;

import com.algo.transact.networksdk.service.TransactControllerFactory;

/**
 * Created by Kapil on 15/10/2017.
 */

public abstract class TransactCall {
    private static TransactCall transactCall;

    /*
    This is the initialization point for all the api calls
    * */
    public static TransactCall getInstance(Context context) {
        if (transactCall == null) {
            transactCall = new TransactCallImpl(new TransactControllerFactory());
        }
        return transactCall;
    }
    public abstract void callLogin(/*Required Interface*/);

}
