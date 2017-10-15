package com.algo.transact.networksdk.service.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Kapil on 15/10/2017.
 */

public interface RetrofitServices {
    @Headers({
            "Content-Type: application/json"
    })
/*
Here we have to add the all methods to call the web service
* */
    //ToDo Remove this dummy api
    // This is just a dummy api name
    @POST("api/user/token")
    Call<ResponseBody> loginUser();

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("")
            .addConverterFactory(GsonConverterFactory.create())
            .client(LogingInterceptor.client)
            .build();
}
