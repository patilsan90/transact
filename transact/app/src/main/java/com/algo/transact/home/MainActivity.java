package com.algo.transact.home;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.algo.transact.R;
import com.algo.transact.login.LoginFragment;

public class MainActivity extends AppCompatActivity {

    private HomeFragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout ll_sat = (LinearLayout) findViewById(R.id.shop_at_shop);
       // ll_sat.setOnClickListener();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        homeFragment = new HomeFragment();
        fragmentTransaction.add(R.id.home_page_frame, homeFragment);
        fragmentTransaction.commit();
    }

}
