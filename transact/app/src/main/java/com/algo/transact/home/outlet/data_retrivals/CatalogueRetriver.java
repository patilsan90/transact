package com.algo.transact.home.outlet.data_retrivals;

import com.algo.transact.home.outlet.data_beans.Item;
import com.algo.transact.home.outlet.data_beans.CategoryItem;
import com.algo.transact.home.outlet.data_beans.SubCategoryItem;

import java.util.ArrayList;

/**
 * Created by sandeep on 6/8/17.
 */

public class CatalogueRetriver {


  public static ArrayList<CategoryItem> getCategories(int shopID)
    {
        ArrayList<CategoryItem> categoryItems =new ArrayList<CategoryItem>();

        categoryItems.add(new CategoryItem("Grocery", 10));
        categoryItems.add(new CategoryItem("Fruits & Vegetables", 11));
        categoryItems.add(new CategoryItem("Dairy Product", 12));
        categoryItems.add(new CategoryItem("Bakery Product", 13));
        return categoryItems;
    }

    public static ArrayList<SubCategoryItem> getSubCategories(int shopID, int categoryID)
    {
        ArrayList<SubCategoryItem> subCategoryItems =new ArrayList<SubCategoryItem>();

        subCategoryItems.add(new SubCategoryItem("Baking Items", 111, 10));
        subCategoryItems.add(new SubCategoryItem("Dry fruits", 112, 10));
        subCategoryItems.add(new SubCategoryItem("Edible Oil", 113, 10));
        subCategoryItems.add(new SubCategoryItem("Pulses", 114, 10));
        subCategoryItems.add(new SubCategoryItem("Ready to cook", 115, 10));
        subCategoryItems.add(new SubCategoryItem("Ready to cook2", 115, 10));
        subCategoryItems.add(new SubCategoryItem("Ready to cook3", 115, 10));
        subCategoryItems.add(new SubCategoryItem("Ready to cook4", 115, 10));
        subCategoryItems.add(new SubCategoryItem("Ready to cook5", 115, 10));
        subCategoryItems.add(new SubCategoryItem("Ready to cook6", 115, 10));
        subCategoryItems.add(new SubCategoryItem("Ready to cook7", 115, 10));

        return subCategoryItems;
    }

    public static ArrayList<Item> getItems(int shopID, int categoryID, int subCategoryID)
    {
        ArrayList<Item> subCategoryItems =new ArrayList<Item>();

        subCategoryItems.add(new Item(1,20, "21", "Badam"+shopID, 250, 230, Item.ITEM_QUANTITY_TYPE.GRAM,500, 0));
        subCategoryItems.add(new Item(1,20, "22", "Badam"+shopID, 250, 230, Item.ITEM_QUANTITY_TYPE.KG,1, 0));

        subCategoryItems.add(new Item(1,20, "22", "Pista"+shopID, 250, 230, Item.ITEM_QUANTITY_TYPE.KG,2, 0));

        subCategoryItems.add(new Item(1,20, "23", "Refined"+shopID, 250, 230, Item.ITEM_QUANTITY_TYPE.MILLILITERS,500, 0));
        subCategoryItems.add(new Item(1,20, "23", "Refined"+shopID, 250, 230, Item.ITEM_QUANTITY_TYPE.LITERS,2, 0));


        return subCategoryItems;
    }
}
