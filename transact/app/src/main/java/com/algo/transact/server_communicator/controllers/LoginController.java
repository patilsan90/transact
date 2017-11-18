package com.algo.transact.server_communicator.controllers;

import android.util.Log;

import com.algo.transact.AppConfig.AppConfig;
import com.algo.transact.login.User;
import com.algo.transact.server_communicator.base.ILoginServiceAPI;
import com.algo.transact.server_communicator.base.ServerConfiguration;
import com.algo.transact.server_communicator.listener.ILoginListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by patilsp on 11/2/2017.
 */

public class LoginController {

    private static final String TAG = AppConfig.TAG;

    public static void login(User user, final ILoginListener listener) {
        ServerConfiguration.LoggerSet();
        ILoginServiceAPI retrofitServices = ServerConfiguration.retrofit.create(ILoginServiceAPI.class);
        Call<User> loginCall = retrofitServices.login(user);
        loginCall.enqueue(new LoginCallback(listener));
    }

    public static void register(User user, final ILoginListener listener) {
        ServerConfiguration.LoggerSet();
        ILoginServiceAPI retrofitServices = ServerConfiguration.retrofit.create(ILoginServiceAPI.class);
        Call<User> loginCall = retrofitServices.register(user);
        loginCall.enqueue(new LoginCallback(listener));
    }

    public static void updateProfile(User user, final ILoginListener listener) {
        ServerConfiguration.LoggerSet();
        ILoginServiceAPI retrofitServices = ServerConfiguration.retrofit.create(ILoginServiceAPI.class);
        Call<User> loginCall = retrofitServices.updateProfile(user);
        loginCall.enqueue(new LoginCallback(listener));
    }

    static class LoginCallback implements Callback<User> {

        ILoginListener listener;

        public LoginCallback(ILoginListener listener) {
            this.listener = listener;
        }

        @Override
        public void onResponse(Call<User> call, Response<User> response) {
            Log.i(AppConfig.TAG, response.code() + " Login API onResponse " + response.body());
            listener.onSuccess(response.body());
        }

        @Override
        public void onFailure(Call<User> call, Throwable t) {
            Log.i(AppConfig.TAG, "Login API onFailure");
            listener.onFailure();
        }
    }
}
