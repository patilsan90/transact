package com.algo.transact.home;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.algo.transact.AppConfig.AppState;
import com.algo.transact.R;
import com.algo.transact.home.shopatshop.ShopAtShop;

/**
 * Created by sandeep on 18/6/17.
 */

public class HomeClickListener implements View.OnClickListener {
    HomeFragment homeFragment;
    public HomeClickListener(HomeFragment homeFragment) {
        this.homeFragment = homeFragment;
    }

    @Override
    public void onClick(View v) {
        if(R.id.shop_at_shop == v.getId())
        {
            Log.i(AppState.TAG, "Clicked on shop @ shop");
            Intent myIntent = new Intent(homeFragment.getActivity(), ShopAtShop.class);
            //below line is for testing purpose
          //  Intent myIntent = new Intent(homeFragment.getActivity(), ShopAtShop.class);
            myIntent.putExtra("name", "Sample name"); //Optional parameter pass parameters
            homeFragment.getActivity().startActivity(myIntent);

        }
    }
}
