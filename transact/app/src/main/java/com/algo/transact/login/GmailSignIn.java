package com.algo.transact.login;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.algo.transact.AppState;
import com.algo.transact.server_communication.UserAuthentication;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.algo.transact.AppState;
import com.algo.transact.server_communication.UserAuthentication;

/**
 * Created by Sandeep Patil on 8/5/17.
 */

public class GmailSignIn {

    public static final int RC_SIGN_IN = 9001;
    private static final String TAG = "CognitionMall";

    public GmailSignIn(LoginActivity loginActivity) {
    }

    public void configure() {

        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
// options specified by gso.
        AppState.getInstance().loginActivity.mGoogleApiClient = new GoogleApiClient.Builder(AppState.getInstance().loginActivity)
                .enableAutoManage(AppState.getInstance().loginActivity /* FragmentActivity */, AppState.getInstance().loginActivity /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }

/* This function receives click event from button. */
    public void signinByGmail() {
        Log.i(TAG, "gmail sign in started");

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(AppState.getInstance().loginActivity.mGoogleApiClient);
        AppState.getInstance().loginActivity.startActivityForResult(signInIntent, RC_SIGN_IN);


        Log.i(TAG, "Session created successfully");
    }

/* This function is called after onActivityResult. */
    // [START handleSignInResult]
    public void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            Log.i(TAG, "Gmail sign in successful");
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            // mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
            //updateUI(true);

            verifyGmailSignin(acct);
        } else {
            Log.i(TAG, "Gmail sign out, unauthentic");

            // Signed out, show unauthenticated UI.
            //  updateUI(false);
        }
    }
    // [END handleSignInResult]

    public boolean verifyGmailSignin(GoogleSignInAccount acct) {
        String displayName = acct.getDisplayName();
        String familyName = acct.getFamilyName();
        String givenName = acct.getGivenName();
        String email = acct.getEmail();
        Uri photoURL = acct.getPhotoUrl();

        Toast.makeText(AppState.getInstance().loginActivity, "Welcome " + displayName, Toast.LENGTH_SHORT).show();

        SessionInfo sessionInfo = new SessionInfo();
        sessionInfo.displayName = displayName;
        sessionInfo.familyName = familyName;
        sessionInfo.firstName = givenName;
        sessionInfo.emailID = email;
        sessionInfo.profilePhotoURL = photoURL;
        sessionInfo.logged_in_by = SessionInfo.LOGIN_OTIONS.GMAIL;
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

    // [START signOut]
    public void signOutFromGmail() {
        Auth.GoogleSignInApi.signOut(AppState.getInstance().loginActivity.mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        // [START_EXCLUDE]
                        //  updateUI(false);
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END signOut]

}//class end

/*    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }*/