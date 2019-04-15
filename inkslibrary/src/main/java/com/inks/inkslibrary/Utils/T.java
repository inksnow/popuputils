package com.inks.inkslibrary.Utils;

/**
 * Created by Administrator on 2018/3/30 0030.
 */

import android.content.Context;
import android.widget.Toast;

import static android.widget.Toast.makeText;

/**
 * Toast统一管理类
 *
 */
public class T
{
    private static Toast toast;
    private T()
    {
        /** cannot be instantiated**/
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isShow = true;

    public static void showShort(Context context, CharSequence message)
    {
        if (isShow){
            if(toast==null){
                toast = makeText(context, message, Toast.LENGTH_SHORT);
            }else{
                toast.setText(message);
            }
            toast.show();

        }

    }


//    /**
//     * 短时间显示Toast
//     *
//     * @param context
//     * @param message
//     */
//    public static void showShort(Context context, int message)
//    {
//        if (isShow){
//            if(toast==null){
//                toast.makeText(context, message, Toast.LENGTH_SHORT);
//            }else{
//                toast.setText(message);
//            }
//            toast.show();
//
//        }
//    }
//    /**
//     * 长时间显示Toast
//     *
//     * @param context
//     * @param message
//     */
//    public static void showLong(Context context, CharSequence message)
//    {
//        if (isShow)
//            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
//    }
//
//    /**
//     * 长时间显示Toast
//     *
//     * @param context
//     * @param message
//     */
//    public static void showLong(Context context, int message)
//    {
//        if (isShow)
//            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
//    }
//    /**
//     * 自定义显示Toast时间
//     *
//     * @param context
//     * @param message
//     * @param duration
//     */
//    public static void show(Context context, CharSequence message, int duration)
//    {
//        if (isShow)
//            Toast.makeText(context, message, duration).show();
//    }
//
//    /**
//     * 自定义显示Toast时间
//     *
//     * @param context
//     * @param message
//     * @param duration
//     */
//    public static void show(Context context, int message, int duration)
//    {
//        if (isShow)
//            Toast.makeText(context, message, duration).show();
//    }

}
