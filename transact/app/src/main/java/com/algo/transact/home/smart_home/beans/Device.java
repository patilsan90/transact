package com.algo.transact.home.smart_home.beans;

/**
 * Created by patilsp on 11/13/2017.
 */

public class Device {
    private int device_id;
    private int house_id;
    private String device_phy_id;
    private String dev_mac_addr;
    private String local_ip_addr;

    public int getDevice_id() {
        return device_id;
    }

    public int getHouse_id() {
        return house_id;
    }

    public String getDevice_phy_id() {
        return device_phy_id;
    }

    public String getDev_mac_addr() {
        return dev_mac_addr;
    }

    public String getLocal_ip_addr() {
        return local_ip_addr;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

    public void setHouse_id(int house_id) {
        this.house_id = house_id;
    }

    public void setDevice_phy_id(String device_phy_id) {
        this.device_phy_id = device_phy_id;
    }

    public void setDev_mac_addr(String dev_mac_addr) {
        this.dev_mac_addr = dev_mac_addr;
    }

    public void setLocal_ip_addr(String local_ip_addr) {
        this.local_ip_addr = local_ip_addr;
    }
}
