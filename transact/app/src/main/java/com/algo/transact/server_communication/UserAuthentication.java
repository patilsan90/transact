package com.algo.transact.server_communication;

import com.algo.transact.login.SessionInfo;

import java.io.File;

/**
 * Created by Sandeep Patil on 8/5/17.
 */

public class UserAuthentication {

    static UserAuthentication authentication;

    private UserAuthentication() {

    }

    public static UserAuthentication getInstance() {
        if (authentication == null)
            authentication = new UserAuthentication();
        return authentication;
    }

    public String verifyMobileNumber(SessionInfo info) {
        String mobNumber = verifyMobileNumberFromServer(info);
        return mobNumber;

    }

    public boolean authenticateUser(File sessionFile) {
        //todo ::
        // this function contains temporary logic
        // which need to be replaced once server setup is done.
        if (sessionFile.exists())
            return true;

        return false;
    }


    private String verifyMobileNumberFromServer(SessionInfo info) {

        /*
        * Todo
        * Send email id to our own server and return mobile number if verified earlier
        * else return null
        * */
        return null;

    }
}
