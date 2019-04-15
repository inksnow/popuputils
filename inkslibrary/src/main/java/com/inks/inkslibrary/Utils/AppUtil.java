package com.inks.inkslibrary.Utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

/**
 * Created by inks on 2018/9/29 0029.
 */

public class AppUtil {

    /**
     * 通过指定的包名启动应用
     * @param context 上下文
     * @param packageName 指定启动的包名
     */
    public static void openAppByPackageName(Context context , String packageName) {
        Log.d("CJT","openAppByPackageName --00-- "+packageName);
        if (checkApplication(context , packageName)) {
            Log.d("CJT","openAppByPackageName --11-- "+packageName);
            Intent localIntent = new Intent("android.intent.action.MAIN", null);
            localIntent.addCategory("android.intent.category.LAUNCHER");
            List<ResolveInfo> appList = context.getPackageManager().queryIntentActivities(localIntent, 0);
            for (int i = 0; i < appList.size(); i++) {

                ResolveInfo resolveInfo = appList.get(i);
                String packageStr = resolveInfo.activityInfo.packageName;
                String className = resolveInfo.activityInfo.name;
                Log.d("CJT","openAppByPackageName --22-- packageName  ："+packageName + " -- packageStr : " + packageStr);

                if (packageStr.equals(packageName)) {
                    Log.d("CJT","openAppByPackageName --7777777777777777-- packageName  ："+packageName + " -- packageStr : " + packageStr);
                    // 这个就是你想要的那个Activity
                    ComponentName cn = new ComponentName(packageStr, className);
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setComponent(cn);
                    context.startActivity(intent);
                    Log.d("CJT" , "openApp-----111---打开完成！！");
                }
            }
        }else{
            Toast.makeText(context , "未安装此应用" , Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 卸载指定应用的包名
     * @param context 上下文
     * @param packageName 指定的应用包名
     */
    public static void unInstall(Context context , String packageName) {
        if (checkApplication(context , packageName)) {
            Uri packageURI = Uri.parse("package:" + packageName);
            Intent intent = new Intent(Intent.ACTION_DELETE);
            intent.setData(packageURI);
            context.startActivity(intent);
            Log.d("CJT" , "unInstall  --  删除成功！" + packageName);
        }
    }

    /**
     * 判断该包名的应用是否安装
     * @param context 上下文
     * @param packageName 应用包名
     * @return 是否安装
     */
    public static boolean checkApplication(Context context, String packageName) {
        if (packageName == null || "".equals(packageName)) {
            return false;
        }
        try {
            context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }





}
