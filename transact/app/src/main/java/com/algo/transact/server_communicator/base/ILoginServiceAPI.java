package com.algo.transact.server_communicator.base;

import com.algo.transact.home.smart_home.beans.House;
import com.algo.transact.login.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Kapil on 15/10/2017.
 */

public interface ILoginServiceAPI {
    @Headers({
            "Content-Type: application/json"
    })
    /*
     * Here we have to add the all methods to call the web service
     */

    @POST("/user/login")
    Call<User> login(@Body User user);

    @POST("/user/register")
    Call<User> register(@Body User user);

    @POST("/user/update")
    Call<User> updateProfile(@Body User user);

}
