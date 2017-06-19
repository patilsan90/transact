package com.algo.transact.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.algo.transact.AppState;
import com.algo.transact.home.shopatshop.ShopAtShop;
import com.algo.transact.R;

public class VerifyMobileNoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_mobile_no);
    }

    void submitOTP()
    {
       // http://login.cheapsmsbazaar.com/vendorsms/pushsms.aspx?user=san27deep&password=san27deep&msisdn=919423306174&sid=san27deep&msg=test%20message&fl=0&gwid=2

    }

    public void submitOTP(View view) {
        Log.i("Login", "Submitting OTP");

        Intent myIntent = new Intent(this, ShopAtShop.class);
        myIntent.putExtra("isVerifySuccess", "YES"); //Optional parameters
        this.startActivity(myIntent);
        finishActivities();

        // TODO below code is temporary code line and needs to be look into it.
        AppState.getInstance().loginActivity.writeSessionFile(AppState.getInstance().loginActivity.sessionInfo);

    }

    private void finishActivities() {
        this.finish();
        //TODO :: find any other way to finish below activity.
        EnterMobileNoActivity.mobileNoActivity.finish();
        AppState.getInstance().loginActivity.finish();

    }


}
