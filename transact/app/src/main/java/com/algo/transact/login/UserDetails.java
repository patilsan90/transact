package com.algo.transact.login;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.Serializable;

import static com.algo.transact.login.UserDetails.LOGIN_OTIONS.FB;
import static com.algo.transact.login.UserDetails.LOGIN_OTIONS.GMAIL;
import static com.algo.transact.login.UserDetails.LOGIN_OTIONS.OTHER;

/**
 * Created by Sandeep Patil on 17/4/17.
 */

public class UserDetails implements Serializable {

    public static String USERDETAILS = "UserDetails";
    public static final String TransactPREFERENCES = "TranPref" ;

    public String displayName = ""; // For ex: Sandeep Patil
    public String firstName= ""; // For ex:: Sandeep
    public String familyName= ""; // For ex:: Patil
    public String emailID= "";
    public String countryCode= "";
    public String mobNo= "";
    public String password= "";
    public String createdAt= "";
    public String updatedAt= "";

    public String sessionID;
    //public Uri profilePhotoURL;
    public String profilePhotoURL= "";
    public LOGIN_OTIONS loggedInUsing;
    public String dob= "";
    public String gender= "";

    static UserDetails details;

    public UserDetails()
    {

    }

 public static UserDetails  getInstance()
    {
        if(details==null)
            details = new UserDetails();
        return details;
    }
    public void setUserPreferences(Activity activity) {

        SharedPreferences sharedpreferences = activity.getSharedPreferences(TransactPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        Gson gson = new Gson();
        editor.putString(USERDETAILS, gson.toJson(this));
        editor.apply();

    }

    public static UserDetails getUserPreferences(Activity activity) {

        SharedPreferences sharedpreferences = activity.getSharedPreferences(TransactPREFERENCES, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        UserDetails details =  gson.fromJson(sharedpreferences.getString(USERDETAILS,""), UserDetails.class);
        return details;
    }

    public void setUserPreferences(Activity activity, JSONObject jsonUserObject) {

        SharedPreferences sharedpreferences = activity.getSharedPreferences(TransactPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        Gson gson = new Gson();
        editor.putString(USERDETAILS, gson.toJson(this));
        editor.apply();

    }
    public static void signOut(Activity activity) {

        SharedPreferences sharedpreferences = activity.getSharedPreferences(TransactPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.remove(USERDETAILS);
        editor.apply();
    }

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

