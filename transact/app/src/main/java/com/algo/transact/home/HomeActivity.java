package com.algo.transact.home;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.algo.transact.AppConfig.AppConfig;
import com.algo.transact.AppConfig.IntentPutExtras;
import com.algo.transact.AppConfig.IntentResultCode;
import com.algo.transact.AppConfig.SessionManager;
import com.algo.transact.R;
import com.algo.transact.home.outlet.data_beans.Outlet;
import com.algo.transact.home.outlet.outlet_front.OutletFront;
import com.algo.transact.login.LoginActivity;
import com.algo.transact.optical_code.CodeScannerActivity;
import com.algo.transact.optical_code.CodeScannerRequestType;
import com.algo.transact.optical_code.OpticalCode;
import com.google.android.gms.vision.text.Line;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;

import static com.algo.transact.login.LoginActivity.activity;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private int REQ_CODE_SCANNER = 100;

    private HomeFragment homeFragment;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    boolean isDrawerOpen = false;

    private SessionManager session;
    private Button btLogout;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    LinearLayout llProfileTab;
    private MyProfileFragment profileFragment;
    private TextView textView_profile, textView_home, textView_favourites, textView_updates;
    private Drawable drawable_profile, drawable_home, drawable_favourites, drawable_updates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.appBarColor));
        setContentView(R.layout.activity_home);

        textView_home = findViewById(R.id.content_main_home_tv_home);
        textView_favourites = findViewById(R.id.content_main_home_tv_favourites);
        textView_profile = findViewById(R.id.content_main_home_tv_profile);
        textView_updates = findViewById(R.id.content_main_home_tv_updates);
        drawable_home = getResources().getDrawable(R.drawable.ic_icon_home);
        drawable_favourites = getResources().getDrawable(R.drawable.ic_favourites);
        drawable_profile = getResources().getDrawable(R.drawable.ic_profile);
        drawable_updates = getResources().getDrawable(R.drawable.ic_updates);
        LinearLayout llProfileTab = (LinearLayout) findViewById(R.id.home_ll_profile);
        llProfileTab.setOnClickListener(this);

        LinearLayout llHomeTab = (LinearLayout) findViewById(R.id.home_ll_home_tab);
        llHomeTab.setOnClickListener(this);

        LinearLayout llUpdateTab = findViewById(R.id.home_ll_updates);
        llUpdateTab.setOnClickListener(this);

        LinearLayout llFavouriteTab = findViewById(R.id.home_ll_favourites);
        llFavouriteTab.setOnClickListener(this);

        FloatingActionButton fabCodeScanner = (FloatingActionButton) findViewById(R.id.home_fab_code_scanner);
        fabCodeScanner.setOnClickListener(this);

        Log.d(AppConfig.TAG, "Token ::" + FirebaseInstanceId.getInstance().getToken());
//        homeFragment = new HomeFragment();
//
///*
//        fragmentManager = getFragmentManager();
//        fragmentTransaction = fragmentManager.beginTransaction();
//        homeFragment = new HomeFragment();
//        fragmentTransaction.add(R.id.home_page_frame, homeFragment);
//        fragmentTransaction.commit();
//*/
//
//
//        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        //transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
//        transaction.replace(R.id.home_page_frame, homeFragment);
//        transaction.commit();


        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            Log.i(AppConfig.TAG, "Logged in check");
            session.logoutUser(session);
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
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
                Log.i(AppConfig.TAG, "onDrawerOpened " + getTitle());
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                Log.i(AppConfig.TAG, "onDrawerClosed: " + getTitle());
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
        Log.i(AppConfig.TAG, "onOptionsItemSelected: " + item.getItemId());
        if (mDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    public void openDrawer(View view) {
        Log.i(AppConfig.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName());

        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    @Override
    protected void onResume() {
        super.onResume();
        addHomeFragment();

    }

    private void addHomeFragment() {
        homeFragment = new HomeFragment();
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_page_frame, homeFragment);
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.commit();
        changeHomeFocus();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.i(AppConfig.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
        }.getClass().getEnclosingMethod().getName() + " requestCode:: " + requestCode + " resultCode:: " + resultCode);

        if (data == null) {
            Log.e(AppConfig.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
            }.getClass().getEnclosingMethod().getName() + "  No Data Received");
            return;
        }
        OpticalCode barcodeDetails = (OpticalCode) data.getSerializableExtra(IntentPutExtras.CODE_OBJECT);

        if (requestCode == REQ_CODE_SCANNER) {
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

        switch (v.getId()) {
            case R.id.home_fab_code_scanner:
                Intent intent = new Intent(this, CodeScannerActivity.class);
                intent.putExtra(CodeScannerRequestType.CODE_REQUEST_TYPE, CodeScannerRequestType.REQUEST_TYPE.REQ_SELECT_OUTLET);
                startActivityForResult(intent, REQ_CODE_SCANNER);
                break;

            case R.id.home_ll_profile: {
/*
                // fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.home_page_frame, new MyProfileFragment());
                fragmentTransaction.commit();
*/
                // fragmentManager = getFragmentManager();
                changeImageColor(R.id.home_ll_profile);
                android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (profileFragment == null)
                    profileFragment = new MyProfileFragment();

                transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.home_page_frame, profileFragment);
                transaction.commit();

                break;
            }
            case R.id.home_ll_home_tab: {
                changeImageColor(R.id.home_ll_home_tab);
                Log.i(AppConfig.TAG, "Class: " + this.getClass().getSimpleName() + " Method: " + new Object() {
                }.getClass().getEnclosingMethod().getName() + "  home_ll_home_tab homeFragment");
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
            case R.id.home_ll_favourites:
                changeImageColor(R.id.home_ll_favourites);
                break;
            case R.id.home_ll_updates:
                changeImageColor(R.id.home_ll_updates);
                break;
        }
    }

    private void changeImageColor(int imageId) {
        switch (imageId) {
            case R.id.home_ll_profile:
                changeProfileFocus();
                break;
            case R.id.home_ll_home_tab:
                changeHomeFocus();
                break;
            case R.id.home_ll_favourites:
                changeFavouriteFocus();
                break;
            case R.id.home_ll_updates:
                changeUpdateFocus();
                break;
        }
    }

    private void changeProfileFocus() {
        Drawable drawable_profile = getResources().getDrawable(R.drawable.ic_profile);
        drawable_profile.setTint(getResources().getColor(R.color.appBarColor));
        Drawable drawable_home = getResources().getDrawable(R.drawable.ic_icon_home);
        drawable_home.setTint(getResources().getColor(R.color.home_page_text_color_normal));
        Drawable drawable_favourites = getResources().getDrawable(R.drawable.ic_favourites);
        drawable_favourites.setTint(getResources().getColor(R.color.home_page_text_color_normal));
        textView_profile.setCompoundDrawablesWithIntrinsicBounds(null, drawable_profile, null, null);
        textView_updates.setCompoundDrawablesWithIntrinsicBounds(null, drawable_updates, null, null);
        textView_favourites.setCompoundDrawablesWithIntrinsicBounds(null, drawable_favourites, null, null);
        textView_home.setCompoundDrawablesWithIntrinsicBounds(null, drawable_home, null, null);
        textView_profile.setTextColor(getResources().getColor(R.color.appBarColor));
        textView_home.setTextColor(getResources().getColor(R.color.home_page_text_color_normal));
        textView_favourites.setTextColor(getResources().getColor(R.color.home_page_text_color_normal));
        textView_updates.setTextColor(getResources().getColor(R.color.home_page_text_color_normal));
    }

    private void changeHomeFocus() {
        Drawable drawable_home = getResources().getDrawable(R.drawable.ic_icon_home);
        drawable_home.setTint(getResources().getColor(R.color.appBarColor));
        Drawable drawable_profile = getResources().getDrawable(R.drawable.ic_profile);
        drawable_profile.setTint(getResources().getColor(R.color.home_page_text_color_normal));
        Drawable drawable_favourites = getResources().getDrawable(R.drawable.ic_favourites);
        drawable_favourites.setTint(getResources().getColor(R.color.home_page_text_color_normal));
        Drawable drawable_updates = getResources().getDrawable(R.drawable.ic_updates);
        drawable_updates.setTint(getResources().getColor(R.color.home_page_text_color_normal));
        textView_home.setCompoundDrawablesWithIntrinsicBounds(null, drawable_home, null, null);
        textView_profile.setCompoundDrawablesWithIntrinsicBounds(null, drawable_profile, null, null);
        textView_updates.setCompoundDrawablesWithIntrinsicBounds(null, drawable_updates, null, null);
        textView_favourites.setCompoundDrawablesWithIntrinsicBounds(null, drawable_favourites, null, null);
        textView_home.setTextColor(getResources().getColor(R.color.appBarColor));
        textView_profile.setTextColor(getResources().getColor(R.color.home_page_text_color_normal));
        textView_favourites.setTextColor(getResources().getColor(R.color.home_page_text_color_normal));
        textView_updates.setTextColor(getResources().getColor(R.color.home_page_text_color_normal));
    }

    private void changeUpdateFocus() {
        Drawable drawable_updates = getResources().getDrawable(R.drawable.ic_updates);
        drawable_updates.setTint(getResources().getColor(R.color.appBarColor));
        Drawable drawable_home = getResources().getDrawable(R.drawable.ic_icon_home);
        drawable_home.setTint(getResources().getColor(R.color.home_page_text_color_normal));
        Drawable drawable_profile = getResources().getDrawable(R.drawable.ic_profile);
        drawable_profile.setTint(getResources().getColor(R.color.home_page_text_color_normal));
        Drawable drawable_favourites = getResources().getDrawable(R.drawable.ic_favourites);
        drawable_favourites.setTint(getResources().getColor(R.color.home_page_text_color_normal));
        textView_profile.setCompoundDrawablesWithIntrinsicBounds(null, drawable_profile, null, null);
        textView_updates.setCompoundDrawablesWithIntrinsicBounds(null, drawable_updates, null, null);
        textView_favourites.setCompoundDrawablesWithIntrinsicBounds(null, drawable_favourites, null, null);
        textView_home.setCompoundDrawablesWithIntrinsicBounds(null, drawable_home, null, null);
        textView_updates.setTextColor(getResources().getColor(R.color.appBarColor));
        textView_profile.setTextColor(getResources().getColor(R.color.home_page_text_color_normal));
        textView_favourites.setTextColor(getResources().getColor(R.color.home_page_text_color_normal));
        textView_home.setTextColor(getResources().getColor(R.color.home_page_text_color_normal));


    }

    private void changeFavouriteFocus() {
        Drawable drawable_favourites = getResources().getDrawable(R.drawable.ic_favourites);
        drawable_favourites.setTint(getResources().getColor(R.color.appBarColor));
        Drawable drawable_home = getResources().getDrawable(R.drawable.ic_icon_home);
        drawable_home.setTint(getResources().getColor(R.color.home_page_text_color_normal));
        Drawable drawable_profile = getResources().getDrawable(R.drawable.ic_profile);
        drawable_profile.setTint(getResources().getColor(R.color.home_page_text_color_normal));
        textView_profile.setCompoundDrawablesWithIntrinsicBounds(null, drawable_profile, null, null);
        textView_updates.setCompoundDrawablesWithIntrinsicBounds(null, drawable_updates, null, null);
        textView_favourites.setCompoundDrawablesWithIntrinsicBounds(null, drawable_favourites, null, null);
        textView_home.setCompoundDrawablesWithIntrinsicBounds(null, drawable_home, null, null);
        textView_favourites.setTextColor(getResources().getColor(R.color.appBarColor));
        textView_profile.setTextColor(getResources().getColor(R.color.home_page_text_color_normal));
        textView_home.setTextColor(getResources().getColor(R.color.home_page_text_color_normal));
        textView_updates.setTextColor(getResources().getColor(R.color.home_page_text_color_normal));
    }
}
