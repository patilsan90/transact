package com.algo.transact.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.algo.transact.R;

public class EnterMobileNoActivity extends AppCompatActivity {

    static EnterMobileNoActivity mobileNoActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_mobile_no);
        mobileNoActivity = this;
    }

    public void submit_mobile_no(View view)
    {
        Intent myIntent = new Intent(this, VerifyMobileNoActivity.class);
        myIntent.putExtra("name", "Sample name"); //Optional parameter pass parameters
        startActivity(myIntent);
    }
}
