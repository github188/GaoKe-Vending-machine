package com.aircontrol.demo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Silenoff on 2016/11/22.
 */

public class WifiInfoUtil {

    public static String getCurrentWifiSSID(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        String ssid = wifiInfo.getSSID();
        String qq = ssid.substring(0,1);
        String ww = ssid.substring(ssid.length()-1);
        if(ssid.substring(0,1).equals("\"") && ssid.substring(ssid.length()-1).equals("\"")){
            ssid = ssid.substring(1,ssid.length() - 1);
        }
        return ssid;
    }
    public static List<ScanResult> getNearByWifiInfo(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
        wifiManager.startScan();
        List<ScanResult> list = wifiManager.getScanResults();
        for (int i = 0; i < list.size(); i++)
            for (int j = 1; j < list.size(); j++) {
                if (list.get(i).level < list.get(j).level)  //level属性即为强度
                {
                    ScanResult temp = null;
                    temp = list.get(i);
                    list.set(i, list.get(j));
                    list.set(j, temp);
                }
            }
        return list;
    }
}
