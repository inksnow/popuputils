package com.inks.inkslibrary.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.Set;

/**
 * Created by Administrator on 2017/12/28 0028.
 */

public class SharedPrefencesUtils
{

    public final static String BRIDGE_LIB_SHAREF_FILE_NAME = "myprefile";//sharedprefences file.xml

    public static String getStringSharePref(Context context, String key ) {
        SharedPreferences sharpfs = context.getSharedPreferences( BRIDGE_LIB_SHAREF_FILE_NAME, Context.MODE_PRIVATE );
        if( sharpfs.contains( key ) )
        {
            return sharpfs.getString( key, null );
        }
        return null;
    }

    public static String getStringSharePref(
            Context context,
            String key,
            String defaultValue )
    {
        SharedPreferences sharpfs = context.getSharedPreferences( BRIDGE_LIB_SHAREF_FILE_NAME, Context.MODE_MULTI_PROCESS );
        if( null != sharpfs && sharpfs.contains( key ) )
        {
            return sharpfs.getString( key, defaultValue );
        }
        return defaultValue;
    }

    public static void setStringSharePref(
            Context context,
            String key,
            String value )
    {
        SharedPreferences sharpfs = context.getSharedPreferences( BRIDGE_LIB_SHAREF_FILE_NAME, Context.MODE_MULTI_PROCESS );
        if( null != sharpfs && !TextUtils.isEmpty( key ) && !TextUtils.isEmpty( value ) )
        {
            SharedPreferences.Editor editor = sharpfs.edit();
            editor.putString( key, value );
            editor.commit();
        }
    }

    public static int getIntSharePref(
            Context context,
            String key,
            int defValue )
    {
        SharedPreferences sharpfs = context.getSharedPreferences( BRIDGE_LIB_SHAREF_FILE_NAME, Context.MODE_MULTI_PROCESS );
        if( null != sharpfs && sharpfs.contains( key ) && !TextUtils.isEmpty( key ) )
        {
            return sharpfs.getInt( key, defValue );
        }
        return defValue;
    }

    public static int getIntSharePref(
            Context context,
            String key )
    {
        SharedPreferences sharpfs = context.getSharedPreferences( BRIDGE_LIB_SHAREF_FILE_NAME, Context.MODE_MULTI_PROCESS );
        if( null != sharpfs && sharpfs.contains( key ) && !TextUtils.isEmpty( key ) )
        {
            return sharpfs.getInt( key, -1 );
        }
        return -1;
    }

    public static void setIntSharePref(
            Context context,
            String key,
            int value )
    {
        SharedPreferences sharpfs = context.getSharedPreferences( BRIDGE_LIB_SHAREF_FILE_NAME, Context.MODE_MULTI_PROCESS );
        if( null != sharpfs && !TextUtils.isEmpty( key ) )
        {
            SharedPreferences.Editor editor = sharpfs.edit();
            editor.putInt( key, value );
            editor.commit();
        }
    }

    public static long getLongSharePref(
            Context context,
            String key,
            long defValue )
    {
        SharedPreferences sharpfs = context.getSharedPreferences( BRIDGE_LIB_SHAREF_FILE_NAME, Context.MODE_MULTI_PROCESS );
        if( null != sharpfs && sharpfs.contains( key ) && !TextUtils.isEmpty( key ) )
        {
            return sharpfs.getLong( key, defValue );
        }
        return -1;
    }

    public static long getLongSharePref(
            Context context,
            String key )
    {
        SharedPreferences sharpfs = context.getSharedPreferences( BRIDGE_LIB_SHAREF_FILE_NAME, Context.MODE_MULTI_PROCESS );
        if( null != sharpfs && sharpfs.contains( key ) && !TextUtils.isEmpty( key ) )
        {
            return sharpfs.getLong( key, -1 );
        }
        return -1;
    }

    public static void setLongSharePref(
            Context context,
            String key,
            long value )
    {
        SharedPreferences sharpfs = context.getSharedPreferences( BRIDGE_LIB_SHAREF_FILE_NAME, Context.MODE_MULTI_PROCESS );
        if( null != sharpfs && !TextUtils.isEmpty( key ) )
        {
            SharedPreferences.Editor editor = sharpfs.edit();
            editor.putLong( key, value );
            editor.commit();
        }
    }

    public static boolean getBooleanSharePref(
            Context context,
            String key,
            boolean defaultValue )
    {
        SharedPreferences sharpfs = context.getSharedPreferences( BRIDGE_LIB_SHAREF_FILE_NAME, Context.MODE_MULTI_PROCESS );
        if( null != sharpfs && sharpfs.contains( key ) && !TextUtils.isEmpty( key ) )
        {
            return sharpfs.getBoolean( key, defaultValue );
        }
        return defaultValue;
    }

    public static void setBooleanSharePref(
            Context context,
            String key,
            boolean value )
    {
        SharedPreferences sharpfs = context.getSharedPreferences( BRIDGE_LIB_SHAREF_FILE_NAME, Context.MODE_MULTI_PROCESS );
        if( null != sharpfs && !TextUtils.isEmpty( key ) )
        {
            SharedPreferences.Editor editor = sharpfs.edit();
            editor.putBoolean( key, value );
            editor.commit();
        }
    }

//----------------------------------------------------------------------
//自己传递Shared file name
//----------------------------------------------------------------------

    public static int getIntSharePrefs(
            Context context,
            String key,
            int defValue,
            String fileName )
    {
        if( null == context || TextUtils.isEmpty( key ) )
        {
            return defValue;
        }
        SharedPreferences sp = context.getSharedPreferences( fileName, Context.MODE_MULTI_PROCESS );
        return sp.getInt( key, defValue );
    }

    public static void setIntSharePrefs(
            Context context,
            String key,
            int value,
            String fileName )
    {
        if( null == context )
        {
            return;
        }
        SharedPreferences sp = context.getSharedPreferences( fileName, Context.MODE_MULTI_PROCESS );
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt( key, value );
        editor.commit();
    }

    public static long getLongSharePrefs(
            Context context,
            String key,
            long defValue,
            String fileName )
    {
        if( null == context || TextUtils.isEmpty( key ) )
        {
            return defValue;
        }
        SharedPreferences sp = context.getSharedPreferences( fileName, Context.MODE_MULTI_PROCESS );
        return sp.getLong( key, defValue );
    }

    public static void setLongSharePrefs(
            Context context,
            String key,
            long value,
            String fileName )
    {
        if( null == context )
        {
            return;
        }
        SharedPreferences sp = context.getSharedPreferences( fileName, Context.MODE_MULTI_PROCESS );
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong( key, value );
        editor.commit();
    }

    public static float getFloatSharePrefs(
            Context context,
            String key,
            float defValue,
            String fileName )
    {
        if( null == context || TextUtils.isEmpty( key ) )
        {
            return defValue;
        }
        SharedPreferences sp = context.getSharedPreferences( fileName, Context.MODE_MULTI_PROCESS );
        return sp.getFloat( key, defValue );
    }

    public static void setFloatSharePrefs(
            Context context,
            String key,
            float value,
            String fileName )
    {
        if( null == context )
        {
            return;
        }
        SharedPreferences sp = context.getSharedPreferences( fileName, Context.MODE_MULTI_PROCESS );
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat( key, value );
        editor.commit();
    }

    public static String getStringSharePrefs(
            Context context,
            String key,
            String defValue,
            String fileName )
    {
        if( null == context || TextUtils.isEmpty( key ) )
        {
            return defValue;
        }
        SharedPreferences sp = context.getSharedPreferences( fileName, Context.MODE_MULTI_PROCESS );
        return sp.getString( key, defValue );
    }

    public static void setStringSharePrefs(
            Context context,
            String key,
            String value,
            String fileName )
    {
        if( null == context )
        {
            return;
        }
        SharedPreferences sp = context.getSharedPreferences( fileName, Context.MODE_MULTI_PROCESS );
        SharedPreferences.Editor editor = sp.edit();
        editor.putString( key, value );
        editor.commit();
    }

    public static Set<String> getSetSharePrfs(
            Context context,
            String key,
            Set<String> defValue,
            String fileName )
    {
        if( null == context || TextUtils.isEmpty( key ) )
        {
            return defValue;
        }
        SharedPreferences sp = context.getSharedPreferences( fileName, Context.MODE_MULTI_PROCESS );
        return sp.getStringSet( key, defValue );
    }

    public static void setSetSharePrfs(
            Context context,
            String key,
            Set<String> value,
            String fileName )
    {
        if( null == context )
        {
            return;
        }
        SharedPreferences sp = context.getSharedPreferences( fileName, Context.MODE_MULTI_PROCESS );
        SharedPreferences.Editor editor = sp.edit();
        editor.putStringSet( key, value );
        editor.commit();
    }

    public static boolean getBooleanSharePrfs(
            Context context,
            String key,
            boolean defValue,
            String fileName )
    {
        if( null == context || TextUtils.isEmpty( key ) )
        {
            return defValue;
        }
        SharedPreferences sp = context.getSharedPreferences( fileName, Context.MODE_MULTI_PROCESS );
        return sp.getBoolean( key, defValue );
    }

    public static void setBooleanSharePrfs(
            Context context,
            String key,
            boolean value,
            String fileName )
    {
        if( null == context )
        {
            return;
        }
        SharedPreferences sp = context.getSharedPreferences( fileName, Context.MODE_MULTI_PROCESS );
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean( key, value );
        editor.commit();
    }

    public static void clearData(
            Context context,
            String key )
    {
        if( context != null && !TextUtils.isEmpty( key ) )
        {
            SharedPreferences sharpfs = context.getSharedPreferences( BRIDGE_LIB_SHAREF_FILE_NAME, Context.MODE_MULTI_PROCESS );
            SharedPreferences.Editor editor = sharpfs.edit();
            editor.remove( key ).commit();
        }
    }
}
