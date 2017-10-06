package com.algo.transact.home.smart_home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.algo.transact.AppConfig.IntentPutExtras;
import com.algo.transact.R;
import com.algo.transact.home.outlet.data_beans.Outlet;

public class InitialOptionsActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_options);

        Button btConfigure = (Button) findViewById(R.id.initial_options_configure_device);
        Button btBuy = (Button) findViewById(R.id.initial_options_buy_new_device);
        Button btDemo = (Button) findViewById(R.id.initial_options_demo);

        btConfigure.setOnClickListener(this);
        btBuy.setOnClickListener(this);
        btDemo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.initial_options_buy_new_device:

                break;
            case R.id.initial_options_configure_device:
               this.startActivity(new Intent(this, ScanWifiActivity.class));
                //this.startActivity(new Intent(this, ConfigureDeviceActivity.class));
                break;

            case R.id.initial_options_demo:
                this.startActivity(new Intent(this, SmartHomeActivity.class));

                break;

        }
    }
}
