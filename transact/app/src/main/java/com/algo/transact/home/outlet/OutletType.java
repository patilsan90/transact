package com.algo.transact.home.outlet;

/**
 * Created by sandeep on 6/8/17.
 */

public class OutletType {

    public static String SHOP;

    public enum OUTLET_TYPE
    {
        GROCERY_STORE,
        GENERAL_STORE,
        RESTAURANT;

        public static OUTLET_TYPE fromInteger(int x) {
            switch(x) {
                case 0:
                    return GROCERY_STORE;
                case 1:
                    return GENERAL_STORE;
                case 2:
                    return RESTAURANT;
            }
            return null;
        }
    }
}
