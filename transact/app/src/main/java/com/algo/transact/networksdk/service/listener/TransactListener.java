package com.algo.transact.networksdk.service.listener;

/**
 * Created by Kapil on 15/10/2017.
 */

public interface TransactListener {
    /*
    These are the common call back methods for every api
     */
    void onSuccess();
    void onFailure();
    void onError();
}
