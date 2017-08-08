package com.algo.transact.home.shopatshop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.algo.transact.AppConfig.AppState;
import com.algo.transact.R;

public class SASOffersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sas_offers);
    }

    @Override
    public void onBackPressed() {
      //  super.onBackPressed();
        Log.i(AppState.TAG,"onBackPressed of SASOffersActivity");
        this.finish();
    }
}
