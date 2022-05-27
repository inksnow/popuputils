package com.inks.popuptest;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.inks.inkslibrary.Popup.PopupPrompt;
import com.inks.inkslibrary.Popup.PromptSettings;

public class PromptTestActivity extends AppCompatActivity {
    private PopupPrompt popupPrompt;
    private Window window;
    private LayoutInflater inflater;
    private Context context;
    private Animation anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prompt_test);
        window = this.getWindow();
        inflater = this.getLayoutInflater();
        context = this;
        popupPrompt = new PopupPrompt();
        // 加载动画资源
        anim  = AnimationUtils.loadAnimation(this,R.anim.alpha);
    }

    public void buttonClick(View view) {
        switch (view.getId()){
            case R.id.button1:
                popupPrompt.miss();
                PromptSettings.Builder builder = new PromptSettings.Builder();
                PromptSettings promptSettings = builder .clippingEnabled(true).build();
                popupPrompt.popupPrompt(window,context,inflater,promptSettings,0);
                break;
            case R.id.button2:
                popupPrompt.miss();
                 builder = new PromptSettings.Builder();
                builder.bgAlpha(0.6f)
                        .duration(2000)
                        .bgColour(new int[]{0XFFFF0000,  0XFFFFFFFF})
                        .showImage(true)
                        .clippingEnabled(true)
                        .showMode(PromptSettings.MODE_SHOW_IMAGE)
                        .image(getDrawable(R.drawable.ic_launcher_foreground))
                        .imageWidth(160)
                        .imageAnim(anim)
                        .buttonColour(0XFFFF0000)
                        .popupAnim(R.style.popup_bottom_top)
                        .location(Gravity.BOTTOM);
                 promptSettings = builder.build();
                popupPrompt.popupPrompt(window,context,inflater,promptSettings,0);
                break;
            case R.id.button3:
                popupPrompt.miss();
                builder = new PromptSettings.Builder();
                builder.bgAlpha(0.6f)
                        .duration(2000)
                        .showImage(true)
                        .showMode(PromptSettings.MODE_SHOW_PRO)
                        .imageWidth(120)
                        .text("加载中，请稍后...")
                        .showButton(false)
                        .width( 600)
                        .radius(new float[]{25,25,25,25,25,25,25,25})
                        .popupAnim(R.style.popup_left_right)
                        .location(Gravity.CENTER);
                promptSettings = builder.build();
                popupPrompt.popupPrompt(window,context,inflater,promptSettings,0);
                break;
            case R.id.button4:
                popupPrompt.miss();
                builder = new PromptSettings.Builder();
                builder.bgAlpha(0.6f)
                        .duration(2000)
                        .bgColour(new int[]{0XFF03a9f4,  0XFFFFFFFF})
                        .showImage(true)
                        .clippingEnabled(true)
                        .showMode(PromptSettings.MODE_SHOW_PRO)
                        .imageWidth(120)
                        .showButton(true)
                        .proColour(0XFF00FF00)
                        .buttonColour(0XFF03a9f4)
                        .popupAnim(R.style.popup_top_down)
                        .location(Gravity.TOP);
                promptSettings = builder.build();
                popupPrompt.popupPrompt(window,context,inflater,promptSettings,0);
                break;
            case R.id.button5:
                popupPrompt.miss();
                builder = new PromptSettings.Builder();
                builder.bgAlpha(0.6f)
                        .duration(2000)
                        .clippingEnabled(true)
                        .bgColour(new int[]{0XFF03a9f4,  0XFFFFFFFF})
                        .showImage(false)
                        .showButton(false)
                        .textGravity(Gravity.CENTER)
                        .textColour(0xFFfd3498)
                        .popupAnim(R.style.popup_bottom_top)
                        .location(Gravity.BOTTOM);
                promptSettings = builder.build();
                popupPrompt.popupPrompt(window,context,inflater,promptSettings,0);
                break;
            case R.id.button6:
                popupPrompt.miss();
                builder = new PromptSettings.Builder();
                builder.bgAlpha(0.6f)
                        .duration(2000)
                        .bgColour(new int[]{0XFF03a9f4,  0XFFFFFFFF})
                        .showImage(false)
                        .showButton(false)
                        .textGravity(Gravity.CENTER)
                        .textColour(0xFFfd3498)
                        .text("你好！")
                        .width(200)
                        .height(80)
                        .gravityX(340)
                        .gravityY(860)
                        .textGravity(Gravity.CENTER)
                        .radius(new float[]{25,25,25,25,25,25,25,25})
                        .location(Gravity.TOP);
                promptSettings = builder.build();
                popupPrompt.popupPrompt(window,context,inflater,promptSettings,0);
                break;
        }
    }

}
