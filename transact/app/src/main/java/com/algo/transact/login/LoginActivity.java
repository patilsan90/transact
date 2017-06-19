package com.algo.transact.login;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.algo.transact.home.shopatshop.ShopAtShop;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.algo.transact.AppState;
import com.algo.transact.R;
import com.algo.transact.server_communication.UserAuthentication;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Arrays;


public class LoginActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {


    private static final String TAG = "CognitionMall";
    public GoogleApiClient mGoogleApiClient;
    public SessionInfo sessionInfo;
    public ProgressDialog mProgressDialog;
    boolean isRegistrationForm = false;
    LoginFragment loginFragment;
    RegisterUserFragment registerUserFragment;
    EditText email;
    EditText mobNo;
    CallbackManager callbackManager;
    private GmailSignIn gmailSignIn;
    private LoginButton fbloginButton;
    private FBSignIn fbSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AppState.getInstance().loginActivity = this;
        AppState.AppCacheFolder = getCacheDir().getAbsolutePath() + File.pathSeparator + "MallAppInfo";
        AppState.sessionFile = AppState.AppCacheFolder + File.pathSeparator + "SessionInfo.txt";
        callbackManager = CallbackManager.Factory.create();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        loginFragment = new LoginFragment();
        fragmentTransaction.add(R.id.login_fragment_place, loginFragment);
        fragmentTransaction.commit();

        File sessionFile = new File(AppState.sessionFile);

        if (UserAuthentication.getInstance().authenticateUser(sessionFile))
            startMainActivity();


        findViewById(R.id.signin_using_gmail).setOnClickListener(this);
        //findViewById(R.id.signin_using_fb).setOnClickListener(this);

        fbloginButton = (LoginButton) findViewById(R.id.signin_using_fb);
        fbloginButton.setReadPermissions("email");

        gmailSignIn = new GmailSignIn(this);
        fbSignIn = new FBSignIn(this);

        gmailSignIn.configure();
        fbSignIn.configure();

    }

    void startMainActivity() {
        Intent myIntent = new Intent(this, ShopAtShop.class);
        myIntent.putExtra("name", "Sample name"); //Optional parameter pass parameters
        startActivity(myIntent);
        this.finish();

    }

    @Override
    public void onBackPressed() {
        if (AppState.getInstance().loginState == AppState.LOGIN_STATE.LOGIN_VIEW)
            super.onBackPressed();
        else {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.login_fragment_place, loginFragment);
            fragmentTransaction.commit();
            AppState.getInstance().loginState = AppState.LOGIN_STATE.LOGIN_VIEW;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mProgressDialog != null)
            mProgressDialog.dismiss();
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }


    // [START onActivityResult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i(TAG, "onActivityResult :: " + requestCode);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == gmailSignIn.RC_SIGN_IN) {
            Log.i(TAG, "onActivityResult :: Gmail Signin");

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            int statusCode = result.getStatus().getStatusCode();
            // Toast.makeText(AppState.getInstance().loginActivity, "Status code:: " + statusCode, Toast.LENGTH_SHORT).show();
            gmailSignIn.handleSignInResult(result);
        } else if (requestCode == fbSignIn.RC_SIGN_IN) {
            Log.i(TAG, "onActivityResult :: FB Signin");

            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
    // [END onActivityResult]


    @Override
    public void onClick(View v) {
        Log.i(TAG, "On Click event");
        switch (v.getId()) {
            case R.id.signin_using_gmail:
                gmailSignIn.signOutFromGmail();
                gmailSignIn.signinByGmail();
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
        // signOutFromGmail();
        // return;
        //if(authentication successfull)

        Log.i(TAG, "Login with fb");
//        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email"));

        /*
        String email_id = "temp@temp.com";
        String mob_no = "9999999999";
        String user_name = "Temporary Man";

        SessionInfo sessionInfo = new SessionInfo();
        sessionInfo.emailID = email_id;
        sessionInfo.mobNo = mob_no;
        sessionInfo.logged_in_by = SessionInfo.LOGIN_OTIONS.FB;
        AppState.getInstance().loginActivity.sessionInfo = sessionInfo;
        Log.i(TAG, "Creating session using FB credentials");

        if (writeSessionFile(sessionInfo))
            startMainActivity();*/

        Log.i(TAG, "Session created successfully");
    }


    boolean writeSessionFile(SessionInfo info) {
        File projDir = new File(AppState.AppCacheFolder);
        if (!projDir.exists())
            projDir.mkdirs();

        File sessionFile = new File(AppState.sessionFile);

        Log.i(TAG, "File Location ::" + AppState.sessionFile);
        if (!sessionFile.exists()) {
            Log.i(TAG, "File does not exist, creating one");
            try {
                sessionFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "File exist yeyeye");

        }

        try (
                OutputStream file = new FileOutputStream(AppState.sessionFile);
                OutputStream buffer = new BufferedOutputStream(file);
                ObjectOutput output = new ObjectOutputStream(buffer);
        ) {
            output.writeObject(info);
        } catch (IOException ex) {
            //fLogger.log(Level.SEVERE, "Cannot perform output.", ex);
            Log.e(TAG, "Error in writting session object");

        }
        return true;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }


}
