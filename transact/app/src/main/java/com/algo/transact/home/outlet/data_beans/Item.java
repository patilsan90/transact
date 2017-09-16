package com.algo.transact.home.outlet.data_beans;

import java.io.Serializable;

/**
 * Created by Sandeep Patil on 16/4/17.
 */

public class Item implements Serializable{

    private String item_id;
    private String item_name;

    private int item_category;
    private int item_subCategory;

    private double actual_cost;
    private double discounted_cost;

    private ITEM_QUANTITY_TYPE item_form;// 1: Solid, 2: Liquid
    private float item_quantity;

    private int item_count;

    public enum ITEM_QUANTITY_TYPE{
        LITERS,
        MILLILITERS,
        GRAM,
        KG
    }

    public int getItem_category() {
        return item_category;
    }

    public int getItem_subCategory() {
        return item_subCategory;
    }

    public Item(int item_category, int item_subCategory, String item_id, String item_name, double actual_cost, double discounted_cost, ITEM_QUANTITY_TYPE item_form, float item_quantity, int item_count) {
        this.item_category = item_category;
        this.item_subCategory = item_subCategory;

        this.item_id = item_id;
        this.item_name = item_name;
        this.actual_cost = actual_cost;
        this.discounted_cost = discounted_cost;

        this.item_form = item_form;
        this.item_count = item_count;

        this.item_quantity = item_quantity;

    }

    public String getItem_id() {
        return item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public double getActual_cost() {
        return actual_cost;
    }

    public double getDiscounted_cost() {
        return discounted_cost;
    }

    public float getItem_quantity() {
        return item_quantity;
    }

    public void increaseItem_count() {
        item_count++;
    }

    public void decreaseItem_count() {
        item_count--;
    }

    public void setItem_quantity(int quantity) {
        item_quantity=quantity;
    }

    public ITEM_QUANTITY_TYPE getItem_form() {
        return item_form;
    }

    public int getItem_count() {
        return item_count;
    }

    public void setItem_count(int item_count) {
        this.item_count = item_count;
    }

    public String qtTypeInString(ITEM_QUANTITY_TYPE type)
    {
        String str;
        switch (type)
        {
            case GRAM:
                return "gm";
            case KG:
                return "kg";
            case LITERS:
                return "ltr";
            case MILLILITERS:
                return "ml";
            default:
                return "Unknown";
        }
    }
}
