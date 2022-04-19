package com.inks.inkslibrary.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * @ProjectName: Demo1
 * @Package: com.hausen.demo1.utils
 * @ClassName: GetIpAndDisplayUtil
 * @Description: java类作用描述
 * @CreateDate: 2020/3/27 10:56
 * @UpdateDate: 2020/3/27 10:56
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class GetIpAndDisplayUtil {

    public static String getIp(){
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {  //IPv4地址
                        return inetAddress.getHostAddress();
                    }
                }
            }
            return "";
        } catch (SocketException ex) {
           return "";
        }
    }

    /**
     * 判断IP地址的合法性，这里采用了正则表达式的方法来判断 return true，合法
     */
    public static boolean ipCheck(String text) {
        if (text != null && !text.isEmpty()) {
            // 定义正则表达式
            String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                    + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\." +"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                    + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
            // 判断ip地址是否与正则表达式匹配
            if (text.matches(regex)) {
                // 返回判断信息
                return true;
            } else {
                // 返回判断信息
                return false;
            }
        }
        return false;
    }


    public static String getDisplay(Activity activity){
    Display display = activity.getWindowManager().getDefaultDisplay();

        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;

        return width+"*"+height;

}

    public static String getRealMetrics(Activity activity){
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(metric);
        int width = metric.widthPixels; // 宽度（PX）
        int height = metric.heightPixels; // 高度（PX）
        float density = metric.density; // 密度（0.75 / 1.0 / 1.5）
        int densityDpi = metric.densityDpi;
        return width+"*"+height;

    }

    public static String getScreenType(Activity activity){
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(metric);
        int width = metric.widthPixels; // 宽度（PX）
        int height = metric.heightPixels; // 高度（PX）
        if(width>height){
            return "横屏";
        }else{
            return "竖屏";
        }
    }

    public static String getScreenType(Context context){
        DisplayMetrics dm2 = context.getResources().getDisplayMetrics();
        int width = dm2.widthPixels; // 宽度（PX）
        int height = dm2.heightPixels; // 高度（PX）
        if(width>height){
            return "横屏";
        }else{
            return "竖屏";
        }

    }




    public static String getOutSize(Activity activity){

        Point outSize = new Point();
        activity.getWindowManager().getDefaultDisplay().getRealSize(outSize);
        int x = outSize.x;
        int y = outSize.y;

        return x+"*"+y;
    }

    public static String getOutMetrics (Activity activity){

        DisplayMetrics outMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(outMetrics);
        int widthPixel = outMetrics.widthPixels;
        int heightPixel = outMetrics.heightPixels;

        return widthPixel+"*"+heightPixel;
    }




    public static int getDisplayWidth(Activity activity){

        WindowManager manager = activity.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;

//
//
//        Display display = activity.getWindowManager().getDefaultDisplay();
//
//        Point point = new Point();
//        display.getSize(point);
//        int width = point.x;
//        int height = point.y;

        return width;

    }

    public static int getDisplayHeight(Activity activity){

        WindowManager manager = activity.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;

//
//
//        Display display = activity.getWindowManager().getDefaultDisplay();
//
//        Point point = new Point();
//        display.getSize(point);
//        int width = point.x;
//        int height = point.y;

        return height;

    }
    
}
