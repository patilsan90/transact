package com.algo.transact.AppConfig;

import com.algo.transact.home.MainActivity;
import com.algo.transact.home.shopatshop.data_beans.CartItem;
import com.algo.transact.home.offers.OfferItem;
import com.algo.transact.login.LoginActivity;

import java.util.ArrayList;

/**
 * Created by kapil on 14/4/17.
 */

public class AppState {

    public static enum LOGIN_STATE {LOGIN_VIEW, REGISTRATION_VIEW};
    public static enum LIST_TYPE  {NORMAL_CART,CHECKOUT_CART}

    public LOGIN_STATE loginState;

    public static String TAG = "TRANSACT APP";
    public LoginActivity loginActivity;
    public MainActivity mainActivity;


    private ArrayList<CartItem> cartItemsList;
    private ArrayList<OfferItem> offersList;

    //public static boolean isMallSelected = false;

    private static AppState state;

    public static String sessionFile;
    public static String AppCacheFolder;

    //public SessionManager session;

    private AppState()
    {
        cartItemsList = new ArrayList<CartItem>();
        offersList = new ArrayList<OfferItem>();
    }

    public static AppState getInstance()
    {
        if(state == null)
         state = new AppState();

        return state;
    }

    public void addCartItem(CartItem cartItem)
    {
        cartItemsList.add(cartItem);

    }
    public ArrayList<CartItem> getCartItemList()
    {
        return cartItemsList;
    }

    public void addOfferItem(OfferItem cartItem)
    {
        offersList.add(cartItem);
    }
    public ArrayList<OfferItem> getOfferItemList()
    {
        return offersList;
    }

}
