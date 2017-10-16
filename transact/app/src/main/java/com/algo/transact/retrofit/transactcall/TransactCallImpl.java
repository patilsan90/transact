package com.algo.transact.retrofit.transactcall;

import com.algo.transact.retrofit.TransactControllerFactory;

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
