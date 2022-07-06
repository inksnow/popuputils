package com.inks.inkslibrary.Utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

public class MacUtils {
    /**
     * 获取MAC地址
     * 适配各个版本Android系统 <6.0===》getWifiMac 6.0-7.0===》getMacAddressFromFile   >=7.0=====》
     * param context
     * return
     */
    public static String getMacAddress(@NonNull Context context) {
        String mac = "02:00:00:00:00:00";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mac = getWifiMac(context);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mac = getMacAddressFromFile();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mac = getMacFromHardware();
        }
        return mac;
    }
    /**
     * Android 6.0以下
     * 基础Mac获取方法
     *
     * @return
     */
    private static String getWifiMac(@NonNull Context context) {
        ////LogUtils.logD(TAG, "获取WifiMac地址===》getWifiMac方法 当前系统版本==》" + Build.VERSION.SDK_INT);
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo.getMacAddress();
    }

    /**
     * Android 6.0（包括） - Android 7.0（不包括）
     * 通过读取系统文件获取Mac
     *
     * @return
     */
    private static String getMacAddressFromFile() {
        //LogUtils.logD(TAG, "获取WifiMac地址===》getMacAddressFromFile方法 当前系统版本==》" + Build.VERSION.SDK_INT);
        String WifiAddress = "02:00:00:00:00:00";
        try {
            WifiAddress = new BufferedReader(new FileReader(new File("/sys/class/net/wlan0/address"))).readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return WifiAddress;
    }

    /**
     * Android 7.0之后获取Mac地址 需要通过遍历网络接口获取Ip 否则将永远返回02:00:00:00:00
     * 遍历循环所有的网络接口，找到接口是 wlan0
     * 必须的权限 <uses-permission android:name="android.permission.INTERNET"></uses-permission>
     *
     * @return
     */
    private static String getMacFromHardware() {
        //LogUtils.logD(TAG, "获取WifiMac地址===》getMacFromHardware方法 当前系统版本==》" + Build.VERSION.SDK_INT);
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return null;
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
