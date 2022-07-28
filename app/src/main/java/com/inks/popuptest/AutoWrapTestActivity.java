package com.inks.popuptest;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.inks.inkslibrary.bean.AutoWrapItemBean;
import com.inks.inkslibrary.view.AutoWrapGroup;

import java.util.ArrayList;
import java.util.List;

public class AutoWrapTestActivity extends AppCompatActivity {

    private AutoWrapGroup autoWrapGroup;
    ArrayList<AutoWrapItemBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_wrap_test);
        autoWrapGroup = findViewById(R.id.AutoWrapGroup);

        AutoWrapItemBean bean1 = new AutoWrapItemBean();
        bean1.setText("艾迪康叫号机");
        list.add(bean1);

        AutoWrapItemBean bean2 = new AutoWrapItemBean();
        bean2.setText("管理站");
        bean2.setSelect(true);
        list.add(bean2);

        AutoWrapItemBean bean3 = new AutoWrapItemBean();
        bean3.setText("暗示法咖啡和康师傅康师傅");
        list.add(bean3);

        AutoWrapItemBean bean4 = new AutoWrapItemBean();
        bean4.setText("沙发斯蒂芬");
        list.add(bean4);

        AutoWrapItemBean bean5 = new AutoWrapItemBean();
        bean5.setText("情况无人坡起王培荣空气炮无人区人员");
        list.add(bean5);

        AutoWrapItemBean bean6 = new AutoWrapItemBean();
        bean6.setText("胜多负少的");
        list.add(bean6);

        autoWrapGroup.setTexts(list);
        autoWrapGroup.setAutoWrapGroupClick(new AutoWrapGroup.AutoWrapGroupClick() {
            @Override
            public void onItemClick(int index) {
                list.get(index).setSelect( !list.get(index).isSelect());
                autoWrapGroup.setTexts(list);
            }

            @Override
            public void onDeleteClick(int index) {

            }
        });

    }
}