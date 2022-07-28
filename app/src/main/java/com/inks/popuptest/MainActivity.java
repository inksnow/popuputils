package com.inks.popuptest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.inks.inkslibrary.Popup.ConvenientPopup;
import com.inks.inkslibrary.Popup.PopupSelectDateAndAmPm;
import com.inks.inkslibrary.Popup.PopupSelectDateTime;
import com.inks.inkslibrary.Popup.PopupSelectDateTime2;
import com.inks.inkslibrary.Utils.GetMacUtil;
import com.inks.inkslibrary.Utils.L;
import com.inks.inkslibrary.Utils.MacUtils;

import java.util.Calendar;
import java.util.GregorianCalendar;

import top.defaults.view.DateTimePickerView;

public class MainActivity extends AppCompatActivity {
    PopupSelectDateTime selectDateTime;
    PopupSelectDateTime2 selectDateTime2;
    PopupSelectDateAndAmPm selectDateAndAmPm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        selectDateTime = new PopupSelectDateTime();
        selectDateTime2 = new PopupSelectDateTime2();
        selectDateAndAmPm = new PopupSelectDateAndAmPm();
        DateTimePickerView dateTimePickerView = findViewById(R.id.datePickerView) ;
        dateTimePickerView.setStartDate(Calendar.getInstance());
// 注意：月份是从0开始计数的
        dateTimePickerView.setSelectedDate(new GregorianCalendar(2023, 6, 27, 21, 30));

        L.ee(GetMacUtil.getMacAddr());
        L.ee(GetMacUtil.getMacAddress(getApplicationContext()));
        L.ee(MacUtils.getMacAddress(getApplicationContext()));

    }

    public void buttonClick(View view) {
        switch (view.getId()){
            case R.id.button1:
                Intent intent = new Intent(this,PromptTestActivity.class);
                startActivity(intent);
                break;
            case R.id.button2:
                 intent = new Intent(this,SelectTestActivity.class);
                startActivity(intent);
                break;

            case R.id.button3:
//                intent = new Intent(this,PopupViewTestActivity.class);
//                startActivity(intent);

                ConvenientPopup.getInstance().loadDialog(MainActivity.this,"正在加载..." , true);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ConvenientPopup.getInstance().loadDialog(MainActivity.this,"正在加载..." , false);

                    }
                },5000);
                break;
            case R.id.button4:
                intent = new Intent(this,ConvenientPopupActivity.class);
                startActivity(intent);
                break;
            case R.id.button5:
                intent = new Intent(this,AutoWrapTestActivity.class);
                startActivity(intent);
                break;
            case R.id.LoadButton:
                LoadButton loadButton =(LoadButton)  view;
                loadButton.start(new LoadButton.OnLoadListener() {
                    @Override
                    public void onLoad() {
                        selectDateTime2.popupDateTime(getWindow(), getApplicationContext(), true,true, new PopupSelectDateTime2.OnClickListener() {
                            @Override
                            public void onTimeSet(String timeStr, int selectYear, int selectMonth, int selectDay, int selectHour, int selectMinute,int a) {
                                L.e(timeStr);
                                loadButton.re();
                            }

                            @Override
                            public void onCancelBack() {
                                startActivity(new Intent(MainActivity.this,WebActivity.class));
                                loadButton.re();
                            }
                        },0);



                    }
                });

                break;
            case R.id.test1:

                selectDateAndAmPm.popupDateTime(getWindow(), getApplicationContext(), new PopupSelectDateAndAmPm.OnClickListener() {
                    @Override
                    public void onTimeSet(String timeStr, int selectYear, int selectMonth, int selectDay, int selectHour, int selectMinute, String selectAmPm, int what) {
                        L.e(timeStr);
                    }

                    @Override
                    public void onCancelBack() {

                    }
                });


//                selectDateTime.popupDateTime(getWindow(), getApplicationContext(), true,false, new PopupSelectDateTime.OnClickListener() {
//                    @Override
//                    public void onTimeSet(String timeStr, int selectYear, int selectMonth, int selectDay, int selectHour, int selectMinute,int a) {
//                        L.e(timeStr);
//                    }
//
//                    @Override
//                    public void onCancelBack() {
//
//                    }
//                },0);



                break;
        }
    }


}
