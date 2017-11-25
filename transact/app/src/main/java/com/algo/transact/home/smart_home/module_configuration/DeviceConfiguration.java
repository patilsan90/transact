package com.algo.transact.home.smart_home.module_configuration;

/**
 * Created by patilsp on 11/18/2017.
 */

public class DeviceConfiguration {
    private static String BASE_URL_SMART_HOME_DEV_CONFIG = "config";
    private static String BASE_URL_SMART_HOME_GET_UPDATE = "get_updates";


    private String wifi_ssid;
    private String wifi_psw;
    private String owner_id;
    private String transact_server_url;
    private String update_url = BASE_URL_SMART_HOME_GET_UPDATE;
    private String configure_url = BASE_URL_SMART_HOME_DEV_CONFIG;


    public static final String CONFIG_BASE_URL = "http://10.10.10.1:1337";

    public void setWifi_name(String wifi_ssid) {
        this.wifi_ssid = wifi_ssid;
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
