package com.inks.inkslibrary.Popup;

import android.graphics.drawable.Drawable;

/**
 * ProjectName:    PopupTest
 * Package:        com.inks.inkslibrary.Popup
 * ClassName:      SelectListBaseBean
 * Description:     选择类
 * Author:         inks
 * CreateDate:     2022/6/13 11:55
 */
public class SelectListBaseBean {
    private String  selectListShowText ="";
    private boolean  selectListChoosed = false;
    private Drawable selectListIcon = null;

    public String getSelectListShowText() {
        return selectListShowText;
    }

    public void setSelectListShowText(String selectListShowText) {
        this.selectListShowText = selectListShowText;
    }

    public boolean isSelectListChoosed() {
        return selectListChoosed;
    }

    public void setSelectListChoosed(boolean selectListChoosed) {
        this.selectListChoosed = selectListChoosed;
    }

    public Drawable getSelectListIcon() {
        return selectListIcon;
    }

    public void setSelectListIcon(Drawable selectListIcon) {
        this.selectListIcon = selectListIcon;
    }
}
