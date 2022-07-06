package com.inks.inkslibrary.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;

/**
 * @ProjectName: Demo1
 * @Package: com.hausen.demo1.utils
 * @ClassName: GetMacUtil
 * @Description: java类作用描述
 * @CreateDate: 2020/3/23 15:50
 * @UpdateDate: 2020/3/23 15:50
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class GetMacUtil {

    @SuppressLint("MissingPermission")
    public static String getMacAddress(Context context) {


        WifiManager wifiMgr = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();

        if (!wifiMgr.isWifiEnabled())
        {
            //必须先打开，才能获取到MAC地址
            wifiMgr.setWifiEnabled( true );
            //wifiMgr.setWifiEnabled( false );
        }

        wifiInfo.getMacAddress();

        String macAddress = null;
        StringBuffer buf = new StringBuffer();
        NetworkInterface networkInterface = null;
        try {
            networkInterface = NetworkInterface.getByName("eth1");
            if (networkInterface == null) {
                networkInterface = NetworkInterface.getByName("wlan0");
            }
            if (networkInterface == null) {
                return "02:00:00:00:00:00";
            }
            byte[] addr = networkInterface.getHardwareAddress();




            for (byte b : addr) {
                buf.append(String.format("%02X:", b));
            }
            if (buf.length() > 0) {
                buf.deleteCharAt(buf.length() - 1);
            }
            macAddress = buf.toString();
        } catch (SocketException e) {
            e.printStackTrace();
            return "02:00:00:00:00:00";
        }
        Log.i("----getMacAddress--1--", "" + buf.toString());
        return macAddress;
    }

    // Android 6.0以上获取WiFi的Mac地址
    //由于android6.0对wifi mac地址获取进行了限制，用原来的方法获取会获取到02:00:00:00:00:00这个固定地址。
    //但是可以通过读取节点进行获取"/sys/class/net/wlan0/address"
    public static String getMacAddr() {
        try {
            return loadFileAsString("/sys/class/net/wlan0/address")
                    .toUpperCase().substring(0, 17);
        } catch (IOException e) {
            e.printStackTrace();
            return "02:00:00:00:00:00";
        }
    }
    private static String loadFileAsString(String filePath)
            throws java.io.IOException {
        StringBuffer fileData = new StringBuffer(1000);
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead = 0;
        while ((numRead = reader.read(buf)) != -1) {
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
        }
        reader.close();
        return fileData.toString();
    }



}
