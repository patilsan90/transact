package com.algo.transact.home.outlet.data_beans;

/**
 * Created by sandeep on 6/8/17.
 */

public class SubCategory extends  Category{

    public int subCategoryID ;
    public String subCategoryName ;

    public SubCategory(Category category, int subCategoryID, String subCategoryName) {
        super(category.outletID, category.categoryID, category.categoryName);
        this.subCategoryName =subCategoryName;
        this.subCategoryID = subCategoryID;
    }

    public int getSubCategoryID() {
        return subCategoryID;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }
}

