package com.inks.popuptest;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.inks.inkslibrary.Popup.ConvenientPopup;
import com.inks.inkslibrary.Popup.PopupPrompt;
import com.inks.inkslibrary.Popup.PopupSelect;
import com.inks.inkslibrary.Popup.PopupView;
import com.inks.inkslibrary.Popup.PromptSettings;
import com.inks.inkslibrary.Popup.SelectListDataBean;
import com.inks.inkslibrary.Popup.SelectSettings;
import com.inks.inkslibrary.Popup.ViewSettings;
import com.inks.inkslibrary.Utils.L;
import com.inks.inkslibrary.Utils.T;

import java.util.ArrayList;
import java.util.List;

public class PopupViewTestActivity extends AppCompatActivity {
    private PopupView popupView;
    private Window window;
    private LayoutInflater inflater;
    private Context context;
    private PopupPrompt popupPrompt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_test);

        window = this.getWindow();
        inflater = this.getLayoutInflater();
        context = this;
        popupView = new PopupView();
        popupPrompt = new PopupPrompt();
        initData();
    }

    private  void initData(){

    }


    public void ccc(View view) {

        switch (view.getId()){
            case R.id.test:
                TextView textView = new TextView(context);
                textView.setText("这真是一个提示！");
                textView.setTextColor(0XFF333333);
                textView.setPadding(50,0,0,0);
                ViewSettings.Builder builder = new ViewSettings.Builder();
                ViewSettings promptSettings =
                        builder.clickListener(popupBackListener)
                                .titleTextStr("这是一个提示")
                                .focusable(false)
                                .outsideTouchable(false)
                                .build();
                popupView.popupView(window,context,inflater,textView,promptSettings,1);
                break;
            case R.id.test2:
                LinearLayout linearLayout = (LinearLayout) inflater.inflate(
                    R.layout.image_and_text, null);

                 builder = new ViewSettings.Builder();
                 promptSettings =
                        builder .clickListener(popupBackListener)
                                .showTitle(false)
                                .showButton1(false)
                                .popupBg(new int[] {0XFF03a9f4,0XFFFFFFFF})
                                .build();
                popupView.popupView(window,context,inflater,linearLayout,promptSettings,2);
                break;
            case R.id.test3:
                 linearLayout = (LinearLayout) inflater.inflate(
                        R.layout.scrollviewtest, null);
                builder = new ViewSettings.Builder();
                promptSettings =
                        builder .clickListener(popupBackListener)
                                .titleTextStr("你好呀")
                                .titleIcon(getDrawable(R.mipmap.ic_launcher_round))
                                .titleTextPaddings(new int[]{10,20,0,20})
                                .showTitleIcon(true)
                                .popupHeight(-2)
                                .build();
                popupView.popupView(window,context,inflater,linearLayout,promptSettings,3);

                break;
            case R.id.test4:
                 textView = new TextView(context);
                textView.setText("你确定要这样做吗？");
                textView.setTextColor(0XFF333333);
                textView.setGravity(Gravity.CENTER);
                builder = new ViewSettings.Builder();
                promptSettings =
                        builder .clickListener(popupBackListener)
                                .showTitle(false)
                                .showButton1(false)
                                .popupHeight(-2)
                                .build();
                popupView.popupView(window,context,inflater,textView,promptSettings,4);
                break;
            case R.id.test5:
                ConvenientPopup.getInstance().showPopupMsg(PopupViewTestActivity.this, "这是一个消息提示",true, "", true,new PopupView.onClickListener() {
                    @Override
                    public void onYesBack(int what) {
                        T.showShort(getApplicationContext(),"确认");
                    }

                    @Override
                    public void onCancelBack(int what) {
                        T.showShort(getApplicationContext(),"取消");
                    }
                });
                break;
            case R.id.test6:
                List<SelectListDataBean> list = new ArrayList<>();
                for (int i = 0; i < 15; i++) {
                    SelectListDataBean selectListDataBean = new SelectListDataBean();
                    selectListDataBean.setText("请选择");
                    selectListDataBean.setChoosed(false);
                    list.add(selectListDataBean);
                }


                ConvenientPopup.getInstance().showSelectBottom(PopupViewTestActivity.this,"",true,list,null);
                break;

            case R.id.test7:
                List<SelectListDataBean> list2 = new ArrayList<>();
                for (int i = 0; i < 15; i++) {
                    SelectListDataBean selectListDataBean = new SelectListDataBean();
                    selectListDataBean.setText("请选择");
                    selectListDataBean.setChoosed(false);
                    list2.add(selectListDataBean);
                }

                ConvenientPopup.getInstance().showSelectCenter(PopupViewTestActivity.this,"",false,list2,null);
                break;


        }
    }

    PopupView.onClickListener popupBackListener = new PopupView.onClickListener() {

        @Override
        public void onYesBack(int what) {
            showMgs("你点击了提示框"+what+"确定按钮");

        }

        @Override
        public void onCancelBack(int what) {
            showMgs("你点击了提示框"+what+"取消按钮");
        }
    };

    private void showMgs(String mgs){
        popupPrompt.miss();
        PromptSettings.Builder builder = new PromptSettings.Builder();
        PromptSettings promptSettings = builder
                .text(mgs)
                .clippingEnabled(true)
                .textPaddings(new int[]{50,0,0,0})
                .location(Gravity.TOP)
                .popupAnim(R.style.popup_top_down)
                .bgAlpha(0.6f)
                .build();
        popupPrompt.popupPrompt(window,context,inflater,promptSettings,0);
    }


    //为了解决弹出PopupWindow后外部的事件不会分发,既外部的界面不可以点击
    @Override
    public boolean dispatchTouchEvent(MotionEvent event){
        L.e("dispatchTouchEvent");
        if (popupView.getpWindow()!= null && popupView.getpWindow().isShowing()){
            L.e("popupSelect.getpWindow().isShowing()");
            return false;
        }else{
            return super.dispatchTouchEvent(event);
        }
    }


}
