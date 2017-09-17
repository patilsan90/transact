package com.algo.transact.home.outlet.data_retrivals;

import com.algo.transact.home.outlet.OutletType;
import com.algo.transact.home.outlet.data_beans.Cart;
import com.algo.transact.home.outlet.data_beans.Category;
import com.algo.transact.home.outlet.data_beans.Item;
import com.algo.transact.home.outlet.data_beans.Outlet;
import com.algo.transact.home.outlet.data_beans.SubCategory;

import java.util.ArrayList;

/**
 * Created by sandeep on 5/8/17.
 */

public class DataRetriver {

    static int tempCount;
    public static ArrayList<Outlet> retriveClosest10Shops() {
        ArrayList<Outlet> shopsList = new ArrayList<Outlet>();

//        int outletID, String outletName, String outletDisplayName, OutletType.OUTLET_TYPE outletType
        shopsList.add(new Outlet(1, "BigBaz HSR", "BigBazaar", OutletType.OUTLET_TYPE.GROCERY_STORE, "HSR Sector 1, HSR Layout"));
        shopsList.add(new Outlet(2, "BigBaz Mar", "BigBazaar", OutletType.OUTLET_TYPE.GROCERY_STORE, "HSR Sector 1, HSR Layout"));
        shopsList.add(new Outlet(3, "BigBaz Agr", "BigBazaar", OutletType.OUTLET_TYPE.GROCERY_STORE, "HSR Sector 2, HSR Layout"));
        shopsList.add(new Outlet(4, "BigBaz Bel", "BigBazaar", OutletType.OUTLET_TYPE.GROCERY_STORE, "HSR Sector 2, HSR Layout"));
        shopsList.add(new Outlet(5, "BigBaz Silk", "BigBazaar", OutletType.OUTLET_TYPE.GROCERY_STORE, "HSR Sector 2, HSR Layout"));
        shopsList.add(new Outlet(6, "BigBaz BTM", "BigBazaar", OutletType.OUTLET_TYPE.GROCERY_STORE, "HSR Sector 3, HSR Layout"));
        shopsList.add(new Outlet(7, "Dmart BTM1", "DMart", OutletType.OUTLET_TYPE.GROCERY_STORE, "BTM Sector 1, HSR Layout"));
        shopsList.add(new Outlet(8, "BigBaz BTM2", "DMart", OutletType.OUTLET_TYPE.GROCERY_STORE, "BTM Sector 1, HSR Layout"));
        shopsList.add(new Outlet(9, "BigBaz BTM3", "DMart", OutletType.OUTLET_TYPE.GROCERY_STORE, "BTM Sector 1, HSR Layout"));
        shopsList.add(new Outlet(10, "EasyCart BTM4", "EasyCart", OutletType.OUTLET_TYPE.GROCERY_STORE, "BTM Sector 1, HSR Layout"));
        shopsList.add(new Outlet(11, "EasyCart BTM5", "EasyCart", OutletType.OUTLET_TYPE.GROCERY_STORE, "BTM Sector 1, HSR Layout"));
        shopsList.add(new Outlet(12, "EasyCart BTM6", "EasyCart", OutletType.OUTLET_TYPE.GROCERY_STORE, "BTM Sector 2, HSR Layout"));
        shopsList.add(new Outlet(13, "EasyCart BTM7", "EasyCart", OutletType.OUTLET_TYPE.GROCERY_STORE, "BTM Sector 2, HSR Layout"));

        return shopsList;
    }

    public static ArrayList<Cart> retriveStoredCarts() {
        return CartsFactory.getInstance().getCarts();
    }

/*    public static Cart retriveCart(Outlet shopID) {
//        return CartsFactory.getInstance().getCart(shopID);
        return CartsFactory.getInstance().getCart();

    }*/

    public static Item getItemDetailsFromShop(int shopID, String itemID) {
        SubCategory sc = new SubCategory(new Category(1, 11, null, "1st Floor P Wing"), 111, null);
        Item temporary_item = new Item(sc, itemID, "Example "+(++tempCount), 250, 230, Item.ITEM_QUANTITY_TYPE.GRAM,500, 1);
        return temporary_item;
    }
}
