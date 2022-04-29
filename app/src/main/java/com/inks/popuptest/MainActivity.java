package com.inks.popuptest;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.inks.inkslibrary.Popup.PopupSelectDateTime;
import com.inks.inkslibrary.Utils.L;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.inks.inkslibrary.Utils.DensityUtils.dp2px;

public class MainActivity extends AppCompatActivity {
    PopupSelectDateTime selectDateTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        selectDateTime = new PopupSelectDateTime();

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
                intent = new Intent(this,PopupViewTestActivity.class);
                startActivity(intent);
                
                break;
            case R.id.LoadButton:
                LoadButton loadButton =(LoadButton)  view;
                loadButton.start(new LoadButton.OnLoadListener() {
                    @Override
                    public void onLoad() {
                        L.e("onload");
                    }
                });

                break;
            case R.id.test1:

                selectDateTime.popupDateTime(getWindow(), getApplicationContext(), true,false, new PopupSelectDateTime.OnClickListener() {
                    @Override
                    public void onTimeSet(String timeStr, int selectYear, int selectMonth, int selectDay, int selectHour, int selectMinute) {
                        L.e(timeStr);
                    }

                    @Override
                    public void onCancelBack() {

                    }
                });



                break;
        }
    }

}
