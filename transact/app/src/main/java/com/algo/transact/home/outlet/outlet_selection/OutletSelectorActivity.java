package com.algo.transact.home.outlet.outlet_selection;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.algo.transact.AppConfig.AppState;
import com.algo.transact.AppConfig.IntentPutExtras;
import com.algo.transact.AppConfig.IntentResultCode;
import com.algo.transact.AppConfig.Permissions;
import com.algo.transact.R;
import com.algo.transact.barcode.BarcodeDetails;
import com.algo.transact.barcode.BarcodeScannerFragment;
import com.algo.transact.barcode.CodeScannerActivity;
import com.algo.transact.gps_location.GPSTracker;
import com.algo.transact.home.outlet.outlet_front.OutletFront;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.util.ArrayList;
import java.util.List;

public class OutletSelectorActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks {

    GPSTracker gpsTracker;
    Location currentGPSlocation;

    int outletID;
    TabLayout tabLayout;
    ViewPager viewPager;
    private BarcodeScannerFragment barcodeScannerFragment;
    private FloatingActionButton fabCodeScanner;


    private GoogleApiClient googleApiClient;

    final static int REQUEST_LOCATION = 199;

private Activity activity;
    private NearByOutletsListFragment outletsListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlet_selector);

        fabCodeScanner = (FloatingActionButton)findViewById(R.id.outlet_fab_barcode_scanner);
        fabCodeScanner.setOnClickListener(this);

        activity=this;
        gpsTracker = new GPSTracker(getApplicationContext(), this);

        enableLocation();
        outletsListFragment = new NearByOutletsListFragment();
        if (gpsTracker.canGetLocation()) {

            final String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, permissions, Permissions.RC_HANDLE_GPS_PERM);
            }
        }

            viewPager = (ViewPager) findViewById(R.id.outlet_seletor_viewpager);
            setupViewPager(viewPager);
            tabLayout = (TabLayout) findViewById(R.id.outlet_seletor_tabs);
            tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(outletsListFragment, "NearBy \nShops");
        adapter.addFragment(new IncompleteCartsListFragment(), "Continue with \nIncomplete Cart");
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.i(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName()+" requestCode:: "+requestCode +" resultCode:: "+resultCode);

        if(data==null)
        {
            Log.i(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
            }.getClass().getEnclosingMethod().getName()+"  No Data Received");
            return;
        }

        if(requestCode == REQUEST_LOCATION)
        {

            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                Log.i(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
                }.getClass().getEnclosingMethod().getName() + "GPS RESULT OK ::"+ result);
                outletsListFragment.populateOutletsList();
            } if (resultCode == Activity.RESULT_CANCELED) {
            //Write your code if there's no result
            Log.i(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
            }.getClass().getEnclosingMethod().getName() + "GPS RESULT CANCELED");

        }
            return;

        }

        BarcodeDetails barcodeDetails = (BarcodeDetails)data.getSerializableExtra(IntentPutExtras.CODE_OBJECT);
       // Item newItem = (Item) data.getSerializableExtra(IntentPutExtras.NEW_ITEM_DATA);

        switch (barcodeDetails.getActionType())
        {
            case OUTLET_SELECTOR:
            case OUTLET_ITEM_SELECTOR:
            {
                /*Log.i(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
                }.getClass().getEnclosingMethod().getName()+"  RESPONSE_NEW_ITEM_SELECTED");
                Intent intent = new Intent();
                intent.putExtra(IntentPutExtras.REQUEST_TYPE, IntentPutExtras.RESPONSE_NEW_ITEM_SELECTED);
                intent.putExtra(IntentPutExtras.NEW_ITEM_DATA, newItem);
                setResult(IntentResultCode.RESULT_OK_NEW_ITEM_ADDITION, intent);
                finish();
                */

                Intent intent = new Intent(this, OutletFront.class);
                intent.putExtra(IntentPutExtras.DATA_TYPE, IntentPutExtras.ID);
                intent.putExtra(IntentPutExtras.ID, barcodeDetails.getOutletID());
                this.startActivityForResult(intent, IntentResultCode.TRANSACT_RESULT_OK);

                //this.startActivity(intent);
                //setResult(RESULT_OK, intent);
                finish();

                break;
            }
        }
    }


    private void enableLocation() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult connectionResult) {

                            // Timber.v("Location error " + connectionResult.getErrorCode());
                        }
                    }).build();
            googleApiClient.connect();
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);
            builder.setAlwaysShow(true);
            PendingResult<LocationSettingsResult> result =
                    LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                status.startResolutionForResult(
                                        (Activity) activity, REQUEST_LOCATION);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                    }
                }
            });
        }
    }


        @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.outlet_fab_barcode_scanner:
            {
                Intent intent = new Intent(this, CodeScannerActivity.class);
             //   intent.putExtra(IntentPutExtras.REQUEST_TYPE, IntentPutExtras.REQUEST_SELECT_SHOP);
                this.startActivityForResult(intent, IntentResultCode.TRANSACT_RESULT_OK);
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {

        Log.i(AppState.TAG, "Class: "+ this.getClass().getSimpleName()+ " Method: "+new Object(){}.getClass().getEnclosingMethod().getName());
/*        if (requestType == IntentPutExtras.REQUEST_SELECT_SHOP)
        {
            Intent intent = new Intent();
            setResult(RESULT_CANCELLED_SHOP_SELECTION, intent);
        }*/
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.i(AppState.TAG, "onRequestPermissionsResult");
        switch (requestCode) {
            case Permissions.RC_HANDLE_GPS_PERM: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(AppState.TAG, "Reading current location");
                    //   currentGPSlocation = locationManager.getCurrentLocation();
                }
                break;
            }
            case Permissions.RC_HANDLE_CAMERA_PERM: {
                barcodeScannerFragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
                ;
                break;
            }

        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(AppState.TAG, "onConnected ......................");
       // outletsListFragment.populateOutletsList();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(AppState.TAG, "onConnectionSuspended .................");

    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(android.support.v4.app.FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
