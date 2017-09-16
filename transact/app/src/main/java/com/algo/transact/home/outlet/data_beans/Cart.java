package com.algo.transact.home.outlet.data_beans;

import com.algo.transact.home.outlet.OutletType;

import java.util.ArrayList;

/**
 * Created by sandeep on 6/8/17.
 */

public class Cart extends Outlet{

    public String cartCreationDateTime;
    public ArrayList<Item> cartList =new ArrayList<>();

    public Cart(Outlet outlet) {
        super(outlet.outletID, outlet.outletName, outlet.outletDisplayName, outlet.outletType, outlet.location);
    }

    public String getCartCreationDateTime() {
        return cartCreationDateTime;
    }

    public ArrayList<Item> getCartList() {
        return cartList;
    }

}
