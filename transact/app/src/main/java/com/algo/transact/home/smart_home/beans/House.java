package com.algo.transact.home.smart_home.beans;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.algo.transact.login.User;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by patilsp on 10/14/2017.
 */

public class House {

    private int house_id;
    private String house_name;
    private int owner_id;
    private String authentication_code;
    private boolean has_multi_users;


    public int getHouse_id() {
        return house_id;
    }

    public void setHouse_id(int house_id) {
        this.house_id = house_id;
    }

    public String getHouse_name() {
        return house_name;
    }

    public void setHouse_name(String house_name) {
        this.house_name = house_name;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public String getAuthentication_code() {
        return authentication_code;
    }

    public void setAuthentication_code(String authentication_code) {
        this.authentication_code = authentication_code;
    }
}

