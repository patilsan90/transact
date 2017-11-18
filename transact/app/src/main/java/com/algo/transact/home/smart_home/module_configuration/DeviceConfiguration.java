package com.algo.transact.home.smart_home.module_configuration;

/**
 * Created by patilsp on 11/18/2017.
 */

public class DeviceConfiguration {
    String wifi_name;
    String wifi_psw;
    String owner_id;
    String transact_server_url;

    public static final String CONFIG_BASE_URL = "http://10.10.10.1:1337";

    public void setWifi_name(String wifi_name) {
        this.wifi_name = wifi_name;
    }

    public void setWifi_psw(String wifi_psw) {
        this.wifi_psw = wifi_psw;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public void setTransact_server_url(String transact_server_url) {
        this.transact_server_url = transact_server_url;
    }
}
