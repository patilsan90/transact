package com.algo.transact.login;

import android.net.Uri;

import java.io.Serializable;

import static com.algo.transact.login.UserDetails.LOGIN_OTIONS.FB;
import static com.algo.transact.login.UserDetails.LOGIN_OTIONS.GMAIL;
import static com.algo.transact.login.UserDetails.LOGIN_OTIONS.OTHER;

/**
 * Created by Sandeep Patil on 17/4/17.
 */

public class UserDetails implements Serializable {

    public String displayName; // For ex: Sandeep Patil
    public String firstName; // For ex:: Sandeep
    public String familyName; // For ex:: Patil
    public String emailID;
    public String countryCode;
    public String mobNo;
    public String password;
    public String createdAt;
    public String updatedAt;

    public String sessionID;
    public Uri profilePhotoURL;
    public LOGIN_OTIONS loggedInUsing;

    public enum LOGIN_OTIONS {FB, GMAIL, OTHER}

    @Override
    public String toString() {

        return "User Details "+displayName+"  "+firstName+"  "+familyName+"  "+emailID+"  "+countryCode +"  "+mobNo +"  "+password +"  "+createdAt +"   "+updatedAt;

    }


    public String loggedInUsingToSting(LOGIN_OTIONS opt)
    {
        switch(opt) {
            case FB:
                return "FB";
            case GMAIL:
                return "GMAIL";
            default:
                return "OTHER";
        }

    }
    public LOGIN_OTIONS loggedInUsingToEnum(String opt)
    {
        switch(opt) {
            case "FB":
                return FB;
            case "GMAIL":
                return GMAIL;
            default:
                return OTHER;
        }

    }
}

