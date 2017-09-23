package com.algo.transact.AppConfig;

import com.algo.transact.home.HomeActivity;
import com.algo.transact.home.outlet.data_beans.Item;
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
    public HomeActivity homeActivity;


    private ArrayList<Item> itemsList;

    //public static boolean isMallSelected = false;

    private static AppState state;

    public static String sessionFile;
    public static String AppCacheFolder;

    //public SessionManager session;

    private AppState()
    {
        itemsList = new ArrayList<Item>();
    }

    public static AppState getInstance()
    {
        if(state == null)
         state = new AppState();

        return state;
    }

    public void addCartItem(Item item)
    {
        itemsList.add(item);

    }
    public ArrayList<Item> getCartItemList()
    {
        return itemsList;
    }

    }
