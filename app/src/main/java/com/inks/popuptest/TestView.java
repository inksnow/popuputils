package com.inks.popuptest;

import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static com.inks.inkslibrary.Utils.DensityUtils.dp2px;

/**
 * @ProjectName: PopupTest
 * @Package: com.inks.popuptest
 * @ClassName: TextView
 * @Description: java类作用描述
 * @Author: inks
 * @CreateDate: 2022/4/24 17:13
 */
public class TestView extends FrameLayout {
    public TestView(Context context) {
        this(context,null);

    }

    public TestView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
       int  bgMargin = dp2px(getContext(),10);
        RelativeLayout  relativeLayout = new RelativeLayout(getContext());
        relativeLayout.setElevation(dp2px(getContext(),10));

        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
        drawable.setColors(new int[]{0XFF000000, 0XFF000000});

        float[] externalRound = {8, 8, 8, 8, 8, 8, 8, 8};
        ShapeDrawable shapeDrawable = new ShapeDrawable();
        shapeDrawable.getPaint().setColor(Color.BLACK);
        shapeDrawable.getPaint().setAntiAlias(true);
        shapeDrawable.setShape(new RoundRectShape(externalRound,null,null));



        LayoutParams relativeParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        relativeParams.setMargins(bgMargin, bgMargin, bgMargin, bgMargin);
        relativeParams.gravity = Gravity.CENTER;
        relativeLayout.setLayoutParams(relativeParams);
        relativeLayout.setBackground(shapeDrawable);

        addView(relativeLayout);
      relativeLayout.requestLayout();
    }
}
