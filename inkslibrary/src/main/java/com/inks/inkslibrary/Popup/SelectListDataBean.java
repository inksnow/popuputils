package com.inks.inkslibrary.Popup;

import android.graphics.drawable.Drawable;

public class SelectListDataBean {

    private String text ="";
    private boolean choosed = false;
    private Drawable icon = null;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isChoosed() {
        return choosed;
    }

    public void setChoosed(boolean choosed) {
        this.choosed = choosed;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}
