package com.algo.transact.home.smart_bike;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.algo.transact.R;

public class ConfigureBikeActivity extends AppCompatActivity implements View.OnClickListener {
    private RadioButton checkBox_mode_secure, checkBox_mode_tracking, checkBox_mode_none;
    private FragmentManager fragmentManager;
    private TrackingModeFragment trackingModeFragment;
    private SecureModeFragment secureModeFragment;
    private TextView textView_share_location;
    private TextView textView_home;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_bike);
        getSupportActionBar().setTitle("Smart Bike");
        checkBox_mode_secure = findViewById(R.id.activity_smart_bike_checkbox_secure_mode);
        checkBox_mode_tracking = findViewById(R.id.activity_smart_bike_checkbox_tracking_mode);
        checkBox_mode_none = findViewById(R.id.activity_smart_bike_checkbox_off_mode);
        textView_share_location = findViewById(R.id.activity_smart_bike_tv_share_location);
        textView_share_location.setOnClickListener(this);
        textView_home = findViewById(R.id.activity_smart_bike_tv_home);
        textView_home.setOnClickListener(this);
        checkBox_mode_secure.setOnClickListener(this);
        checkBox_mode_tracking.setOnClickListener(this);
        checkBox_mode_none.setOnClickListener(this);
        fragmentManager = getSupportFragmentManager();
        checkBox_mode_secure.setChecked(true);
        launchSecureModeFragment();
    }

    private void launchSecureModeFragment() {
        if (checkBox_mode_secure.isChecked()) {
            if (secureModeFragment == null) {
                secureModeFragment = new SecureModeFragment();
            }
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (!secureModeFragment.isVisible()) {
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
                fragmentTransaction.replace(R.id.acitivty_smart_bike_layout_container, secureModeFragment);
                fragmentTransaction.commit();
            }
        }
    }

    private void launchTrackinggModeFragment() {
        if (checkBox_mode_tracking.isChecked()) {
            if (trackingModeFragment == null) {
                trackingModeFragment = new TrackingModeFragment();
            }
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (!trackingModeFragment.isVisible()) {
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
                fragmentTransaction.replace(R.id.acitivty_smart_bike_layout_container, trackingModeFragment);
                fragmentTransaction.commit();
            }

        }
    }

    private void removeFragment() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (trackingModeFragment != null && trackingModeFragment.isVisible()) {
            fragmentTransaction.remove(trackingModeFragment);
            fragmentTransaction.commit();
        } else if (secureModeFragment != null && secureModeFragment.isVisible()) {
            fragmentTransaction.remove(secureModeFragment);
            fragmentTransaction.commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_smart_bike, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_smart_bike_checkbox_secure_mode:
                checkBox_mode_secure.setChecked(true);
                checkBox_mode_none.setChecked(false);
                checkBox_mode_tracking.setChecked(false);
                launchSecureModeFragment();
                break;
            case R.id.activity_smart_bike_checkbox_tracking_mode:
                checkBox_mode_tracking.setChecked(true);
                checkBox_mode_none.setChecked(false);
                checkBox_mode_secure.setChecked(false);
                launchTrackinggModeFragment();
                break;
            case R.id.activity_smart_bike_checkbox_off_mode:
                checkBox_mode_none.setChecked(true);
                checkBox_mode_secure.setChecked(false);
                checkBox_mode_tracking.setChecked(false);
                removeFragment();
                break;
            case R.id.activity_smart_bike_tv_share_location:
                sendAppMsg();
                break;
            case R.id.activity_smart_bike_tv_home:
                this.finish();
                break;
        }
    }

    public void sendAppMsg() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String text = " message you want to share..";
    /*setPackage of the application if selected app you want to use e.g. intent.setPackege("com.whatsapp")*/
        if (intent != null) {
            intent.putExtra(Intent.EXTRA_TEXT, text);//
            startActivity(Intent.createChooser(intent, text));
        } else {
            Toast.makeText(this, "App not found", Toast.LENGTH_SHORT)
                    .show();
        }
    }
}
