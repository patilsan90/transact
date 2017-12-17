package com.algo.transact.home.smart_home.module_configuration;

/**
 * Created by patilsp on 11/25/2017.
 */

public class ConfigStatus {

    String mac_addr;
    boolean is_success;

    public String getMac_addr() {
        return mac_addr;
    }

    public void setMac_addr(String mac_addr) {
        this.mac_addr = mac_addr;
    }

    public boolean isIs_success() {
        return is_success;
    }

    public void setIs_success(boolean is_success) {
        this.is_success = is_success;
    }

}
