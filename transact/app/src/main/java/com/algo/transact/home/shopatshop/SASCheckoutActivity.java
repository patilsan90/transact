package com.algo.transact.home.shopatshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.algo.transact.AppConfig.AppState;
import com.algo.transact.R;
import com.algo.transact.home.shopatshop.mycart.MyCartAdapter;
import com.instamojo.android.Instamojo;
import com.instamojo.android.models.Order;

public class SASCheckoutActivity extends AppCompatActivity {

    ListView checkout_cart_list;
    private MyCartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sas_checkout);
        checkout_cart_list = (ListView) findViewById(R.id.checkout_cart_list);
        MyCartAdapter.list_type= AppState.LIST_TYPE.CHECKOUT_CART;
        cartAdapter = new MyCartAdapter(this);
        checkout_cart_list.setAdapter(cartAdapter);
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
}
