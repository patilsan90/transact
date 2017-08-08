package com.algo.transact.home.shopatshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.algo.transact.AppConfig.AppState;
import com.algo.transact.AppConfig.IntentPutExtras;
import com.algo.transact.R;
import com.algo.transact.generic_structures.GenericAdapter;
import com.algo.transact.generic_structures.IGenericAdapter;
import com.algo.transact.home.shopatshop.data_beans.Cart;
import com.algo.transact.home.shopatshop.data_beans.CartItem;
import com.algo.transact.home.shopatshop.data_retrivals.CartsFactory;
import com.algo.transact.home.shopatshop.mycart.MyCartAdapter;
import com.instamojo.android.Instamojo;
import com.instamojo.android.models.Order;

public class SASCheckoutActivity extends AppCompatActivity implements IGenericAdapter{

    ListView lvcheckoutCart;
    private int shopID;
    private String requestType;
    private Cart cart;
    private GenericAdapter genericAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sas_checkout);
        lvcheckoutCart = (ListView) findViewById(R.id.checkout_cart_list);

        requestType = getIntent().getStringExtra(IntentPutExtras.REQUEST_TYPE);
        shopID = getIntent().getIntExtra(IntentPutExtras.ID, 0);

        cart = CartsFactory.getInstance().getCart(shopID);
        genericAdapter = new GenericAdapter(this, this, lvcheckoutCart, cart.getCartList(), R.layout.list_item_view_mycart_checkout);

       // MyCartAdapter.list_type= AppState.LIST_TYPE.CHECKOUT_CART;
        //cartAdapter = new MyCartAdapter(this);
        //lvcheckoutCart.setAdapter(cartAdapter);

        Instamojo.initialize(this);
    }

    public void onCheckout(View v)
    {
        Log.i(AppState.TAG,"Checkout cart Initiated");
        Order order = new Order("Tran", "1234", "San_transact", "san27deep@gmail.com", "9423306174", "11", "Trasact_san");
        order.setWebhook("http://www.futureinduction.com/webhook/");
        if(!validateOrder(order))
        {
            Log.e("App", "Order not valid");
            return;
        }

    }

    boolean validateOrder(Order order)
    {
        //Validate the Order
        if (!order.isValid()){
            //oops order validation failed. Pinpoint the issue(s).

            if (!order.isValidName()){
                Log.e("App", "Buyer name is invalid");
            }

            if (!order.isValidEmail()){
                Log.e("App", "Buyer email is invalid");
            }

            if (!order.isValidPhone()){
                Log.e("App", "Buyer phone is invalid");
            }

            if (!order.isValidAmount()){
                Log.e("App", "Amount is invalid");
            }

            if (!order.isValidDescription()){
                Log.e("App", "description is invalid");
            }

            if (!order.isValidTransactionID()){
                Log.e("App", "Transaction ID is invalid");
            }

            if (!order.isValidRedirectURL()){
                Log.e("App", "Redirection URL is invalid");
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
        CartItem cartItem =(CartItem) listItem;
        TextView item_view = (TextView) view.findViewById(R.id.item_name);
        item_view.setText(" " + cartItem.getItem_name());

        item_view = (TextView) view.findViewById(R.id.actual_cost);
        item_view.setText("Actual Cost: " + cartItem.getActual_cost());

        item_view = (TextView) view.findViewById(R.id.discounted_cost);
        item_view.setText("Disc. Cost: " + cartItem.getDiscounted_cost());

        item_view = (TextView) view.findViewById(R.id.total_cost);
        double total_cost = cartItem.getDiscounted_cost() * cartItem.getItem_quantity();

        item_view.setText("Total Cost: " + total_cost);

        TextView total_items_view = (TextView) view.findViewById(R.id.total_items);


        total_items_view.setText("Total Items: " + cartItem.getItem_quantity());
    /*    Log.i(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());
    */
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
