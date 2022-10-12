package com.inks.inkslibrary.Popup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inks.inkslibrary.R;
import com.inks.inkslibrary.view.ClickEffectText;
import com.inks.inkslibrary.view.PickerView;

import java.util.ArrayList;
import java.util.Calendar;


public class PopupSelectDateTime2 {
    public interface OnClickListener {
        public void onTimeSet(String timeStr, int selectYear, int selectMonth, int selectDay, int selectHour, int selectMinute,int what);

        public void onCancelBack();

    }

    private OnClickListener mOnClickListener;
    private View contentView = null;
    private PopupWindow pWindow;
    private Context context;
    private Window window;
    private SelectDateTimeSettings settings;

    private ClickEffectText buttonDone;
    private ClickEffectText buttonCancel;
    private TextView textViewTime;
    //上下100年
    private int leftYear = 100;
    private int rightYear = 100;
    private boolean showYearMonthDay;
    private boolean showHourMinute;
    private boolean onlyYear = false;

    private int what;

    private RelativeLayout  rootView;
    //选择
    private int selectYear, selectMonth, selectDay, selectHour, selectMinute;

    private ArrayList<String> yearLists = new ArrayList<>();
    private ArrayList<String> monthLists = new ArrayList<>();
    private ArrayList<String> dayLists = new ArrayList<>();
    private ArrayList<String> hourLists = new ArrayList<>();
    private ArrayList<String> minuteLists = new ArrayList<>();


    private PickerView yearPicker, monthPicker, dayPicker, hourPicker, minutePicker;


    private void initLists() {

        yearLists.clear();
        for (int i = (selectYear - leftYear); i < (selectYear + rightYear); i++) {
            yearLists.add(String.format("%04d", i));
        }

        monthLists.clear();
        for (int i = 1; i < 13; i++) {
            monthLists.add(String.format("%02d", i));
        }
        hourLists.clear();
        for (int i = 0; i < 24; i++) {
            hourLists.add(String.format("%02d", i));
        }

        minuteLists.clear();
        for (int i = 0; i < 60; i++) {
            minuteLists.add(String.format("%02d", i));
        }
        initDayList();
    }


    private void initDayList() {
        int dayNumber = getDayOfMonth(selectYear, selectMonth);
        dayLists.clear();
        for (int i = 1; i <= dayNumber; i++) {
            dayLists.add(String.format("%02d", i));
        }

        if (selectDay > dayNumber) {
            selectDay = dayNumber;
        }
        if (dayPicker != null) {
            dayPicker.setLists(dayLists);
            dayPicker.setSelectStr(String.format("%02d", selectDay));
        }

    }


    private void initSelectWithNowTime() {
        Calendar now = Calendar.getInstance();
        selectYear = now.get(Calendar.YEAR);
        selectMonth = (now.get(Calendar.MONTH) + 1);
        selectDay = now.get(Calendar.DAY_OF_MONTH);
        selectHour = now.get(Calendar.HOUR_OF_DAY);
        selectMinute = now.get(Calendar.MINUTE);


    }


    public void setOnClickListener(OnClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    public void popupDateTime(Window window, Context context) {
        popupDateTime(window, context, null, null,0);
    }

    public void popupDateTime(Window window, Context context, final SelectDateTimeSettings settings) {
        popupDateTime(window, context, settings, null,0);
    }

    public void popupDateTime(Window window, Context context, OnClickListener mOnClickListener) {
        popupDateTime(window, context, null, mOnClickListener,0);
    }

    public void popupDateTime(Window window, Context context, boolean showYearMonthDay, boolean showHourMinute, OnClickListener mOnClickListener,int what) {
        initSelectWithNowTime();
        popupDateTime(window, context, null, showYearMonthDay, showHourMinute, mOnClickListener, selectYear, selectMonth, selectDay, selectHour, selectMinute,what);
    }

    public void popupDateTime(Window window, Context context, boolean showYearMonthDay, boolean showHourMinute, OnClickListener mOnClickListener,int what,boolean onlyYear) {
        initSelectWithNowTime();
        popupDateTime(window, context, null, showYearMonthDay, showHourMinute, mOnClickListener, selectYear, selectMonth, selectDay, selectHour, selectMinute,what,0.5f,onlyYear);


    }


    public void popupDateTime(Window window, Context context, boolean showYearMonthDay, boolean showHourMinute, OnClickListener mOnClickListener, int what, float alpha) {
        initSelectWithNowTime();

        popupDateTime(window,context,null,showYearMonthDay,showHourMinute,mOnClickListener,selectYear,selectMonth,selectDay,selectHour,selectMinute,what,alpha);
    }



    public void popupDateTime(Window window, Context context, boolean showYearMonthDay, boolean showHourMinute, OnClickListener mOnClickListener, int what, float alpha,boolean onlyYear) {
        initSelectWithNowTime();

        popupDateTime(window,context,null,showYearMonthDay,showHourMinute,mOnClickListener,selectYear,selectMonth,selectDay,selectHour,selectMinute,what,alpha,onlyYear);
    }



    public void popupDateTime(Window window, Context context, final SelectDateTimeSettings settings, OnClickListener mOnClickListener,int what) {
        initSelectWithNowTime();
        popupDateTime(window, context, settings, true, true, mOnClickListener, selectYear, selectMonth, selectDay, selectHour, selectMinute,what);
    }

    public void popupDateTime(Window window, Context context, final SelectDateTimeSettings settings, boolean showYearMonthDay, boolean showHourMinute, OnClickListener mOnClickListener,
                              int selectYear, int selectMonth, int selectDay, int selectHour, int selectMinute,int what) {
        popupDateTime(window, context, settings, showYearMonthDay, showHourMinute, mOnClickListener, selectYear, selectMonth, selectDay, selectHour, selectMinute, what,0.5f);
    }


    public void popupDateTime(Window window, Context context, final SelectDateTimeSettings settings, boolean showYearMonthDay, boolean showHourMinute, OnClickListener mOnClickListener,
                              int selectYear, int selectMonth, int selectDay, int selectHour, int selectMinute,int what,float alpha) {

        popupDateTime(window, context, settings, showYearMonthDay, showHourMinute, mOnClickListener, selectYear, selectMonth, selectDay, selectHour, selectMinute, what, alpha,false);
    }


    @SuppressLint("ClickableViewAccessibility")
    public void popupDateTime(Window window, Context context, final SelectDateTimeSettings settings, boolean showYearMonthDay, boolean showHourMinute, OnClickListener mOnClickListener,
                              int selectYear, int selectMonth, int selectDay, int selectHour, int selectMinute,int what,float alpha,boolean onlyYear) {

        initLists();
        this.window = window;
        this.context = context;
        this.settings = settings;
        this.mOnClickListener = mOnClickListener;
        this.showYearMonthDay = showYearMonthDay;
        this.showHourMinute = showHourMinute;
        this.what = what;
        this.onlyYear = onlyYear;

        if (!(pWindow != null && pWindow.isShowing())) {
            contentView = LayoutInflater.from(context).inflate(R.layout.popup_select_date_time_2, null);
            pWindow = new PopupWindow(contentView, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);

            rootView= contentView.findViewById(R.id.root_layout);
            rootView.getBackground().mutate().setAlpha((int) (alpha*255));

            textViewTime = contentView.findViewById(R.id.tv_time);
            setText();

            yearPicker = contentView.findViewById(R.id.pv_year);
            yearPicker.setLists(yearLists);
            yearPicker.setSelectStr(String.format("%04d", selectYear));

            monthPicker = contentView.findViewById(R.id.pv_month);
            monthPicker.setLists(monthLists);
            monthPicker.setSelectStr(String.format("%02d", selectMonth));

            dayPicker = contentView.findViewById(R.id.pv_day);
            dayPicker.setLists(dayLists);
            dayPicker.setSelectStr(String.format("%02d", selectDay));

            hourPicker = contentView.findViewById(R.id.pv_hour);
            hourPicker.setLists(hourLists);
            hourPicker.setSelectStr(String.format("%02d", selectHour));

            minutePicker = contentView.findViewById(R.id.pv_minute);
            minutePicker.setLists(minuteLists);
            minutePicker.setSelectStr(String.format("%02d", selectMinute));
            LinearLayout layoutYear = contentView.findViewById(R.id.layout_y_m_d);

            LinearLayout layoutHour = contentView.findViewById(R.id.layout_h_m);
            if (!showYearMonthDay) {
                layoutYear.setVisibility(View.GONE);
            }
            if (!showHourMinute) {
                layoutHour.setVisibility(View.GONE);
            }

            if(onlyYear){
                monthPicker.setVisibility(View.GONE);
                dayPicker.setVisibility(View.GONE);
                layoutHour.setVisibility(View.GONE);
            }

            buttonDone = contentView.findViewById(R.id.buttonDone);
            buttonCancel = contentView.findViewById(R.id.buttonCancel);

            buttonDone.setOnClickListener(clickListener);
            buttonCancel.setOnClickListener(clickListener);

            yearPicker.setOnSelectedItemChangedListener(pickerSelectListener);
            monthPicker.setOnSelectedItemChangedListener(pickerSelectListener);
            dayPicker.setOnSelectedItemChangedListener(pickerSelectListener);
            hourPicker.setOnSelectedItemChangedListener(pickerSelectListener);
            minutePicker.setOnSelectedItemChangedListener(pickerSelectListener);

            pWindow.setAnimationStyle(R.style.popup_bottom_top);
            pWindow.setTouchable(true);
            pWindow.setFocusable(true);
            pWindow.setOutsideTouchable(true);
            pWindow.setBackgroundDrawable(new BitmapDrawable());
            pWindow.setClippingEnabled(true);
            //backgroundAlpha(alpha);
            pWindow.showAtLocation(window.getDecorView(), Gravity.BOTTOM, 0, 0);
            pWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    //backgroundAlpha(1f);

                }
            });

        } else {

        }

    }


    private void setText() {

        if(onlyYear){
            textViewTime.setText(String.format("%04d", selectYear));
            return;
        }

        if (showYearMonthDay && showHourMinute) {
            textViewTime.setText(String.format("%04d", selectYear) + "-"
                    + String.format("%02d", selectMonth) + "-"
                    + String.format("%02d", selectDay) + " "
                    + String.format("%02d", selectHour) + ":"
                    + String.format("%02d", selectMinute));
        } else if (showYearMonthDay) {
            textViewTime.setText(String.format("%04d", selectYear) + "-"
                    + String.format("%02d", selectMonth) + "-"
                    + String.format("%02d", selectDay));
        } else if (showHourMinute) {
            textViewTime.setText(String.format("%02d", selectHour) + ":"
                    + String.format("%02d", selectMinute));
        }

    }

    PickerView.OnSelectedItemChangedListener pickerSelectListener = new PickerView.OnSelectedItemChangedListener() {
        @Override
        public void onSelectedItemChanged(PickerView view, int previousPosition, int selectedItemPosition, String indexString) {
            if (view.getId() == R.id.pv_year) {
                selectYear = Integer.valueOf(indexString);
                if (selectMonth == 2) {
                    initDayList();
                }
            } else if (view.getId() == R.id.pv_month) {
                selectMonth = Integer.valueOf(indexString);
                initDayList();
            } else if (view.getId() == R.id.pv_day) {
                selectDay = Integer.valueOf(indexString);
            } else if (view.getId() == R.id.pv_hour) {
                selectHour = Integer.valueOf(indexString);
            } else if (view.getId() == R.id.pv_minute) {
                selectMinute = Integer.valueOf(indexString);
            }
            setText();
        }
    };




    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            miss();
            if (v.getId() == R.id.buttonDone) {
                if (mOnClickListener != null) {
                    mOnClickListener.onTimeSet(textViewTime.getText().toString(), selectYear, selectMonth, selectDay, selectHour, selectMinute,what);
                }
            } else if (v.getId() == R.id.buttonCancel) {
                if (mOnClickListener != null) {
                    mOnClickListener.onCancelBack();
                }
            }
        }
    };


    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = bgAlpha; // 0.0-1.0
        window.setAttributes(lp);
    }


    public void miss() {
        if (pWindow != null && pWindow.isShowing()) {
            pWindow.dismiss();
        }
    }

    public PopupWindow getPopupWindow() {
        return pWindow;
    }


    /**
     * 获取某年某月有多少天
     *
     * @param year
     * @param month
     * @return
     */
    public int getDayOfMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, 0); //输入类型为int类型
        return c.get(Calendar.DAY_OF_MONTH);
    }
}
