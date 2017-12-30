package com.algo.transact.login;

import android.os.Bundle;
import android.util.Log;

import com.algo.transact.AppConfig.AppConfig;
import com.algo.transact.server_communicator.listener.ILoginListener;
import com.algo.transact.server_communicator.request_handler.LoginRequestHandler;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Sandeep Patil on 8/5/17.
 */

public class FBSignIn {

    public static final int RC_SIGN_IN = 64206;
    private static final String TAG = AppConfig.TAG;


    public static void configure(final ILoginListener listener) {

        CallbackManager callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // AppConfig code
                        Log.i(TAG, "FB login success :: LoginManager");
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
                        parameters.putString("fields", "id, first_name, last_name, email, gender, birthday, location");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    private Bundle getFacebookData(JSONObject object) {
                        User signInUser = new User();
                        try {
                            Bundle bundle = new Bundle();
                            String id = object.getString("id");

                            try {
                                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                                Log.i("profile_pic", profile_pic + "");
                                bundle.putString("profile_pic", profile_pic.toString());
                                signInUser.profilePhotoURL = profile_pic.toString();

                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                                return null;
                            }

                            bundle.putString("idFacebook", id);
                            if (object.has("first_name"))
                                signInUser.firstName = object.getString("first_name");

                            if (object.has("last_name"))
                                signInUser.familyName = object.getString("last_name");

                            if (object.has("email"))
                                signInUser.emailID = object.getString("email");
                            // if (object.has("gender"))
                            //   bundle.putString("gender", object.getString("gender"));
                            //if (object.has("birthday"))
                            //  bundle.putString("birthday", object.getString("birthday"));
                            //if (object.has("location"))
                            //  bundle.putString("location", object.getJSONObject("location").getString("name"));

                            signInUser.displayName = signInUser.firstName + " " + signInUser.familyName;
                            signInUser.loginType = User.LOGIN_OTIONS.FB;
                            // signInUser.countryCode = "+91";
                            Log.i(TAG, "FB SIgn in :: " + signInUser);
                            LoginRequestHandler.login(signInUser, listener);
                            return bundle;
                        } catch (JSONException e) {
                            Log.d(TAG, "Error parsing JSON");
                        }
                        return null;
                    }

                    @Override
                    public void onCancel() {
                        // AppConfig code
                        Log.i(TAG, "FB login cancel :: LoginManager");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // AppConfig code
                        Log.i(TAG, "FB login error :: LoginManager");
                    }
                });

    }//configure end

}