package com.algo.transact.login;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.io.Serializable;

import static com.algo.transact.login.User.LOGIN_OTIONS.FB;
import static com.algo.transact.login.User.LOGIN_OTIONS.GMAIL;
import static com.algo.transact.login.User.LOGIN_OTIONS.OTHER;

/**
 * Created by Sandeep Patil on 17/4/17.
 */

public class User implements Serializable {

    private static final String USERDETAILS = "User";
    private static final String TransactPREFERENCES = "TranPref";

    @SerializedName("display_name")
    public String displayName = ""; // For ex: Sandeep Patil

    @SerializedName("first_name")
    public String firstName = ""; // For ex:: Sandeep

    @SerializedName("family_name")
    public String familyName = ""; // For ex:: Patil

    @SerializedName("email_id")
    public String emailID = "";

    @SerializedName("country_code")
    public String countryCode = "";

    @SerializedName("primary_mobile_number")
    public String mobileNo = "";

    @SerializedName("password")
    public String password = "";

    @SerializedName("created_at")
    public String createdAt = "";

    @SerializedName("updated_at")
    public String updatedAt = "";

    @SerializedName("profile_photo_url")
    public String profilePhotoURL = "";

    @SerializedName("login_type")
    public LOGIN_OTIONS loginType;

    @SerializedName("date_of_birth")
    public String dob = "";

    @SerializedName("gender")
    public GENDER gender;

    @SerializedName("notification_token")
    String notification_token;

    private static User details;

    public String sessionID;

    public User() {

    }

    public enum GENDER {
        MALE,
        FEMALE
    }

    public static User getInstance() {
        if (details == null)
            details = new User();
        return details;
    }

    public void setUserPreferences(Activity activity) {

        SharedPreferences sharedpreferences = activity.getSharedPreferences(TransactPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        Gson gson = new Gson();
        editor.putString(USERDETAILS, gson.toJson(this));
        editor.apply();
    }

    public static User getUserPreferences(Activity activity) {

        SharedPreferences sharedpreferences = activity.getSharedPreferences(TransactPREFERENCES, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        User user = gson.fromJson(sharedpreferences.getString(USERDETAILS, ""), User.class);
        return user;
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

        return "User Details:: displayName: " + displayName + "  \nfirstName: " + firstName + " \nfamilyName:: " + familyName + "\nemailID::  " + emailID + " \ncountryCode  " + countryCode + " \nmobileNo :: " + mobileNo + " \nPassword:: " + password + "  " + createdAt + "   " + updatedAt;

    }


    public String loggedInUsingToSting(LOGIN_OTIONS opt) {
        switch (opt) {
            case FB:
                return "FB";
            case GMAIL:
                return "GMAIL";
            default:
                return "OTHER";
        }

    }

    public LOGIN_OTIONS loggedInUsingToEnum(String opt) {
        switch (opt) {
            case "FB":
                return FB;
            case "GMAIL":
                return GMAIL;
            default:
                return OTHER;
        }

    }

    public String getDisplayName() {
        return displayName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getEmailID() {
        return emailID;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public String getPassword() {
        return password;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getProfilePhotoURL() {
        return profilePhotoURL;
    }

    public GENDER getGender() {
        return gender;
    }

    public String getSessionID() {
        return sessionID;
    }
}

