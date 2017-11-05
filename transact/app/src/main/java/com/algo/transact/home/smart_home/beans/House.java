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

    private static final String HOMEDETAILS = "SmartHome";
    private static final String TransactPREFERENCES = "TranPref";

    int id;
    String house_name;
    int owner_id;
    String authentication_code;
    ArrayList<Room> al_rooms = new ArrayList<>();

    public int getId() {
        return id;
    }

    public String getHouse_name() {
        return house_name;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public String getAuthentication_code() {
        return authentication_code;
    }

    public ArrayList<Room> getAl_rooms() {
        return al_rooms;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setHouse_name(String house_name) {
        this.house_name = house_name;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public void setAuthentication_code(String authentication_code) {
        this.authentication_code = authentication_code;
    }

    public void setAl_rooms(ArrayList<Room> al_rooms) {
        this.al_rooms = al_rooms;
    }

    public static House getUserPreferences(Activity activity) {

        SharedPreferences sharedpreferences = activity.getSharedPreferences(TransactPREFERENCES, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        House house = gson.fromJson(sharedpreferences.getString(HOMEDETAILS, ""), House.class);
        return house;
    }

    public void setUserPreferences(Activity activity) {

        SharedPreferences sharedpreferences = activity.getSharedPreferences(TransactPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        Gson gson = new Gson();
        editor.putString(HOMEDETAILS, gson.toJson(this));
        editor.apply();
    }
}
