package com.algo.transact.login;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.algo.transact.AppConfig.AppConfig;
import com.algo.transact.AppConfig.SessionManager;
import com.algo.transact.R;
import com.algo.transact.home.HomeActivity;
import com.algo.transact.server_communicator.listener.ILoginListener;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Arrays;


public class LoginActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener, ILoginListener {


    private static final String TAG = "CognitionMall";
    private LoginFragment loginFragment;

    public static LoginActivity activity;

    public static enum VISIBLE_FORM {LOGIN_FORM, REGISTRATION_FORM};

    public static VISIBLE_FORM visibleForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity = this;
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        loginFragment = new LoginFragment();
        fragmentTransaction.add(R.id.login_fragment_place, loginFragment);
        fragmentTransaction.commit();

        SessionManager session = new SessionManager(getApplicationContext());

        if (session.isLoggedIn()) {
            startMainActivity();
            this.finish();
        }


        findViewById(R.id.signin_using_gmail).setOnClickListener(this);
        findViewById(R.id.signin_using_fb).setOnClickListener(this);

        LoginButton fbloginButton = (LoginButton) findViewById(R.id.signin_using_fb);
        fbloginButton.setReadPermissions("email");

        GmailSignIn.configure(this);
        FBSignIn.configure(this);

    }

    void startMainActivity() {
        Intent myIntent = new Intent(this, HomeActivity.class);
        myIntent.putExtra("name", "Sample name"); //Optional parameter pass parameters
        startActivity(myIntent);
        this.finish();

    }

    @Override
    public void onBackPressed() {
        if (visibleForm == VISIBLE_FORM.LOGIN_FORM)
            super.onBackPressed();
        else {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.login_fragment_place, loginFragment);
            fragmentTransaction.commit();
            visibleForm = VISIBLE_FORM.REGISTRATION_FORM;
        }

    }

    // [START onActivityResult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i(TAG, "onActivityResult :: " + requestCode);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == GmailSignIn.RC_SIGN_IN) {
            Log.i(TAG, "onActivityResult :: Gmail Signin");

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            int statusCode = result.getStatus().getStatusCode();
            // Toast.makeText(AppConfig.getInstance().loginActivity, "Status code:: " + statusCode, Toast.LENGTH_SHORT).show();
            GmailSignIn.handleSignInResult(result, this);
        } else if (requestCode == FBSignIn.RC_SIGN_IN) {
            Log.i(TAG, "onActivityResult :: FB Signin");
            CallbackManager callbackManager = CallbackManager.Factory.create();
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
    // [END onActivityResult]

    @Override
    public void onClick(View v) {
        Log.i(TAG, "On Click event");
        switch (v.getId()) {
            case R.id.signin_using_gmail:
                GmailSignIn.signOutFromGmail();
                GmailSignIn.signinByGmail(this);
                break;
            case R.id.signin_using_fb:
                signinByFB();
                break;
           /*   case R.id.disconnect_button:
                revokeAccess();
                break;*/
        }
    }

    public void signinByFB() {

        Log.i(TAG, "Login with fb");
//        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email"));

        Log.i(TAG, "Session created successfully");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    @Override
    public void onSuccess(User user) {


        Log.d(AppConfig.TAG, "OnSuccess LoginActivity :: " + user);

        if (user.getMobileNo() == null) {
            Log.d(AppConfig.TAG, "OnSuccess LoginActivity :: Mobile number is null");
            Intent myIntent = new Intent(this, EnterMobileNoActivity.class);
            myIntent.putExtra("newUser", user);
            this.startActivity(myIntent);
        } else if (user.getMobileNo().length() == 0) {
            Log.d(AppConfig.TAG, "OnSuccess LoginActivity :: Mobile number is not null, it's 0");
            Intent myIntent = new Intent(this, EnterMobileNoActivity.class);
            myIntent.putExtra("newUser", user);
            this.startActivity(myIntent);
        } else {
            SessionManager session = new SessionManager(getApplicationContext());
            session.setLogin(true);
            user.setUserPreferences(this);

        }

    }

    @Override
    public void onFailure() {

    }
}
