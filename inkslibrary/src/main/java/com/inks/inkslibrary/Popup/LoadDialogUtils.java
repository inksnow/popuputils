package com.inks.inkslibrary.Popup;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inks.inkslibrary.R;


public class LoadDialogUtils {
    private  TextView tipTextView;
    private  Dialog loadingDialog;

    public LoadDialogUtils(Context context) {
        createLoadingDialog(context);

    }

    public  void createLoadingDialog(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.popup_dialog_loading, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v
                .findViewById(R.id.dialog_loading_view);// 加载布局
         tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字

         loadingDialog = new Dialog(context, R.style.popup_MyDialogStyle);// 创建自定义样式dialog
        loadingDialog.setCancelable(false); // 是否可以按“返回键”消失
        loadingDialog.setCanceledOnTouchOutside(false); // 点击加载框以外的区域
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
        /**
         *将显示Dialog的方法封装在这里面
         */
        Window window = loadingDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setGravity(Gravity.CENTER);
        window.setAttributes(lp);
        window.setWindowAnimations(R.style.popup_PopWindowAnimStyle);
      //  loadingDialog.show();
    }

    public  void upText(String s){
       getTipTextView().setText(s);
        if(!getDialog().isShowing()){
            getDialog().show();
        }
    }

    public  void closeDialog( ){
        getDialog().dismiss();
    }

    public  TextView getTipTextView() {
        return tipTextView;
    }

    public  Dialog getDialog() {
        return loadingDialog;
    }

    /**
     * 关闭dialog
     *
     * http://blog.csdn.net/qq_21376985
     *
     * @param mDialogUtils
     */
    public static void closeDialog(Dialog mDialogUtils) {
        if (mDialogUtils != null && mDialogUtils.isShowing()) {
            mDialogUtils.dismiss();
        }
    }
 
}
 
 