package com.algo.transact.home;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.algo.transact.AppConfig.AppState;
import com.algo.transact.AppConfig.IntentResultCode;
import com.algo.transact.R;
import com.algo.transact.home.outlet.outlet_selection.OutletSelectorActivity;

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
        if(R.id.fragment_home_restaurants == v.getId())
        {
            Log.i(AppState.TAG, "Clicked on shop @ shop");
          //  Intent ssaIntent = new Intent(homeFragment.getActivity(), ShopScannerActivity.class);
            Intent ssaIntent = new Intent(homeFragment.getActivity(), OutletSelectorActivity.class);
            //below line is for testing purpose
            homeFragment.getActivity().startActivityForResult(ssaIntent, IntentResultCode.TRANSACT_REQUEST);
        }
    }
}
