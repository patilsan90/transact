package com.algo.transact.home.outlet.outlet_front;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.algo.transact.AppConfig.AppConfig;
import com.algo.transact.AppConfig.IntentPutExtras;
import com.algo.transact.R;
import com.algo.transact.generic_structures.GenericAdapter;
import com.algo.transact.generic_structures.IGenericAdapter;
import com.algo.transact.home.outlet.data_beans.Cart;
import com.algo.transact.home.outlet.data_beans.Item;
import com.algo.transact.home.outlet.data_beans.Outlet;
import com.algo.transact.home.outlet.data_retrivals.CartsFactory;
import com.instamojo.android.Instamojo;
import com.instamojo.android.models.Order;

import java.util.ArrayList;

public class MyCartCheckoutActivity extends AppCompatActivity implements IGenericAdapter {

    ListView lvcheckoutCart;
    private int shopID;
    private String requestType;
    private Cart cart;
    private GenericAdapter genericAdapter;
    private TextView tvCartTotal;
    private Outlet outlet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sas_checkout);
        lvcheckoutCart = (ListView) findViewById(R.id.checkout_cart_list);

        requestType = getIntent().getStringExtra(IntentPutExtras.DATA_TYPE);
        shopID = getIntent().getIntExtra(IntentPutExtras.ID, 0);
        outlet  = (Outlet) getIntent().getSerializableExtra(IntentPutExtras.OUTLET_OBJECT);

        Log.i(AppConfig.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " Outlet OBJ:: "+(outlet == null));


        tvCartTotal = (TextView) findViewById(R.id.cart_total);

        cart = CartsFactory.getInstance(this).getCart(outlet);

        genericAdapter = new GenericAdapter(this, this, lvcheckoutCart, cart.getCartList(), R.layout.list_item_view_mycart_checkout);

        int noOfItems = cart.getCartList().size();
        double cart_total = 0;
        Item item;
        for (int i = 0; i < noOfItems; i++) {
            item = cart.getCartList().get(i);
            cart_total = cart_total + item.getDiscounted_cost() * item.getItem_count();
        }
        tvCartTotal.setText("Cart Total: " + cart_total + " Rs.");
        Instamojo.initialize(this);
    }

    public void goBack(View v) {
    this.finish();
    }

    public void onCheckout(View v) {
        Log.i(AppConfig.TAG, "Checkout cart Initiated");
        Order order = new Order("Tran", "1234", "San_transact", "san27deep@gmail.com", "9423306174", "11", "Trasact_san");
        order.setWebhook("http://www.futureinduction.com/webhook/");
        if (!validateOrder(order)) {
            Log.e("AppConfig", "Order not valid");
            return;
        }

    }

    boolean validateOrder(Order order) {
        //Validate the Order
        if (!order.isValid()) {
            //oops order validation failed. Pinpoint the issue(s).

            if (!order.isValidName()) {
                Log.e("AppConfig", "Buyer name is invalid");
            }

            if (!order.isValidEmail()) {
                Log.e("AppConfig", "Buyer email is invalid");
            }

            if (!order.isValidPhone()) {
                Log.e("AppConfig", "Buyer phone is invalid");
            }

            if (!order.isValidAmount()) {
                Log.e("AppConfig", "Amount is invalid");
            }

            if (!order.isValidDescription()) {
                Log.e("AppConfig", "description is invalid");
            }

            if (!order.isValidTransactionID()) {
                Log.e("AppConfig", "Transaction ID is invalid");
            }

            if (!order.isValidRedirectURL()) {
                Log.e("AppConfig", "Redirection URL is invalid");
            }

            if (!order.isValidWebhook()) {
                Toast.makeText(this, "Webhook URL is invalid", Toast.LENGTH_SHORT).show();
            }

            return false;
        }
        return true;
    }

    @Override
    public View addViewItemToList(View view, Object listItem, int index) {
        Item item = (Item) listItem;
        TextView item_view = (TextView) view.findViewById(R.id.item_name);
        item_view.setText("" + item.getItem_name());

/*
        item_view = (TextView) view.findViewById(R.id.actual_cost);
        item_view.setText("Actual Cost: " + item.getActual_cost());
*/

        item_view = (TextView) view.findViewById(R.id.item_quantity);
        item_view.setText("("+item.getItem_quantity()+" "+item.qtTypeInString(item.getItem_form())+")");

        item_view = (TextView) view.findViewById(R.id.discounted_cost);
        item_view.setText("Cost per item: " + item.getDiscounted_cost());

        item_view = (TextView) view.findViewById(R.id.total_cost);
        double total_cost = item.getDiscounted_cost() * item.getItem_count();

        item_view.setText("Total Cost: " + total_cost);

        TextView total_items_view = (TextView) view.findViewById(R.id.total_items);
        total_items_view.setText("Total Items: " + item.getItem_count());

  /*      Log.i(AppConfig.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());
    */
        return view;
    }

    @Override
    public void listUpdateCompleteNotification(ArrayList list, GenericAdapter genericAdapter) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
