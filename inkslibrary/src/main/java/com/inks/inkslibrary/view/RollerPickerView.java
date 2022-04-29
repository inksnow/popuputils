package com.inks.inkslibrary.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import com.inks.inkslibrary.R;

import java.util.ArrayList;


/**
 * Created by inks on 2019/5/1 0021.
 */

public class RollerPickerView extends View {
    private ArrayList<String> lists = new ArrayList<>();
    private int itemNumber = 3;
    private float textSize = 60;
    private int textUncheckedColour = 0XFF515151;
    private int textSelectionColour = 0Xff2b2b2b;
    private float midTextScale = 0.1f;
    //选中条目背景色
    private int selectedBgMid = 0XFFf0f0f0;
    private int selectedBgSides = 0XFFf0f0f0;
    //未选中的背景色
    private int unSelectedBgMid = 0XFFf0f0f0;
    private int unSelectedBgSides = 0XFFf0f0f0;
    //分割线颜色
    private int dividingLineColor = 0Xffdddddd;
    //当前选中项
    private int selectIndex = 0;

    //左偏移,值越大越靠左
    private int leftDeviation = 0;
    //标注，如时分秒
    private String tagText = "";
    //tag颜色
    private  int tagColor = 0XFFf0f0f0;
    //tag大小
    private int tagTextSize = 60;
    //TAG右偏移，值越大越靠左
    private int tagRightDeviation = 0;


    private static String TAG = "RollerPickerView";
    private Paint mPaint;//文字画笔
    private Paint mTagPaint;//tag文字画笔
    private Paint mLinePaint;//分割线画笔
    private Paint mbgPaint;//背景画笔
    private float oneItemHeight;
    private int specWidthSize, specHeightSize;
    private GradualColourUtil gradualColourUtil;
    private float lastMoveY = 0;
    private float moveY = 0;
    //滑动速度跟踪器
    private VelocityTracker velocityTracker;
    private float yVelocity = 0;
    private boolean actionDown = false;
    private boolean needBack = false;

    //上边条目区域
    private RectF unS1;
    //下边条目区域
    private RectF unS2;
    //选中条目区域
    private RectF unS;
    //着色器
    private Shader shader1;
    private Shader shader2;
    private Shader shaderMid;


    //定义一个接口对象listerner
    private OnItemSelectListener listener;

    //获得接口对象的方法。
    public void setOnItemSelectListener(OnItemSelectListener listener) {
        this.listener = listener;
    }

    //定义一个接口
    public interface OnItemSelectListener {
        public void onItemSelect(int index, String indexString,View view);
    }

    public RollerPickerView(Context context) {
        this(context, null);
    }

    public RollerPickerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RollerPickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public RollerPickerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);

    }

    public void init(AttributeSet attrs) {

        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.RollerPickerView);
            textUncheckedColour = typedArray.getColor(R.styleable.RollerPickerView_textUncheckedColour, 0XFF515151);
            textSelectionColour = typedArray.getColor(R.styleable.RollerPickerView_textSelectionColour, 0Xff2b2b2b);
            midTextScale = typedArray.getFloat(R.styleable.RollerPickerView_midTextScale, 0.1f);
            //选中条目背景色
            selectedBgMid = typedArray.getColor(R.styleable.RollerPickerView_selectedBgMid, 0XFFf0f0f0);
            selectedBgSides = typedArray.getColor(R.styleable.RollerPickerView_selectedBgSides, 0XFFf0f0f0);
            //未选中的背景色
            unSelectedBgMid = typedArray.getColor(R.styleable.RollerPickerView_unSelectedBgMid, 0XFFf0f0f0);
            unSelectedBgSides = typedArray.getColor(R.styleable.RollerPickerView_unSelectedBgSides, 0XFFf0f0f0);
            //分割线颜色
            dividingLineColor = typedArray.getColor(R.styleable.RollerPickerView_dividingLineColor, 0Xffdddddd);
            textSize = typedArray.getDimension(R.styleable.RollerPickerView_textSize, 60);
            itemNumber = typedArray.getInteger(R.styleable.RollerPickerView_itemNumber, 3);

            leftDeviation = (int) typedArray.getDimension(R.styleable.RollerPickerView_leftDeviation, 0);

            tagRightDeviation =(int)  typedArray.getDimension(R.styleable.RollerPickerView_tagRightDeviation, 0);

            tagTextSize = (int) typedArray.getDimension(R.styleable.RollerPickerView_tagTextSize, textSize);

            tagColor = typedArray.getColor(R.styleable.RollerPickerView_tagColor, textSelectionColour);

            tagText = typedArray.getString(R.styleable.RollerPickerView_tagText);



            typedArray.recycle();
        }


        gradualColourUtil = new GradualColourUtil(textUncheckedColour, textSelectionColour);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(dividingLineColor);
        mLinePaint.setStrokeWidth(4);
        mbgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mTagPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTagPaint.setTextAlign(Paint.Align.CENTER);
        mTagPaint.setTextSize(tagTextSize);
        mTagPaint.setColor(tagColor);

        if (velocityTracker == null) {//velocityTracker对象为空 获取velocityTracker对象
            velocityTracker = VelocityTracker.obtain();
        } else {//velocityTracker对象不为空 将velocityTracker对象重置为初始状态
            velocityTracker.clear();
        }
        lists.clear();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        specWidthSize = MeasureSpec.getSize(widthMeasureSpec);//宽
        specHeightSize = MeasureSpec.getSize(heightMeasureSpec);//高
        oneItemHeight = specHeightSize / (float) itemNumber;
        setMeasuredDimension(specWidthSize, specHeightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (moveY == 0) {
            if (Math.abs(lastMoveY) > oneItemHeight / 2) {
                if (lastMoveY > 0) {
                    selectIndex++;
                } else {
                    selectIndex--;
                }
            }
            lastMoveY = 0;
            if (!actionDown) {
                int selectItemIndex;
                if (lists.size() > 0) {
                    if (selectIndex > 0) {
                        selectItemIndex = -selectIndex + (1 - (-selectIndex + 1) / lists.size()) * lists.size();
                    } else {
                        selectItemIndex = -selectIndex + selectIndex / lists.size() * lists.size();
                    }
                    if (listener != null) {
                        if (needBack) {
                            listener.onItemSelect(selectItemIndex, lists.get(selectItemIndex),this);
                            needBack = false;
                        }
                    }
                }
            }
        } else {
            lastMoveY = lastMoveY + moveY;
            moveY = 0;
            if (Math.abs(lastMoveY) >= oneItemHeight) {
                if (lastMoveY > 0) {
                    lastMoveY = lastMoveY - oneItemHeight;
                    selectIndex++;
                } else {
                    lastMoveY = lastMoveY + oneItemHeight;
                    selectIndex--;
                }
            }
        }
        if (unS1 == null || unS == null || unS2 == null) {
            unS1 = new RectF(0, 0, specWidthSize, (itemNumber - 1) / 2 * oneItemHeight);
            unS = new RectF(0, (itemNumber - 1) / 2 * oneItemHeight, specWidthSize, (itemNumber + 1) / 2 * oneItemHeight);
            unS2 = new RectF(0, (itemNumber + 1) / 2 * oneItemHeight, specWidthSize, specHeightSize);
        }
        if (shader1 == null || shader2 == null || shaderMid == null) {
            shader1 = new LinearGradient(0, 0, 0, (itemNumber - 1) / 2 * oneItemHeight, unSelectedBgSides, unSelectedBgMid, Shader.TileMode.REPEAT);
            shader2 = new LinearGradient(0, (itemNumber + 1) / 2 * oneItemHeight, 0, specHeightSize, unSelectedBgMid, unSelectedBgSides, Shader.TileMode.REPEAT);
            shaderMid = new LinearGradient(0, (itemNumber - 1) / 2 * oneItemHeight, 0, (itemNumber + 1) / 2 * oneItemHeight, new int[]{selectedBgSides, selectedBgMid, selectedBgSides}, null, Shader.TileMode.REPEAT);
        }
        mbgPaint.setShader(shader1);
        canvas.drawRect(unS1, mbgPaint);
        mbgPaint.setShader(shader2);
        canvas.drawRect(unS2, mbgPaint);
        mbgPaint.setShader(shaderMid);
        canvas.drawRect(unS, mbgPaint);

        canvas.drawLine(0, (itemNumber - 1) / 2 * oneItemHeight, specWidthSize, (itemNumber - 1) / 2 * oneItemHeight, mLinePaint);
        canvas.drawLine(0, (itemNumber + 1) / 2 * oneItemHeight, specWidthSize, (itemNumber + 1) / 2 * oneItemHeight, mLinePaint);

        //text到中心的最远距离（隐藏到中心）
        float furthest = (itemNumber + 1) / 2.0f * oneItemHeight;
        float midDistance = (itemNumber / 2.0f * oneItemHeight);

        for (int i = -1; i < itemNumber + 1; i++) {
            float baseline = oneItemHeight / 2.0f + oneItemHeight * i + lastMoveY;
            //当前距离中心距离
            float nowDistance = Math.abs(baseline - midDistance);
            //当前距离中心距离百分比
            float nowDisPer = nowDistance / furthest;
            int alpha = 255 - (int) (nowDisPer * 255);
            float scale = midTextScale * nowDisPer;
            if (nowDistance < oneItemHeight) {
                if (nowDistance < (oneItemHeight * 0.8)) {
                    mPaint.setTextSize(textSize * ((1.0f + midTextScale - scale)));
                } else {
                    mPaint.setTextSize(textSize);
                }
                mPaint.setColor(gradualColourUtil.getColour(1 - (nowDistance / oneItemHeight)));
            } else {
                mPaint.setTextSize(textSize);
                mPaint.setColor(textUncheckedColour);
            }
            mPaint.setAlpha(alpha);
            //mPaint.setTextSize(textSize*(1.0f+0.6f-scale)+textSize*(0.6f-scale)*0.5f);
            //mPaint.setTextScaleX(1.0f+scale);
            Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
            float distance = (fontMetrics.bottom - fontMetrics.top) / 2.0f - fontMetrics.bottom;
            String text;
            if (lists.size() > 1) {
                if (i < ((itemNumber - 1) / 2)) {
                    //距离中间条目的个数（上）
                    int x = (((itemNumber - 1) / 2) - i) + selectIndex;

                    if (x <= 0) {
                        int x2 = -x % lists.size();
                        text = lists.get(x2);
                    } else {
                        //绝对个数
                        int x2 = (x - ((x - 1) / lists.size()) * lists.size());
                        text = lists.get((lists.size() - x2));
                    }

                } else {
                    //距离中间条目的个数（下）
                    int x = (i - ((itemNumber - 1) / 2)) - selectIndex;
                    if (x < 0) {
                        int x2 = lists.size() - ((-x) - ((-x) - 1) / lists.size() * lists.size());
                        text = lists.get((x2));
                    } else {
                        //绝对个数
                        int x2 = x % lists.size();
                        text = lists.get(x2);
                    }

                }
            } else if (lists.size() == 1) {
                text = lists.get(0);
            } else {
                text = "Empty";
            }
            canvas.drawText(text, specWidthSize / 2.0f-leftDeviation, baseline + distance, mPaint);
        }

        //画tag
        Paint.FontMetrics fontMetrics = mTagPaint.getFontMetrics();
        float distance = (fontMetrics.bottom - fontMetrics.top) / 2.0f - fontMetrics.bottom;
        canvas.drawText(tagText, specWidthSize-mTagPaint.measureText(tagText)-tagRightDeviation, midDistance+distance, mTagPaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        velocityTracker.addMovement(event);//向velocityTracker对象添加action
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                actionDown = true;
                mHandler.removeMessages(111);
                moveY = 0;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                actionDown = false;
                velocityTracker.computeCurrentVelocity(1000, 3500);
                yVelocity = velocityTracker.getYVelocity();
                mHandler.removeMessages(111);
                mHandler.sendEmptyMessageDelayed(111, 30);
                break;
            case MotionEvent.ACTION_MOVE:
                velocityTracker.computeCurrentVelocity(1000, 3500);
                moveY = velocityTracker.getYVelocity() / 100;
                if (moveY != 0) {
                    invalidate();
                }
                break;
        }
        return true;
    }


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 111:
//                    if (yVelocity >= 100) {
//                        yVelocity = yVelocity - 50;
//                    } else if (yVelocity <= -50) {
//                        yVelocity = yVelocity + 50;
//                    } else if(yVelocity>=10){
//                        yVelocity = yVelocity -15;
//                    }else if(yVelocity<=-10){
//                        yVelocity = yVelocity +15;
//                    } else {
//                        yVelocity = 0;
//                    }
//
                    if(yVelocity>50){
                        yVelocity = yVelocity-50;
                    }else if(yVelocity<-50){
                        yVelocity = yVelocity+50;
                    }else{
                        yVelocity=0;
                    }

                    moveY = yVelocity / 150f;
                    needBack = true;
                    invalidate();
                    mHandler.removeMessages(111);
                    if (moveY != 0) {
                        mHandler.sendEmptyMessageDelayed(111, 30);
                    }

                    break;
                case 222:

                    break;
            }
        }
    };

    @Override
    protected void onDetachedFromWindow() {
        velocityTracker.recycle();
        super.onDetachedFromWindow();
    }


    public ArrayList<String> getLists() {
        return lists;
    }

    public void setLists(ArrayList<String> lists) {
        mHandler.removeMessages(111);
        this.lists = lists;
        lastMoveY = 0;
        moveY = 0;
        needBack = false;
        selectIndex = 0;
        invalidate();
    }

    public int getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(int itemNumber) {
        mHandler.removeMessages(111);
        lastMoveY = 0;
        moveY = 0;
        needBack = false;
        if (itemNumber > 0) {
            if (itemNumber % 2 == 0) {
                this.itemNumber = itemNumber + 1;
            } else {
                this.itemNumber = itemNumber;
            }
            shader1 = null;
            unS1 = null;
            oneItemHeight = specHeightSize / (float) this.itemNumber;
            Log.e("itemNumber:", this.itemNumber + "");
            invalidate();
        }
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
        invalidate();
    }

    public int getTextUncheckedColour() {
        return textUncheckedColour;
    }

    public void setTextUncheckedColour(int textUncheckedColour) {
        this.textUncheckedColour = textUncheckedColour;
        invalidate();
    }

    public int getTextSelectionColour() {
        return textSelectionColour;
    }

    public void setTextSelectionColour(int textSelectionColour) {
        this.textSelectionColour = textSelectionColour;
        invalidate();
    }

    public float getMidTextScale() {
        return midTextScale;
    }

    public void setMidTextScale(float midTextScale) {
        this.midTextScale = midTextScale;
        invalidate();
    }

    public int getSelectedBgMid() {
        return selectedBgMid;
    }

    public void setSelectedBgMid(int selectedBgMid) {
        this.selectedBgMid = selectedBgMid;
        shaderMid = null;
        invalidate();
    }

    public int getSelectedBgSides() {
        return selectedBgSides;
    }

    public void setSelectedBgSides(int selectedBgSides) {
        this.selectedBgSides = selectedBgSides;
        shaderMid = null;
        invalidate();
    }

    public int getUnSelectedBgMid() {
        return unSelectedBgMid;
    }

    public void setUnSelectedBgMid(int unSelectedBgMid) {
        this.unSelectedBgMid = unSelectedBgMid;
        shader1 = null;
        shader2 = null;
        invalidate();
    }

    public int getUnSelectedBgSides() {
        return unSelectedBgSides;
    }

    public void setUnSelectedBgSides(int unSelectedBgSides) {
        this.unSelectedBgSides = unSelectedBgSides;
        shader1 = null;
        shader2 = null;
        invalidate();
    }

    public int getDividingLineColor() {
        return dividingLineColor;

    }

    public void setDividingLineColor(int dividingLineColor) {
        this.dividingLineColor = dividingLineColor;
        invalidate();
    }

    public int getSelectIndex() {
        int selectItemIndex;
        if (selectIndex > 0) {
            selectItemIndex = -selectIndex + (1 - (-selectIndex + 1) / lists.size()) * lists.size();
        } else {
            selectItemIndex = -selectIndex + selectIndex / lists.size() * lists.size();
        }
        return selectItemIndex;
    }

    public void setSelectIndex(int selectIndex) {
        this.selectIndex = -selectIndex;
        mHandler.removeMessages(111);
        lastMoveY = 0;
        moveY = 0;
        needBack = false;
        invalidate();
    }

    public void setSelectStr(String str){
        for (int i = 0; i < lists.size(); i++) {
            if(lists.get(i).equals(str)){
                setSelectIndex(i);
                break;
            }

        }
    }

    public int getLeftDeviation() {
        return leftDeviation;
    }

    public void setLeftDeviation(int leftDeviation) {
        this.leftDeviation = leftDeviation;
    }

    public String getTagText() {
        return tagText;
    }

    public void setTagText(String tagText) {
        this.tagText = tagText;
    }

    public int getTagColor() {
        return tagColor;
    }

    public void setTagColor(int tagColor) {
        this.tagColor = tagColor;
    }

    public int getTagTextSize() {
        return tagTextSize;
    }

    public void setTagTextSize(int tagTextSize) {
        this.tagTextSize = tagTextSize;
    }

    public int getTagRightDeviation() {
        return tagRightDeviation;
    }

    public void setTagRightDeviation(int tagRightDeviation) {
        this.tagRightDeviation = tagRightDeviation;
    }

    public static class GradualColourUtil {
        private final int startColour;
        private final int endColour;

        public GradualColourUtil(int startColour, int endColour) {
            this.startColour = startColour;
            this.endColour = endColour;
        }

        public int getColour(float per) {
            int R = (int) ((endColour - startColour) / 65536 % 256 * per);
            int G = (int) ((endColour - startColour) / 256 % 256 * per);
            int B = (int) ((endColour - startColour) % 256 * per);
            int returnR = startColour / 65536 + R;
            int returnG = startColour / 256 % 256 + G;
            int returnB = startColour % 256 + B;
            return returnR * 65536 + returnG * 256 + returnB + 0XFF000000;
        }
    }

}
