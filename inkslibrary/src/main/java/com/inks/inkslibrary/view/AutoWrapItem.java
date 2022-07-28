package com.inks.inkslibrary.view;

import android.content.Context;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.inks.inkslibrary.R;
import com.inks.inkslibrary.Utils.DensityUtils;
import com.inks.inkslibrary.bean.AutoWrapItemBean;

public class AutoWrapItem extends ViewGroup {
    private Context context;
    private TextView textView;
    private ImageView imageView;
    //单位dp
    private float textSize = 30;
    private int textViewHeight = 0;
    private int textWidth = 0;
    private int paddingLeft = 25;
    private int paddingRight = 25;
    private int paddingTop = 10;
    private int paddingBottom = 10;

    private int imageLeft = 20;
    private boolean deleteAble = true;
    private int index = 0;
    private AutoWrapItemClick autoWrapItemClick;

    private AutoWrapItemBean mWrapItemBean;

    public interface AutoWrapItemClick{
            void onItemClick(int index);
            void onDeleteClick(int index);
    }

    public AutoWrapItem(Context context) {
        super(context);
        this.context = context;
    }

    public AutoWrapItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoWrapItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //int specHeightSize = MeasureSpec.getSize(textViewHeight + paddingTop + paddingBottom);//高
        int specHeightSize = textViewHeight + paddingTop + paddingBottom;//高

        int specWidthSize;
        if (deleteAble) {
            specWidthSize = textWidth + textViewHeight + imageLeft + paddingLeft + paddingRight;
        } else {
            specWidthSize = textWidth + paddingLeft + paddingRight;

        }
        //specWidthSize = MeasureSpec.getSize(specWidthSize);
        setMeasuredDimension(specWidthSize, specHeightSize);
    }

    public void setText(AutoWrapItemBean bean,int index) {
        this.mWrapItemBean =bean;
        setText(bean.getText(),bean.isDeleteAble(),index,bean.isSelect(),bean.getTextColor(),bean.getTextSelectColor(),bean);
    }



    public void setText(String text, boolean deleteAble, int index,boolean select,int textColor,int textSelectColor,AutoWrapItemBean bean) {
        this.index = index;
        this.deleteAble = deleteAble;
        textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setText(text);
        textView.setTextSize(px2sp(context,DensityUtils.dp2px(context,bean.getTextSizeDp()) ));
        if(select){
            textView.setTextColor(textSelectColor);
        }else{
            textView.setTextColor(textColor);
        }

        textView.setIncludeFontPadding(false);
        textViewHeight = textView.getLineHeight();
        TextPaint textPaint = textView.getPaint();
//        Paint.FontMetrics forFontMetrics = textPaint.getFontMetrics();
//        textViewHeight = (int) (forFontMetrics.descent - forFontMetrics.ascent);
//        L.e("textViewHeight2:"+textViewHeight);

        textWidth = (int) Math.ceil(textPaint.measureText(text));
        imageView = new ImageView(context);
       // imageView.setImageResource(R.drawable.btn_delete);

        textView.setOnClickListener( textClick);
        imageView.setOnClickListener( imageClick);
//        int specHeightSize = MeasureSpec.getSize(textViewHeight+padding*2);//高
//        int specWidthSize = textWidth+textViewHeight+padding*3;
        // setMeasuredDimension(specWidthSize, specHeightSize);
        requestLayout();

    }

    OnClickListener textClick = new OnClickListener() {
        @Override
        public void onClick(View v) {

//            if(mWrapItemBean!=null){
//                if(mWrapItemBean.isSelect()){
//                    textView.setTextColor(mWrapItemBean.getTextColor());
//                    mWrapItemBean.setSelect(false);
//
//                }else{
//                    textView.setTextColor(mWrapItemBean.getTextSelectColor());
//                    mWrapItemBean.setSelect(true);
//                }
//            }


            if(autoWrapItemClick!=null){
                autoWrapItemClick.onItemClick(index);
            }

        }
    };




    OnClickListener imageClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if(autoWrapItemClick!=null){
                autoWrapItemClick.onDeleteClick(index);
            }
        }
    };

    public void setAutoWrapItemClick(AutoWrapItemClick autoWrapItemClick){
        this.autoWrapItemClick = autoWrapItemClick;
    }


    public int getItemWidth() {
        if (deleteAble) {
            return textWidth + textViewHeight + imageLeft + paddingLeft + paddingRight;
        } else {
            return textWidth + paddingLeft + paddingRight;
        }

    }

    public int getItemHeight() {
        return textViewHeight + paddingTop + paddingBottom;
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        removeAllViews();
        if (textView != null) {
            addView(textView);
            textView.layout(paddingLeft, paddingTop, textWidth + paddingLeft, textViewHeight + paddingTop);
            if (deleteAble && imageView != null) {
                addView(imageView);
                imageView.layout(textWidth + paddingLeft + imageLeft, paddingTop, textWidth + paddingLeft + imageLeft + textViewHeight, textViewHeight + paddingTop);
            }
        }
        //setBackgroundColor(0XFF556600);
        setBackgroundResource(R.drawable.p_auto_wrap_shape_rectangle_bg);
    }


    /**
     * 将px值转换为sp值，保证文字大小不变
     * @param pxValue
     * @return
     */

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }



}
