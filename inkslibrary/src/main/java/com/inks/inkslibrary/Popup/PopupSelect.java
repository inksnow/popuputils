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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.inks.inkslibrary.R;
import com.inks.inkslibrary.Utils.GetResId;
import com.inks.inkslibrary.Utils.L;

import java.util.List;

public class PopupSelect {
    public interface onClickListener {
        public void onChooseBack(List<SelectListDataBean> selectListDataBeans, int what);

        public void onCancelBack(List<SelectListDataBean> selectListDataBeans, int what);
    }

    private View contentView = null;
    private PopupWindow pWindow;
    private Context context;
    private Window window;
    private LayoutInflater inflater;
    private SelectSettings selectSettings;
    private int what;

    private LinearLayout bgView;
    private LinearLayout titleView;
    private ImageView titleIcon;
    private TextView titleText;
    private View titleDivision;
    private ListView listView;
    private View buttonDivision1;
    private View buttonDivision2;
    private TextView button1, buttonTop1;
    private TextView button2, buttonTop2;

    private ListAdapter listAdapter;
    private List<SelectListDataBean> selectListDataBeans;


    @SuppressLint("ClickableViewAccessibility")
    public void popupSelect(Window window, Context context, LayoutInflater inflater, final SelectSettings selectSettings, final int what) {

        this.window = window;
        this.context = context;
        this.inflater = inflater;
        this.selectSettings = selectSettings;
        this.what = what;
        this.selectListDataBeans = selectSettings.getSelectListDataBean();
        if (selectListDataBeans != null) {
            if (!(pWindow != null && pWindow.isShowing())) {

                contentView = inflater.inflate(GetResId.getId(context, "layout", "popup_select"), null);
                bgView = contentView.findViewById(GetResId.getId(context, "id", "popup_select"));
                titleView = contentView.findViewById(GetResId.getId(context, "id", "popup_title"));
                titleIcon = contentView.findViewById(GetResId.getId(context, "id", "popup_title_icon"));
                titleText = contentView.findViewById(GetResId.getId(context, "id", "popup_title_text"));
                titleDivision = contentView.findViewById(GetResId.getId(context, "id", "popup_title_division"));
                listView = contentView.findViewById(GetResId.getId(context, "id", "popup_list"));
                buttonDivision1 = contentView.findViewById(GetResId.getId(context, "id", "popup_list_division"));
                buttonDivision2 = contentView.findViewById(GetResId.getId(context, "id", "popup_button_division"));
                button1 = contentView.findViewById(GetResId.getId(context, "id", "popup_button_1"));
                button2 = contentView.findViewById(GetResId.getId(context, "id", "popup_button_2"));

                buttonTop1 = contentView.findViewById(GetResId.getId(context, "id", "popup_button_top_1"));
                buttonTop2 = contentView.findViewById(GetResId.getId(context, "id", "popup_button_top_2"));
                initView();

                listAdapter = new ListAdapter(context, selectSettings, selectListDataBeans);
                listView.setAdapter(listAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        L.e("点击了" + position);

                        if (selectSettings.isMultipleSelection()) {
                            //多选
                            if (selectListDataBeans.get(position).isChoosed()) {
                                selectListDataBeans.get(position).setChoosed(false);
                            } else {
                                selectListDataBeans.get(position).setChoosed(true);
                            }
                            listAdapter.setData(selectListDataBeans);
                        } else {
                            //单选
                            for (int i = 0; i < selectListDataBeans.size(); i++) {
                                selectListDataBeans.get(i).setChoosed(false);
                            }
                            selectListDataBeans.get(position).setChoosed(true);
                            listAdapter.setData(selectListDataBeans);
                            if (!selectSettings.isShowButton2()) {
                                miss();
                                if (selectSettings.getClickListener() != null) {
                                    selectSettings.getClickListener().onChooseBack(selectListDataBeans, what);
                                }
                            }
                        }

                    }
                });
                button1.setTag(1);
                button2.setTag(2);
                button1.setOnTouchListener(touchListener);
                button2.setOnTouchListener(touchListener);
                button1.setOnClickListener(clickListener);
                button2.setOnClickListener(clickListener);

                buttonTop1.setTag(1);
                buttonTop2.setTag(2);
                buttonTop1.setOnTouchListener(touchListener);
                buttonTop2.setOnTouchListener(touchListener);
                buttonTop1.setOnClickListener(clickListener);
                buttonTop2.setOnClickListener(clickListener);


                int animationStyle = GetResId.getId(context, "style", "popup_fade_in_out");
                switch (selectSettings.getAnimation()) {
                    case popup_top_down:
                        animationStyle = GetResId.getId(context, "style", "popup_top_down");
                        break;
                    case popup_bottom_top:
                        animationStyle = GetResId.getId(context, "style", "popup_bottom_top");
                        break;
                    case popup_left_right:
                        animationStyle = GetResId.getId(context, "style", "popup_left_right");
                        break;
                    case popup_fade_in_out:
                        animationStyle = GetResId.getId(context, "style", "popup_fade_in_out");
                        break;
                }


                if (selectSettings.isAutoHeight()) {
                    View listItem = listAdapter.getView(0, null, listView);
                    listItem.measure(0, 0);
                    int relheight = listItem.getMeasuredHeight();

                    if (relheight * selectListDataBeans.size() > selectSettings.getPopupHeight()) {
                        pWindow = new PopupWindow(contentView, selectSettings.getPopupWidth(), selectSettings.getPopupHeight());
                    } else {
                        pWindow = new PopupWindow(contentView, selectSettings.getPopupWidth(), LinearLayout.LayoutParams.WRAP_CONTENT);
                    }

                } else {
                    pWindow = new PopupWindow(contentView, selectSettings.getPopupWidth(), selectSettings.getPopupHeight());

                }


                pWindow.setAnimationStyle(animationStyle);
                pWindow.setTouchable(selectSettings.isTouchable());
                pWindow.setFocusable(selectSettings.isFocusable());
                pWindow.setOutsideTouchable(selectSettings.isOutsideTouchable());
                pWindow.setInputMethodMode(selectSettings.getInputMethodMode());
                pWindow.setSoftInputMode(selectSettings.getSoftInputMode());
                pWindow.setBackgroundDrawable(new BitmapDrawable());
                pWindow.setClippingEnabled(selectSettings.isClippingEnabled());
                pWindow.showAtLocation(window.getDecorView(), selectSettings.getPopupGravity(), 0, 0);
                pWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        //backgroundAlpha(1f);

                    }
                });

            } else {
                L.e("已显示");
            }
        } else {
            L.e("数据为空");
        }

    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            miss();

            if (selectSettings.getClickListener() != null) {
                switch ((int) v.getTag()) {
                    case 1:
                        L.e("点击了取消");
                        if (selectSettings.getClickListener() != null) {
                            selectSettings.getClickListener().onCancelBack(selectListDataBeans, what);
                        }
                        break;
                    case 2:
                        L.e("点击了确认");
                        if (selectSettings.getClickListener() != null) {
                            selectSettings.getClickListener().onChooseBack(selectListDataBeans, what);
                        }
                        break;
                }
            }
        }
    };

    View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch ((int) v.getTag()) {
                case 1:
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        button1.setTextColor(selectSettings.getButtonTextColor1() - 0Xaa000000);
                    } else if ((event.getAction() == MotionEvent.ACTION_UP) || (event.getAction() == MotionEvent.ACTION_OUTSIDE)) {
                        button1.setTextColor(selectSettings.getButtonTextColor1());
                    }
                    break;
                case 2:
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        button2.setTextColor(selectSettings.getButtonTextColor2() - 0Xaa000000);
                    } else if ((event.getAction() == MotionEvent.ACTION_UP) || (event.getAction() == MotionEvent.ACTION_OUTSIDE)) {
                        button2.setTextColor(selectSettings.getButtonTextColor2());
                    }
                    break;
            }
            return false;
        }
    };


    @SuppressLint("WrongConstant")
    private void initView() {

        //backgroundAlpha(selectSettings.getBgAlpha());
        View rootView = contentView.findViewById(R.id.root_layout);
        rootView.getBackground().mutate().setAlpha((int) (0.5 * 255));

        //背景色及圆角
        GradientDrawable drawable = new GradientDrawable();
        //形状（矩形）
        drawable.setShape(GradientDrawable.RECTANGLE);
        //渐变样式
        drawable.setGradientType(GradientDrawable.RECTANGLE);
        //渐变方向（左到右）
        drawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
        //圆角
        drawable.setCornerRadii(selectSettings.getPopupRadius());
        //颜色
        drawable.setColors(selectSettings.getPopupBg());
        bgView.setBackground(drawable);

        if (selectSettings.isShowTitle()) {
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
            drawable.setCornerRadii(new float[]{selectSettings.getPopupRadius()[0],
                    selectSettings.getPopupRadius()[1], selectSettings.getPopupRadius()[2],
                    selectSettings.getPopupRadius()[3], 0, 0, 0, 0});
            //颜色
            drawable.setColors(selectSettings.getTitleBg());
            titleView.setBackground(drawable);

            if (selectSettings.isShowTitleIcon() && selectSettings.getTitleIcon() != null) {
                titleIcon.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) titleIcon.getLayoutParams();
                linearParams.width = selectSettings.getTitleIconWidth();
                linearParams.height = selectSettings.getTitleIconHeight();
                titleIcon.setLayoutParams(linearParams);
                titleIcon.setPadding(selectSettings.getTitleIconPaddings()[0],
                        selectSettings.getTitleIconPaddings()[1],
                        selectSettings.getTitleIconPaddings()[2],
                        selectSettings.getTitleIconPaddings()[3]);

                titleIcon.setImageDrawable(selectSettings.getTitleIcon());
            } else {
                titleIcon.setVisibility(View.GONE);
            }

            if (selectSettings.isShowTitleText()) {
                titleText.setVisibility(View.VISIBLE);
                titleText.setTextSize(selectSettings.getTitleTextSize());
                titleText.setTextColor(selectSettings.getTitleTextColor());
                titleText.setText(selectSettings.getTitleTextStr());
                titleText.setPadding(selectSettings.getTitleTextPaddings()[0],
                        selectSettings.getTitleTextPaddings()[1],
                        selectSettings.getTitleTextPaddings()[2],
                        selectSettings.getTitleTextPaddings()[3]);
                titleText.setGravity(selectSettings.getTitleTextGravity());
            } else {
                titleText.setVisibility(View.GONE);
            }
            titleDivision.setVisibility(View.VISIBLE);
            titleDivision.setBackgroundColor(selectSettings.getTitleDividingColor());
        } else {
            titleView.setVisibility(View.GONE);
            titleDivision.setVisibility(View.GONE);
        }

        listView.setPadding(selectSettings.getListPaddings()[0],
                selectSettings.getListPaddings()[1],
                selectSettings.getListPaddings()[2],
                selectSettings.getListPaddings()[3]);
        listView.setVerticalScrollBarEnabled(selectSettings.isScrollBarEnabled());
        listView.setScrollBarFadeDuration(selectSettings.getScrollBarFadeDuration());
        listView.setScrollBarSize(selectSettings.getScrollBarSize());
        listView.setScrollBarStyle(selectSettings.getScrollBarStyle());
        listView.setDivider(selectSettings.getListDivider());
        listView.setDividerHeight(selectSettings.getListDividerHeight());

        if (selectSettings.getPopupGravity() == Gravity.BOTTOM) {
            //底部弹窗，取消确定显示在上边
            button1.setVisibility(View.GONE);
            button2.setVisibility(View.GONE);
            buttonDivision1.setVisibility(View.GONE);
            buttonDivision2.setVisibility(View.GONE);
            if (selectSettings.isShowButton1()) {
                buttonTop1.setVisibility(View.VISIBLE);
                buttonTop1.setText(selectSettings.getButtonTextStr1());
                buttonTop1.setTextSize(selectSettings.getButtonTextSize());
                buttonTop1.setTextColor(selectSettings.getButtonTextColor1());
                buttonTop1.setPadding(selectSettings.getButtonPaddings()[0],
                        selectSettings.getButtonPaddings()[1],
                        selectSettings.getButtonPaddings()[2],
                        selectSettings.getButtonPaddings()[3]);
            } else {
                if (selectSettings.isShowButton2()) {
                    buttonTop1.setVisibility(View.INVISIBLE);

                } else {
                    buttonTop1.setVisibility(View.GONE);

                }
            }

            if (selectSettings.isShowButton2()) {
                buttonTop2.setVisibility(View.VISIBLE);
                buttonTop2.setText(selectSettings.getButtonTextStr2());
                buttonTop2.setTextSize(selectSettings.getButtonTextSize());
                buttonTop2.setTextColor(selectSettings.getButtonTextColor2());
                buttonTop2.setPadding(selectSettings.getButtonPaddings()[0],
                        selectSettings.getButtonPaddings()[1],
                        selectSettings.getButtonPaddings()[2],
                        selectSettings.getButtonPaddings()[3]);
            } else {
                if (selectSettings.isShowButton1()) {
                    buttonTop2.setVisibility(View.INVISIBLE);

                } else {
                    buttonTop2.setVisibility(View.GONE);

                }
            }


        } else {

            if (selectSettings.isShowButton1() || selectSettings.isShowButton2()) {
                buttonDivision1.setVisibility(View.VISIBLE);
                buttonDivision1.setBackgroundColor(selectSettings.getButtonDividingColor());
            } else {
                buttonDivision1.setVisibility(View.GONE);
            }
            if (selectSettings.isShowButton1() && selectSettings.isShowButton2()) {
                buttonDivision2.setVisibility(View.VISIBLE);
                buttonDivision2.setBackgroundColor(selectSettings.getButtonDividingColor());
            } else {
                buttonDivision2.setVisibility(View.GONE);
            }


            buttonTop1.setVisibility(View.GONE);
            buttonTop2.setVisibility(View.GONE);

            if (selectSettings.isShowButton1()) {
                button1.setVisibility(View.VISIBLE);
                button1.setText(selectSettings.getButtonTextStr1());
                button1.setTextSize(selectSettings.getButtonTextSize());
                button1.setTextColor(selectSettings.getButtonTextColor1());
                button1.setPadding(selectSettings.getButtonPaddings()[0],
                        selectSettings.getButtonPaddings()[1],
                        selectSettings.getButtonPaddings()[2],
                        selectSettings.getButtonPaddings()[3]);
            } else {
                button1.setVisibility(View.GONE);
            }

            if (selectSettings.isShowButton2()) {
                button2.setVisibility(View.VISIBLE);
                button2.setText(selectSettings.getButtonTextStr2());
                button2.setTextSize(selectSettings.getButtonTextSize());
                button2.setTextColor(selectSettings.getButtonTextColor2());
                button2.setPadding(selectSettings.getButtonPaddings()[0],
                        selectSettings.getButtonPaddings()[1],
                        selectSettings.getButtonPaddings()[2],
                        selectSettings.getButtonPaddings()[3]);
            } else {
                button2.setVisibility(View.GONE);
            }

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

    public PopupWindow getpWindow() {
        return pWindow;
    }

}
