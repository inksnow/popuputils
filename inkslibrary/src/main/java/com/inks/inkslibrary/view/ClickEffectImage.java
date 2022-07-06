package com.inks.inkslibrary.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.inks.inkslibrary.R;


/**
 * @ProjectName: JavaCameraX
 * @Package: com.ink.javacamerax.view
 * @ClassName: ClickEffectImage
 * @Description: 点击效果
 * @Author: inks
 * @CreateDate: 2022/4/21 13:29
 */
public class ClickEffectImage extends androidx.appcompat.widget.AppCompatImageView {

    private int downColor = 0XFF33c6c0;

    public ClickEffectImage(@NonNull Context context) {
        this(context,null);
    }

    public ClickEffectImage(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ClickEffectImage(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(attrs!=null){
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ClickEffect);
            downColor = typedArray.getColor(R.styleable.ClickEffect_ColorFilter, 0XFF33c6c0);
            typedArray.recycle();
        }
    }

    public int getDownColor() {
        return downColor;
    }

    public void setDownColor(int downColor) {
        this.downColor = downColor;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            setColorFilter(downColor);

        }else if(event.getAction()== MotionEvent.ACTION_UP || event.getAction()== MotionEvent.ACTION_CANCEL){
            setColorFilter(null);


        }


        return super.onTouchEvent(event);
    }
}
