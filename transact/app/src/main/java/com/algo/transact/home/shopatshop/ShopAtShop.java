package com.algo.transact.home.shopatshop;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.algo.transact.AppState;
import com.algo.transact.barcode.BarcodeCaptureActivity;
import com.algo.transact.home.LocateCategories;
import com.algo.transact.home.MainActivity;
import com.algo.transact.home.shopatshop.mycart.ItemCountSelectionActivity;
import com.algo.transact.home.shopatshop.mycart.MyCartFragment;
import com.algo.transact.home.offers.OffersFragment;
import com.algo.transact.login.LoginActivity;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.vision.barcode.Barcode;
import com.algo.transact.R;

import java.io.File;

public class ShopAtShop extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "CognitionMall";

    private static final int BARCODE_READER_REQUEST_CODE = 1;
    private static final String LOG_TAG = ShopAtShop.class.getSimpleName();
    public MyCartFragment myCartFragment;
    public OffersFragment offersFragment;
    private boolean isBack = false;
    private Button showCartButton;
    private int back_press_counter = 0;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_at_shop);

        myCartFragment = new MyCartFragment();
        offersFragment = new OffersFragment();
        Log.i("Generic info ", " Activity onCreate ShopAtShop");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

/*
        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
// options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
*/

        //---------------------------
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.shop_at_shop_page_frame, myCartFragment);
        fragmentTransaction.commit();

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

        public void scanItemAtShop(View view) {

        if (AppState.checkProccedStatus() == false) {
            Toast.makeText(this, "Please select mall first !!!", Toast.LENGTH_SHORT).show();
            return;
        }

        AppState.isProductScan = true;
            BarcodeCaptureActivity.scanner_type = BarcodeCaptureActivity.SCANNER_TYPE.ORDERitemAtShop;
        Intent intent = new Intent(getApplicationContext(), BarcodeCaptureActivity.class);
        startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);

        /*
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.home_fragment, scanProduct);
        fragmentTransaction.commit();
        */

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(AppState.TAG,"Barcode onActivityResult");
        if (requestCode == BARCODE_READER_REQUEST_CODE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    Point[] p = barcode.cornerPoints;
                    //mResultTextView.setText(barcode.displayValue);
                    //} else
                    //  mResultTextView.setText(R.string.no_barcode_captured);
                    Log.i(AppState.TAG,"Barcode scanned, Item selected");
                    if (AppState.isProductScan == false) {
                        // This indicates was executed to select mall
                        AppState.isMallSelected = true;
                    } else {
                        //Else condition indicates, scan executed to select item but not mall
                        Intent intent = new Intent(this, ItemCountSelectionActivity.class);
                        startActivity(intent);
                        /* TODO ::
                         * If ItemCountSelectionActivity is not required then
                         * add item to cart at this place itself
                         */
                    }

                } else Log.e(LOG_TAG, String.format(getString(R.string.barcode_error_format),
                        CommonStatusCodes.getStatusCodeString(resultCode)));
            } else super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void showMyCart(View view) {

        if (AppState.checkProccedStatus() == false) {
            Toast.makeText(this, "Please select mall first !!!", Toast.LENGTH_SHORT).show();
            return;
        }

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        if (isBack == false) {
            showCartButton.setText("Back");
            fragmentTransaction.replace(R.id.shop_at_shop_page_frame, myCartFragment);
            isBack = true;
        } else {
            showCartButton.setText("My Cart");
            fragmentTransaction.replace(R.id.shop_at_shop_page_frame, offersFragment);
            isBack = false;
        }

        fragmentTransaction.commit();
    }

    public void locateCategories(View view) {
        Log.i("Home", "locateCategories Clicked");
        Intent myIntent = new Intent(this, LocateCategories.class);
        this.startActivity(myIntent);

    }

    public void selectMall(View view) {
        Log.i("Home", "Select mall Clicked");
        AppState.isProductScan = false;
        Intent intent = new Intent(getApplicationContext(), BarcodeCaptureActivity.class);
        startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);

//        Intent myIntent = new Intent(this, MallSelectionActivity.class);
        //      this.startActivity(myIntent);

    }

    public void gotoHomefromSAS(View v)
    {
        Log.i("Home", "Select mall Clicked");
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
        this.finish();
    }

    public void browseCatalog(View v)
    {
        Log.i("Home", "Select mall Clicked");
        Intent intent = new Intent(getApplicationContext(), SASCatalogActivity.class);
        startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
    }
    public void browseOffers(View v)
    {
        Log.i("Home", "Select mall Clicked");
        Intent intent = new Intent(getApplicationContext(), SASOffersActivity.class);
        startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
    }
    public void checkoutCart(View v)
    {
        Log.i("Home", "Select mall Clicked");
        Intent intent = new Intent(getApplicationContext(), SASCheckoutActivity.class);
        startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    public void logout(View view) {
        Log.i("Home", "Logout clicked");

        File sessionFile = new File(AppState.sessionFile);
        if (sessionFile.exists()) {
            signOutFromGmail();
            LoginManager.getInstance().logOut();
            Log.i("Home", "Logout, file exists, deleting");
            sessionFile.delete();
            Intent myIntent = new Intent(this, LoginActivity.class);
            startActivity(myIntent);
            this.finish();
        } else {
            Log.i("Home", "Logout, file does not exists, its a error case");
        }
    }

    // [START signOut]
    private void signOutFromGmail() {
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
}
