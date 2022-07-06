package com.inks.inkslibrary.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.inks.inkslibrary.R;


/**
 * @ClassName: ClickEffectText
 * @Description: text的点击效果
 * @Author: inks
 * @CreateDate: 2022/4/21 11:43
 */
public class ClickEffectText extends androidx.appcompat.widget.AppCompatTextView {

    private int initColor;
    private int downColor = 0XFF33c6c0;

    public ClickEffectText(@NonNull Context context) {
        this(context, null);
    }

    public ClickEffectText(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClickEffectText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ClickEffect);
            downColor = typedArray.getColor(R.styleable.ClickEffect_ColorFilter, 0XFF33c6c0);
            typedArray.recycle();
        }
        initColor = getCurrentTextColor();
    }

    public int getInitColor() {
        return initColor;
    }

    public void setInitColor(int initColor) {
        this.initColor = initColor;
    }

    public int getDownColor() {
        return downColor;
    }

    public void setDownColor(int downColor) {
        this.downColor = downColor;
    }


    @Override
    public void setTextColor(int color) {
        super.setTextColor(color);
        initColor = getCurrentTextColor();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            setTextColor(downColor);
            for (int i = 0; i < getCompoundDrawables().length; i++) {
                if (getCompoundDrawables()[i] != null)
                    getCompoundDrawables()[i].setColorFilter(downColor, PorterDuff.Mode.SRC_IN);

            }


        } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
            setTextColor(initColor);
            for (int i = 0; i < getCompoundDrawables().length; i++) {
                if (getCompoundDrawables()[i] != null)
                    getCompoundDrawables()[i].setColorFilter(null);

            }
        }


        return super.onTouchEvent(event);
    }

}
