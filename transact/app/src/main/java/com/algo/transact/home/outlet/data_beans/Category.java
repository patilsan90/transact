package com.algo.transact.home.outlet.data_beans;

/**
 * Created by sandeep on 6/8/17.
 */

public class Category {

    public int outletID;
    public String categoryName;
    public int categoryID;
    public String categoryLocation;

    public Category(int outletID, int categoryID, String categoryName, String categoryLocation) {

        this.outletID = outletID;
        this.categoryName = categoryName;
        this.categoryID = categoryID;
        this.categoryLocation = categoryLocation;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public int getOutletID() {
        return outletID;
    }

    public String getCategoryLocation() {
        return categoryLocation;
    }
}
