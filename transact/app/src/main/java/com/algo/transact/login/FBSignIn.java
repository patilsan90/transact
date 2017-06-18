package com.algo.transact.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.algo.transact.AppState;
import com.algo.transact.server_communication.UserAuthentication;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.algo.transact.AppState;
import com.algo.transact.server_communication.UserAuthentication;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Sandeep Patil on 8/5/17.
 */

public class FBSignIn {

    public static final int RC_SIGN_IN = 64206;
    private static final String TAG = "CognitionMall";

    public FBSignIn(LoginActivity loginActivity) {
    }

    public void configure() {

        LoginManager.getInstance().registerCallback(AppState.getInstance().loginActivity.callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Log.i(TAG, "FB login success :: LoginManager");
                        //Profile profile = Profile.getCurrentProfile();
                        //Toast.makeText(this,,Toast.LENGTH_LONG).show();
                        //tmp = loginResult.getAccessToken().getUserId();
                        //Toast.makeText(AppState.getInstance().loginActivity,"WC:: "+ profile.getId() + " : " + profile.getLastName() +" : "+profile.getName(), Toast.LENGTH_LONG).show();

                        AppState.getInstance().loginActivity.mProgressDialog = new ProgressDialog(AppState.getInstance().loginActivity);
                        AppState.getInstance().loginActivity.mProgressDialog.setMessage("Loading...");
                        AppState.getInstance().loginActivity.mProgressDialog.show();
                        String accessToken = loginResult.getAccessToken().getToken();
                        Log.i("accessToken", accessToken);

                        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.i("LoginActivity", response.toString());
                                // Get facebook data from login
                                Bundle bFacebookData = getFacebookData(object);
                            }
                        });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
                        request.setParameters(parameters);
                        request.executeAsync();

                    }

                    private Bundle getFacebookData(JSONObject object) {
                        SessionInfo sessionInfo = new SessionInfo();
                        try {
                            Bundle bundle = new Bundle();
                            String id = object.getString("id");

                            try {
                                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                                Log.i("profile_pic", profile_pic + "");
                                bundle.putString("profile_pic", profile_pic.toString());
                                sessionInfo.profilePhotoURL = Uri.parse(profile_pic.toString());

                            } catch (MalformedURLException e) {
                                AppState.getInstance().loginActivity.mProgressDialog.dismiss();
                                e.printStackTrace();
                                return null;
                            }

                            bundle.putString("idFacebook", id);
                            if (object.has("first_name"))
                                // bundle.putString("first_name", object.getString("first_name"));
                                sessionInfo.firstName = object.getString("first_name");

                            if (object.has("last_name"))
                                // bundle.putString("last_name", object.getString("last_name"));
                                sessionInfo.familyName = object.getString("last_name");

                            if (object.has("email"))
                                //    bundle.putString("email", object.getString("email"));
                                sessionInfo.emailID = object.getString("email");
                            // if (object.has("gender"))
                            //   bundle.putString("gender", object.getString("gender"));
                            //if (object.has("birthday"))
                            //  bundle.putString("birthday", object.getString("birthday"));
                            //if (object.has("location"))
                            //  bundle.putString("location", object.getJSONObject("location").getString("name"));

                            sessionInfo.displayName = sessionInfo.firstName + " " + sessionInfo.familyName;
                            sessionInfo.logged_in_by = SessionInfo.LOGIN_OTIONS.FB;

                            verifyFbSignin(sessionInfo);
                            AppState.getInstance().loginActivity.mProgressDialog.dismiss();
                            return bundle;
                        } catch (JSONException e) {
                            AppState.getInstance().loginActivity.mProgressDialog.dismiss();

                            Log.d(TAG, "Error parsing JSON");
                        }
                        AppState.getInstance().loginActivity.mProgressDialog.dismiss();
                        return null;
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Log.i(TAG, "FB login cancel :: LoginManager");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Log.i(TAG, "FB login error :: LoginManager");
                    }
                });

    }//configure end

    public boolean verifyFbSignin(SessionInfo sessionInfo) {

        Toast.makeText(AppState.getInstance().loginActivity, "Welcome " + sessionInfo.displayName, Toast.LENGTH_SHORT).show();

        AppState.getInstance().loginActivity.sessionInfo = sessionInfo;
        Log.i("TAG", "Creating session using GMAIL credentials");

        if (UserAuthentication.getInstance().verifyMobileNumber(sessionInfo) != null) {
            if (AppState.getInstance().loginActivity.writeSessionFile(sessionInfo))
                AppState.getInstance().loginActivity.startMainActivity();
        } else {
            Intent myIntent = new Intent(AppState.getInstance().loginActivity, EnterMobileNoActivity.class);
            myIntent.putExtra("name", "Sample name"); //Optional parameter pass parameters
            AppState.getInstance().loginActivity.startActivity(myIntent);
        }

        return true;

    }
}