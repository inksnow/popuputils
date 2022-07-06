package com.inks.inkslibrary.view;

import android.content.Context;
import android.graphics.ColorMatrixColorFilter;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @ProjectName: JavaCameraX
 * @Package: com.ink.javacamerax.view
 * @ClassName: ClickEffectImageColorMatrix
 * @Description: 点击变暗效果
 * @Author: inks
 * @CreateDate: 2022/4/21 13:29
 */
public class ClickEffectImageColorMatrix extends androidx.appcompat.widget.AppCompatImageView {

    private int downColor = 0XFF777777;
    //MATRIX中的值是0.5使得R,G,B通道的值都减半
    private final static float[] MATRIX = new float[] {
            0.5f, 0, 0, 0, 0,
            0, 0.5f, 0, 0, 0,
            0, 0, 0.5f, 0, 0,
            0, 0, 0, 1, 0 };

    public ClickEffectImageColorMatrix(@NonNull Context context) {
        super(context);
    }

    public ClickEffectImageColorMatrix(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ClickEffectImageColorMatrix(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public int getDownColor() {
        return downColor;
    }

    public void setDownColor(int downColor) {
        this.downColor = downColor;
    }

    public static float[] getMATRIX() {
        return MATRIX;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN){
            ColorMatrixColorFilter filter = new ColorMatrixColorFilter(MATRIX);
            //直接设置颜色，如果图片不是透明的话，会直接使图片全部填充为新颜色
            //setColorFilter(downColor);
            setColorFilter(filter);

            //黑白效果
//            ColorMatrix colorMatrix = new ColorMatrix();
//            colorMatrix.setSaturation(0);//将饱和度设置为了0，变为黑白图
//            setColorFilter(new ColorMatrixColorFilter(colorMatrix));


        }else if(event.getAction()== MotionEvent.ACTION_UP  || event.getAction()== MotionEvent.ACTION_CANCEL){
            setColorFilter(null);


        }


        return super.onTouchEvent(event);
    }
}
