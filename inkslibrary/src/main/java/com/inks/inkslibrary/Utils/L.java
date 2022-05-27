package com.inks.inkslibrary.Utils;

import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;

import java.util.Formatter;

/**
 *
 * ProjectName:
 * Package:        com.inks.inkslibrary.Utils
 * ClassName:      L
 * Description:    log打印
 * Author:         inks
 * CreateDate:     2022/5/27 10:59
 */

public class L {
    private L() {
        /* 不可被实例化 */
        throw new UnsupportedOperationException("Cannot be instantiated!");
    }

    // 是否需要打印bug，可以在application的onCreate函数里面初始化
    public static boolean isDebug = true;
    private static final String TAG = "DefaultTag";

    public static void i(String msg) {
        if (isDebug)
            Log.i(getClassName(), msg);
    }

    public static void d(String msg) {
        if (isDebug)
            Log.d(getClassName(), msg);
    }

    public static void e(String msg) {
        if (isDebug)
            Log.e(getClassName(), msg);
    }

    public static void v(String msg) {
        if (isDebug)
            Log.v(getClassName(), msg);
    }
    public static void w(String msg) {
        if (isDebug)
            Log.w(getClassName(), msg);
    }

    public static void is(String msg) {
        if (isDebug)
            Log.i(getClassLine(), msg);
    }

    public static void ds(String msg) {
        if (isDebug)
            Log.d(getClassLine(), msg);
    }

    public static void es(String msg) {
        if (isDebug)
            Log.e(getClassLine(), msg);
    }

    public static void vs(String msg) {
        if (isDebug)
            Log.v(getClassLine(), msg);
    }

    public static void ws(String msg) {
        if (isDebug)
            Log.w(getClassLine(), msg);
    }

    public static void iii(String msg) {
        if (isDebug)
            Log.i(getClassLine(), " "+"\n"+msg);
    }

    public static void ddd(String msg) {
        if (isDebug)
            Log.d(getClassLine(), " "+"\n"+msg);
    }

    public static void eee(String msg) {
        if (isDebug)
            Log.e(getClassLine(), " "+"\n"+msg);
    }

    public static void vvv(String msg) {
        if (isDebug)
            Log.v(getClassLine(), " "+"\n"+msg);
    }

    public static void www(String msg) {
        if (isDebug)
            Log.w(getClassLine(), " "+"\n"+msg);
    }


    public static void ii(Object... contents) {
        if (isDebug)
            Log.i(getClassLine(), contents2String(contents));
    }

    public static void dd(Object... contents) {
        if (isDebug)
            Log.d(getClassLine(), contents2String(contents));
    }

    public static void ee(Object... contents) {
        if (isDebug)
            Log.e(getClassLine(), contents2String(contents));
    }

    public static void vv(Object... contents) {
        if (isDebug)
            Log.v(getClassLine(), contents2String(contents));
    }

    public static void ww(Object... contents) {
        if (isDebug)
            Log.w(getClassLine(), contents2String(contents));
    }


    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            Log.d(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            Log.e(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            Log.v(tag, msg);
    }
    public static void w(String tag, String msg) {
        if (isDebug)
            Log.w(tag, msg);
    }


    private static String getClassLine() {
        final StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        final int stackIndex = 2;
        if (stackIndex >= stackTrace.length) {
            return TAG;
        }

        StackTraceElement targetElement = stackTrace[stackIndex];
        final String fileName = getFileName(targetElement);
        return new Formatter()
                .format("(%s:%d)",
                        fileName,
                        targetElement.getLineNumber())
                .toString();
    }

    private static String getClassName() {
        final StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        final int stackIndex = 2;
        if (stackIndex >= stackTrace.length) {
            return "DefaultTag";
        }
        StackTraceElement targetElement = stackTrace[stackIndex];
        String className = targetElement.getClassName();

        String[] classNameInfo = className.split("\\.");
        if (classNameInfo.length > 0) {
            className = classNameInfo[classNameInfo.length - 1];
        }
        return className+":"+targetElement.getLineNumber();
    }


    private static String getFileName(final StackTraceElement targetElement) {
        String fileName = targetElement.getFileName();
        if (fileName != null) return fileName;
        String className = targetElement.getClassName();
        String[] classNameInfo = className.split("\\.");
        if (classNameInfo.length > 0) {
            className = classNameInfo[classNameInfo.length - 1];
        }
        int index = className.indexOf('$');
        if (index != -1) {
            className = className.substring(0, index);
        }
        return className + ".java";
    }

    private static String contents2String(Object... contents){
        String back = " ";
        if(contents==null){
            return back;
        }
        for (int i = 0; i < contents.length; i++) {
            try {
                if (contents[i] instanceof Intent
                        ||contents[i] instanceof Long
                        ||contents[i] instanceof Double
                        ||contents[i] instanceof Float
                        ||contents[i] instanceof String
                ){
                    back = back+"\n"+contents[i].toString();

                } else{
                    back = back+"\n"+new  Gson().toJson(contents[i]);

                }

            }catch (Exception | Error e ){
                back = back+"\n"+contents[i].getClass().getSimpleName()+" 转Json失败";

            }
        }

        return back;

    }

}
