package com.inks.inkslibrary.Popup;

import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.PopupWindow;


public class PromptSettings {

    //不显示或者显示图片还是ProgressBar
    public static final int MODE_SHOW_IMAGE = 0x2;
    public static final int MODE_SHOW_PRO = 0x3;
    //宽
    private final int width;
    //高
    private final int height;
    //位置
    private final int location;

    private final boolean focusable;
    private final boolean outsideTouchable;
    private final boolean touchable;
    private final int inputMethodMode;
    private final int softInputMode;
    private final PopupWindow.OnDismissListener dismissListener;
    private final PopupPrompt.onClickListener clickListener;
    private final float[] radius;
    private final int[] bgColour;
    private final int proColour;
    private final  float textSize;
    private final int textColour;
    private final int buttonColour;
    private final Drawable image;
    private final Animation imageAnim;
    private final String text;
    private final String buttonText;
    private final boolean showText;
    private final boolean showButton;
    private final boolean showImage;
    private final int imageWidth;
    private final int[] imagePaddings;
    private final int[] buttonPaddings;
    private final int[] textPaddings;
    //显示图片还是ProgressBar
    private final int showMode;
    //时间
    private final int duration;
    //背景透明度
    private final float bgAlpha;
    //text左  右  中
    private final int textGravity;
    //popup动画
    private final int popupAnim;

    private final int gravityX;
    private final int gravityY;

    public float getTextSize() {
        return textSize;
    }

    public float[] getRadius() {
        return radius;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getLocation() {
        return location;
    }

    public boolean isFocusable() {
        return focusable;
    }

    public boolean isTouchable() {
        return touchable;
    }

    public boolean isOutsideTouchable() {
        return outsideTouchable;
    }

    public int getInputMethodMode() {
        return inputMethodMode;
    }

    public int getSoftInputMode() {
        return softInputMode;
    }

    public PopupWindow.OnDismissListener getDismissListener() {
        return dismissListener;
    }

    public PopupPrompt.onClickListener getClickListener() {
        return clickListener;
    }

    public int[] getBgColour() {
        return bgColour;
    }

    public int getProColour() {
        return proColour;
    }

    public int getTextColour() {
        return textColour;
    }

    public int getButtonColour() {
        return buttonColour;
    }

    public Drawable getImage() {
        return image;
    }

    public Animation getImageAnim() {
        return imageAnim;
    }

    public String getText() {
        return text;
    }

    public String getButtonText() {
        return buttonText;
    }

    public boolean isShowText() {
        return showText;
    }

    public boolean isShowButton() {
        return showButton;
    }

    public boolean isShowImage() {
        return showImage;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int[] getImagePaddings() {
        return imagePaddings;
    }

    public int[] getButtonPaddings() {
        return buttonPaddings;
    }

    public int[] getTextPaddings() {
        return textPaddings;
    }

    public int getShowMode() {
        return showMode;
    }

    public int getDuration() {
        return duration;
    }

    public float getBgAlpha() {
        return bgAlpha;
    }

    public int getTextGravity() {
        return textGravity;
    }

    public int getPopupAnim() {
        return popupAnim;
    }

    public int getGravityX() {
        return gravityX;
    }

    public int getGravityY() {
        return gravityY;
    }

    public static class Builder {
        //宽
        private int width = WindowManager.LayoutParams.MATCH_PARENT;
        //高
        private int height = 160;
        //位置
        private int location = Gravity.TOP;
        private boolean focusable = false;
        private boolean outsideTouchable = false;
        private boolean touchable = true;
        private int inputMethodMode = PopupWindow.INPUT_METHOD_NEEDED;
        private int softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;
        //弹窗miss监听
        private PopupWindow.OnDismissListener dismissListener = null;
        //右侧按键监听
        private PopupPrompt.onClickListener clickListener = null;
        //圆角 （必须是8个值）
        private  float[] radius={0,0,0,0,0,0,0,0};
        //背景色（渐变色数组，必须大于等于2个值，如果是单色就用2个一样的颜色）
        private int[] bgColour = {0XFF03a9f4,0XFF03a9f4};
        //ProgressBar 颜色
        private int proColour = 0XFFFFFFFF;
        //文字颜色
        private int textColour = 0XFFFFFFFF;
        //按钮文字颜色
        private int buttonColour = 0XFFFFFFFF;
        //文字和按钮字体大小
        private   float textSize = 18;
        //显示左边的图片
        private Drawable image = null;
        //显示左边的图片动画
        private Animation imageAnim = null;
        //提示文字
        private String text = "这是一个提示框!";
        //按钮文字
        private String buttonText = "好的";
        //是否显示文字
        private boolean showText = true;
        //是否显示按钮
        private boolean showButton = true;
        //是否显示左边Pro 或 图片
        private boolean showImage = false;
        //图片宽度
        private int imageWidth = 120;
        //Padding
        private int[] imagePaddings = {20, 20, 20, 20};
        private int[] buttonPaddings = {20, 20, 20, 20};
        private int[] textPaddings = {0, 0, 0, 0};
        //显示图片还是ProgressBar
        private int showMode = MODE_SHOW_PRO;
        //自动时间，小于等于0 不自动消失
        private int duration = 3000;
        //背景透明度
        private float bgAlpha = 1.0f;
        //text左  右  中
        private int textGravity = Gravity.CENTER_VERTICAL;
        //弹窗动画，默认style.popup_fade_in_out 写好的有 popup_top_down popup_bottom_top popup_left_right
        //可以传入自己的动画
        private int popupAnim = 0;
        //X  Y 坐标的偏移值，根据此值可以自定义位置
        private int gravityX = 0;
        private int gravityY = 0;

        public Builder() {
        }

        public Builder width(int width) {
            this.width = width;
            return this;
        }

        public Builder height(int height) {
            this.height = height;
            return this;
        }

        public Builder location(int location) {
            this.location = location;
            return this;
        }

        public Builder focusable(boolean focusable) {
            this.focusable = focusable;
            return this;
        }

        public Builder outsideTouchable(boolean outsideTouchable) {
            this.outsideTouchable = outsideTouchable;
            return this;
        }

        public Builder inputMethodMode(int inputMethodMode) {
            this.inputMethodMode = inputMethodMode;
            return this;
        }

        public Builder softInputMode(int softInputMode) {
            this.softInputMode = softInputMode;
            return this;
        }

        public Builder dismissListener(PopupWindow.OnDismissListener dismissListener) {
            this.dismissListener = dismissListener;
            return this;
        }

        public Builder clickListener(PopupPrompt.onClickListener clickListener) {
            this.clickListener = clickListener;
            return this;
        }

        public Builder radius(float[] radius) {
            this.radius = radius;
            return this;
        }

        public Builder textSize(float textSize) {
            this.textSize = textSize;
            return this;
        }

        public Builder bgColour(int[] bgColour) {
            this.bgColour = bgColour;
            return this;
        }

        public Builder proColour(int proColour) {
            this.proColour = proColour;
            return this;
        }

        public Builder textColour(int textColour) {
            this.textColour = textColour;
            return this;
        }

        public Builder buttonColour(int buttonColour) {
            this.buttonColour = buttonColour;
            return this;
        }

        public Builder image(Drawable image) {
            this.image = image;
            return this;
        }

        public Builder imageAnim(Animation imageAnim) {
            this.imageAnim = imageAnim;
            return this;
        }

        public Builder text(String text) {
            this.text = text;
            return this;
        }

        public Builder buttonText(String buttonText) {
            this.buttonText = buttonText;
            return this;
        }

        public Builder showText(boolean showText) {
            this.showText = showText;
            return this;
        }

        public Builder showButton(boolean showButton) {
            this.showButton = showButton;
            return this;
        }

        public Builder showImage(boolean showImage) {
            this.showImage = showImage;
            return this;
        }

        public Builder imageWidth(int imageWidth) {
            this.imageWidth = imageWidth;
            return this;
        }

        public Builder imagePaddings(int[] imagePaddings) {
            this.imagePaddings = imagePaddings;
            return this;
        }

        public Builder buttonPaddings(int[] buttonPaddings) {
            this.buttonPaddings = buttonPaddings;
            return this;
        }

        public Builder textPaddings(int[] textPaddings) {
            this.textPaddings = textPaddings;
            return this;
        }

        public Builder showMode(int showMode) {
            this.showMode = showMode;
            return this;
        }

        public Builder duration(int duration) {
            this.duration = duration;
            return this;
        }

        public Builder bgAlpha(float bgAlpha) {
            this.bgAlpha = bgAlpha;
            return this;
        }

        public Builder textGravity(int textGravity) {
            this.textGravity = textGravity;
            return this;
        }

        public Builder popupAnim(int popupAnim) {
            this.popupAnim = popupAnim;
            return this;
        }

        public Builder touchable(boolean touchable) {
            this.touchable = touchable;
            return this;
        }

        public Builder gravityX(int gravityX) {
            this.gravityX = gravityX;
            return this;
        }

        public Builder gravityY(int gravityY) {
            this.gravityY = gravityY;
            return this;
        }

        //返回外部对象
        public PromptSettings build() {
            return new PromptSettings(this);
        }

    }

    private PromptSettings(Builder builder) {
        width = builder.width;
        height = builder.height;
        location = builder.location;
        focusable = builder.focusable;
        touchable = builder.touchable;
        outsideTouchable = builder.outsideTouchable;
        inputMethodMode = builder.inputMethodMode;
        softInputMode = builder.softInputMode;
        dismissListener = builder.dismissListener;
        clickListener = builder.clickListener;
        radius= builder.radius;
        bgColour = builder.bgColour;
        proColour = builder.proColour;
        textColour = builder.textColour;
        buttonColour = builder.buttonColour;
        image = builder.image;
        imageAnim = builder.imageAnim;
        text = builder.text;
        buttonText = builder.buttonText;
        showText = builder.showText;
        showButton = builder.showButton;
        showImage = builder.showImage;
        imageWidth = builder.imageWidth;
        imagePaddings = builder.imagePaddings;
        buttonPaddings = builder.buttonPaddings;
        textPaddings = builder.textPaddings;
        showMode = builder.showMode;
        duration = builder.duration;
        bgAlpha = builder.bgAlpha;
        textGravity = builder.textGravity;
        popupAnim = builder.popupAnim;
        gravityX = builder.gravityX;
        gravityY = builder.gravityY;
        textSize = builder.textSize;
    }


}


