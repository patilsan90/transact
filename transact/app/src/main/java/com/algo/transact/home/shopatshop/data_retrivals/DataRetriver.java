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
        shopsList.add(new ShopDetails("BigBaz BTM", 6));

        return shopsList;
    }

    public static ArrayList<Cart> retriveStoredCarts() {
        return CartsFactory.getInstance().getCarts();
    }

    public static Cart retriveCart(int shopID) {
        return CartsFactory.getInstance().getCart(shopID);
    }

    public static Item getItemDetailsFromShop(int shopID, String itemID) {
        Item temporary_item = new Item(1,20, itemID, "Example "+(++tempCount), 250, 230, Item.ITEM_QUANTITY_TYPE.GRAM,500, 0);
        return temporary_item;
    }
}
