package com.algo.transact.home.outlet.data_beans;

/**
 * Created by sandeep on 6/8/17.
 */

public class CategoryItem {

    public String categoryName;
    public int categoryID ;
    public CategoryItem(String categoryName, int categoryID) {

        this.categoryName = categoryName;
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getCategoryID() {
        return categoryID;
    }


}
