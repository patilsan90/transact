package com.algo.transact.server_communicator.listener;

import com.algo.transact.login.User;

/**
 * Created by Kapil on 15/10/2017.
 */

public interface ILoginListener {

    void onSuccess(User user);

    void onFailure();

}
