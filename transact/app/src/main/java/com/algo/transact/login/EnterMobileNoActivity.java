package com.algo.transact.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.algo.transact.AppConfig.AppState;
import com.algo.transact.R;

public class EnterMobileNoActivity extends AppCompatActivity {

    static EnterMobileNoActivity mobileNoActivity;
    private UserDetails newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_mobile_no);
        mobileNoActivity = this;
        newUser = (UserDetails) getIntent().getSerializableExtra("newUser");
    }

    public void submit_mobile_no(View view)
    {
        Log.i(AppState.TAG, "Clicked on submit_mobile_no");

        EditText etMobileNo =(EditText) findViewById(R.id.enter_mob_et_no);
        newUser.mobNo=etMobileNo.getText().toString().trim();
        Intent myIntent = new Intent(this, VerifyMobileNoActivity.class);
        myIntent.putExtra("newUser", newUser); //Optional parameter pass parameters
        startActivity(myIntent);
    }
}
