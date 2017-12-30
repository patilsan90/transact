package com.algo.transact.home.smart_home.module_configuration;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.algo.transact.AppConfig.AppConfig;
import com.algo.transact.R;
import com.algo.transact.login.User;
import com.algo.transact.server_communicator.base.ServerConfiguration;
import com.algo.transact.server_communicator.request_handler.SmartHomeRequestHandler;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

import static com.algo.transact.server_communicator.request_handler.SmartHomeRequestHandler.RECENT_LISTENER.DEVICE_CONFIGURATION;

public class ConfigureWifiDeviceActivity extends AppCompatActivity {

    EditText etWifiName;
    EditText etWifiPsw;
    EditText etRoomName;
    // EditText etRoomDesc;

    public static String ipAddress = "192.168.1.9";// ur ip
    public static int portNumber = 1337;// portnumber

    private Socket client;
    private OutputStreamWriter printwriter;

    private int counter;
    private Button btSaveConfigure;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_wifi_device);

        TextView tvLableWifiName = (TextView) findViewById(R.id.configure_wifi_device_tv_wifi_name);
        tvLableWifiName.setText("Configure details for " + ScanWifiActivity.connectedWifiSSID);

        SmartHomeRequestHandler.registerUser(this);

        etWifiName = (EditText) findViewById(R.id.configure_wifi_device_wifi_name);
        etWifiPsw = (EditText) findViewById(R.id.configure_wifi_device_wifi_psw);
        //  etRoomName = (EditText) findViewById(R.id.configure_wifi_device_room_name);
        //  etRoomDesc = (EditText) findViewById(R.id.configure_wifi_device_room_desc);
        btSaveConfigure = (Button) findViewById(R.id.configure_wifi_device_save_configuration);
        btSaveConfigure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveConfiguration();
            }
        });
    }

    public void saveConfiguration() {

        /*HouseRoomMapping mapping = new HouseRoomMapping();
        SmartHomeRequestHandler.addSHHouseRoomMapping(mapping, DEVICE_CONFIGURATION);*/

        showProgressDialog("Saving Configuration, wait a second");

        DeviceConfiguration configuration = new DeviceConfiguration();
        configuration.setWifi_name(etWifiName.getText().toString().trim());
        configuration.setWifi_psw(etWifiPsw.getText().toString().trim());
        configuration.setTransact_server_url(ServerConfiguration.getBaseUrlSmartHome());

        User user = User.getUserPreferences(this);
        configuration.setOwner_id(user.getMobileNo());

        SmartHomeRequestHandler.deviceConfigure(configuration, DEVICE_CONFIGURATION);

    }

    private void showProgressDialog(String message) {
        pDialog = new ProgressDialog(this);
        if (!pDialog.isShowing()) {
            pDialog.show();
            pDialog.setMessage(message);
        }
    }

    public void hideProgressDialog() {
        if (pDialog != null)
            if (pDialog.isShowing())
                pDialog.dismiss();
    }

    public void saveWifiInformation(View view) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    ipAddress = etWifiName.getText().toString().trim();
                    client = new Socket(ipAddress, portNumber);
                    printwriter = new OutputStreamWriter(client.getOutputStream(), "ISO-8859-1");
                    printwriter.write(etRoomName.getText().toString().trim());
                    printwriter.flush();
                    printwriter.close();
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void saveMACAddress(String deviceMACAddress) {

        Log.i(AppConfig.TAG, "************* Configured device Mac ID :: " + deviceMACAddress);
        Toast.makeText(this, "Congratulations, device configured successfully", Toast.LENGTH_LONG).show();
        //SmartHomeRequestHandler.registerDeviceMacAddr(deviceMACAddress, DEVICE_CONFIGURATION);
        hideProgressDialog();
        this.finish();
    }
}
