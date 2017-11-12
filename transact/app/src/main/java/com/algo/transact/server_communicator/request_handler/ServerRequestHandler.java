package com.algo.transact.server_communicator.request_handler;

import com.algo.transact.home.smart_home.beans.House;
import com.algo.transact.home.smart_home.beans.Peripheral;
import com.algo.transact.home.smart_home.beans.Room;
import com.algo.transact.login.User;
import com.algo.transact.server_communicator.controllers.LoginController;
import com.algo.transact.server_communicator.controllers.SmartHomeController;
import com.algo.transact.server_communicator.listener.ILoginListener;
import com.algo.transact.server_communicator.listener.ISmartHomeListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Kapil on 15/10/2017.
 */

public class ServerRequestHandler{

    public static void login(User user, ILoginListener listener) {
        LoginController.login(user, listener);
    }

    public static void register(User user, ILoginListener listener) {
        LoginController.register(user, listener);
    }

    public static void updateProfile(User user, ILoginListener listener) {
        LoginController.updateProfile(user, listener);
    }


    public static void getHouse(User user, ISmartHomeListener listener) {
        SmartHomeController.getHouse(user, listener);
    }

    public static void getPeripherals(Room room, ISmartHomeListener listener) {
        SmartHomeController.getPeripherals(room, listener);
    }

    public static void getWaterLevelPeripherals(House house, ISmartHomeListener listener) {
        SmartHomeController.getWaterLevelPeripherals(house, listener);
    }


    public static void updatePeripheralStatus(Room room, Peripheral peripheral, ISmartHomeListener listener)
    {
        SmartHomeController.updatePeripheralStatus(room, peripheral, listener);
    }

    public static void getPeripheralStatus(Peripheral peripheral, ISmartHomeListener listener)
    {
        SmartHomeController.getPeripheralStatus(peripheral, listener);
    }

    public static void updatePeripherals(ArrayList<Peripheral> alPeripherals, final ISmartHomeListener listener)
    {
        SmartHomeController.updatePeripherals(alPeripherals, listener);
    }

}
