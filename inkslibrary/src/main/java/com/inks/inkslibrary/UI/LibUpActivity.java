package com.inks.inkslibrary.UI;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.inks.inkslibrary.R;
import com.inks.inkslibrary.Service.QueryVersionBackBean;
import com.inks.inkslibrary.Utils.APKVersionCodeUtils;
import com.inks.inkslibrary.Utils.ClickUtil;
import com.inks.inkslibrary.Utils.DensityUtils;
import com.inks.inkslibrary.Utils.GetResId;
import com.inks.inkslibrary.Utils.L;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class LibUpActivity extends Activity {
    private LinearLayout info1View, downingView, failView;
    private ScrollView scrollView;
    //分割线
    private View DividingLine_1, DividingLine_2, DividingLine_3;
    private ProgressBar progressBar;
    //版本信息
    private TextView titleView, currentVersionView, latestVersionView, mgsTitleView, mgsView, noView, yesView;
    //下载中
    private TextView loading_pro;
    //下载失败
    private TextView failTextView, failOkView;

    private QueryVersionBackBean queryVersionBackBean;
    private long total = 0;
    private int process = 0;
    private int readBytes = 0;
    private File file = null;

    // 要申请的权限
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //this.setTheme(GetResId.getStyleId(this,"StyleTransparentActivity"));
        super.onCreate(savedInstanceState);
        if (!LibUpUIData.isStatuBar()) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏
        }
        setContentView(GetResId.getId(this, "layout", "activity_lib_up"));

        String responseStr = getIntent().getStringExtra("responseStr");
        Gson gs = new Gson();
        queryVersionBackBean = gs.fromJson(responseStr, QueryVersionBackBean.class);

        initView();
        setViewStyle();
        setViewData();

    }

    private void initView() {
        info1View = findViewById(GetResId.getId(this, "id", "up_info_1"));
        scrollView = findViewById(GetResId.getId(this, "id", "up_ScrollView"));
        downingView = findViewById(GetResId.getId(this, "id", "up_downing"));
        failView = findViewById(GetResId.getId(this, "id", "up_fail"));
        DividingLine_1 = findViewById(GetResId.getId(this, "id", "up_DividingLine_1"));
        DividingLine_2 = findViewById(GetResId.getId(this, "id", "up_DividingLine_2"));
        DividingLine_3 = findViewById(GetResId.getId(this, "id", "up_DividingLine_3"));
        progressBar = findViewById(GetResId.getId(this, "id", "up_ProgressBar"));
        titleView = findViewById(GetResId.getId(this, "id", "up_title_1"));
        currentVersionView = findViewById(GetResId.getId(this, "id", "up_currentVersion"));
        latestVersionView = findViewById(GetResId.getId(this, "id", "up_latestVersion"));
        mgsTitleView = findViewById(GetResId.getId(this, "id", "up_mgsTitle"));
        mgsView = findViewById(GetResId.getId(this, "id", "up_mgs"));
        noView = findViewById(GetResId.getId(this, "id", "up_no"));
        yesView = findViewById(GetResId.getId(this, "id", "up_yes"));
        loading_pro = findViewById(GetResId.getId(this, "id", "up_loading_pro"));
        failTextView = findViewById(GetResId.getId(this, "id", "up_failText"));
        failOkView = findViewById(GetResId.getId(this, "id", "up_fail_ok"));

        noView.setOnClickListener(cccc);
        yesView.setOnClickListener(cccc);
        failOkView.setOnClickListener(cccc);
        noView.setTag("no");
        yesView.setTag("yes");
        failOkView.setTag("ok");
    }

    private void setViewStyle() {
        //设置宽高
        RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) info1View.getLayoutParams();
        linearParams.height = DensityUtils.dp2px(this, LibUpUIData.getHeight());
        linearParams.width = DensityUtils.dp2px(this, LibUpUIData.getWidth());
        info1View.setLayoutParams(linearParams);
        linearParams = (RelativeLayout.LayoutParams) downingView.getLayoutParams();
        linearParams.height = DensityUtils.dp2px(this, LibUpUIData.getHeight());
        linearParams.width = DensityUtils.dp2px(this, LibUpUIData.getWidth());
        downingView.setLayoutParams(linearParams);
        linearParams = (RelativeLayout.LayoutParams) failView.getLayoutParams();
        linearParams.height = DensityUtils.dp2px(this, LibUpUIData.getHeight());
        linearParams.width = DensityUtils.dp2px(this, LibUpUIData.getWidth());
        failView.setLayoutParams(linearParams);

        //背景色及圆角
        GradientDrawable drawable = new GradientDrawable();
        //形状（矩形）
        drawable.setShape(GradientDrawable.RECTANGLE);
        //渐变样式
        drawable.setGradientType(GradientDrawable.RECTANGLE);
        //渐变方向（左到右）
        drawable.setOrientation(LibUpUIData.getOrientation());
        //圆角
        drawable.setCornerRadius(DensityUtils.dp2px(this, LibUpUIData.getRadius()));
        //颜色
        drawable.setColors(LibUpUIData.getColours());
        info1View.setBackground(drawable);
        downingView.setBackground(drawable);
        failView.setBackground(drawable);
        //滚动条时间
        scrollView.setScrollBarFadeDuration(LibUpUIData.getDuration() * 1000);
        //分割线颜色
        DividingLine_1.setBackgroundColor(LibUpUIData.getDividingLineColour());
        DividingLine_2.setBackgroundColor(LibUpUIData.getDividingLineColour());
        DividingLine_3.setBackgroundColor(LibUpUIData.getDividingLineColour());
        //progressBar颜色
        ColorStateList colorStateList = ColorStateList.valueOf(LibUpUIData.getProgressBarColour());
        progressBar.setIndeterminateTintList(colorStateList);

        titleView.setTextColor(LibUpUIData.getTextcolours()[0]);
        currentVersionView.setTextColor(LibUpUIData.getTextcolours()[0]);
        latestVersionView.setTextColor(LibUpUIData.getTextcolours()[0]);
        mgsTitleView.setTextColor(LibUpUIData.getTextcolours()[0]);
        mgsView.setTextColor(LibUpUIData.getTextcolours()[0]);
        noView.setTextColor(LibUpUIData.getTextcolours()[1]);
        yesView.setTextColor(LibUpUIData.getTextcolours()[1]);
        loading_pro.setTextColor(LibUpUIData.getTextcolours()[0]);
        failTextView.setTextColor(LibUpUIData.getTextcolours()[0]);
        failOkView.setTextColor(LibUpUIData.getTextcolours()[1]);

        titleView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, LibUpUIData.getTextSizes()[0]);
        currentVersionView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, LibUpUIData.getTextSizes()[1]);
        latestVersionView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, LibUpUIData.getTextSizes()[1]);
        mgsTitleView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, LibUpUIData.getTextSizes()[1]);
        mgsView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, LibUpUIData.getTextSizes()[2]);
        noView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, LibUpUIData.getTextSizes()[0]);
        yesView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, LibUpUIData.getTextSizes()[0]);
        loading_pro.setTextSize(TypedValue.COMPLEX_UNIT_DIP, LibUpUIData.getTextSizes()[1]);
        failTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, LibUpUIData.getTextSizes()[0]);
        failOkView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, LibUpUIData.getTextSizes()[0]);

        noView.setPadding(LibUpUIData.getButtonPadding(), LibUpUIData.getButtonPadding(), LibUpUIData.getButtonPadding(), LibUpUIData.getButtonPadding());
        yesView.setPadding(LibUpUIData.getButtonPadding(), LibUpUIData.getButtonPadding(), LibUpUIData.getButtonPadding(), LibUpUIData.getButtonPadding());
        failOkView.setPadding(LibUpUIData.getButtonPadding(), LibUpUIData.getButtonPadding(), LibUpUIData.getButtonPadding(), LibUpUIData.getButtonPadding());

    }

    private void setViewData() {
        info1View.setVisibility(View.GONE);
        downingView.setVisibility(View.GONE);
        failView.setVisibility(View.VISIBLE);
        titleView.setText(LibUpUIData.getTitle());
        currentVersionView.setText(LibUpUIData.getCurrentVersion() + APKVersionCodeUtils.getVerName(this));
        latestVersionView.setText(LibUpUIData.getLatestVersion() + queryVersionBackBean.getAppVersion());
        mgsTitleView.setText(LibUpUIData.getMgsTitle());
        if (LibUpUIData.isLanguage()) {
            mgsView.setText(queryVersionBackBean.getVersionMgsCH());
        } else {
            mgsView.setText(queryVersionBackBean.getVersionMgsEN());
        }
        noView.setText(LibUpUIData.getNo());
        yesView.setText(LibUpUIData.getDownYes());
        loading_pro.setText(LibUpUIData.getLoading() + "  --/--...");
        failTextView.setText(LibUpUIData.getFailText());
        failOkView.setText(LibUpUIData.getFailOk());

        if (queryVersionBackBean.getMustUp().equals("1")) {
            //强制更新
            noView.setClickable(false);
            noView.setTextColor(LibUpUIData.getTextcolours()[2]);
        }

        info1View.setVisibility(View.VISIBLE);
        downingView.setVisibility(View.GONE);
        failView.setVisibility(View.GONE);

    }


    View.OnClickListener cccc = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!ClickUtil.isFastDoubleClick((long) 200)) {
                switch ((String) v.getTag()) {
                    case "no":
                        finish();
                        break;
                    case "yes":
                        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                            // 检查该权限是否已经获取
                            int i = ContextCompat.checkSelfPermission(LibUpActivity.this, permissions[0]);
                            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                            if (i != PackageManager.PERMISSION_GRANTED) {
                                // 如果没有授予该权限，就去提示用户请求
                                showDialogTipUserRequestPermission();
                            } else {
                                info1View.setVisibility(View.GONE);
                                downingView.setVisibility(View.VISIBLE);
                                failView.setVisibility(View.GONE);
                                download();
                            }
                        } else {
                            info1View.setVisibility(View.GONE);
                            downingView.setVisibility(View.VISIBLE);
                            failView.setVisibility(View.GONE);
                            download();
                        }


                        break;
                    case "ok":
                        info1View.setVisibility(View.VISIBLE);
                        downingView.setVisibility(View.GONE);
                        failView.setVisibility(View.GONE);
                        break;
                }
            }
        }
    };


    private void download() {
        DownloadThread myThread = new DownloadThread();
        myThread.start();
    }

    private class DownloadThread extends Thread {
        @Override
        public void run() {
            super.run();
            OkHttpClient client = new OkHttpClient();

            //请求超时设置
            client.newBuilder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(10 * 60, TimeUnit.SECONDS)
                    .build();


            //构建FormBody，传入要提交的参数
            FormBody formBody = new FormBody
                    .Builder()
                    .add("download", "app")
                    .build();
            final Request request;
            request = new Request.Builder()
                    .url(queryVersionBackBean.getDownUri())
                    .post(formBody)
                    .build();

            L.e("queryVersionBackBean.getDownUri()：" + queryVersionBackBean.getDownUri());

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    L.e("失败2");
                    showFail();
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    // final String responseStr = response.body().string();
                    //String fileName = response.header("File-Name");
                    //  L.e(fileName);
                    InputStream is = null;//输入流
                    FileOutputStream fos = null;//输出流
                    try {
                        is = response.body().byteStream();//获取输入流
                        total = response.body().contentLength();//获取文件大小
                        process = 0;
                        L.e("文件大小" + total);
                        mHandler.sendEmptyMessage(201);
                        if (is != null) {
                            file = new File(Environment.getExternalStorageDirectory(), "a.apk");// 设置路径
                            L.e(file.toString());
                            if (file.exists()) {
                                file.delete();
                            }
                            file.createNewFile();
                            fos = new FileOutputStream(file);
                            byte[] buf = new byte[1024];
                            int ch = -1;
                            readBytes = 0;
                            while ((ch = is.read(buf)) != -1) {
                                L.e("ch：" + ch);
                                fos.write(buf, 0, ch);
                                readBytes += ch;
                                if ((((readBytes / (float) total) * 100) - process) > 1) {
                                    process = (int) ((readBytes / (float) total) * 100);
                                    mHandler.sendEmptyMessage(202);
                                }
                            }
                        }
                        fos.flush();
                        L.e("下载完成");
                        // 下载完成
                        if (fos != null) {
                            fos.close();
                        }
                        mHandler.sendEmptyMessage(203);
                    } catch (Exception e) {
                        L.e("Exception:" + e);
                        mHandler.sendEmptyMessage(204);
                        L.e("204");
                    } finally {
                        try {
                            if (is != null)
                                is.close();
                        } catch (IOException e) {
                        }
                        try {
                            if (fos != null)
                                fos.close();
                        } catch (IOException e) {
                        }
                    }
                }
            });
        }
    }


    private void showFail() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                info1View.setVisibility(View.GONE);
                failView.setVisibility(View.VISIBLE);
                downingView.setVisibility(View.GONE);
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();

    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 111:
                    break;
                case 201://设置总进度（文件大小）
                    loading_pro.setText(LibUpUIData.getLoading() + "  " + FormetFileSize(readBytes) + "/" + FormetFileSize(total) + "...");
                    break;
                case 202://设置进度
                    loading_pro.setText(LibUpUIData.getLoading() + "  " + FormetFileSize(readBytes) + "/" + FormetFileSize(total) + "...");
                    break;
                case 203://下载APK完成
                    //  finish();
                    if (file != null) {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        //判断是否是AndroidN以及更高的版本
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            Uri contentUri = FileProvider.getUriForFile(LibUpActivity.this, LibUpUIData.getAuthority(), file);
                            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                            L.e("判断是否是AndroidN以及更高的版本");
                        } else {
                            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        }

                        // L.e("aaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
                        startActivity(intent);
                        finish();

//                        Intent intent = new Intent(Intent.ACTION_VIEW);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        intent.setDataAndType(Uri.fromFile(file),
//                                "application/vnd.android.package-archive");
//                        startActivity(intent);
                        //  android.os.Process.killProcess(android.os.Process.myPid());
                    }
                    break;
                case 204://失败
                    showFail();
                    break;
            }
        }
    };

    public String FormetFileSize(long fileS) {//转换文件大小
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS == 0) {
            fileSizeString = "0.00B";
        } else if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onBackPressed() {

    }


    // 提示用户该请求权限的弹出框
    private void showDialogTipUserRequestPermission() {

        new AlertDialog.Builder(this)
                .setTitle(LibUpUIData.getPermissionTitle())
                .setMessage(LibUpUIData.getPermissionMgs())
                .setPositiveButton(LibUpUIData.getPermissionOpen(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startRequestPermission();
                    }
                })
                .setNegativeButton(LibUpUIData.getNo(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setCancelable(false).show();
    }

    // 开始提交请求权限
    private void startRequestPermission() {
        ActivityCompat.requestPermissions(this, permissions, 321);
    }

    // 用户权限 申请 的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 321) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    // 判断用户是否 点击了不再提醒。(检测该权限是否还可以申请)
                    boolean b = shouldShowRequestPermissionRationale(permissions[0]);
                    if (!b) {
                        // 用户还是想用我的 APP 的
                        // 提示用户去应用设置界面手动开启权限
                        goToAppSetting();
                        //showDialogTipUserGoToAppSettting();
                    }
                } else {
                  //  Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // 提示用户去应用设置界面手动开启权限

    private void showDialogTipUserGoToAppSettting() {

        dialog = new AlertDialog.Builder(this)
                .setTitle(LibUpUIData.getPermissionTitle())
                .setMessage(LibUpUIData.getPermissionMgs())
                .setPositiveButton(LibUpUIData.getPermissionOpen(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 跳转到应用设置界面
                        goToAppSetting();
                    }
                })
                .setNegativeButton(LibUpUIData.getNo(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setCancelable(false).show();
    }

    // 跳转到当前应用的设置界面
    private void goToAppSetting() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 123);
    }

    //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 检查该权限是否已经获取
                int i = ContextCompat.checkSelfPermission(this, permissions[0]);
                // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                if (i != PackageManager.PERMISSION_GRANTED) {
                    // 提示用户应该去应用设置界面手动开启权限
                    //goToAppSetting();
                    //showDialogTipUserGoToAppSettting();
                } else {
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                   // Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


}
