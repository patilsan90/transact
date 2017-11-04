package com.algo.transact.server_communicator.request_handler;

import com.algo.transact.login.User;
import com.algo.transact.server_communicator.controllers.LoginController;
import com.algo.transact.server_communicator.listener.ILoginListener;

/**
 * Created by Kapil on 15/10/2017.
 */

public class ServerRequestHandler {

    public static void login(User user, ILoginListener listener) {
        LoginController.login(user, listener);
    }

    public static void register(User user, ILoginListener listener) {
        LoginController.register(user, listener);
    }

    public static void updateProfile(User user, ILoginListener listener) {
        LoginController.updateProfile(user, listener);
    }


}
