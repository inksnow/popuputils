package com.inks.inkslibrary.Utils;

import android.support.v4.view.ViewPager;
import android.view.animation.OvershootInterpolator;

import java.lang.reflect.Field;

/**
 * ViewPager自动轮播时间设置
 * Created by Administrator on 2018/4/9 0009.
 */

public class ViewPagerAutoPlayTimeUtil {

    public static void setViewPagerScrollSpeed(ViewPager viewPager, int speed) {
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            ViewPagerScroller viewPagerScroller = new ViewPagerScroller(viewPager.getContext(), new OvershootInterpolator(0.6F));
            field.set(viewPager, viewPagerScroller);
            viewPagerScroller.setDuration(speed);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
