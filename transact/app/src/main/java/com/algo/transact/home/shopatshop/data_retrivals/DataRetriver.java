package com.algo.transact.home.shopatshop.data_retrivals;

import com.algo.transact.home.shopatshop.data_beans.Cart;
import com.algo.transact.home.shopatshop.data_beans.Item;
import com.algo.transact.home.shopatshop.data_beans.ShopDetails;

import java.util.ArrayList;

/**
 * Created by sandeep on 5/8/17.
 */

public class DataRetriver {

    static int tempCount;
    public static ArrayList<ShopDetails> retriveClosest10Shops() {
        ArrayList<ShopDetails> shopsList = new ArrayList<ShopDetails>();

        shopsList.add(new ShopDetails("BigBaz HSR", 1));
        shopsList.add(new ShopDetails("BigBaz Mar", 2));
        shopsList.add(new ShopDetails("BigBaz Agr", 3));
        shopsList.add(new ShopDetails("BigBaz Bel", 4));
        shopsList.add(new ShopDetails("BigBaz Silk", 5));
        shopsList.add(new ShopDetails("BigBaz BTM", 7));
        shopsList.add(new ShopDetails("BigBaz BTM1", 8));
        shopsList.add(new ShopDetails("BigBaz BTM2", 9));
        shopsList.add(new ShopDetails("BigBaz BTM3", 10));
        shopsList.add(new ShopDetails("BigBaz BTM4", 11));
        shopsList.add(new ShopDetails("BigBaz BTM5", 12));
        shopsList.add(new ShopDetails("BigBaz BTM6", 13));
        shopsList.add(new ShopDetails("BigBaz BTM7", 14));


        return shopsList;
    }

    public static ArrayList<Cart> retriveStoredCarts() {
        return CartsFactory.getInstance().getCarts();
    }

    public static Cart retriveCart(int shopID) {
        return CartsFactory.getInstance().getCart(shopID);
    }

    public static Item getItemDetailsFromShop(int shopID, String itemID) {
        Item temporary_item = new Item(1,20, itemID, "Example "+(++tempCount), 250, 230, Item.ITEM_QUANTITY_TYPE.GRAM,500, 1);
        return temporary_item;
    }
}
