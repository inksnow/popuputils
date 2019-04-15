package com.inks.inkslibrary.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Administrator on 2018/3/30 0030.
 */

public class ProgressDialogUtil {
    private static ProgressDialog pd;

    public static void showPd(Context context, String s) {

        showPd(context,s,false,false);
    }

    public static void showPd(Context context, String s , Boolean touchOutside, Boolean touchBack) {
        pd = new ProgressDialog(context);
        pd.setCanceledOnTouchOutside(touchOutside);
        pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });
        pd.setMessage(s);
        pd.setCancelable(touchBack);
        pd.show();
    }

    public static void dismissPd() {
        if (pd.isShowing()) {
            pd.dismiss();
        }
    }
}
