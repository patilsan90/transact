package com.algo.transact.server_communicator.base;

import com.algo.transact.home.smart_home.beans.House;
import com.algo.transact.home.smart_home.beans.Peripheral;
import com.algo.transact.home.smart_home.beans.Room;
import com.algo.transact.home.smart_home.beans.SHUser;
import com.algo.transact.home.smart_home.beans.SmartHomeCollector;
import com.algo.transact.home.smart_home.module_configuration.DeviceConfiguration;
import com.algo.transact.login.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Kapil on 15/10/2017.
 */

public interface ISmartHomeServiceAPI {
    @Headers({
            "Content-Type: application/json"
    })

    @POST("/smart_home/get_house")
    Call<SmartHomeCollector> getHouse(@Body User user);

    @POST("/smart_home/get_peripherals")
    Call<ArrayList<Peripheral>> getPeripherals(@Body Room room);

    @POST("/smart_home/get_water_level_peripherals")
    Call<ArrayList<Peripheral>> getWaterLevelPeripherals(@Body House house);

    @POST("/smart_home/get_peripheral_status")
    Call<Peripheral> getPeripheralStatus(@Body Peripheral peripheral);

    @POST("/smart_home/create_new_room")
    Call<Room> creatNewRoom(@Body Room room);

/*
    @Multipart
    @POST("/smart_home/update_peripheral_status")
    Call<Peripheral> updatePeripheralStatus(@Part("room") Room room,
                                            @Part("peripheral") Peripheral peripheral);
*/

    @POST("/smart_home/update_peripheral_status")
    Call<Peripheral> updatePeripheralStatus(@Body Peripheral peripheral);

    @POST("/smart_home/update_peripherals")
    Call<ResponseStatus> updatePeripherals(@Body ArrayList<Peripheral> alPeripherals);

    @POST("/smart_home/update_room")
    Call<ResponseStatus> updateRoom(@Body Room room);

    @POST("/smart_home/add_new_room")
    Call<Room> addNewRoom(@Body Room room);

    @POST("/smart_home/remove_user_sh_access")
    Call<SHUser> removeSHUser(@Body SHUser user);

    @POST("/smart_home/add_user_sh_access")
    Call<SHUser> addSHUser(@Body SHUser user);

    @POST("/smart_home/device/configure")
    Call<ResponseStatus> deviceConfigure(@Body DeviceConfiguration configuration);


}
