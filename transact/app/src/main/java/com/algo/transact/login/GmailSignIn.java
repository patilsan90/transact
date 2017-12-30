package com.algo.transact.login;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.algo.transact.AppConfig.AppConfig;
import com.algo.transact.server_communicator.request_handler.LoginRequestHandler;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

/**
 * Created by Sandeep Patil on 8/5/17.
 */

public class GmailSignIn {

    public static final int RC_SIGN_IN = 9001;
    private static final String TAG = AppConfig.TAG;
    private static GoogleApiClient mGoogleApiClient;

    public static void configure(LoginActivity loginActivity) {

        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
// options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(loginActivity)
                .enableAutoManage(loginActivity /* FragmentActivity */, loginActivity /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    /* This function receives click event from button. */
    public static void signinByGmail(LoginActivity activity) {
        Log.i(TAG, "gmail sign in started");

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);

    }


    /* This function is called after onActivityResult. */
    // [START handleSignInResult]
    public static void handleSignInResult(GoogleSignInResult result, LoginActivity activity) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            Log.i(TAG, "Gmail sign in successfull");
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            String displayName = acct.getDisplayName();
            String familyName = acct.getFamilyName();
            String givenName = acct.getGivenName();
            String email = acct.getEmail();
            Uri photoURL = acct.getPhotoUrl();

            User signInUser = new User();
            signInUser.displayName = displayName;
            signInUser.familyName = familyName;
            signInUser.firstName = givenName;
            signInUser.emailID = email;

            if (photoURL != null)
                signInUser.profilePhotoURL = photoURL.toString();

            signInUser.loginType = User.LOGIN_OTIONS.GMAIL;

            Log.i(TAG, "Gmail sign in successfull User " + signInUser);
            LoginRequestHandler.login(signInUser, activity);


        } else {
            Log.i(TAG, "Gmail sign out, unauthentic");

            // Signed out, show unauthenticated UI.
            //  updateUI(false);
        }
    }
    // [END handleSignInResult]

    // [START signOut]
    public static void signOutFromGmail() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
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