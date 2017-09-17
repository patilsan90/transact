package com.algo.transact.home.outlet.data_retrivals;

import com.algo.transact.home.outlet.data_beans.Category;
import com.algo.transact.home.outlet.data_beans.Item;
import com.algo.transact.home.outlet.data_beans.SubCategory;

import java.util.ArrayList;

/**
 * Created by sandeep on 6/8/17.
 */

public class CatalogueRetriver {

    static ArrayList<Category> categories;  // these are temporary static declarations
    static  ArrayList<SubCategory> subCategories; // these are temporary static declarations

  public static ArrayList<Category> getCategories(int shopID)
    {
        categories = new ArrayList<>();
        categories.add(new Category(1, 10, "Category 1"));
        categories.add(new Category(1, 11, "Category 2"));
        categories.add(new Category(1, 12, "Category 3"));
        categories.add(new Category(1, 13, "Category 4"));
        return categories;
    }

    public static ArrayList<SubCategory> getSubCategories(int shopID, int categoryID)
    {
        subCategories = new ArrayList<>();

        if(categoryID == 10) {
            subCategories.add(new SubCategory(categories.get(0), 111, "Sub Category 1 1"));
            subCategories.add(new SubCategory(categories.get(0), 112, "Sub Category 1 2"));
            subCategories.add(new SubCategory(categories.get(0), 113, "Sub Category 1 3"));
            subCategories.add(new SubCategory(categories.get(0), 114, "Sub Category 1 4"));
            subCategories.add(new SubCategory(categories.get(0), 115, "Sub Category 1 5"));
        }
        if(categoryID == 11) {
            subCategories.add(new SubCategory(categories.get(1), 111, "Sub Cat 2 1"));
            subCategories.add(new SubCategory(categories.get(1), 112, "Sub Cat 2 2"));
            subCategories.add(new SubCategory(categories.get(1), 113, "Sub Cat 2 3"));
            subCategories.add(new SubCategory(categories.get(1), 114, "Sub Cat 2 4"));
            subCategories.add(new SubCategory(categories.get(1), 115, "Sub Cat 2 5"));
        }
        if(categoryID == 12) {
            subCategories.add(new SubCategory(categories.get(2), 111, "Sub Cat 3 1"));
            subCategories.add(new SubCategory(categories.get(2), 112, "Sub Cat 3 2"));
            subCategories.add(new SubCategory(categories.get(2), 113, "Sub Cat 3 3"));
            subCategories.add(new SubCategory(categories.get(2), 114, "Sub Cat 3 4"));
            subCategories.add(new SubCategory(categories.get(2), 115, "Sub Cat 3 5"));
        }
        return subCategories;
    }

    public static ArrayList<Item> getCatalogueItems(int shopID, int categoryID, int subCategoryID)
    {
        ArrayList<Item> subCategoryItems =new ArrayList<Item>();

        // retrive data from server here
        if(categoryID ==10 && subCategoryID == 111) {
            subCategoryItems.add(new Item(subCategories.get(0), "21", "Item21 " + shopID, 250, 250, Item.ITEM_QUANTITY_TYPE.GRAM, 500, 0));
            subCategoryItems.add(new Item(subCategories.get(0), "22", "Item22 " + shopID, 25, 23, Item.ITEM_QUANTITY_TYPE.KG, 1, 0));
            subCategoryItems.add(new Item(subCategories.get(0), "23", "Item23 " + shopID, 20, 17, Item.ITEM_QUANTITY_TYPE.KG, 2, 0));
            subCategoryItems.add(new Item(subCategories.get(0), "24", "Item24 " + shopID, 10, 8, Item.ITEM_QUANTITY_TYPE.MILLILITERS, 500, 0));
            subCategoryItems.add(new Item(subCategories.get(0), "25", "Item25 " + shopID, 10, 8, Item.ITEM_QUANTITY_TYPE.LITERS, 2, 0));
        }
        if(categoryID ==10 && subCategoryID == 112) {
            subCategoryItems.add(new Item(subCategories.get(1), "26", "Item26 " + shopID, 250, 250, Item.ITEM_QUANTITY_TYPE.GRAM, 500, 0));
            subCategoryItems.add(new Item(subCategories.get(1), "27", "Item27 " + shopID, 25, 23, Item.ITEM_QUANTITY_TYPE.KG, 1, 0));
            subCategoryItems.add(new Item(subCategories.get(1), "28", "Item28 " + shopID, 20, 17, Item.ITEM_QUANTITY_TYPE.KG, 2, 0));
            subCategoryItems.add(new Item(subCategories.get(1), "29", "Item29 " + shopID, 10, 8, Item.ITEM_QUANTITY_TYPE.MILLILITERS, 500, 0));
            subCategoryItems.add(new Item(subCategories.get(1), "30", "Item30 " + shopID, 10, 8, Item.ITEM_QUANTITY_TYPE.LITERS, 2, 0));
        }

        return subCategoryItems;
    }
}
