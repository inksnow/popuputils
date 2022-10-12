package com.inks.inkslibrary.Popup;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.inks.inkslibrary.R;
import com.inks.inkslibrary.Utils.DensityUtils;
import com.inks.inkslibrary.Utils.GetIpAndDisplayUtil;
import com.inks.inkslibrary.Utils.L;
import com.inks.inkslibrary.view.MaxHeightScrollView;

import java.util.List;

/**
 * ProjectName:    PopupTest
 * Package:        com.inks.inkslibrary.Popup
 * ClassName:      ConvenientPopup
 * Description:     java类作用描述
 * Author:         inks
 * CreateDate:     2022/6/13 13:50
 */
public class ConvenientPopup {

    private PopupSelect2 popupSelect;
    private PopupView popupView;

    private PopupSelectDateAndAmPm popupSelectDateAndAmPm;
    private PopupSelectDateTime popupSelectDateTime;
    private PopupSelectDateTime2 popupSelectDateTime2;

    private LoadDialogUtils loadDialogUtils;

    private static volatile ConvenientPopup mInstance;

    private Activity mActivity;

    private ConvenientPopup() {
        if (popupSelect == null) {
            popupSelect = new PopupSelect2();
        }
        if (popupView == null) {
            popupView = new PopupView();
        }

        if (popupSelectDateAndAmPm == null) {
            popupSelectDateAndAmPm = new PopupSelectDateAndAmPm();
        }

        if (popupSelectDateTime == null) {
            popupSelectDateTime = new PopupSelectDateTime();
        }

        if (popupSelectDateTime2 == null) {
            popupSelectDateTime2 = new PopupSelectDateTime2();
        }
    }


    public static ConvenientPopup getInstance() {
        if (mInstance == null) {
            synchronized (ConvenientPopup.class) {
                if (mInstance == null) {
                    mInstance = new ConvenientPopup();
                }
            }
        }
        return mInstance;
    }


public void loadDialog(Activity activity,String str,boolean show){
        if(mActivity!=null && activity.getClass().equals(mActivity.getClass())){
            if(loadDialogUtils==null){
                loadDialogUtils = new LoadDialogUtils(activity);
            }
        }else{
            loadDialogUtils = new LoadDialogUtils(activity);
        }
        if(show){
            loadDialogUtils.upText(str);
        }else{
            loadDialogUtils.closeDialog();
        }

    mActivity = activity;

}

    /**
     * @param activity activity
     * @param s        消息类容
     * @throws
     * @Description: 消息提示弹窗，只显示消息类容和确认按钮，无点击监听
     * @return:
     * @date: 2022/7/26
     */
    public void showPopupMsg(Activity activity, String s) {
        showPopupMsg(activity.getApplicationContext(), activity.getWindow(), activity.getLayoutInflater(), s,
                null, false, "取消", "确认", 0XFF03a9f4, 0XFF03a9f4, true, true, false, 0, 0, null);
    }


    /**
     * @param activity      activity
     * @param s             消息类容
     * @param title         标题
     * @param popupMsgClick 监听
     * @param msgCenter     类容是否居中
     * @throws
     * @Description: 消息提示弹窗，只显示消息类容和确认按钮，无点击监听
     * @return:
     * @date: 2022/7/26
     */
    public void showPopupMsg(Activity activity, String s, boolean msgCenter, String title, boolean showExit, PopupView.onClickListener popupMsgClick) {
        showPopupMsg(activity.getApplicationContext(), activity.getWindow(), activity.getLayoutInflater(), s,
                title, showExit, "取消", "确认", 0XFF03a9f4, 0XFF03a9f4, msgCenter, true, false, 0, 0, popupMsgClick);
    }

    /**
     * @param activity      activity
     * @param s             消息类容
     * @param title         标题
     * @param popupMsgClick 监听
     * @param focusable     是否点击外边取消弹窗
     * @param what          tag,监听会返回
     * @throws
     * @Description: 消息提示弹窗，只显示消息类容和确认按钮，无点击监听
     * @return:
     * @date: 2022/7/26
     */
    public void showPopupMsg(Activity activity, String s, String title, boolean showExit, PopupView.onClickListener popupMsgClick, boolean focusable, int what) {
        showPopupMsg(activity.getApplicationContext(), activity.getWindow(), activity.getLayoutInflater(), s,
                title, showExit, "取消", "确认", 0XFF03a9f4, 0XFF03a9f4, true, true, focusable, 0, what, popupMsgClick);
    }


    /**
     * @param context        context
     * @param window         window
     * @param layoutInflater layoutInflater
     * @param s              类容文字
     * @param title          标题，为空则不显示
     * @param showExit       是否显示退出按钮，左边的按钮
     * @param exitText       退出按钮文字
     * @param doneText       确认按钮文字
     * @param exitColor      退出按钮颜色
     * @param doneColor      确认按钮颜色
     * @param msgCenter      类容是否居中
     * @param titleCenter    标题是否居中
     * @param focusable      是否点击外边取消弹窗
     * @param msgMaxHeight   类容最大高度
     * @param what           tag,监听会返回
     * @param popupMsgClick  点击监听
     */
    public void showPopupMsg(Context context, Window window, LayoutInflater layoutInflater, String s, String title, boolean showExit,
                             String exitText, String doneText, int exitColor, int doneColor, boolean msgCenter, boolean titleCenter,
                             boolean focusable, int msgMaxHeight, int what, PopupView.onClickListener popupMsgClick) {

        if (popupView.getpWindow() != null && popupView.getpWindow().isShowing()) {
            popupView.miss();
        }
        View v = LayoutInflater.from(context).inflate(R.layout.popup_convenient_msg_view, null, false);
        MaxHeightScrollView scrollView = v.findViewById(R.id.scroll_view);
        scrollView.setMaxHeight(msgMaxHeight == 0 ? DensityUtils.dp2px(context, 420) : msgMaxHeight);
        TextView textView = v.findViewById(R.id.text_view);
        textView.setText(s);
        if (msgCenter) {
            textView.setGravity(Gravity.CENTER);
        } else {
            textView.setGravity(Gravity.CENTER_VERTICAL);
        }

        ViewSettings.Builder builder = new ViewSettings.Builder();
        ViewSettings promptSettings =
                builder.clickListener(popupMsgClick).popupWidth(DensityUtils.dp2px(context, 310))
                        // .popupHeight(DensityUtil.dp2px(getContext(), 160))
                        .popupHeight(LinearLayout.LayoutParams.WRAP_CONTENT)
                        .buttonPaddings(new int[]{DensityUtils.dp2px(context, 10), DensityUtils.dp2px(context, 10), DensityUtils.dp2px(context, 10), DensityUtils.dp2px(context, 10)})
                        .showTitle(!TextUtils.isEmpty(title))
                        .showTitleIcon(false)
                        .titleTextSize((int) DensityUtils.dp2sp(context, 18))
                        .titleTextStr(title)
                        .titleTextGravity(titleCenter ? Gravity.CENTER : Gravity.LEFT | Gravity.CENTER_VERTICAL)
                        .titleTextPaddings(new int[]{0, DensityUtils.dp2px(context, 10), 0, DensityUtils.dp2px(context, 10)})
                        .showButton1(showExit)
                        .buttonTextStr1(exitText)
                        .buttonTextStr2(doneText)
                        .buttonTextColor1(exitColor)
                        .buttonTextColor2(doneColor)
                        .buttonTextSize((int) DensityUtils.dp2sp(context, 16))
                        .showTitleIcon(false)
                        .focusable(focusable)
                        .outsideTouchable(false)
                        .titleDividingColor(0XF0F8F8F8)
                        .buttonDividingColor(0XF0F8F8F8)
                        .build();
        popupView.popupView(window, context, layoutInflater, v, promptSettings, what);

    }


    public void showSelectBottom(Activity activity, String title, boolean multipleSelection, List<SelectListDataBean> selectListDataBeans, PopupSelect.onClickListener selectBackListener) {
        showSelectBottom(activity, title, multipleSelection, selectListDataBeans, selectBackListener, 0);
    }

    public void showSelectBottom(Activity activity, String title, boolean multipleSelection, List<SelectListDataBean> selectListDataBeans, PopupSelect.onClickListener selectBackListener, int what) {

        if (multipleSelection) {
            if (TextUtils.isEmpty(title)) {
                title = " ";
            }
            showSelectBottom(activity.getApplicationContext(), activity.getWindow(), activity.getLayoutInflater(), title,
                    "取消", "确认", true, true, 0XFF03a9f4, 0XFF03a9f4, multipleSelection, true, selectListDataBeans, selectBackListener, what);

        } else {
            showSelectBottom(activity.getApplicationContext(), activity.getWindow(), activity.getLayoutInflater(), title,
                    "取消", "确认", false, false, 0XFF03a9f4, 0XFF03a9f4, multipleSelection, true, selectListDataBeans, selectBackListener, what);

        }

    }


    /**
     * @param context             context
     * @param window              window
     * @param layoutInflater      layoutInflater
     * @param title               标题，为空不显示
     * @param exitText            取消文字
     * @param doneText            确认文字
     * @param showExit            是否显示取消
     * @param showDone            是否显示确认
     * @param exitColor           取消文字颜色
     * @param doneColor           确认文字颜色
     * @param multipleSelection   是否多选
     * @param focusable           点击外部是否可取消
     * @param selectListDataBeans selectListDataBeans
     * @param selectBackListener  监听
     * @param what                tag
     */
    public void showSelectBottom(Context context, Window window, LayoutInflater layoutInflater,
                                 String title, String exitText, String doneText,
                                 boolean showExit, boolean showDone,
                                 int exitColor, int doneColor, boolean multipleSelection, boolean focusable,
                                 List<SelectListDataBean> selectListDataBeans, PopupSelect.onClickListener selectBackListener, int what) {

        if (popupSelect.getpWindow() != null && popupSelect.getpWindow().isShowing()) {
            popupSelect.miss();
        }


        GradientDrawable drawable = new GradientDrawable();
        //形状（矩形）
        drawable.setShape(GradientDrawable.RECTANGLE);
        //渐变方向（左到右）
        drawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
        //颜色
        drawable.setColors(new int[]{0XF0F8F8F8, 0XF0F8F8F8, 0XF0F8F8F8});
        int displayHeight = GetIpAndDisplayUtil.getDisplayHeight(window);
        int dp10 = DensityUtils.dp2px(context, 10);
        int dp5 = DensityUtils.dp2px(context, 5);
        int dp8 = DensityUtils.dp2px(context, 8);
        SelectSettings.Builder builder = new SelectSettings.Builder();
        SelectSettings promptSettings =
                builder.selectListDataBean(selectListDataBeans)
                        .clickListener(selectBackListener)
                        .popupWidth(-1)
                        .multipleSelection(multipleSelection)
                        .titleTextStr(title)
                        .showTitle(!TextUtils.isEmpty(title))
                        .popupHeight((int) (displayHeight * 0.5))
                        .animation(SelectSettings.PopupAnimation.popup_bottom_top)
                        .popupGravity(Gravity.BOTTOM)
                        .selectImagePosition(SelectSettings.SelectImagePosition.RIGHT)
                        .titleTextGravity(Gravity.CENTER)
                        .titleTextPaddings(new int[]{0, dp5 * 2, 0, dp5 * 2})
                        .popupRadius(new float[]{dp5, dp5, dp5, dp5, 0, 0, 0, 0})
                        .buttonPaddings(new int[]{0, dp5 * 3, 0, dp5 * 3})
                        .buttonTextStr1(exitText)
                        .buttonTextStr2(doneText)
                        .showButton1(showExit)
                        .showButton2(showDone)
                        .buttonTextColor1(exitColor)
                        .buttonTextColor2(doneColor)
                        .titleDividingColor(0X88AAAAAA)
                        .listDividerHeight(2)
                        .listDivider(drawable)
                        .listTextGravity(Gravity.CENTER)
                        .listTextPaddings(new int[]{dp10, 0, dp10, 0})
                        .listLayoutPadding(new int[]{0, dp5, 0, dp5})
                        .listPaddings(new int[]{dp10 * 2, dp10, dp10 * 2, dp10 * 2})
                        .listSelectImageOff(ContextCompat.getDrawable(context, R.drawable.select_null))
                        .listSelectImageOn(ContextCompat.getDrawable(context, R.drawable.select_1))
                        .listSelectImagePaddings(new int[]{dp5, dp5, dp5, dp5})
                        .clippingEnabled(true)
                        .titleDividingColor(0XF0F8F8F8)
                        .buttonDividingColor(0XF0F8F8F8)
                        .focusable(focusable)
                      //  .bgAlpha(1)
                        .build();
        popupSelect.popupSelect(window, context, layoutInflater, promptSettings, what);

    }


    public void showSelectCenter(Activity activity, String title, boolean multipleSelection, List<SelectListDataBean> selectListDataBeans, PopupSelect.onClickListener selectBackListener) {
        showSelectCenter(activity, title, multipleSelection, selectListDataBeans, selectBackListener, 0);
    }

    public void showSelectCenter(Activity activity, String title, boolean multipleSelection, List<SelectListDataBean> selectListDataBeans, PopupSelect.onClickListener selectBackListener, int what) {

        showSelectCenter(activity, title, multipleSelection, selectListDataBeans, Gravity.CENTER_VERTICAL, selectBackListener, what);

    }

    public void showSelectCenter(Activity activity, String title, boolean multipleSelection, List<SelectListDataBean> selectListDataBeans, int listTextGravity, PopupSelect.onClickListener selectBackListener, int what) {
        int displayWidth = GetIpAndDisplayUtil.getDisplayWidth(activity);

        if (multipleSelection) {
            showSelectCenter(activity.getApplicationContext(), activity.getWindow(), activity.getLayoutInflater(), title,
                    "取消", "确认", listTextGravity, (int) (displayWidth * 0.8), true, true, 0XFF03a9f4, 0XFF03a9f4, multipleSelection, true, selectListDataBeans, selectBackListener, what);

        } else {
            showSelectCenter(activity.getApplicationContext(), activity.getWindow(), activity.getLayoutInflater(), title,
                    "取消", "确认", listTextGravity, (int) (displayWidth * 0.8), false, false, 0XFF03a9f4, 0XFF03a9f4, multipleSelection, true, selectListDataBeans, selectBackListener, what);

        }

    }


    /**
     * @param context             context
     * @param window              window
     * @param layoutInflater      layoutInflater
     * @param title               标题，为空不显示
     * @param exitText            取消文字
     * @param doneText            确认文字
     * @param showExit            是否显示取消
     * @param showDone            是否显示确认
     * @param exitColor           取消文字颜色
     * @param doneColor           确认文字颜色
     * @param multipleSelection   是否多选
     * @param focusable           点击外部是否可取消
     * @param selectListDataBeans selectListDataBeans
     * @param selectBackListener  监听
     * @param what                tag
     */
    public void showSelectCenter(Context context, Window window, LayoutInflater layoutInflater,
                                 String title, String exitText, String doneText, int listTextGravity, int popupWidth,
                                 boolean showExit, boolean showDone,
                                 int exitColor, int doneColor, boolean multipleSelection, boolean focusable,
                                 List<SelectListDataBean> selectListDataBeans, PopupSelect.onClickListener selectBackListener, int what) {

        if (popupSelect.getpWindow() != null && popupSelect.getpWindow().isShowing()) {
            popupSelect.miss();
        }


        GradientDrawable drawable = new GradientDrawable();
        //形状（矩形）
        drawable.setShape(GradientDrawable.RECTANGLE);
        //渐变方向（左到右）
        drawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
        //颜色
        drawable.setColors(new int[]{0XF0F8F8F8, 0XF0F8F8F8, 0XF0F8F8F8});
        int displayHeight = GetIpAndDisplayUtil.getDisplayHeight(window);
        int dp10 = DensityUtils.dp2px(context, 10);
        int dp5 = DensityUtils.dp2px(context, 5);
        int dp8 = DensityUtils.dp2px(context, 8);
        SelectSettings.Builder builder = new SelectSettings.Builder();
        SelectSettings promptSettings =
                builder.selectListDataBean(selectListDataBeans)
                        .clickListener(selectBackListener)
                        .popupWidth(popupWidth)
                        .multipleSelection(multipleSelection)
                        .titleTextStr(title)
                        .showTitle(!TextUtils.isEmpty(title))
                        .popupHeight((int) (displayHeight * 0.5))
                        .animation(SelectSettings.PopupAnimation.popup_fade_in_out)
                        .popupGravity(Gravity.CENTER)
                        .selectImagePosition(SelectSettings.SelectImagePosition.RIGHT)
                        .titleTextGravity(Gravity.CENTER)
                        .titleTextPaddings(new int[]{0, dp5 * 2, 0, dp5 * 2})
                        .popupRadius(new float[]{dp5, dp5, dp5, dp5, dp5, dp5, dp5, dp5})
                        .buttonPaddings(new int[]{0, dp5 * 3, 0, dp5 * 3})
                        .buttonTextStr1(exitText)
                        .buttonTextStr2(doneText)
                        .showButton1(showExit)
                        .showButton2(showDone)
                        .buttonTextColor1(exitColor)
                        .buttonTextColor2(doneColor)
                        .titleDividingColor(0X88AAAAAA)
                        .listDividerHeight(2)
                        .listDivider(drawable)
                        .listTextGravity(listTextGravity)
                        .listTextPaddings(new int[]{dp10, 0, dp10, 0})
                        .listLayoutPadding(new int[]{0, dp5, 0, dp5})
                        .listPaddings(new int[]{dp10 * 2, dp10, dp10 * 2, dp10 * 2})
                        .listSelectImageOff(ContextCompat.getDrawable(context, R.drawable.select_null))
                        .listSelectImageOn(ContextCompat.getDrawable(context, R.drawable.select_1))
                        .listSelectImagePaddings(new int[]{dp8, dp8, dp8, dp8})
                        .clippingEnabled(true)
                        .titleDividingColor(0XF0F8F8F8)
                        .buttonDividingColor(0XF0F8F8F8)
                        .focusable(focusable)
                       // .bgAlpha(1)
                        .build();
        popupSelect.popupSelect(window, context, layoutInflater, promptSettings, what);

    }




    /**
     * @throws
     * @Description: 选择年月日
     * @param:
     * @return:
     * @date: 2022/7/26
     */
    public void showPopupSelectYearMonthDay(Activity activity, PopupSelectDateTime2.OnClickListener dateSelectClick, int what) {
        showPopupSelectYearMonthDay(activity, dateSelectClick, what,0.5f);
    }
    public void showPopupSelectYearMonthDay(Activity activity, PopupSelectDateTime2.OnClickListener dateSelectClick, int what,float alpha) {
        popupSelectDateTime2.popupDateTime(activity.getWindow(), activity.getApplicationContext(), true, false,
                dateSelectClick, what,alpha);
    }


    /**
     * @Description: 只选择年
     * @param:
     * @return:
     * @date: 2022/10/12
     * @throws
     */
    public void showPopupSelectYear(Activity activity, PopupSelectDateTime2.OnClickListener dateSelectClick, int what ) {
        popupSelectDateTime2.popupDateTime(activity.getWindow(), activity.getApplicationContext(), true, false,
                dateSelectClick, what,true);
    }
    public void showPopupSelectYear(Activity activity, PopupSelectDateTime2.OnClickListener dateSelectClick, int what,float alpha ) {
        popupSelectDateTime2.popupDateTime(activity.getWindow(), activity.getApplicationContext(), true, false,
                dateSelectClick, what,alpha,true);
    }

    /**
     * @throws
     * @Description: 选择年月日 上午下午
     * @param:
     * @return:
     * @date: 2022/7/26
     */
    public void showPopupSelectDataAmPm(Activity activity, PopupSelectDateAndAmPm.OnClickListener onClickListener, int what) {
        showPopupSelectDataAmPm(activity, onClickListener, what,0.5f);
    }
    public void showPopupSelectDataAmPm(Activity activity, PopupSelectDateAndAmPm.OnClickListener onClickListener, int what,float a) {
        popupSelectDateAndAmPm.popupDateTime(activity.getWindow(), activity.getApplicationContext(), onClickListener, what,a);
    }

    /**
     * @throws
     * @Description: 选择年月日 时分秒
     * @param:
     * @return:
     * @date: 2022/7/26
     */
    public void showPopupSelectDataHHSSMM(Activity activity, PopupSelectDateTime2.OnClickListener onClickListener, int what) {
        showPopupSelectDataHHSSMM(activity, onClickListener, what,0.5f);
    }

    public void showPopupSelectDataHHSSMM(Activity activity, PopupSelectDateTime2.OnClickListener onClickListener, int what,float alpha) {
        popupSelectDateTime2.popupDateTime(activity.getWindow(), activity.getApplicationContext(), true, true, onClickListener, what,alpha);
    }

    /**
     * @throws
     * @Description: 选择年月日时分，上下布局(自定义view，滚动不流畅，可替换为popupSelectDateTime2的view写个上下布局的)
     * @param:
     * @return:
     * @date: 2022/7/26
     */
    public void showPopupSelectData(Activity activity, PopupSelectDateTime.OnClickListener dateSelectClick, int what) {
       showPopupSelectData(activity, dateSelectClick, what,0.5f);
    }
    public void showPopupSelectData(Activity activity, PopupSelectDateTime.OnClickListener dateSelectClick, int what,float alpha) {
        popupSelectDateTime.popupDateTime(activity.getWindow(), activity.getApplicationContext(), true, true,
                dateSelectClick, what,alpha);
    }


    /**
     * @throws
     * @Description: 选择弹窗是否在显示
     * @param:
     * @return:
     * @date: 2022/7/11
     */
    public boolean isSelectShowing() {
        if (popupSelect != null && popupSelect.getpWindow() != null && popupSelect.getpWindow().isShowing()) {
            return true;
        }
        return false;
    }


    /**
     * @throws
     * @Description: PopupView 是否在显示
     * @param:
     * @return:
     * @date: 2022/7/11
     */
    public boolean isPopupViewShowing() {
        if (popupView != null && popupView.getpWindow() != null && popupView.getpWindow().isShowing()) {
            return true;
        }
        return false;
    }

    public boolean isPopupDataTimeShowing() {
        if (popupSelectDateTime != null && popupSelectDateTime.getPopupWindow() != null && popupSelectDateTime.getPopupWindow().isShowing()) {
            return true;
        }

        if (popupSelectDateTime2 != null && popupSelectDateTime2.getPopupWindow() != null && popupSelectDateTime2.getPopupWindow().isShowing()) {
            return true;
        }

        if (popupSelectDateAndAmPm != null && popupSelectDateAndAmPm.getPopupWindow() != null && popupSelectDateAndAmPm.getPopupWindow().isShowing()) {
            return true;
        }

        return false;
    }


    /**
     * @throws
     * @Description: 是否有popup在显示
     * @param:
     * @return:
     * @date: 2022/7/11
     */
    public boolean isPopupShowing() {
        return (isSelectShowing() || isPopupViewShowing() || isPopupDataTimeShowing());
    }


    /**
     * @throws
     * @Description: 关闭所有弹窗
     * @param:
     * @return:
     * @date: 2022/7/11
     */
    public void missPopup() {
        if (isSelectShowing()) {
            popupSelect.miss();
        }
        if (isPopupViewShowing()) {
            popupView.miss();
        }

        if (popupSelectDateTime != null && popupSelectDateTime.getPopupWindow() != null && popupSelectDateTime.getPopupWindow().isShowing()) {
            popupSelectDateTime.miss();
        }

        if (popupSelectDateTime2 != null && popupSelectDateTime2.getPopupWindow() != null && popupSelectDateTime2.getPopupWindow().isShowing()) {
            popupSelectDateTime2.miss();
        }

        if (popupSelectDateAndAmPm != null && popupSelectDateAndAmPm.getPopupWindow() != null && popupSelectDateAndAmPm.getPopupWindow().isShowing()) {
            popupSelectDateAndAmPm.miss();
        }


    }


}
