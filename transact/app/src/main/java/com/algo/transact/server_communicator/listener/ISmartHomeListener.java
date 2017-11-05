package com.algo.transact.server_communicator.listener;

import com.algo.transact.home.smart_home.beans.House;
import com.algo.transact.home.smart_home.beans.Peripheral;
import com.algo.transact.login.User;

import java.util.ArrayList;

/**
 * Created by Kapil on 15/10/2017.
 */

public interface ISmartHomeListener {

    void onGetHouse(House house);

    void onGetPeripherals(ArrayList<Peripheral> alPeripherals);

    void updatePeripheralStatus(Peripheral peripheral);

    void onFailure();

}
