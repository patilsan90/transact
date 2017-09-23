package com.algo.transact.home;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.algo.transact.AppConfig.AppState;
import com.algo.transact.AppConfig.IntentPutExtras;
import com.algo.transact.AppConfig.IntentResultCode;
import com.algo.transact.AppConfig.SessionManager;
import com.algo.transact.R;
import com.algo.transact.optical_code.CodeScannerActivity;
import com.algo.transact.optical_code.CodeScannerRequestType;
import com.algo.transact.optical_code.OpticalCode;
import com.algo.transact.home.outlet.data_beans.Outlet;
import com.algo.transact.home.outlet.outlet_front.OutletFront;
import com.algo.transact.login.LoginActivity;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private int REQ_CODE_SCANNER = 100;

    private HomeFragment homeFragment;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    boolean isDrawerOpen= false;

    public SessionManager session;
    private Button btLogout;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    LinearLayout llProfileTab;
    private MyProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

      //  LinearLayout ll_sat = (LinearLayout) findViewById(R.id.shop_at_shop);
       // ll_sat.setOnClickListener();

        LinearLayout llProfileTab = (LinearLayout) findViewById(R.id.home_ll_profile);
        llProfileTab.setOnClickListener(this);

        LinearLayout llHomeTab = (LinearLayout) findViewById(R.id.home_ll_home_tab);
        llHomeTab.setOnClickListener(this);

        FloatingActionButton fabCodeScanner = (FloatingActionButton) findViewById(R.id.home_fab_code_scanner);
        fabCodeScanner.setOnClickListener(this);

        homeFragment = new HomeFragment();


/*

        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        homeFragment = new HomeFragment();
        fragmentTransaction.add(R.id.home_page_frame, homeFragment);
        fragmentTransaction.commit();
*/


        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.home_page_frame, homeFragment);
        transaction.commit();


        AppState.getInstance().homeActivity =this;
        session= new SessionManager(getApplicationContext());

        if(!session.isLoggedIn())
        {
            Log.i(AppState.TAG, "Logged in check");
            session.logoutUser(session);
            Intent intent= new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            this.finish();
        }


      // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        createDrawer();

    }

    private void createDrawer() {
        // DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.home_drawer_layout);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                Log.i(AppState.TAG, "onDrawerOpened " + getTitle());
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                Log.i(AppState.TAG, "onDrawerClosed: " + getTitle());
                invalidateOptionsMenu();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerToggle.syncState();
        // mDrawerLayout.setDrawerListener(mDrawerToggle); /*check if any issue comes because of addDrawerListener.*/
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(AppState.TAG, "onOptionsItemSelected: "+item.getItemId());
          if(mDrawerToggle.onOptionsItemSelected(item))
              return true;

        return super.onOptionsItemSelected(item);
    }

    public void openDrawer(View view)
    {
        Log.i(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.i(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " requestCode:: " + requestCode + " resultCode:: " + resultCode);

        if (data == null) {
            Log.e(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
            }.getClass().getEnclosingMethod().getName() + "  No Data Received");
            return;
        }
        OpticalCode barcodeDetails = (OpticalCode) data.getSerializableExtra(IntentPutExtras.CODE_OBJECT);

        if (requestCode == REQ_CODE_SCANNER)
        {
            Intent intent = new Intent(this, OutletFront.class);
            intent.putExtra(IntentPutExtras.DATA_TYPE, IntentPutExtras.ID);
            intent.putExtra(IntentPutExtras.ID, barcodeDetails.getOutletId());
            long openTIme = 10000;
            long closeTIme = 30000;
            ArrayList<Outlet.WEEKDAY> outletCloseDays = new ArrayList<Outlet.WEEKDAY>();
            outletCloseDays.add(Outlet.WEEKDAY.SUNDAY);

            Outlet outlet = new Outlet(barcodeDetails.getOutletId(), "BigBaz HSR", "BigBazaar", barcodeDetails.getOutletType(), "HSR Sector 1, HSR Layout", 250, "Rs", true, 2, openTIme, closeTIme, outletCloseDays);

            intent.putExtra(IntentPutExtras.OUTLET_OBJECT, outlet);

            this.startActivityForResult(intent, IntentResultCode.TRANSACT_RESULT_OK);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.home_fab_code_scanner:
                Intent intent =new Intent(this, CodeScannerActivity.class);
                intent.putExtra(CodeScannerRequestType.CODE_REQUEST_TYPE,CodeScannerRequestType.REQUEST_TYPE.REQ_SELECT_OUTLET);
                startActivityForResult(intent, REQ_CODE_SCANNER);
                break;

            case R.id.home_ll_profile:
            {
/*
                // fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.home_page_frame, new MyProfileFragment());
                fragmentTransaction.commit();
*/
                // fragmentManager = getFragmentManager();
                android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if(profileFragment == null)
                profileFragment = new MyProfileFragment();
                
                transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.home_page_frame, profileFragment);
                transaction.commit();

                break;
            }
            case R.id.home_ll_home_tab:
            {
                Log.i(AppState.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
                }.getClass().getEnclosingMethod().getName()+"  home_ll_home_tab homeFragment");
/*
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                fragmentTransaction.replace(R.id.home_page_frame, homeFragment);
                fragmentTransaction.commit();
*/
                android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
                transaction.replace(R.id.home_page_frame, homeFragment);
                transaction.commit();
                break;
            }
        }
    }
}
