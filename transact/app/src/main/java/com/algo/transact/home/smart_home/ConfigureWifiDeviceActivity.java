package com.algo.transact.home.smart_home;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.algo.transact.R;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ConfigureWifiDeviceActivity extends AppCompatActivity {

    EditText etWifiName;
    EditText etWifiPsw;
    EditText etRoomName;
    EditText etRoomDesc;

    public static String ipAddress = "192.168.1.9";// ur ip
    public static int portNumber = 1337;// portnumber

    private Socket client;
    private OutputStreamWriter printwriter;

    private int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_wifi_device);

        TextView tvLableWifiName = (TextView) findViewById(R.id.configure_wifi_device_tv_wifi_name);
        tvLableWifiName.setText("Configure details for " + ScanWifiActivity.connectedWifiSSID);

        etWifiName = (EditText) findViewById(R.id.configure_wifi_device_wifi_name);
        etWifiPsw = (EditText) findViewById(R.id.configure_wifi_device_wifi_psw);
        etRoomName = (EditText) findViewById(R.id.configure_wifi_device_room_name);
        etRoomDesc = (EditText) findViewById(R.id.configure_wifi_device_room_desc);

        etWifiName.setText("192.168.1.9");
    }

    public void saveWifiInformation(View view) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    ipAddress=etWifiName.getText().toString().trim();
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
}
