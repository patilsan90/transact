package com.algo.transact.home.shopatshop.data_retrivals;

import com.algo.transact.home.shopatshop.data_beans.Cart;
import com.algo.transact.home.shopatshop.data_beans.ShopDetails;

import java.util.ArrayList;

/**
 * Created by sandeep on 5/8/17.
 */

public class DataRetriver {

    private static DataRetriver shopper;
    private int shopID;
    private DataRetriver()
    {

    }
public static DataRetriver getInstance()
    {
        if(shopper==null)
            shopper=new DataRetriver();
        return shopper;
    }

    public ArrayList<ShopDetails> retriveClosest10Shops()
    {
        ArrayList<ShopDetails> shopsList =new ArrayList<ShopDetails>();

        shopsList.add(new ShopDetails("BigBaz HSR",1));
        shopsList.add(new ShopDetails("BigBaz Mar",2));
        shopsList.add(new ShopDetails("BigBaz Agr",3));
        shopsList.add(new ShopDetails("BigBaz Bel",4));
        shopsList.add(new ShopDetails("BigBaz Silk",5));
        shopsList.add(new ShopDetails("BigBaz BTM",6));


        return shopsList;
    }

    public ArrayList<Cart> retriveStoredCarts()
    {
        return  CartsFactory.getInstance().getCarts();
    }
    public Cart retriveCart(int shopID)
    {
        return  CartsFactory.getInstance().getCart(shopID);
    }



}
