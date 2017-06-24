package com.algo.transact.home.shopatshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.algo.transact.AppState;
import com.algo.transact.R;
import com.algo.transact.home.shopatshop.mycart.MyCartAdapter;

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
    }
}
