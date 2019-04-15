package com.inks.inkslibrary.Utils;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

public class ViewPagerScroller extends Scroller {
    private int mDuration;  
  
    public ViewPagerScroller(Context context) {
        super(context);  
    }  
  
    public ViewPagerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);  
    }  
  
    public void setDuration(int mDuration) {  
        this.mDuration = mDuration;  
    }  
  
    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {  
        super.startScroll(startX, startY, dx, dy, this.mDuration);  
    }  
  
    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {  
        super.startScroll(startX, startY, dx, dy, this.mDuration);  
    }
}