package com.algo.transact.home.outlet.data_beans;

/**
 * Created by sandeep on 6/8/17.
 */

public class SubCategoryItem {

    public String categoryName;
    public int categoryID ;
    public int subCategoryID ;

    public SubCategoryItem(String categoryName,  int subCategoryID,int categoryID) {
        this.categoryName = categoryName;
        this.categoryID = categoryID;
        this.subCategoryID = subCategoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public int getSubCategoryID() {
        return subCategoryID;
    }
}

