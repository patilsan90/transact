package com.algo.transact.home.smart_home.beans;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import com.google.gson.Gson;

/**
 * Created by patilsp on 11/5/2017.
 */

public class SmartHomeConfig {

    private static final String HOMECONFIGDETAILS = "SmartHomeConfig";
    private static final String TransactPREFERENCES = "TranPref";

    VIEW defaultView;

    public void setDefaultView(VIEW defaultView) {
        this.defaultView = defaultView;
    }

    public enum VIEW {
        EXPAND_VIEW,
        LIST_VIEW
    }

    public VIEW getDefaultView() {
        return defaultView;
    }

    public static SmartHomeConfig getUserPreferences(Activity activity) {

        SharedPreferences sharedpreferences = activity.getSharedPreferences(TransactPREFERENCES, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        SmartHomeConfig config = gson.fromJson(sharedpreferences.getString(HOMECONFIGDETAILS, ""), SmartHomeConfig.class);
        return config;
    }

    public void setUserPreferences(Activity activity) {

        SharedPreferences sharedpreferences = activity.getSharedPreferences(TransactPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        Gson gson = new Gson();
        editor.putString(HOMECONFIGDETAILS, gson.toJson(this));
        editor.apply();
    }
}
