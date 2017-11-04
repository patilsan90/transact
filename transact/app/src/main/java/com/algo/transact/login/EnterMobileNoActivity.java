package com.algo.transact.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.algo.transact.AppConfig.AppConfig;
import com.algo.transact.R;

public class EnterMobileNoActivity extends AppCompatActivity {

    static EnterMobileNoActivity activity;
    private User newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_mobile_no);
        activity = this;
        newUser = (User) getIntent().getSerializableExtra("newUser");
    }

    public void submit_mobile_no(View view) {
        Log.i(AppConfig.TAG, "Clicked on submit_mobile_no");

        EditText etMobileNo = (EditText) findViewById(R.id.enter_mob_et_no);
        newUser.mobileNo = etMobileNo.getText().toString().trim();
        if (newUser.mobileNo.length() == 0)
            Toast.makeText(this, "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
        else {
            Intent myIntent = new Intent(this, VerifyMobileNoActivity.class);
            myIntent.putExtra("newUser", newUser); //Optional parameter pass parameters
            startActivity(myIntent);
        }
    }
}
