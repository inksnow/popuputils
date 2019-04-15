package com.inks.inkslibrary.Utils;

import android.util.Log;

/**
 * 事件点击工具类，设置事件点击的响应时间间隔 
 *  zy 
 */  
public class ClickUtil {  
  
    private static long lastClickTime = 0;  
    private static int lastButtonId = -1;  
    private static long DIFF = 1000;    //时间间隔  
  
    /** 
     * 判断两次点击的间隔，如果小于1s，则认为是多次无效点击（任意两个view，固定时长1s） 
     * 
     * @return 
     */  
    public static boolean isFastDoubleClick() {  
        return isFastDoubleClick(-1, DIFF);  
    }  
  
    /** 
     * 判断两次点击的间隔，如果小于diff，则认为是多次无效点击（任意两个view，自定义间隔时长） 
     * 
     * @return 
     */  
    public static boolean isFastDoubleClick(long diff) {  
        return isFastDoubleClick(-1, diff);  
    }  
  
    /** 
     * 判断两次点击的间隔，如果小于1s，则认为是多次无效点击（同一个view，固定时长1s） 
     * 
     * @return 
     */  
    public static boolean isFastDoubleClick(int buttonId) {  
        return isFastDoubleClick(buttonId, DIFF);  
    }  
  
    /** 
     * 判断两次点击的间隔，如果小于diff，则认为是多次无效点击（同一按钮，自定义间隔时长） 
     * 
     * @param diff 
     * @return 
     */  
    public static boolean isFastDoubleClick(int buttonId, long diff) {  
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;  
        if (lastButtonId == buttonId && lastClickTime > 0 && timeD < diff) {  
            Log.d("isFastDoubleClick", "短时间内view被多次点击");
            return true;  
        }  
        lastClickTime = time;  
        lastButtonId = buttonId;  
        return false;  
    }  
} 