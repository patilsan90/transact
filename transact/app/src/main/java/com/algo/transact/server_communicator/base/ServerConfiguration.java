package com.algo.transact.server_communicator.base;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Kapil on 15/10/2017.
 */

public class ServerConfiguration {

    private static String BASE_URL = "http://192.168.1.7:8090";
    private static String BASE_URL_SMART_HOME = BASE_URL+"/smart_home";


    private static OkHttpClient client;

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(ServerConfiguration.LoggerSet())
            .build();

    public static OkHttpClient LoggerSet() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();
        return client;
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public static String getBaseUrlSmartHome() {
        return BASE_URL_SMART_HOME;
    }
}