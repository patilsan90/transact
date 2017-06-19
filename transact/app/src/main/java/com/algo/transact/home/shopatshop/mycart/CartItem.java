package com.algo.transact.home.shopatshop.mycart;

/**
 * Created by Sandeep Patil on 16/4/17.
 */

public class CartItem {

    private String item_id;
    private String item_name;
    private double actual_cost;
    private double discounted_cost;
    private int item_quantity;

    public CartItem(String item_id, String item_name, double actual_cost, double discounted_cost, int item_quantity) {
        this.item_id = item_id;
        this.item_name = item_name;
        this.item_quantity = item_quantity;
        this.actual_cost = actual_cost;
        this.discounted_cost = discounted_cost;
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

    public int getItem_quantity() {
        return item_quantity;
    }

    public void increaseItem_quantity() {
        item_quantity++;
    }

    public void decreaseItem_quantity() {
        item_quantity--;
    }

}
