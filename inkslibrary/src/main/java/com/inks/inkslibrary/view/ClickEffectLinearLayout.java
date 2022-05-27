package com.inks.inkslibrary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;


import com.inks.inkslibrary.R;

import java.util.ArrayList;


/**
 * @ClassName: ClickEffectLinearLayout
 * @Description: 点击LinearLayout，使其图片和文字有点击效果
 * @Author: inks
 * @CreateDate: 2022/4/22 15:49
 */
public class ClickEffectLinearLayout extends LinearLayout {
    private ArrayList<View> views = new ArrayList<>();
    private int downColor = 0XFF33c6c0;
    private int initColor;
    private int  hintColor;

    public ClickEffectLinearLayout(Context context) {
        this(context,null);
    }

    public ClickEffectLinearLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ClickEffectLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public ClickEffectLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        if(attrs!=null){
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ClickEffect);
            downColor = typedArray.getColor(R.styleable.ClickEffect_ColorFilter, 0XFF33c6c0);
            typedArray.recycle();
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        initView();
    }

    private void initView() {
        views.clear();
            for (int i = 0; i <getChildCount() ; i++) {
                View view = getChildAt(i);
                views.add(view);
            }
        }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){


            for (int i = 0; i < views.size(); i++) {
                if(views.get(i) instanceof TextView){
                    TextView textView = (TextView) views.get(i);
                    initColor = textView.getCurrentTextColor();
                    hintColor = textView.getCurrentHintTextColor();
                    textView.setTextColor(downColor);
                    textView.setHintTextColor(downColor);
                }else if (views.get(i) instanceof ImageView){
                    ImageView imageView = (ImageView) views.get(i);
                    imageView.setColorFilter(downColor);
                }
            }

        }else if(event.getAction()== MotionEvent.ACTION_UP || event.getAction()== MotionEvent.ACTION_CANCEL){
            for (int i = 0; i < views.size(); i++) {
                if(views.get(i) instanceof TextView){
                    TextView textView = (TextView) views.get(i);
                    textView.setTextColor(initColor);
                    textView.setHintTextColor(hintColor);
                }else if (views.get(i) instanceof ImageView){
                    ImageView imageView = (ImageView) views.get(i);
                    imageView.setColorFilter(null);
                }
            }
        }


        return super.onTouchEvent(event);
    }
}
