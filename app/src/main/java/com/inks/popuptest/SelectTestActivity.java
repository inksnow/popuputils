package com.inks.popuptest;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

import com.inks.inkslibrary.Popup.PopupPrompt;
import com.inks.inkslibrary.Popup.PopupSelect;
import com.inks.inkslibrary.Popup.PromptSettings;
import com.inks.inkslibrary.Popup.SelectListDataBean;
import com.inks.inkslibrary.Popup.SelectSettings;
import com.inks.inkslibrary.Utils.L;

import java.util.ArrayList;
import java.util.List;

public class SelectTestActivity extends AppCompatActivity {
    private PopupSelect popupSelect;
    private Window window;
    private LayoutInflater inflater;
    private Context context;
    private List<SelectListDataBean> selectListDataBeans = new ArrayList<>();
    private List<SelectListDataBean> selectListDataBeans2= new ArrayList<>();
    private PopupPrompt popupPrompt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_test);

        window = this.getWindow();
        inflater = this.getLayoutInflater();
        context = this;
        popupSelect = new PopupSelect();
        popupPrompt = new PopupPrompt();
        initData();
    }

    private  void initData(){
        selectListDataBeans.clear();
        SelectListDataBean selectListDataBean = new SelectListDataBean();
        selectListDataBean.setText("list000001");
        selectListDataBean.setChoosed(false);
        selectListDataBeans.add(selectListDataBean);
        selectListDataBean = new SelectListDataBean();
        selectListDataBean.setText("list000002");
        selectListDataBean.setChoosed(false);
        selectListDataBeans.add(selectListDataBean);
        selectListDataBean = new SelectListDataBean();
        selectListDataBean.setText("list000003");
        selectListDataBean.setChoosed(false);
        selectListDataBeans.add(selectListDataBean);
        selectListDataBean = new SelectListDataBean();
        selectListDataBean.setText("list000004");
        selectListDataBean.setChoosed(false);
        selectListDataBeans.add(selectListDataBean);

        selectListDataBeans2.clear();
         selectListDataBean = new SelectListDataBean();
        selectListDataBean.setText("中文");
        selectListDataBean.setIcon(getDrawable(R.drawable.cn));
        selectListDataBean.setChoosed(true);
        selectListDataBeans2.add(selectListDataBean);
        selectListDataBean = new SelectListDataBean();
        selectListDataBean.setText("Deutsch");
        selectListDataBean.setIcon(getDrawable(R.drawable.de));
        selectListDataBean.setChoosed(false);
        selectListDataBeans2.add(selectListDataBean);
        selectListDataBean = new SelectListDataBean();
        selectListDataBean.setText("English");
        selectListDataBean.setIcon(getDrawable(R.drawable.us));
        selectListDataBean.setChoosed(false);
        selectListDataBeans2.add(selectListDataBean);
        selectListDataBean = new SelectListDataBean();
        selectListDataBean.setText("한국어");
        selectListDataBean.setIcon(getDrawable(R.drawable.kr));
        selectListDataBean.setChoosed(false);
        selectListDataBeans2.add(selectListDataBean);
    }


    public void ccc(View view) {

        switch (view.getId()){
            case R.id.test:
                SelectSettings.Builder builder = new SelectSettings.Builder();
                SelectSettings promptSettings =
                        builder.selectListDataBean(selectListDataBeans)
                                .clickListener(selectBackListener)
                                .build();
                popupSelect.popupSelect(window,context,inflater,promptSettings,0);
                break;
            case R.id.test2:
                 builder = new SelectSettings.Builder();
                 promptSettings =
                        builder.selectListDataBean(selectListDataBeans)
                                .clickListener(selectBackListener)
                                .titleTextStr("请选择")
                                .showListSelectImage(false)
                                .listTextGravity(Gravity.CENTER)
                                .showButton1(false)
                                .titleTextPaddings(new int[]{0,20,0,20})
                                .titleTextGravity(Gravity.CENTER)
                                .build();
                popupSelect.popupSelect(window,context,inflater,promptSettings,0);
                break;
            case R.id.test3:

                builder = new SelectSettings.Builder();
                promptSettings =
                        builder.selectListDataBean(selectListDataBeans2)
                                .clickListener(selectBackListener)
                                .titleTextStr("请选择语言")
                                .titleIcon(getDrawable(R.drawable.l))
                                .titleTextPaddings(new int[]{10,20,0,20})
                                .showTitleIcon(true)
                                .multipleSelection(false)
                                .showListIcon(true)
                                .build();
                popupSelect.popupSelect(window,context,inflater,promptSettings,0);

                break;
            case R.id.test4:
                builder = new SelectSettings.Builder();
                promptSettings =
                        builder.selectListDataBean(selectListDataBeans2)
                                .clickListener(selectBackListener)
                                .titleTextStr("请选择语言")
                                .showTitleIcon(true)
                                .showListSelectImage(false)
                                .multipleSelection(false)
                                .showButton2(false)
                                .showListIcon(false)
                                .listTextGravity(Gravity.CENTER)
                                .titleTextPaddings(new int[]{0,20,0,20})
                                .titleTextGravity(Gravity.CENTER)
                                .build();
                popupSelect.popupSelect(window,context,inflater,promptSettings,0);
                break;
        }
    }

    PopupSelect.onClickListener selectBackListener = new PopupSelect.onClickListener() {
        @Override
        public void onChooseBack(List<SelectListDataBean> selectListDataBeans, int what) {
            String choosed ="";
            for(int i = 0;i<selectListDataBeans.size();i++){
                if(selectListDataBeans.get(i).isChoosed()){
                    choosed = choosed+(i+1)+".";
                }
            }
            if(choosed.equals("")){
                showMgs("你一个都没有选中");
            }else{
                showMgs("你选择了第"+choosed+"条");
            }

        }

        @Override
        public void onCancelBack(List<SelectListDataBean> selectListDataBeans, int what) {

        }
    };

    private void showMgs(String mgs){
        popupPrompt.miss();
        PromptSettings.Builder builder = new PromptSettings.Builder();
        PromptSettings promptSettings = builder
                .text(mgs)
                .textPaddings(new int[]{50,0,0,0})
                .location(Gravity.CENTER)
                .popupAnim(R.style.popup_left_right)
                .bgAlpha(0.6f)
                .build();
        popupPrompt.popupPrompt(window,context,inflater,promptSettings,0);
    }


    //为了解决弹出PopupWindow后外部的事件不会分发,既外部的界面不可以点击
    @Override
    public boolean dispatchTouchEvent(MotionEvent event){
        L.e("dispatchTouchEvent");
        if (popupSelect.getpWindow()!= null && popupSelect.getpWindow().isShowing()){
            L.e("popupSelect.getpWindow().isShowing()");
            return false;
        }else{
            return super.dispatchTouchEvent(event);
        }
    }

}
