package com.algo.transact.home.shopatshop.data_beans;

import java.util.ArrayList;

/**
 * Created by sandeep on 6/8/17.
 */

public class Cart {
    public int shopID;
    public String cartCreationDateTime;
    public ArrayList<CartItem> cartList;
    public String cartTestShopDisplayName; // This is testing variable, May be we need to delete it
    public String cartTestShopName; // This is testing variable, May be we need to delete it

    public String getCartCreationDateTime() {
        return cartCreationDateTime;
    }

    public ArrayList<CartItem> getCartList() {
        return cartList;
    }

    public String getCartShopDisplayName() {
        return cartTestShopDisplayName;
    }

    public String getCartShopName() {
        return cartTestShopName;
    }

    public int getShopID() {
        return shopID;
    }

}
