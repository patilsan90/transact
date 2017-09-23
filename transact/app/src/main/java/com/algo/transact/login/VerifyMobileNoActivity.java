package com.algo.transact.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.algo.transact.AppConfig.AppState;
import com.algo.transact.AppConfig.HTTPReqController;
import com.algo.transact.AppConfig.HTTPReqURLConfig;
import com.algo.transact.AppConfig.SessionManager;
import com.algo.transact.home.HomeActivity;
import com.algo.transact.R;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VerifyMobileNoActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    private SessionManager session;
    private UserDetails newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_mobile_no);
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        newUser = (UserDetails) getIntent().getSerializableExtra("newUser");
        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(VerifyMobileNoActivity.this,
                    HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    void submitOTP() {
        // http://login.cheapsmsbazaar.com/vendorsms/pushsms.aspx?user=san27deep&password=san27deep&msisdn=919423306174&sid=san27deep&msg=test%20message&fl=0&gwid=2

    }

    public void submitOTP(View view) {
        Log.i("Login", "Submitting OTP");

        //Intent myIntent = new Intent(this, OutletFront.class);
        //myIntent.putExtra("isVerifySuccess", "YES"); //Optional parameters
        ///this.startActivity(myIntent);
        //finishActivities();
        boolean otpSuccess = true;// this assignment is for testing purpose

        if (otpSuccess) {
            registerUser(newUser);
        } else {
            Toast.makeText(getApplicationContext(),
                    "Please enter valid OTP!", Toast.LENGTH_LONG)
                    .show();
        }

        // TODO below code is temporary code line and needs to be look into it.
        // AppState.getInstance().loginActivity.writeSessionFile(AppState.getInstance().loginActivity.sessionInfo);

    }

    private void finishActivities() {
        this.finish();
        //TODO :: find any other way to finish below activity.
        EnterMobileNoActivity.mobileNoActivity.finish();
        AppState.getInstance().loginActivity.finish();

    }

    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     */
    private void registerUser(final UserDetails newUser) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Registering ...");
        showDialog();

        if(newUser==null)
            Log.i(AppState.TAG, "Registering user but User object is null");

        Log.i(AppState.TAG, "Registering user Obj "+newUser);

        Log.i(AppState.TAG, "Registering user");
        StringRequest strReq = new StringRequest(Request.Method.POST,
                HTTPReqURLConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(AppState.TAG, "Register Response: " + response.toString());
                hideDialog();
                UserDetails registeredUser = new UserDetails();
                Log.i("LOGLOG", "in onRespose");
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    Log.i(AppState.TAG, "in try");
                    if (!error) {
                        Log.i(AppState.TAG, "in !error");
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        //String uid = jObj.getString("uid");
                        JSONObject user = jObj.getJSONObject("user");
                        Log.i(AppState.TAG, "No error in response");
                        registeredUser.emailID = user.getString("email");
                        registeredUser.mobNo = user.getString("mobile_num");
                        registeredUser.countryCode = user.getString("country_code");
                        registeredUser.loggedInUsing = registeredUser.loggedInUsingToEnum(user.getString("logged_in_using"));
                        registeredUser.displayName = user.getString("display_name");
                        registeredUser.firstName = user.getString("first_name");
                        registeredUser.familyName = user.getString("family_name");
                        registeredUser.createdAt = user.getString("created_at");
                        registeredUser.updatedAt = user.getString("updated_at");
                        Log.i(AppState.TAG, registeredUser.toString());

                        session.setLogin(true);

                        Toast.makeText(getApplicationContext(), "Welcome to the amazingly easy world!", Toast.LENGTH_LONG).show();

                        // Launch login activity
                        Intent intent = new Intent(
                                VerifyMobileNoActivity.this,
                                HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Log.i(AppState.TAG, "in else !error");
                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                "ERR MSG:: " + errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(AppState.TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Log.i("LOGLOG", "in getParams");
                Map<String, String> params = new HashMap<String, String>();

                params.put("email", newUser.emailID);
                params.put("mobile_num", newUser.mobNo);
                params.put("country_code", newUser.countryCode);
                params.put("logged_in_using", newUser.loggedInUsingToSting(newUser.loggedInUsing));
                params.put("display_name", newUser.displayName);
                params.put("first_name", newUser.firstName);
                params.put("family_name", newUser.familyName);
                params.put("password", newUser.password);
                Log.i(AppState.TAG, newUser.toString());
                return params;
            }

        };

        // Adding request to request queue
        HTTPReqController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
