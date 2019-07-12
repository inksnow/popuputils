package com.inks.inkslibrary.Popup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.inks.inkslibrary.Utils.GetResId;
import com.inks.inkslibrary.Utils.L;

import java.util.List;

public class PopupView {
    public interface onClickListener {
        public void onYesBack( int what);
        public void onCancelBack(int what);
    }

    private View contentView = null;
    private PopupWindow pWindow;
    private Context context;
    private Window window;
    private LayoutInflater inflater;
    private ViewSettings viewSettings;
    private int what;

    private LinearLayout bgView;
    private LinearLayout titleView;
    private ImageView titleIcon;
    private TextView titleText;
    private View titleDivision;
    private LinearLayout content;
    private View buttonDivision1;
    private View buttonDivision2;
    private TextView button1;
    private TextView button2;



    @SuppressLint("ClickableViewAccessibility")
        public void popupView(Window window, Context context, LayoutInflater inflater, View view,final ViewSettings viewSettings, final int what) {

        this.window = window;
        this.context = context;
        this.inflater = inflater;
        this.viewSettings = viewSettings;
        this.what = what;
        if(view!=null){
            if (!(pWindow != null && pWindow.isShowing())) {

                contentView = inflater.inflate(GetResId.getId(context, "layout", "popup_view"), null);
                pWindow = new PopupWindow(contentView, viewSettings.getPopupWidth(), viewSettings.getPopupHeight());
                bgView = contentView.findViewById(GetResId.getId(context, "id", "popup_select"));
                titleView= contentView.findViewById(GetResId.getId(context, "id", "popup_title"));
                titleIcon= contentView.findViewById(GetResId.getId(context, "id", "popup_title_icon"));
                titleText= contentView.findViewById(GetResId.getId(context, "id", "popup_title_text"));
                titleDivision= contentView.findViewById(GetResId.getId(context, "id", "popup_title_division"));
                content= contentView.findViewById(GetResId.getId(context, "id", "popup_content"));
                buttonDivision1= contentView.findViewById(GetResId.getId(context, "id", "popup_list_division"));
                buttonDivision2= contentView.findViewById(GetResId.getId(context, "id", "popup_button_division"));
                button1= contentView.findViewById(GetResId.getId(context, "id", "popup_button_1"));
                button2= contentView.findViewById(GetResId.getId(context, "id", "popup_button_2"));
                initView();
                content.addView(view);
                button1.setTag(1);
                button2.setTag(2);
                button1.setOnTouchListener(touchListener);
                button2.setOnTouchListener(touchListener);
                button1.setOnClickListener(clickListener);
                button2.setOnClickListener(clickListener);


                pWindow.setAnimationStyle(GetResId.getId(context, "style", "popup_fade_in_out"));
                pWindow.setTouchable(viewSettings.isTouchable());
                pWindow.setFocusable(viewSettings.isFocusable());
                pWindow.setOutsideTouchable(viewSettings.isOutsideTouchable());
                pWindow.setInputMethodMode(viewSettings.getInputMethodMode());
                pWindow.setSoftInputMode(viewSettings.getSoftInputMode());
                pWindow.setBackgroundDrawable(new BitmapDrawable());
                pWindow.setClippingEnabled(viewSettings.isClippingEnabled());
                pWindow.showAtLocation(window.getDecorView(), Gravity.CENTER, 0, 0);
                pWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        backgroundAlpha(1f);

                    }
                });

            }else{
                L.e("已显示");
            }
        }else{
            L.e("数据为空");
        }

    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            miss();

            if(viewSettings.getClickListener()!=null){
                switch ((int)v.getTag()){
                    case 1:
                        L.e("点击了取消");
                        if(viewSettings.getClickListener()!=null){
                            viewSettings.getClickListener().onCancelBack(what);
                        }
                        break;
                    case 2:
                        L.e("点击了确认");
                        if(viewSettings.getClickListener()!=null){
                            viewSettings.getClickListener().onYesBack(what);
                        }
                        break;
                }
            }
        }
    };

    View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch ((int)v.getTag()){
                case 1:
                    if(event.getAction()==MotionEvent.ACTION_DOWN){
                        button1.setTextColor(viewSettings.getButtonTextColor1()-0Xaa000000);
                    }else if((event.getAction()==MotionEvent.ACTION_UP)||(event.getAction()==MotionEvent.ACTION_OUTSIDE)){
                        button1.setTextColor(viewSettings.getButtonTextColor1());
                    }
                    break;
                case 2:
                    if(event.getAction()==MotionEvent.ACTION_DOWN){
                        button2.setTextColor(viewSettings.getButtonTextColor2()-0Xaa000000);
                    }else if((event.getAction()==MotionEvent.ACTION_UP)||(event.getAction()==MotionEvent.ACTION_OUTSIDE)){
                        button2.setTextColor(viewSettings.getButtonTextColor2());
                    }
                    break;
            }
            return false;
        }
    };




    private void initView(){

        backgroundAlpha(viewSettings.getBgAlpha());

        //背景色及圆角
        GradientDrawable drawable = new GradientDrawable();
        //形状（矩形）
        drawable.setShape(GradientDrawable.RECTANGLE);
        //渐变样式
        drawable.setGradientType(GradientDrawable.RECTANGLE);
        //渐变方向（左到右）
        drawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
        //圆角
        drawable.setCornerRadii(viewSettings.getPopupRadius());
        //颜色
        drawable.setColors(viewSettings.getPopupBg());
        bgView.setBackground(drawable);

        if(viewSettings.isShowTitle()){
            titleView.setVisibility(View.VISIBLE);

            //背景色及圆角
             drawable = new GradientDrawable();
            //形状（矩形）
            drawable.setShape(GradientDrawable.RECTANGLE);
            //渐变样式
            drawable.setGradientType(GradientDrawable.RECTANGLE);
            //渐变方向（左到右）
            drawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
            //圆角
            drawable.setCornerRadii(new float[]{viewSettings.getPopupRadius()[0],
                    viewSettings.getPopupRadius()[1],viewSettings.getPopupRadius()[2],
                    viewSettings.getPopupRadius()[3],0,0,0,0});
            //颜色
            drawable.setColors(viewSettings.getTitleBg());
            titleView.setBackground(drawable);

            if(viewSettings.isShowTitleIcon() && viewSettings.getTitleIcon()!=null){
                titleIcon.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) titleIcon.getLayoutParams();
                linearParams.width = viewSettings.getTitleIconWidth();
                linearParams.height =  viewSettings.getTitleIconHeight();
                titleIcon.setLayoutParams(linearParams);
                titleIcon.setPadding(viewSettings.getTitleIconPaddings()[0],
                        viewSettings.getTitleIconPaddings()[1],
                        viewSettings.getTitleIconPaddings()[2],
                        viewSettings.getTitleIconPaddings()[3]);

                titleIcon.setImageDrawable(viewSettings.getTitleIcon());
            }else{
                titleIcon.setVisibility(View.GONE);
            }

            if(viewSettings.isShowTitleText()){
                titleText.setVisibility(View.VISIBLE);
                titleText.setTextSize(viewSettings.getTitleTextSize());
                titleText.setTextColor(viewSettings.getTitleTextColor());
                titleText.setText(viewSettings.getTitleTextStr());
                titleText.setPadding(viewSettings.getTitleTextPaddings()[0],
                        viewSettings.getTitleTextPaddings()[1],
                        viewSettings.getTitleTextPaddings()[2],
                        viewSettings.getTitleTextPaddings()[3]);
                titleText.setGravity(viewSettings.getTitleTextGravity());
            }else{
                titleText.setVisibility(View.GONE);
            }
            titleDivision.setVisibility(View.VISIBLE);
            titleDivision.setBackgroundColor(viewSettings.getTitleDividingColor());
        }else{
            titleView.setVisibility(View.GONE);
            titleDivision.setVisibility(View.GONE);
        }

        content.setPadding(viewSettings.getListPaddings()[0],
                viewSettings.getListPaddings()[1],
                viewSettings.getListPaddings()[2],
                viewSettings.getListPaddings()[3]);
        if(viewSettings.isShowButton1()||viewSettings.isShowButton2()){
            buttonDivision1.setVisibility(View.VISIBLE);
            buttonDivision1.setBackgroundColor(viewSettings.getButtonDividingColor());
        }else{
            buttonDivision1.setVisibility(View.GONE);
        }
        if(viewSettings.isShowButton1()&&viewSettings.isShowButton2()){
            buttonDivision2.setVisibility(View.VISIBLE);
            buttonDivision2.setBackgroundColor(viewSettings.getButtonDividingColor());
        }else{
            buttonDivision2.setVisibility(View.GONE);
        }

        if(viewSettings.isShowButton1()){
            button1.setVisibility(View.VISIBLE);
            button1.setText(viewSettings.getButtonTextStr1());
            button1.setTextSize(viewSettings.getButtonTextSize());
            button1.setTextColor(viewSettings.getButtonTextColor1());
            button1.setPadding(viewSettings.getButtonPaddings()[0],
                    viewSettings.getButtonPaddings()[1],
                    viewSettings.getButtonPaddings()[2],
                    viewSettings.getButtonPaddings()[3]);
        }else{
            button1.setVisibility(View.GONE);
        }

        if(viewSettings.isShowButton2()){
            button2.setVisibility(View.VISIBLE);
            button2.setText(viewSettings.getButtonTextStr2());
            button2.setTextSize(viewSettings.getButtonTextSize());
            button2.setTextColor(viewSettings.getButtonTextColor2());
            button2.setPadding(viewSettings.getButtonPaddings()[0],
                    viewSettings.getButtonPaddings()[1],
                    viewSettings.getButtonPaddings()[2],
                    viewSettings.getButtonPaddings()[3]);
        }else{
            button2.setVisibility(View.GONE);
        }





    }


    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        window.setAttributes(lp);
    }


    public void miss() {
        L.e("miss");
        if (pWindow != null && pWindow.isShowing()) {
            pWindow.dismiss();
        }
    }

    public PopupWindow getpWindow(){
        return pWindow;
    }

}
