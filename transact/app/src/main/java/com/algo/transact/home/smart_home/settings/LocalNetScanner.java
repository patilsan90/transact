package com.algo.transact.home.smart_home.settings;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.util.Log;

import com.algo.transact.AppConfig.AppConfig;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;

import static android.content.Context.WIFI_SERVICE;

/**
 * Created by patilsp on 11/13/2017.
 */

public class LocalNetScanner {

    private Context context;
    private ArrayList<String> rechableIPs = new ArrayList<>();

    public LocalNetScanner(Context context) {
        this.context = context;
        start();
    }

    public ArrayList<String> getRechableIPs() {
        return rechableIPs;
    }

    /**
     * 24-bit block	10.0.0.0 – 10.255.255.255	16,777,216	10.0.0.0/8 (255.0.0.0)	24 bits	8 bits	single class A network
     * 20-bit block	172.16.0.0 – 172.31.255.255	1,048,576	172.16.0.0/12 (255.240.0.0)	20 bits	12 bits	16 contiguous class B networks
     * 16-bit block	192.168.0.0 – 192.168.255.255	65,536	192.168.0.0/16 (255.255.0.0)	16 bits	16 bits	256 contiguous class C networks
     */

    public void start() {
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                WifiManager wm = (WifiManager) context.getApplicationContext().getSystemService(WIFI_SERVICE);
                String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

                Log.d(AppConfig.TAG, "method2 first element is reachable :: " + ip);
                String ipBlocks[] = ip.split("\\.");

                Log.d(AppConfig.TAG, "method2 first element  :: " + ipBlocks[0] + "   :: " + ipBlocks[1]);

                switch (ipBlocks[0]) {
                    case "192":
                        Log.d(AppConfig.TAG, "Supported  :: " + ipBlocks[0] + "   :: " + ipBlocks[1]);
                        checkHosts(ipBlocks[0] + "." + ipBlocks[1] + "." + ipBlocks[2]);
                        // checkHosts("192.168.1");
                        break;
                    case "172":
                        Log.e(AppConfig.TAG, "Currently not supported :" + ipBlocks[0] + "   :: " + ipBlocks[1]);
                        break;
                    case "10":
                        Log.e(AppConfig.TAG, "Currently not supported :" + ipBlocks[0] + "   :: " + ipBlocks[1]);
                        break;
                }
            }
        });

        th.start();
        try {
            th.join();
            Log.d(AppConfig.TAG, "Search for all the IP's finish");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void checkHosts(String subnet) {
        int timeout = 10;
        for (int i = 1; i < 255; i++) {
            String ip = subnet + "." + i;
            try {
                if (InetAddress.getByName(ip).isReachable(timeout)) {
                    Log.i(AppConfig.TAG, ip + " is reachable");
                    rechableIPs.add(ip);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
