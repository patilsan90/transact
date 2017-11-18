package com.algo.transact.home.smart_home;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.algo.transact.AppConfig.AppConfig;
import com.algo.transact.AppConfig.Permissions;
import com.algo.transact.R;
import com.algo.transact.generic_structures.GenericAdapter;
import com.algo.transact.generic_structures.IGenericAdapter;

import java.util.ArrayList;
import java.util.List;

public class ScanWifiActivity extends AppCompatActivity implements IGenericAdapter {

    WifiManager wifiManager;
    WifiReceiver receiverWifi;
    List<ScanResult> wifiList;
    StringBuilder sb = new StringBuilder();
    private GenericAdapter genericAdapterWifiList;
    private ArrayList<ScanResult> foundWifiList;

    private boolean isExpectedDeviceConnected = false;
    WifiConfiguration wifiConf = new WifiConfiguration();

    public static String connectedWifiSSID=": ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_wifi);

        ListView lvWifiList = (ListView) findViewById(R.id.scanned_wifi_lv_wifi_list);
        TextView tvscanWifiPlaceholder = (TextView) findViewById(R.id.scan_wifi_tv_placeholder);

          lvWifiList.setEmptyView(tvscanWifiPlaceholder);
        foundWifiList = new ArrayList<>();
        genericAdapterWifiList = new GenericAdapter(this, this, lvWifiList, foundWifiList, R.layout.list_item_view_wifi);

// Initiate wifi service manager
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        // Check for wifi is disabled
        if (wifiManager.isWifiEnabled() == false) {
            // If wifi disabled then enable it
            Toast toast = Toast.makeText(getApplicationContext(), "wifi is disabled.. Enabling it",
                    Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();

            wifiManager.setWifiEnabled(true);
        }

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
            final String[] permissions = new String[]{Manifest.permission.CHANGE_WIFI_STATE};
            ActivityCompat.requestPermissions(this, permissions, Permissions.RC_HANDLE_GPS_PERM);
        }
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
            final String[] permissions = new String[]{Manifest.permission.ACCESS_WIFI_STATE};
            ActivityCompat.requestPermissions(this, permissions, Permissions.RC_HANDLE_GPS_PERM);
        }

        final String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, Permissions.RC_HANDLE_GPS_PERM);
        }

        // wifi scaned value broadcast receiver
        receiverWifi = new WifiReceiver();

        // Register broadcast receiver
        // Broacast receiver will automatically call when number of wifi connections changed
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        registerReceiver(receiverWifi, intentFilter);
        wifiManager.startScan();

    }

    public void refreshWifiList(View view) {
        wifiManager.startScan();
    }

    @Override
    public void onBackPressed() {
        unregisterReceiver(receiverWifi);
        super.onBackPressed();
    }

    @Override
    public View addViewItemToList(View view, Object listItem, int index) {

        ScanResult res = (ScanResult) listItem;
        TextView item_view = (TextView) view.findViewById(R.id.wifi_tv_wifi_name);
        ImageView iView = (ImageView) view.findViewById(R.id.wifi_iv_wifi_type);
        if (res.SSID.contains("Techies"))
            iView.setImageResource(R.drawable.ic_wifi_signal_smart);

        item_view.setText("" + res.SSID);
        return view;
    }

    @Override
    public void listUpdateCompleteNotification(ArrayList list, GenericAdapter genericAdapter) {

    }

    public void wifiConnected() {
        if (isExpectedDeviceConnected) {
            Toast.makeText(this, "Wifi Network connected", Toast.LENGTH_SHORT).show();
            unregisterReceiver(receiverWifi);
            Intent intent = new Intent(this, ConfigureWifiDeviceActivity.class);
            this.startActivity(intent);
            this.finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ScanResult targetWifi = wifiList.get(position);
        Toast.makeText(this,"Wifi:: "+targetWifi.SSID+"   "+targetWifi.capabilities, Toast.LENGTH_SHORT).show();

        String psw="dontaskpassword1";
        String securityMode=getScanResultSecurity(targetWifi);
        wifiConf.SSID = "\"" + targetWifi.SSID + "\"";
        if (securityMode.equalsIgnoreCase("OPEN")) {
            Toast.makeText(this, "Wifi  Security Mode OPEN", Toast.LENGTH_SHORT).show();
            wifiConf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);

        } else if (securityMode.equalsIgnoreCase("WEP")) {
            Toast.makeText(this, "Wifi  Security Mode WEP", Toast.LENGTH_SHORT).show();
            wifiConf.wepKeys[0] = "\"" + psw + "\"";
            wifiConf.wepTxKeyIndex = 0;
            wifiConf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            wifiConf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);

        } else if (securityMode.equalsIgnoreCase("PSK")) {
            Toast.makeText(this, "Wifi  Security Mode PSK", Toast.LENGTH_SHORT).show();
            wifiConf.preSharedKey = "\"" + psw + "\"";
            wifiConf.hiddenSSID = true;
            wifiConf.status = WifiConfiguration.Status.ENABLED;
            wifiConf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            wifiConf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            wifiConf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            wifiConf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            wifiConf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            wifiConf.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
            wifiConf.allowedProtocols.set(WifiConfiguration.Protocol.WPA);


        } else {
            Log.i(AppConfig.TAG, "# Unsupported security mode: "+securityMode);
            Toast.makeText(this, "Wifi  Security Mode Unknown:: "+securityMode, Toast.LENGTH_SHORT).show();
        }
        int res = wifiManager.addNetwork(wifiConf);
        Toast.makeText(this, "Wifi  ID :: "+res, Toast.LENGTH_SHORT).show();
        wifiManager.disconnect();
        wifiManager.enableNetwork(res, true);
        wifiManager.reconnect();
        connectedWifiSSID = targetWifi.SSID;
        isExpectedDeviceConnected = true;
    }

    public String getScanResultSecurity(ScanResult scanResult) {

        final String cap = scanResult.capabilities;
        final String[] securityModes = { "WEP", "PSK", "EAP" };

        for (int i = securityModes.length - 1; i >= 0; i--) {
            if (cap.contains(securityModes[i])) {
                return securityModes[i];
            }
        }

        return "OPEN";
    }

    // Broadcast receiver class called its receive method
    // when number of wifi connections changed
    class WifiReceiver extends BroadcastReceiver {

        // This method call when number of wifi connections changed
        public void onReceive(Context c, Intent intent) {


            final String action = intent.getAction();
            // Toast.makeText(c, "Wifi  Action " + action, Toast.LENGTH_SHORT).show();

            if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(action)) {
                NetworkInfo netInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                //WifiManager wifiManager = (WifiManager) c.getSystemService(Context.WIFI_SERVICE);
                if ((netInfo.getDetailedState() == (NetworkInfo.DetailedState.CONNECTED))) {
                    // your wifi is connected, do what you want to do

                    wifiConnected();

                } else if ((netInfo.getDetailedState() == (NetworkInfo.DetailedState.DISCONNECTED))) {
                   // Toast.makeText(c, "Wifi Network not connected", Toast.LENGTH_SHORT).show();
                }
            } else {
                wifiList = wifiManager.getScanResults();
                Log.i(AppConfig.TAG, "Wifi list size " + wifiList.size());
                for (int i = 0; i < wifiList.size(); i++) {
                    ScanResult res = wifiList.get(i);
                    boolean devExist = false;
                    for (int j = 0; j < foundWifiList.size(); j++) {
                        if (foundWifiList.get(j).SSID.equals(res.SSID)) {
                            devExist = true;
                            break;
                        }
                    }
                    if (!devExist) {
                        foundWifiList.add(res);
                        genericAdapterWifiList.notifyDataSetChanged();
                    }
                }

            }

        }

    }
}
