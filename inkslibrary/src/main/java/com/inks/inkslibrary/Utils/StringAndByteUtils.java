package com.inks.inkslibrary.Utils;

/**
 * Created by Administrator on 2018/5/21 0021.
 */

public class StringAndByteUtils {


    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return "";
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }


    public static String printHexByte(byte[]data){
        if (data != null && data.length > 0) {
            final StringBuilder stringBuilder = new StringBuilder(data.length);
            for (byte byteChar : data)
                stringBuilder.append(String.format("%02X ", byteChar));
            return stringBuilder.toString();
        }else{
            return "";
        }
    }


    public static byte[] stringToBytes(String string){
        if(string==null || string.equals("")){
            return null;
        }
        string=string.toUpperCase();
        int length =string.length()/2;
        char[]hexChars=string.toCharArray();
        byte[]d=new byte[length];
        for(int i=0;i<length;i++){
            int pos=i*2;
            d[i]=(byte)(charToByte(hexChars[pos])<<4|charToByte(hexChars[pos+1]));
        }
        return  d;
    }
    public static byte charToByte(char c){
        return (byte)"0123456789ABCDEF".indexOf(c);
    }



    //00000000 ~  11111111
    public static boolean judgeFlag(int x,int position){

        int m  = 1 << (position-1);
        if((x&m)==0){
            //Log.e(x+"第"+position+"位","0");
            return false;

        }else{
            // Log.e(x+"第"+position+"位","1");
            return true;
        }

    }


    //00000000 ~  11111111
    public static boolean judgeFlag(String s, int position){
        int x=Integer.valueOf(s,16);
        int m  = 1 << (position-1);
        if((x&m)==0){
            //Log.e(x+"第"+position+"位","0");
            return false;

        }else{
            // Log.e(x+"第"+position+"位","1");
            return true;
        }

    }

    public static String RemoveSpaces(String data){
        if (data != null && !data.equalsIgnoreCase("null")) {
            data = data.replaceAll(" ", "");
            return data;
        }else{
            return "";
        }

    }
}

