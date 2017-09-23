package com.algo.transact.home.outlet.data_retrivals;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Looper;
import android.util.Log;

import com.algo.transact.AppConfig.AppConfig;
import com.algo.transact.AppConfig.AppState;
import com.algo.transact.home.outlet.data_beans.Cart;
import com.algo.transact.home.outlet.data_beans.Outlet;
import com.algo.transact.login.UserDetails;
import com.google.gson.Gson;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import static android.R.attr.path;

/**
 * Created by sandeep on 6/8/17.
 */

public class CartsFactory {

    public static final String TransactCartPREFERENCES = "TranCartPref" ;
    public static final String TransactCart = "TranCart" ;
    private static final int MAX_CARTS = 10;

    private ArrayList<Cart> cartsList = new ArrayList<Cart>();

    transient private static CartsFactory cartsFactory;
    transient private Activity activity;
    transient File path;
    transient File file;

    private CartsFactory(Activity activity)
    {
        this.activity = activity;
      //  cartsList =createCartsList();
     }

    public void storeCarts()
    {
  /*      Thread thread = new Thread() {
            @Override
            public void run() {

                Looper.prepare();

   */
  while (cartsList.size()>MAX_CARTS)
  {
      cartsList.remove(0);
      Log.i(AppState.TAG, "Class: " + cartsFactory.getClass().getSimpleName() + " Method: " + new Object() {
      }.getClass().getEnclosingMethod().getName()+" Remove extra carts > 10 ");

  }
  SharedPreferences sharedpreferences = activity.getSharedPreferences(TransactCartPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                Gson gson = new Gson();
                editor.putString(TransactCart, gson.toJson(this));
                editor.apply();
                Log.i(AppState.TAG, "Class: " + cartsFactory.getClass().getSimpleName() + " Method: " + new Object() {
                }.getClass().getEnclosingMethod().getName()+" Object Saved  "+gson.toJson(this));

/*

            }
        };

        thread.start();
*/

    }

    public static CartsFactory getInstance(Activity activity)
    {

        if(cartsFactory == null)
        {
            cartsFactory =new CartsFactory(activity);
            SharedPreferences sharedpreferences = activity.getSharedPreferences(TransactCartPREFERENCES, Context.MODE_PRIVATE);
            Gson gson = new Gson();
            CartsFactory details =  gson.fromJson(sharedpreferences.getString(TransactCart,""), CartsFactory.class);
            if(details!=null)
            {
                cartsFactory = details;
                cartsFactory.activity = activity;
                Log.i(AppState.TAG, "Class: " + cartsFactory.getClass().getSimpleName() + " Method: " + new Object() {
                }.getClass().getEnclosingMethod().getName()+" GSON exists new object");
            }


            Log.i(AppState.TAG, "Class: " + cartsFactory.getClass().getSimpleName() + " Method: " + new Object() {
            }.getClass().getEnclosingMethod().getName()+" Creating new object");
        }

        Log.i(AppState.TAG, "Class: " + cartsFactory.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        return cartsFactory;
    }

    public ArrayList<Cart> getCarts()
    {
        return cartsList;
    }

    public Cart getCart(Outlet outlet) {

        for (int i = 0; i < cartsList.size(); i++) {
            if(cartsList.get(i).getOutletID()== outlet.getOutletID())
                return cartsList.get(i);
        }
        Cart cart=new Cart(outlet);
        //cart.outletID = outlet.getOutletID();
        //cart.outletDisplayName = outlet.getOutletDisplayName();
        //cart.outletName = outlet.getOutletName();

       // cartsList.add(cart);
        cartsList.add(0, cart);
        return cart;
    }

}
