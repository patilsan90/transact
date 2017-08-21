package com.algo.transact.home.shopatshop.data_retrivals;

import com.algo.transact.home.shopatshop.data_beans.Cart;
import com.algo.transact.home.shopatshop.data_beans.Item;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by sandeep on 6/8/17.
 */

public class CartsFactory {
    private ArrayList<Cart> cartsList;
    private static CartsFactory cartsFactory;
    private CartsFactory()
    {
        cartsList =createCartsList();
    }

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
            if(cartsList.get(i).getShopID()==shopId)
                return cartsList.get(i).getCartShopDisplayName();
        }
        return null;
    }
    public String getShopName(int shopId)
    {
        for (int i = 0; i < cartsList.size(); i++) {
            if(cartsList.get(i).getShopID()==shopId)
                return cartsList.get(i).getCartShopName();
        }
        return null;
    }


    private ArrayList<Cart> createCartsList()
    {
        ArrayList<Cart> cartsList = new ArrayList<Cart>();

        cartsList.add(createTestCart("Big B1", "Big Bazar", 1));
        cartsList.add(createTestCart("Big B2", "Big Bazar", 2));

        return cartsList;
    }

    private Cart createTestCart(String displayName, String shopName, int shopID)
    {
        ArrayList<Item> itemList = new ArrayList<Item>();
        //  String item_id, String item_name, double actual_cost, double discounted_cost, int item_quantity
        itemList.add(new Item(1,20, "21", "Badam"+shopID, 250, 230, Item.ITEM_QUANTITY_TYPE.GRAM,500, 2));
        itemList.add(new Item(1,20, "23", "Refined"+shopID, 250, 230, Item.ITEM_QUANTITY_TYPE.MILLILITERS,500, 1));

        Cart cart =new Cart();
        cart.shopID=shopID;
        cart.cartTestShopDisplayName = displayName;
        cart.cartTestShopName = shopName;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd_HHmmss");
        cart.cartCreationDateTime = sdf.toString();
        cart.cartList= itemList;
        return  cart;
    }

    public Cart getCart(int shopID) {

        for (int i = 0; i < cartsList.size(); i++) {
            if(cartsList.get(i).getShopID()==shopID)
                return cartsList.get(i);
        }
        Cart cart=new Cart();
        cart.shopID = shopID;
        cartsList.add(cart);
        return cart;
    }

}
