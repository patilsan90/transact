package com.algo.transact.networksdk.service.transactcall;

import android.content.Context;

import com.algo.transact.networksdk.service.TransactControllerFactory;

/**
 * Created by Kapil on 15/10/2017.
 */

public class TransactCallImpl extends TransactCall {
    private TransactControllerFactory transactControllerFactory;

    public TransactCallImpl(TransactControllerFactory transactControllerFactory) {
        this.transactControllerFactory = transactControllerFactory;
    }

    @Override
    public void callLogin() {
//        transactControllerFactory.callLogin();
    }

}
