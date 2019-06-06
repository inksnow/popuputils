package com.inks.inkslibrary.Popup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.inks.inkslibrary.Utils.GetResId;
import com.inks.inkslibrary.Utils.L;

public class PopupPrompt {
    public interface onClickListener {
        public void onClick(int what);
    }
    private View contentView = null;
    private PopupWindow pWindow;
    private Context context;
    private Window window;
    private LayoutInflater inflater;
    private PromptSettings promptSettings;

    private LinearLayout bgView;
    private RelativeLayout imageAndProView;
    private TextView textView;
    private Button buttonView;
    private ImageView imageView;
    private ProgressBar progressBar;

    @SuppressLint("ClickableViewAccessibility")
    public void popupPrompt(Window window, Context context, LayoutInflater inflater, final PromptSettings promptSettings, final int what) {
                this.window = window;
                this.context = context;
                this.inflater = inflater;
                this.promptSettings = promptSettings;


        if (pWindow != null && pWindow.isShowing()) {
            initView();
            contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
           pWindow.update( contentView.getWidth(),contentView.getHeight());
            if(promptSettings.getDuration()>0){
                L.e("promptSettings.getDuration():"+promptSettings.getDuration());
                myHandler.removeMessages(1);
                myHandler.sendEmptyMessageDelayed(1, promptSettings.getDuration());
            }
        }else{
            contentView = inflater.inflate(GetResId.getId(context, "layout", "popup_prompt"), null);
            pWindow = new PopupWindow(contentView, promptSettings.getWidth(), promptSettings.getHeight());

            textView = contentView.findViewById(GetResId.getId(context, "id", "popup_text"));
            bgView= contentView.findViewById(GetResId.getId(context, "id", "popup_prompt"));
            imageAndProView= contentView.findViewById(GetResId.getId(context, "id", "popup_image_pro"));
            buttonView= contentView.findViewById(GetResId.getId(context, "id", "popup_button"));
            imageView= contentView.findViewById(GetResId.getId(context, "id", "popup_image"));
            progressBar= contentView.findViewById(GetResId.getId(context, "id", "popup_pro"));
            initView();

            buttonView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(event.getAction()==MotionEvent.ACTION_DOWN){
                        buttonView.setTextColor(promptSettings.getButtonColour()-0Xaa000000);
                    }else if((event.getAction()==MotionEvent.ACTION_UP)||(event.getAction()==MotionEvent.ACTION_OUTSIDE)){
                        buttonView.setTextColor(promptSettings.getButtonColour());
                    }
                    return false;
                }
            });
            buttonView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    miss();
                    if(promptSettings.getClickListener()!=null){
                        promptSettings.getClickListener().onClick(what);
                    }

                }
            });


            pWindow.setClippingEnabled(promptSettings.isClippingEnabled());

            pWindow.showAtLocation(window.getDecorView(), promptSettings.getLocation(), promptSettings.getGravityX(), promptSettings.getGravityY());

            if(promptSettings.getDuration()>0){
                myHandler.removeMessages(1);
                myHandler.sendEmptyMessageDelayed(1, promptSettings.getDuration());
            }
            pWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                     backgroundAlpha(1f);
                    if(promptSettings.getDismissListener()!=null){
                        promptSettings.getDismissListener().onDismiss();
                    }
                }
            });
        }
    }

    private void initView() {
        //背景色及圆角
        GradientDrawable drawable = new GradientDrawable();
        //形状（矩形）
        drawable.setShape(GradientDrawable.RECTANGLE);
        //渐变样式
        drawable.setGradientType(GradientDrawable.RECTANGLE);
        //渐变方向（左到右）
        drawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
        //圆角
        drawable.setCornerRadii(promptSettings.getRadius());
        //颜色
        drawable.setColors(promptSettings.getBgColour());
        bgView.setBackground(drawable);

        if(promptSettings.isShowText()){
            textView.setVisibility(View.VISIBLE);
            textView.setText(promptSettings.getText());
            textView.setTextColor(promptSettings.getTextColour());
            textView.setTextSize(promptSettings.getTextSize());
            textView.setPadding(promptSettings.getTextPaddings()[0],
                    promptSettings.getTextPaddings()[1],
                    promptSettings.getTextPaddings()[2],
                    promptSettings.getTextPaddings()[3]);
            textView.setGravity(promptSettings.getTextGravity());
        }else{
            textView.setVisibility(View.GONE);
        }


        if(promptSettings.isShowImage()){
            imageAndProView.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) imageAndProView.getLayoutParams();
            linearParams.width = promptSettings.getImageWidth();
            imageAndProView.setLayoutParams(linearParams);
            if(promptSettings.getShowMode()==PromptSettings.MODE_SHOW_PRO){
                progressBar.setVisibility(View.VISIBLE);
                imageView.clearAnimation();
                imageView.setVisibility(View.GONE);
                //progressBar颜色
                ColorStateList colorStateList = ColorStateList.valueOf(promptSettings.getProColour());
                progressBar.setIndeterminateTintList(colorStateList);
                progressBar.setPadding(promptSettings.getImagePaddings()[0],
                        promptSettings.getImagePaddings()[1],
                        promptSettings.getImagePaddings()[2],
                        promptSettings.getImagePaddings()[3]);
            }else{
                if(promptSettings.getImage()==null){
                    imageAndProView.setVisibility(View.GONE);
                }else{
                    progressBar.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);
                    imageView.setImageDrawable(promptSettings.getImage());
                    imageView.setPadding(promptSettings.getImagePaddings()[0],
                            promptSettings.getImagePaddings()[1],
                            promptSettings.getImagePaddings()[2],
                            promptSettings.getImagePaddings()[3]);
                    if(promptSettings.getImageAnim()!=null){
                        imageView.startAnimation(promptSettings.getImageAnim());
                    }

                }
            }
        }else{
            imageAndProView.setVisibility(View.GONE);
        }

        if(promptSettings.isShowButton()){
            buttonView.setVisibility(View.VISIBLE);
            buttonView.setText(promptSettings.getButtonText());
            buttonView.setTextColor(promptSettings.getButtonColour());
            buttonView.setTextSize(promptSettings.getTextSize());
            buttonView.setPadding(promptSettings.getButtonPaddings()[0],
                    promptSettings.getButtonPaddings()[1],
                    promptSettings.getButtonPaddings()[2],
                    promptSettings.getButtonPaddings()[3]);
        }else{
            buttonView.setVisibility(View.GONE);
        }



        //pWindow.setAnimationStyle(GetResId.getId(context, "style", "popup_top_down"));


        backgroundAlpha(promptSettings.getBgAlpha());
        if(promptSettings.getPopupAnim()==0){
            pWindow.setAnimationStyle(GetResId.getId(context, "style", "popup_fade_in_out"));
        }else{
            pWindow.setAnimationStyle(promptSettings.getPopupAnim());
        }

        pWindow.setTouchable(promptSettings.isTouchable());
        pWindow.setFocusable(promptSettings.isFocusable());
        pWindow.setOutsideTouchable(promptSettings.isOutsideTouchable());
        pWindow.setInputMethodMode(promptSettings.getInputMethodMode());
        pWindow.setSoftInputMode(promptSettings.getSoftInputMode());
        pWindow.setBackgroundDrawable(new BitmapDrawable());
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        window.setAttributes(lp);
    }


    public void miss() {
        L.e("miss");
        myHandler.removeCallbacksAndMessages(null);
        if (pWindow != null && pWindow.isShowing()) {
            pWindow.dismiss();
        }
    }

    @SuppressLint("HandlerLeak")
    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    miss();
                    break;
            }
        }
    };


    public PopupWindow getpWindow(){
        return pWindow;
    }

}
