package com.inks.popuptest;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.inks.inkslibrary.Popup.ConvenientPopup;
import com.inks.inkslibrary.Popup.PopupView;
import com.inks.inkslibrary.Popup.SelectListDataBean;
import com.inks.inkslibrary.Utils.L;
import com.inks.inkslibrary.Utils.T;

import java.util.ArrayList;
import java.util.List;

public class ConvenientPopupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convenient_popup);
    }


    public void ccc(View view) {
        switch (view.getId()) {
            case R.id.test5:
                ConvenientPopup.getInstance().showPopupMsg(ConvenientPopupActivity.this, "这是一个消息提示", true, "", true, new PopupView.onClickListener() {
                    @Override
                    public void onYesBack(int what) {
                        T.showShort(getApplicationContext(), "确认");
                    }

                    @Override
                    public void onCancelBack(int what) {
                        T.showShort(getApplicationContext(), "取消");
                    }
                });
                break;
            case R.id.test6:
                List<SelectListDataBean> list = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    SelectListDataBean selectListDataBean = new SelectListDataBean();
                    selectListDataBean.setText("请选择选项"+i);
                    selectListDataBean.setChoosed(false);
                    list.add(selectListDataBean);
                }

                ConvenientPopup.getInstance().showSelectBottom(ConvenientPopupActivity.this, "请选择", true, list, null);
                break;

            case R.id.test7:
                List<SelectListDataBean> list2 = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    SelectListDataBean selectListDataBean = new SelectListDataBean();
                    selectListDataBean.setText("请选择选项"+i);
                    selectListDataBean.setChoosed(false);
                    list2.add(selectListDataBean);
                }

                ConvenientPopup.getInstance().showSelectCenter(ConvenientPopupActivity.this, "", false, list2, null);
                break;
            case R.id.test8:

                ConvenientPopup.getInstance().showPopupSelectDataAmPm(ConvenientPopupActivity.this,null , 0);
                break;
            case R.id.test9:

                ConvenientPopup.getInstance().showPopupSelectDataHHSSMM(ConvenientPopupActivity.this,null , 0);
                break;


        }
    }
    //为了解决弹出PopupWindow后外部的事件不会分发,既外部的界面不可以点击
    @Override
    public boolean dispatchTouchEvent(MotionEvent event){
        if ( ConvenientPopup.getInstance().isPopupShowing()){
            return false;
        }else{
            return super.dispatchTouchEvent(event);
        }
    }
}