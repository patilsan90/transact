package com.algo.transact.home.shopatshop.data_beans;

/**
 * Created by sandeep on 5/8/17.
 */

public class ShopDetails {

    public int getShopID() {
        return shopID;
    }

    public String getShopName() {
        return shopName;
    }

    public String getShopDisplayName() {
        return shopDisplayName;
    }

    int shopID;
    String shopName;
    String shopDisplayName;

public ShopDetails(String shopDisplayName, int shopID)
    {
        this.shopDisplayName=shopDisplayName;
        this.shopID = shopID;
        shopName="Test Shop Name";

    }
}
