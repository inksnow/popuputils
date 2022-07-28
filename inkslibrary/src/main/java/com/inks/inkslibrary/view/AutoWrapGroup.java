package com.inks.inkslibrary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.inks.inkslibrary.Utils.L;
import com.inks.inkslibrary.bean.AutoWrapItemBean;

import java.util.ArrayList;


public class AutoWrapGroup extends ViewGroup {
    private Context context;
    private int width;
    private int intervalTop = 20;
    private int intervalLeftRight = 20;
    private int addViewHeight = 0;
    private ArrayList<AutoWrapItem> itemViews = new ArrayList<>();


    private AutoWrapGroupClick autoWrapGroupClick;

    public interface AutoWrapGroupClick {
        void onItemClick(int index);

        void onDeleteClick(int index);
    }

    public void setAutoWrapGroupClick(AutoWrapGroupClick autoWrapGroupClick) {
        this.autoWrapGroupClick = autoWrapGroupClick;
    }


    public AutoWrapGroup(Context context) {
        super(context);
        this.context = context;
    }

    public AutoWrapGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoWrapGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int specHeightSize = MeasureSpec.getSize(heightMeasureSpec);//高
        int specWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        width = specWidthSize;
        setMeasuredDimension(specWidthSize, getAddViewHeight());
    }

    public void setTexts(ArrayList<AutoWrapItemBean> texts) {
        itemViews.clear();
        if (texts != null) {
            for (int i = 0; i < texts.size(); i++) {
                AutoWrapItem autoWrapItem = new AutoWrapItem(context);
                autoWrapItem.setTag(i);
                autoWrapItem.setText(texts.get(i), i);
                autoWrapItem.setAutoWrapItemClick(autoWrapItemClick);
                autoWrapItem.setOnClickListener(autoWrapItemListener);
                itemViews.add(autoWrapItem);
            }
        }
        requestLayout();

    }

    private int getAddViewHeight() {
        //现在剩余可用宽度
        int surplusWidth = width - intervalLeftRight * 2;
        int beginHeight = intervalTop;
        int beginWidth = intervalLeftRight;
        //总共有多少行
        int line = 1;
        int lineHeight = 0;
        for (int i = 0; i < itemViews.size(); i++) {
            int itemWidth = itemViews.get(i).getItemWidth();
            int itemHeight = itemViews.get(i).getItemHeight();
            lineHeight = itemHeight;
            if (itemWidth > surplusWidth) {
                if (surplusWidth == (width - intervalLeftRight * 2)) {
                    //已经是新一行了,新开一行也放不下，那就直接放咯
//                    addView(itemViews.get(i));
//                    itemViews.get(i).layout(beginWidth,beginHeight,beginWidth+itemWidth,beginHeight+itemHeight);
                    //换下一行
                    if (i < (itemViews.size() - 1)) {
                        //不是最后一个
                        line++;
                    }
                    beginWidth = intervalLeftRight;
                    beginHeight = beginHeight + itemHeight + intervalTop;
                    surplusWidth = width - intervalLeftRight * 2;
                } else {
                    //不是新一行，换一行再放
                    //换下一行
                    line++;
                    beginWidth = intervalLeftRight;
                    beginHeight = beginHeight + itemHeight + intervalTop;
                    surplusWidth = width - intervalLeftRight * 2;
//                    addView(itemViews.get(i));
//                    itemViews.get(i).layout(beginWidth,beginHeight,beginWidth+itemWidth,beginHeight+itemHeight);
                    beginWidth = beginWidth + itemWidth + intervalLeftRight;
                    surplusWidth = surplusWidth - itemWidth - intervalLeftRight;
                }


            } else {
//                addView(itemViews.get(i));
//                itemViews.get(i).layout(beginWidth,beginHeight,beginWidth+itemWidth,beginHeight+itemHeight);
                beginWidth = beginWidth + itemWidth + intervalLeftRight;
                surplusWidth = surplusWidth - itemWidth - intervalLeftRight;
            }
        }
       // L.e("总行数：" + line + "   行高：" + lineHeight + "    总高度：" + (lineHeight * line + intervalTop * (line + 1)));
        return lineHeight * line + intervalTop * (line + 1);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        removeAllViews();

        //现在剩余可用宽度
        int surplusWidth = width - intervalLeftRight * 2;
        int beginHeight = intervalTop;
        int beginWidth = intervalLeftRight;
        for (int i = 0; i < itemViews.size(); i++) {
            int itemWidth = itemViews.get(i).getItemWidth();
            int itemHeight = itemViews.get(i).getItemHeight();
            if (itemWidth > surplusWidth) {
                if (surplusWidth == (width - intervalLeftRight * 2)) {
                    //已经是新一行了,新开一行也放不下，那就直接放咯
                    addView(itemViews.get(i));
                    itemViews.get(i).layout(beginWidth, beginHeight, beginWidth + itemWidth, beginHeight + itemHeight);
                    //换下一行
                    beginWidth = intervalLeftRight;
                    beginHeight = beginHeight + itemHeight + intervalTop;
                    surplusWidth = width - intervalLeftRight * 2;
                } else {
                    //不是新一行，换一行再放
                    //换下一行
                    beginWidth = intervalLeftRight;
                    beginHeight = beginHeight + itemHeight + intervalTop;
                    surplusWidth = width - intervalLeftRight * 2;
                    addView(itemViews.get(i));
                    itemViews.get(i).layout(beginWidth, beginHeight, beginWidth + itemWidth, beginHeight + itemHeight);
                    beginWidth = beginWidth + itemWidth + intervalLeftRight;
                    surplusWidth = surplusWidth - itemWidth - intervalLeftRight;
                }


            } else {
                addView(itemViews.get(i));
                itemViews.get(i).layout(beginWidth, beginHeight, beginWidth + itemWidth, beginHeight + itemHeight);
                beginWidth = beginWidth + itemWidth + intervalLeftRight;
                surplusWidth = surplusWidth - itemWidth - intervalLeftRight;
            }

        }


    }

    OnClickListener autoWrapItemListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (autoWrapGroupClick != null) {
                L.e("整个点击");
                autoWrapGroupClick.onItemClick((Integer) view.getTag());
            }
        }
    };



    AutoWrapItem.AutoWrapItemClick autoWrapItemClick = new AutoWrapItem.AutoWrapItemClick() {
        @Override
        public void onItemClick(int index) {
            if (autoWrapGroupClick != null) {
                L.e("text点击");
                autoWrapGroupClick.onItemClick(index);
            }
        }

        @Override
        public void onDeleteClick(int index) {
            if (autoWrapGroupClick != null) {
                autoWrapGroupClick.onDeleteClick(index);
            }
        }
    };

}
