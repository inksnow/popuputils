package com.inks.inkslibrary.UI;

import android.graphics.drawable.GradientDrawable;

public class LibUpUIData {
    //状态栏
    private static boolean statuBar = true;
    //宽（单位 dp）
    private static int  width = 340;
    //高
    private static int  height = 200;
    //圆角
    private static int  radius = 15;
    //滚动条持续时间（秒）
    private static int  duration = 5;
    //背景色
    private static int[] colours = {0XFFaee5f5,0Xff00c6ff};
    //渐变方向
    private static GradientDrawable.Orientation orientation = GradientDrawable.Orientation.LEFT_RIGHT;
    //字體大小(標題和按鈕、內容標題、內容 單位Dp)
    private static int[] textSizes = {18,16,14};
    //字體顏色(內容 、按鈕正常、按鈕無法點擊)
    private static int[] textcolours = {0XFFffffff,0Xffffffff,0XFF777777};
    //button padding(單位dp)
    private static int buttonPadding = 10;
    //分割线颜色
    private static int dividingLineColour = 0X66666666;
    //下载中progressBar颜色
    private static int progressBarColour = 0XFFFF6666;
    //使用语言 true 中文
    private static boolean language = true;
    private static String title ="版本更新";
    private static String currentVersion ="当前版本：";
    private static String  latestVersion = "最新版本：";
    private static String  mgsTitle = "更新内容：";
    private static String  no = "取消";
    private static String  downYes = "下载";
    private static String  loading = "正在下载    ";
    private static String  failText = "下载失败，请检查网络重试";
    private static String  failOk = "确定";
    //权限
    private static String  permissionTitle = "存储权限不可用";
    private static String  permissionMgs = "请开启存储权限";
    private static String  permissionOpen = "立即开启";

    private static String authority = "com.inks.inklibrary.fileProvider";

    public static boolean isStatuBar() {
        return statuBar;
    }

    public static void setStatuBar(boolean statuBar) {
        LibUpUIData.statuBar = statuBar;
    }

    public static int getWidth() {
        return width;
    }

    public static void setWidth(int width) {
        LibUpUIData.width = width;
    }

    public static int getHeight() {
        return height;
    }

    public static void setHeight(int height) {
        LibUpUIData.height = height;
    }

    public static int getRadius() {
        return radius;
    }

    public static void setRadius(int radius) {
        LibUpUIData.radius = radius;
    }

    public static int getDuration() {
        return duration;
    }

    public static void setDuration(int duration) {
        LibUpUIData.duration = duration;
    }

    public  static int[] getColours() {
        return colours;
    }

    public  static void setColours(int[] colours) {
        LibUpUIData.colours = colours;
    }

    public static GradientDrawable.Orientation getOrientation() {
        return orientation;
    }

    public static void setOrientation(GradientDrawable.Orientation orientation) {
        LibUpUIData.orientation = orientation;
    }

    public static int[] getTextSizes() {
        return textSizes;
    }

    public static void setTextSizes(int[] textSizes) {
        LibUpUIData.textSizes = textSizes;
    }

    public static int[] getTextcolours() {
        return textcolours;
    }

    public static void setTextcolours(int[] textcolours) {
        LibUpUIData.textcolours = textcolours;
    }

    public static int getButtonPadding() {
        return buttonPadding;
    }

    public static void setButtonPadding(int buttonPadding) {
        LibUpUIData.buttonPadding = buttonPadding;
    }

    public static int getDividingLineColour() {
        return dividingLineColour;
    }

    public static void setDividingLineColour(int dividingLineColour) {
        LibUpUIData.dividingLineColour = dividingLineColour;
    }

    public static String getTitle() {
        return title;
    }

    public static void setTitle(String title) {
        LibUpUIData.title = title;
    }

    public static String getCurrentVersion() {
        return currentVersion;
    }

    public static void setCurrentVersion(String currentVersion) {
        LibUpUIData.currentVersion = currentVersion;
    }

    public static String getLatestVersion() {
        return latestVersion;
    }

    public static void setLatestVersion(String latestVersion) {
        LibUpUIData.latestVersion = latestVersion;
    }

    public static String getMgsTitle() {
        return mgsTitle;
    }

    public static void setMgsTitle(String mgsTitle) {
        LibUpUIData.mgsTitle = mgsTitle;
    }

    public static String getNo() {
        return no;
    }

    public static void setNo(String no) {
        LibUpUIData.no = no;
    }

    public static String getDownYes() {
        return downYes;
    }

    public static void setDownYes(String downYes) {
        LibUpUIData.downYes = downYes;
    }

    public static String getLoading() {
        return loading;
    }

    public static void setLoading(String loading) {
        LibUpUIData.loading = loading;
    }

    public static String getFailText() {
        return failText;
    }

    public static void setFailText(String failText) {
        LibUpUIData.failText = failText;
    }

    public static String getFailOk() {
        return failOk;
    }

    public static void setFailOk(String failOk) {
        LibUpUIData.failOk = failOk;
    }

    public static int getProgressBarColour() {
        return progressBarColour;
    }

    public static void setProgressBarColour(int progressBarColour) {
        LibUpUIData.progressBarColour = progressBarColour;
    }

    public static boolean isLanguage() {
        return language;
    }

    public static void setLanguage(boolean language) {
        LibUpUIData.language = language;
    }

    public static String getAuthority() {
        return authority;
    }

    public static void setAuthority(String authority) {
        LibUpUIData.authority = authority;
    }

    public static String getPermissionTitle() {
        return permissionTitle;
    }

    public static void setPermissionTitle(String permissionTitle) {
        LibUpUIData.permissionTitle = permissionTitle;
    }

    public static String getPermissionMgs() {
        return permissionMgs;
    }

    public static void setPermissionMgs(String permissionMgs) {
        LibUpUIData.permissionMgs = permissionMgs;
    }

    public static String getPermissionOpen() {
        return permissionOpen;
    }

    public static void setPermissionOpen(String permissionOpen) {
        LibUpUIData.permissionOpen = permissionOpen;
    }
}
