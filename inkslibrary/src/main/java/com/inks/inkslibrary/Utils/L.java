package com.inks.inkslibrary.Utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

/**
 * Created by Administrator on 2018/3/30 0030.
 */

public class L {
    private L() {
               /* 不可被实例化 */
        throw new UnsupportedOperationException("Cannot be instantiated!");
    }

    // 是否需要打印bug，可以在application的onCreate函数里面初始化
    public static boolean isDebug = true;
    private static final String TAG = "DefaultTag";

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        if (isDebug)
            Log.i(TAG, msg);
    }

    public static void d(String msg) {
        if (isDebug)
            Log.d(TAG, msg);
    }

    public static void e(String msg) {
        if (isDebug)
            Log.e(TAG, msg);
    }

    public static void v(String msg) {
        if (isDebug)
            Log.v(TAG, msg);
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }
    // 下面是传入context的函数
    public static void i(Context context, String msg) {
        if (isDebug){
            try {
                Activity activity = (Activity)context;
                Log.i(activity.getComponentName().getClassName(), msg);

            } catch (Exception e) {
                e.printStackTrace();
                //说明是ApplicationContext
                Log.i("ApplicationContext", msg);

            }
        }
    }
    public static void d(Context context, String msg) {
        if (isDebug){
            try {
                Activity activity = (Activity)context;
                Log.d(activity.getComponentName().getClassName(), msg);

            } catch (Exception e) {
                e.printStackTrace();
                //说明是ApplicationContext
                Log.d("ApplicationContext", msg);

            }
        }
    }
    public static void e(Context context, String msg) {
        if (isDebug){
            try {
                Activity activity = (Activity)context;
                Log.e(activity.getComponentName().getClassName(), msg);
            } catch (Exception e) {
                e.printStackTrace();
                //说明是ApplicationContext
                Log.e("ApplicationContext", msg);

            }
        }
    }
    public static void v(Context context, String msg) {
        if (isDebug){
            try {
                Activity activity = (Activity)context;
                Log.v(activity.getComponentName().getClassName(), msg);

            } catch (Exception e) {
                e.printStackTrace();
                //说明是ApplicationContext
                Log.v("ApplicationContext", msg);

            }
        }
    }
}
