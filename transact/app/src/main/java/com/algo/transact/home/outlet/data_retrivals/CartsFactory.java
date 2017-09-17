package com.algo.transact.home.outlet.data_retrivals;

import com.algo.transact.home.outlet.OutletType;
import com.algo.transact.home.outlet.data_beans.Cart;
import com.algo.transact.home.outlet.data_beans.Category;
import com.algo.transact.home.outlet.data_beans.Item;
import com.algo.transact.home.outlet.data_beans.Outlet;
import com.algo.transact.home.outlet.data_beans.SubCategory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by sandeep on 6/8/17.
 */

public class CartsFactory {
    private ArrayList<Cart> cartsList = new ArrayList<Cart>();
    private static CartsFactory cartsFactory;
    private CartsFactory()
    {
      //  cartsList =createCartsList();
    }

    static int temp_count;
    public static CartsFactory getInstance()
    {
        if(cartsFactory == null)
            cartsFactory =new CartsFactory();
        return cartsFactory;

    }

    public ArrayList<Cart> getCarts()
    {
        return cartsList;
    }

    public String getShopDisplayName(int shopId)
    {
        for (int i = 0; i < cartsList.size(); i++) {
            if(cartsList.get(i).getOutletID()==shopId)
                return cartsList.get(i).getOutletDisplayName();
        }
        return null;
    }
    public String getShopName(int shopId)
    {
        for (int i = 0; i < cartsList.size(); i++) {
            if(cartsList.get(i).getOutletID()==shopId)
                return cartsList.get(i).getOutletName();
        }
        return null;
    }


/*
    private ArrayList<Cart> createCartsList()
    {
        ArrayList<Cart> cartsList = new ArrayList<Cart>();

        cartsList.add(createTestCart("Big B1", "Big Bazar", 1));
        cartsList.add(createTestCart("Big B2", "Big Bazar", 2));

        return cartsList;
    }

    private Cart createTestCart2(String displayName, String shopName, int shopID)
    {
        ArrayList<Item> itemList = new ArrayList<Item>();
        //  String item_id, String item_name, double actual_cost, double discounted_cost, int item_quantity
        SubCategory sc = new SubCategory(new Category(1, 11, null, "1st Floor"), 111, null);
        itemList.add(new Item(sc, "23"+(temp_count++), "Refined"+shopID, 250, 230, Item.ITEM_QUANTITY_TYPE.MILLILITERS,500, 1));

        Outlet outlet = new Outlet(shopID, shopName, displayName, OutletType.OUTLET_TYPE.GROCERY_STORE, "HSR Sector 1, HSR Layout");
        Cart cart =new Cart(outlet);
        //cart.outletID=shopID;
        //cart.outletDisplayName = displayName;
        //cart.outletName = shopName;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd_HHmmss");
        cart.cartCreationDateTime = sdf.toString();
        cart.cartList= itemList;

        return  cart;
    }
*/

    public Cart getCart(Outlet outlet) {

        for (int i = 0; i < cartsList.size(); i++) {
            if(cartsList.get(i).getOutletID()== outlet.getOutletID())
                return cartsList.get(i);
        }
        Cart cart=new Cart(outlet);
        //cart.outletID = outlet.getOutletID();
        //cart.outletDisplayName = outlet.getOutletDisplayName();
        //cart.outletName = outlet.getOutletName();

        cartsList.add(cart);

        return cart;
    }

}
