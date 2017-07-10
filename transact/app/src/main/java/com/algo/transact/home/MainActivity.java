package com.algo.transact.home;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.algo.transact.AppConfig.AppState;
import com.algo.transact.AppConfig.SQLiteHandler;
import com.algo.transact.AppConfig.SessionManager;
import com.algo.transact.R;
import com.algo.transact.login.LoginActivity;
import com.algo.transact.login.LoginFragment;

public class MainActivity extends AppCompatActivity {

    private HomeFragment homeFragment;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private SQLiteHandler db;
    public SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  LinearLayout ll_sat = (LinearLayout) findViewById(R.id.shop_at_shop);
       // ll_sat.setOnClickListener();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        homeFragment = new HomeFragment();
        fragmentTransaction.add(R.id.home_page_frame, homeFragment);
        fragmentTransaction.commit();

        AppState.getInstance().mainActivity=this;
        db = new SQLiteHandler(getApplicationContext());
        session= new SessionManager(getApplicationContext());

        if(!session.isLoggedIn())
        {
            Log.i(AppState.TAG, "Logged in check");
            session.logoutUser(db,session);
            Intent intent= new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            this.finish();
        }


       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        createDrawer();

    }

    private void createDrawer() {
        // DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.home_drawer_layout);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                Log.d("Mall_Drawer", "onDrawerClosed: " + getTitle());

                invalidateOptionsMenu();
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);

        /* mDrawerLayout.setDrawerListener(mDrawerToggle); check if any issue comes because of addDrawerListener. */
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }
}
