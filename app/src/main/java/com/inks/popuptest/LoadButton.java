package com.inks.popuptest;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inks.inkslibrary.Utils.L;

/**
 * @ProjectName: PopupTest
 * @Package: com.inks.popuptest
 * @ClassName: LoadButton
 * @Description: LoadButton
 * @Author: inks
 * @CreateDate: 2022/4/24 10:32
 */
public class LoadButton extends FrameLayout {

    private RelativeLayout relativeLayout;
    private TextView textView;
    private ProgressBar progressBar;
    private GradientDrawable drawablePressed;
    private GradientDrawable drawableUnPressed;
    private StateListDrawable stateListDrawable;
    //使用GradientDrawable设置渐变色后，设置Elevation不生效，要点击一下之后才生效，改用ShapeDrawable，渐变色可以用LinearGradient ，但需要动态计算宽度，暂时不用
    private GradientDrawable drawable;
    private ShapeDrawable shapeDrawable;
    private ColorStateList colorStateList;
    private RippleDrawable rippleDrawable;
    //按钮颜色
    private int color = 0XFFD81B60;
    //按钮渐变开始颜色，结束颜色
    private int startColor = 0XFFD81B60, endColor = 0XFFD81B60;
    //点击效果颜色
    private int rippleColor = 0X6600574B;
    //加载的bar颜色
    private int barColor = 0XFFFFFFFF;
    //按钮圆角半径
    private float radius = 5;
    //按钮文本
    private String text = "";
    //按钮文本颜色
    private int textColor = 0XFFFFFFFF;
    //按钮文字大小
    private float textSize = 14;
    //bar
    private float barWidth = 0;
    //动画时间
    private int duration = 200;
    //动画差值器
    private Interpolator interpolator;
    //文字间隔
    private float letterSpacing = 0;
    private int bgMargin = 0;
    private int bgElevation = 0;
    //加载延时
    private int loadDelayed = 1000;


    //初始化的宽高
    private int initWidth, initHeight;

    private boolean isStatic = true;
    private boolean isLoad = false;

    private ValueAnimator anim;
    private Handler handler;

    public interface OnLoadListener {
        public void onLoad();
    }

    private OnLoadListener mOnLoadListener;

    public LoadButton(@NonNull Context context) {
        this(context, null);
    }

    public LoadButton(@NonNull Context context, @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public LoadButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        initWidth = MeasureSpec.getSize(widthMeasureSpec);//宽
        initHeight = MeasureSpec.getSize(heightMeasureSpec);//高
        initHeight = initHeight - (getPaddingTop() + getPaddingBottom())- bgMargin*2;
        if (barWidth == 0) {
            barWidth = (float) (initHeight * 0.7);
        }

        L.e("onMeasure");
    }

    public void setOnLoadListener(OnLoadListener mOnLoadListener) {
        this.mOnLoadListener = mOnLoadListener;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        L.e("onLayout" + changed);

        invalidate();
    }

    private void init(AttributeSet attrs) {
        interpolator = new AccelerateInterpolator();
        handler = new Handler();
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.LoadButton);
            color = typedArray.getColor(R.styleable.LoadButton_color, 0XFFD81B60);
            startColor = typedArray.getColor(R.styleable.LoadButton_start_color, color);
            endColor = typedArray.getColor(R.styleable.LoadButton_end_color, color);
            rippleColor = typedArray.getColor(R.styleable.LoadButton_ripple_color, 0X6600574B);
            barColor = typedArray.getColor(R.styleable.LoadButton_bar_color, 0XFFFFFFFF);
            textColor = typedArray.getColor(R.styleable.LoadButton_text_color, 0XFFFFFFFF);
            radius = typedArray.getDimension(R.styleable.LoadButton_radius, 0);
            textSize = typedArray.getDimension(R.styleable.LoadButton_text_size, 14);
            barWidth = typedArray.getDimension(R.styleable.LoadButton_bar_width, 0);
            text = typedArray.getString(R.styleable.LoadButton_text);
            duration = typedArray.getInt(R.styleable.LoadButton_duration, 200);
            letterSpacing =  typedArray.getFloat(R.styleable.LoadButton_letter_spacing, 0);
            bgMargin = (int) typedArray.getDimension(R.styleable.LoadButton_bg_margin, 0);
            bgElevation = (int) typedArray.getDimension(R.styleable.LoadButton_bg_elevation, 0);
            loadDelayed =  typedArray.getInt(R.styleable.LoadButton_load_delayed, 1000);
            typedArray.recycle();
        }


        relativeLayout = new RelativeLayout(getContext());
        relativeLayout.setElevation(bgElevation);

        drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
        drawable.setColors(new int[]{startColor, endColor});
        drawable.setCornerRadius(radius);



        shapeDrawable = new ShapeDrawable();
        shapeDrawable.getPaint().setColor(color);
        shapeDrawable.getPaint().setAntiAlias(true);
        float[] externalRound = {radius, radius, radius, radius, radius, radius, radius, radius};
        shapeDrawable.setShape(new RoundRectShape(externalRound,null,null));


        ColorStateList colorStateList =
                new ColorStateList(new int[][]
                        { {android.R.attr.state_enabled}},
                        new int[]{ rippleColor});

        RippleDrawable rippleDrawable = new RippleDrawable(colorStateList, shapeDrawable, null);


        LayoutParams relativeParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        relativeParams.setMargins(bgMargin, bgMargin, bgMargin, bgMargin);
        relativeParams.gravity = Gravity.CENTER;
        relativeLayout.setLayoutParams(relativeParams);
        relativeLayout.setBackground(rippleDrawable);
        relativeLayout.setElevation(bgElevation);
        addView(relativeLayout);



        RelativeLayout.LayoutParams textParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        textView = new TextView(getContext());
        textView.setBackground(null);
        textView.setText(text);
        textView.setLetterSpacing(letterSpacing);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        textView.setTextColor(textColor);
        relativeLayout.addView(textView, textParams);


        progressBar = new ProgressBar(getContext());
        progressBar.setVisibility(GONE);
        ColorStateList barStateList =
                new ColorStateList(new int[][]
                        {{android.R.attr.state_pressed}, {-android.R.attr.state_pressed}},
                        new int[]{barColor, barColor});
        progressBar.setIndeterminateTintList(barStateList);
        relativeLayout.addView(progressBar);



//         drawablePressed = new GradientDrawable();
//        drawablePressed.setShape(GradientDrawable.RECTANGLE);
//        drawablePressed.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
//        drawablePressed.setColors(new int[]{startColor, endColor});
//        drawablePressed.setCornerRadius(radius);
//         drawableUnPressed = new GradientDrawable();
//        //形状（矩形）
//        drawableUnPressed.setShape(GradientDrawable.RECTANGLE);
//        //渐变方向（左到右）
//        drawableUnPressed.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
//        //颜色
//        drawableUnPressed.setColors(new int[]{0XFFff0000, 0XFF0000ff});
//         stateListDrawable = new StateListDrawable();
//        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, drawablePressed);
//        stateListDrawable.addState(new int[]{-android.R.attr.state_pressed}, drawableUnPressed);
//        StateSet.WILD_CARD表示非所有状态，也就是正常状态下的drawable
//        stateListDrawable，StateSet.WILD_CARD的drawable必须要放在最后
//        stateListDrawable.addState(StateSet.WILD_CARD, drawable);


    }

    public void start(OnLoadListener loadListener) {
        this.mOnLoadListener = loadListener;
        start();
    }


    public void start() {
        setEnabled(false);
        if (isStatic) {
            isStatic = false;
            anim = ValueAnimator.ofInt(initWidth, initHeight);
            anim.setDuration(duration);
            anim.setInterpolator(interpolator);
            anim.start();
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    LayoutParams params = (LayoutParams) relativeLayout.getLayoutParams();
                    params.width = (Integer) valueAnimator.getAnimatedValue();
                    relativeLayout.setLayoutParams(params);

                    if (initHeight * 0.5 - radius > 0) {
                        float r = (float) (radius + (initHeight * 0.5 - radius) * ((initWidth + initHeight - (Integer) valueAnimator.getAnimatedValue()) / (float) initWidth));
                        float[] externalRound = {r, r, r, r, r, r, r, r};
                        shapeDrawable.setShape(new RoundRectShape(externalRound,null,null));

                        //drawable.setCornerRadius(r);
                    }

                    textView.setTextColor(changeAlpha(textColor, (int) (255 * (((Integer) valueAnimator.getAnimatedValue()) / (float) initWidth))));

                    if ((Integer) valueAnimator.getAnimatedValue() == initHeight) {
                        isLoad = true;
                        textView.setTextColor(changeAlpha(textColor, 0));
                        //drawable.setCornerRadius((float) (initHeight * 0.5));

                        float[] externalRound = {(float) (initHeight * 0.5), (float) (initHeight * 0.5), (float) (initHeight * 0.5), (float) (initHeight * 0.5), (float) (initHeight * 0.5), (float) (initHeight * 0.5), (float) (initHeight * 0.5), (float) (initHeight * 0.5)};
                        shapeDrawable.setShape(new RoundRectShape(externalRound,null,null));
                        RelativeLayout.LayoutParams barParams = (RelativeLayout.LayoutParams) progressBar.getLayoutParams();
                        barParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                        barParams.width = (int) barWidth;
                        barParams.height = (int) barWidth;
                        progressBar.setLayoutParams(barParams);
                        progressBar.setVisibility(VISIBLE);
                        if(loadDelayed>0){

                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    isLoad = true;
                                    if (mOnLoadListener != null) {
                                        mOnLoadListener.onLoad();
                                    }
                                }
                            },loadDelayed);
                        }


                    }

                }
            });
        }

    }

    //复原
    public void re() {
        handler.removeCallbacksAndMessages(null);
        anim.cancel();
        progressBar.setVisibility(GONE);
        textView.setTextColor(textColor);
        drawable.setCornerRadius(radius);
        LayoutParams params = (LayoutParams) relativeLayout.getLayoutParams();
        params.width = initWidth;
        relativeLayout.setLayoutParams(params);
        isStatic = true;
        setEnabled(true);
    }


    //带动画复原
    public void reAnim(){
        anim = ValueAnimator.ofInt(initHeight, initWidth);
        anim.setDuration(duration);
        anim.setInterpolator(interpolator);
        anim.start();
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                LayoutParams params = (LayoutParams) relativeLayout.getLayoutParams();
                params.width = (Integer) valueAnimator.getAnimatedValue();
                relativeLayout.setLayoutParams(params);

                if (initHeight * 0.5 - radius > 0) {
                    float r = (float) (radius + (initHeight * 0.5 - radius) * ((initWidth + initHeight - (Integer) valueAnimator.getAnimatedValue()) / (float) initWidth));
                    float[] externalRound = {r, r, r, r, r, r, r, r};
                    shapeDrawable.setShape(new RoundRectShape(externalRound,null,null));
                    //drawable.setCornerRadius(r);
                }
                textView.setTextColor(changeAlpha(textColor, (int) (255 * (((Integer) valueAnimator.getAnimatedValue()) / (float) initWidth))));
                if ((Integer) valueAnimator.getAnimatedValue() == initWidth) {
                    re();
                }

            }
        });
    }

    public void removeHandler(){
        handler.removeCallbacksAndMessages(null);
    }

    public static int changeAlpha(int color, int alpha) {

        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        int oldAlpha = Color.alpha(color);
        if (alpha > oldAlpha) {
            alpha = oldAlpha;
        }
        return Color.argb(alpha, red, green, blue);
    }


    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getStartColor() {
        return startColor;
    }

    public void setStartColor(int startColor) {
        this.startColor = startColor;
    }

    public int getEndColor() {
        return endColor;
    }

    public void setEndColor(int endColor) {
        this.endColor = endColor;
    }

    public int getRippleColor() {
        return rippleColor;
    }

    public void setRippleColor(int rippleColor) {
        this.rippleColor = rippleColor;
    }

    public int getBarColor() {
        return barColor;
    }

    public void setBarColor(int barColor) {
        this.barColor = barColor;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public float getBarWidth() {
        return barWidth;
    }

    public void setBarWidth(float barWidth) {
        this.barWidth = barWidth;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Interpolator getInterpolator() {
        return interpolator;
    }

    public void setInterpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
    }

    public float getLetterSpacing() {
        return letterSpacing;
    }

    public void setLetterSpacing(float letterSpacing) {
        this.letterSpacing = letterSpacing;
    }

    public int getBgMargin() {
        return bgMargin;
    }

    public void setBgMargin(int bgMargin) {
        this.bgMargin = bgMargin;
    }

    public int getBgElevation() {
        return bgElevation;
    }

    public void setBgElevation(int bgElevation) {
        this.bgElevation = bgElevation;
        relativeLayout.setElevation(bgElevation);
    }

    public int getLoadDelayed() {
        return loadDelayed;
    }

    public void setLoadDelayed(int loadDelayed) {
        this.loadDelayed = loadDelayed;
    }

    public boolean isStatic() {
        return isStatic;
    }
}
