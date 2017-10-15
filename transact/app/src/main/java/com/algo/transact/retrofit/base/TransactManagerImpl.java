package com.algo.transact.retrofit.base;

import com.algo.transact.networksdk.service.service.LogingInterceptor;
import com.algo.transact.networksdk.service.service.RetrofitServices;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Kapil on 15/10/2017.
 */

public class TransactManagerImpl extends TransactManager {
    /*
    This is just a demo method to show the example how we are gonna
    to integrate other api calls.
    * */
    @Override
    public void callLogin() {
        new LogingInterceptor().LoggerSet();
        RetrofitServices retrofitServices = RetrofitServices.retrofit.create(RetrofitServices.class);
        Call<ResponseBody> loginCall = retrofitServices.loginUser();
        loginCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {

                } else {

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
}
