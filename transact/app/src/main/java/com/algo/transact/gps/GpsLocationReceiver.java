package com.algo.transact.gps;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;

import com.algo.transact.AppConfig.AppConfig;
import com.google.android.gms.location.LocationListener;

/**
 * Created by patilsp on 8/8/2017.
 */

public class GpsLocationReceiver extends BroadcastReceiver implements LocationListener {

    public static GPSListener listener;
    @Override
    public void onLocationChanged(Location location) {
        Log.i(AppConfig.TAG,"GpsLocationReceiver onLocationChanged");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(AppConfig.TAG,"GpsLocationReceiver onReceive");
        if (intent.getAction().matches("android.location.PROVIDERS_CHANGED")) {
            // react on GPS provider change action
            Log.i(AppConfig.TAG,"GpsLocationReceiver onReceive PROVIDERS_CHANGED");
            if(listener != null)
            listener.onGPSenable();
        }
    }

}