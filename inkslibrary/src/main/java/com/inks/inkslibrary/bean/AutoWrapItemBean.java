package com.inks.inkslibrary.bean;

public class AutoWrapItemBean {
    private String text;
    private boolean deleteAble =false;
    private boolean select = false;
    private Object object;
    private String json;
    private int textColor = 0XFF666666;
    private int textSelectColor= 0xFF3CC18C;
    private int textSizeDp = 16;
    private int[] paddingDp=new int[] {10,5,10,5};

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isDeleteAble() {
        return deleteAble;
    }

    public void setDeleteAble(boolean deleteAble) {
        this.deleteAble = deleteAble;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getTextSelectColor() {
        return textSelectColor;
    }

    public void setTextSelectColor(int textSelectColor) {
        this.textSelectColor = textSelectColor;
    }

    public int getTextSizeDp() {
        return textSizeDp;
    }

    public void setTextSizeDp(int textSizeDp) {
        this.textSizeDp = textSizeDp;
    }

    public int[] getPaddingDp() {
        return paddingDp;
    }

    public void setPaddingDp(int[] paddingDp) {
        this.paddingDp = paddingDp;
    }
}
