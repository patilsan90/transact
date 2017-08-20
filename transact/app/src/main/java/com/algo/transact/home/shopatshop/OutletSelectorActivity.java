package com.algo.transact.home.shopatshop;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.algo.transact.AppConfig.AppState;
import com.algo.transact.AppConfig.IntentPutExtras;
import com.algo.transact.AppConfig.IntentRequestResponseType;
import com.algo.transact.AppConfig.IntentResultCode;
import com.algo.transact.AppConfig.ModuleType;
import com.algo.transact.AppConfig.Permissions;
import com.algo.transact.R;
import com.algo.transact.barcode.BarcodeDetails;
import com.algo.transact.barcode.BarcodeScannerFragment;
import com.algo.transact.barcode.CodeScannerActivity;
import com.algo.transact.barcode.IQRResult;
import com.algo.transact.generic_structures.JSON_Extractor;
import com.algo.transact.gps_location.GPSTracker;
import com.algo.transact.home.shopatshop.data_beans.Item;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.algo.transact.AppConfig.IntentResultCode.RESULT_CANCELLED_SHOP_SELECTION;

public class OutletSelectorActivity extends AppCompatActivity implements IQRResult, View.OnClickListener {

    GPSTracker gpsTracker;
    Location currentGPSlocation;

    int shopID;
    TabLayout tabLayout;
    ViewPager viewPager;
    private BarcodeScannerFragment barcodeScannerFragment;
    private FloatingActionButton fabCodeScanner;
    private int requestType;
private Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outlet_selector);

        fabCodeScanner = (FloatingActionButton)findViewById(R.id.outlet_fab_barcode_scanner);
        fabCodeScanner.setOnClickListener(this);

        activity=this;
        gpsTracker = new GPSTracker(getApplicationContext(), this);

        if (gpsTracker.canGetLocation()) {

            final String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, permissions, Permissions.RC_HANDLE_GPS_PERM);
            } else {
                currentGPSlocation = gpsTracker.getCurrentLocation();
                if(currentGPSlocation!=null) {
                    Log.i(AppState.TAG, "Location getAltitude " + currentGPSlocation.getAltitude());
                    Log.i(AppState.TAG, "Location getLongitude " + currentGPSlocation.getLongitude());
                    Log.i(AppState.TAG, "Location getLatitude " + currentGPSlocation.getLatitude());
                    Log.i(AppState.TAG, "Location getProvider " + currentGPSlocation.getProvider());
                }
                else
                    Log.e(AppState.TAG,"Error in acquiring GPS signal");
               // Toast.makeText(this, "Alt :: " + currentGPSlocation.getAltitude() + " Lon " + currentGPSlocation.getLongitude() + " Lat " + currentGPSlocation.getLatitude(), Toast.LENGTH_LONG).show();
            }
        } else
            gpsTracker.showSettingsAlert();

        requestType = getIntent().getIntExtra(IntentPutExtras.REQUEST_TYPE,0);

        if (requestType == IntentPutExtras.REQUEST_SELECT_SHOP) {
            Log.i(AppState.TAG, "ShopScanner for " + IntentPutExtras.REQUEST_SELECT_SHOP);
            viewPager = (ViewPager) findViewById(R.id.outlet_seletor_viewpager);
            setupViewPager(viewPager);
            tabLayout = (TabLayout) findViewById(R.id.outlet_seletor_tabs);
            tabLayout.setupWithViewPager(viewPager);
        } else if (requestType == IntentPutExtras.REQUEST_SELECT_ITEM_FROM_SHOP) {
            Log.i(AppState.TAG, "ShopScanner for " + IntentPutExtras.REQUEST_SELECT_ITEM_FROM_SHOP);
            shopID = getIntent().getIntExtra(IntentPutExtras.ID,0);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ShopsListFragment(), "NearBy \nShops");
        adapter.addFragment(new CartsListFragment(), "Continue with \nIncomplete Cart");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void codeScannerResult(String barcodeResult) {
        Log.i(AppState.TAG, "In ShopScannerActivity ScannerResult " + barcodeResult);

        if (requestType == IntentPutExtras.REQUEST_SELECT_SHOP)
        {
            Log.i(AppState.TAG, "In ShopScannerActivity ScannerResult requestType:: REQUEST_SELECT_SHOP ");
            handleShopSelctionRequest(barcodeResult);
        }
        else if (requestType == IntentPutExtras.REQUEST_SELECT_ITEM_FROM_SHOP)
        {
            Log.i(AppState.TAG, "In ShopScannerActivity ScannerResult requestType:: REQUEST_SELECT_ITEM_FROM_SHOP ");
            handleShopItemSelctionRequest(barcodeResult);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(data==null)
        {
            Log.i(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
            }.getClass().getEnclosingMethod().getName()+"  No Data Received");
            return;
        }
        int request_type = data.getIntExtra(IntentPutExtras.REQUEST_TYPE, 0);
        Item newItem = (Item) data.getSerializableExtra(IntentPutExtras.NEW_ITEM_DATA);
        Log.i(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());
        switch (request_type)
        {
            case IntentPutExtras.RESPONSE_NEW_ITEM_SELECTED:
            {
                Log.i(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
                }.getClass().getEnclosingMethod().getName()+"  RESPONSE_NEW_ITEM_SELECTED");
                Intent intent = new Intent();
                intent.putExtra(IntentPutExtras.REQUEST_TYPE, IntentPutExtras.RESPONSE_NEW_ITEM_SELECTED);
                intent.putExtra(IntentPutExtras.NEW_ITEM_DATA, newItem);
                setResult(IntentResultCode.RESULT_OK_NEW_ITEM_ADDITION, intent);
                finish();
                break;
            }
        }
    }

    private void handleShopItemSelctionRequest(String barcodeResult) {

        //        Intent intent = new Intent();

        String itemId = JSON_Extractor.extractShopItemIdAndVerify(barcodeResult,ModuleType.SHOP, shopID);
        Log.i(AppState.TAG, "handleShopItemSelctionRequest itemId :: "+itemId);

        if(itemId != null)
        {
        Intent intent = new Intent(getApplicationContext(), ItemCountSelectionActivity.class);
        intent.putExtra(IntentPutExtras.REQUEST_TYPE, IntentPutExtras.REQUEST_SELECT_ITEM_FROM_SHOP);
        intent.putExtra(IntentPutExtras.MODULE_ID,shopID);
        intent.putExtra(IntentPutExtras.ID,itemId);
        //setResult(RESULT_OK, intent);
        startActivityForResult(intent, IntentRequestResponseType.REQUEST_SELECT_ITEM_FROM_SHOP);
        //finish();
        }
        else
        {
            Log.i(AppState.TAG, "Invalid Code, this product doesnt belong to this shop");
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Toast toast = Toast.makeText(activity, "Invalid Code, this product doesnt belong to this shop", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                    toast.show();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {

        Log.i(AppState.TAG, "Class: "+ this.getClass().getSimpleName()+ " Method: "+new Object(){}.getClass().getEnclosingMethod().getName());
        if (requestType == IntentPutExtras.REQUEST_SELECT_SHOP)
        {
            Intent intent = new Intent();
            setResult(RESULT_CANCELLED_SHOP_SELECTION, intent);
        }
        finish();
    }

    private void handleShopSelctionRequest(String barcodeResult) {
        JSONObject jObj = null;
        try {
            jObj = new JSONObject(barcodeResult);
            String type = jObj.getString(BarcodeDetails.MODULE_TYPE);
            int shopID = jObj.getInt(BarcodeDetails.MODULE_ID);
            Log.i(AppState.TAG, "In ShopScannerActivity ScannerResult TYPE:: " + type);
            Log.i(AppState.TAG, "In ShopScannerActivity ScannerResult ID:: " + shopID);

            if (ModuleType.SHOP.equals(type)) {
                Intent intent = new Intent(this, ShopAtShop.class);
                intent.putExtra(IntentPutExtras.REQUEST_TYPE, IntentPutExtras.REQUEST_SELECT_SHOP);
                intent.putExtra(IntentPutExtras.ID, shopID);
                this.startActivityForResult(intent, IntentResultCode.RESULT_OK_SHOP_SELECTION);
                //this.startActivity(intent);
                //setResult(RESULT_OK, intent);
                finish();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.outlet_fab_barcode_scanner:
            {
                Intent intent = new Intent(this, CodeScannerActivity.class);
                intent.putExtra(IntentPutExtras.REQUEST_TYPE, IntentPutExtras.REQUEST_SELECT_SHOP);
                this.startActivityForResult(intent, IntentResultCode.RESULT_OK_SHOP_SELECTION);
                break;
            }
        }
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
