package com.algo.transact.server_communicator.listener;

import com.algo.transact.home.smart_home.beans.House;
import com.algo.transact.home.smart_home.beans.Peripheral;
import com.algo.transact.home.smart_home.beans.Room;
import com.algo.transact.home.smart_home.beans.SmartHomeCollector;
import com.algo.transact.login.User;
import com.algo.transact.server_communicator.base.ResponseStatus;

import java.util.ArrayList;

/**
 * Created by Kapil on 15/10/2017.
 */

public interface ISmartHomeListener {

    void onGetHouse(SmartHomeCollector collector);

    void onGetPeripherals(ArrayList<Peripheral> alPeripherals);

    void onUpdatePeripheralStatus(Peripheral peripheral);

    void onUpdatePeripherals(ResponseStatus responseStatus);

    void onFailure();

    void onSuccess(ResponseStatus status);

}