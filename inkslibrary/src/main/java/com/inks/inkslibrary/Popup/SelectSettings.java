package com.inks.inkslibrary.Popup;

import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import java.util.List;

public class SelectSettings {

    private final boolean focusable;
    private final boolean outsideTouchable;
    private final boolean touchable;
    private final int inputMethodMode;
    private final int softInputMode;

    private final PopupSelect.onClickListener clickListener;
    private final List<SelectListDataBean> selectListDataBean;
    private final boolean multipleSelection;
    private final int[] popupBg;
    private final float[] popupRadius;
    private final int[] titleIconPaddings;
    private final int[] titleTextPaddings;
    private final int[] listPaddings;
    private final int[] buttonPaddings;

    private final int popupWidth;
    private final int popupHeight;
    private final int titleIconWidth;
    private final int titleIconHeight;

    private final boolean showTitle;
    private final int[] titleBg;

    private final boolean showTitleIcon;
    private final Drawable titleIcon;

    private final boolean showTitleText;
    private final String titleTextStr;
    private final int titleTextColor;
    private final int titleTextSize;
    private final int titleTextGravity;

    private final boolean scrollBarEnabled;
    private final int scrollBarFadeDuration;
    private final int scrollBarSize;
    private final int scrollBarStyle;


    private final String buttonTextStr1;
    private final String buttonTextStr2;
    private final int buttonTextSize;
    private final int buttonTextColor1;
    private final int buttonTextColor2;
    private final boolean showButton1;
    private final boolean showButton2;
    private final int titleDividingColor;
    private final int buttonDividingColor;

    private final int listHeight;

    private final int listSelectImageHeight;
    private final int listSelectImageWidth;
    private final int listIconHeight;
    private final int listIconWidth;
    private final int listTextSize;

    private final int[] listSelectImagePaddings;
    private final int[] listIconPaddings;
    private final int[] listTextPaddings;

    private final Drawable listSelectImageOn;
    private final Drawable listSelectImageOff;
    private final Drawable listIcon;

    private final int listTextColor;
    private final int listTextSelectColor;
    private final int listTextGravity;

    private final boolean showListSelectImage;
    private final boolean showListIcon;

    private final Drawable listDivider;
    private final int listDividerHeight;

    //背景透明度
    private final float bgAlpha;

    public float getBgAlpha() {
        return bgAlpha;
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

    public Drawable getListDivider() {
        return listDivider;
    }

    public int getListDividerHeight() {
        return listDividerHeight;
    }

    public PopupSelect.onClickListener getClickListener() {
        return clickListener;
    }

    public List<SelectListDataBean> getSelectListDataBean() {
        return selectListDataBean;
    }

    public boolean isMultipleSelection() {
        return multipleSelection;
    }

    public int[] getPopupBg() {
        return popupBg;
    }

    public float[] getPopupRadius() {
        return popupRadius;
    }

    public int[] getTitleIconPaddings() {
        return titleIconPaddings;
    }

    public int[] getTitleTextPaddings() {
        return titleTextPaddings;
    }

    public int[] getListPaddings() {
        return listPaddings;
    }

    public int[] getButtonPaddings() {
        return buttonPaddings;
    }

    public int getPopupWidth() {
        return popupWidth;
    }

    public int getPopupHeight() {
        return popupHeight;
    }

    public int getTitleIconWidth() {
        return titleIconWidth;
    }

    public int getTitleIconHeight() {
        return titleIconHeight;
    }

    public boolean isShowTitle() {
        return showTitle;
    }

    public int[] getTitleBg() {
        return titleBg;
    }

    public boolean isShowTitleIcon() {
        return showTitleIcon;
    }

    public Drawable getTitleIcon() {
        return titleIcon;
    }

    public boolean isShowTitleText() {
        return showTitleText;
    }

    public String getTitleTextStr() {
        return titleTextStr;
    }

    public int getTitleTextColor() {
        return titleTextColor;
    }

    public int getTitleTextSize() {
        return titleTextSize;
    }

    public int getTitleTextGravity() {
        return titleTextGravity;
    }

    public boolean isScrollBarEnabled() {
        return scrollBarEnabled;
    }

    public int getScrollBarFadeDuration() {
        return scrollBarFadeDuration;
    }

    public int getScrollBarSize() {
        return scrollBarSize;
    }

    public int getScrollBarStyle() {
        return scrollBarStyle;
    }

    public String getButtonTextStr1() {
        return buttonTextStr1;
    }

    public String getButtonTextStr2() {
        return buttonTextStr2;
    }

    public int getButtonTextSize() {
        return buttonTextSize;
    }

    public int getButtonTextColor1() {
        return buttonTextColor1;
    }

    public int getButtonTextColor2() {
        return buttonTextColor2;
    }

    public boolean isShowButton1() {
        return showButton1;
    }

    public boolean isShowButton2() {
        return showButton2;
    }

    public int getTitleDividingColor() {
        return titleDividingColor;
    }

    public int getButtonDividingColor() {
        return buttonDividingColor;
    }

    public int getListHeight() {
        return listHeight;
    }

    public int getListSelectImageHeight() {
        return listSelectImageHeight;
    }

    public int getListSelectImageWidth() {
        return listSelectImageWidth;
    }

    public int getListIconHeight() {
        return listIconHeight;
    }

    public int getListIconWidth() {
        return listIconWidth;
    }

    public int getListTextSize() {
        return listTextSize;
    }

    public int[] getListSelectImagePaddings() {
        return listSelectImagePaddings;
    }

    public int[] getListIconPaddings() {
        return listIconPaddings;
    }

    public int[] getListTextPaddings() {
        return listTextPaddings;
    }

    public Drawable getListSelectImageOn() {
        return listSelectImageOn;
    }

    public Drawable getListSelectImageOff() {
        return listSelectImageOff;
    }

    public Drawable getListIcon() {
        return listIcon;
    }


    public int getListTextColor() {
        return listTextColor;
    }

    public int getListTextSelectColor() {
        return listTextSelectColor;
    }

    public int getListTextGravity() {
        return listTextGravity;
    }

    public boolean isShowListSelectImage() {
        return showListSelectImage;
    }

    public boolean isShowListIcon() {
        return showListIcon;
    }


    public static class Builder {

        //确认按钮点击后的回调，或单选没设置确认按钮点击list选项的回调
        private  PopupSelect.onClickListener clickListener = null;
        //List 数据
        private List<SelectListDataBean> selectListDataBean = null;
        //是否可以多选
        private boolean multipleSelection = true;

        //PopupWindow的一些设置，默认点击外边PopupWindow消失
        //如果想设置点击外边不消失设置focusable = false ,outsideTouchable = false
        //在activity中拦截点击事件
        //     @Override
        //    public boolean dispatchTouchEvent(MotionEvent event){
        //        if (popupSelect.getpWindow()!= null && popupSelect.getpWindow().isShowing()){
        //            return false;
        //        }else{
        //            return super.dispatchTouchEvent(event);
        //        }
        //    }
        private boolean focusable = true;
        private boolean outsideTouchable = false;
        private boolean touchable = true;
        private int inputMethodMode = PopupWindow.INPUT_METHOD_NEEDED;
        private int softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;
        //背景色，必须大于等于2个值，设置不同的多个值为渐变效果
        private int[] popupBg ={0XFFFFFFFF,0XFFFFFFFF};
        // 设置图片四个角圆形半径：1、2两个参数表示左上角，3、4表示右上角，5、6表示右下角，7、8表示左下角
        private float[] popupRadius ={20,20,20,20,20,20,20,20};
        //Paddings 设置此值可以调节布局位置
        private int[] titleIconPaddings = {60,10,10,10};
        private int[] titleTextPaddings={80,20,20,20};
        private int[] listPaddings={20,10,20,10};
        private int[] buttonPaddings={20,20,20,20};
        //宽
        private int popupWidth =700;
        //高
        private int popupHeight=460;
        //标题图标
        private int titleIconWidth=140;
        private int titleIconHeight=100;
        //是否显示标题图标和文字布局
        private boolean showTitle = true;
        //标题布局背景
        private int[] titleBg ={0X00000000,0X00000000};
        //是否显示标题图标
        private boolean showTitleIcon = true;
        //标题图标资源
        private Drawable titleIcon = null;
        //是否显示标题文字
        private boolean showTitleText = true;
        //标题文字内容
        private String titleTextStr = "请设置标题";
        //标题文字颜色
        private int titleTextColor = 0XFF333333;
        //标题文字大小
        private int titleTextSize = 18;
        //标题文字位置
        private int titleTextGravity = Gravity.CENTER_VERTICAL;
        //是否显示右边滚动条
        private boolean scrollBarEnabled = true;
        //滚动条自动消失时间
        private int scrollBarFadeDuration = 5000;
        //滚动条大小
        private int scrollBarSize = 5;
        //滚动条style
        private int scrollBarStyle =View.SCROLLBARS_INSIDE_INSET ;
        //button1文字
        private String buttonTextStr1 = "取消";
        //button2文字
        private String buttonTextStr2 = "确定";
        //按钮字体大小
        private int buttonTextSize = 18;
        //按钮文字颜色
        private int buttonTextColor1=0XFF03a9f4;
        private int buttonTextColor2=0XFF03a9f4;
        //是否显示该按钮
        private boolean showButton1 = true;
        private boolean showButton2 = true;
        //标题下分割线颜色
        private int titleDividingColor = 0X33AAAAAA;
        //按钮分割线颜色
        private int buttonDividingColor= 0X33AAAAAA;
        //每一条list高度
        private int listHeight = 100;
        //list选框高宽
        private int listSelectImageHeight = 80;
        private int listSelectImageWidth = 110;
        //list 图标高宽
        private int listIconHeight = 80;
        private int listIconWidth = 80;
        //list文字大小
        private int listTextSize = 16;
        //paddings
        private int[] listSelectImagePaddings={60,10,10,10};
        private int[] listIconPaddings={20,10,10,10};
        private int[] listTextPaddings={10,10,10,10};
        //选中选框
        private Drawable listSelectImageOn = null;
        //未选中选框
        private Drawable listSelectImageOff = null;
        private Drawable listIcon = null;
        //list文字颜色
        private int listTextColor = 0XFF333333;
        //list选中后文字颜色
        private int listTextSelectColor = 0XFF03a9f4;
        //list文字位置
        private int listTextGravity = Gravity.CENTER_VERTICAL;
        //是否显示选框
        private boolean showListSelectImage = true;
        //是否显示图标
        private boolean showListIcon = true;
        //list分割线
        private  Drawable listDivider = null;
        //list分割线高度
        private  int listDividerHeight = 1;
        //背景透明度
        private float bgAlpha = 0.6f;

        public Builder() {
        }

        public Builder bgAlpha(float bgAlpha) {
            this.bgAlpha = bgAlpha;
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

            public Builder touchable(boolean touchable) {
                this.touchable = touchable;
                return this;
            }

            public Builder listDivider(Drawable listDivider) {
            this.listDivider = listDivider;
            return this;
        }

        public Builder listDividerHeight(int listDividerHeight) {
            this.listDividerHeight = listDividerHeight;
            return this;
        }

        public Builder selectListDataBean(List<SelectListDataBean> selectListDataBean) {
            this.selectListDataBean = selectListDataBean;
            return this;
        }

        public Builder multipleSelection(boolean multipleSelection) {
            this.multipleSelection = multipleSelection;
            return this;
        }

        public Builder clickListener(PopupSelect.onClickListener clickListener) {
            this.clickListener = clickListener;
            return this;
        }

        public Builder popupBg(int[] popupBg) {
            this.popupBg = popupBg;
            return this;

        }

        public Builder popupRadius(float[] popupRadius) {
            this.popupRadius = popupRadius;
            return this;
        }

        public Builder titleIconPaddings(int[] titleIconPaddings) {
            this.titleIconPaddings = titleIconPaddings;
            return this;
        }

        public Builder titleTextPaddings(int[] titleTextPaddings) {
            this.titleTextPaddings = titleTextPaddings;
            return this;
        }

        public Builder listPaddings(int[] listPaddings) {
            this.listPaddings = listPaddings;
            return this;
        }

        public Builder buttonPaddings(int[] buttonPaddings) {
            this.buttonPaddings = buttonPaddings;
            return this;
        }

        public Builder popupWidth(int popupWidth) {
            this.popupWidth = popupWidth;
            return this;
        }

        public Builder popupHeight(int popupHeight) {
            this.popupHeight = popupHeight;
            return this;
        }

        public Builder titleIconWidth(int titleIconWidth) {
            this.titleIconWidth = titleIconWidth;
            return this;
        }

        public Builder titleIconHeight(int titleIconHeight) {
            this.titleIconHeight = titleIconHeight;
            return this;
        }

        public Builder showTitle(boolean showTitle) {
            this.showTitle = showTitle;
            return this;
        }

        public Builder titleBg(int[] titleBg) {
            this.titleBg = titleBg;
            return this;
        }

        public Builder showTitleIcon(boolean showTitleIcon) {
            this.showTitleIcon = showTitleIcon;
            return this;
        }

        public Builder titleIcon(Drawable titleIcon) {
            this.titleIcon = titleIcon;
            return this;
        }

        public Builder showTitleText(boolean showTitleText) {
            this.showTitleText = showTitleText;
            return this;
        }

        public Builder titleTextStr(String titleTextStr) {
            this.titleTextStr = titleTextStr;
            return this;
        }

        public Builder titleTextColor(int titleTextColor) {
            this.titleTextColor = titleTextColor;
            return this;
        }

        public Builder titleTextSize(int titleTextSize) {
            this.titleTextSize = titleTextSize;
            return this;
        }

        public Builder titleTextGravity(int titleTextGravity) {
            this.titleTextGravity = titleTextGravity;
            return this;
        }

        public Builder scrollBarEnabled(boolean scrollBarEnabled) {
            this.scrollBarEnabled = scrollBarEnabled;
            return this;
        }

        public Builder scrollBarFadeDuration(int scrollBarFadeDuration) {
            this.scrollBarFadeDuration = scrollBarFadeDuration;
            return this;
        }

        public Builder scrollBarSize(int scrollBarSize) {
            this.scrollBarSize = scrollBarSize;
            return this;
        }

        public Builder scrollBarStyle(int scrollBarStyle) {
            this.scrollBarStyle = scrollBarStyle;
            return this;
        }

        public Builder buttonTextStr1(String buttonTextStr1) {
            this.buttonTextStr1 = buttonTextStr1;
            return this;
        }

        public Builder buttonTextStr2(String buttonTextStr2) {
            this.buttonTextStr2 = buttonTextStr2;
            return this;
        }

        public Builder buttonTextSize(int buttonTextSize) {
            this.buttonTextSize = buttonTextSize;
            return this;
        }

        public Builder buttonTextColor1(int buttonTextColor1) {
            this.buttonTextColor1 = buttonTextColor1;
            return this;
        }

        public Builder buttonTextColor2(int buttonTextColor2) {
            this.buttonTextColor2 = buttonTextColor2;
            return this;
        }

        public Builder showButton1(boolean showButton1) {
            this.showButton1 = showButton1;
            return this;
        }

        public Builder showButton2(boolean showButton2) {
            this.showButton2 = showButton2;
            return this;
        }

        public Builder titleDividingColor(int titleDividingColor) {
            this.titleDividingColor = titleDividingColor;
            return this;
        }

        public Builder buttonDividingColor(int buttonDividingColor) {
            this.buttonDividingColor = buttonDividingColor;
            return this;
        }

        public Builder listHeight(int listHeight) {
            this.listHeight = listHeight;
            return this;
        }

        public Builder listSelectImageHeight(int listSelectImageHeight) {
            this.listSelectImageHeight = listSelectImageHeight;
            return this;
        }

        public Builder listSelectImageWidth(int listSelectImageWidth) {
            this.listSelectImageWidth = listSelectImageWidth;
            return this;
        }

        public Builder listIconHeight(int listIconHeight) {
            this.listIconHeight = listIconHeight;
            return this;
        }

        public Builder listIconWidth(int listIconWidth) {
            this.listIconWidth = listIconWidth;
            return this;
        }

        public Builder listTextSize(int listTextSize) {
            this.listTextSize = listTextSize;
            return this;
        }

        public Builder listSelectImagePaddings(int[] listSelectImagePaddings) {
            this.listSelectImagePaddings = listSelectImagePaddings;
            return this;
        }

        public Builder listIconPaddings(int[] listIconPaddings) {
            this.listIconPaddings = listIconPaddings;
            return this;
        }

        public Builder listTextPaddings(int[] listTextPaddings) {
            this.listTextPaddings = listTextPaddings;
            return this;
        }

        public Builder listSelectImageOn(Drawable listSelectImageOn) {
            this.listSelectImageOn = listSelectImageOn;
            return this;
        }

        public Builder listSelectImageOff(Drawable listSelectImageOff) {
            this.listSelectImageOff = listSelectImageOff;
            return this;
        }

        public Builder listIcon(Drawable listIcon) {
            this.listIcon = listIcon;
            return this;
        }


        public Builder listTextColor(int listTextColor) {
            this.listTextColor = listTextColor;
            return this;
    }

        public Builder listTextSelectColor(int listTextSelectColor) {
            this.listTextSelectColor = listTextSelectColor;
            return this;
        }

        public Builder listTextGravity(int listTextGravity) {
            this.listTextGravity = listTextGravity;
            return this;
        }

        public Builder showListSelectImage(boolean showListSelectImage) {
            this.showListSelectImage = showListSelectImage;
            return this;
        }

        public Builder showListIcon(boolean showListIcon) {
            this.showListIcon = showListIcon;
            return this;
        }

        //返回外部对象
        public SelectSettings build() {
            return new SelectSettings(this);
        }

    }


    private SelectSettings(Builder builder) {
        selectListDataBean = builder.selectListDataBean;
        clickListener = builder.clickListener;

        focusable = builder.focusable;
        touchable = builder.touchable;
        outsideTouchable = builder.outsideTouchable;
        inputMethodMode = builder.inputMethodMode;
        softInputMode = builder.softInputMode;

        popupBg = builder.popupBg;
        popupRadius = builder.popupRadius;
        titleIconPaddings = builder.titleIconPaddings;
        titleTextPaddings = builder.titleTextPaddings;
        listPaddings = builder.listPaddings;
        buttonPaddings = builder.buttonPaddings;

        popupWidth = builder.popupWidth;
        popupHeight = builder.popupHeight;
        titleIconWidth = builder.titleIconWidth;
        titleIconHeight = builder.titleIconHeight;

        showTitle = builder.showTitle;
        titleBg = builder.titleBg;

        showTitleIcon = builder.showTitleIcon;
        titleIcon = builder.titleIcon;

        showTitleText = builder.showTitleText;
        titleTextStr = builder.titleTextStr;
        titleTextColor = builder.titleTextColor;
        titleTextSize = builder.titleTextSize;
        titleTextGravity = builder.titleTextGravity;

        scrollBarEnabled = builder.scrollBarEnabled;
        scrollBarFadeDuration = builder.scrollBarFadeDuration;
        scrollBarSize = builder.scrollBarSize;
        scrollBarStyle = builder.scrollBarStyle;


        buttonTextStr1 = builder.buttonTextStr1;
        buttonTextStr2 = builder.buttonTextStr2;
        buttonTextSize = builder.buttonTextSize;
        buttonTextColor1 = builder.buttonTextColor1;
        buttonTextColor2 = builder.buttonTextColor2;
        showButton1 = builder.showButton1;
        showButton2 = builder.showButton2;
        titleDividingColor = builder.titleDividingColor;
        buttonDividingColor = builder.buttonDividingColor;

        listHeight = builder.listHeight;

        listSelectImageHeight = builder.listSelectImageHeight;
        listSelectImageWidth = builder.listSelectImageWidth;
        listIconHeight = builder.listIconHeight;
        listIconWidth = builder.listIconWidth;
        listTextSize = builder.listTextSize;

        listSelectImagePaddings = builder.listSelectImagePaddings;
        listIconPaddings = builder.listIconPaddings;
        listTextPaddings = builder.listTextPaddings;

        listSelectImageOn = builder.listSelectImageOn;
        listSelectImageOff = builder.listSelectImageOff;
        listIcon = builder.listIcon;

        listTextColor = builder.listTextColor;
        listTextSelectColor = builder.listTextSelectColor;
        listTextGravity = builder.listTextGravity;

        showListSelectImage = builder.showListSelectImage;
        showListIcon = builder.showListIcon;
        multipleSelection = builder.multipleSelection;

        listDivider = builder.listDivider;
        listDividerHeight= builder.listDividerHeight;
        bgAlpha = builder.bgAlpha;
    }
}
