package com.algo.transact.AppConfig;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.algo.transact.home.MainActivity;
import com.algo.transact.login.LoginActivity;
import com.algo.transact.login.LoginFragment;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sandeep on 10/7/17.
 */

public class Login {

    LoginFragment activity;

    public Login(LoginFragment activity) {
    this.activity = activity;
    }

    /**
     * function to verify login details in mysql db
     * */
    public void checkLogin(final String email, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        activity.pDialog.setMessage("Logging in ...");
        activity.showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                HTTPReqController.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(AppState.TAG, "Login Response: " + response.toString());
                activity.hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        Log.i(AppState.TAG, "Successfully logged in");

                        AppState.getInstance().mainActivity.session.setLogin(true);

                        // Now store the user in SQLite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String created_at = user
                                .getString("created_at");

                        // Inserting row in users table
                        AppState.getInstance().loginActivity.db.addUser(name, email, uid, created_at);

                        // Launch main activity
                        Intent intent = new Intent(AppState.getInstance().loginActivity,
                                MainActivity.class);
                        AppState.getInstance().loginActivity.startActivity(intent);
                        AppState.getInstance().loginActivity.finish();
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        //Toast.makeText(getApplicationContext(),
                          //      "Error MSG:: "+errorMsg, Toast.LENGTH_LONG).show();
                        Log.i(AppState.TAG,"Error :: "+errorMsg);

                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.i(AppState.TAG, "Json error: " + e.getMessage());
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(AppState.TAG, "Login Error: " + error.getMessage());
                //Toast.makeText(activity.getActivity().getApplicationContext(),
                  //      error.getMessage(), Toast.LENGTH_LONG).show();

                activity.hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        HTTPReqController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

}
