package com.algo.transact.home.smart_home.beans;

import java.io.Serializable;

/**
 * Created by patilsp on 10/14/2017.
 */

public class SHUser implements Serializable {

    String user_name;
    String mobile_number;
    int house_id;

    public String getUser_name() {
        return user_name;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public int getHouse_id() {
        return house_id;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public void setHouse_id(int house_id) {
        this.house_id = house_id;
    }
}
