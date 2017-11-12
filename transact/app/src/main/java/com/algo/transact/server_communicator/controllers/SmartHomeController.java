package com.algo.transact.server_communicator.controllers;

import android.util.Log;

import com.algo.transact.AppConfig.AppConfig;
import com.algo.transact.home.smart_home.beans.House;
import com.algo.transact.home.smart_home.beans.Peripheral;
import com.algo.transact.home.smart_home.beans.Room;
import com.algo.transact.home.smart_home.beans.SmartHomeCollector;
import com.algo.transact.login.User;
import com.algo.transact.server_communicator.base.ISmartHomeServiceAPI;
import com.algo.transact.server_communicator.base.ResponseStatus;
import com.algo.transact.server_communicator.base.ServerConfiguration;
import com.algo.transact.server_communicator.listener.ISmartHomeListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by patilsp on 11/2/2017.
 */

public class SmartHomeController {

    private static final String TAG = AppConfig.TAG;

    public static void getHouse(User user, final ISmartHomeListener listener) {
        ServerConfiguration.LoggerSet();
        ISmartHomeServiceAPI retrofitServices = ServerConfiguration.retrofit.create(ISmartHomeServiceAPI.class);
        Call<SmartHomeCollector> call = retrofitServices.getHouse(user);
        call.enqueue(new Callback<SmartHomeCollector>() {
            @Override
            public void onResponse(Call<SmartHomeCollector> call, Response<SmartHomeCollector> response) {
                Log.i(AppConfig.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
                }.getClass().getEnclosingMethod().getName());
                if (response.isSuccessful())
                    listener.onGetHouse(response.body());
            }

            @Override
            public void onFailure(Call<SmartHomeCollector> call, Throwable t) {
                Log.i(AppConfig.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
                }.getClass().getEnclosingMethod().getName());

                listener.onFailure();
            }
        });
    }

     public static void getPeripherals(Room room, final ISmartHomeListener listener) {
        ServerConfiguration.LoggerSet();
        ISmartHomeServiceAPI retrofitServices = ServerConfiguration.retrofit.create(ISmartHomeServiceAPI.class);
        Call<ArrayList<Peripheral>> call = retrofitServices.getPeripherals(room);
        call.enqueue(new Callback<ArrayList<Peripheral>>() {
            @Override
            public void onResponse(Call<ArrayList<Peripheral>> call, Response<ArrayList<Peripheral>> response) {
                if (response.isSuccessful())
                    listener.onGetPeripherals(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Peripheral>> call, Throwable t) {
                listener.onFailure();
            }
        });
    }

    public static void getWaterLevelPeripherals(House house, final ISmartHomeListener listener) {
        ServerConfiguration.LoggerSet();
        ISmartHomeServiceAPI retrofitServices = ServerConfiguration.retrofit.create(ISmartHomeServiceAPI.class);
        Call<ArrayList<Peripheral>> call = retrofitServices.getWaterLevelPeripherals(house);
        call.enqueue(new Callback<ArrayList<Peripheral>>() {
            @Override
            public void onResponse(Call<ArrayList<Peripheral>> call, Response<ArrayList<Peripheral>> response) {
                if (response.isSuccessful())
                    listener.onGetPeripherals(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Peripheral>> call, Throwable t) {
                listener.onFailure();
            }
        });
    }

    public static void updatePeripheralStatus(Room room, Peripheral peripheral, final ISmartHomeListener listener) {
        ServerConfiguration.LoggerSet();
        ISmartHomeServiceAPI retrofitServices = ServerConfiguration.retrofit.create(ISmartHomeServiceAPI.class);
        Call<Peripheral> call = retrofitServices.updatePeripheralStatus(peripheral);
        call.enqueue(new Callback<Peripheral>() {
            @Override
            public void onResponse(Call<Peripheral> call, Response<Peripheral> response) {
                if (response.isSuccessful())
                    listener.onUpdatePeripheralStatus(response.body());
            }

            @Override
            public void onFailure(Call<Peripheral> call, Throwable t) {
                listener.onFailure();
            }
        });
    }

    public static void getPeripheralStatus(Peripheral peripheral, final ISmartHomeListener listener) {
        ServerConfiguration.LoggerSet();
        ISmartHomeServiceAPI retrofitServices = ServerConfiguration.retrofit.create(ISmartHomeServiceAPI.class);
        Call<Peripheral> call = retrofitServices.getPeripheralStatus(peripheral);
        call.enqueue(new Callback<Peripheral>() {
            @Override
            public void onResponse(Call<Peripheral> call, Response<Peripheral> response) {
                if (response.isSuccessful())
                    listener.onUpdatePeripheralStatus(response.body());
            }

            @Override
            public void onFailure(Call<Peripheral> call, Throwable t) {
                listener.onFailure();
            }
        });
    }

    public static void updatePeripherals(ArrayList<Peripheral> alPeripherals, final ISmartHomeListener listener) {

        ServerConfiguration.LoggerSet();
        ISmartHomeServiceAPI retrofitServices = ServerConfiguration.retrofit.create(ISmartHomeServiceAPI.class);
        Call<ResponseStatus> call = retrofitServices.updatePeripherals(alPeripherals);
        call.enqueue(new Callback<ResponseStatus>() {
            @Override
            public void onResponse(Call<ResponseStatus> call, Response<ResponseStatus> response) {
                if (response.isSuccessful())
                    listener.onUpdatePeripherals(response.body());
            }

            @Override
            public void onFailure(Call<ResponseStatus> call, Throwable t) {
                listener.onFailure();
            }
        });
    }

    public static void updateRoom(Room room, final ISmartHomeListener listener) {

        ServerConfiguration.LoggerSet();
        ISmartHomeServiceAPI retrofitServices = ServerConfiguration.retrofit.create(ISmartHomeServiceAPI.class);
        Call<ResponseStatus> call = retrofitServices.updateRoom(room);
        call.enqueue(new Callback<ResponseStatus>() {
            @Override
            public void onResponse(Call<ResponseStatus> call, Response<ResponseStatus> response) {
                if (response.isSuccessful())
                    listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ResponseStatus> call, Throwable t) {
                listener.onFailure();
            }
        });
    }
}
