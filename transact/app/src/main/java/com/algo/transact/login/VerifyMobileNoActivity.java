package com.algo.transact.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.algo.transact.AppConfig.SessionManager;
import com.algo.transact.home.HomeActivity;
import com.algo.transact.R;
import com.algo.transact.server_communicator.listener.ILoginListener;
import com.algo.transact.server_communicator.request_handler.ServerRequestHandler;

public class VerifyMobileNoActivity extends AppCompatActivity implements ILoginListener {

    private ProgressDialog pDialog;
    private User newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_mobile_no);
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        newUser = (User) getIntent().getSerializableExtra("newUser");
        // Session manager
        SessionManager session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(VerifyMobileNoActivity.this,
                    HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void submitOTP(View view) {
        Log.i("Login", "Submitting OTP");

        boolean otpSuccess = true;// this assignment is for testing purpose

        if (otpSuccess) {
            ServerRequestHandler.register(newUser, this);
        } else {
            Toast.makeText(getApplicationContext(),
                    "Please enter valid OTP!", Toast.LENGTH_LONG)
                    .show();
        }

    }

    private void finishAllActivities() {

        //TODO :: find any other way to finish below activity.
        this.finish();

        if (EnterMobileNoActivity.activity != null)
            EnterMobileNoActivity.activity.finish();

        if (LoginActivity.activity != null)
            LoginActivity.activity.finish();

    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onSuccess(User user) {

        if (user != null) {
            SessionManager session = new SessionManager(getApplicationContext());
            session.setLogin(true);
            user.setUserPreferences(this);

            Intent intent = new Intent(
                    VerifyMobileNoActivity.this,
                    HomeActivity.class);
            startActivity(intent);
            finishAllActivities();
        }

    }

    @Override
    public void onFailure() {

    }
}
