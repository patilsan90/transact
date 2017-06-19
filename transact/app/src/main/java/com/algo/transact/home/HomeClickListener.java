package com.algo.transact.home;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.algo.transact.AppState;
import com.algo.transact.R;
import com.algo.transact.barcode.BarcodeCaptureActivity;
import com.algo.transact.login.VerifyMobileNoActivity;

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
            BarcodeCaptureActivity.scanner_type =BarcodeCaptureActivity.SCANNER_TYPE.SHOPatSHOP;
            Intent myIntent = new Intent(homeFragment.getActivity(), BarcodeCaptureActivity.class);
            myIntent.putExtra("name", "Sample name"); //Optional parameter pass parameters
            homeFragment.getActivity().startActivity(myIntent);

        }
    }
}
