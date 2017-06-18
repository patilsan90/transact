package com.algo.transact.login;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by Sandeep Patil on 17/4/17.
 */

public class SessionInfo implements Serializable {

    public String displayName; // For ex: Sandeep Patil
    public String firstName; // For ex:: Sandeep
    public String familyName; // For ex:: Patil
    public String emailID;
    public String mobNo;
    public String password;
    public String sessionID;
    public Uri profilePhotoURL;
    public LOGIN_OTIONS logged_in_by;

    public enum LOGIN_OTIONS {FB, GMAIL, OTHER}
}

