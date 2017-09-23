package com.algo.transact.home.outlet.data_retrivals;

import com.algo.transact.home.outlet.data_beans.Category;
import com.algo.transact.home.outlet.data_beans.Item;
import com.algo.transact.home.outlet.data_beans.Outlet;
import com.algo.transact.home.outlet.data_beans.SubCategory;

import java.util.ArrayList;

/**
 * Created by sandeep on 5/8/17.
 */

public class ShopsRetriver {

    static int tempCount;
    public static ArrayList<Outlet> retriveClosest10Shops(Outlet.OUTLET_TYPE outletReqType) {

        ArrayList<Outlet> shopsList = new ArrayList<Outlet>();
        ArrayList<Outlet.WEEKDAY> outletCloseDays = new ArrayList<Outlet.WEEKDAY>();
        outletCloseDays.add(Outlet.WEEKDAY.SUNDAY);

       // Date openTIme = Calendar.getInstance().getTime();
/*
        Date openTIme = new Date();
        openTIme.setTime(10000); // in miliseconds
        Date closeTIme = new Date();
        openTIme.setTime(20000); // in miliseconds
*/
        long openTIme = 10000;
        long closeTIme = 30000;

        //int outletID, String outletName, String outletDisplayName, OutletType.OUTLET_TYPE outletType, String location, int minOrder, String currency, boolean providesDelivery, float deliveryRangeInKM, Time dailyOpenTime, Time dailyCloseTime, ArrayList<WEEKDAY> outletCloseDays
       if(outletReqType == Outlet.OUTLET_TYPE.RESTAURANT)
        shopsList.add(new Outlet(1, "Res HSR", "BigBazaar", Outlet.OUTLET_TYPE.RESTAURANT, "HSR Sector 1, HSR Layout", 250, "Rs", true, 2, openTIme, closeTIme, outletCloseDays));
       else if(outletReqType == Outlet.OUTLET_TYPE.GENERAL_STORE) {
           shopsList.add(new Outlet(2, "Gen1 Mar", "BigBazaar", Outlet.OUTLET_TYPE.GENERAL_STORE, "HSR Sector 1, HSR Layout", 0, "Rs", false, 2, openTIme, closeTIme, outletCloseDays));
           shopsList.add(new Outlet(3, "Ge2 Agr", "BigBazaar", Outlet.OUTLET_TYPE.GENERAL_STORE, "HSR Sector 2, HSR Layout", 300, "Rs", true, 2, openTIme, closeTIme, outletCloseDays));
       }
        else if(outletReqType == Outlet.OUTLET_TYPE.GROCERY_STORE) {
           shopsList.add(new Outlet(4, "BigBaz Bel", "BigBazaar", Outlet.OUTLET_TYPE.GROCERY_STORE, "HSR Sector 2, HSR Layout", 0, "Rs", false, 2, openTIme, closeTIme, outletCloseDays));
           shopsList.add(new Outlet(5, "BigBaz Silk", "BigBazaar", Outlet.OUTLET_TYPE.GROCERY_STORE, "HSR Sector 2, HSR Layout", 0, "Rs", false, 2, openTIme, closeTIme, outletCloseDays));
           shopsList.add(new Outlet(6, "BigBaz BTM", "BigBazaar", Outlet.OUTLET_TYPE.GROCERY_STORE, "HSR Sector 3, HSR Layout", 600, "Rs", true, 6, openTIme, closeTIme, outletCloseDays));
           shopsList.add(new Outlet(7, "Dmart BTM1", "DMart", Outlet.OUTLET_TYPE.GROCERY_STORE, "BTM Sector 1, HSR Layout", 0, "Rs", false, 2, openTIme, closeTIme, outletCloseDays));
           shopsList.add(new Outlet(8, "BigBaz BTM2", "DMart", Outlet.OUTLET_TYPE.GROCERY_STORE, "BTM Sector 1, HSR Layout", 300, "Rs", true, 2, openTIme, closeTIme, outletCloseDays));
           shopsList.add(new Outlet(9, "BigBaz BTM3", "DMart", Outlet.OUTLET_TYPE.GROCERY_STORE, "BTM Sector 1, HSR Layout", 300, "Rs", true, 2, openTIme, closeTIme, outletCloseDays));
       }
       else if(outletReqType == Outlet.OUTLET_TYPE.VEGETABLES) {
           shopsList.add(new Outlet(10, "EasyCart v BTM4", "EasyCart", Outlet.OUTLET_TYPE.VEGETABLES, "BTM Sector 1, HSR Layout", 200, "Rs", true, 2, openTIme, closeTIme, outletCloseDays));
           shopsList.add(new Outlet(11, "EasyCart v BTM5", "EasyCart", Outlet.OUTLET_TYPE.VEGETABLES, "BTM Sector 1, HSR Layout", 200, "Rs", true, 2, openTIme, closeTIme, outletCloseDays));
           shopsList.add(new Outlet(12, "EasyCart v BTM6", "EasyCart", Outlet.OUTLET_TYPE.VEGETABLES, "BTM Sector 2, HSR Layout", 200, "Rs", true, 2, openTIme, closeTIme, outletCloseDays));
           shopsList.add(new Outlet(13, "EasyCart v BTM7", "EasyCart", Outlet.OUTLET_TYPE.VEGETABLES, "BTM Sector 2, HSR Layout", 200, "Rs", true, 2, openTIme, closeTIme, outletCloseDays));
       }
        return shopsList;
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
