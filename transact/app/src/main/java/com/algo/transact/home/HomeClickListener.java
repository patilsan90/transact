package com.algo.transact.home;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.algo.transact.AppConfig.AppState;
import com.algo.transact.AppConfig.IntentPutExtras;
import com.algo.transact.AppConfig.IntentRequestResponseType;
import com.algo.transact.R;
import com.algo.transact.home.shopatshop.OutletSelectorActivity;
import com.algo.transact.home.shopatshop.ShopAtShop;
import com.algo.transact.home.shopatshop.ShopScannerActivity;

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
          //  Intent ssaIntent = new Intent(homeFragment.getActivity(), ShopScannerActivity.class);
            Intent ssaIntent = new Intent(homeFragment.getActivity(), OutletSelectorActivity.class);
            //below line is for testing purpose
          //  Intent myIntent = new Intent(homeFragment.getActivity(), ShopAtShop.class);
            ssaIntent.putExtra(IntentPutExtras.REQUEST_TYPE, IntentPutExtras.REQUEST_SELECT_SHOP); //Optional parameter pass parameters
            homeFragment.getActivity().startActivityForResult(ssaIntent, IntentRequestResponseType.REQUEST_SELECT_SHOP);
        }
    }
}
